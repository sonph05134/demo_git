package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CpGroupSub} and its DTO {@link CpGroupSubDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CpGroupSubMapper extends EntityMapper<CpGroupSubDTO, CpGroupSub> {



    default CpGroupSub fromId(Long id) {
        if (id == null) {
            return null;
        }
        CpGroupSub cpGroupSub = new CpGroupSub();
        cpGroupSub.setId(id);
        return cpGroupSub;
    }
}
