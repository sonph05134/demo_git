package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import org.springframework.data.domain.Page;

public interface SmsCostCustomRepository {

    Page<SMSCost> getByCondition(SMSCostDTO smsCost);

    boolean smsCostExist(SMSCost smsCost);

     boolean smsCostExistUpdate(SMSCostDTO smsCostDTO);
}
