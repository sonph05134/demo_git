package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmsc;
import com.viettel.smsbrandname.service.GatewayService;
import com.viettel.smsbrandname.service.SMSCService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/cfg-smsc")
public class CfgSMSCResource extends StandardizeLogging {

    @Autowired
    private SMSCService smscService;

    @Autowired
    private ResponseFactory iResponseFactory;

    public CfgSMSCResource() {
        super(CfgSMSCResource.class);
    }

    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody PageDTO page) {
        Date date = new Date();
        try {
            return iResponseFactory.success(smscService.search(page), Page.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("create")
    public ResponseEntity<Object> save(@RequestBody CfgSmsc cfgSmsc) {
        Date date = new Date();
        try {
            smscService.save(cfgSmsc, false);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmscDAO_onCreate_success", date);
            return iResponseFactory.success(cfgSmsc, Object.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
    }

    @PostMapping("update")
    public ResponseEntity<Object> update(@RequestBody CfgSmsc cfgSmsc) {
        Date date = new Date();
        try {
            smscService.save(cfgSmsc, true);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmscDAO_onUpdate_success", date);
            return iResponseFactory.success(cfgSmsc, Object.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam String id) {
        Date date = new Date();
        try {
            smscService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmscDAO_onDelete_success", date);
            return iResponseFactory.success(id, String.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
    }

    @PostMapping("changeStatus")
    public ResponseEntity<Object> active(@RequestBody CfgSmsc cfgSmsc, @RequestParam short status) {
        Date date = new Date();
        try {
            smscService.changeStatus(cfgSmsc, status);
            if (status == 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmscDAO_active_success", date);
            }
            if (status == 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwCfgSmscDAO_inactive_success", date);
            }
            return iResponseFactory.success(cfgSmsc, CfgSmsc.class);
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
