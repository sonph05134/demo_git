package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.repository.DiscountProvinceRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DiscountProvinceRepositoryImpl implements DiscountProvinceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> getDiscountByType(Integer type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT DISCOUNT_ID, CONCAT(DISCOUNT_VALUE,'%') discountValue FROM DISCOUNT ");
        stringBuilder.append("WHERE TYPE = :type ORDER BY DISCOUNT_VALUE");
        Query query = entityManager.createNativeQuery(stringBuilder.toString());
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getProvince() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT PROVINCE_ID, PROVINCE_NAME FROM PROVINCE ");
        stringBuilder.append("ORDER BY NLSSORT(UPPER(PROVINCE_NAME),'NLS_SORT=vietnamese')");
        Query query = entityManager.createNativeQuery(stringBuilder.toString());
        return query.getResultList();
    }

    @Override
    public List<Object[]> getProvinceByDiscountId(Long discountId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT province_id provinceId, discount_id discountId ");
        stringBuilder.append("FROM discount_province e WHERE discount_id = :discountId");
        Query query = entityManager.createNativeQuery(stringBuilder.toString());
        query.setParameter("discountId", discountId);
        return query.getResultList();
    }


    @Override
    public void deleteData(List<Long> newListDelete, Long discountId) {
        String delete = "DELETE FROM discount_province WHERE discount_id = :discountId "
            + "AND province_id IN (:provinces)";
        Query queryDel = entityManager.createNativeQuery(delete);
        queryDel.setParameter("discountId", discountId);
        queryDel.setParameter("provinces", newListDelete);
        queryDel.executeUpdate();
    }

    @Override
    public void insertData(Long provinceId, Long discountId) {
        String save = "INSERT INTO discount_province VALUES(:discountId, :provinceId) ";
            Query queryDel = entityManager.createNativeQuery(save);
            queryDel.setParameter("discountId", discountId);
            queryDel.setParameter("provinceId", provinceId);
            queryDel.executeUpdate();

    }
}
