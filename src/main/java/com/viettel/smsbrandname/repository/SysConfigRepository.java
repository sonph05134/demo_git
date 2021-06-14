package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysConfigRepository extends JpaRepository<SysConfig, Long>, SysConfigRepositoryCustom {
}
