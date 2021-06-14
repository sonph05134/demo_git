package com.viettel.smsbrandname.repository.impl;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.CpAlias;
import com.viettel.smsbrandname.domain.TransLogAlias;
import com.viettel.smsbrandname.repository.CPBrandRepositoryCustom;
import com.viettel.smsbrandname.repository.TransLogAliasRepository;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.ChargingService;
import com.viettel.smsbrandname.service.dto.cpbrand.CPBrandTelcoDTO;
import com.viettel.smsbrandname.service.dto.cpbrand.CpAliasTmpFileDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Autogen class Repository Impl: Lop thao tac danh sach CPBrand
 *
 * @author ToolGen
 * @date Mon Dec 14 15:39:20 ICT 2020
 */
@Repository
public class CPBrandRepositoryImpl extends StandardizeLogging implements CPBrandRepositoryCustom {


    private static final Logger logger = LoggerFactory.getLogger(CPBrandRepositoryImpl.class);

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    private final ChargingService chargingService;

    private final TransLogAliasRepository transLogAliasRepository;

    private StringBuilder sqlBackupCpAlias = new StringBuilder(
        "INSERT INTO CP_ALIAS\n"
            + "  (CP_ALIAS_ID,\n"
            + "   CP_ALIAS,\n"
            + "   CP_ALIAS_TMP_ID,\n"
            + "   STATUS,\n"
            + "   CP_ID,\n"
            + "   TYPE,\n"
            + "   GROUP_TYPE,\n"
            + "   TELCO,\n"
            + "   CREATE_DATE,\n"
            + "   END_DATE,\n"
            + "   UPDATE_DATE,\n"
            + "   WEBSERVICE,\n"
            + "   WEBSERVICE_BACKUP,\n"
            + "   ALIAS_TYPE,\n"
            + "   OPTIONAL_KEEP_FEE,\n"
            + "   KEEP_FEE,\n"
            + "   LAST_CHARGE_TIME,\n"
            + "   LAST_CHARGE_STATUS,\n"
            + "   PROCESS_ID,\n"
            + "   PROCESS_TIME,\n"
            + "   ACC_UPDATE,\n"
            + "   INACTIVE_DATE,\n"
            + "   ATTACH_FILE,\n"
            + "   TIME_REPEAT,\n"
            + "   CHECK_BLOCK_SPAM,\n"
            + "   ACCEPT_PARENTCP_SEND,\n"
            + "   CHECK_DUPLICATE,\n"
            + "   COMPANY_NAME,\n"
            + "   COMPANY_NAME_ROMAN,\n"
            + "   TAX_CODE,\n"
            + "   MONTH_KEEP_FEE,\n"
            + "   NUMBER_SMS_CHECK_SPAM,\n"
            + "   ACC_CREATE,\n"
            + "   FILE_PATH_CREATE,\n"
            + "   FILE_PATH_CANCEL)\n"
            + "  SELECT CP_ALIAS_ID_SEQ.NEXTVAL,\n"
            + "         CP.CP_ALIAS,\n"
            + "         CP.CP_ALIAS_TMP_ID,\n"
            + "         2 STATUS,\n"
            + "         CP.CP_ID,\n"
            + "         CP.TYPE,\n"
            + "         CP.GROUP_TYPE,\n"
            + "         CP.TELCO,\n"
            + "         SYSDATE AS CREATE_DATE,\n"
            + "         CP.END_DATE,\n"
            + "         CP.UPDATE_DATE,\n"
            + "         CP.WEBSERVICE,\n"
            + "         CP.WEBSERVICE_BACKUP,\n"
            + "         CP.ALIAS_TYPE,\n"
            + "         CP.OPTIONAL_KEEP_FEE,\n"
            + "         CP.KEEP_FEE,\n"
            + "         CP.LAST_CHARGE_TIME,\n"
            + "         CP.LAST_CHARGE_STATUS,\n"
            + "         CP.PROCESS_ID,\n"
            + "         CP.PROCESS_TIME,\n"
            + "         CP.ACC_UPDATE,\n"
            + "         CP.INACTIVE_DATE,\n"
            + "         CP.ATTACH_FILE,\n"
            + "         CP.TIME_REPEAT,\n"
            + "         CP.CHECK_BLOCK_SPAM,\n"
            + "         CP.ACCEPT_PARENTCP_SEND,\n"
            + "         CP.CHECK_DUPLICATE,\n"
            + "         CP.COMPANY_NAME,\n"
            + "         FC_CONVERTVN2ROMAN(CP.COMPANY_NAME),\n"
            + "         CP.TAX_CODE,\n"
            + "         CP.MONTH_KEEP_FEE,\n"
            + "         CP.NUMBER_SMS_CHECK_SPAM,\n"
            + "         CP.ACC_CREATE,\n"
            + "         CP.FILE_PATH_CREATE,\n"
            + "         CP.FILE_PATH_CANCEL\n"
            + "    FROM CP_ALIAS CP\n"
            + "   WHERE 1=1 \n");

    public CPBrandRepositoryImpl(ChargingService chargingService, TransLogAliasRepository transLogAliasRepository) {
        super(CPBrandRepositoryImpl.class);
        this.chargingService = chargingService;
        this.transLogAliasRepository = transLogAliasRepository;
    }

    /**
     * Lay du lieu cac filter
     *
     * @param aliasType :
     * @param start
     * @param limit
     * @param cpId
     * @return
     */
    @Override
    public List<Object[]> search(Integer aliasType, String brandName, String telco, Integer start, Integer limit, Long cpId) {
        Date date = new Date();
        List<Object[]> list;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("CASE  WHEN a.ALIAS_TYPE = :P_ADVERTISE_CONSTANTS THEN '" + Translator.toLocale("prop.advertise") + "'\n"
                + " WHEN a.ALIAS_TYPE = :P_CUSTOMERCARE_CONSTANTS THEN '" + Translator.toLocale("prop.customerCare") + "'\n"
                + " ELSE ' ' \n"
                + "END AS aliasTypeName, \n");
            sql.append("a.CP_ALIAS alias, \n");
            sql.append("CPG.group_name groupTypeName, \n");
            sql.append("a.telco telco, \n");
            sql.append("TO_CHAR(a.create_date, 'DD/MM/YYYY') createDateStr, \n");
            sql.append("CASE  WHEN a.status = :ACTIVE THEN '" + Translator.toLocale("state.active") + "'\n"
                + "           WHEN a.status = :P_INACTIVE THEN '" + Translator.toLocale("state.inactive") + "'\n"
                + "           ELSE ' ' \n"
                + "     END AS statusName, \n");
            sql.append("a.TIME_REPEAT timeRepeat, \n");
            sql.append("ROW_NUMBER() OVER(ORDER BY a.update_date DESC NULLS LAST, a.cp_alias) RN, \n");
            sql.append("a.status status, \n");
            sql.append("a.cp_alias_tmp_id, \n");
            sql.append("a.cp_alias_id, \n");
            sql.append("a.alias_type, \n");
            sql.append("a.attach_file, \n");
            sql.append("a.CHECK_DUPLICATE, \n");
            sql.append("a.CHECK_BLOCK_SPAM, \n");
            sql.append("a.INACTIVE_DATE, \n");
            sql.append("a.WEBSERVICE_BACKUP, \n");
            sql.append("a.MONTH_KEEP_FEE, \n");
            sql.append("a.OPTIONAL_KEEP_FEE, \n");
            sql.append("a.GROUP_TYPE, \n");
            sql.append("a.KEEP_FEE, \n");
            sql.append("a.WEBSERVICE, \n");
            sql.append("a.NUMBER_SMS_CHECK_SPAM, \n");
            sql.append("a.ACCEPT_PARENTCP_SEND, \n");
            sql.append("a.TAX_CODE, \n");
            sql.append("a.CP_ID, \n");
            sql.append("a.COMPANY_NAME, \n");
            sql.append("c.TELCO_NAME \n");
            sql.append("FROM cp_alias a LEFT JOIN telco c ON a.telco = c.telco_code \n");
            sql.append("                JOIN cp d ON a.cp_id = d.cp_id \n");
            sql.append("                LEFT JOIN CP_ALIAS_TMP CPT ON CPT.CP_ALIAS_ID = A.CP_ALIAS_TMP_ID \n");
            sql.append("LEFT JOIN \n");
            sql.append(" (SELECT b.group_name, b.group_id FROM cp_alias_group b GROUP BY b.group_name, b.group_id) CPG \n");
            sql.append("ON CPG.group_id    = a.group_type \n");
            sql.append("WHERE a.status    <> 2 \n");
            sql.append("AND (:P_ALIAS_TYPE = -1 OR a.ALIAS_TYPE    = :P_ALIAS_TYPE) \n");
            sql.append("AND (:P_CP_ALIAS is null OR :P_CP_ALIAS = '' OR LOWER(a.CP_ALIAS) LIKE '%' || :P_CP_ALIAS || '%') \n");
            sql.append("AND (:P_TELCO IS null OR :P_TELCO = '' OR a.telco    = :P_TELCO) \n");
            sql.append("AND a.cp_id = :P_CP_ID \n");
            sql.append("ORDER BY a.update_date DESC NULLS LAST, a.cp_alias");

            StringBuilder sqlPagination = new StringBuilder();
            sqlPagination.append("SELECT * FROM");
            sqlPagination.append("(").append(sql).append(")");
            sqlPagination.append(" WHERE ((:P_START + :P_LIMIT = 0) OR (RN BETWEEN (:P_START + 1) AND (:P_START + :P_LIMIT)))");

            Query query = entityManager.createNativeQuery(sqlPagination.toString());

            query.setParameter("P_ADVERTISE_CONSTANTS", Constants.CP_ALIAS_ALIAS_TYPE_ADVERTISE);
            query.setParameter("P_CUSTOMERCARE_CONSTANTS", Constants.CP_ALIAS_ALIAS_TYPE_CUSTOMER_CARE);
            query.setParameter("ACTIVE", Constants.ACTIVE);
            query.setParameter("P_INACTIVE", Constants.INACTIVE);

            query.setParameter("P_TELCO", telco);
            query.setParameter("P_ALIAS_TYPE", aliasType);
            query.setParameter("P_CP_ALIAS", brandName != null ? brandName.trim().toLowerCase() : null);
            query.setParameter("P_START", start);
            query.setParameter("P_LIMIT", limit);
            query.setParameter("P_CP_ID", cpId);

            list = query.getResultList();
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return list;
    }


    /**
     * Lay du lieu cac select box telco
     *
     * @return List<CPBrandTelcoDTO>
     */
    @Override
    public List<CPBrandTelcoDTO> getTelcos() {
        StringBuilder sql = new StringBuilder();
        List<CPBrandTelcoDTO> telcoDTOS = new ArrayList<>();

        try {
            sql.append("SELECT telco_code, telco_name FROM telco ORDER BY telco_name");
            List<Object[]> data = entityManager.createNativeQuery(sql.toString()).getResultList();

            if (data != null && !data.isEmpty()) {
                for (Object[] objects : data) {
                    CPBrandTelcoDTO cpBrandTelcoDTO = new CPBrandTelcoDTO();
                    cpBrandTelcoDTO.setTelcoCode(objects[0].toString());
                    cpBrandTelcoDTO.setTelcoName(objects[1].toString());

                    telcoDTOS.add(cpBrandTelcoDTO);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


        return telcoDTOS;

    }

    @Override
    public int createHistoryData(Long cpAliasID, Long cpAliasTmpID, String actionKeyword, String userName) {
        Date date = new Date();
        int result;
        try {
            StringBuilder desc = new StringBuilder();
            desc.append(Translator.toLocaleWithDefault("label.userName", ""));
            desc.append(" ").append(userName).append(" ");
            desc.append(Translator.toLocaleWithDefault(actionKeyword, ""));
            desc.append(" ");
            desc.append(Translator.toLocaleWithDefault("alert.alias", " "));

            StringBuilder sql = new StringBuilder();
            sql.append(""
                + "INSERT INTO CP_ALIAS_HIS\n"
                + "  (CP_ALIAS_HIS_ID,\n"
                + "   CP_ALIAS_ID,\n"
                + "   CP_ALIAS_TMP_ID,\n"
                + "   APPROVE_BEFORE,\n"
                + "   APPROVE_AFTER,\n"
                + "   DESCRIPTION,\n"
                + "   USER_CREATED,\n"
                + "   DATE_CREATED)\n"
                + "SELECT \n"
                + "  CP_ALIAS_HIS_ID_SEQ.NEXTVAL,\n"
                + "   :P_CP_ALIAS_ID,\n"
                + "   CASE WHEN :P_CP_ALIAS_TMP_ID = '' THEN NULL " +
                "          ELSE :P_CP_ALIAS_TMP_ID end,\n"
                + "   NULL,\n"
                + "   NULL,\n"
                + "   :P_DESCRIPTION,\n"
                + "   :P_USER_CREATED,\n"
                + "   SYSDATE FROM CP_ALIAS_HIS WHERE ROWNUM = 1");
            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("P_CP_ALIAS_ID", cpAliasID);
            query.setParameter("P_CP_ALIAS_TMP_ID", cpAliasTmpID == null ? "" : cpAliasTmpID);
            query.setParameter("P_DESCRIPTION", desc.toString());
            query.setParameter("P_USER_CREATED", userName);
            result = query.executeUpdate();
            if (result < 1) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong them duoc ban ghi lich su tac dong Alias", date);
                throw new BusinessException(ErrorCodeResponse.UNKNOWN);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "UpdateSuccess", date);
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return result;
    }

    @Override
    public void updateStatus(Long cpAliasId, Long cpAliasTmpId, String username, Integer action) {
        Date date = new Date();
        Session session = entityManager.unwrap(Session.class);
        try {
            String userName = SecurityUtils.getCurrentUserLogin().get();
            cpAliasTmpId = Integer.valueOf(-1).equals(cpAliasTmpId) ? null : cpAliasTmpId;
            //Them ban ghi lich su tac dong
            if (createHistoryData(cpAliasId, cpAliasTmpId, Constants.STATUS_ACTIVE.equals(action) ? "label.active" : "label.inactive", userName) <= 0) {
                writeInfoLog("1", "Khong them duoc ban ghi lich su tac dong Alias!", date);
                session.doWork(Connection::rollback);
            }
            //Them ban ghi backup
            String sql = sqlBackupCpAlias
                + "     AND CP.CP_ALIAS_ID = :P_CP_ALIAS_ID\n"
                + "     AND CP.STATUS IN (0, 1, 3)\n";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("P_CP_ALIAS_ID", cpAliasId);
            if (query.executeUpdate() > 0) {
                //Cap nhat trang thai bang CP_ALIAS_TMP (Neu co)
                String queryUdtCpAliasTmpStr
                    = "UPDATE CP_ALIAS_TMP\n"
                    + "   SET STATUS        = :P_STATUS,\n"
                    + "       UPDATE_DATE   = SYSDATE,\n"
                    + "       INACTIVE_DATE = NULL,\n"
                    + "       ACC_UPDATE    = :P_ACC_UPDATE\n"
                    + " WHERE CP_ALIAS_ID IN\n"
                    + "       (SELECT CP.CP_ALIAS_TMP_ID\n"
                    + "          FROM CP_ALIAS CP\n"
                    + "         WHERE CP.CP_ALIAS_ID = :P_CP_ALIAS_ID)";
                Query queryUdtCpAliasTmp = entityManager.createNativeQuery(queryUdtCpAliasTmpStr);
                queryUdtCpAliasTmp.setParameter("P_STATUS", action);
                queryUdtCpAliasTmp.setParameter("P_ACC_UPDATE", userName);
                queryUdtCpAliasTmp.setParameter("P_CP_ALIAS_ID", cpAliasId);
                queryUdtCpAliasTmp.executeUpdate();
                //Cap nhat trang thai bang CP_ALIAS
                String queryUdtCpAliasStr
                    = "UPDATE CP_ALIAS\n"
                    + "   SET STATUS        = :P_STATUS,\n"
                    + "       UPDATE_DATE   = SYSDATE,\n"
                    + "       INACTIVE_DATE = NULL,\n"
                    + "       ACC_UPDATE    = :P_ACC_UPDATE\n"
                    + " WHERE CP_ALIAS_ID = :P_CP_ALIAS_ID";
                Query queryUdtCpAlias = entityManager.createNativeQuery(queryUdtCpAliasStr);
                queryUdtCpAlias.setParameter("P_STATUS", action);
                queryUdtCpAlias.setParameter("P_ACC_UPDATE", userName);
                queryUdtCpAlias.setParameter("P_CP_ALIAS_ID", cpAliasId);
                int rls = queryUdtCpAlias.executeUpdate();
                if (Constants.STATUS_ACTIVE.equals(action)) {
                    writeInfoLog(rls > 0 ? ConstantsLog.TRANSACTION_STATUS.SUCCESS : ConstantsLog.TRANSACTION_STATUS.FAIL, rls > 0 ? "Kich hoat thanh cong" : "Kich hoat loi", date);
                } else {
                    writeInfoLog(rls > 0 ? ConstantsLog.TRANSACTION_STATUS.SUCCESS : ConstantsLog.TRANSACTION_STATUS.FAIL, rls > 0 ? "InactiveSucceeded" : "InactiveFailed", date);
                }
                if (rls <= 0) {
                    session.doWork(Connection::rollback);
                }
            } else {
                if (Constants.STATUS_ACTIVE.equals(action)) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Kich hoat loi: Khong insert duoc ban ghi backup vao bang CP_ALIAS!", date);
                } else {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "InactiveFailed: Khong insert duoc ban ghi backup vao bang CP_ALIAS!", date);
                }
                session.doWork(Connection::rollback);
            }
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            session.doWork(Connection::rollback);
            throw e;
        }
    }

    @Override
    public int deleteCpAlias(long cpAliasId, String username) {
        Date date = new Date();
        Integer result;
        try {
            String queryDelCpStr = "update cp_alias set status=2, end_date = sysdate, acc_update= :username where cp_alias_id =:cp_alias_id ";
            Query queryDelCp = entityManager.createNativeQuery(queryDelCpStr);
            queryDelCp.setParameter("username", username);
            queryDelCp.setParameter("cp_alias_id", cpAliasId);
            result = queryDelCp.executeUpdate();
            if (result < 0) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "deleteFail", date);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

    @Override
    public List<Object[]> getAliasGroup(Integer type) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT b.group_id, b.group_name ");
        query.append("FROM cp_alias_group b ");
        query.append("WHERE b.TYPE = :type ");
        query.append("GROUP BY b.group_name, b.group_id ");
        query.append("ORDER BY GROUP_NAME");

        return entityManager.createNativeQuery(query.toString()).setParameter("type", type).getResultList();
    }

    @Override
    public List<Object[]> getWS(Integer status) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT PARTNER_CODE, PARTNER_NAME ");
        query.append("FROM PARTNER p ");
        query.append("WHERE partner_vttelco =:status ");
        query.append("ORDER BY PARTNER_NAME");

        return entityManager.createNativeQuery(query.toString())
            .setParameter("status", status)
            .getResultList();
    }

    @Override
    public List<Object[]> getKeepFee() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT FEE_ID, FEE_VALUE ");
        query.append("FROM ALIAS_KEEP_FEE b ");
        query.append("ORDER BY FEE_VALUE");

        return entityManager.createNativeQuery(query.toString()).getResultList();
    }

    @Override
    public boolean checkAliasExitsInCreate(String cpAlias, String telco, Long cpId) {
        Date date = new Date();
        Boolean isDuplicate;
        try {
            String queryCheckCpAliasStr = "from CpAlias where cpAlias =:cpAlias and cpId =:cpId AND telco =:telco AND status <> 2";
            Query query = entityManager.createQuery(queryCheckCpAliasStr);
            query.setParameter("cpAlias", cpAlias);
            query.setParameter("cpId", cpId);
            query.setParameter("telco", telco);
            isDuplicate = query.getResultList().size() > 0;
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return isDuplicate;
    }

    @Override
    public boolean checkAliasExitsInEdit(String cpAlias, String telco, Long cpId, Long cpAliasId) {
        Date date = new Date();
        Boolean isDuplicate;
        try {
            String queryCheckCpAliasStr = "from CpAlias where cpAlias =:cpAlias and cpId =:cpId and cpAliasId != :cpAliasId AND telco =:telco AND status <> 2";
            Query query = entityManager.createQuery(queryCheckCpAliasStr);
            query.setParameter("cpAlias", cpAlias);
            query.setParameter("cpId", cpId);
            query.setParameter("cpAliasId", cpAliasId);
            query.setParameter("telco", telco);
            isDuplicate = query.getResultList().size() > 0;
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return isDuplicate;
    }

    @Override
    public String checkTaxCodeAndCompanyName(Long cpId, String taxCode, String companyName) {
        Date date = new Date();
        StringBuilder lstCpCode;
        try {
            Query query = entityManager.createNativeQuery("SELECT DISTINCT cpCode FROM ("
                + "SELECT TO_CHAR(CP.CP_CODE) cpCode\n"
                + "  FROM CP_ALIAS CPA\n"
                + "  JOIN CP\n"
                + "    ON CP.CP_ID = CPA.CP_ID\n"
                + " WHERE 1 = 1\n"
                + "   AND CPA.STATUS IN (0, 1, 3)\n"
                + "   AND CPA.CP_ID != :P_CP_ID\n"
                + "   AND (LOWER(FC_CONVERTVN2ROMAN(CPA.TAX_CODE)) = LOWER(FC_CONVERTVN2ROMAN(:P_TAX_CODE)) \n"
                + "       OR\n"
                + "       LOWER(FC_CONVERTVN2ROMAN(CPA.COMPANY_NAME)) = LOWER(FC_CONVERTVN2ROMAN(:P_COMPANY_NAME)))\n"
                + "UNION ALL\n"
                + "SELECT TO_CHAR(CP.CP_CODE) cpCode\n"
                + "  FROM CP\n"
                + " WHERE 1 = 1\n"
                + "   AND CP.STATUS IN (0, 1, 3)\n"
                + "   AND CP.CP_ID != :P_CP_ID\n"
                + "   AND (LOWER(FC_CONVERTVN2ROMAN(CP.TAX_CODE)) = LOWER(FC_CONVERTVN2ROMAN(:P_TAX_CODE)) \n"
                + "       OR\n"
                + "       LOWER(FC_CONVERTVN2ROMAN(CP.CP_NAME)) = LOWER(FC_CONVERTVN2ROMAN(:P_COMPANY_NAME))))");
            query.setParameter("P_CP_ID", cpId);
            query.setParameter("P_TAX_CODE", taxCode);
            query.setParameter("P_COMPANY_NAME", companyName);
            List<String> lstResult = query.getResultList();
            lstCpCode = new StringBuilder();
            if (!lstResult.isEmpty()) {
                for (String item : lstResult) {
                    lstCpCode.append((item)).append(", ");
                }
            }

            if (lstCpCode.length() > 0) {
                lstCpCode = new StringBuilder(lstCpCode.substring(0, lstCpCode.length() - 2));
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return lstCpCode.toString();
    }

    public Boolean updateCommissionDefault(Long cpId, String userId, String username) {
        Date date = new Date();
        String reason = Translator.toLocale("reasonAdminADJUST_COMMISSION_HIS");
        String sqlHis = " "
            + "INSERT INTO ADJUST_COMMISSION_HIS\n"
            + "  (CP_ID, USER_ID, UPDATED_DATE, REASON, OLD_VALUE, NEW_VALUE, USER_NAME)\n"
            + "  SELECT CP.CP_ID,\n"
            + "        CASE WHEN :P_USER_ID = '' THEN NULL " +
            "          ELSE :P_USER_ID end,\n"
            + "         SYSDATE,\n"
            + "         :P_REASON,\n"
            + "         CP.COMMISSION_PERCENT_CODE,\n"
            + "         'C0',\n"
            + "         :P_USER_NAME\n"
            + "    FROM CP  \n"
            + "  WHERE CP.CP_ID = :P_CP_ID  ";
        Query queryHis = entityManager.createNativeQuery(sqlHis);
        queryHis.setParameter("P_CP_ID", cpId);
        queryHis.setParameter("P_USER_ID", "");
        queryHis.setParameter("P_REASON", reason);
        queryHis.setParameter("P_USER_NAME", username);
        if (queryHis.executeUpdate() > 0) {
            String sql = " UPDATE CP SET CP.COMMISSION_PERCENT_CODE = 'C0' WHERE CP.CP_ID = :P_CP_ID ";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("P_CP_ID", cpId);
            Boolean updateCount = query.executeUpdate() > 0;
            if (!updateCount) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong cap nhat duoc ma tinh phi hoa hong = C0 cho CP_ID:" + cpId, date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "UpdateSuccess", date);
            }
            return updateCount;
        } else {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong cap nhat duoc ma tinh phi hoa hong = C0 cho CP_ID:" + cpId, date);
        }
        return false;
    }

    @Override
    public void updateCpAliasTmp(CpAlias currentAlias) {
        if (currentAlias.getCpAliasTmpId() != null && currentAlias.getCpAliasTmpId() > 0) {
            String sqlUpdateTmp = ""
                + "UPDATE CP_ALIAS_TMP\n"
                + "   SET CP_ALIAS             = :P_CP_ALIAS,\n"
                + "       STATUS               = :P_STATUS,\n"
                + "       GROUP_TYPE           = :P_GROUP_TYPE,\n"
                + "       TELCO                = :P_TELCO,\n"
                + "       WEBSERVICE           = :P_WEBSERVICE,\n"
                + "       WEBSERVICE_BACKUP    = :P_WEBSERVICE_BACKUP,\n"
                + "       ALIAS_TYPE           = :P_ALIAS_TYPE,\n"
                + "       OPTIONAL_KEEP_FEE    = :P_OPTIONAL_KEEP_FEE,\n"
                + "       KEEP_FEE             = :P_KEEP_FEE,\n"
                + "       ACC_UPDATE           = :P_ACC_UPDATE,\n"
                + "       UPDATE_DATE          = SYSDATE,\n"
                + "       INACTIVE_DATE        = (CASE WHEN :P_INACTIVE_DATE = '' THEN NULL ELSE :P_INACTIVE_DATE END),\n"
                + "       ATTACH_FILE          = :P_ATTACH_FILE, \n"
                + "       TIME_REPEAT          = :P_TIME_REPEAT, \n"
                + "       CHECK_BLOCK_SPAM     = :P_CHECK_BLOCK_SPAM,\n"
                + "       ACCEPT_PARENTCP_SEND = :P_ACCEPT_PARENTCP_SEND,\n"
                + "       CHECK_DUPLICATE      = :P_CHECK_DUPLICATE, \n"
                + "       COMPANY_NAME         = :P_COMPANY_NAME,\n"
                + "       TAX_CODE             = :P_TAX_CODE,\n"
                + "       NUMBER_SMS_CHECK_SPAM= (CASE WHEN :P_NUMBER_SMS_CHECK_SPAM = '' THEN NULL ELSE :P_NUMBER_SMS_CHECK_SPAM END), \n"
                + "       MONTH_KEEP_FEE       = (CASE WHEN :P_MONTH_KEEP_FEE = '' THEN NULL ELSE :P_MONTH_KEEP_FEE END) \n"
                + " WHERE CP_ALIAS_ID = :P_CP_ALIAS_ID AND APPROVE != :P_APPROVECANCEL_CONSTANTS ";
            Query query = entityManager.createNativeQuery(sqlUpdateTmp);
            query.setParameter("P_CP_ALIAS", currentAlias.getCpAlias());
            query.setParameter("P_STATUS", currentAlias.getStatus());
            query.setParameter("P_GROUP_TYPE", currentAlias.getGroupType());
            query.setParameter("P_TELCO", currentAlias.getTelco());
            query.setParameter("P_WEBSERVICE", currentAlias.getWebservice());
            query.setParameter("P_WEBSERVICE_BACKUP", currentAlias.getWebserviceBackup());
            query.setParameter("P_ALIAS_TYPE", currentAlias.getAliasType());
            query.setParameter("P_OPTIONAL_KEEP_FEE", currentAlias.getOptionalKeepFee());
            query.setParameter("P_KEEP_FEE", currentAlias.getKeepFee());
            query.setParameter("P_ACC_UPDATE", currentAlias.getAccUpdate());
            query.setParameter("P_INACTIVE_DATE", currentAlias.getInactiveDate() == null ? "" : currentAlias.getInactiveDate());
            query.setParameter("P_ATTACH_FILE", currentAlias.getAttachFile());
            query.setParameter("P_TIME_REPEAT", currentAlias.getTimeRepeat());
            query.setParameter("P_CHECK_BLOCK_SPAM", currentAlias.getCheckBlockSpam());
            query.setParameter("P_ACCEPT_PARENTCP_SEND", currentAlias.getAcceptParentCpSend());
            query.setParameter("P_CHECK_DUPLICATE", currentAlias.getCheckDuplicate());
            query.setParameter("P_COMPANY_NAME", currentAlias.getCompanyName());
            query.setParameter("P_TAX_CODE", currentAlias.getTaxCode());
            query.setParameter("P_NUMBER_SMS_CHECK_SPAM", currentAlias.getNumberSmsCheckSpam() == null ? "" : currentAlias.getNumberSmsCheckSpam());
            query.setParameter("P_MONTH_KEEP_FEE", currentAlias.getMonthKeepFee() == null ? "" : currentAlias.getMonthKeepFee());
            query.setParameter("P_CP_ALIAS_ID", currentAlias.getCpAliasTmpId());
            query.setParameter("P_APPROVECANCEL_CONSTANTS", Constants.CP_ALIAS_TMP_APPROVE_APPROVE_CANCEL);
            if (query.executeUpdate() < 1) {
                logger.info("update to CP_ALIAS_TMP fail with cpAliasId: {}", currentAlias.getCpAliasId());
                throw new BusinessException(ErrorCodeResponse.UNKNOWN);
            }
        }
    }

    @Override
    public CpAlias getCpAlias(long cpAliasId) {
        String queryGetCpStr = "from CpAlias where cpAliasId = :cpAliasId";
        Query queryGetCp = entityManager.createQuery(queryGetCpStr);
        queryGetCp.setParameter("cpAliasId", cpAliasId);
        return (CpAlias) queryGetCp.getSingleResult();
    }

    @Override
    public CpAliasTmpFileDTO getCpAliasTmpByCpAliasID(Long cpAliasId) {
        Date date = new Date();
        CpAliasTmpFileDTO cpAliasTmpFileDTO = new CpAliasTmpFileDTO();
        try {
            Query query = entityManager.createNativeQuery(""
                + "SELECT TMP.CP_ALIAS_ID      cpAliasId,\n"
                + "       TMP.FILE_PATH_CREATE filePathCreate,\n"
                + "       TMP.FILE_PATH_CANCEL filePathCancel\n"
                + "  FROM CP_ALIAS CP\n"
                + "  JOIN CP_ALIAS_TMP TMP\n"
                + "    ON TMP.CP_ALIAS_ID = CP.CP_ALIAS_TMP_ID\n"
                + " WHERE CP.CP_ALIAS_ID = :P_CP_ALIAS_ID");
            query.setParameter("P_CP_ALIAS_ID", cpAliasId);
            List<Object[]> list = query.getResultList();
            if (!DataUtil.isNullOrEmpty(list)) {
                cpAliasTmpFileDTO.setCpAliasId(DataUtil.safeToLong(list.get(0)[0]));
                cpAliasTmpFileDTO.setFilePathCreate(DataUtil.safeToString(list.get(0)[1]));
                cpAliasTmpFileDTO.setFilePathCancel(DataUtil.safeToString(list.get(0)[2]));
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return cpAliasTmpFileDTO;
    }

    @Override
    public boolean chargeCancelAlias(Long cpId, String cpCode, String telco, String cpAlias, String currency, String aliasType) {
        Date date = new Date();
        try {
            Query cpQuery = entityManager.createQuery("select chargeType from Cp where cpId = :cpId").setParameter("cpId", cpId);
            List cp = cpQuery.getResultList();
            String chargeType = String.valueOf(cp.get(0)).trim();
            if ("PRE".equals(chargeType)) {
                Query cancelAliasCostQuery = entityManager.createQuery("select cancelAliasCost from AliasCost where aliasTelco = :telco and aliasCostType = :costType");
                cancelAliasCostQuery.setParameter("telco", telco);
                cancelAliasCostQuery.setParameter("costType", Long.parseLong(aliasType));
                Query cancelAliasCostUsdQuery = entityManager.createQuery("select cancelAliasCostUSD from AliasCost where aliasTelco = :telco and aliasCostType = :costType");
                cancelAliasCostUsdQuery.setParameter("telco", telco);
                cancelAliasCostUsdQuery.setParameter("costType", Long.parseLong(aliasType));
                List aliasCost = cancelAliasCostQuery.getResultList();
                List aliasCostUsd = cancelAliasCostUsdQuery.getResultList();
                BigDecimal regAliasCost = new BigDecimal(aliasCost.get(0).toString());
                BigDecimal regAliasCostUsd = new BigDecimal(aliasCostUsd.get(0).toString());

                if ("6000".equals(cpAlias.trim())) {
                    return true;
                }
                if (Constants.VND.equals(currency) && regAliasCost.longValue() > 0) {
                    boolean chargeResult = chargingService.charge(cpCode, regAliasCost, 2, "charge_CancelAliasCost", cpAlias + "_aliasfee", Integer.parseInt(aliasType));
                    if (!chargeResult) {
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
                        throw new BusinessException(ErrorCodeResponse.NOT_MONEY);
                    } else {
                        //insert trans_log_alias
                        TransLogAlias transLogAlias = new TransLogAlias();
                        transLogAlias.setCpId(cpId);
                        transLogAlias.setAlias(cpAlias + "_aliasfee");
                        transLogAlias.setAmount(regAliasCost.negate());
                        transLogAlias.setTransTime(new java.sql.Date(System.currentTimeMillis()));
                        transLogAliasRepository.save(transLogAlias);
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insert trans_log_alias VND success", date);
                    }
                } else if (Constants.USD.equals(currency) && regAliasCostUsd.longValue() > 0) {
                    boolean chargeResult = chargingService.charge(cpCode, regAliasCostUsd, 2, "charge_CancelAliasCostUsd", cpAlias + "_aliasfee", Integer.parseInt(aliasType));
                    if (!chargeResult) {
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
                        throw new BusinessException(ErrorCodeResponse.NOT_MONEY);
                    } else {
                        //insert trans_log_alias
                        TransLogAlias transLogAlias = new TransLogAlias();
                        transLogAlias.setCpId(cpId);
                        transLogAlias.setAlias(cpAlias + "_aliasfee");
                        transLogAlias.setAmount(regAliasCostUsd.negate());
                        transLogAlias.setTransTime(new java.sql.Date(System.currentTimeMillis()));
                        transLogAliasRepository.save(transLogAlias);
                        writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insert trans_log_alias USD success", date);
                    }
                }
            }
        } catch (Exception ex) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "Khong tru duoc tien trong Tai khoan", date);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return true;
    }

    @Override
    public int deleteCpAliasTmp(Long cpAliasId) {
        Date date = new Date();
        int result;
        try {
            String accUpdate = SecurityUtils.getCurrentUserLogin().get();
            String queryDelCpStr = "update CP_ALIAS_TMP set status=2, end_date = sysdate, UPDATE_DATE = sysdate,  acc_update= :accUpdate where cp_alias_id = :cpAliasId";
            Query queryDelCp = entityManager.createNativeQuery(queryDelCpStr);
            queryDelCp.setParameter("accUpdate", accUpdate);
            queryDelCp.setParameter("cpAliasId", cpAliasId);
            result = queryDelCp.executeUpdate();
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return result;
    }

    @Override
    public int countAlias(Long cpId, String alias, String aliasType, String accUpdate) {
        Date date = new Date();
        int result;
        try {
            String sql = "SELECT count(*) FROM cp_alias WHERE cp_alias = :cpAlias AND cp_id = :cpId "
                + "AND status IN (0, 1, 3) AND alias_type = :type AND acc_update = :accUpdate";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("cpAlias", alias);
            query.setParameter("cpId", cpId);
            query.setParameter("type", Long.valueOf(aliasType));
            query.setParameter("accUpdate", accUpdate);
            result = DataUtil.safeToInt(query.getSingleResult());
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return result;
    }

    @Override
    public int deleteCpUsersAlias(Long cpId, String alias, String aliasType, String accUpdate) {
        Date date = new Date();
        int result;
        try {
            String sql = "DELETE FROM cp_users_alias WHERE cp_id = :cpId AND alias = :alias AND alias_type = :type";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("cpId", cpId);
            query.setParameter("alias", alias);
            query.setParameter("type", Long.valueOf(aliasType));
            result = query.executeUpdate();
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return result;
    }
}

