package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.SysConfig;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;

import java.sql.SQLException;

public interface SysConfigService {

    ApiResponseDTO onSearch(String keyWord, Long module, Long deleted,
                            Integer start, Integer limit) throws SQLException;

    void saveOrEdit(SysConfig sysConfig, String username);

    void activeOrInactive(SysConfig sysConfig);
}
