package com.viettel.cmsmobilebrandname.mobile.dto.mapper;

import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AliasCostMapper extends EntityMapper<AliasCostDTO,AliasCost> {
}
