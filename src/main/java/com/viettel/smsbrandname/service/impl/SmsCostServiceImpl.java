package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.repository.SmsCostRepository;
import com.viettel.smsbrandname.service.SmsCostService;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Primary
public class SmsCostServiceImpl extends StandardizeLogging implements SmsCostService {

    @Autowired
    private SmsCostRepository smsCostRepository;

    public SmsCostServiceImpl() {
        super(SmsCostServiceImpl.class);
    }

    @Override
    public Page<SMSCost> getByCondition(SMSCostDTO smsCostDTO) {
        DataUtil.trim(smsCostDTO);
        return smsCostRepository.getByCondition(smsCostDTO);
    }

    public boolean smsCostExistUpdate(SMSCostDTO smsCostDTO) {
      return smsCostRepository.smsCostExistUpdate(smsCostDTO);
    }


    @Override
    public void save(SMSCost smsCost) {
        Date date = new Date();
        DataUtil.trim(smsCost);
        if (smsCostRepository.smsCostExist(smsCost)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existData", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("sms-cost.brand.exist"));
        }
        if (DataUtil.isNullOrEmpty(smsCost.getGroupName())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "nullgroupName", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_NULL_GROUP_NAME);
        }
        if (DataUtil.isNullOrEmpty(smsCost.getPrice())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "nullprice", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_NULL_PRICE, "VND");
        }
        if (smsCost.getPrice().intValue() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_INSERT_FAIL);
        }
        if (smsCost.getPrice().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_PRICE_TOO_LARGE, "VND");
        }
        if (DataUtil.isNullOrEmpty(smsCost.getPriceUsd())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "nullpriceUsd", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_NULL_PRICE, "USD");
        }
        if (smsCost.getPriceUsd().intValue() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_INSERT_FAIL);
        }
        String priceUsd = smsCost.getPriceUsd().toString();
        String[] priceUsds = priceUsd.split("/.");
        if (priceUsds[0].length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_PRICE_TOO_LARGE, "USD");
        }
        smsCost.setPriceUsdToDB();
        smsCostRepository.save(smsCost);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Date date = new Date();
        try {
            smsCostRepository.deleteByCpAliasGroupId(id);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.SMS_COST_DELETE_FAIL);
        }
    }
}

