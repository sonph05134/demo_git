package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import org.springframework.data.domain.Page;

public interface SmsCostService {
    Page<SMSCost> getByCondition(SMSCostDTO smsCostDTO);

    void save(SMSCost partner);

    void delete(Long id);
    boolean smsCostExistUpdate(SMSCostDTO smsCostDTO);
}
