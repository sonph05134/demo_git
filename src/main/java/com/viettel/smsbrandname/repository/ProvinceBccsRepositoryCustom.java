package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.ProvinceBccs;

import java.util.List;

public interface ProvinceBccsRepositoryCustom {

    List<Object[]> onSearch(Long provinceId, Integer start, Integer limit, Double timeZone);

    List<Object[]> getLstProvinceNotInProvinceBccs();

    boolean isExistsDataProvinceBccs(ProvinceBccs provinceBCCS);

    boolean isExistProvinceSMS(Long provinceId);

    boolean isMappedDataProvinceBccs(Long provinceId, Long id);

    boolean deleteProvinceBccs(Long id);
}
