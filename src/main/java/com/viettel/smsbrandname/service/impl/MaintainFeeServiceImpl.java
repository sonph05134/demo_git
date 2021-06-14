package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.MaintainFee;
import com.viettel.smsbrandname.repository.MaintainFeeRepository;
import com.viettel.smsbrandname.service.MaintainFeeService;
import com.viettel.smsbrandname.service.dto.MaintainFeeDTO;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class MaintainFeeServiceImpl extends StandardizeLogging implements MaintainFeeService {

    private final Logger log = LoggerFactory.getLogger(MaintainFeeServiceImpl.class);

    @Autowired
    private MaintainFeeRepository maintainFeeRepository;

    public MaintainFeeServiceImpl() {
        super(MaintainFeeServiceImpl.class);
    }

    @Override
    public Page<MaintainFee> search(MaintainFeeDTO maintainFee) {
        int pageSize = maintainFee.getPageSize();
        int currentPage = maintainFee.getCurrentPage();
        Pageable page = PageRequest.of(currentPage, pageSize, Sort.by("feeValue").ascending());
        if (maintainFee.getCurrency() == -1) {
            return maintainFeeRepository.findAll(page);
        }
        return maintainFeeRepository.findAllByCurrency(page, maintainFee.getCurrency());
    }

    @Override
    public void save(MaintainFeeDTO maintainFeeDTO) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(maintainFeeDTO.getId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        DataUtil.trim(maintainFeeDTO);
        BigDecimal feeValue = new BigDecimal(maintainFeeDTO.getFeeValue());
        MaintainFee maintainFee = MaintainFee.convertFromDTO(maintainFeeDTO);

        if (Long.valueOf(maintainFeeDTO.getFeeValue()) < 0) {
            writeInfoLog(Constants.TRANSACTION_STATUS.FAIL, "errorfeeValueInvalid", date);
            throw new BusinessException(ErrorCodeResponse.FEE_VALUE_INVALID);
        }

        if (maintainFee.getCurrency() == Constants.CURRENCY_USD_VAL) {
            BigDecimal usdLength = new BigDecimal(Constants.USD_LENGTH);
            BigDecimal value = (new BigDecimal(maintainFeeDTO.getFeeValue())).multiply(usdLength);
            maintainFee.setFeeValue(value);
            feeValue = value;
        }
        if (maintainFeeRepository.maintainFeeExist(maintainFee.getId(), feeValue, maintainFee.getCurrency())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsData", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("maintain.fee"));
        }
        try {
            maintainFeeRepository.save(maintainFee);
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
    public void delete(Long id) {
        Date date = new Date();
        try {
            maintainFeeRepository.deleteById(id);
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
    }
}
