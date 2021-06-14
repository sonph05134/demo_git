package com.viettel.cmsmobilebrandname.mobile.dto.mapper;

import com.viettel.cmsmobilebrandname.mobile.dto.FilterDTOMB;
import com.viettel.smsbrandname.domain.CfgFilterEntity;
import com.viettel.smsbrandname.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MBFilterMapper extends EntityMapper<FilterDTOMB, CfgFilterEntity> {

}
