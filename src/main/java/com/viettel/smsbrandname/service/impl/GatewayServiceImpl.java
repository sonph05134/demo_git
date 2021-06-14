package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.repository.CfgGatewayRepository;
import com.viettel.smsbrandname.service.GatewayService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class GatewayServiceImpl extends StandardizeLogging implements GatewayService {

    @Autowired
    private CfgGatewayRepository cfgGatewayRepository;

    public GatewayServiceImpl() {
        super(GatewayServiceImpl.class);
    }

    @Override
    public Page<CfgGate> search(PageDTO page) {
        return cfgGatewayRepository.getAll(page);
    }

    @Override
    public void save(CfgGate cfgGate, boolean isUpdate) {
        Date date = new Date();
        DataUtil.trim(cfgGate);
        if (cfgGate.getStatus() != 0 && cfgGate.getStatus() != 1) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "invalidStatus", date);
            throw new BusinessException(ErrorCodeResponse.STATUS_ILLEGAL);
        }
        if (!isUpdate && cfgGatewayRepository.getByCfgGateId(cfgGate.getCfgGateId()).isPresent()) {
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("smsc-gateway.gateway-code"));
        }
        if (cfgGate.getStatus() == 0) {
            Integer connection = cfgGatewayRepository.checkSmscGateway(cfgGate.getCfgGateId());
            if (connection > 0) {
                throw new BusinessException(ErrorCodeResponse.INACTIVE_GATEWAY_FAIL, cfgGate.getCfgGateId(), connection.toString());
            }
        }
        try {
            cfgGatewayRepository.save(cfgGate);
        }
        catch (Exception e) {
            if (!isUpdate) {
                if (e instanceof ConstraintViolationException) {
                    Integer errCode = ((ConstraintViolationException) e).getErrorCode();
                    if (!DataUtil.isNullOrEmpty(errCode) && errCode.equals(1)) {
                        throw new BusinessException(ErrorCodeResponse.GATEWAY_EXIST, cfgGate.getCfgGateId());
                    }
                } else {
                    throw new BusinessException(ErrorCodeResponse.SAVE_GATEWAY_FAIL, cfgGate.getCfgGateId());
                }
            }
            if (isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwGateWayCfgDAO_onSave_failed", date);
                throw new BusinessException(ErrorCodeResponse.SAVE_GATEWAY_FAIL, cfgGate.getCfgGateId());
            }
//            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwGateWayCfgDAO_onSave_failed", date);
//            throw new BusinessException(ErrorCodeResponse.SAVE_GATEWAY_FAIL, cfgGate.getCfgGateId());
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            cfgGatewayRepository.deleteByCfgGateId(id);
        } catch (Exception e) {
            if (e instanceof ConstraintViolationException) {
                Integer errorCode = ((ConstraintViolationException) e).getErrorCode();
                if (!DataUtil.isNullOrEmpty(errorCode) && errorCode.equals(2292)) {
                    throw new BusinessException(ErrorCodeResponse.DELETE_GATEWAY_FAIL, id);
                }
            }
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwGateWayCfgDAO_onDelete_failed", new Date());
            throw e;
        }
    }

    @Override
    public void changeStatus(CfgGate cfgGate, int status) {
        Date date = new Date();
        if (status == 0) {
            Integer connection = cfgGatewayRepository.checkSmscGateway(cfgGate.getCfgGateId());
            if (connection > 0) {
                throw new BusinessException(ErrorCodeResponse.INACTIVE_GATEWAY_FAIL, cfgGate.getCfgGateId(), connection.toString());
            }
        }
        try {
            cfgGatewayRepository.changeStatus(cfgGate.getCfgGateId(), status);
        }
        catch (Exception e) {
            if (status == 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "active failed", date);
            }
            if (status == 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "inactive failed", date);
            }
            throw new BusinessException(ErrorCodeResponse.GATEWAY_CHANGE_STATUS_FAIL, cfgGate.getCfgGateId());
        }
    }
}
