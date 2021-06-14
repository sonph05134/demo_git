package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IsdnCustomRepository {
    CommonResponseDTO search(String userName, String msisdn, Pageable pageable);

    List<String> getLstPrefix();

    Boolean checkExisted(String userName, Long id);
}
