package com.viettel.smsbrandname.service.impl;

import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.cmsmobilebrandname.mobile.dto.mapper.AliasCostMapper;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.repository.AliasCostRepository;
import com.viettel.smsbrandname.service.AliasCostService;
import com.viettel.smsbrandname.service.dto.aliascost.AliasResultDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AliasCostServiceImpl extends StandardizeLogging implements AliasCostService {

    @Autowired
    AliasCostRepository aliasCostRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AliasCostMapper aliasCostMapper;

    public AliasCostServiceImpl() {
        super(AliasCostServiceImpl.class);
    }

    @Override
    public AliasCostDTO getInformationAliasCost(String teLcoAlias, Long aliasType) {
        return aliasCostMapper.toDto(aliasCostRepository.getInformationAliasCost(teLcoAlias,aliasType));
    }

    @Override
    public ApiResponseDTO onSearch(String telco, Integer aliasType, Integer limit, Integer start) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> list = aliasCostRepository.onSearch(telco, aliasType, limit, start);
        int total = aliasCostRepository.onSearch(telco, aliasType, 0, 0).size();
        apiResponseDTO.setData(AliasResultDTO.toDto(list));
        apiResponseDTO.setTotalRow(total);
        return apiResponseDTO;
    }

    @Override
    public void saveOrEdit(AliasCost aliasCost) {
        Date date = new Date();
        if (DataUtil.isNullOrEmpty(aliasCost.getRegAliasCost())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "regAliasCostNull", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_REG_ALIAS_COST_NULL, "VND");
        }
        if (aliasCost.getRegAliasCost() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCost.getRegAliasCost().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (DataUtil.isNullOrEmpty(aliasCost.getCancelAliasCost())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "cancelAliasCostNull", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_CANCEL_ALIAS_COST_NULL, "VND");
        }
        if (aliasCost.getCancelAliasCost() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCost.getCancelAliasCost().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (DataUtil.isNullOrEmpty(aliasCost.getKeepAliasCost())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "keepAliasCostNull", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_KEEP_ALIAS_COST_NULL, "VND");
        }
        if (aliasCost.getKeepAliasCost() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCost.getKeepAliasCost().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (DataUtil.isNullOrEmpty(aliasCost.getRegAliasCostUSD())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "regAliasCostNull", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_REG_ALIAS_COST_NULL, "USD");
        }
        if (aliasCost.getRegAliasCostUSD() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCost.getRegAliasCostUSD().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (DataUtil.isNullOrEmpty(aliasCost.getCancelAliasCostUSD())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "cancelAliasCostNull", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_CANCEL_ALIAS_COST_NULL, "USD");
        }
        if (aliasCost.getCancelAliasCostUSD() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCost.getCancelAliasCostUSD().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (DataUtil.isNullOrEmpty(aliasCost.getKeepAliasCostUSD())) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "keepAliasCostNull", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_KEEP_ALIAS_COST_NULL, "USD");
        }
        if (aliasCost.getKeepAliasCostUSD() < 0) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCost.getKeepAliasCostUSD().toString().length() > 20) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "tooLargeValue", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
        if (aliasCostExist(aliasCost)) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "existsData", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_EXIST);
        }
        try {
            aliasCostRepository.save(aliasCost);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "insertFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_SAVE_FAILED);
        }
    }

    @Override
    public void delete(Long aliasCostId) {
        Date date = new Date();
        try {
            aliasCostRepository.deleteById(aliasCostId);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            throw new BusinessException(ErrorCodeResponse.ALIAS_COST_DELETE_FAIL);
        }
    }

    public boolean aliasCostExist(AliasCost aliasCost) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("aliasTelco", aliasCost.getAliasTelco());
        hashMap.put("aliasCostType", aliasCost.getAliasCostType());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(aliasCostId) FROM AliasCost ")
            .append("WHERE aliasTelco = :aliasTelco AND aliasCostType = :aliasCostType ");
        if (!DataUtil.isNullOrEmpty(aliasCost.getAliasCostId())) {
            sql.append("AND aliasCostId != :aliasCostId ");
            hashMap.put("aliasCostId", aliasCost.getAliasCostId());
        }
        Query query = entityManager.createQuery(sql.toString());
        hashMap.forEach(query::setParameter);

        return ((Long) query.getSingleResult()).intValue() > 0;
    }
}
