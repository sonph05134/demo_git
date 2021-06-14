package com.viettel.smsbrandname.service;

import java.math.BigDecimal;

public interface ChargingService {

    boolean checkExistsCP(String cpCode);

    boolean changeBalance(String cpCode, BigDecimal amount, int channel, String note, long exchangeRate,
                          long discountValue, String attachFile, String saleTransId, String branchId, String modifiedUser,
                          int balanceType, String branchName, int hasCommission, int commissionBccsOk);

    boolean charge(String cpCode, BigDecimal amount, int channel, String chargeType, String note,
                   int balanceType);
}
