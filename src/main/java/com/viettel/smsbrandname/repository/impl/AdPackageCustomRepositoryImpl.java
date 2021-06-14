package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.AdPackage;
import com.viettel.smsbrandname.repository.AdPackageCustomRepository;
import com.viettel.smsbrandname.service.dto.AdPackageDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class AdPackageCustomRepositoryImpl implements AdPackageCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static String SEARCH_PACKAGE = "from AdPackage where 1 = 1 ";

    private static String SEARCH_COUNT_PACKAGE = "select count (id) from AdPackage where 1 = 1 ";

    private static String SEARCH_EXISTED_PACKAGE = "from AdPackage where lower(packageName) =:packageName";

    @Override
    public CommonResponseDTO search(String packageName, Integer type, Pageable pageable) {
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        StringBuilder sb = new StringBuilder(SEARCH_PACKAGE);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_PACKAGE);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(packageName)) {
            sb.append(" and lower(packageName) like :packageName");
            sbCount.append(" and lower(packageName) like :packageName");
            params.put("packageName", DataUtil.makeLikeParam(packageName));
        }
        if (!DataUtil.isNullOrEmpty(type)) {
            sb.append(" and type = :type");
            sbCount.append(" and type = :type");
            params.put("type", type);
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
    public Optional<AdPackage> findFirstByPackageNameAndStatus(String packageName) {
        Query query = entityManager.createQuery(SEARCH_EXISTED_PACKAGE);
        query.setParameter("packageName", !DataUtil.isNullOrEmpty(packageName) ? packageName.toLowerCase() : "");
        AdPackage adPackage = query.getResultList().size() > 0 ? (AdPackage) query.getResultList().get(0) : null;
        return !DataUtil.isNullOrEmpty(adPackage) ? Optional.of(adPackage) : Optional.empty();
    }

    //start sonph 10/04/2021
    @Override
    public Optional<AdPackage> findByNameAndType(String name, String type) {
        StringBuilder str = new StringBuilder("from AdPackage where 1=1 ");
        if (!DataUtil.isNullOrEmpty(name)) {
            str.append(" AND lower(packageName) =:packageName ");
        }
        if (!DataUtil.isNullOrEmpty(type)) {
            str.append(" AND type =:type ");
        }
        Query query = entityManager.createQuery(str.toString());
        if (!DataUtil.isNullOrEmpty(name)) {
            query.setParameter("packageName", name.toLowerCase());
        }
        if (!DataUtil.isNullOrEmpty(type)) {
            query.setParameter("type", DataUtil.safeToInt(type));
        }
        List<AdPackage> lst = query.getResultList();
        return DataUtil.isNullOrEmpty(lst) ? Optional.empty() : Optional.of(lst.get(0));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer changeStatus(Long id, Integer status) {
        try {
            String str = "update AdPackage set status =: status where id =:id";
            Query query = entityManager.createQuery(str);
            query.setParameter("status", status == 0 ? Constants.STATUS_ACTIVE: Constants.STATUS_INACTIVE);
            query.setParameter("id", id);
            int result = query.executeUpdate();
            return result > 0 ? result : null;
        } catch (Exception e) {
            throw e;
        }
    }
    //end sonph 10/04/2021
}
