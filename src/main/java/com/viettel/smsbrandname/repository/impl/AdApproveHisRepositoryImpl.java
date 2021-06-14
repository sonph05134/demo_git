package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.repository.AdApproveHisRepository;
import com.viettel.smsbrandname.service.dto.AdApproveHisDTO;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsSearchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Created by pmvt-os-chc-136 on 12/29/2020.
 */
@Repository
public class AdApproveHisRepositoryImpl implements AdApproveHisRepository{
    @PersistenceContext
    private EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(AdApproveHisRepositoryImpl.class);

    @Override
    public Page<AdApproveHisDTO> findAdApproveHisByProgId(AdApproveHisDTO adApproveHisDTO) {
        List<AdApproveHisDTO> lstVA = new ArrayList<>();
        int total = 0;
        int currentPage = adApproveHisDTO.getCurrentPage();
        int pageSize = adApproveHisDTO.getPageSize();
        try{
        StringBuilder sql =new StringBuilder("select H.ID , H.APPROVER  , H.STATE_BEFORE , H.STATE_AFTER  " +
                ", to_char(H.TIME_CHANGE ,'dd/MM/yyyy') TIME_CHANGE, H.NOTE  , H.PROG_ID  from AD_APPROVE_HIS H  where H.PROG_ID = :progId ");
        String sqlCount = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("progId", adApproveHisDTO.getProgId());
        Query queryCount = entityManager.createNativeQuery(sqlCount);
            queryCount.setParameter("progId", adApproveHisDTO.getProgId());
            total = ((BigDecimal) queryCount.getSingleResult()).intValue();
        query.setFirstResult(currentPage * pageSize);
        query.setMaxResults(pageSize);
        List<Object[]> list = query.getResultList();
        lstVA = adApproveHisDTO.toDto(list);
    } catch (Exception e) {
        logger.error(e.getMessage(), e);
        e.printStackTrace();
    }
        return new PageImpl<>(lstVA, PageRequest.of(currentPage, pageSize), total);
    }
}
