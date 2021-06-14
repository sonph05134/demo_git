package com.viettel.cmsmobilebrandname.mobile.repository.custom;

import com.viettel.cmsmobilebrandname.mobile.repository.custom.MBSmsCostCustomRepository;
import com.viettel.smsbrandname.domain.PriceLevel;
import com.viettel.smsbrandname.domain.PriceLevel_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MBSmsCostCustomRepositoryImpl implements MBSmsCostCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

}
