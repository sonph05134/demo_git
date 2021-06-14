package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdJob;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdJobCustomRepository {
    CommonResponseDTO search(String jobName, Pageable pageable);

    Optional<AdJob> findByName(String jobName);
}
