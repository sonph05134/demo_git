package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Province;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;

public interface ProvinceService {

    ApiResponseDTO onSearch(Long provinceId, Integer start, Integer limit, Double timeZone);

    void saveOrEdit(Province province, String username);

    void delete(Province province, String userName);

}
