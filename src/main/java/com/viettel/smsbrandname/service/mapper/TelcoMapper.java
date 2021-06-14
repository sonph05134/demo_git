package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.TelcoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telco} and its DTO {@link TelcoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TelcoMapper extends EntityMapper<TelcoDTO, Telco> {



    default Telco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telco telco = new Telco();
        telco.setId(id);
        return telco;
    }
}
