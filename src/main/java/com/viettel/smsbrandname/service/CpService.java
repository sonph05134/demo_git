package com.viettel.smsbrandname.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.service.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface CpService {

    CommonResponseDTO search(CpSearchDTO searchDTO);

    List<ProvinceDTO> findAllProvince();

    List<DistrictDTO> findDistrictByProvinceId(Long id);

    Cp add(CpDTO cp, MultipartFile attachFile, MultipartFile monthCommissionAttachFile);

    List<CommissionPercentCodeDTO> getAllCommissionPercentCode();

    CpDTO checkExistedValue(String cpCode, String cpName, String userName, String wsUsername, String cpCysId);

    CpDTO findCpById(Long id);

    Cp update(CpDTO cp, MultipartFile attachFile, MultipartFile monthCommissionAttachFile, Principal principal);

    CommonResponseDTO searchProvinceUser(ProvinceUserSearchDTO searchDTO);

    CommonResponseDTO searchStaff(StaffSearchDTO searchDTO);

    void deleteCp(Long id,Boolean isSaving);

    List<AdjustCommissionHisSearchDTO> searchAdjustCommissionHis(Long cpId);

    ByteArrayInputStream downloadAttachFile(String fileName) throws IOException;

    Boolean checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode);

    List<CpDTO> findCpByUserLogin();

    List<ComboBean> findByInput();

    ByteArrayInputStream export(CpSearchDTO dto);

    ProvinceUsersDTO getLoginPermission(String username);

    Optional<Cp> findFirstByUserNameAndStatus(String userName);
}
