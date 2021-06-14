package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.repository.MaintainFeeCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;

public class MaintainFeeCustomRepositoryImpl implements MaintainFeeCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean maintainFeeExist(Long id, BigDecimal feeValue, Integer currency) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(fee_id) FROM alias_keep_fee")
            .append(" WHERE currency = :currency AND fee_value = :feeValue ");
        HashMap<String, Object> hmap = new HashMap<>();
        if (id != null) {
            sql.append(" and fee_Id != :id ");
            hmap.put("id", id);
        }
        hmap.put("currency", currency);
        hmap.put("feeValue", feeValue);
        Query query = entityManager.createNativeQuery(sql.toString());
        hmap.forEach(query::setParameter);
        return ((BigDecimal) query.getSingleResult()).longValue() > 0;
    }
}
