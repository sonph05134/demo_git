package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.ProvinceBccs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ProvinceBccs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvinceBccsRepository extends JpaRepository<ProvinceBccs, Long>, ProvinceBccsRepositoryCustom {
}
