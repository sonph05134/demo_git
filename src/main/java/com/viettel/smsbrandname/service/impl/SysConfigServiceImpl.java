package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.SysConfig;
import com.viettel.smsbrandname.repository.SysConfigRepository;
import com.viettel.smsbrandname.service.SysConfigService;
import com.viettel.smsbrandname.service.dto.SysConfigDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class SysConfigServiceImpl extends StandardizeLogging implements SysConfigService {

    @Autowired
    SysConfigRepository sysConfigRepository;

    public SysConfigServiceImpl() {
        super(SysConfigServiceImpl.class);
    }

    @Override
    public ApiResponseDTO onSearch(String keyWord, Long module, Long deleted, Integer start, Integer limit) throws SQLException {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> list = sysConfigRepository.onSearch(keyWord, module, deleted, start, limit);
        int total = sysConfigRepository.onSearch(keyWord, module, deleted, 0, 0).size();
        apiResponseDTO.setData(SysConfigDTO.toDto(list));
        apiResponseDTO.setTotalRow(total);
        return apiResponseDTO;
    }

    @Override
    public void saveOrEdit(SysConfig sysConfig, String username) {
        Date date = new Date();
        if (sysConfigRepository.isExistsData(sysConfig)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "isExistsData", date);
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("sys-config"));
        }
        sysConfig.setUserUpdated(username);
        sysConfig.setDateUpdated(Instant.now());
        try {
            sysConfigRepository.save(sysConfig);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.SYS_CONFIG_SAVE_FAIL);
        }
    }

    @Override
    @Transactional
    public void activeOrInactive(SysConfig sysConfig) {
        sysConfigRepository.updateDeleted(sysConfig);
    }
}
