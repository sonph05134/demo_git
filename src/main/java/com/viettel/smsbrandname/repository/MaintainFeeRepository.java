package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.MaintainFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface MaintainFeeRepository extends JpaRepository<MaintainFee, Long>, MaintainFeeCustomRepository {

    Page<MaintainFee> findAll(Pageable page);

    Page<MaintainFee> findAllByCurrency(Pageable page, Integer currency);

    Optional<MaintainFee> findByCurrencyAndFeeValueAndIdNot(Integer currency, BigDecimal feeValue, Long id);

    void deleteById(Long id);
}
