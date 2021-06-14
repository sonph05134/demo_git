package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdJob;

import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdJobRepository extends JpaRepository<AdJob, Long>, AdJobCustomRepository {
}
