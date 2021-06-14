package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdRegFinal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdRegFinal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdRegFinalRepository extends JpaRepository<AdRegFinal, Long>, AdRegFinalCustomRepository {
}
