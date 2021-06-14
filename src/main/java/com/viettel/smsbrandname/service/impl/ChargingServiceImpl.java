package com.viettel.smsbrandname.service.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.security.PassTranformer;
import com.viettel.smsbrand.ChargeResult;
import com.viettel.smsbrand.ChargingAPIService;
import com.viettel.smsbrand.CpBalance;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.service.ChargingService;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

@Service
public class ChargingServiceImpl extends StandardizeLogging implements ChargingService {

    private final Logger log = LoggerFactory.getLogger(ChargingServiceImpl.class);

    private String password;

    private String username;

    private ChargingAPIService service;

    public ChargingServiceImpl(@Value("${charging.password}") String password, @Value("${charging.username}") String username,
                               @Value("${charging.url}") String url) {
        super(ChargingServiceImpl.class);
        try {
            this.username = PassTranformer.decrypt(username);
            this.password = PassTranformer.decrypt(password);
            this.service = new ChargingAPIService(new URL(url));
        } catch (Exception e) {
            log.error("descrypt fail", e);
        }
    }

    @Override
    public boolean checkExistsCP(String cpCode) {
        Date date = new Date();
        if (cpCode == null || "".equals(cpCode.trim())) {
            return false;
        }
        try {
            CpBalance result = service.getChargingAPIPort().checkBalance(username,
                password, cpCode, 0);
            return result.getErrCode() == 0;
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "cpIsNotExists", date);
            throw new BusinessException(ErrorCodeResponse.CP_NOT_EXIST_IN_CHARGING);
        }
    }

    @Override
    public boolean changeBalance(String cpCode, BigDecimal amount, int channel, String note, long exchangeRate,
                                 long discountValue, String attachFile, String saleTransId, String branchId, String modifiedUser,
                                 int balanceType, String branchName, int hasCommission, int commissionBccsOk) {
        Date date = new Date();
        if (cpCode == null) {
            return false;
        }
        try {
            ChargeResult result = service.getChargingAPIPort().changeBalance(this.username, this.password,
                cpCode, amount, balanceType, channel, note, exchangeRate, discountValue, attachFile,
                saleTransId, branchId, branchName, modifiedUser, hasCommission, commissionBccsOk);
            return result.getErrCode() == 0;
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

    @Override
    public boolean charge(String cpCode, BigDecimal amount, int channel, String chargeType, String note,
                          int balanceType) {
        if (cpCode == null) {
            return false;
        }
        try {
            ChargeResult result = service.getChargingAPIPort().charge(
                this.username, this.password, cpCode, amount, balanceType, channel, chargeType, note);
            return result.getErrCode() == 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
