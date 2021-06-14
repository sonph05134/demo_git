package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.domain.AdminMsisdn;
import com.viettel.smsbrandname.repository.IsdnCustomRepository;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
public class IsdnCustomRepositoryImpl implements IsdnCustomRepository {

    private final EntityManager entityManager;

    private static String SEARCH_ISDN = "from AdminMsisdn where status = 1 ";

    private static String SEARCH_COUNT_ISDN = "select count (id) from AdminMsisdn where status = 1 ";

    public IsdnCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CommonResponseDTO search(String userName, String msisdn, Pageable pageable) {
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        StringBuilder sb = new StringBuilder(SEARCH_ISDN);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_ISDN);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(userName)) {
            sb.append(" and lower(userName) like :userName");
            sbCount.append(" and lower(userName) like :userName");
            params.put("userName", DataUtil.makeLikeParam(userName));
        }
        if (!DataUtil.isNullOrEmpty(msisdn)) {
            sb.append(" and msisdn like :msisdn");
            sbCount.append(" and msisdn like :msisdn");
            params.put("msisdn", DataUtil.makeLikeParam(msisdn));
        }
        sb.append(" order by id desc");
        Query query = entityManager.createQuery(sb.toString());
        Query queryCount = entityManager.createQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<AdminMsisdn> dtoList = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        int count = DataUtil.safeToInt(queryCount.getSingleResult());
        responseDTO.setListData(dtoList);
        responseDTO.setCount(count);
        return responseDTO;
    }

    @Override
    public List<String> getLstPrefix() {
        List<String> lstPrefix = new ArrayList<>();
        String sql = "SELECT prefix_detail FROM telco";
        Query query = entityManager.createNativeQuery(sql);
        List<String> lstResult = query.getResultList();
        if (lstResult != null) {
            for (String list : lstResult) {
                List<String> lstTemp;
                if (!DataUtil.isNullOrEmpty(list)) {
                    lstTemp = Arrays.asList(list.split(","));
                    for (String item : lstTemp) {
                        lstPrefix.add(item);
                    }
                }
            }
        }
        return lstPrefix;
    }

    @Override
    public Boolean checkExisted(String userName, Long id) {
        String sql = "SELECT COUNT(*) FROM admin_msisdn "
            + "WHERE user_name = :userName and status = 1";
        if (!DataUtil.isNullOrEmpty(id)) {
            sql += " AND id != :id ";
        }
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userName", userName);
        if (!DataUtil.isNullOrEmpty(id)) {
            query.setParameter("id", id);
        }
        return !DataUtil.isNullOrEmpty(query.getSingleResult()) ? DataUtil.safeToInt(query.getSingleResult()) > 0 : false;
    }
}
