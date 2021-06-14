package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmsc;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface SMSCService {

    Page<CfgSmsc> search(PageDTO page);

    void save(CfgSmsc cfgSmsc, boolean isUpdate);

    void delete(String id);

    void changeStatus(CfgSmsc cfgSmsc, short status);
}
