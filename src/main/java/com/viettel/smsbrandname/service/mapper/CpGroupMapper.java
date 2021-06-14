package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.CpGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CpGroup} and its DTO {@link CpGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CpGroupMapper extends EntityMapper<CpGroupDTO, CpGroup> {



    default CpGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CpGroup cpGroup = new CpGroup();
        cpGroup.setId(id);
        return cpGroup;
    }
}
