package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DateUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.CpService;
import com.viettel.smsbrandname.service.KeyCloakUserService;
import com.viettel.smsbrandname.service.dto.MtDTO;
import com.viettel.smsbrandname.service.dto.MtSearchDTO;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class MtRepositoryImpl implements MtRepositoryCustom {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    private final KeyCloakUserService keyCloakUserService;

    private final CpService cpService;

    private static final String SEARCH_QUERY_COUNT = "select count(*) from ( #sql )";

    private static final String SEARCH_QUERY = "SELECT a.mt_time mtTime,\n" +
        "  CASE\n" +
        "    WHEN ((pr.prog_type = 1\n" +
        "    AND pr.type        IN (1, 2, 3))\n" +
        "    OR (pr.prog_type    = 0\n" +
        "    AND pr.type         = 3)\n" +
        "    OR a.CP_ID         IN (8233,8721,9229) )\n" +
        "    AND :isProvinceUser = 1\n" +
        "    THEN\n" +
        "      CASE\n" +
        "        WHEN LENGTH(a.receiver) >= 5\n" +
        "        THEN SUBSTR(a.receiver, 0, LENGTH(a.receiver) - 5)\n" +
        "          || '*****'\n" +
        "        ELSE '*****'\n" +
        "      END\n" +
        "    ELSE a.receiver\n" +
        "  END AS receiver,\n" +
        "  a.sender,\n" +
        "  a.message,\n" +
        "  a.num_mt numMt,\n" +
        "  (\n" +
        "  CASE a.status\n" +
        "    WHEN 0\n" +
        "    THEN 'Thất bại - Chưa xử lý'\n" +
        "    WHEN 1\n" +
        "    THEN 'Thất bại - Lỗi nội dung'\n" +
        "    WHEN 2\n" +
        "    THEN 'Gửi thành công'\n" +
        "    WHEN 3\n" +
        "    THEN 'Thất bại - Gửi lỗi'\n" +
        "    WHEN 16\n" +
        "    THEN 'Thất bại - Retry lần 1'\n" +
        "    WHEN 17\n" +
        "    THEN 'Thất bại - Retry lần 2'\n" +
        "    WHEN 19\n" +
        "    THEN 'Gửi thành công'\n" +
        "    WHEN 20\n" +
        "    THEN 'Thất bại - Retry thất bại'\n" +
        "    WHEN 22\n" +
        "    THEN 'Thất bại - Lỗi Brand'\n" +
        "    WHEN 23\n" +
        "    THEN 'Thất bại - Không đủ tiền'\n" +
        "    WHEN 24\n" +
        "    THEN 'Thất bại - Không lấy được giá của tin nhắn'\n" +
        "    WHEN 25\n" +
        "    THEN 'Thất bại - Tin nhắn chưa update'\n" +
        "    WHEN 26\n" +
        "    THEN 'Thất bại - Thuê bao đạt max tin'\n" +
        "    WHEN 27\n" +
        "    THEN 'Thất bại - Thuê bao từ chối nhận tin quảng cáo'\n" +
        "  END) AS status ,\n" +
        "  a.cp_code cpCode,\n" +
        "  (\n" +
        "  CASE a.alias_type\n" +
        "    WHEN 0\n" +
        "    THEN 'Chăm sóc khách hàng'\n" +
        "    WHEN 1\n" +
        "    THEN 'Quảng cáo'\n" +
        "  END) AS aliasType,\n" +
        "  a.webservice,\n" +
        "  tc.telco_name telcoName,\n" +
        "  a.dlr_status dlrStatus\n" +
        "FROM mt a\n" +
        "JOIN cp b\n" +
        "ON a.cp_id = b.cp_id\n" +
        "LEFT JOIN prog pr\n" +
        "ON a.prog_id = pr.prog_id\n" +
        "LEFT JOIN telco tc\n" +
        "ON tc.telco_id = a.telco_id\n" +
        "WHERE 1 = 1 ";

    public MtRepositoryImpl(KeyCloakUserService keyCloakUserService, @Qualifier("cpServiceImpl") CpService cpService) {
        this.keyCloakUserService = keyCloakUserService;
        this.cpService = cpService;
    }

    @Override
    public Page<Object[]> search(String fromDate, String toDate, String receiver, Long cpId, String sender, Long aliasType, Long telcoId, Pageable pageable) {
        try {
            Integer checkTaiKhoanTinh = 0;
            Map<String, Object> params = new HashMap<>();
            String userName = SecurityUtils.getCurrentUserLogin().get();
            ProvinceUsersDTO provinceUsersDTO = cpService.getLoginPermission(userName);
            Long provinceId = provinceUsersDTO.getProvinceId();
            Integer isParent = provinceUsersDTO.getIsParent();
            Long provinceUserId = provinceUsersDTO.getId();
            List<RoleRepresentation> realmRoles = keyCloakUserService.getUserRealmRoles(userName);
            if (userName.toLowerCase().indexOf("smsbrand_") >= 0) {
                checkTaiKhoanTinh = 1;
            } else {
                if (!DataUtil.isNullOrEmpty(realmRoles)) {
                    checkTaiKhoanTinh = realmRoles.stream().anyMatch(roleRepresentation -> roleRepresentation.getName().toLowerCase().indexOf("bulk_admin_") >= 0) ? 1 : 0;
                }
            }
            StringBuilder sql = new StringBuilder(SEARCH_QUERY);
            params.put("isProvinceUser", checkTaiKhoanTinh);
            if (!DataUtil.isNullOrEmpty(isParent) && isParent.equals(0) && !DataUtil.isNullOrEmpty(provinceUserId)
                && !provinceUserId.equals(-1L) && !DataUtil.isNullOrEmpty(provinceId) && !provinceId.equals(-1L)) {
                sql.append(" AND b.province_id = :provinceId AND b.province_user_id = :provinceUserId ");
                params.put("provinceId", provinceId);
                params.put("provinceUserId", provinceUserId);
            } else if (!DataUtil.isNullOrEmpty(provinceId) && provinceId != -1L && !DataUtil.isNullOrEmpty(isParent) && isParent.equals(1)) {
                sql.append(" AND b.province_id = :provinceId ");
                params.put("provinceId", provinceId);
            }
            if (!DataUtil.isNullOrEmpty(receiver)) {
                sql.append(" AND a.receiver = :receiver ");
                params.put("receiver", receiver);
            }
            if (!DataUtil.isNullOrEmpty(fromDate)) {
                sql.append(" AND a.mt_time >= TO_DATE(:fromDate,'dd/MM/yyyy hh24:mi:ss') ");
                params.put("fromDate", fromDate);
            }
            if (!DataUtil.isNullOrEmpty(toDate)) {
                sql.append(" AND a.mt_time <= TO_DATE(:toDate,'dd/MM/yyyy hh24:mi:ss') ");
                params.put("toDate", toDate);
            }
            if (!DataUtil.isNullOrEmpty(aliasType)) {
                sql.append(" AND a.alias_type = :aliasType ");
                params.put("aliasType", aliasType);
            }
            if (!DataUtil.isNullOrEmpty(telcoId)) {
                sql.append(" AND tc.telco_id = :telcoId ");
                params.put("telcoId", telcoId);
            }
            if (!DataUtil.isNullOrEmpty(cpId)) {
                sql.append(" AND a.cp_id = :cpId ");
                params.put("cpId", cpId);
            }
            if (!DataUtil.isNullOrEmpty(sender)) {
                sql.append(" AND a.sender = :sender ");
                params.put("sender", sender);
            }
            Query query = entityManager.createNativeQuery(sql.toString());
            Query queryCount = entityManager.createNativeQuery(SEARCH_QUERY_COUNT.replace("#sql", sql.toString()));
            DbUtils.setParramToQuery(query, params);
            DbUtils.setParramToQuery(queryCount, params);
            List<Object[]> objects;
            if (!DataUtil.isNullOrEmpty(pageable)) {
                objects = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
            } else {
                objects = query.getResultList();
            }
            Integer count = DataUtil.safeToInt(queryCount.getSingleResult());
            return new PageImpl<>(objects, !DataUtil.isNullOrEmpty(pageable) ? pageable : PageRequest.of(0, 1), count);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCodeResponse.UNKNOWN);
        }
    }
}
