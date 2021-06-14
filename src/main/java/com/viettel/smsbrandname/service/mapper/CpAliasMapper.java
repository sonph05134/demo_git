package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CpAlias} and its DTO {@link CpAliasDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CpAliasMapper extends EntityMapper<CpAliasDTO, CpAlias> {



    default CpAlias fromId(Long id) {
        if (id == null) {
            return null;
        }
        CpAlias cpAlias = new CpAlias();
        cpAlias.setCpAliasId(id);
        return cpAlias;
    }
}
