package com.viettel.smsbrandname.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viettel.log.util.ConstantsLog;
import com.viettel.security.PassTranformer;
import com.viettel.smsbrandname.commons.*;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.domain.CpGroupSub;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.repository.CpRepository;
import com.viettel.smsbrandname.repository.ProvinceUserRepository;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.*;
import com.viettel.smsbrandname.service.dto.*;
import com.viettel.smsbrandname.service.mapper.CpMapper;
import com.viettel.smsbrandname.service.mapper.ProvinceUsersMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.hibernate.Session;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CpServiceImpl extends StandardizeLogging implements CpService {

    @Value("${folder-dir.upload-dir}")
    private String cpAttachDir;

    @Value("${emailConf.adminEmail}")
    private String adminEmail;

    //tam thoi set bang true de ko gui email
    private Boolean TEST_SYSTEM = true;

    private final Logger log = LoggerFactory.getLogger(CpServiceImpl.class);

    private final CpRepository cpRepository;

    private final ProvinceUserRepository provinceUserRepository;

    private final ProvinceUsersMapper provinceUsersMapper;

    private final EntityManager entityManager;

    private final CpMapper cpMapper;

    private final ExcelUtils excelUtils;

    private final UserService userService;

    private final KeyCloakUserService keyCloakUserService;

    private final RoleService roleService;

    public CpServiceImpl(CpRepository cpRepository, EntityManager entityManager, CpMapper mapper, ProvinceUserRepository provinceUserRepository, ProvinceUsersMapper provinceUsersMapper, ExcelUtils excelUtils, UserService userService, KeyCloakUserService keyCloakUserService, RoleService roleService) {
        super(CpServiceImpl.class);
        this.cpRepository = cpRepository;
        this.entityManager = entityManager;
        this.cpMapper = mapper;
        this.provinceUserRepository = provinceUserRepository;
        this.provinceUsersMapper = provinceUsersMapper;
        this.excelUtils = excelUtils;
        this.userService = userService;
        this.keyCloakUserService = keyCloakUserService;
        this.roleService = roleService;
    }

    public CommonResponseDTO search(CpSearchDTO searchDTO) {
        return cpRepository.search(searchDTO);
    }

    public List<ProvinceDTO> findAllProvince() {
        return cpRepository.findAllProvince();
    }

    public List<DistrictDTO> findDistrictByProvinceId(Long id) {
        return cpRepository.findDistrictByProvinceId(id);
    }

    @Override
    public Cp add(CpDTO cp, MultipartFile attachFile, MultipartFile monthCommissionAttachFile) {
        if (!DataUtil.isNullOrEmpty(attachFile)) {
            String fileName = attachFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String outputAttachFileName = cp.getCpCode() + "_" + date + "." + fileType;
            cp.setAttachFile(outputAttachFileName);
            saveAttachFile(attachFile, outputAttachFileName);
        }
        if (!DataUtil.isNullOrEmpty(monthCommissionAttachFile)) {
            String fileName = monthCommissionAttachFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String outputAttachFileName = cp.getCpCode() + "c" + date + "." + fileType;
            cp.setMonthCommissionAttachFile(outputAttachFileName);
            saveAttachFile(monthCommissionAttachFile, outputAttachFileName);
        }
        try {
            if (!DataUtil.isNullOrEmpty(cp.getWsPassword())) {
                cp.setWsPassword(PassTranformer.encrypt(cp.getWsPassword()));
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeResponse.CANT_ENCRYPT_PWS);
        }
        cp.setDateCreated(new Date());
        cp.setCpNameRoman(DataUtil.removeSpecialChars(cp.getCpName()));
        Cp addEntity = cpRepository.save(cpMapper.toEntity(cp));
        insertCpUsers(addEntity.getUserName(), addEntity.getCpId());
        KeyCloakUserDTO keyCloakUserDTO = new KeyCloakUserDTO();
        keyCloakUserDTO.setEnable(true);
        keyCloakUserDTO.setUsername(addEntity.getUserName());
        keyCloakUserDTO.setPassword(cp.getPassword());
        keyCloakUserDTO.setLastName(addEntity.getCpName());
        keyCloakUserDTO.setCpId(addEntity.getCpId());
        keyCloakUserDTO.setCpCode(addEntity.getCpCode());
        saveUserToKeyCloakServer(keyCloakUserDTO, addEntity);
        sendEmailToUser(addEntity);
        return addEntity;
    }

    @Override
    public List<CommissionPercentCodeDTO> getAllCommissionPercentCode() {
        return cpRepository.getAllCommissionPercentCode();
    }

    @Override
    public CpDTO checkExistedValue(String cpCode, String cpName, String userName, String wsUsername, String cpCysId) {
        return cpRepository.checkExistedValue(cpCode, cpName, userName, wsUsername, cpCysId);
    }

    @Override
    public CpDTO findCpById(Long id) {
        String provinceUserName = "";
        String reason = cpRepository.searchAdjustCommissionHis(id).size() > 0 ? cpRepository.searchAdjustCommissionHis(id).get(0).getReason() : "";
        CpDTO dto = new CpDTO();
        Cp cp = cpRepository.findById(id).isPresent() ? cpRepository.findById(id).get() : null;
        if (!DataUtil.isNullOrEmpty(cp) && !DataUtil.isNullOrEmpty(cp.getProvinceUserId())) {
            provinceUserName = provinceUserRepository.findById(cp.getProvinceUserId()).isPresent() ? provinceUserRepository.findById(cp.getProvinceUserId()).get().getUserName() : "";
        }
        dto = cpMapper.toDto(cp);
        dto.setProvinceUserName(provinceUserName);
        dto.setReason(reason);
        return dto;
    }

    @Override
    public Cp update(CpDTO cp, MultipartFile attachFile, MultipartFile monthCommissionAttachFile, Principal principal) {
        CpDTO oldCp = findCpById(cp.getCpId());
        if (!DataUtil.isNullOrEmpty(attachFile)) {
            String fileName = attachFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String outputAttachFileName = cp.getCpCode() + "_" + date + "." + fileType;
            cp.setAttachFile(outputAttachFileName);
            saveAttachFile(attachFile, outputAttachFileName);
        }
        if (!DataUtil.isNullOrEmpty(monthCommissionAttachFile)) {
            String fileName = monthCommissionAttachFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String outputAttachFileName = cp.getCpCode() + "_COM" + date + "." + fileType;
            cp.setMonthCommissionAttachFile(outputAttachFileName);
            saveAttachFile(monthCommissionAttachFile, outputAttachFileName);
        }
        if (!DataUtil.isNullOrEmpty(cp.getWsPassChange()) && cp.getWsPassChange()) {
            try {
                if (!DataUtil.isNullOrEmpty(cp.getWsPassword())) {
                    cp.setWsPassword(PassTranformer.encrypt(cp.getWsPassword()));
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeResponse.CANT_ENCRYPT_PWS);
            }
        }
        cp.setCpNameRoman(DataUtil.removeSpecialChars(cp.getCpName()));
        updateCpUserStatus(cp.getCpId(), cp.getUserName());
        if (!DataUtil.isNullOrEmpty(cp.getOldValueCommissionPercentCode())) adjustCommission(cp, principal);
        addOrDeleteCpSubGroupLimit(cp, oldCp);
        updateUserToKeyCloakServer(cp);
        return cpRepository.save(cpMapper.toEntity(cp));
    }

    @Override
    public CommonResponseDTO searchProvinceUser(ProvinceUserSearchDTO searchDTO) {
        return cpRepository.searchProvinceUser(searchDTO);
    }

    @Override
    public CommonResponseDTO searchStaff(StaffSearchDTO searchDTO) {
        return cpRepository.searchStaff(searchDTO);
    }

    @Override
    public void deleteCp(Long id, Boolean isSaving) {
        Boolean canDelete = keyCloakUserService.checkDeletePermission();
        if (canDelete) {
            String userId = null;
            CpDTO dto = findCpById(id);
            if (!DataUtil.isNullOrEmpty(dto) && isSaving) {
                cpRepository.deleteById(id);
                deleteCpAlias(id);
                userId = !DataUtil.isNullOrEmpty(keyCloakUserService.search(dto.getUserName())) ? keyCloakUserService.search(dto.getUserName()).get(0).getId() : null;
            }
            if (!DataUtil.isNullOrEmpty(userId) && isSaving) keyCloakUserService.delete(userId);
        } else {
            throw new BusinessException(ErrorCodeResponse.CANT_DELETE_CP);
        }
    }

    @Override
    public List<AdjustCommissionHisSearchDTO> searchAdjustCommissionHis(Long cpId) {
        return cpRepository.searchAdjustCommissionHis(cpId);
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

    private void saveAttachFile(MultipartFile file, String fileName) {
        Date date = new Date();
        int validateAttachFile = FileUtils.validateAttachFile(file, fileName);
        if (validateAttachFile == 24) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "wrongFileType", date);
            throw new BusinessException(ErrorCodeResponse.FILE_WRONG_TYPE);
        } else if (validateAttachFile == 25) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "wrongFileSize20MB", date);
            throw new BusinessException(ErrorCodeResponse.FILE_TOO_LARGE);
        }
        //copy file
        Boolean copyTmp = FileUtils.copyAttachFile(file, cpAttachDir, fileName);
        if (!copyTmp) {
            log.error("Khong copy duoc file dinh kem len server");
            throw new BusinessException(ErrorCodeResponse.SAVE_FILE_FAIL);
        } else {
            writeInfoLog("File copied successful: " + cpAttachDir + File.separator + fileName);
        }
    }

    @Override
    public Boolean checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode) {
        return cpRepository.checkExistedOrderNoOrCpName(cpName, taxCode, isSaving, cpId, commissionPercentCode);
    }

    @Override
    public List<CpDTO> findCpByUserLogin() {
        ProvinceUsers province = provinceUserRepository.getByUserNameAndStatus(SecurityUtils.getCurrentUserLogin().get(), Constants.STATUS.ACTIVE).orElse(null);
        List<CpDTO> lstCp = cpRepository.findAllByProvince(province == null ? null : province.getProvinceId(),
            province == null ? null : province.getId())
            .stream().map(cpMapper::toDto).collect(Collectors.toList());
        return lstCp;
    }

    @Override
    public List<ComboBean> findByInput() {
        return cpRepository.getCpByProvinceIdAndProvinceUserIdAndStatus();
    }

    @Override
    public ByteArrayInputStream export(CpSearchDTO dto) {
        try {
            List<CpDTO> lst = (List<CpDTO>) cpRepository.search(dto).getListData();
            if (!DataUtil.isNullOrEmpty(lst))
                lst = lst.stream().map(cpDTO -> {
                    if (!DataUtil.isNullOrEmpty(cpDTO.getApproveType()))
                        if (cpDTO.getApproveType().equals(0L)) {
                            cpDTO.setApproveTypeName(Translator.toLocale("cp.approveType0"));
                        } else {
                            cpDTO.setApproveTypeName(Translator.toLocale("cp.approveType1"));
                        }
                    if (!DataUtil.isNullOrEmpty(cpDTO.getStatus()))
                        if (cpDTO.getStatus().equals(0L)) {
                            cpDTO.setStatusName(Translator.toLocale("cp.statusInactive"));
                        } else {
                            cpDTO.setStatusName(Translator.toLocale("cp.statusActive"));
                        }
                    if (!DataUtil.isNullOrEmpty(cpDTO.getChargeType()))
                        if (cpDTO.getChargeType().equals("PRE")) {
                            cpDTO.setChargeType(Translator.toLocale("cp.chargeType.pre"));
                        } else {
                            cpDTO.setChargeType(Translator.toLocale("cp.chargeType.pos"));
                        }
                    return cpDTO;
                }).collect(Collectors.toList());
            List<String> lstHeader = Arrays.asList(
                Translator.toLocale("cp.code"),
                Translator.toLocale("cp.name"),
                Translator.toLocale("cp.channel"),
                Translator.toLocale("cp.username"),
                Translator.toLocale("cp.approveType"),
                Translator.toLocale("cp.status"),
                Translator.toLocale("cp.chargeType"),
                Translator.toLocale("cp.currency"),
                Translator.toLocale("cp.cskh"),
                Translator.toLocale("cp.qc"),
                Translator.toLocale("cp.province"),
                Translator.toLocale("cp.district"),
                Translator.toLocale("cp.address"));
            List<String> lstColumn = Arrays.asList(
                "cpCode",
                "cpName",
                "channel",
                "userName",
                "approveTypeName",
                "statusName",
                "chargeType",
                "currency",
                "balance",
                "adBalance",
                "provinceName",
                "districtName",
                "address");
            String title = Translator.toLocale("cp.title");
            List<Integer> lstSize = new ArrayList<>();
            lstSize.addAll(Arrays.asList(
                ExcelUtils.WIDTH * 20,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 15,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 40,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 20,
                ExcelUtils.WIDTH * 20,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 30,
                ExcelUtils.WIDTH * 60));
            List<Integer> lstAlign = new ArrayList<>();
            lstAlign.addAll(Arrays.asList(
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_RIGHT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT,
                ExcelUtils.ALIGN_LEFT));
            return excelUtils.export(lst, lstHeader, lstColumn, title, lstSize, lstAlign);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Integer adjustCommission(CpDTO dto, Principal principal) {
        return cpRepository.adjustCommission(dto, getUserName(principal));
    }

    private String getUserName(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getLogin();
        }
        return null;
    }

    private void saveUserToKeyCloakServer(KeyCloakUserDTO keyCloakUserDTO, Cp cpEntity) {
        try {
            keyCloakUserService.create(keyCloakUserDTO, cpEntity);
        } catch (Exception ex) {
            rollbackData(cpEntity.getCpId(), cpEntity.getUserName(), true);
            if (ex instanceof BusinessException && !Constants.ERR_CONSTANTS.ROLLBACK.equals(ex.getMessage())) {
                throw new BusinessException(((BusinessException) ex).getErrorCode(), ex.getMessage());
            }
            throw new BusinessException(ErrorCodeResponse.UNKNOWN);
        }
    }

    private void rollbackData(Long cpId, String userName, Boolean isSaving) {
        deleteCp(cpId, isSaving);
        deleteCpUsers(cpId, userName);
    }

    private void changeKeyCloakUserStatusOrCpName(String username, Long status, String cpName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        Boolean preEnable = false;
        String preLastName = null;
        try {
            userRepresentation = !DataUtil.isNullOrEmpty(keyCloakUserService.search(username)) ? keyCloakUserService.search(username).get(0) : null;
            preEnable = userRepresentation.isEnabled();
            preLastName = userRepresentation.getLastName();
            if (!DataUtil.isNullOrEmpty(userRepresentation)) {
                if (!DataUtil.isNullOrEmpty(userRepresentation.getLastName()) && !userRepresentation.getLastName().equals(cpName)) {
                    userRepresentation.setLastName(cpName);
                }
                if (Constants.STATUS_ACTIVE.equals(DataUtil.safeToInt(status))) {
                    userRepresentation.setEnabled(true);
                } else {
                    userRepresentation.setEnabled(false);
                }
                keyCloakUserService.update(userRepresentation);
            }
        } catch (Exception ex) {
            userRepresentation.setLastName(preLastName);
            userRepresentation.setEnabled(preEnable);
            keyCloakUserService.update(userRepresentation);
            throw ex;
        }
    }

    @Override
    public ProvinceUsersDTO getLoginPermission(String username) {
        return cpRepository.getLoginPermission(username);
    }

    private Integer insertCpUsers(String userName, Long cpId) {
        Date date = new Date();
        Query query;
        try {
            String insertCpUsers = "INSERT INTO cp_users(id, user_name, cp_id, status, type) "
                + "VALUES(cp_users_seq.nextval, :userName, :cpId, 1, 0)";
            query = entityManager.createNativeQuery(insertCpUsers);
            query.setParameter("userName", userName);
            query.setParameter("cpId", cpId);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return query.executeUpdate();
    }

    private void updateCpUserStatus(Long cpId, String userName) {
        Date date = new Date();
        String updateSlToken = "UPDATE sl_token SET wrong_code = 0 WHERE isdn = :userName";
        String updateCpUsers = "UPDATE cp_users SET status = 1 WHERE cp_id = :cpId AND user_name = :userName";
        try {
            Query querySlToken = entityManager.createNativeQuery(updateSlToken);
            querySlToken.setParameter("userName", userName);
            querySlToken.executeUpdate();

            Query queryCpUser = entityManager.createNativeQuery(updateCpUsers);
            queryCpUser.setParameter("cpId", cpId);
            queryCpUser.setParameter("userName", userName);
            queryCpUser.executeUpdate();
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    private Integer deleteCpGroupSubLimit(Long cpId) {
        Date date = new Date();
        int result;
        String sql = "DELETE FROM cp_group_sub_limit WHERE cp_id = :cpId";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("cpId", cpId);
            result = query.executeUpdate();
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
        return result;
    }

    private void insertCpGroupSubLimit(Long cpId, Long freeSms) {
        Date date = new Date();
        String insertSubLimit = "INSERT INTO cp_group_sub_limit(cp_id, msisdn, month_free_sms, reset_date, num_group_contain) "
            + "VALUES(?, ?, ?, SYSDATE, ?)";

        String selectGroupSub = "SELECT a.msisdn, COUNT(*) count "
            + "FROM cp_group_sub a "
            + "JOIN cp_group b ON a.cp_group_id = b.cp_group_id "
            + "WHERE a.cp_id = :cpId AND b.create_user IS NULL "
            + "GROUP BY a.msisdn";
        try {
            List<String> lstPrefixVt = getLstPrefixVt();
            Query querySelect = entityManager.createNativeQuery(selectGroupSub);
            querySelect.setParameter("cpId", cpId);
            List<CpGroupSubDTO> lstResult = new ArrayList<>();
            List<Object[]> lstObject = querySelect.getResultList();
            if (!DataUtil.isNullOrEmpty(lstObject)) {
                lstObject.stream().forEach(objects -> {
                    CpGroupSubDTO dto = new CpGroupSubDTO();
                    dto.setMsisdn(DataUtil.safeToString(objects[0]));
                    dto.setCount(DataUtil.safeToInt(objects[1]));
                    lstResult.add(dto);
                });
            }
            Session session = entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                int k = 0;
                int batchSize = 1000;
                try (PreparedStatement ps = connection.prepareStatement(insertSubLimit)) {
                    if (!DataUtil.isNullOrEmpty(lstResult)) {
                        for (CpGroupSubDTO sub : lstResult) {
                            k++;
                            if (isValidTelco(lstPrefixVt, sub.getMsisdn())) {
                                int index = 0;
                                ps.setLong(++index, cpId);
                                ps.setString(++index, sub.getMsisdn());
                                ps.setLong(++index, freeSms);
                                ps.setLong(++index, sub.getCount());
                                ps.addBatch();
                            }
                            if (k % batchSize == 0 || k == lstResult.size()) {
                                ps.executeBatch();
                                ps.clearBatch();
                            }
                        }
                    }
                } catch (Exception ex) {
                    throw ex;
                }
            });
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    private boolean isValidTelco(List<String> lstPrefix, String msisdn) {
        boolean check = false;
        for (String prefix : lstPrefix) {
            if (msisdn.startsWith(prefix)) {
                check = true;
                break;
            }
        }
        return check;
    }

    private List<String> getLstPrefixVt() {
        Date date = new Date();
        List<String> lstPrefixVt = new ArrayList<String>();
        String sql = "SELECT telco_prefix FROM telco WHERE telco_code='VT'";
        try {
            Query query = entityManager.createNativeQuery(sql);
            String prefix = query.getSingleResult().toString();
            String[] prefixArray = prefix.split(",");
            for (String st : prefixArray) {
                lstPrefixVt.add(st.trim());
            }
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
        }
        return lstPrefixVt;
    }

    private Integer deleteCpUsers(Long cpId, String userName) {
        Integer result;
        try {
            String deleteSql = "delete from cp_users c where c.user_name =:userName and c.cp_id =:cpId";
            Query query = entityManager.createNativeQuery(deleteSql);
            query.setParameter("userName", userName);
            query.setParameter("cpId", cpId);
            result = query.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

    public void sendEmailToUser(Cp cp) {
        try {
            String strMailContent = Constants.MAIL_FORMAT_CP_REG
                .replace("{CP_CODE}", DataUtil.removeHtmlTag(cp.getCpCode()))
                .replace("{CP_NAME}", DataUtil.removeHtmlTag(cp.getCpName()))
                .replace("{ADDRESS}", DataUtil.removeHtmlTag(cp.getAddress()))
                .replace("{STAFF}", DataUtil.removeHtmlTag(cp.getStaffCode() + " - " + cp.getStaffName()));
            String lstMailTo[] = {adminEmail, cp.getStaffEmail()};
            MailUtils mailUtils = new MailUtils();
            if (!this.TEST_SYSTEM) {
                mailUtils.send(lstMailTo, null, null, "[SMS Brandname] Thông báo KH đăng ký mới - Mã KH: " + cp.getCpCode(), strMailContent, null, null);
            }
        } catch (Exception ex) {
            rollbackData(cp.getCpId(), cp.getUserName(), true);
            throw ex;
        }
    }

    private void addOrDeleteCpSubGroupLimit(CpDTO newDTO, CpDTO oldDTO) {
        if (!newDTO.getAccountType().equals(oldDTO.getAccountType())) {
            if (newDTO.getAccountType().equals(0L)) { //acount basic
                deleteCpGroupSubLimit(oldDTO.getCpId());
            } else { //account limit
                //insert subs to cp_group_sub_limit
                insertCpGroupSubLimit(oldDTO.getCpId(), newDTO.getFreeSms());
                //inactive child cp_users
                inactiveSubCpUsers(oldDTO.getCpId());
            }
        }
    }

    private Integer inactiveSubCpUsers(Long cpId) {
        Date date = new Date();
        int result = -1;
        String sql = "UPDATE cp_users SET status = 0 WHERE cp_id = :cpId AND type = 1";
        try {
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("cpId", cpId);
            result = query.executeUpdate();
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
        return result;
    }

    private void updateRealmRoles(String userId, CpDTO dto) {
        try {
            keyCloakUserService.updateRealmLevelRoles(userId, dto);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void updateUserToKeyCloakServer(CpDTO cp) {
        Date date = new Date();
        try {
            String keyCloakUserId = keyCloakUserService.search(cp.getUserName()).size() > 0 ? keyCloakUserService.search(cp.getUserName()).get(0).getId() : null;
            if (!DataUtil.isNullOrEmpty(cp.getPassword()) && !DataUtil.isNullOrEmpty(keyCloakUserId)) {
                keyCloakUserService.resetPassword(keyCloakUserId, cp.getPassword());
            }
            changeKeyCloakUserStatusOrCpName(cp.getUserName(), cp.getStatus(), cp.getCpName());
            if (!DataUtil.isNullOrEmpty(keyCloakUserId)) {
                updateRealmRoles(keyCloakUserId, cp);
            }
        } catch (Exception ex) {
            rollbackData(cp.getCpId(), cp.getUserName(), false);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

    private void deleteCpAlias(Long cpId) {
        Date date = new Date();
        try {
            String queryDelCpAliasStr = "delete from cp_alias where cp_id = :cpId";
            Query queryDelCpAlias = entityManager.createNativeQuery(queryDelCpAliasStr);
            queryDelCpAlias.setParameter("cpId", cpId);
            queryDelCpAlias.executeUpdate();

            String selectUserName = "SELECT user_name FROM cp_users WHERE cp_id = :cpId";
            Query querySelectUserName = entityManager.createNativeQuery(selectUserName);
            querySelectUserName.setParameter("cpId", cpId);
            List<String> lstUserName = querySelectUserName.getResultList();
            if (!DataUtil.isNullOrEmpty(lstUserName)) {
                lstUserName.stream().forEach(userName -> {
                    String userId = !DataUtil.isNullOrEmpty(keyCloakUserService.search(userName)) ? keyCloakUserService.search(userName).get(0).getId() : null;
                    if (!DataUtil.isNullOrEmpty(userId)) keyCloakUserService.delete(userId);
                });
            }
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @Override
    public Optional<Cp> findFirstByUserNameAndStatus(String userName) {
        return cpRepository.findFirstByUserNameAndStatus(userName, 1L);
    }
}
