package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.CfgSmscGate;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface CfgSmscGateService {

    Page<CfgSmscGate> getAll(PageDTO pageDTO);

    void save(CfgSmscGate cfgSmscGate);

    void delete(Long id);

    void changeStatus(CfgSmscGate cfgSmscGate, int status);
}
