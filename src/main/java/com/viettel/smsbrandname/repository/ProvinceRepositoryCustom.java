package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.Province;

import java.util.List;

public interface ProvinceRepositoryCustom {

    List<Object[]> onSearch(Long provinceId, Integer start, Integer limit, Double timeZone);

    boolean isExistsDataProvinceName(Province province);

    boolean isExistsDataProvinceCode(Province province);

    void insertHistoryTable(String tableName, String rowId, String userName, String values, String action);

    boolean isExistsCPWithProvince(Long provinceId);

    boolean isExistData(Long provinceId);

    boolean isExitDataBCCS(Long id);

}
