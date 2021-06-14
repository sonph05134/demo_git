package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CpAlias;
import com.viettel.smsbrandname.service.dto.cpbrand.CPBrandTelcoDTO;
import com.viettel.smsbrandname.service.dto.cpbrand.CpAliasTmpFileDTO;

import java.util.List;

/**
 * Autogen class Repository Interface: Lop thao tac danh sach CPBrand
 *
 * @author toolGen
 * @date Mon Dec 14 15:39:20 ICT 2020
 */
public interface CPBrandRepositoryCustom {

    List<Object[]> search(Integer aliasType, String brandName, String telco, Integer start, Integer limit, Long cpId);

    List<CPBrandTelcoDTO> getTelcos();

    int createHistoryData(Long cpAliasID, Long cpAliasTmpID, String actionKeyword, String userName);

    void updateStatus(Long cpAliasId,Long cpAliasTmpId, String username, Integer action);

    int deleteCpAlias(long cpAliasId, String username);

    List<Object[]> getAliasGroup(Integer type);

    List<Object[]> getWS(Integer status);

    List<Object[]> getKeepFee();

    boolean checkAliasExitsInCreate(String cpAlias, String telco, Long cpId);

    boolean checkAliasExitsInEdit(String cpAlias, String telco, Long cpId, Long cpAliasId);

    String checkTaxCodeAndCompanyName(Long cpId, String taxCode, String companyName);

    Boolean updateCommissionDefault(Long cpId, String userId, String username);

    void updateCpAliasTmp(CpAlias cpAlias);

    CpAlias getCpAlias(long cpAliasId);

    CpAliasTmpFileDTO getCpAliasTmpByCpAliasID(Long cpAliasId);

    boolean chargeCancelAlias(Long cpId, String cpCode, String telco, String cpAlias, String currency, String aliasType);

    int deleteCpAliasTmp(Long cpAliasId);

    int countAlias(Long cpId, String alias, String aliasType, String accUpdate);

    int deleteCpUsersAlias(Long cpId, String alias, String aliasType, String accUpdate);
}
