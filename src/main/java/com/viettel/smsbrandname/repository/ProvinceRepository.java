package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Long>, ProvinceRepositoryCustom {
    List<Province> findAllByUserNameContaining(String userName);
}
