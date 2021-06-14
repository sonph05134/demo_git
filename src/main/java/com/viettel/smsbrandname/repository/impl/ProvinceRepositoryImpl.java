package com.viettel.smsbrandname.repository.impl;


import com.viettel.smsbrandname.domain.Province;
import com.viettel.smsbrandname.repository.ProvinceRepositoryCustom;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProvinceRepositoryImpl implements ProvinceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> onSearch(Long provinceId, Integer start, Integer limit, Double timeZone) {
        StringBuilder sql = new StringBuilder(" "
            + " SELECT p.province_id provinceId,\n"
            + "         p.province_name provinceName,\n"
            + "         p.province_code provinceCode,\n"
            + "         p.province_code_bccs provinceCodeBccs,\n"
            + "         p.user_updated userUpdated,\n"
            + "         to_char(p.date_updated + :P_TIME_ZONE, 'dd/mm/yyyy hh24:mi:ss') dateUpdateds,\n"
            + "         row_number() over(order by p.province_name) rn \n"
            + "    FROM province p\n"
            + "   where (:P_PROVINCE_ID = -1 OR p.province_id = :P_PROVINCE_ID)");

        StringBuilder sqlPagination = new StringBuilder();
        sqlPagination.append("SELECT * FROM");
        sqlPagination.append("(").append(sql).append(")");
        sqlPagination.append(" WHERE ((:P_START + :P_LIMIT = 0) OR (RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

        Query query = entityManager.createNativeQuery(sqlPagination.toString());
        query.setParameter("P_PROVINCE_ID", provinceId);
        query.setParameter("P_START", start);
        query.setParameter("P_LIMIT", limit);
        query.setParameter("P_TIME_ZONE", timeZone / 24);
        return query.getResultList();
    }

    @Override
    public boolean isExistsDataProvinceName(Province province) {
        String sql = ""
            + "SELECT * \n"
            + "  FROM PROVINCE S\n"
            + " WHERE (:P_PROVINCE_ID = -1 OR S.PROVINCE_ID != :P_PROVINCE_ID) \n"
            + "       AND (FC_CONVERTVN2ROMAN(S.PROVINCE_NAME) = FC_CONVERTVN2ROMAN(:P_PROVINCE_NAME)) ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_PROVINCE_NAME", province.getProvinceName());
        query.setParameter("P_PROVINCE_ID", province.getProvinceId() == null ? -1 : province.getProvinceId());

        return query.getResultList().size() > 0L;
    }

    @Override
    public boolean isExistsDataProvinceCode(Province province) {
        String sql = ""
            + "SELECT * \n"
            + "  FROM PROVINCE S\n"
            + " WHERE (:P_PROVINCE_ID = -1 OR S.PROVINCE_ID != :P_PROVINCE_ID) \n"
            + "         AND (FC_CONVERTVN2ROMAN(S.PROVINCE_CODE) = FC_CONVERTVN2ROMAN(:P_PROVINCE_CODE)) \n";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_PROVINCE_CODE", province.getProvinceCode());
        query.setParameter("P_PROVINCE_ID", province.getProvinceId() == null ? -1 : province.getProvinceId());

        return query.getResultList().size() > 0L;
    }

    public boolean isExistsCPWithProvince(Long provinceId) {
        String sql = ""
            + "SELECT * \n"
            + "  FROM CP\n"
            + " WHERE CP.PROVINCE_ID = :P_PROVINCE_ID ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_PROVINCE_ID", provinceId);

        return query.getResultList().size() > 0L;
    }

    @Override
    public boolean isExistData(Long provinceId) {
        String sql = ""
            + "SELECT * \n"
            + "  FROM PROVINCE\n"
            + " WHERE PROVINCE.PROVINCE_ID = :P_PROVINCE_ID ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_PROVINCE_ID", provinceId);

        return query.getResultList().size() > 0L;
    }

    @Override
    public boolean isExitDataBCCS(Long id) {
        String sql = ""
            + "SELECT * \n"
            + "  FROM PROVINCE_BCCS\n"
            + " WHERE PROVINCE_BCCS.ID = :ID ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("ID", id);

        return query.getResultList().size() > 0L;
    }

    public void insertHistoryTable(String tableName, String rowId, String userName, String values, String action) {
        String strSql = ""
            + "INSERT INTO HISTORY_TABLE\n"
            + "  (TABLE_NAME, TABLE_ID, USER_MODIFY, TABLE_VALUE, ACTION)\n"
            + "VALUES\n"
            + "  (:P_TABLE_NAME, :P_TABLE_ID, :P_USER_MODIFY, :P_TABLE_VALUE, :P_ACTION) ";
        Query query = entityManager.createNativeQuery(strSql);
        query.setParameter("P_TABLE_NAME", tableName);
        query.setParameter("P_TABLE_ID", rowId);
        query.setParameter("P_USER_MODIFY", userName);
        query.setParameter("P_TABLE_VALUE", values);
        query.setParameter("P_ACTION", action);
        query.executeUpdate();
    }
}
