package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.repository.GatewayCustomRepository;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GatewayCustomRepositoryImpl implements GatewayCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CfgGate> getAll(PageDTO pageDTO) {
        Integer pageIndex = pageDTO.getCurrentPage();
        Integer pageSize = pageDTO.getPageSize();
        String sql = "from CfgGate order by NLSSORT(upper(cfgGateId),'NLS_SORT=vietnamese') ";
        List<CfgGate> lst = entityManager.createQuery(sql).setFirstResult(pageIndex * pageSize).setMaxResults(pageSize).getResultList();
        Query queryCount = entityManager.createQuery("select count(cfgGateId) " + sql);
        return new PageImpl<>(lst, PageRequest.of(pageIndex,
            pageSize), (Long) queryCount.getSingleResult());
    }

    @Override
    public int checkSmscGateway(String gateId) {
        String sql = "select cfg_gate_id from cfg_smsc_gate where cfg_gate_id = :gateId and status = 1";
        Query query = entityManager.createNativeQuery(sql).setParameter("gateId", gateId);
        return query.getResultList().size();
    }

    @Override
    @Transactional
    public void changeStatus(String gateId, int status) {
        String sql = "update CfgGate set status = :status where cfgGateId = :gateId";
        entityManager.createQuery(sql).setParameter("status", status).setParameter("gateId", gateId).executeUpdate();
    }
}
