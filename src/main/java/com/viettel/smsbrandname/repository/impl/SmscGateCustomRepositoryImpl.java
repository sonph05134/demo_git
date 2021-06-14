package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.repository.SmscGateCustomRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

@Repository
public class SmscGateCustomRepositoryImpl implements SmscGateCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean smscGateExist(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from cfg_smsc_gate cn, cfg_gate gw, cfg_smsc smsc ")
            .append("where cn.cfg_smsc_gate_id = :id and cn.cfg_gate_id = gw.cfg_gate_id ")
            .append("and gw.status = 1 and cn.cfg_smsc_id = smsc.cfg_smsc_id and smsc.enable = 1");
        Query query = entityManager.createNativeQuery(sql.toString())
            .setParameter("id", id);
        return ((BigDecimal) query.getSingleResult()).longValue() > 0;
    }

    @Override
    @Transactional
    public void changeStatus(Long id, int status) {
        String sql = "update cfg_smsc_gate set status = :status where cfg_smsc_gate_id = :id";
        entityManager.createNativeQuery(sql).setParameter("status", status).setParameter("id", id).executeUpdate();
    }
}
