package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmsc;
import com.viettel.smsbrandname.repository.GatewayCustomRepository;
import com.viettel.smsbrandname.repository.SMSCCustomRepository;
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
public class SMSCCustomRepositoryImpl implements SMSCCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CfgSmsc> getAll(PageDTO pageDTO) {
        Integer pageIndex = pageDTO.getCurrentPage();
        Integer pageSize = pageDTO.getPageSize();
        String sql = "from CfgSmsc order by NLSSORT(upper(cfgSmscId),'NLS_SORT=vietnamese') ";
        List<CfgSmsc> lst = entityManager.createQuery(sql).setFirstResult(pageIndex * pageSize).setMaxResults(pageSize).getResultList();
        Query queryCount = entityManager.createQuery("select count(cfgSmscId) " + sql);
        return new PageImpl<>(lst, PageRequest.of(pageIndex,
            pageSize), (Long) queryCount.getSingleResult());
    }

    @Override
    public int checkSmscGateway(String smscId) {
        String sql = "SELECT cfgSmscId from CfgSmscGate where cfgSmscId = :cfgSmscId and status = 1";
        Query query = entityManager.createQuery(sql).setParameter("cfgSmscId", smscId);
        return query.getResultList().size();
    }

    @Override
    @Transactional
    public void changeStatus(String smscId, short status) {
        String sql = "update CfgSmsc set enable = :status where cfgSmscId = :smscId";
        entityManager.createQuery(sql).setParameter("status", status).setParameter("smscId", smscId).executeUpdate();
    }
}
