package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CpAlias;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CpAlias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CpAliasRepository extends JpaRepository<CpAlias, Long> {

    @Query(value = "select c from CpAlias c where c.status <> 2 and (:cpId is null or c.cpId =:cpId) ORDER BY LOWER(c.cpAlias) ASC ")
    List<CpAlias> searchBrandName(@Param("cpId") Long cpId);
}
