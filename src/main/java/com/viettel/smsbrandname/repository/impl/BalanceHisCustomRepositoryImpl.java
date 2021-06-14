package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.repository.BalanceHisCustomRepository;
import com.viettel.smsbrandname.service.dto.VirtualAccountDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class BalanceHisCustomRepositoryImpl implements BalanceHisCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean updateBalanceHis(VirtualAccountDTO virtual, boolean firstTran, String saleTransId, String userName) {
        String sql = "UPDATE balance_his SET status_bccs_usd = :statusBccsUsd, "
            + "exchange_rate = :exchangeRate, sale_trans_id = :saleTransId, "
            + "is_bccs_ok = :isBccsOk, has_commission = :hasCommission, "
            + "commission_bccs_ok = :commissionBccsOk, change_note = :changeNote, "
            + "branch_name = :branchName, modified_user = :modifiedUser, "
            + "branch_id = :branchId "
            + "WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("statusBccsUsd", 1);
        query.setParameter("exchangeRate", virtual.getRate());
        query.setParameter("saleTransId", saleTransId);
        query.setParameter("isBccsOk", 1);
        query.setParameter("hasCommission", firstTran ? 1 : 0);
        query.setParameter("commissionBccsOk", 0);
        query.setParameter("changeNote", virtual.getNote());
        query.setParameter("branchName", virtual.getBranchName());
        query.setParameter("modifiedUser", userName);
        query.setParameter("branchId", virtual.getBranch());
        query.setParameter("id", virtual.getId());
        return query.executeUpdate() > 0;
    }

    @Override
    public boolean updateIsBccsOk(Long balanceHisId, Long saleTransId) {
        return false;
    }
}
