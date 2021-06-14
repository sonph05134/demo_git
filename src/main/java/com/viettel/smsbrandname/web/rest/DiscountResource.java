package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.Discount;
import com.viettel.smsbrandname.service.Discountervice;
import com.viettel.smsbrandname.service.MaintainFeeService;
import com.viettel.smsbrandname.service.dto.DiscountDTO;
import com.viettel.smsbrandname.service.dto.MaintainFeeDTO;
import com.viettel.smsbrandname.service.impl.DiscountServiceImpl;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/discount")
public class DiscountResource extends StandardizeLogging {

    @Autowired
    private ResponseFactory iResponseFactory;

    @Autowired
    private Discountervice discountervice;

    public DiscountResource() {
        super(DiscountResource.class);
    }

    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody DiscountDTO discount) {
        Date date = new Date();
        try {
            return iResponseFactory.success(discountervice.search(discount), Page.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody Discount discount) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(discount.getId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        try {
            discountervice.save(discount);
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertOk", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "updateOk", date);
            }
            return iResponseFactory.success(discount, Discount.class);
        } catch (Exception e) {
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
            discountervice.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
            return iResponseFactory.success(id, Long.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
