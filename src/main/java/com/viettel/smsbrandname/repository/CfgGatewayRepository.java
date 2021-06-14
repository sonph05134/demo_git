package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CfgGate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CfgGatewayRepository extends JpaRepository<CfgGate, String>, GatewayCustomRepository {

    Optional<CfgGate> getByCfgGateId(String gateId);

    void deleteByCfgGateId(String id);
}
