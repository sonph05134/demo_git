package com.viettel.smsbrandname.service;

import com.viettel.cmsmobilebrandname.mobile.dto.AliasCostDTO;
import com.viettel.smsbrandname.domain.AliasCost;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;

public interface AliasCostService {

    AliasCostDTO getInformationAliasCost(String teLcoAlias, Long aliasType);

    ApiResponseDTO onSearch(String telco, Integer aliasType, Integer limit, Integer start);

    void saveOrEdit(AliasCost aliasCost);

    void delete(Long aliasCostId);
}
