package com.viettel.smsbrandname.repository;


import com.viettel.smsbrandname.domain.DiscountProvince;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountProvinceRepository extends JpaRepository<DiscountProvince, String>, DiscountProvinceRepositoryCustom {

}
