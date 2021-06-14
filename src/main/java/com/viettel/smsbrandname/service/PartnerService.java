package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Partner;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface PartnerService {

    Page<Partner> search(PageDTO page);

    void save(Partner partner);

    void delete(Long id);
}
