package com.viettel.smsbrandname.repository.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DbUtils;
import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.domain.Prog;
import com.viettel.smsbrandname.repository.ProgRepository;


import com.viettel.smsbrandname.commons.MaperUtils;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsSearchDTO;

import com.viettel.smsbrandname.repository.ProgRepository;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgAdvertisementSearchDTO;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgResultDTO;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.dto.response.CPProgAdvertisementResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProgRepositoryImpl implements ProgRepository {
    private static final Logger logger1 = LoggerFactory.getLogger(ProgRepositoryImpl.class);
    private static String SEARCH_PROG_QUERY_COUNT = "select count(*) from PROG PR where 1=1 ";
    private final Logger logger = LoggerFactory.getLogger(ProgRepositoryImpl.class);
    @Value("${pre_fix}")
    private String preFix;
    @Value("${suf_fix}")
    private String sufFix;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ComboBean> getListCheckBox() {
        String sql = "select DISTINCT cp.cp_id value, cp.cp_code label from cp where cp.status = 1 order by cp.cp_code,cp.cp_id asc";
        Query query = entityManager.createNativeQuery(sql.toString());
        return new MaperUtils(query.getResultList()).add("value").add("label").transform(ComboBean.class);
    }

    @Override
    public Page<CareCustomerSmsSearchDTO> doSearch(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO, Long provinceid, boolean isExport) {
        List<CareCustomerSmsSearchDTO> lstVA = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        HashMap<String, Object> hmap = new HashMap<>();
        int total = 0;
        int currentPage = careCustomerSmsSearchDTO.getCurrentPage();
        int pageSize = careCustomerSmsSearchDTO.getPageSize();
        StringBuilder sql = new StringBuilder("select  row_number() over(order by PROG.create_date DESC, PROG.prog_code DESC) rn, " +
            "CP.CP_ID cpId,\n" +
            "PROG.PROG_ID progId," +
            "CP.CP_NAME cpName, \n" +
            "CP.CP_CODE cpCode,\n" +
            "PROG.PROG_CODE progCode,\n" +
            "PROG.TYPE type,\n" +
            "PROG.CONTENT content,\n" +
            "PROG.ALIAS alias,\n" +
            "to_char(PROG.CREATE_DATE,'dd/MM/yyyy hh24:mi:ss') createDate,\n" +
            "to_char(PROG.SENT_SCHEDULE, 'dd/MM/yyyy hh24:mi:ss') sentSchedule,\n " +
            "PROG.STATUS statusIT  from PROG inner join CP on prog.CP_ID = CP.CP_ID and cp.status = 1 \n" +
            " WHERE prog.prog_type = 0   AND prog.deleted = 0 ");
        if (provinceid != null && provinceid != -1L) {
            sql.append(" and CP.province_id = :provinceid ");
            hmap.put("provinceid", provinceid);
        }

        if (StringUtils.isNoneEmpty(careCustomerSmsSearchDTO.getCpCode())) {
            sql.append(" and CP.CP_CODE = :cpCode ");
            hmap.put("cpCode", careCustomerSmsSearchDTO.getCpCode());
        }
        if (careCustomerSmsSearchDTO.getCpId() != -1) {
            sql.append(" and CP.CP_ID = :cpId");
            hmap.put("cpId", careCustomerSmsSearchDTO.getCpId());
        }
        if (StringUtils.isNotEmpty(careCustomerSmsSearchDTO.getProgCode())) {
            sql.append(" and upper(PROG.PROG_CODE) like  upper(:progCode) escape '&' ");
            hmap.put("progCode", "%" + careCustomerSmsSearchDTO.getProgCode() + "%");
        }
        if (!careCustomerSmsSearchDTO.getStatusIT().equals("-1")) {
            sql.append(" and PROG.STATUS= :statusIt");
            hmap.put("statusIt", careCustomerSmsSearchDTO.getStatusIT());
        }
        if (careCustomerSmsSearchDTO.getCreateDate() != null) {
            sql.append(" and TO_CHAR(PROG.CREATE_DATE, 'DD/MM/YYYY') = :createDate");
            hmap.put("createDate", format.format(careCustomerSmsSearchDTO.getCreateDate()));
        }
        if (careCustomerSmsSearchDTO.getSentSchedule() != null) {
            sql.append(" and TO_CHAR(PROG.SENT_SCHEDULE,'DD/MM/YYYY') = :sentSchedule");
            hmap.put("sentSchedule", format.format(careCustomerSmsSearchDTO.getSentSchedule()));
        }
        String sqlCount = "SELECT COUNT(*) FROM (" + sql.toString() + ")";
        try {
            Query query = entityManager.createNativeQuery(sql.toString());
            hmap.forEach(query::setParameter);
            if (!isExport) {
                Query queryCount = entityManager.createNativeQuery(sqlCount);
                hmap.forEach(queryCount::setParameter);
                total = ((BigDecimal) queryCount.getSingleResult()).intValue();
                query.setFirstResult(currentPage * pageSize);
                query.setMaxResults(pageSize);
            }
            List<Object[]> listSearch = query.getResultList();
            MaperUtils maper = new MaperUtils(listSearch);
            lstVA = maper
                .add("stt")
                .add("cpId")
                .add("progId")
                .add("cpName")
                .add("cpCode")
                .add("progCode")
                .add("type")
                .add("content")
                .add("alias")
                .add("createDate")
                .add("sentSchedule")
                .add("statusIT")
                .transform(CareCustomerSmsSearchDTO.class);
        } catch (Exception e) {
            logger.error("Loi khi thuc hien search" + e);
        }
        return new PageImpl<>(lstVA, PageRequest.of(currentPage, pageSize), total);
    }

    @Override
    public CareCustomerSmsSearchDTO getCareCustomerSmsSearchDTOById(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO) {
        List<CareCustomerSmsSearchDTO> lstVA = new ArrayList<>();
        HashMap<String, Object> hmap = new HashMap<>();
        StringBuilder sql = new StringBuilder("select " +
            "CP.CP_ID cpId,\n" +
            "PROG.PROG_ID progId,\n" +
            "CP.CP_NAME cpName, \n" +
            "CP.CP_CODE cpCode,\n" +
            "PROG.PROG_CODE progCode,\n" +
            "PROG.TYPE type,\n" +
            "PROG.CONTENT content,\n" +
            "PROG.ALIAS alias,\n" +
            "PROG.LINK link,\n" +
            "PROG.CONVERT_VN convertVN,\n" +
            "to_char(PROG.CREATE_DATE,'dd/MM/yyyy hh24:mi:ss') createDate,\n" +
            "to_char(PROG.SENT_SCHEDULE, 'dd/MM/yyyy hh24:mi:ss') sentSchedule,\n " +
            "PROG.STATUS statusIT, " +
            "       PROG.group_name_list groupNameList,\n" +
            "       PROG.group_sex       groupSex\n" +
            " from PROG inner join CP on prog.CP_ID = CP.CP_ID and cp.status = 1 \n" +
            "where prog.prog_type = 0   AND prog.deleted = 0  ");
        if (careCustomerSmsSearchDTO.getCpId() != null) {
            sql.append(" and CP.CP_ID = :cpId ");
            hmap.put("cpId", careCustomerSmsSearchDTO.getCpId());
        }
        if (careCustomerSmsSearchDTO.getProgId() != null) {
            sql.append(" and PROG.PROG_ID= :progId ");
            hmap.put("progId", careCustomerSmsSearchDTO.getProgId());
        }
        try {

            Query query = entityManager.createNativeQuery(sql.toString());
            hmap.forEach(query::setParameter);
            List<Object[]> listSearch = query.getResultList();
            MaperUtils maper = new MaperUtils(listSearch);
            lstVA = maper
                .add("cpId")
                .add("progId")
                .add("cpName")
                .add("cpCode")
                .add("progCode")
                .add("type")
                .add("content")
                .add("alias")
                .add("link")
                .add("convertVN")
                .add("createDate")
                .add("sentSchedule")
                .add("statusIT")
                .add("groupNameList")
                .add("groupSex")

                .transform(CareCustomerSmsSearchDTO.class);
        } catch (Exception e) {
            logger.error("Loi khi thuc hien search" + e);
        }
        return lstVA.get(0);
    }


    @Override
    public boolean updateProg(Long progId, Long status) {
        StringBuilder sql = new StringBuilder(" update PROG set PROG.STATUS = :status where PROG.PROG_ID = :progId ");
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("progId", progId);
        Query query = entityManager.createNativeQuery(sql.toString());
        map.forEach(query::setParameter);
        int tag = -1;
        boolean isTrue = false;

        try {
            tag = query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tag != -1) {
            isTrue = true;
        }
        return isTrue;
    }


    @Override
    public CPProgAdvertisementResponseDTO searchAdvertisement(CPProgAdvertisementSearchDTO cpProgSearchDTO, Long provinceid) {
        StringBuilder sql = new StringBuilder();
        List<Object[]> list = null;
        CPProgAdvertisementResponseDTO responseDTO = new CPProgAdvertisementResponseDTO();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            sql.append("select row_number() over(order by a.create_date DESC, a.prog_code DESC) rn, " +
                " a.PROG_ID, a.PROG_CODE , a.ALIAS, alg.GROUP_NAME,a.CAT_ID, a.CONTENT, a.STATUS,a.TESTED, TO_CHAR(a.CREATE_DATE, 'DD/MM/YYYY') CREATE_DATE, \n" +
                " a.CREATE_USER,a.IS_ADMIN_UPLOAD, TO_CHAR(a.START_DATE, 'DD/MM/YYYY') START_DATE,TO_CHAR(a.FINISH_DATE, 'DD/MM/YYYY') FINISH_DATE, a.MAX_SMS, a.TYPE,a.LINK,a.TOTAL_SUB,\n" +
                " a.CONVERT_VN,a.SEND_DAY, a.EXCEPTION_DAY, a.START_TIME_ZONE,a.FINISH_TIME_ZONE,a.NEED_APPROVE_RUN,b.CAT_NAME , :PREFIX , :SUFFIX , a.SENT_TIME_ZONE, " +
                " TO_CHAR(a.start_date, 'DD/MM/YYYY'), c.CP_CODE,  c.province_id from prog a \n" +
                " join ad_category b on a.cat_id = b.id join cp c on a.cp_id = c.cp_id\n" +
                " left join (select distinct cp_id, group_type, cp_alias, alias_type, status from cp_alias ca where ca.alias_type = 1  and CA.status = 1 and CA.telco = 'VT')\n" +
                " ca on ca.cp_id = a.cp_id and ca.cp_alias = a.alias \n" +
                " left join (select alias_group_type, group_id, group_name from cp_alias_group where type = 0 group by alias_group_type, group_id, group_name) alg \n" +
                " on alg.alias_group_type = ca.alias_type and alg.group_id = ca.group_type where a.deleted = 0 AND a.prog_type = 1 AND c.cp_code NOT IN ('SHOPONE')  ");
            HashMap<String, Object> params = new HashMap();
            StringBuilder sbCount = new StringBuilder(" select count(*) from ( ");
            sbCount.append(sql);
            sbCount.append(" ) where 1=1 ");
            if (provinceid != null && provinceid != -1L) {
                sql.append(" and c.province_id = :provinceid ");
                sbCount.append(" and  province_id = :provinceid ");
                params.put("provinceid", cpProgSearchDTO.getCpId());
            }
            if (!DataUtil.isNullOrEmpty(cpProgSearchDTO.getCpId())) {
                sql.append(" and  lower(c.cp_code)  like  lower(:cpId ) ");
                sbCount.append(" and  lower(cp_code)  like  lower(:cpId ) ");
                params.put("cpId", "%" + cpProgSearchDTO.getCpId() + "%");
            }
            if (!DataUtil.isNullOrEmpty(cpProgSearchDTO.getProgCode())) {
                sql.append(" and lower(a.PROG_CODE) like lower(:progCode)");
                sbCount.append(" and lower(PROG_CODE) like lower(:progCode) ");
                params.put("progCode", "%" + cpProgSearchDTO.getProgCode() + "%");
            }
            if (!DataUtil.isNullOrEmpty(cpProgSearchDTO.getCatId())) {
                sql.append(" and a.CAT_ID = :catId");
                sbCount.append(" and CAT_ID = :catId");
                params.put("catId", cpProgSearchDTO.getCatId());
            }
            if (!DataUtil.isNullOrEmpty(cpProgSearchDTO.getProgStatus())) {
                sql.append(" and a.STATUS = :progStatus");
                sbCount.append(" and STATUS = :progStatus");
                params.put("progStatus", cpProgSearchDTO.getProgStatus());
            }
            if (!DataUtil.isNullOrEmpty(cpProgSearchDTO.getProgCreateDate())) {
                String progCreateDate = format.format(cpProgSearchDTO.getProgCreateDate());
                sql.append(" and  TO_CHAR(a.CREATE_DATE, 'DD/MM/YYYY')   = :progCreateDate");
                sbCount.append(" and  CREATE_DATE   = :progCreateDate");
                params.put("progCreateDate", progCreateDate);
            }
            if (!DataUtil.isNullOrEmpty(cpProgSearchDTO.getProgSentSchedule())) {
                String progSentSchedule = format.format(cpProgSearchDTO.getProgSentSchedule());
                sql.append(" and  TO_CHAR(a.start_date, 'DD/MM/YYYY')  = :startState");
                sbCount.append(" and  start_date  = :startState");
                params.put("startState", progSentSchedule);
            }
            Query query = entityManager.createNativeQuery(sql.toString());
            Query queryCount = entityManager.createNativeQuery(sbCount.toString());
            query.setParameter("PREFIX", preFix);
            query.setParameter("SUFFIX", sufFix);
            queryCount.setParameter("PREFIX", preFix);
            queryCount.setParameter("SUFFIX", sufFix);
            DbUtils.setParramToQuery(query, params);
            DbUtils.setParramToQuery(queryCount, params);
            list = query.setFirstResult(cpProgSearchDTO.getCurrentPage() * cpProgSearchDTO.getPageSize()).setMaxResults(cpProgSearchDTO.getPageSize()).getResultList();
            Integer count = DataUtil.safeToInt(queryCount.getSingleResult());
            responseDTO.setListData(CPProgResultDTO.toDto(list));
            responseDTO.setCount(count);
            return responseDTO;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
