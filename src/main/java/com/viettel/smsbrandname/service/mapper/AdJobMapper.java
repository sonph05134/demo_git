package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.AdJobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdJob} and its DTO {@link AdJobDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdJobMapper extends EntityMapper<AdJobDTO, AdJob> {



    default AdJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdJob adJob = new AdJob();
        adJob.setId(id);
        return adJob;
    }
}
