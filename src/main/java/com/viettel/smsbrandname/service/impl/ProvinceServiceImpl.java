package com.viettel.smsbrandname.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Province;
import com.viettel.smsbrandname.repository.ProvinceRepository;
import com.viettel.smsbrandname.service.ProvinceService;
import com.viettel.smsbrandname.service.dto.province.ProvinceDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceServiceImpl extends StandardizeLogging implements ProvinceService {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceServiceImpl.class);

    @Autowired
    ProvinceRepository provinceRepository;

    public ProvinceServiceImpl() {
        super(ProvinceServiceImpl.class);
    }

    @Override
    public ApiResponseDTO onSearch(Long provinceId, Integer start, Integer limit, Double timeZone) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> list = provinceRepository.onSearch(provinceId, start, limit, timeZone);
        int total = provinceRepository.onSearch(provinceId, 0, 0, timeZone).size();
        apiResponseDTO.setData(ProvinceDTO.toDto(list));
        apiResponseDTO.setTotalRow(total);
        return apiResponseDTO;
    }

    @Override
    public void saveOrEdit(Province province, String username) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(province.getProvinceId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        if (provinceRepository.isExistsDataProvinceName(province)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "isExistData", date);
            throw new BusinessException(ErrorCodeResponse.ERR_NAME_EXIST, Translator.toLocale("province.info"));
        }

        if (provinceRepository.isExistsDataProvinceCode(province)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "isExistData", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("province.info"));
        }
        province.setDateUpdated(new Date());
        province.setUserUpdated(username);
        try {
            provinceRepository.save(province);
        }
        catch (Exception e) {
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
    public void delete(Province province, String userName) {
        Date date = new Date();

        if (provinceRepository.isExistsCPWithProvince(province.getProvinceId())) {
            throw new BusinessException(ErrorCodeResponse.EXIST_CP_WITH_PROVINCE);
        }

        if (!provinceRepository.isExistData(province.getProvinceId())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }

        String json = "";
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(province);
            provinceRepository.insertHistoryTable("PROVINCE", province.getProvinceId().toString(), userName, json, Constants.DELETE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new BusinessException(ErrorCodeResponse.UNKNOWN);
        }
        try {
            provinceRepository.deleteById(province.getProvinceId());
        } catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.DELETE_FAIL);
        }
    }
}
