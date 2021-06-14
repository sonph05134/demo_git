package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.CfgSmscGate;
import com.viettel.smsbrandname.domain.Telco;
import com.viettel.smsbrandname.service.CfgSmscGateService;
import com.viettel.smsbrandname.service.TelcoService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/cfg-smsc-gate")
public class CfgSmscGateResource extends StandardizeLogging {

    @Autowired
    private CfgSmscGateService cfgSmscGateService;


    @Autowired
    private ResponseFactory iResponseFactory;

    public CfgSmscGateResource() {
        super(CfgSmscGateResource.class);
    }


    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody PageDTO page) {
        Date date = new Date();
        try {
            return iResponseFactory.success(cfgSmscGateService.getAll(page), Page.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody CfgSmscGate cfgSmscGate) {
        Date date = new Date();
        try {
            boolean isUpdate;
            if (DataUtil.isNullOrEmpty(cfgSmscGate.getCfgSmscGateId())) {
                isUpdate = false;
            } else {
                isUpdate = true;
            }
            cfgSmscGateService.save(cfgSmscGate);
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmsSplitDAO_onCreate_success", date);
            }
            else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmsSplitDAO_onUpdate_success", date);
            }
            return iResponseFactory.success(cfgSmscGate, Object.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
    }

    @GetMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        Date date = new Date();
        try {
            cfgSmscGateService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmsSplitDAO_onDelete_success", date);
            return iResponseFactory.success(id, Long.class);
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("changeStatus")
    public ResponseEntity<Object> active(@RequestBody CfgSmscGate cfgSmscGate, @RequestParam int status) {
        Date date = new Date();
        try {
            cfgSmscGateService.changeStatus(cfgSmscGate, status);
            if (status == 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmsSplitDAO_active_success", date);
            }
            if (status == 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmsSplitDAO_inactive_success", date);
            }
            return iResponseFactory.success(cfgSmscGate, CfgSmscGate.class);
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
