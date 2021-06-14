package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.domain.Discount;
import com.viettel.smsbrandname.repository.DiscountCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;

public class DiscountCustomRepositoryImpl implements DiscountCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public boolean discountExist(Discount discount) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(*) FROM discount WHERE discount_value = :discountValue and type= :type ");
        HashMap<String, Object> hmap = new HashMap<>();
        if(discount.getId() != null) {
            sql.append(" AND discount_id != :discountId");
            hmap.put("discountId", discount.getId());
        }
        hmap.put("discountValue", discount.getDiscountValue());
        hmap.put("type", discount.getType());
        Query query = entityManager.createNativeQuery(sql.toString());
        hmap.forEach(query::setParameter);
        return ((BigDecimal) query.getSingleResult()).longValue() > 0;
    }

    @Override
    public void deleteDiscountProvince(Long id) {
        Query query = entityManager.createNativeQuery("DELETE FROM discount_province WHERE discount_id = :discountId");
        query.setParameter("discountId", id);
        query.executeUpdate();
    }
}
