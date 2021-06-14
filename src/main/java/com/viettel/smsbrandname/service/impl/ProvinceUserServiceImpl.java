package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.repository.ProvinceUserRepository;
import com.viettel.smsbrandname.service.ProvinceUserService;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProvinceUserServiceImpl implements ProvinceUserService {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceUserServiceImpl.class);
    @Autowired
    ProvinceUserRepository provinceUserRepository;

    @Override
    public ApiResponseDTO onSearch(Long provinceId, String username, String email, String keyWord, Integer start, Integer limit) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        List<Object[]> list = provinceUserRepository.onSearch(provinceId, username, email, keyWord, start, limit);
        int total = provinceUserRepository.onSearch(provinceId, username, email, keyWord, 0, 0).size();
        apiResponseDTO.setData(ProvinceUsersDTO.toDto(list));
        apiResponseDTO.setTotalRow(total);
        return apiResponseDTO;
    }

    @Override
    @Transactional
    public void saveOrEdit(ProvinceUsersDTO provinceUsersDTO) {
        if (provinceUserRepository.isExistsVsaUser(provinceUsersDTO.getUsername())) {
            throw new BusinessException(ErrorCodeResponse.ERR_EXIST, Translator.toLocale("username"));
        }

        if (Constants.ACCOUNT_LEVEL_DISTRICT.equals(provinceUsersDTO.getIsParent()) && Constants.ACCOUNT_TYPE_FINANCE.equals(provinceUsersDTO.getUserType())) {
            throw new BusinessException(ErrorCodeResponse.INVALID_ACCOUNT_TYPE_FINANCE);
        }

        if (!provinceUserRepository.isValidAccountLevel(provinceUsersDTO.getUserType(), provinceUsersDTO.getProvinceId())) {
            throw new BusinessException(ErrorCodeResponse.INVALID_ACCOUNT_LEVEL);
        }

        ProvinceUsers provinceUsers = new ProvinceUsers();
        provinceUsers.setId(provinceUsersDTO.getId());
        provinceUsers.setUserName(provinceUsersDTO.getUsername());
        provinceUsers.setUserType(provinceUsersDTO.getUserType());
        provinceUsers.setProvinceId(provinceUsersDTO.getProvinceId());
        provinceUsers.setIsParent(provinceUsersDTO.getIsParent());
        provinceUsers.setStatus(provinceUsersDTO.getStatus());

        provinceUserRepository.save(provinceUsers);
        // insert user
        // insert role
    }

    @Override
    @Transactional
    public void activeOrInactive(ProvinceUsersDTO provinceUsersDTO) {
        if (provinceUserRepository.updateStatus(provinceUsersDTO.getId(), provinceUsersDTO.getStatus())) {
            provinceUserRepository.updateStatusUser(provinceUsersDTO.getUsername(), provinceUsersDTO.getStatus());
        }
    }
}
