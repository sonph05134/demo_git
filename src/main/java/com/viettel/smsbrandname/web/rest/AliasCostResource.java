package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.service.AliasCostService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/management/alias-cost")
public class AliasCostResource extends StandardizeLogging {

    private final AliasCostService aliasCostService;
    private final ResponseFactory responseFactory;

    public AliasCostResource(AliasCostService aliasCostService,
                           ResponseFactory responseFactory) {
        super(AliasCostResource.class);
        this.aliasCostService = aliasCostService;
        this.responseFactory = responseFactory;
    }

    @GetMapping("search")
    public ResponseEntity<Object> onSearch(@RequestParam Integer aliasType,
                                           @RequestParam String telco,
                                           @RequestParam Integer start,
                                           @RequestParam Integer limit) {
        Date date = new Date();
        try {
            return responseFactory.success(aliasCostService.onSearch(telco, aliasType, limit, start), ApiResponseDTO.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save-edit")
    public ResponseEntity<Object> onSaveOrEdit(@RequestBody AliasCost aliasCost) {
        Date date = new Date();
        try {
            boolean isUpdate;
            if (DataUtil.isNullOrEmpty(aliasCost.getAliasCostId())) {
                isUpdate = false;
            } else {
                isUpdate = true;
            }
            aliasCostService.saveOrEdit(aliasCost);
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertOk", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "updateOk", date);
            }
            return responseFactory.success(null);
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @DeleteMapping("delete/{aliasCostId}")
    public ResponseEntity<Object> onDelete(@PathVariable Long aliasCostId) {
        Date date = new Date();
        try {
            aliasCostService.delete(aliasCostId);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
            return responseFactory.success(null);
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
