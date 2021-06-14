package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.service.SmsCostService;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/sms-cost")
public class SmsCostResource extends StandardizeLogging {

    @Autowired
    private ResponseFactory iResponseFactory;

    @Autowired
    private SmsCostService smsCostService;

    public SmsCostResource() {
        super(SmsCostResource.class);
    }

    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody SMSCostDTO smsCostDTO) {
        Date date = new Date();
        try {
            return iResponseFactory.success(smsCostService.getByCondition(smsCostDTO), Page.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody SMSCost smsCost) {
        Date date = new Date();
        try {
            smsCostService.save(smsCost);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertOk", date);
            return iResponseFactory.success(smsCost, Object.class);
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        Date date = new Date();
        try {
            smsCostService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
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
}
