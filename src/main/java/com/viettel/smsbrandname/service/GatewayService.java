package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface GatewayService {

    Page<CfgGate> search(PageDTO page);

    void save(CfgGate cfgGate, boolean isUpdate);

    void delete(String id);

    void changeStatus(CfgGate cfgGate, int status);
}
