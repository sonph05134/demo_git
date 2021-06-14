package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.domain.PriceLevel;
import com.viettel.smsbrandname.service.AliasCostService;
import com.viettel.smsbrandname.service.PriceLevelService;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/management/price-level")
public class PriceLevelResource extends StandardizeLogging {

    private final PriceLevelService priceLevelService;
    private final ResponseFactory responseFactory;

    public PriceLevelResource(PriceLevelService priceLevelService,
                              ResponseFactory responseFactory) {
        super(PriceLevelResource.class);
        this.priceLevelService = priceLevelService;
        this.responseFactory = responseFactory;
    }

    @GetMapping("search")
    public ResponseEntity<Object> onSearch(@RequestParam Long numberSms,
                                           @RequestParam Integer start,
                                           @RequestParam Integer limit) {
        Date date = new Date();
        try {
            return responseFactory.success(priceLevelService.onSearch(numberSms, start, limit), ApiResponseDTO.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save-edit")
    public ResponseEntity<Object> onSaveOrEdit(@RequestBody PriceLevel priceLevel) {
        Date date = new Date();
        try {
            boolean isUpdate;
            if (DataUtil.isNullOrEmpty(priceLevel.getPriceLevelId())) {
                isUpdate = false;
            } else {
                isUpdate = true;
            }
            priceLevelService.saveOrEdit(priceLevel);
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
            priceLevelService.delete(aliasCostId);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteOk", date);
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
