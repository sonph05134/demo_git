package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.repository.ComboRepository;
import com.viettel.smsbrandname.service.dto.ComboBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ComboRepositoryImpl implements ComboRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ComboBean> getLstProvinceBccs(String userName) {
        String sql = ""
            + "SELECT PB.PROVINCE_BCCS_ID value,\n"
            + "     PB.PROVINCE_BCCS_NAME label\n"
            + "  FROM PROVINCE_BCCS PB\n"
            + "  LEFT JOIN PROVINCE_USERS P\n"
            + "    ON P.PROVINCE_ID = PB.PROVINCE_ID\n"
            + " WHERE 1 = 1\n"
            + "   AND (:P_USER_NAME IS NULL OR P.USER_NAME = :P_USER_NAME OR NOT EXISTS\n"
            + "        (SELECT 1\n"
            + "           FROM PROVINCE_BCCS BC\n"
            + "           JOIN PROVINCE_USERS PU\n"
            + "             ON PU.PROVINCE_ID = BC.PROVINCE_ID\n"
            + "          WHERE PU.USER_NAME = :P_USER_NAME))\n"
            + " GROUP BY PB.PROVINCE_BCCS_ID, PB.PROVINCE_BCCS_NAME\n"
            + " ORDER BY PB.PROVINCE_BCCS_ID";
        List<Object[]> lst = entityManager.createNativeQuery(sql).setParameter("P_USER_NAME", userName).getResultList();
        return new MaperUtils(lst).add("value").add("label").transform(ComboBean.class);
    }

    @Override
    public List<ComboBean> getListPriceLevel() {
        String sql = "SELECT price_level_id AS value, price_level_name AS label FROM price_level ORDER BY num_sms ASC, type ASC";
        List<Object[]> lst = entityManager.createNativeQuery(sql).getResultList();
        return new MaperUtils(lst).add("value").add("label").transform(ComboBean.class);
    }

    @Override
    public List<ComboBean> getSMSC() {
        StringBuilder sql = new StringBuilder();
        sql.append("select upper(cfg_smsc_id) label, cfg_smsc_id value from Cfg_Smsc ")
            .append("where enable = 1 order by NLSSORT(upper(cfg_Smsc_Id),'NLS_SORT=vietnamese')");
        List<Object[]> lst = entityManager.createNativeQuery(sql.toString()).getResultList();
        return new MaperUtils(lst).add("label").add("value").transform(ComboBean.class);
    }

    @Override
    public List<ComboBean> getGateway() {
        StringBuilder sql = new StringBuilder();
        sql.append("select upper(cfg_gate_id) label, cfg_gate_id value from Cfg_Gate ")
            .append("where status = 1 order by NLSSORT(upper(cfg_Gate_Id),'NLS_SORT=vietnamese')");
        List<Object[]> lst = entityManager.createNativeQuery(sql.toString()).getResultList();
        return new MaperUtils(lst).add("label").add("value").transform(ComboBean.class);
    }
}
