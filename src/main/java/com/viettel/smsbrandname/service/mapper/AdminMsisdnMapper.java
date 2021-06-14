package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.AdminMsisdnDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdminMsisdn} and its DTO {@link AdminMsisdnDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdminMsisdnMapper extends EntityMapper<AdminMsisdnDTO, AdminMsisdn> {



    default AdminMsisdn fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdminMsisdn adminMsisdn = new AdminMsisdn();
        adminMsisdn.setId(id);
        return adminMsisdn;
    }
}
