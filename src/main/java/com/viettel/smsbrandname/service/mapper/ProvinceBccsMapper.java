package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.ProvinceBccsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProvinceBccs} and its DTO {@link ProvinceBccsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProvinceBccsMapper extends EntityMapper<ProvinceBccsDTO, ProvinceBccs> {



    default ProvinceBccs fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProvinceBccs provinceBccs = new ProvinceBccs();
        provinceBccs.setId(id);
        return provinceBccs;
    }
}
