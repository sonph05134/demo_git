package com.viettel.smsbrandname.service.mapper;


import com.viettel.smsbrandname.domain.*;
import com.viettel.smsbrandname.service.dto.TransLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransLog} and its DTO {@link TransLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransLogMapper extends EntityMapper<TransLogDTO, TransLog> {



    default TransLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransLog transLog = new TransLog();
        transLog.setTransId(id);
        return transLog;
    }
}
