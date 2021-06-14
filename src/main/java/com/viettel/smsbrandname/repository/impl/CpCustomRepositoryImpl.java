package com.viettel.smsbrandname.repository.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.ProvinceUsers;
import com.viettel.smsbrandname.repository.CpCustomRepository;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.VirtualAccountDTO;
import com.viettel.smsbrandname.service.dto.*;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Repository
public class CpCustomRepositoryImpl extends StandardizeLogging implements CpCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private String payMethodNote = ".PayMethod: ";

    private static String SEARCH_CP_QUERY = "select CP.CP_ID, CP.CP_CODE,CP.CP_NAME,NVL(CP.CHANNEL,'none') ,CP.USER_NAME,NVL(CP.APPROVE_TYPE,0),CP.STATUS,CP.CHARGE_TYPE, \n" +
        "        CP.CURRENCY,NVL(CP.BALANCE,0),NVL(CP.AD_BALANCE,0),NVL(P.PROVINCE_NAME,'none'),NVL(PD.DISTRICT_NAME,'none'),CP.ADDRESS, CP.TAX_CODE \n" +
        "        ,NVL(CP.ATTACH_FILE,'none'),NVL(CP.MONTH_COMMISSION_ATTACH_FILE,'none'),CP.DATE_CREATED  \n" +
        "        from CP CP  \n" +
        "        left join PROVINCE P on P.PROVINCE_ID = CP.PROVINCE_ID \n" +
        "        left join PROVINCE_USERS PU ON CP.PROVINCE_USER_ID = PU.ID\n" +
        "        left join PROVINCE_DISTRICT PD on CP.DISTRICT_ID = PD.ID where 1=1 ";

    private static String SEARCH_CP_QUERY_COUNT = "SELECT COUNT(*) FROM CP\n" +
        "        left join PROVINCE P on P.PROVINCE_ID = CP.PROVINCE_ID \n" +
        "        left join PROVINCE_USERS PU ON CP.PROVINCE_USER_ID = PU.ID\n" +
        "        left join PROVINCE_DISTRICT PD on CP.DISTRICT_ID = PD.ID where 1=1 ";

    private static String SEARCH_ALL_PROVINCE = "select PROVINCE_ID,PROVINCE_NAME from PROVINCE ";

    private static String SEARCH_DISTRICT_BY_PROVINCE_ID = "select ID,DISTRICT_NAME from PROVINCE_DISTRICT where status=1 and PROVINCE_ID =:provinceId";

    private static String SEARCH_ALL_COMMISSION_PERCENT_CODE = "select * from COMMISSION_PERCENT_CODE";

    private static String SEARCH_CHECK_EXISTED = "select CP.CP_ID, CP.CP_CODE,CP.CP_NAME,NVL(CP.CHANNEL,'none') ,CP.USER_NAME,NVL(CP.APPROVE_TYPE,0),CP.STATUS,CP.CHARGE_TYPE,\n" +
        "CP.CURRENCY,NVL(CP.BALANCE,0),NVL(CP.AD_BALANCE,0),NVL(P.PROVINCE_NAME,'none'),NVL(PD.DISTRICT_NAME,'none'),CP.ADDRESS,NVL(CP.WS_USERNAME,'none'),NVL(CP.CP_SYSID,'none') \n" +
        "from CP CP \n" +
        "left join PROVINCE P on P.PROVINCE_ID = CP.PROVINCE_ID\n" +
        "left join PROVINCE_DISTRICT PD on CP.DISTRICT_ID = PD.ID where 1=1 ";

    private static String SEARCH_PROVINCE_USER = "select ID,USER_NAME from PROVINCE_USERS WHERE USER_TYPE=0 ";

    private static String SEARCH_COUNT_PROVINCE_USER = "select count(*) from PROVINCE_USERS WHERE USER_TYPE=0 ";

    private static String SEARCH_STAFF = "select staff_code,name from staff where 1=1 ";

    private static String SEARCH_COUNT_STAFF = "select count(*) from staff where 1=1 ";

    private static String SEARCH_ADJUST_COMMISSION_HIS = "select CP_ID,UPDATED_DATE,USER_NAME,OLD_VALUE,NEW_VALUE,REASON from ADJUST_COMMISSION_HIS where CP_ID =:cpId order by UPDATED_DATE desc ";

    private static String SEARCH_CHECK_EXISTED_ORDER_OR_NAME = "SELECT COUNT(*) FROM CP WHERE #cpId AND (LOWER(FC_CONVERTVN2ROMAN(CP_NAME)) = FC_CONVERTVN2ROMAN(:cpName) OR LOWER(TAX_CODE) = FC_CONVERTVN2ROMAN(:taxCode))";

    private static String GET_LOGIN_PERMISSION = "SELECT PU.ID,PU.USER_NAME,PU.USER_TYPE,PU.PROVINCE_ID,PU.IS_PARENT,P.PROVINCE_CODE \n" +
        "FROM PROVINCE_USERS PU \n" +
        "JOIN PROVINCE P ON PU.PROVINCE_ID= P.PROVINCE_ID WHERE PU.USER_NAME = :USERNAME ";

    public CpCustomRepositoryImpl() {
        super(CpCustomRepositoryImpl.class);
    }

    @Override
    public List<ComboBean> findAllCpPreProvince(String currency, Long status, Optional<ProvinceUsers> optional) {
        StringBuilder sql = new StringBuilder("SELECT cp_id value, cp_code label FROM cp "
            + "WHERE charge_type = 'PRE' ");
        HashMap<String, Object> hashMap = new HashMap<>();
        if (currency != null && !"0".equals(currency)) {
            sql.append("AND currency = :currency ");
            hashMap.put("currency", currency);
        }
        if (status != -1) {
            sql.append("AND status = :status ");
            hashMap.put("status", status);
        }
        if (optional.isPresent()) {
            sql.append("AND province_id = :provinceId ");
            hashMap.put("provinceId", optional.get().getProvinceId());
        }
        sql.append("ORDER BY NLSSORT(LOWER(cp_code), 'NLS_SORT=VIETNAMESE') ASC");

        Query query = entityManager.createNativeQuery(sql.toString());
        hashMap.forEach(query::setParameter);
        return new MaperUtils(query.getResultList()).add("value").add("label").transform(ComboBean.class);
    }

    @Override
    public Page<VirtualAccountDTO> findByCondition(VirtualAccountDTO virtualAccount, ProvinceUsersDTO optional, boolean isExport) {
        Date date = new Date();
        List<VirtualAccountDTO> lstVA;
        int total = 0;
        int currentPage;
        int pageSize;
        try {
            DataUtil.trim(virtualAccount);
            currentPage = virtualAccount.getCurrentPage();
            pageSize = virtualAccount.getPageSize();

            HashMap<String, Object> hmap = new HashMap<>();

            Long provinceId = !DataUtil.isNullOrEmpty(optional) ? optional.getProvinceId() : -1;
            String currency = virtualAccount.getCurrency();
            int status = virtualAccount.getStatus();
            String cpCode = virtualAccount.getCpCode();
            int balanceType = virtualAccount.getBalanceType();
            Date fromDate = virtualAccount.getFromDate();
            Date toDate = virtualAccount.getToDate();

            StringBuilder sql = new StringBuilder(""
                + "SELECT a.cp_id          cpId,\n"
                + "       a.cp_code        cpCode,\n"
                + "       a.cp_name        cpName,\n"
                + "       a.currency       currency,\n"
                + "       b.balance_type   balanceType,\n"
                + "       a.status         status,\n"
                + "       b.amount         amount,\n"
                + "       b.balance_before balanceBefore,\n"
                + "       b.balance_after  balanceAfter,\n"
                + "       b.change_note note,\n"
                + "       case\n"
                + "         when INSTR(b.change_note, '" + payMethodNote + "') > 0 then\n"
                + "          substr(b.change_note,\n"
                + "                 INSTR(b.change_note, '" + payMethodNote + "') + length('" + payMethodNote + "'),\n"
                + "                 length(b.change_note))\n"
                + "         else\n"
                + "          '2'\n"
                + "       end as payMethod,\n"
                + "       a.balance balance,\n"
                + "       a.ad_balance adBalance,\n"
                + "       b.change_time dateChange,\n"
                + "       b.exchange_rate rate,\n"
                + "       b.discount_value || '%' discount,\n"
                + "       b.attach_file attachFile,\n"
                + "       b.status_bccs_usd statusBccsUsd,\n"
                + "       b.is_bccs_ok isBccsOk,\n"
                + "       nvl(b.branch_id, ' ') branch,\n"
                + "       b.branch_name branchName,\n"
                + "       b.modified_user userModified,\n"
                + "       b.id id,\n"
                + "       b.has_commission hasCommission,\n"
                + "       b.commission_bccs_ok commissionBccsOk,\n"
                + "       b.sale_trans_id saleTransId\n"
                + "  FROM cp a\n"
                + "  JOIN balance_his b\n"
                + "    ON a.cp_id = b.cp_id\n"
                + " WHERE a.charge_type = 'PRE' ");
            if (provinceId != null && provinceId != -1L) {
                sql.append("AND a.province_id = :provinceId ");
                hmap.put("provinceId", provinceId);
            }
            if (currency != null && !"0".equals(currency)) {
                sql.append("AND a.currency = :currency ");
                hmap.put("currency", currency);
            }
            if (status != -1) {
                sql.append("AND a.status = :status ");
                hmap.put("status", status);
            }
            if (cpCode != null && !"-1".equals(cpCode)) {
                sql.append("AND a.cp_id = :cpCode ");
                hmap.put("cpCode", cpCode);
            }
            if (balanceType != -1) {
                sql.append("AND b.balance_type = :balanceType ");
                hmap.put("balanceType", balanceType);
            }
            if (fromDate != null) {
                sql.append("AND TRUNC(b.change_time) >= :fromDate ");
                hmap.put("fromDate", fromDate);
            }
            if (toDate != null) {
                sql.append("AND TRUNC(b.change_time) <= :toDate ");
                hmap.put("toDate", toDate);
            }
            sql.append("ORDER BY b.change_time DESC, NLSSORT(LOWER(a.cp_code), 'NLS_SORT=VIETNAMESE') ASC");
            String sqlCount = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
            Query queryCp = entityManager.createNativeQuery(sql.toString());
            hmap.forEach(queryCp::setParameter);
            if (!isExport) {
                Query queryCount = entityManager.createNativeQuery(sqlCount);
                hmap.forEach(queryCount::setParameter);
                total = ((BigDecimal) queryCount.getSingleResult()).intValue();
                queryCp.setFirstResult(currentPage * pageSize);
                queryCp.setMaxResults(pageSize);
            }
            List<Object[]> rsCp = queryCp.getResultList();
            MaperUtils maper = new MaperUtils(rsCp);
            lstVA = maper.add("cpId").add("cpCode").add("cpName")
                .add("currency").add("balanceType").add("status").add("amount")
                .add("balanceBefore").add("balanceAfter").add("note").add("payMethod")
                .add("currentBalance").add("adBalance").add("dateChange").add("rate")
                .add("discount").add("attachFile").add("statusBccsUsd")
                .add("isBccsOk").add("branch").add("branchName")
                .add("userModified").add("id").add("hasCommission")
                .add("commissionBccsOk").add("saleTransId").transform(VirtualAccountDTO.class);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return new PageImpl<>(lstVA, PageRequest.of(currentPage, pageSize), total);
    }

    @Override
    public boolean isFirtTrans(Long cpId, Long balanceId) {
        String sql = "SELECT COUNT(*) FROM balance_his WHERE cp_id = :cpId "
            + "AND change_note LIKE 'addMoney%' AND id != :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("cpId", cpId);
        query.setParameter("id", balanceId);
        return ((BigDecimal) query.getSingleResult()).intValue() == 0;
    }

    @Override
    public CommonResponseDTO search(CpSearchDTO searchDTO) {
        StringBuilder sb = new StringBuilder(SEARCH_CP_QUERY);
        StringBuilder sbCount = new StringBuilder(SEARCH_CP_QUERY_COUNT);
        HashMap<String, Object> params = new HashMap();
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        if (!DataUtil.isNullOrEmpty(searchDTO.getCpCode())) {
            sb.append(" and lower(CP.CP_CODE) like :cpCode ");
            sbCount.append(" and lower(CP.CP_CODE) like :cpCode ");
            params.put("cpCode", DataUtil.makeLikeParam(searchDTO.getCpCode()));
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getCpName())) {
            sb.append(" and lower(CP.CP_NAME) like :cpName ");
            sbCount.append(" and lower(CP.CP_NAME) like :cpName ");
            params.put("cpName", DataUtil.makeLikeParam(searchDTO.getCpName()));
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getUserName())) {
            sb.append(" and lower(CP.USER_NAME) like :userName ");
            sbCount.append(" and lower(CP.USER_NAME) like :userName ");
            params.put("userName", DataUtil.makeLikeParam(searchDTO.getUserName()));
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getChargeType())) {
            sb.append(" and CP.CHARGE_TYPE = :chargeType ");
            sbCount.append(" and CP.CHARGE_TYPE = :chargeType ");
            params.put("chargeType", searchDTO.getChargeType());
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getCurrency())) {
            sb.append(" and CP.CURRENCY = :currency ");
            sbCount.append(" and CP.CURRENCY = :currency ");
            params.put("currency", searchDTO.getCurrency());
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getStatus())) {
            sb.append(" and CP.STATUS = :status ");
            sbCount.append(" and CP.STATUS = :status ");
            params.put("status", searchDTO.getStatus());
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getProvinceId())) {
            sb.append(" and CP.PROVINCE_ID = :provinceId ");
            sbCount.append(" and CP.PROVINCE_ID = :provinceId ");
            params.put("provinceId", searchDTO.getProvinceId());
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getCpAlias())) {
            sb.append(" and CP.CP_ID in (select ca.CP_ID from CP_ALIAS ca where lower(ca.CP_ALIAS) like :cpAlias) ");
            sbCount.append(" and CP.CP_ID in (select ca.CP_ID from CP_ALIAS ca where lower(ca.CP_ALIAS) like :cpAlias) ");
            params.put("cpAlias", DataUtil.makeLikeParam(searchDTO.getCpAlias()));
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getSendWithOther())) {
            sb.append(" and CP.SEND_WITH_OTHER = 1");
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getProvinceUserId())) {
            sb.append(" and CP.PROVINCE_USER_ID  = :provinceUserId");
            sbCount.append(" and CP.PROVINCE_USER_ID  = :provinceUserId");
            params.put("provinceUserId", searchDTO.getProvinceUserId());
        }
        sb.append(" order by CP.CP_ID desc");
        Query query = entityManager.createNativeQuery(sb.toString());
        Query queryCount = entityManager.createNativeQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<Object[]> list;
        if (!DataUtil.isNullOrEmpty(searchDTO.getPage()) && !DataUtil.isNullOrEmpty(searchDTO.getPageSize())) {
            list = query.setFirstResult(searchDTO.getPage()).setMaxResults(searchDTO.getPageSize()).getResultList();
        } else {
            list = query.getResultList();
        }
        Integer count = DataUtil.safeToInt(queryCount.getSingleResult());
        responseDTO.setListData(CpDTO.toDto(list));
        responseDTO.setCount(count);
        return responseDTO;
    }

    @Override
    public List<ProvinceDTO> findAllProvince() {
        List<ProvinceDTO> list;
        StringBuilder sb = new StringBuilder(SEARCH_ALL_PROVINCE);
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> objects = query.getResultList();
        list = ProvinceDTO.toDto(objects);
        return list;
    }

    @Override
    public List<DistrictDTO> findDistrictByProvinceId(Long id) {
        List<DistrictDTO> list;
        HashMap<String, Object> params = new HashMap<>();
        params.put("provinceId", id);
        StringBuilder sb = new StringBuilder(SEARCH_DISTRICT_BY_PROVINCE_ID);
        Query query = entityManager.createNativeQuery(sb.toString());
        DbUtils.setParramToQuery(query, params);
        List<Object[]> objects = query.getResultList();
        list = DistrictDTO.toDto(objects);
        return list;
    }

    @Override
    public List<CommissionPercentCodeDTO> getAllCommissionPercentCode() {
        List<CommissionPercentCodeDTO> list;
        StringBuilder sb = new StringBuilder(SEARCH_ALL_COMMISSION_PERCENT_CODE);
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> objects = query.getResultList();
        list = CommissionPercentCodeDTO.toDto(objects);
        return list;
    }

    @Override
    public CpDTO checkExistedValue(String cpCode, String cpName, String userName, String wsUsername, String cpCysId) {
        StringBuilder sb = new StringBuilder(SEARCH_CHECK_EXISTED);
        HashMap<String, Object> params = new HashMap();
        if (!DataUtil.isNullOrEmpty(cpCode)) {
            sb.append(" and lower(CP.CP_CODE) =:cpCode");
            params.put("cpCode", cpCode.toLowerCase());
        }
        if (!DataUtil.isNullOrEmpty(cpName)) {
            sb.append(" and lower(CP.CP_NAME) =:cpName");
            params.put("cpName", cpName.toLowerCase());
        }
        if (!DataUtil.isNullOrEmpty(userName)) {
            sb.append(" and lower(CP.USER_NAME) =:userName");
            params.put("userName", userName.toLowerCase());
        }
        if (!DataUtil.isNullOrEmpty(wsUsername)) {
            sb.append(" and lower(CP.WS_USERNAME) =:wsUsername");
            params.put("wsUsername", wsUsername.toLowerCase());
        }
        if (!DataUtil.isNullOrEmpty(cpCysId)) {
            sb.append(" and lower(CP.CP_SYSID) =:cpSysId");
            params.put("cpSysId", cpCysId.toLowerCase());
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        DbUtils.setParramToQuery(query, params);
        List<Object[]> list = query.getResultList();
        List<CpDTO> rs = CpDTO.toCheckDto(list);
        return rs.size() > 0 ? rs.get(0) : null;
    }

    @Override
    public CommonResponseDTO searchProvinceUser(ProvinceUserSearchDTO searchDTO) {
        StringBuilder sb = new StringBuilder(SEARCH_PROVINCE_USER);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_PROVINCE_USER);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(searchDTO.getProvinceId())) {
            sb.append(" and PROVINCE_ID =:provinceId");
            sbCount.append(" and PROVINCE_ID =:provinceId");
            params.put("provinceId", searchDTO.getProvinceId());
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getUserName())) {
            sb.append(" and lower(USER_NAME) like :userName");
            sbCount.append(" and lower(USER_NAME) like :userName");
            params.put("userName", DataUtil.makeLikeParam(searchDTO.getUserName().toLowerCase()));
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        Query queryCount = entityManager.createNativeQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<Object[]> list = query.setFirstResult(searchDTO.getPage()).setMaxResults(searchDTO.getPageSize()).getResultList();
        Integer count = DataUtil.safeToInt(queryCount.getSingleResult());
        List<ProvinceUserSearchDTO> dtoList = ProvinceUserSearchDTO.toDto(list);
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        responseDTO.setCount(count);
        responseDTO.setListData(dtoList);
        return responseDTO;
    }

    @Override
    public CommonResponseDTO searchStaff(StaffSearchDTO searchDTO) {
        StringBuilder sb = new StringBuilder(SEARCH_STAFF);
        StringBuilder sbCount = new StringBuilder(SEARCH_COUNT_STAFF);
        HashMap<String, Object> params = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(searchDTO.getStaffCode())) {
            sb.append(" and lower(STAFF_CODE) like :staffCode");
            sbCount.append(" and lower(STAFF_CODE) like :staffCode");
            params.put("staffCode", DataUtil.makeLikeParam(searchDTO.getStaffCode().toLowerCase()));
        }
        if (!DataUtil.isNullOrEmpty(searchDTO.getName())) {
            sb.append(" and lower(NAME) like :name");
            sbCount.append(" and lower(NAME) like :name");
            params.put("name", DataUtil.makeLikeParam(searchDTO.getName().toLowerCase()));
        }
        Query query = entityManager.createNativeQuery(sb.toString());
        Query queryCount = entityManager.createNativeQuery(sbCount.toString());
        DbUtils.setParramToQuery(query, params);
        DbUtils.setParramToQuery(queryCount, params);
        List<Object[]> list = query.setFirstResult(searchDTO.getPage()).setMaxResults(searchDTO.getPageSize()).getResultList();
        Integer count = DataUtil.safeToInt(queryCount.getSingleResult());
        List<StaffSearchDTO> dtoList = StaffSearchDTO.toDto(list);
        CommonResponseDTO responseDTO = new CommonResponseDTO();
        responseDTO.setCount(count);
        responseDTO.setListData(dtoList);
        return responseDTO;
    }

    @Override
    public List<AdjustCommissionHisSearchDTO> searchAdjustCommissionHis(Long cpId) {
        List<AdjustCommissionHisSearchDTO> result;
        Query query = entityManager.createNativeQuery(SEARCH_ADJUST_COMMISSION_HIS);
        query.setParameter("cpId", cpId);
        result = AdjustCommissionHisSearchDTO.toDto(query.getResultList());
        return result;
    }

    @Override
    public Boolean checkExistedOrderNoOrCpName(String cpName, String taxCode, Boolean isSaving, Long cpId, String commissionPercentCode) {
        if (!"C0".equalsIgnoreCase(commissionPercentCode)) {
            String sql = SEARCH_CHECK_EXISTED_ORDER_OR_NAME.replaceAll("#cpId", !isSaving ? "CP_ID != :cpId" : "1=1");
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("taxCode", taxCode);
            query.setParameter("cpName", cpName);
            if (!isSaving) query.setParameter("cpId", cpId);
            Integer count = DataUtil.safeToInt(query.getSingleResult());
            return count > 0;
        }
        return false;
    }

    @Override
    public Integer adjustCommission(CpDTO dto, String userName) {
        String sql = "INSERT INTO ADJUST_COMMISSION_HIS(CP_ID, NEW_VALUE, OLD_VALUE, REASON, UPDATED_DATE, USER_NAME) "
            + "VALUES (:P_CP_ID, :P_NEW_VALUE, :P_OLD_VALUE, :P_REASON, SYSDATE, :P_USER_NAME)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("P_CP_ID", dto.getCpId());
        query.setParameter("P_NEW_VALUE", dto.getCommissionPercentCode());
        query.setParameter("P_OLD_VALUE", dto.getOldValueCommissionPercentCode());
        query.setParameter("P_REASON", dto.getReason());
        query.setParameter("P_USER_NAME", userName);
        return query.executeUpdate();
    }

    @Override
    public ProvinceUsersDTO getLoginPermission(String username) {
        Query query = entityManager.createNativeQuery(GET_LOGIN_PERMISSION);
        query.setParameter("USERNAME", username);
        List<Object[]> result = query.getResultList();
        ProvinceUsersDTO dto = ProvinceUsersDTO.toLoginDto(result);
        return dto;
    }

    @Override
    public List<ComboBean> getCpByProvinceIdAndProvinceUserIdAndStatus() {
        Date date = new Date();
        List<ComboBean> lstCP;
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT cp_id AS value, cp_code AS label FROM cp "
            + "WHERE 1 = 1 AND status = 1 ");
        try {
            String userName = SecurityUtils.getCurrentUserLogin().get();
            ProvinceUsersDTO provinceUsersDTO = getLoginPermission(userName);
            Long provinceId = provinceUsersDTO.getProvinceId();
            Integer isParent = provinceUsersDTO.getIsParent();
            Long provinceUserId = provinceUsersDTO.getId();
            if (!DataUtil.isNullOrEmpty(isParent) && isParent.equals(0) && !DataUtil.isNullOrEmpty(provinceUserId) && !provinceUserId.equals(-1L)
                && !DataUtil.isNullOrEmpty(provinceId) && !provinceId.equals(-1L)) {
                sql.append("AND province_id = :provinceId AND province_user_id = :provinceUserId ");
                params.put("provinceId", provinceId);
                params.put("provinceUserId", provinceUserId);
            } else if (!DataUtil.isNullOrEmpty(provinceId) && provinceId != -1L && !DataUtil.isNullOrEmpty(isParent) && isParent.equals(1)) {
                sql.append("AND province_id = :provinceId ");
                params.put("provinceId", provinceId);
            }
            sql.append("ORDER BY NLSSORT(LOWER(cp_code), 'NLS_SORT=VIETNAMESE') ASC");
            Query query = entityManager.createNativeQuery(sql.toString());
            DbUtils.setParramToQuery(query, params);
            lstCP = new MaperUtils(query.getResultList()).add("value").add("label").transform(ComboBean.class);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new BusinessException(ErrorCodeResponse.UNKNOWN);
        }
        return lstCP;
    }
}
