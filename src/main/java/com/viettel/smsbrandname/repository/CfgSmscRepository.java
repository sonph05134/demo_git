package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CfgSmsc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CfgSmscRepository extends JpaRepository<CfgSmsc, String>, SMSCCustomRepository {

    Optional<CfgSmsc> getByCfgSmscIdIgnoreCase(String smscId);

    Optional<CfgSmsc> getByCfgSmscId(String smscId);

    void deleteByCfgSmscId(String smscId);
}
