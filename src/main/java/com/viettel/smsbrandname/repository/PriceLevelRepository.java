package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.PriceLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceLevelRepository extends JpaRepository<PriceLevel, Long>, PriceLevelRepositoryCustom {

   @Query(value = "select pl.priceLevelId  from PriceLevel pl where pl.priceLevelId = ?1")
   Long checkPriceLevel(Long priceLevelId);

}
