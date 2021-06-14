package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.repository.SmsCostCustomRepository;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class SmsCostCustomRepositoryImpl implements SmsCostCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<SMSCost> getByCondition(SMSCostDTO smsCost) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT cp_alias_group_id cpAliasGroupId, group_id groupId, ")
            .append("group_name groupName, price price, type type, price_level priceLevel, (price_usd/:usdLength) priceUsd, ")
            .append("alias_group_type aliasGroupType ")
            .append("FROM cp_alias_group WHERE 1=1 ");
        HashMap<String, Object> hashMap = new HashMap<>();
        if (smsCost.getGroupName() != null) {
            sql.append("AND LOWER(group_name) LIKE CONCAT( '%',  CONCAT( :name , '%') ) ");
            hashMap.put("name", smsCost.getGroupName().trim().toLowerCase());
        }
        if (smsCost.getType() != null && smsCost.getType() != -1) {
            sql.append("AND type = :type ");
            hashMap.put("type", smsCost.getType());
        }
        if (smsCost.getAliasGroupType() != null && smsCost.getAliasGroupType() != -1) {
            sql.append("AND alias_group_type = :groupType ");
            hashMap.put("groupType", smsCost.getAliasGroupType());
        }
        hashMap.put("usdLength", Constants.USD_LENGTH);
        sql.append("ORDER BY group_id ASC, NLSSORT(LOWER(group_name), 'NLS_SORT=VIETNAMESE') ASC, type ASC, price_level ASC");
        Query query = entityManager.createNativeQuery(sql.toString());
        Integer pageIndex = smsCost.getCurrentPage();
        Integer pageSize = smsCost.getPageSize();
        hashMap.forEach(query::setParameter);
        List<Object[]> lst = query.setFirstResult(pageIndex * pageSize).setMaxResults(pageSize).getResultList();
        MaperUtils maper = new MaperUtils(lst);
        maper.add("cpAliasGroupId").add("groupId").add("groupName").add("price").add("type")
            .add("priceLevel").add("priceUsd").add("aliasGroupType");
        Query queryCount = entityManager.createNativeQuery("select count(*) from (" + sql.toString() + ")");
        hashMap.forEach(queryCount::setParameter);
        return new PageImpl<>(maper.transform(SMSCost.class), PageRequest.of(pageIndex,
            pageSize), ((BigDecimal) queryCount.getSingleResult()).longValue());
    }

    public boolean smsCostExist(SMSCost smsCost) {
        HashMap<String, Object> hmap = new HashMap<>();
        hmap.put("groupId", smsCost.getGroupId());
        hmap.put("type", smsCost.getType());
        hmap.put("aliasGroupType", smsCost.getAliasGroupType());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(cpAliasGroupId) FROM SMSCost ")
            .append("WHERE groupId = :groupId AND type = :type ")
            .append("AND aliasGroupType = :aliasGroupType ");
        if (smsCost.getPriceLevel() != null && !"".equals(smsCost.getPriceLevel())) {
            sql.append("AND priceLevel = :priceLevel ");
            hmap.put("priceLevel", smsCost.getPriceLevel());
        } else {
            sql.append("AND priceLevel IS NULL ");
        }
        if (smsCost.getCpAliasGroupId() != null) {
            sql.append("AND cpAliasGroupId != :cpAliasGroupId");
            hmap.put("cpAliasGroupId", smsCost.getCpAliasGroupId());
        }
        Query query = entityManager.createQuery(sql.toString());
        hmap.forEach(query::setParameter);
        return ((Long) query.getSingleResult()).intValue() > 0;
    }

    public boolean smsCostExistUpdate(SMSCostDTO smsCostDTO) {
        HashMap<String, Object> hmap = new HashMap<>();
        hmap.put("groupId", smsCostDTO.getGroupId());
        hmap.put("type", smsCostDTO.getType());
        hmap.put("aliasGroupType", smsCostDTO.getAliasGroupType());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(cpAliasGroupId) FROM SMSCost ")
            .append("WHERE groupId = :groupId AND type = :type ")
            .append("AND aliasGroupType = :aliasGroupType ");
        if (smsCostDTO.getPriceLevel() != null && smsCostDTO.getPriceLevel() != -1) {
            sql.append("AND priceLevel = :priceLevel ");
            hmap.put("priceLevel", smsCostDTO.getPriceLevel());
        } else {
            sql.append("AND priceLevel IS NULL ");
        }
        if (smsCostDTO.getCpAliasGroupId() != null) {
            sql.append("AND cpAliasGroupId != :cpAliasGroupId");
            hmap.put("cpAliasGroupId", smsCostDTO.getCpAliasGroupId());
        }
        Query query = entityManager.createQuery(sql.toString());
        hmap.forEach(query::setParameter);
        Object singleResult = query.getSingleResult();
        Number count = ((Number) singleResult);

        return count.intValue() > 0;
    }

}
