package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.VirtualAccountDTO;

public interface BalanceHisCustomRepository {

    boolean updateBalanceHis(VirtualAccountDTO virtual, boolean firstTran, String saleTransId, String userName);
    boolean updateIsBccsOk(Long balanceHisId, Long saleTransId);
}
