package com.viettel.cmsmobilebrandname.mobile.service.impl;

import com.viettel.cmsmobilebrandname.mobile.service.MBCpService;
import com.viettel.smsbrandname.commons.ExcelUtils;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.repository.CpRepository;
import com.viettel.smsbrandname.repository.ProvinceUserRepository;
import com.viettel.smsbrandname.service.CpService;
import com.viettel.smsbrandname.service.KeyCloakUserService;
import com.viettel.smsbrandname.service.RoleService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.*;
import com.viettel.smsbrandname.service.impl.CpServiceImpl;
import com.viettel.smsbrandname.service.mapper.CpMapper;
import com.viettel.smsbrandname.service.mapper.ProvinceUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.List;


@Service
public class MBCpServiceImpl  extends CpServiceImpl implements MBCpService{


    @Qualifier("cpServiceImpl")
    @Autowired
    private CpService cpService;

    public MBCpServiceImpl(CpRepository cpRepository, EntityManager entityManager, CpMapper mapper, ProvinceUserRepository provinceUserRepository, ProvinceUsersMapper provinceUsersMapper, ExcelUtils excelUtils, UserService userService, KeyCloakUserService keyCloakUserService, RoleService roleService) {
        super(cpRepository, entityManager, mapper, provinceUserRepository, provinceUsersMapper, excelUtils, userService, keyCloakUserService, roleService);
    }



    public CommonResponseDTO search(CpSearchDTO searchDTO) {
        return null;
    }


    public List<ProvinceDTO> findAllProvince() {
        return null;
    }

    public List<DistrictDTO> findDistrictByProvinceId(Long id) {
        return cpService.findDistrictByProvinceId(id);
    }


    public Cp add(CpDTO cp, MultipartFile attachFile, MultipartFile monthCommissionAttachFile) {
        return cpService.add(cp,attachFile,monthCommissionAttachFile);
    }

    public Boolean checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode) {
        return cpService.checkExistedOrderNoOrCpName(cpName,taxCode,isSaving,cpId,commissionPercentCode);
    }


    public CpDTO checkExistedValue(String cpCode, String cpName, String userName, String wsUsername, String cpCysId) {
        return cpService.checkExistedValue(cpCode,cpName,userName,wsUsername,cpCysId);
    }



}
