package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CfgSmscGate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CfgSmscGateRepository extends JpaRepository<CfgSmscGate, Long>, SmscGateCustomRepository {

    void deleteByAndCfgSmscGateId(Long id);
}
