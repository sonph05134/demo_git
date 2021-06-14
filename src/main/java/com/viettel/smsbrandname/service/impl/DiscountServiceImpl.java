package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Discount;
import com.viettel.smsbrandname.domain.MaintainFee;
import com.viettel.smsbrandname.repository.DiscountRepository;
import com.viettel.smsbrandname.service.Discountervice;
import com.viettel.smsbrandname.service.dto.DiscountDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class DiscountServiceImpl extends StandardizeLogging implements Discountervice {

    @Autowired
    private DiscountRepository discountRepository;

    public DiscountServiceImpl() {
        super(DiscountServiceImpl.class);
    }

    @Override
    public Page<Discount> search(DiscountDTO discount) {
        int pageSize = discount.getPageSize();
        int currentPage = discount.getCurrentPage();
        Pageable page = PageRequest.of(currentPage, pageSize, Sort.by("discountValue").ascending());
        if (discount.getType() == -1) {
            return discountRepository.findAll(page);
        }
        return discountRepository.findAllByType(page, discount.getType());
    }

    @Override
    public void save(Discount discount) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(discount.getId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        DataUtil.trim(discount);
        if (discountRepository.discountExist(discount)) {
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("discount.value"));
        }

        try {
            discountRepository.save(discount);
        } catch (Exception e) {
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "updateFail", date);
            }
            throw new BusinessException(ErrorCodeResponse.SAVE_FAIL);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Date date = new Date();
        try {
            discountRepository.deleteById(id);
            discountRepository.deleteDiscountProvince(id);
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
    }
}
