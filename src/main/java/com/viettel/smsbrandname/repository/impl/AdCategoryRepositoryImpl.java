package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.domain.AdCategory;
import com.viettel.smsbrandname.repository.AdCategoryCustomRepository;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Repository
public class AdCategoryRepositoryImpl implements AdCategoryCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static String SEARCH_CATEGORY = "from AdCategory where recycle = 1 ";

    private static String SEARCH_COUNT_CATEGORY = "select count (id) from AdCategory where recycle = 1 ";

    @Override
    public List<ComboBean> findAdCategoryByRecycle() {
        StringBuilder sql = new StringBuilder("select ID value,CAT_NAME label from AD_CATEGORY  where RECYCLE = 0 ORDER BY NLSSORT(LOWER(cat_name), 'NLS_SORT=VIETNAMESE') ASC ");
        Query query = entityManager.createNativeQuery(sql.toString());
        return new MaperUtils(query.getResultList()).add("value").add("label").transform(ComboBean.class);
    }

    @Override
    public CommonResponseDTO search(String catCode, String catName, Pageable pageable) {
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        StringBuilder sb = new StringBuilder(SEARCH_CATEGORY);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_CATEGORY);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(catCode)) {
            sb.append(" and lower(catCode) like :catCode");
            sbCount.append(" and lower(catCode) like :catCode");
            params.put("catCode", DataUtil.makeLikeParam(catCode));
        }
        if (!DataUtil.isNullOrEmpty(catName)) {
            sb.append(" and lower(catName) like :catName");
            sbCount.append(" and lower(catName) like :catName");
            params.put("catName", DataUtil.makeLikeParam(catName));
        }
        sb.append(" order by id desc");
        Query query = entityManager.createQuery(sb.toString());
        Query queryCount = entityManager.createQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<AdCategory> dtoList = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        int count = DataUtil.safeToInt(queryCount.getSingleResult());
        responseDTO.setListData(dtoList);
        responseDTO.setCount(count);
        return responseDTO;
    }
}
