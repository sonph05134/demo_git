package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.VSAUserDTO;

import java.util.List;

public interface VSAUserService {
    VSAUserDTO save(VSAUserDTO dto);

    List<VSAUserDTO> findAll();
}
