package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CpCustomRepository {
    List<ComboBean> findAllCpPreProvince(String currency, Long status, Optional<ProvinceUsers> optional);

    Page<VirtualAccountDTO> findByCondition(VirtualAccountDTO virtualAccount, ProvinceUsersDTO optional, boolean isExport);

    boolean isFirtTrans(Long cpId, Long balanceId);

    CommonResponseDTO search(CpSearchDTO searchDTO);

    List<ProvinceDTO> findAllProvince();

    List<DistrictDTO> findDistrictByProvinceId(Long id);

    List<CommissionPercentCodeDTO> getAllCommissionPercentCode();

    CpDTO checkExistedValue(String cpCode, String cpName, String userName, String wsUsername, String cpCysId);

    CommonResponseDTO searchProvinceUser(ProvinceUserSearchDTO searchDTO);

    CommonResponseDTO searchStaff(StaffSearchDTO searchDTO);

    List<AdjustCommissionHisSearchDTO> searchAdjustCommissionHis(Long cpId);

    Boolean checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode);

    Integer adjustCommission(CpDTO dto, String userName);

    ProvinceUsersDTO getLoginPermission(String username);

    List<ComboBean> getCpByProvinceIdAndProvinceUserIdAndStatus();
}
