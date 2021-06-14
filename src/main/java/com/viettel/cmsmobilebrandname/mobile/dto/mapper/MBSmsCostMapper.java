package com.viettel.cmsmobilebrandname.mobile.dto.mapper;

import com.viettel.smsbrandname.domain.SMSCost;
import com.viettel.smsbrandname.service.dto.SMSCostDTO;
import com.viettel.smsbrandname.service.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface MBSmsCostMapper extends EntityMapper<SMSCostDTO, SMSCost> {
}
