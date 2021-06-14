package com.viettel.cmsmobilebrandname.mobile.service;

import com.viettel.smsbrandname.service.SmsCostService;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface MBSmsCostService extends SmsCostService {

    boolean checkPriceLevelId(Long PriceLevel);
    SMSCostDTO searchById(@PathVariable Long id);
    boolean update(SMSCostDTO smsCostDTO);
    boolean checkExistCpAliasGroup(SMSCostDTO smsCostDTO);
}
