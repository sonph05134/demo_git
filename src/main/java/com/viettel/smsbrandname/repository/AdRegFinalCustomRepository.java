package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdRegFinal;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdRegFinalCustomRepository {
    CommonResponseDTO search(Long msisdn, Pageable pageable);

    Optional<AdRegFinal> findByMsisdn(Long msisdn);

}
