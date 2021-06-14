package com.viettel.cmsmobilebrandname.mobile.service;

import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.smsbrandname.service.CpAliasService;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;
import com.viettel.smsbrandname.service.dto.CpDTO;

public interface MbCpAliasService extends CpAliasService {

   boolean minusFees(Long cpId, Long cpAliasId) throws Exception;
    CpDTO getInformationCp(Long id);

    CpAliasDTO checkExistCpAliasByIdAndCpAliasId(Long cpId, Long cpAliasId);

    AliasCostDTO getInformationAliasCost(String teLcoAlias, Long aliasType);
}
