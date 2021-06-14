package com.viettel.smsbrandname.repository;


import com.viettel.smsbrandname.domain.CpAlias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CPBrandRepository extends JpaRepository<CpAlias, Long>, CPBrandRepositoryCustom {

}
