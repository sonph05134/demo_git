package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.*;
import com.viettel.smsbrandname.domain.AdApproveHis;
import com.viettel.smsbrandname.domain.Prog;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.repository.AdApproveHisCustomRepository;
import com.viettel.smsbrandname.repository.ProgRepository;
import com.viettel.smsbrandname.repository.ProgRepositoryCustom;
import com.viettel.smsbrandname.repository.ProvinceUserRepository;
import com.viettel.smsbrandname.service.ProgService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsResultDTO;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsSearchDTO;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgAdvertisementSearchDTO;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgResultDTO;
import com.viettel.smsbrandname.service.dto.cpprog.ProgAdApproveHisDTO;
import com.viettel.smsbrandname.service.dto.response.CPProgAdvertisementResponseDTO;
import com.viettel.smsbrandname.service.dto.response.CareProgResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgServiceImpl extends StandardizeLogging implements ProgService {
    private static final String ENTITY_NAME = "prog";
    private final Logger log = LoggerFactory.getLogger(ProgServiceImpl.class);

    @Autowired
    private ProgRepository progRepository;
    @Autowired
    private AdApproveHisCustomRepository adApproveHisCustomRepository;

    @Autowired
    private ProvinceUserRepository provinceUserRepository;

    @Autowired
    ExcelReportUtils excelUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private ProgRepositoryCustom progRepositoryCustom;

    public ProgServiceImpl() {
        super(ProgServiceImpl.class);
    }

    @Override
    public List<ComboBean> getListCheckBox() {
        List<ComboBean> lst = progRepository.getListCheckBox();
        return lst;
    }

    @Override
    public Page<CareCustomerSmsSearchDTO> search(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO) {
        Long provinceid = -1L;
        Optional<ProvinceUsers> optional = provinceUserRepository.getByUserName(careCustomerSmsSearchDTO.getUserName());
        if (optional.isPresent()) {
            provinceid = optional.get().getProvinceId();
        }
        return progRepository.doSearch(careCustomerSmsSearchDTO, provinceid, false);
    }

    @Override
    public CPProgAdvertisementResponseDTO searchAdvertisement(CPProgAdvertisementSearchDTO cpProgAdvertisementSearchDTO) {
        Long provinceid = -1L;
        Optional<ProvinceUsers> optional = provinceUserRepository.getByUserName(cpProgAdvertisementSearchDTO.getUserName());
        if (optional.isPresent()) {
            provinceid = optional.get().getProvinceId();
        }
        return progRepository.searchAdvertisement(cpProgAdvertisementSearchDTO, provinceid);
    }

    private String getUserName(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getLogin();
        }
        return null;
    }

    @Override
    public ByteArrayInputStream export(CPProgAdvertisementSearchDTO cpProgAdvertisementSearchDTO, Principal principal) {
        try {
            Long provinceid = -1L;
            Optional<ProvinceUsers> optional = provinceUserRepository.getByUserName(cpProgAdvertisementSearchDTO.getUserName());
            if (optional.isPresent()) {
                provinceid = optional.get().getProvinceId();
            }
            CPProgAdvertisementResponseDTO lst = progRepository.searchAdvertisement(cpProgAdvertisementSearchDTO, provinceid);
            if (lst.getListData().size() > 0) {

                List<CPProgResultDTO> lstResult = lst.getListData().stream().map(e -> {
                    CPProgResultDTO cPProgResultDTO = (CPProgResultDTO) e;
                    String status = "";
                    String tested = "";
                    String isAdminUpload = "";
                    switch (cPProgResultDTO.getStatus()) {
                        case "0":
                            status = Translator.toLocale("advertisement.0");
                            break;
                        case "1":
                            status = Translator.toLocale("advertisement.1");
                            break;
                        case "2":
                            status = Translator.toLocale("advertisement.2");
                            break;
                        case "3":
                            status = Translator.toLocale("advertisement.3");
                            break;
                        case "4":
                            status = Translator.toLocale("advertisement.4");
                            break;
                        case "5":
                            status = Translator.toLocale("advertisement.5");
                            break;
                        case "6":
                            status = Translator.toLocale("advertisement.6");
                            break;
                        case "7":
                            status = Translator.toLocale("advertisement.7");
                            break;
                        case "8":
                            status = Translator.toLocale("advertisement.8");
                            break;
                        case "9":
                            status = Translator.toLocale("advertisement.9");
                            break;
                    }
                    cPProgResultDTO.setStatus(status);

                    if (cPProgResultDTO.getTested().equals("0")) {
                        tested = Translator.toLocale("advertisement.tested.0");
                    } else if (cPProgResultDTO.getTested().equals("1")) {
                        tested = Translator.toLocale("advertisement.tested.1");
                    }


                    cPProgResultDTO.setTested(tested);
                    if (cPProgResultDTO.getType().equals("3")) {
                        if (cPProgResultDTO.getIsAdminUpdoad().equals("1")) {
                            isAdminUpload = Translator.toLocale("advertisement.isAdmin.1");
                        } else {
                            isAdminUpload = Translator.toLocale("advertisement.isAdmin.2");
                        }
                    } else {
                        isAdminUpload = Translator.toLocale("advertisement.isAdmin.0");

                    }

                    cPProgResultDTO.setIsAdminUpdoad(isAdminUpload);


                    return cPProgResultDTO;
                }).collect(Collectors.toList());

                List<String> lstHeader = Arrays.asList(
                    Translator.toLocale("advertisement.catId"),
                    Translator.toLocale("advertisement.code"),
                    Translator.toLocale("advertisement.brandname"),
                    Translator.toLocale("advertisement.content"),
                    Translator.toLocale("advertisement.status"),
                    Translator.toLocale("advertisement.admin"),
                    Translator.toLocale("advertisement.createdDate"),
                    Translator.toLocale("advertisement.startDate"),
                    Translator.toLocale("advertisement.finishDate"),
                    Translator.toLocale("advertisement.upload"));
                List<String> lstColumn = Arrays.asList(
                    "catName",
                    "code",
                    "allas",
                    "content",
                    "status",
                    "tested",
                    "createDate",
                    "startDate",
                    "finishDate",
                    "isAdminUpdoad");
                String title = Translator.toLocale("advertisement.report.title");
                List<Integer> lstSize = new ArrayList<>();
                lstSize.addAll(Arrays.asList(
                    ExcelUtils.WIDTH * 10,
                    ExcelUtils.WIDTH * 30,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 100,
                    ExcelUtils.WIDTH * 10,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20));
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
                    ExcelUtils.ALIGN_LEFT,
                    ExcelUtils.ALIGN_LEFT));
                return excelUtils.export(lstResult, lstHeader, lstColumn, title, lstSize, lstAlign);
            } else {
                throw new BusinessException(ErrorCodeResponse.EXPORT_NOT_EXISTED, "");

            }

        } catch (Exception e) {
            throw new BusinessException(ErrorCodeResponse.EXPORT_NOT_EXISTED, "");
        }

    }

    @Override
    public CareCustomerSmsSearchDTO getCareCustomerSmsSearchDTOById(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO) {

        return progRepository.getCareCustomerSmsSearchDTOById(careCustomerSmsSearchDTO);
    }

    @Override
    public void updateSMSgAdvertisement(List<ProgAdApproveHisDTO> progAdApproveHisDTO, boolean isRefuse) throws Exception {
        Date date = new Date();
        try {
            progAdApproveHisDTO.stream().forEach(item -> {
                if (item.getProgId() == null || item.getUser() == null
                    || item.getStatusOld() == null) {
                    throw new BadRequestAlertException(Translator.toLocale("advertisement.update.error"), ENTITY_NAME, "idexists");
                }
                String status = "";
                if (isRefuse) {
                    if (item.getNeedApproveRun().equals("1")) {
                        status = "2";
                    } else if (item.getNeedApproveRun().equals("0")) {
                        status = "6";
                    }
                } else if (!isRefuse) {
                    status = "0";
                }
                Prog prog = progRepositoryCustom.getByProgId(item.getProgId());
                if (prog == null) {
                    throw new BadRequestAlertException(Translator.toLocale("advertisement.update.notExit"), ENTITY_NAME, "idexists");
                }
                prog.setStatus(status);
                prog.setHidePrefix(item.getHidePrefix());
                prog.setHideSuffix(item.getHideSuffix());
                Prog updateProg = progRepositoryCustom.save(prog);
                if (updateProg == null) {
                    throw new BadRequestAlertException(Translator.toLocale("advertisement.update.error"), ENTITY_NAME, "idexists");
                }
                AdApproveHis adApproveHis = new AdApproveHis();
                adApproveHis.setNote(item.getNote());
                adApproveHis.setProgId(item.getProgId());
                adApproveHis.setStateAfter(Integer.parseInt(status));
                adApproveHis.setStateBefore(Integer.parseInt(item.getStatusOld()));
                Instant today = new Date().toInstant();
                adApproveHis.setTimeChange(today);
                adApproveHis.setApprover(item.getUser());
                AdApproveHis saveAdApproveHis = adApproveHisCustomRepository.save(adApproveHis);
                if (saveAdApproveHis == null) {
                    throw new BadRequestAlertException(Translator.toLocale("advertisement.update.error"), ENTITY_NAME, "idexists");
                }
            });
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, isRefuse ? "approveOk" : "denyOk", date);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @Override
    public Prog updateProg(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO, boolean isRefuse) {
        Prog prog = null;
        Date sysDate = new Date();
        if (careCustomerSmsSearchDTO.getProgId() != null) {
            prog = progRepositoryCustom.getByProgId(careCustomerSmsSearchDTO.getProgId());
        }
        if (isRefuse) {
            if (prog != null) {
                prog.setStatus("4");
                prog = progRepositoryCustom.save(prog);
            }
        } else {
            if (prog != null) {
                prog.setStatus("1");
                prog = progRepositoryCustom.save(prog);
            }
        }
        return prog;

    }

    @Override
    public ByteArrayInputStream exportListCareCustmoerSms(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");
        Long provinceid = -1L;
        Optional<ProvinceUsers> optional = provinceUserRepository.getByUserName(careCustomerSmsSearchDTO.getUserName());
        if (optional.isPresent()) {
            provinceid = optional.get().getProvinceId();
        }
        try {
            Page<CareCustomerSmsSearchDTO> lst = progRepository.doSearch(careCustomerSmsSearchDTO, provinceid, false);
            List<CareCustomerSmsSearchDTO> list = lst.getContent();
            if (list.size() > 0) {
                List<CareCustomerSmsResultDTO> lstResult = list.stream().map(e -> {
                    CareCustomerSmsResultDTO careCustomerSmsResultDTO = new CareCustomerSmsResultDTO();
                    careCustomerSmsResultDTO.setCpCode(e.getCpCode());
                    careCustomerSmsResultDTO.setCpName(e.getCpName());
                    careCustomerSmsResultDTO.setProgCode(e.getProgCode());
                    careCustomerSmsResultDTO.setAlias(e.getAlias());
                    careCustomerSmsResultDTO.setContent(e.getContent());
                    String status = "";
                    String messageType = "";
                    switch (e.getStatusIT()) {
                        case "0":
                            status = Translator.toLocale("careCustomerSms.0");
                            break;
                        case "1":
                            status = Translator.toLocale("careCustomerSms.1");
                            break;
                        case "2":
                            status = Translator.toLocale("careCustomerSms.2");
                            break;
                        case "3":
                            status = Translator.toLocale("careCustomerSms.3");
                            break;
                        case "4":
                            status = Translator.toLocale("careCustomerSms.4");
                            break;
                        case "5":
                            status = Translator.toLocale("careCustomerSms.5");
                            break;

                    }
                    careCustomerSmsResultDTO.setStatusIT(status);
                    if (!DataUtil.isNullOrEmpty(e.getType())) {
                        System.out.println(e.getType());
                        switch (e.getType()) {
                            case "0":
                                messageType = Translator.toLocale("careCustomerSms.type.0");
                                break;
                            case "1":
                                messageType = Translator.toLocale("careCustomerSms.type.1");
                                break;
                            case "2":
                                messageType = Translator.toLocale("careCustomerSms.type.2");
                                break;
                            case "3":
                                messageType = Translator.toLocale("careCustomerSms.type.3");
                                break;
                            default:
                                messageType = Translator.toLocale("careCustomerSms.type.na");

                        }
                    } else {
                        messageType = Translator.toLocale("careCustomerSms.type.na");

                    }

                    careCustomerSmsResultDTO.setType(messageType);
                    if (e.getCreateDate() != null) {
                        careCustomerSmsResultDTO.setCreateDate(format.format(e.getCreateDate()));
                    }
                    if (e.getSentSchedule() != null) {
                        careCustomerSmsResultDTO.setSentSchedule(format.format(e.getSentSchedule()));
                    }
                    return careCustomerSmsResultDTO;
                }).collect(Collectors.toList());

                List<String> lstHeader = Arrays.asList(
                    Translator.toLocale("careCustomerSms.cpName"),
                    Translator.toLocale("careCustomerSms.cpCode"),
                    Translator.toLocale("careCustomerSms.progCode"),
                    Translator.toLocale("careCustomerSms.alias"),
                    Translator.toLocale("careCustomerSms.progType"),
                    Translator.toLocale("careCustomerSms.content"),
                    Translator.toLocale("careCustomerSms.statusIT"),
                    Translator.toLocale("careCustomerSms.createDate"),
                    Translator.toLocale("careCustomerSms.sentSchedule"));
                List<String> lstColumn = Arrays.asList(
                    "cpName",
                    "cpCode",
                    "progCode",
                    "alias",
                    "type",
                    "content",
                    "statusIT",
                    "createDate",
                    "sentSchedule");
                String title = Translator.toLocale("careCustomerSms.report.title");
                List<Integer> lstSize = new ArrayList<>();
                lstSize.addAll(Arrays.asList(
                    ExcelUtils.WIDTH * 30,
                    ExcelUtils.WIDTH * 30,
                    ExcelUtils.WIDTH * 40,
                    ExcelUtils.WIDTH * 40,
                    ExcelUtils.WIDTH * 40,
                    ExcelUtils.WIDTH * 90,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20,
                    ExcelUtils.WIDTH * 20));
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
                    ExcelUtils.ALIGN_LEFT));
                return excelUtils.export(lstResult, lstHeader, lstColumn, title, lstSize, lstAlign);
            } else {
                throw new BusinessException(ErrorCodeResponse.EXPORT_NOT_EXISTED, "");

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodeResponse.EXPORT_NOT_EXISTED, "");
        }
    }

    @Override
    public byte[] geTattachFile(Long progId) throws Exception {
        byte[] bytes = progRepositoryCustom.getByProgId(progId).getAttachFile();
//        byte [] bytes = new byte[2048];
//        DataInputStream in = new DataInputStream(myblob.getBinaryStream());


        return bytes;
    }


}
