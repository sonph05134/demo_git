package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CfgGate;
import com.viettel.smsbrandname.domain.CfgSmsc;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface SMSCCustomRepository {

    Page<CfgSmsc> getAll(PageDTO pageDTO);

    int checkSmscGateway(String gateId);

    void changeStatus(String smscId, short status);
}
