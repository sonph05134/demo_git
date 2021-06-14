package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;

import java.util.Optional;

public interface ProvinceUsersService {
    Optional<ProvinceUsersDTO> getByUserNameAndStatus(String userName, Integer status);
}
