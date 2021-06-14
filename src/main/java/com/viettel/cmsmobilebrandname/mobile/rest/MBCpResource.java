package com.viettel.cmsmobilebrandname.mobile.rest;


//import com.viettel.cmsmobilebrandname.mobile.service.MBCpService;

import com.google.gson.Gson;
import com.viettel.cmsmobilebrandname.mobile.service.MBCpService;
import com.viettel.cmsmobilebrandname.mobile.service.impl.MBCpServiceImpl;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("api/cp/MB/")
public class MBCpResource extends StandardizeLogging {

    public MBCpResource() {
        super(MBCpResource.class);
    }


    @Autowired
    MBCpService mbCpService;

    @Autowired
    private ResponseFactory iResponseFactory;


    @PostMapping(value = "add",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> MBAdd(
         @RequestParam String cpJson,
         @RequestParam(value = "attachFile", required = false) MultipartFile attachFile,
         @RequestParam(value = "monthCommissionAttachFile", required = false) MultipartFile monthCommissionAttachFile){

        CpDTO cpDTO = new Gson().fromJson(cpJson, CpDTO.class);
        CpDTO checkWsUserName = !DataUtil.isNullOrEmpty(cpDTO.getWsUsername()) ? mbCpService.checkExistedValue(null, null, null, cpDTO.getWsUsername(), null) : null;
        if (!DataUtil.isNullObject(checkWsUserName)) {
            writeInfoLog("Loi khi thuc hien update Cp: Trung WsUsername");
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cp.WSUsername.response");
        }

        CpDTO checkUserName = !DataUtil.isNullOrEmpty(cpDTO.getUserName()) ? mbCpService.checkExistedValue(null, null, cpDTO.getUserName(), null, null) : null;
        if (!DataUtil.isNullObject(checkUserName)) {
            writeInfoLog("Loi khi them Cp: trung ten Cp");
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "userName.cp.failure");
        }

        CpDTO checkCpCode = !DataUtil.isNullOrEmpty(cpDTO.getCpCode()) ? mbCpService.checkExistedValue(cpDTO.getCpCode(), null, null, null, null) : null;
        if (!DataUtil.isNullObject(checkCpCode)) {
            writeInfoLog("Loi khi thuc hien them moi Cp: Trung Cp Code");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpCode", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "cpCode.cp.exist");
        }

        CpDTO checkCpName = !DataUtil.isNullOrEmpty(cpDTO.getCpName()) ? mbCpService.checkExistedValue(null, cpDTO.getCpName(), null, null, null) : null;

        if (!DataUtil.isNullObject(checkCpName)) {
            writeInfoLog("Loi khi them Cp: trung ten Cp");
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsCpName", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "name.cp.exist");
        }

        if(cpDTO.getWsPassword().matches(Constants.PASSWORD_PATTERN) == false){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "passApiNotStrength", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.FAILURE, "userName.cp.failure");
        }

        if(cpDTO.getPassword().matches(Constants.PASSWORD_PATTERN) == false){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "passNotStrength", new Date());
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.FAILURE, "userName.cp.failure");
        }

        mbCpService.add(cpDTO,attachFile,monthCommissionAttachFile);
            return  null;
    }

    @GetMapping(value = "check-existed-order-or-name")
    public ResponseEntity<Object> checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode) {
        Date date = new Date();
        Boolean isExisted = mbCpService.checkExistedOrderNoOrCpName(cpName, taxCode, isSaving, cpId, commissionPercentCode);
        if (isExisted) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "duplicateNameOrTax", date);
        }
        return iResponseFactory.success(isExisted, Boolean.class);
    }


}
