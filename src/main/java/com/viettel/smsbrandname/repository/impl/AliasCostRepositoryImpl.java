package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.repository.AliasCostRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AliasCostRepositoryImpl implements AliasCostRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> onSearch(String telco, Integer aliasType, Integer limit, Integer start) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n"
                +  "a.ALIAS_COST_ID, \n"
                +  "b.TELCO_NAME, \n"
                +  "a.ALIAS_TELCO, \n"
                +  "a.ALIAS_COST_TYPE, \n"
                +  "CASE  WHEN a.ALIAS_COST_TYPE = :P_ADVERTISE_CONSTANTS THEN '" + Translator.toLocale("prop.advertise") + "'\n"
                + "       WHEN a.ALIAS_COST_TYPE = :P_CUSTOMER_CARE_CONSTANTS THEN '" + Translator.toLocale("prop.customerCare") + "'\n"
                + "       ELSE ' ' \n"
                + " END AS aliasCostTypeName, \n"
                +  "a.REG_ALIAS_COST, \n"
                +  "a.CANCEL_ALIAS_COST, \n"
                +  "a.KEEP_ALIAS_COST, \n"
                +  "a.REG_ALIAS_COST_USD, \n"
                +  "a.CANCEL_ALIAS_COST_USD, \n"
                +  "a.KEEP_ALIAS_COST_USD, \n"
                +  "TO_CHAR(a.MODIFIED_DATE, 'DD-MM-YYYY HH24:MI:SS'), \n"
                +  "a.CREATE_DATE, \n"
                +  "ROW_NUMBER() OVER(ORDER BY a.MODIFIED_DATE DESC) RN \n"
                +  "FROM ALIAS_COST a JOIN TELCO b ON a.alias_telco = b.telco_code \n"
                +  "WHERE \n"
                +  "(:P_ALIAS_TELCO IS NULL OR a.alias_telco = :P_ALIAS_TELCO) \n"
                +  "AND (:P_ALIAS_COST_TYPE = -1 OR a.ALIAS_COST_TYPE = :P_ALIAS_COST_TYPE) \n"
        );

        StringBuilder sqlPagination = new StringBuilder();
        sqlPagination.append("SELECT * FROM");
        sqlPagination.append("(").append(sql).append(")");
        sqlPagination.append(" WHERE ((:P_START + :P_LIMIT = 0) OR (RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

        Query query = entityManager.createNativeQuery(sqlPagination.toString());
        query.setParameter("P_ALIAS_TELCO", DataUtil.isNullOrEmpty(telco) ? null : telco);
        query.setParameter("P_ALIAS_COST_TYPE", aliasType);
        query.setParameter("P_START", start);
        query.setParameter("P_LIMIT", limit);
        query.setParameter("P_ADVERTISE_CONSTANTS", Constants.CP_ALIAS_ALIAS_TYPE_ADVERTISE);
        query.setParameter("P_CUSTOMER_CARE_CONSTANTS", Constants.CP_ALIAS_ALIAS_TYPE_CUSTOMER_CARE);

        return query.getResultList();
    }
}
