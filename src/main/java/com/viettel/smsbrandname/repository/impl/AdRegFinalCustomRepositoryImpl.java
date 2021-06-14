package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.domain.AdRegFinal;
import com.viettel.smsbrandname.repository.AdRegFinalCustomRepository;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class AdRegFinalCustomRepositoryImpl implements AdRegFinalCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static String SEARCH_AD_REG = "from AdRegFinal where 1 = 1 ";

    private static String SEARCH_COUNT_AD_REG = "select count (id) from AdRegFinal where 1 = 1 ";

    private static String SEARCH_EXISTED_AD_REG = "from AdRegFinal where msisdn =:msisdn";

    @Override
    public CommonResponseDTO search(Long msisdn, Pageable pageable) {
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        StringBuilder sb = new StringBuilder(SEARCH_AD_REG);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_AD_REG);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(msisdn)) {
            sb.append(" and to_char(msisdn) like :msisdn");
            sbCount.append(" and to_char(msisdn) like :msisdn");
            params.put("msisdn", DataUtil.makeLikeParam(msisdn.toString()));
        }
        sb.append(" order by id desc");
        Query query = entityManager.createQuery(sb.toString());
        Query queryCount = entityManager.createQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<AdRegFinal> dtoList = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        int count = DataUtil.safeToInt(queryCount.getSingleResult());
        responseDTO.setListData(dtoList);
        responseDTO.setCount(count);
        return responseDTO;
    }

    @Override
    public Optional<AdRegFinal> findByMsisdn(Long msisdn) {
        Query query = entityManager.createQuery(SEARCH_EXISTED_AD_REG);
        query.setParameter("msisdn", !DataUtil.isNullOrEmpty(msisdn) ? msisdn : "");
        AdRegFinal adRegFinal = query.getResultList().size() > 0 ? (AdRegFinal) query.getResultList().get(0) : null;
        return !DataUtil.isNullOrEmpty(adRegFinal) ? Optional.of(adRegFinal) : Optional.empty();
    }
}
