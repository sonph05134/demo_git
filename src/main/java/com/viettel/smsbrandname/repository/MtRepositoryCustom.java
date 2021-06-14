package com.viettel.smsbrandname.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MtRepositoryCustom {
    Page<Object[]> search(String fromDate, String toDate, String receiver, Long cpId, String sender, Long aliasType, Long telcoId, Pageable pageable);
}
