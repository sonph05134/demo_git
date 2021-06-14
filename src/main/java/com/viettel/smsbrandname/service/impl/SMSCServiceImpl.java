package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmsc;
import com.viettel.smsbrandname.repository.CfgGatewayRepository;
import com.viettel.smsbrandname.repository.CfgSmscRepository;
import com.viettel.smsbrandname.service.GatewayService;
import com.viettel.smsbrandname.service.SMSCService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SMSCServiceImpl extends StandardizeLogging implements SMSCService {

    @Autowired
    private CfgSmscRepository cfgSmscRepository;

    public SMSCServiceImpl() {
        super(SMSCServiceImpl.class);
    }

    @Override
    public Page<CfgSmsc> search(PageDTO page) {
        return cfgSmscRepository.getAll(page);
    }

    @Override
    public void save(CfgSmsc cfgSmsc, boolean isUpdate) {
        Date date = new Date();
        DataUtil.trim(cfgSmsc);
        if(!isUpdate && cfgSmscRepository.getByCfgSmscIdIgnoreCase(cfgSmsc.getCfgSmscId()).isPresent()) {
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("smsc-gateway.smsc-code"));
        }
        if(cfgSmsc.getEnable() == 0) {
            Integer connection = cfgSmscRepository.checkSmscGateway(cfgSmsc.getCfgSmscId());
            if(connection > 0) {
                throw new BusinessException(ErrorCodeResponse.INACTIVE_GATEWAY_FAIL, cfgSmsc.getCfgSmscId(), connection.toString());
            }
        }
        try {
            cfgSmscRepository.save(cfgSmsc);
        }
        catch (Exception e) {
            if (!isUpdate) {
                if (e instanceof ConstraintViolationException) {
                    Integer errCode = ((ConstraintViolationException) e).getErrorCode();
                    if (!DataUtil.isNullOrEmpty(errCode) && errCode.equals(1)) {
                        throw new BusinessException(ErrorCodeResponse.SMSC_EXIST, cfgSmsc.getCfgSmscId());
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        Date date = new Date();
        try {
            cfgSmscRepository.deleteByCfgSmscId(id);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwCfgSmscDAO_onDelete_failed", date);
            if (e instanceof ConstraintViolationException) {
                Integer errCode = ((ConstraintViolationException) e).getErrorCode();
                if (!DataUtil.isNullOrEmpty(errCode) && errCode.equals(2292)) {
                    throw new BusinessException(ErrorCodeResponse.SMSC_DELETE_IN_USE);
                }
            }
            throw new BusinessException(ErrorCodeResponse.SMSC_DELETE_FAIL, id);
        }
    }

    @Override
    public void changeStatus(CfgSmsc cfgSmsc, short status) {
        Date date = new Date();
        if(status == 0) {
            Integer connection = cfgSmscRepository.checkSmscGateway(cfgSmsc.getCfgSmscId());
            if(connection > 0) {
                throw new BusinessException(ErrorCodeResponse.INACTIVE_SMSC_FAIL, cfgSmsc.getCfgSmscId(), connection.toString());
            }
        }
        try {
            cfgSmscRepository.changeStatus(cfgSmsc.getCfgSmscId(), status);
        }
        catch (Exception e) {
            if (status == 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwCfgSmscDAO_active_fail", date);
            }
            if (status == 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwCfgSmscDAO_inactive_fail", date);
            }
            throw new BusinessException(ErrorCodeResponse.SMSC_CHANGE_STATUS_FAIL);
        }
    }
}
