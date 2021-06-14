package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;

public interface ProvinceUserService {

    ApiResponseDTO onSearch(Long provinceId, String username,
                            String email, String keyWord,
                            Integer start, Integer limit);

    void saveOrEdit(ProvinceUsersDTO provinceUsersDTO);

    void activeOrInactive(ProvinceUsersDTO provinceUsersDTO);

}
