package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.domain.AdJob;
import com.viettel.smsbrandname.domain.AdPackage;
import com.viettel.smsbrandname.repository.AdJobCustomRepository;
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
public class AdJobCustomRepositoryImpl implements AdJobCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static String SEARCH_JOB = "from AdJob where 1 = 1 ";

    private static String SEARCH_COUNT_JOB = "select count (id) from AdJob where 1 = 1 ";

    private static String SEARCH_EXISTED_JOB = "from AdJob where lower(jobName) =:jobName";

    @Override
    public CommonResponseDTO search(String jobName, Pageable pageable) {
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        StringBuilder sb = new StringBuilder(SEARCH_JOB);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_JOB);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(jobName)) {
            sb.append(" and lower(jobName) like :jobName");
            sbCount.append(" and lower(jobName) like :jobName");
            params.put("jobName", DataUtil.makeLikeParam(jobName));
        }
        sb.append(" order by id desc");
        Query query = entityManager.createQuery(sb.toString());
        Query queryCount = entityManager.createQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<AdPackage> dtoList = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        int count = DataUtil.safeToInt(queryCount.getSingleResult());
        responseDTO.setListData(dtoList);
        responseDTO.setCount(count);
        return responseDTO;
    }

    @Override
    public Optional<AdJob> findByName(String jobName) {
        Query query = entityManager.createQuery(SEARCH_EXISTED_JOB);
        query.setParameter("jobName", !DataUtil.isNullOrEmpty(jobName) ? jobName.toLowerCase() : "");
        AdJob adJob = query.getResultList().size() > 0 ? (AdJob) query.getResultList().get(0) : null;
        return !DataUtil.isNullOrEmpty(adJob) ? Optional.of(adJob) : Optional.empty();
    }
}
