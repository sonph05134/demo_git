package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.AdRegFinalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdRegFinal} and its DTO {@link AdRegFinalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdRegFinalMapper extends EntityMapper<AdRegFinalDTO, AdRegFinal> {



    default AdRegFinal fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdRegFinal adRegFinal = new AdRegFinal();
        adRegFinal.setId(id);
        return adRegFinal;
    }
}
