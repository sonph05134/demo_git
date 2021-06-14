package com.viettel.smsbrandname.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.service.BccsService;
import com.viettel.smsbrandname.service.CpService;
import com.viettel.smsbrandname.service.VirtualAccountService;
import com.viettel.smsbrandname.service.dto.*;
import com.viettel.smsbrandname.service.mapper.CpMapper;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/cp")
public class CpResource extends StandardizeLogging {

    @Autowired
    private ResponseFactory iResponseFactory;

    private final CpService cpService;

    private final CpMapper cpMapper;

    private final BccsService bccsService;

    public CpResource(@Qualifier("cpServiceImpl") CpService cpService, CpMapper cpMapper, BccsService bccsService) {
        super(CpResource.class);
        this.cpService = cpService;
        this.cpMapper = cpMapper;
        this.bccsService = bccsService;
    }

    @Autowired
    private VirtualAccountService virtualAccountService;

    /**
     * @param searchDTO client's search parameters
     * @return 1 page data and count of data in DB
     * @author quyentv
     */
    @GetMapping("search")
    public ResponseEntity<Object> search(CpSearchDTO searchDTO) {
        Date date = new Date();
        CommonResponseDTO responseDTO;
        try {
            responseDTO = cpService.search(searchDTO);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return iResponseFactory.success(responseDTO, CommonResponseDTO.class);
    }

    @GetMapping("all-province")
    public ResponseEntity<List<ProvinceDTO>> findAllProvince() {
        List<ProvinceDTO> list = cpService.findAllProvince();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("all-percent-code")
    public ResponseEntity<List<CommissionPercentCodeDTO>> findAllCommissionPercentCode() {
        List<CommissionPercentCodeDTO> list = cpService.getAllCommissionPercentCode();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("find-district-by-province/{id}")
    public ResponseEntity<List<DistrictDTO>> findAllProvince(@PathVariable Long id) {
        List<DistrictDTO> list = cpService.findDistrictByProvinceId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Object> add(
        @RequestParam String cpJson,
        @RequestParam(value = "attachFile", required = false) MultipartFile attachFile,
        @RequestParam(value = "monthCommissionAttachFile", required = false) MultipartFile monthCommissionAttachFile) {
        Date date = new Date();
        CpDTO cp = new Gson().fromJson(cpJson, CpDTO.class);
        CpDTO checkCpCode = !DataUtil.isNullOrEmpty(cp.getCpCode()) ? cpService.checkExistedValue(cp.getCpCode(), null, null, null, null) : null;
        CpDTO checkCpName = !DataUtil.isNullOrEmpty(cp.getCpName()) ? cpService.checkExistedValue(null, cp.getCpName(), null, null, null) : null;
        CpDTO checkUserName = !DataUtil.isNullOrEmpty(cp.getUserName()) ? cpService.checkExistedValue(null, null, cp.getUserName(), null, null) : null;
        CpDTO checkWsUserName = !DataUtil.isNullOrEmpty(cp.getWsUsername()) ? cpService.checkExistedValue(null, null, null, cp.getWsUsername(), null) : null;
        CpDTO checkCpSysId = !DataUtil.isNullOrEmpty(cp.getCpSysid()) ? cpService.checkExistedValue(null, null, null, null, cp.getCpSysid()) : null;
        if (!DataUtil.isNullObject(checkCpCode)) {
            writeInfoLog("Loi khi thuc hien them moi Cp: Trung Cp Code");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpCode", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpCode");
        }
        if (!DataUtil.isNullObject(checkCpName) && !DataUtil.isNullOrEmpty(cp.getConfirmExistedCp()) && !cp.getConfirmExistedCp()) {
            writeInfoLog("Loi khi them Cp: trung ten Cp");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpName", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpName");
        }
        if (!DataUtil.isNullObject(checkUserName)) {
            writeInfoLog("Loi khi them Cp: trung ten Cp");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "changeFalse", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "userName");
        }
        if (!DataUtil.isNullObject(checkWsUserName)) {
            writeInfoLog("Loi khi thuc hien update Cp: Trung WsUsername");
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "wsUsername");
        }
        if (!DataUtil.isNullObject(checkCpSysId)) {
            writeInfoLog("Loi khi them Cp: trung Cp Sysid");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpsysid", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpSysid");
        }
        Cp entity = new Cp();
        try {
            entity = cpService.add(cp, attachFile, monthCommissionAttachFile);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
        return iResponseFactory.success(entity, Cp.class);
    }

    @GetMapping(value = "getCPPre", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCP(@RequestParam String currency, @RequestParam Long status, Principal principal) {
        return iResponseFactory.success(virtualAccountService.getCPPre(currency, status, principal), ComboBean.class);
    }

    @PostMapping(value = "getCpByCondition")
    public ResponseEntity<Object> getCpByCondition(@RequestBody VirtualAccountDTO virtualAccount, Principal principal) {
        Page<VirtualAccountDTO> page = virtualAccountService.getBalanceHisByCondition(virtualAccount, principal);
        return iResponseFactory.success(page, Page.class);
    }

    @PostMapping(value = "downloadAttach")
    public ResponseEntity<?> downloadCpAttach(@RequestParam String fileName) throws IOException {
        return ResponseEntity.ok().body(new InputStreamResource(virtualAccountService.downloadCPAttachFile(fileName)));
    }

    @PostMapping(value = "exportVitrualAccount")
    public ResponseEntity<?> exportVirtualAccountCP(@RequestBody VirtualAccountDTO virtualAccount, Principal principal) {
        return ResponseEntity.ok().body(new InputStreamResource(virtualAccountService.export(virtualAccount, principal)));
    }

    @PostMapping(value = "saveCPVirtual")
    public ResponseEntity<?> saveCPVirtual(@RequestParam MultipartFile file, @RequestParam String currency,
                                           @RequestParam Long cpId, @RequestParam Integer balanceType, @RequestParam BigDecimal amount,
                                           @RequestParam BigDecimal rate, Principal principal) {
        VirtualAccountDTO virtual = new VirtualAccountDTO();
        virtual.setFile(file);
        virtual.setCurrency(currency);
        virtual.setCpId(cpId);
        virtual.setBalanceType(balanceType);
        virtual.setAmount(amount);
        virtual.setRate(rate);
        virtualAccountService.refund(principal, virtual);
        return iResponseFactory.success(VirtualAccountDTO.class);
    }

    @GetMapping(value = "getCPById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCPById(@RequestParam Long cpId) {
        return iResponseFactory.success(virtualAccountService.getCPById(cpId), Cp.class);
    }

    @PostMapping(value = "updateTransCPVirtual")
    public ResponseEntity<?> updateTransCPVirtual(@RequestBody VirtualAccountDTO virtual, Principal principal) {
        virtualAccountService.updateTrans(virtual, principal);
        return iResponseFactory.success(VirtualAccountDTO.class);
    }

    @PostMapping(value = "retryTrans")
    public ResponseEntity<?> retryTrans(@RequestBody VirtualAccountDTO virtual) {
        virtualAccountService.retryTrans(virtual);
        return iResponseFactory.success(VirtualAccountDTO.class);
    }

    @GetMapping("find-cp-by-user")
    public ResponseEntity<List<CpDTO>> findCpByUserLogin() {
        List<CpDTO> list = cpService.findCpByUserLogin();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("auto-complete")
    public ResponseEntity<List<ComboBean>> findByInput() {
        List<ComboBean> list = cpService.findByInput();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "find-by-id/{id}")
    public ResponseEntity<Object> findCpById(@PathVariable Long id) {
        return iResponseFactory.success(cpService.findCpById(id), CpDTO.class);
    }

    @PutMapping(value = "update")
    public ResponseEntity<Object> update(
        @RequestParam String cpJson,
        @RequestParam(value = "attachFile", required = false) MultipartFile attachFile,
        @RequestParam(value = "monthCommissionAttachFile", required = false) MultipartFile monthCommissionAttachFile,
        Principal principal) {
        Date date = new Date();
        CpDTO cp = new Gson().fromJson(cpJson, CpDTO.class);
        CpDTO checkCpCode = !DataUtil.isNullOrEmpty(cp.getCpCode()) ? cpService.checkExistedValue(cp.getCpCode(), null, null, null, null) : null;
        CpDTO checkCpName = !DataUtil.isNullOrEmpty(cp.getCpName()) ? cpService.checkExistedValue(null, cp.getCpName(), null, null, null) : null;
        CpDTO checkUserName = !DataUtil.isNullOrEmpty(cp.getUserName()) ? cpService.checkExistedValue(null, null, cp.getUserName(), null, null) : null;
        CpDTO checkWsUserName = !DataUtil.isNullOrEmpty(cp.getWsUsername()) ? cpService.checkExistedValue(null, null, null, cp.getWsUsername(), null) : null;
        CpDTO checkCpSysId = !DataUtil.isNullOrEmpty(cp.getCpSysid()) ? cpService.checkExistedValue(null, null, null, null, cp.getCpSysid()) : null;
        if (!DataUtil.isNullObject(checkCpCode) && !checkCpCode.getCpId().equals(cp.getCpId())) {
            writeInfoLog("Loi khi thuc hien update Cp: Trung Cp Code");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpCode", date);
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpCode");
        }
        if (!DataUtil.isNullObject(checkCpName) && !checkCpName.getCpId().equals(cp.getCpId()) && !DataUtil.isNullOrEmpty(cp.getConfirmExistedCp()) && !cp.getConfirmExistedCp()) {
            writeInfoLog("Loi khi thuc hien update Cp: Trung Ten Cp");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpName", date);
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpName");
        }
        if (!DataUtil.isNullObject(checkUserName) && !checkUserName.getCpId().equals(cp.getCpId())) {
            writeInfoLog("Loi khi thuc hien update Cp: Khac Username");
            //kiem tra user co ton tai tren keycloak hay ko
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "userName");
        }
        if (!DataUtil.isNullObject(checkWsUserName) && !checkWsUserName.getCpId().equals(cp.getCpId())) {
            writeInfoLog("Loi khi thuc hien update Cp: Trung WsUsername");
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "wsUsername");
        }
        if (!DataUtil.isNullObject(checkCpSysId) && !checkCpSysId.getCpId().equals(cp.getCpId())) {
            writeInfoLog("Loi khi thuc hien update Cp: Trung Cp Sysid");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpsysid", date);
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpSysid");
        }
        Cp entity = new Cp();
        try {
            entity = cpService.update(cp, attachFile, monthCommissionAttachFile, principal);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, new Date());
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
        return iResponseFactory.success(entity, Cp.class);
    }

    @GetMapping(value = "search-province-user")
    public ResponseEntity<Object> searchProvinceUser(ProvinceUserSearchDTO searchDTO) {
        return iResponseFactory.success(cpService.searchProvinceUser(searchDTO), CommonResponseDTO.class);
    }

    @GetMapping(value = "search-staff")
    public ResponseEntity<Object> searchStaff(StaffSearchDTO searchDTO) {
        return iResponseFactory.success(cpService.searchStaff(searchDTO), CommonResponseDTO.class);
    }

    @GetMapping(value = "search-adjust-commission-his/{id}")
    public ResponseEntity<Object> searchAdjustCommissionHis(@PathVariable Long id) {
        return iResponseFactory.success(cpService.searchAdjustCommissionHis(id), AdjustCommissionHisSearchDTO.class);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Object> deleteCpById(@PathVariable Long id) {
        try {
            cpService.deleteCp(id, true);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) ex).getErrorCode(),ex.getMessage());
            }
        }
        return iResponseFactory.success(null);
    }

    @PostMapping(value = "downloadCpFiles")
    public ResponseEntity<?> downloadCpFiles(@RequestParam String fileName) throws IOException {
        Date date = new Date();
        ByteArrayInputStream attachFile;
        try {
            attachFile = cpService.downloadAttachFile(fileName);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return ResponseEntity.ok().body(new InputStreamResource(attachFile));
    }

    @GetMapping(value = "check-existed-order-or-name")
    public ResponseEntity<Object> checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode) {
        Date date = new Date();
        Boolean isExisted = cpService.checkExistedOrderNoOrCpName(cpName, taxCode, isSaving, cpId, commissionPercentCode);
        if (isExisted) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "duplicateNameOrTax", date);
        }
        return iResponseFactory.success(isExisted, Boolean.class);
    }

    @GetMapping(value = "find-bccs-projects")
    public ResponseEntity<Object> findBccsProjects() {
        return iResponseFactory.success(bccsService.getLstProject(), ComboBean.class);
    }

    @PostMapping(value = "exportCp")
    public ResponseEntity<?> exportVirtualAccountCP(@RequestBody CpSearchDTO searchDTO) {
        ByteArrayInputStream reportStream;
        try {
            reportStream = cpService.export(searchDTO);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, new Date());
            throw ex;
        }
        return ResponseEntity.ok().body(new InputStreamResource(reportStream));
    }

    @GetMapping(value = "get-login-permission")
    public ResponseEntity<Object> getLoginPermission(String username) {
        return iResponseFactory.success(cpService.getLoginPermission(username), ProvinceUsersDTO.class);
    }
}
