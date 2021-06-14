package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.SysConfig;

import java.util.List;

public interface SysConfigRepositoryCustom {

    List<Object[]> onSearch(String keyWord, Long module, Long deleted,
                                  Integer start, Integer limit);

    boolean isExistsData(SysConfig sysConfig);

    void updateDeleted(SysConfig sysConfig);
}
