package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdPackage;

import com.viettel.smsbrandname.service.dto.AdPackageDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the AdPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdPackageRepository extends JpaRepository<AdPackage, Long>, AdPackageCustomRepository {
}
