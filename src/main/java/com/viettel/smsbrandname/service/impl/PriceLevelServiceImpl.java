package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.PriceLevel;
import com.viettel.smsbrandname.repository.PriceLevelRepository;
import com.viettel.smsbrandname.service.PriceLevelService;
import com.viettel.smsbrandname.service.dto.PriceLevelDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PriceLevelServiceImpl extends StandardizeLogging implements PriceLevelService {

    @Autowired
    PriceLevelRepository priceLevelRepository;

    public PriceLevelServiceImpl() {
        super(PriceLevelServiceImpl.class);
    }

    @Override
    public boolean checkPriceLevel(Long priceLevelId) {
        boolean check = false;
        try {
            if(priceLevelRepository.checkPriceLevel(priceLevelId) > 0){
                check = true;
            }
        }catch (Exception e){
            return check;

        }
        return check;
    }

    @Override
    public ApiResponseDTO onSearch(Long numberSms, Integer start, Integer limit) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> list = priceLevelRepository.onSearch(numberSms, start, limit);
        int total = priceLevelRepository.onSearch(numberSms, 0, 0).size();
        apiResponseDTO.setData(PriceLevelDTO.toDto(list));
        apiResponseDTO.setTotalRow(total);
        return apiResponseDTO;
    }

    @Override
    public void saveOrEdit(PriceLevel priceLevel) {
        Date date = new Date();
        if (priceLevelRepository.checkDuplicate(priceLevel)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "isExistData", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("price.level.name"));
        }
        try {
            priceLevelRepository.save(priceLevel);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.PRICE_LEVEL_SAVE_FAIL);
        }
    }

    @Override
    public void delete(Long priceLevelId) {
        Date date = new Date();
        try {
            priceLevelRepository.deleteById(priceLevelId);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.PRICE_LEVEL_DELETE_FAIL);
        }
    }
}
