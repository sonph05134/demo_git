package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Telco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelcoRepository extends JpaRepository<Telco, Long> {

    Page<Telco> findAll(Pageable page);
    Optional<Telco> getByTelcoCodeIgnoreCaseAndIdNot(String telcoCode, Long id);
    Optional<Telco> getByTelcoCodeIgnoreCase(String telcoCode);
    Optional<Telco> getByTelcoNameIgnoreCaseAndIdNot(String telcoName, Long id);
    Optional<Telco> getByTelcoNameIgnoreCase(String telcoName);
}
