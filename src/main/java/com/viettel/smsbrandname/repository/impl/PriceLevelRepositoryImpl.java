package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.PriceLevel;
import com.viettel.smsbrandname.repository.PriceLevelRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PriceLevelRepositoryImpl implements PriceLevelRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Object[]> onSearch(Long numberSms, Integer start, Integer limit) {
        StringBuilder sql = new StringBuilder(" "
            + "select t.price_level_id priceLevelId,\n"
            + "       t.price_level_name priceLevelName,\n"
            + "       t.num_sms numSms,\n"
            + "       t.type,\n"
            + "       row_number() over(order by t.num_sms, t.type) rn, \n"
            + "       CASE   WHEN t.type = :P_TYPE_LESS_CONSTANTS THEN '" + Translator.toLocale("prop.type.less") + "'\n"
            + "              WHEN t.type = :P_TYPE_EQUAL_GREATER_CONSTANTS THEN '" + Translator.toLocale("prop.type.equal.greater") + "'\n"
            + "              ELSE ' ' \n"
            + "       END AS typeName\n"
            + "  from price_level t\n"
            + " where (:P_NUM_SMS = -1 OR t.num_sms =:P_NUM_SMS) ");

        StringBuilder sqlPagination = new StringBuilder();
        sqlPagination.append("SELECT * FROM");
        sqlPagination.append("(").append(sql).append(")");
        sqlPagination.append(" WHERE ((:P_START + :P_LIMIT = 0) OR (RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

        Query query = entityManager.createNativeQuery(sqlPagination.toString());
        query.setParameter("P_NUM_SMS", numberSms == null ? -1 : numberSms);
        query.setParameter("P_START", start);
        query.setParameter("P_LIMIT", limit);

        query.setParameter("P_TYPE_LESS_CONSTANTS", Constants.PRICE_LEVEL_TYPE_LESS);
        query.setParameter("P_TYPE_EQUAL_GREATER_CONSTANTS", Constants.PRICE_LEVEL_TYPE_EQUAL_GREATER);
        return query.getResultList();
    }

    @Override
    public boolean checkDuplicate(PriceLevel priceLevel) {
        String sql = ""
            + "select * \n"
            + "  from price_level t \n"
            + " where ((t.num_sms = :P_NUM_SMS \n"
            + "   AND t.type = :P_TYPE) OR (LOWER(PRICE_LEVEL_NAME) = :P_PRICE_LEVEL_NAME) ) \n"
            + "   AND (:P_PRICE_LEVEL_ID is null OR :P_PRICE_LEVEL_ID = -1 OR t.price_level_id != :P_PRICE_LEVEL_ID)";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_NUM_SMS", priceLevel.getNumSms());
        query.setParameter("P_TYPE", priceLevel.getType());
        query.setParameter("P_PRICE_LEVEL_NAME", priceLevel.getPriceLevelName().toLowerCase());
        query.setParameter("P_PRICE_LEVEL_ID", priceLevel.getPriceLevelId() == null ? -1 : priceLevel.getPriceLevelId());

        return query.getResultList().size() > 0L;
    }
}
