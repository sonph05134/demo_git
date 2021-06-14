package com.viettel.cmsmobilebrandname.mobile.repository;

import com.viettel.cmsmobilebrandname.mobile.dto.FilterDTOMB;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.domain.CfgFilterEntity;
import com.viettel.smsbrandname.domain.CfgFilterEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class FilterSpecification implements Specification<CfgFilterEntity> {

    private FilterDTOMB filterDTOMB;

   public FilterSpecification(FilterDTOMB filterDTOMB){
       this.filterDTOMB = filterDTOMB;
   }

    @Override
    public Predicate toPredicate(Root<CfgFilterEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = null;
        if(filterDTOMB != null){
            predicates = new ArrayList<>();
            String keywordLower = "";
            if(!DataUtil.isNullOrEmpty(filterDTOMB.getKeyword())) {
                keywordLower  = filterDTOMB.getKeyword().toLowerCase().trim();
            }
            if (filterDTOMB.getStatus() != -1) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(CfgFilterEntity_.KEYWORD)), "%" + keywordLower + "%"));
                predicates.add(criteriaBuilder.equal(root.get(CfgFilterEntity_.STATUS), filterDTOMB.getStatus()));
            } else {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(CfgFilterEntity_.KEYWORD)), "%" + keywordLower + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }

}
