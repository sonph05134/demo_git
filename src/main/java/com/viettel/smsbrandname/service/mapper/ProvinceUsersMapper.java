package com.viettel.smsbrandname.service.mapper;

import com.viettel.smsbrandname.domain.CpGroup;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.service.dto.CpGroupDTO;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProvinceUsersMapper extends EntityMapper<ProvinceUsersDTO, ProvinceUsers> {
}
