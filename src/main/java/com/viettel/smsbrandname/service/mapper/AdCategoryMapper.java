package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.AdCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdCategory} and its DTO {@link AdCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdCategoryMapper extends EntityMapper<AdCategoryDTO, AdCategory> {



    default AdCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdCategory adCategory = new AdCategory();
        adCategory.setId(id);
        return adCategory;
    }
}
