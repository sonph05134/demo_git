package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.SMSCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCostRepository extends JpaRepository<SMSCost, Long>, SmsCostCustomRepository {
    void deleteByCpAliasGroupId(Long id);
}
