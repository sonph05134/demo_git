package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Optional<Partner> getByCodeIgnoreCaseAndIdNot(String code, Long id);

    Optional<Partner> getByNameIgnoreCaseAndIdNot(String name, Long id);

    Optional<Partner> getByCodeIgnoreCase(String code);

    Optional<Partner> getByNameIgnoreCase(String name);
}
