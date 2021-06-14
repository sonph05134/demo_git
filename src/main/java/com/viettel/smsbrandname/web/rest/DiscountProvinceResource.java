package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.service.DiscountProvinceService;
import com.viettel.smsbrandname.service.dto.DiscountProvinceActionDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/discount-province")
public class DiscountProvinceResource extends StandardizeLogging {

    @Autowired
    DiscountProvinceService discountProvinceService;

    @Autowired
    ResponseFactory responseFactory;

    public DiscountProvinceResource() {
        super(DiscountProvinceResource.class);
    }

    @GetMapping("discount")
    public ResponseEntity<Object> getAllDiscount(@RequestParam Integer type) {
        return responseFactory.success(discountProvinceService.getDiscountByType(type), ComboboxResponseDTO.class);
    }

    @GetMapping("province")
    public ResponseEntity<Object> getProvince() {
        Date date = new Date();
        try {
            return responseFactory.success(discountProvinceService.getProvince(), ComboboxResponseDTO.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("province-by-discount/{discountId}")
    public ResponseEntity<Object> getProvinceByDiscount(@PathVariable Long discountId) {
        return responseFactory.success(discountProvinceService.getProvinceByDiscount(discountId), ComboboxResponseDTO.class);
    }

    @PostMapping("save-action")
    public ResponseEntity<Object> saveAction(@RequestBody DiscountProvinceActionDTO discountProvinceAction) {
        Date date = new Date();
        try {
            discountProvinceService.saveAction(discountProvinceAction);
            return responseFactory.success(null);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new BusinessException(ErrorCodeResponse.DELETE_SAVE_DISCOUNT_PROVINCE_FAIL);
        }
    }

}
