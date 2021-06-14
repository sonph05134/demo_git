package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmsc;
import com.viettel.smsbrandname.domain.CfgSmscGate;
import com.viettel.smsbrandname.repository.CfgGatewayRepository;
import com.viettel.smsbrandname.repository.CfgSmscGateRepository;
import com.viettel.smsbrandname.repository.CfgSmscRepository;
import com.viettel.smsbrandname.service.CfgSmscGateService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class CfgSmscGateServiceImpl extends StandardizeLogging implements CfgSmscGateService {

    @Autowired
    private CfgSmscGateRepository cfgSmscGateRepository;

    @Autowired
    private CfgGatewayRepository cfgGateRepo;

    @Autowired
    private CfgSmscRepository cfgSmscRepo;

    public CfgSmscGateServiceImpl() {
        super(CfgSmscGateServiceImpl.class);
    }

    @Override
    public Page<CfgSmscGate> getAll(PageDTO pageDTO) {
        Pageable page = PageRequest.of(pageDTO.getCurrentPage(), pageDTO.getPageSize(), Sort.by("cfgGateId").ascending());
        return cfgSmscGateRepository.findAll(page);
    }

    @Override
    public void save(CfgSmscGate cfgSmscGate) {
        Date date = new Date();
        if(!gateExist(cfgSmscGate.getCfgGateId())) {
            throw new BusinessException(ErrorCodeResponse.ERR_NOT_EXISTED, Translator.toLocale("smsc-gateway.gateway-code"));
        }
        if(!smscExist(cfgSmscGate.getCfgSmscId())) {
            throw new BusinessException(ErrorCodeResponse.ERR_NOT_EXISTED, Translator.toLocale("smsc-gateway.smsc-code"));
        }
        DataUtil.trim(cfgSmscGate);
        try {
            cfgSmscGateRepository.save(cfgSmscGate);
        }
        catch (Exception e) {
            if (e instanceof ConstraintViolationException) {
                Integer errCode = ((ConstraintViolationException) e).getErrorCode();
                if (!DataUtil.isNullOrEmpty(errCode) && errCode.equals(1)) {
                    throw new BusinessException(ErrorCodeResponse.SMSC_GATE_EXIST, cfgSmscGate.getCfgGateId(), cfgSmscGate.getCfgSmscId());
                }
            } else {
                throw new BusinessException(ErrorCodeResponse.SMSC_GATE_SAVE_FAIL, cfgSmscGate.getCfgGateId(), cfgSmscGate.getCfgSmscId());
            }
        }
    }

    @Override
    public void changeStatus(CfgSmscGate cfgSmscGate, int status) {
        Date date = new Date();
        if(status == 1 && !cfgSmscGateRepository.smscGateExist(cfgSmscGate.getCfgSmscGateId())) {
            throw new BusinessException(ErrorCodeResponse.ACTIVE_CONNECTION_FAIL, cfgSmscGate.getCfgGateId(), cfgSmscGate.getCfgSmscId());
        }
        DataUtil.trim(cfgSmscGate);
        try {
            cfgSmscGateRepository.changeStatus(cfgSmscGate.getCfgSmscGateId(), status);
        }
        catch (Exception e) {
            if (status == 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwCfgSmsSplitDAO_active_failed", date);
            }
            if (status == 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwCfgSmsSplitDAO_inactive_failed", date);
            }
            throw new BusinessException(ErrorCodeResponse.SMSC_GATE_CHANGE_STATUS_FAIL);
        }
    }

    private boolean gateExist(String gatewayId) {
        Optional<CfgGate> optional = cfgGateRepo.getByCfgGateId(gatewayId);
        return optional.isPresent();
    }

    private boolean smscExist(String smscId) {
        Optional<CfgSmsc> optional = cfgSmscRepo.getByCfgSmscId(smscId);
        return optional.isPresent();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Date date = new Date();
        try {
            cfgSmscGateRepository.deleteByAndCfgSmscGateId(id);
        }
        catch (Exception e) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "GwCfgSmsSplitDAO_onDelete_failed", date);
            throw new BusinessException(ErrorCodeResponse.SMSC_GATE_NOT_EXIST);
        }
    }
}
