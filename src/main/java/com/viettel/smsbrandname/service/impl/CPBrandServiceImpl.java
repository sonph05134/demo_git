package com.viettel.smsbrandname.service.impl;

import com.google.gson.Gson;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.*;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.domain.CpAlias;
import com.viettel.smsbrandname.domain.TransLogAlias;
import com.viettel.smsbrandname.repository.AliasCostRepository;
import com.viettel.smsbrandname.repository.CPBrandRepository;
import com.viettel.smsbrandname.repository.CpRepository;
import com.viettel.smsbrandname.repository.TransLogAliasRepository;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.CPBrandService;
import com.viettel.smsbrandname.service.ChargingService;
import com.viettel.smsbrandname.service.dto.cpbrand.*;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CPBrandServiceImpl extends StandardizeLogging implements CPBrandService {

    private static final Logger logger = LoggerFactory.getLogger(CPBrandServiceImpl.class);

    @Value("${folder-dir.attachFileDir}")
    private String cpAttachDir;

    @Autowired
    CPBrandRepository cpBrandRepository;

    @Autowired
    CpRepository cpRepository;

    @Autowired
    ChargingService chargingService;

    @Autowired
    TransLogAliasRepository transLogAliasRepository;

    @Autowired
    AliasCostRepository aliasCostRepository;

    @Autowired
    ExcelUtils excelUtils;

    private final EntityManager entityManager;

    public CPBrandServiceImpl(EntityManager entityManager) {
        super(CPBrandServiceImpl.class);
        this.entityManager = entityManager;
    }

    @Override
    public List<CPBrandTelcoDTO> getTelcos() {
        return cpBrandRepository.getTelcos();
    }

    @Override
    public ApiResponseDTO search(Integer aliasType, String brandName, String telco, Integer start, Integer limit, Long cpId) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> listData = cpBrandRepository.search(aliasType, brandName, telco, start, limit, cpId);
        apiResponseDTO.setTotalRow(cpBrandRepository.search(aliasType, brandName, telco, 0, 0, cpId).size());
        apiResponseDTO.setData(CpBrandResultDTO.toDto(listData));
        return apiResponseDTO;
    }

    @Override
    @Transactional
    public void activeOrInactiveBrand(CpBrandActionDTO cpBrandActionDTO) {

        Long cpAliasId = cpBrandActionDTO.getCpAliasID();
        Long cpAliasTempId =
            null == cpBrandActionDTO.getCpAliasTmpID() || -1 == cpBrandActionDTO.getCpAliasTmpID() ? null : cpBrandActionDTO.getCpAliasTmpID();
        String actionKeyword = cpBrandActionDTO.getActionKeyword();
        String username = cpBrandActionDTO.getUserName();
        Integer action = cpBrandActionDTO.getAction();
        // active or inactive here
        cpBrandRepository.updateStatus(cpAliasId, cpAliasTempId, username, action);
    }

    @Override
    @Transactional
    public void deleteCpBrand(CpBrandActionDTO cpBrandActionDTO) {
        Date date = new Date();
        Session session = entityManager.unwrap(Session.class);
        try {
            String userName = SecurityUtils.getCurrentUserLogin().get();
            CpAlias cpAlias = cpBrandRepository.getCpAlias(cpBrandActionDTO.getCpAliasID());
            Cp cp = cpRepository.getByCpId(cpAlias.getCpId()).isPresent() ? cpRepository.getByCpId(cpAlias.getCpId()).get() : null;

            Long cpAliasId = cpBrandActionDTO.getCpAliasID();
            Long cpAliasTempId =
                null == cpBrandActionDTO.getCpAliasTmpID() || -1 == cpBrandActionDTO.getCpAliasTmpID() ? null : cpBrandActionDTO.getCpAliasTmpID();
            String actionKeyword = cpBrandActionDTO.getActionKeyword();
            String username = cpBrandActionDTO.getUserName();

            Long cpId = !DataUtil.isNullOrEmpty(cp) ? cp.getCpId() : 0L;
            String cpCode = !DataUtil.isNullOrEmpty(cp) ? cp.getCpCode() : "";
            String telco = !DataUtil.isNullOrEmpty(cpAlias) ? cpAlias.getTelco() : "";
            String brandName = !DataUtil.isNullOrEmpty(cpAlias) ? cpAlias.getCpAlias() : "";
            String currency = !DataUtil.isNullOrEmpty(cp) ? cp.getCurrency() : "";
            String aliasType = !DataUtil.isNullOrEmpty(cpAlias) ? DataUtil.safeToString(cpAlias.getAliasType()) : "";
            // insert history data
            cpBrandRepository.createHistoryData(cpAliasId, cpAliasTempId, actionKeyword, username);
            /////////////////
            CpAliasTmpFileDTO cpAliasTmp = cpBrandRepository.getCpAliasTmpByCpAliasID(cpAliasId);

            if (cpBrandRepository.chargeCancelAlias(cpId, cpCode, telco, brandName, currency, aliasType)) {
                if (cpBrandRepository.deleteCpAlias(cpAliasId, userName) > 0) {
                    Boolean check = true;
                    if (cpAliasTmp != null && cpAliasTmp.getCpAliasId() != null && cpAliasTmp.getCpAliasId() > 0) {
                        if (cpBrandRepository.deleteCpAliasTmp(cpAliasTmp.getCpAliasId()) <= 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, Constants.RESULT.DEL_SUCCESS_STR, date);
                        int countAlias = cpBrandRepository.countAlias(cpId, brandName, aliasType, userName);
                        if (countAlias != -1 && countAlias == 0) {
                            //delete all alias info in cp_users_alias
                            cpBrandRepository.deleteCpUsersAlias(cpId, brandName, aliasType, userName);
                        }
                    } else {
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "DeleteCpAliasTMP_Nok", date);
                        session.doWork(Connection::rollback);
                    }
                } else {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, Constants.RESULT.DEL_FAIL_STR, date);
                    session.doWork(Connection::rollback);
                }
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
                session.doWork(Connection::rollback);
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            session.doWork(Connection::rollback);
            throw ex;
        }
    }

    @Override
    public ByteArrayInputStream exportBrands(CPBrandSearchDTO cpBrandSearchDTO) throws Exception {
        Date date = new Date();
        ByteArrayInputStream dataStream;
        try {
            List<String> header = new ArrayList<>();
            header.add(Translator.toLocale("export.cp.alias.type"));
            header.add(Translator.toLocale("export.cp.alias"));
            header.add(Translator.toLocale("export.cp.group.name"));
            header.add(Translator.toLocale("export.cp.telco.name"));
            header.add(Translator.toLocale("export.cp.created.date"));
            header.add(Translator.toLocale("export.cp.status"));

            List<String> column = new ArrayList<>();
            column.add("aliasTypeName");
            column.add("alias");
            column.add("groupTypeName");
            column.add("telcoName");
            column.add("createDateStr");
            column.add("statusName");

            String title = Translator.toLocale("export.cp.title");

            List<Integer> sizes = new ArrayList<>();
            sizes.add(ExcelUtils.WIDTH * 20);
            sizes.add(ExcelUtils.WIDTH * 20);
            sizes.add(ExcelUtils.WIDTH * 20);
            sizes.add(ExcelUtils.WIDTH * 20);
            sizes.add(ExcelUtils.WIDTH * 20);
            sizes.add(ExcelUtils.WIDTH * 35);

            List<Integer> aligns = new ArrayList<>();
            aligns.add(ExcelUtils.ALIGN_LEFT);
            aligns.add(ExcelUtils.ALIGN_LEFT);
            aligns.add(ExcelUtils.ALIGN_LEFT);
            aligns.add(ExcelUtils.ALIGN_LEFT);
            aligns.add(ExcelUtils.ALIGN_CENTER);
            aligns.add(ExcelUtils.ALIGN_LEFT);

            List<CpBrandResultDTO> cpBrandResult = CpBrandResultDTO.toDto(cpBrandRepository
                .search(cpBrandSearchDTO.getAliasType(), cpBrandSearchDTO.getBrandName(), cpBrandSearchDTO.getTelco(),
                    0, 0, cpBrandSearchDTO.getCpId()));

            if (cpBrandResult.isEmpty()) {
                throw new BusinessException(ErrorCodeResponse.DATA_NOT_MATCH);
            }

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String dateExport = Translator.toLocale("excel.date-export") + " " + df.format(new Date());
            dataStream = excelUtils.export(cpBrandResult, header, column, title, sizes, aligns, dateExport);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return dataStream;
    }


    @Override
    public List<ComboboxResponseDTO> getAliasGroup(Integer type) {
        return new MaperUtils().convertToListCombobox(cpBrandRepository.getAliasGroup(type));
    }

    public List<ComboboxResponseDTO> getWS(Integer status) {
        return new MaperUtils().convertToListCombobox(cpBrandRepository.getWS(status));
    }

    public List<ComboboxResponseDTO> getKeepFee() {
        return new MaperUtils().convertToListCombobox(cpBrandRepository.getKeepFee());
    }

    @Override
    @Transactional
    public ApiResponseDTO createCpAlias(String data, String username, String userId, String commissionDefault, String checked, MultipartFile file) {
        Date execDate = new Date();
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        if (!DataUtil.isNullOrEmpty(data)) {
            CpAlias cpAlias = new Gson().fromJson(data, CpAlias.class);
            if (null != cpAlias) {
                if (chargeRegAlias(cpAlias)) {
                    if (cpBrandRepository.checkAliasExitsInCreate(cpAlias.getCpAlias(), cpAlias.getTelco(), cpAlias.getCpId())) {
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Insert CpAlias fail. CpAlias is duplicated", execDate);
                        throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("Brandname"));
                    }
                    // 0: chua check tax code and company name
                    // 1: da check tax code and company name
                    if ("0".equals(checked)) {
                        String cpCode = cpBrandRepository.checkTaxCodeAndCompanyName(cpAlias.getCpId(), cpAlias.getTaxCode(), cpAlias.getCompanyName());
                        if (!DataUtil.isNullOrEmpty(cpCode)) {
                            apiResponseDTO.setData(cpCode);
                            apiResponseDTO.setErrors(Translator.toLocale("error.cp.company.name.or.tax.code.exist"));
                            apiResponseDTO.setCode(Constants.CP_ALIAS_EXITS_TAX_CODE_OR_COMPANY_NAME);
                            return apiResponseDTO;
                        }
                    }

                    if (!DataUtil.isNullOrEmpty(file)) {
                        String fileName = cpAlias.getAttachFile();
                        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        String outputAttachFileName = cpAlias.getCpId() + "_" + cpAlias.getCpId() + date + "." + fileType;
                        saveAttachFile(file, outputAttachFileName);

                        cpAlias.setAttachFile(outputAttachFileName);
                    }

                    if (!DataUtil.isNullOrEmpty(cpAlias.getInactiveDateStr())) {
                        final DateTimeFormatter formatter = DateTimeFormatter
                            .ofPattern("dd/MM/yyyy HH:mm:ss")
                            .withZone(ZoneId.systemDefault());
                        Instant result = Instant.from(formatter.parse(cpAlias.getInactiveDateStr()));
                        cpAlias.setInactiveDate(result);
                    } else {
                        cpAlias.setInactiveDate(null);
                    }

                    cpAlias.setCreateDate(Instant.now());
                    cpAlias.setAccCreate(username);
                    CpAlias newCpAlias = saveAlias(cpAlias, username);

                    cpBrandRepository.createHistoryData(newCpAlias.getCpAliasId(), null, "prop.new", username);

                    if ("0".equals(commissionDefault) && !cpBrandRepository.updateCommissionDefault(newCpAlias.getCpId(), userId, username)) {
                        logger.info("insert to COMMISSION_PERCENT_CODE fail with cpId: {}", newCpAlias.getCpId());
                        throw new BusinessException(ErrorCodeResponse.CREATE_ALIAS_FAIL);
                    }

                    apiResponseDTO.setData(null);
                    apiResponseDTO.setCode(Constants.SUCCESS);
                } else {
                    throw new BusinessException(ErrorCodeResponse.NOT_MONEY);
                }
            }

        }
        return apiResponseDTO;
    }

    @Override
    @Transactional
    public ApiResponseDTO editCpAlias(String data, String username, String userId, String commissionDefault, String checked, MultipartFile file) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        if (!DataUtil.isNullOrEmpty(data)) {
            CpAlias cpAlias = new Gson().fromJson(data, CpAlias.class);
            if (null != cpAlias) {
                if (cpBrandRepository.checkAliasExitsInEdit(cpAlias.getCpAlias(), cpAlias.getTelco(), cpAlias.getCpId(), cpAlias.getCpAliasId())) {
                    throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("Brandname"));
                }

                // 0: chua check tax code and company name
                // 1: da check tax code and company name
                if ("0".equals(checked)) {
                    String cpCode = cpBrandRepository.checkTaxCodeAndCompanyName(cpAlias.getCpId(), cpAlias.getTaxCode(), cpAlias.getCompanyName());
                    if (!DataUtil.isNullOrEmpty(cpCode)) {
                        apiResponseDTO.setData(cpCode);
                        apiResponseDTO.setErrors(Translator.toLocale("error.cp.company.name.or.tax.code.exist"));
                        apiResponseDTO.setCode(Constants.CP_ALIAS_EXITS_TAX_CODE_OR_COMPANY_NAME);
                        return apiResponseDTO;
                    }
                }

                Long updateStatus = cpAlias.getStatus();
                Long oldStatus;
                Long oldCpAliasTmpId;
                String oldAttachFileName;

                // saveOrEdit new data is backup data
                CpAlias backUpData = cpBrandRepository.getCpAlias(cpAlias.getCpAliasId());
                if (backUpData != null) {
                    CpAlias clone = SerializationUtils.clone(backUpData);
                    oldCpAliasTmpId = backUpData.getCpAliasTmpId();
                    oldStatus = backUpData.getStatus();
                    oldAttachFileName = backUpData.getAttachFile();
                    clone.setCpAliasId(null);
                    clone.setStatus(2L);
                    cpBrandRepository.save(clone);

                    if (!DataUtil.isNullOrEmpty(oldCpAliasTmpId)) {
                        cpBrandRepository.updateCpAliasTmp(backUpData);
                    }
                } else {
                    logger.info("Not found old data of Id {}", cpAlias.getCpAliasId());
                    throw new BusinessException(ErrorCodeResponse.EDIT_ALIAS_FAIL);
                }

                if (!DataUtil.isNullOrEmpty(file)) {
                    if (!DataUtil.isNullOrEmpty(oldAttachFileName)) {
                        boolean deleteFile = FileUtils.deleteAttachFile(cpAttachDir, oldAttachFileName);
                        if (!deleteFile) {
                            logger.info("Delete old file fail with alias Id {}", cpAlias.getCpAliasId());
                            throw new BusinessException(ErrorCodeResponse.EDIT_ALIAS_FAIL);
                        }
                    }

                    String outputAttachFileName = renameFile(cpAlias.getAttachFile(), cpAlias.getCpId(), cpAlias.getCpAlias());
                    saveAttachFile(file, outputAttachFileName);

                    cpAlias.setAttachFile(outputAttachFileName);
                }

                if (null != oldStatus && 3L == oldStatus && 0L == updateStatus) {
                    updateStatus = 3L;
                }

                cpAlias.setStatus(updateStatus);
                cpAlias.setUpdateDate(Instant.now());
                cpAlias.setAccUpdate(username);
                cpAlias.setCreateDate(backUpData.getCreateDate());
                cpAlias.setAccCreate(backUpData.getAccCreate());

                if (!DataUtil.isNullOrEmpty(cpAlias.getInactiveDateStr())) {
                    final DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm:ss")
                        .withZone(ZoneId.systemDefault());
                    Instant result = Instant.from(formatter.parse(cpAlias.getInactiveDateStr()));
                    cpAlias.setInactiveDate(result);
                } else {
                    cpAlias.setInactiveDate(null);
                }

                CpAlias newCpAlias = saveAlias(cpAlias, username);

                cpBrandRepository.createHistoryData(newCpAlias.getCpAliasId(), oldCpAliasTmpId, "prop.update", username);

                if ("0".equals(commissionDefault) && !cpBrandRepository.updateCommissionDefault(newCpAlias.getCpId(), userId, username)) {
                    logger.info("insert to COMMISSION_PERCENT_CODE fail with cpId: {}", newCpAlias.getCpId());
                    throw new BusinessException(ErrorCodeResponse.EDIT_ALIAS_FAIL);
                }

                apiResponseDTO.setData(null);
                apiResponseDTO.setCode(Constants.SUCCESS);
            } else {
                logger.info("Convert data form Json fail");
                throw new BusinessException(ErrorCodeResponse.EDIT_ALIAS_FAIL);
            }

        }
        return apiResponseDTO;
    }

    private String renameFile(String fileName, Long cpId, String cpAlias) {
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return cpAlias + "_" + cpId + date + "." + fileType;
    }

    private void saveAttachFile(MultipartFile file, String fileName) {
        int validateAttachFile = FileUtils.validateAttachFile(file, fileName);
        if (validateAttachFile == 24) {
            throw new BusinessException(ErrorCodeResponse.FILE_WRONG_TYPE);
        } else if (validateAttachFile == 25) {
            throw new BusinessException(ErrorCodeResponse.FILE_TOO_LARGE);
        }

        //copy file
        Boolean copyTmp = FileUtils.copyAttachFile(file, cpAttachDir, fileName);
        if (!copyTmp) {
            logger.error("Khong copy duoc file dinh kem len server");
            throw new BusinessException(ErrorCodeResponse.SAVE_FILE_FAIL);
        }
    }

    private CpAlias saveAlias(CpAlias cpAlias, String username) {
        Date date = new Date();
        try {
            cpAlias.setType(0L);
            cpAlias.setAccUpdate(username);
            if ("0".equals(cpAlias.getCheckBlockSpam())) {
                cpAlias.setCheckBlockSpam("NO");
            } else {
                cpAlias.setCheckBlockSpam("YES");
            }

            if (cpAlias.getKeepFee() == null) {
                cpAlias.setKeepFee(0L);
            }
            cpAlias.setUssdEnabled(0L);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return cpBrandRepository.saveAndFlush(cpAlias);

    }

    @Override
    public ByteArrayInputStream downloadAttachFile(String fileName) throws IOException {
        File file = new File(cpAttachDir + File.separator + fileName);
        if (!file.exists()) {
            throw new BusinessException(ErrorCodeResponse.FILE_NOT_EXIST);
        }
        return new ByteArrayInputStream(
            FileCopyUtils.copyToByteArray(file));
    }

    private boolean chargeRegAlias(CpAlias cpAlias) {
        try {
            Optional<Cp> cpOptional = cpRepository.findFirstByCpIdAndStatus(cpAlias.getCpId(), Long.valueOf(Constants.STATUS.ACTIVE));
            if (cpOptional.isPresent()) {
                Cp cp = cpOptional.get();

                if (Constants.PRE.equals(cp.getChargeType())) {
                    if (cpAlias.getOptionalKeepFee() == 1) {
                        //Co phi duy tri
                        String aliasCost = cpAlias.getKeepFee().toString();
                        BigDecimal regAliasCost = new BigDecimal(aliasCost);
                        saveTransHis(cpAlias, regAliasCost, regAliasCost, cp);
                    } else {
                        //Khong co phi duy tri
                        Optional<AliasCost> aliasCostOptional = aliasCostRepository.findFirstByAliasCostTypeAndAliasTelco(cpAlias.getAliasType(), cpAlias.getTelco());
                        if (aliasCostOptional.isPresent()) {
                            AliasCost aliasCost = aliasCostOptional.get();
                            BigDecimal regAliasCost = new BigDecimal(aliasCost.getRegAliasCost());
                            BigDecimal regAliasCostUsd = new BigDecimal(aliasCost.getRegAliasCostUSD());
                            saveTransHis(cpAlias, regAliasCost, regAliasCostUsd, cp);
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private void saveTransHis(CpAlias cpAlias, BigDecimal regAliasCost, BigDecimal regAliasCostUsd, Cp cp) {
        Date date = new Date();
        try {
            TransLogAlias transLogAlias = new TransLogAlias();
            transLogAlias.setCpId(cpAlias.getCpId());
            transLogAlias.setAliasType(Integer.parseInt(cpAlias.getAliasType().toString()));
            transLogAlias.setAlias(cpAlias.getCpAlias() + "_aliasfee");
            transLogAlias.setTransTime(new java.sql.Date(System.currentTimeMillis()));

            if (Constants.VND.equals(cp.getCurrency()) && regAliasCost.longValue() > 0) {
                boolean chargeResult = chargingService.charge(cp.getCpCode(), regAliasCost, 2, "charge_RegAliasCost", cpAlias.getCpAlias() + "_aliasfee", Integer.parseInt(cpAlias.getAliasType().toString()));
                if (!chargeResult) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
                    throw new BusinessException(ErrorCodeResponse.NOT_MONEY);
                }
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "chargRegAlias VND success", date);
                transLogAlias.setAmount(regAliasCost.negate());
                transLogAliasRepository.save(transLogAlias);
            } else if (Constants.USD.equals(cp.getCurrency()) && regAliasCostUsd.longValue() > 0) {
                boolean chargeResult = chargingService.charge(cp.getCpCode(), regAliasCostUsd, 2, "charge_RegAliasCostUsd", cpAlias.getCpAlias() + "_aliasfee", Integer.parseInt(cpAlias.getAliasType().toString()));
                if (!chargeResult) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
                    throw new BusinessException(ErrorCodeResponse.NOT_MONEY);
                }
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "chargRegAlias USD success", date);
                transLogAlias.setAmount(regAliasCostUsd.negate());
                transLogAliasRepository.save(transLogAlias);
            }
        } catch (Exception ex) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

}
