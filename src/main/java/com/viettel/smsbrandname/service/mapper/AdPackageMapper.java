package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.AdPackageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdPackage} and its DTO {@link AdPackageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdPackageMapper extends EntityMapper<AdPackageDTO, AdPackage> {



    default AdPackage fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdPackage adPackage = new AdPackage();
        adPackage.setId(id);
        return adPackage;
    }
}
