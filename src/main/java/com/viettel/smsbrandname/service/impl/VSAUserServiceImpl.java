package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.RandomUtils;
import com.viettel.smsbrandname.service.VSAUserService;
import com.viettel.smsbrandname.service.dto.VSAUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VSAUserServiceImpl implements VSAUserService {

    private Logger logger = LoggerFactory.getLogger(VSAUserServiceImpl.class);

    @Override
    public VSAUserDTO save(VSAUserDTO dto) {
        logger.info("Saving VSAUserDTO with params:[cpId: {},userName: {},password: {},status: {}]", dto.getCpId(), dto.getUserName(), dto.getPassword(), dto.getStatus());
        return null;
    }

    @Override
    public List<VSAUserDTO> findAll() {
        List<VSAUserDTO> dtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            VSAUserDTO dto = new VSAUserDTO();
            dto.setUserName("smsbrand_sample" + i);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
