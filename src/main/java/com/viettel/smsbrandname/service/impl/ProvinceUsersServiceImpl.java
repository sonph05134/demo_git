package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.service.ProvinceUsersService;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvinceUsersServiceImpl implements ProvinceUsersService {
    @Override
    public Optional<ProvinceUsersDTO> getByUserNameAndStatus(String userName, Integer status) {
        return Optional.empty();
    }
}
