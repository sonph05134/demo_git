package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmscGate;
import com.viettel.smsbrandname.service.GatewayService;
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
@RequestMapping("api/cfg-gate")
public class CfgGateResource extends StandardizeLogging {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private ResponseFactory iResponseFactory;

    public CfgGateResource() {
        super(CfgGateResource.class);
    }

    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody PageDTO page) {
        Date date = new Date();
        try {
            return iResponseFactory.success(gatewayService.search(page), Page.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("create")
    public ResponseEntity<Object> save(@RequestBody CfgGate cfgGate) {
        Date date = new Date();

        try {
            gatewayService.save(cfgGate, false);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwGateWayCfgDAO_onCreate_success", date);
            return iResponseFactory.success(cfgGate, Object.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
    }

    @PostMapping("update")
    public ResponseEntity<Object> update(@RequestBody CfgGate cfgGate) {
        Date date = new Date();

        try {
            gatewayService.save(cfgGate, true);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwGateWayCfgDAO_onUpdate_success", date);
            return iResponseFactory.success(cfgGate, Object.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam String id) {
        Date date = new Date();
        try {
            gatewayService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "GwGateWayCfgDAO_onDelete_success", date);
            return iResponseFactory.success(id, String.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, new Date());
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            throw e;
        }
    }

    @PostMapping("changeStatus")
    public ResponseEntity<Object> active(@RequestBody CfgGate cfgGate, @RequestParam int status) {
        Date date = new Date();
        try {
            gatewayService.changeStatus(cfgGate, status);
            if (status == 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "inactive success", date);
            }
            if (status == 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "active success", date);
            }
            return iResponseFactory.success(cfgGate, CfgGate.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
