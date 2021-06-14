package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.MtDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mt} and its DTO {@link MtDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MtMapper extends EntityMapper<MtDTO, Mt> {



    default Mt fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mt mt = new Mt();
        mt.setMtId(id);
        return mt;
    }
}
