package com.viettel.cmsmobilebrandname.mobile.repository.custom;

import com.viettel.smsbrandname.domain.Cp;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MbCpAliasCustomRepositoryImpl implements MbCpAliasCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Cp getInformationCp(Long id) {
        return (Cp) entityManager
            .createQuery("select cp from Cp cp where cp.cpId  = :id")
            .setParameter("id",id)
            .getSingleResult();
    }
}
