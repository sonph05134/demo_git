package com.viettel.smsbrandname.service.mapper;

import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.service.dto.CpDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CpMapper extends EntityMapper<CpDTO, Cp> {
}
