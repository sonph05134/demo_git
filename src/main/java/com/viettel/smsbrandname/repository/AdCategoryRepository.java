package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the AdCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdCategoryRepository extends JpaRepository<AdCategory, Long>, AdCategoryCustomRepository {
    Optional<AdCategory> findFirstByCatCodeAndRecycle(String code, Integer recycle);

    Optional<AdCategory> findFirstByCatNameAndRecycle(String name, Integer recycle);
}
