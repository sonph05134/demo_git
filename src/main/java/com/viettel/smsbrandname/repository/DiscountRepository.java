package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long>, DiscountCustomRepository {

    Page<Discount> findAll(Pageable page);

    Page<Discount> findAllByType(Pageable page, Integer type);
}
