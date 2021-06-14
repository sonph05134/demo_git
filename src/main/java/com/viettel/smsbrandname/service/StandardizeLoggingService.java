package com.viettel.smsbrandname.service;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.dto.LogBeanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class StandardizeLoggingService {
    private final Logger logger = LoggerFactory.getLogger(StandardizeLoggingService.class);

    private final EntityManager entityManager;

    public StandardizeLoggingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void writeKPILog(Long errorCode, String mess, boolean success, Date startTime, String className, String methodName) {
        String info;
        Date date = new Date();
        String strSql = ""
            + "INSERT INTO KPI_LOG\n"
            + "  (KPI_LOG_ID,\n"
            + "   APPLICATION_CODE,\n"
            + "   SERVICE_CODE,\n"
            + "   SESSION_ID,\n"
            + "   IP_PORT_PARENT_NODE,\n"
            + "   IP_PORT_CURRENT_NODE,\n"
            + "   REQUEST_CONTENT,\n"
            + "   RESPONSE_CONTENT,\n"
            + "   START_TIME,\n"
            + "   END_TIME,\n"
            + "   DURATION,\n"
            + "   ERROR_CODE,\n"
            + "   ERROR_DESCRIPTION,\n"
            + "   TRANSACTION_STATUS,\n"
            + "   ACTION_NAME,\n"
            + "   ACCOUNT,\n"
            + "   USER_NAME)\n"
            + "SELECT\n"
            + "  KPI_LOG_SEQ.NEXTVAL,\n"
            + "   :P_APPLICATION_CODE,\n"
            + "   :P_SERVICE_CODE,\n"
            + "   :P_SESSION_ID,\n"
            + "   :P_IP_PORT_PARENT_NODE,\n"
            + "   :P_IP_PORT_CURRENT_NODE,\n"
            + "   :P_REQUEST_CONTENT,\n"
            + "   :P_RESPONSE_CONTENT,\n"
            + "   TO_DATE(:P_START_TIME, 'dd/MM/yyyy HH24:mi:ss'),\n"
            + "   TO_DATE(:P_END_TIME, 'dd/MM/yyyy HH24:mi:ss'),\n"
            + "   :P_DURATION,\n"
            + "   :P_ERROR_CODE,\n"
            + "   :P_ERROR_DESCRIPTION,\n"
            + "   :P_TRANSACTION_STATUS,\n"
            + "   :P_ACTION_NAME,\n"
            + "   :P_ACCOUNT,\n"
            + "   :P_USER_NAME FROM DUAL";
        try {
            String path = "";
            String ipAddress = "";
            String sessionId = "";
            String userName = SecurityUtils.getCurrentUserLogin().get();
            HttpServletRequest request = RequestContextHolder.getRequestAttributes() != null ? ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
            if (request != null) {
                ipAddress = !DataUtil.isNullOrEmpty(request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
                path = request.getServerName() + ":" + request.getServerPort();
                sessionId = !DataUtil.isNullOrEmpty(request.getRequestedSessionId()) ? request.getRequestedSessionId() : request.getSession().getId();
            }
            String param = this.getAllParams(request.getParameterMap());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Timestamp startTimeS = new Timestamp(startTime.getTime());
            Timestamp curTimes = new Timestamp(date.getTime());
            Long duration = curTimes.getTime() - startTimeS.getTime();
            Query query = entityManager.createNativeQuery(strSql);
            query.setParameter("P_APPLICATION_CODE", ConstantsLog.APP_CODE.APPLICATION_CODE);
            query.setParameter("P_SERVICE_CODE", ConstantsLog.APP_CODE.CMSAdmin);
            query.setParameter("P_SESSION_ID", sessionId);
            query.setParameter("P_IP_PORT_PARENT_NODE", "127.0.0.1");
            query.setParameter("P_IP_PORT_CURRENT_NODE", path);
            query.setParameter("P_REQUEST_CONTENT", param);
            query.setParameter("P_RESPONSE_CONTENT", mess);
            query.setParameter("P_START_TIME", df.format(startTime));
            query.setParameter("P_END_TIME", df.format(new Date()));
            query.setParameter("P_DURATION", duration);
            query.setParameter("P_ERROR_CODE", errorCode);
            query.setParameter("P_ERROR_DESCRIPTION", mess);
            query.setParameter("P_TRANSACTION_STATUS", success ? "0" : "1");
            query.setParameter("P_ACTION_NAME", className + "." + methodName);
            query.setParameter("P_ACCOUNT", ipAddress);
            query.setParameter("P_USER_NAME", userName);
            int insertedRecord = query.executeUpdate();
            if (insertedRecord < 1) {
                info = writeLog(ConstantsLog.LOG_TYPE.ERROR,
                    ConstantsLog.ACTION_TYPE.OTHER,
                    ConstantsLog.APP_CODE.CMSAdmin,
                    date,
                    "",
                    "Error writeLog function: Can't insert KPI_LOG table!",
                    null,
                    "writeKPILog",
                    "StandardizeLogging",
                    "",
                    "",
                    "");
                logger.info(info);
            }
        } catch (Exception e) {
            info = writeLog(ConstantsLog.LOG_TYPE.ERROR,
                ConstantsLog.ACTION_TYPE.OTHER,
                ConstantsLog.APP_CODE.CMSAdmin,
                date,
                "",
                "Error writeLog function: " + e.toString() + "(lineNumber: " + ((e.getStackTrace() != null && e.getStackTrace().length > 0) ? e.getStackTrace()[0].getLineNumber() : "-1") + ")",
                null,
                "writeKPILog",
                "StandardizeLogging",
                "",
                "",
                "");
            logger.error(info, e);
        }
    }

    private String getAllParams(Map<String, String[]> params) {
        try {
            String requestParams = "";
            if (!DataUtil.isNullOrEmpty(params)) {
                for (Map.Entry<String, String[]> entry : params.entrySet()) {
                    requestParams += entry.getKey() + ":" + entry.getValue()[0] + ";";
                }
            }
            if (requestParams.length() > 4000) {
                requestParams = requestParams.substring(0, 4000);
            }
            return requestParams;
        } catch (Exception e) {
            String info = writeLog(ConstantsLog.LOG_TYPE.ERROR,
                ConstantsLog.ACTION_TYPE.OTHER,
                ConstantsLog.APP_CODE.CMSAdmin,
                new Date(),
                "",
                "Error writeLog function: " + e.toString() + "(lineNumber: " + ((e.getStackTrace() != null && e.getStackTrace().length > 0) ? e.getStackTrace()[0].getLineNumber() : "-1") + ")",
                null,
                "getAllParams",
                "StandardizeLogging",
                "",
                "",
                "");
            logger.error(info, e);
        }
        return "";
    }

    private String writeLog(String logType, String actionType, String appCode, Date startTime, String result, String description, Map<String, String[]> params, String function, String _class, String userName, String path, String ipAddress) {
        try {
            LogBeanDTO logB = this.convertToObjectLogBean(logType, actionType, appCode, startTime, result, description, params, function, _class, userName, path, ipAddress);
            String log;
            if ("end_action".equals(logType)) {
                log = logB.getLOG_TYPE() + "|" + logB.getAPP_CODE() + "|" + logB.getSTART_TIME() + "|" + logB.getUSERNAME() + "|" + logB.getIP_ADDRESS() + "|" + logB.getPATH() + "|" + logB.getFUNCTION() + "|" + logB.getPARAM_LIST() + "|" + logB.getCLASS() + "|" + logB.getDURATION() + "|" + actionType + "_" + logB.getDESCRIPTION();
            } else if ("error".equals(logType)) {
                log = logB.getLOG_TYPE() + "|" + logB.getAPP_CODE() + "|" + logB.getSTART_TIME() + "|" + logB.getUSERNAME() + "|" + logB.getIP_ADDRESS() + "|" + logB.getPATH() + "|" + logB.getFUNCTION() + "|" + logB.getPARAM_LIST() + "|" + logB.getCLASS() + "|" + actionType + "_" + logB.getDESCRIPTION() + "|" + logB.getERROR_CODE();
            } else {
                log = logB.getLOG_TYPE() + "|" + logB.getSTART_TIME() + "|" + logB.getAPP_CODE() + "|" + logB.getUSERNAME() + "|" + logB.getIP_ADDRESS() + "|" + logB.getPATH() + "|" + logB.getRESULT() + "|" + logB.getDURATION();
            }

            return log;
        } catch (Exception var15) {
            logger.error(var15.toString(), var15);
            return "errorWriteLog";
        }
    }

    private LogBeanDTO convertToObjectLogBean(String logType, String actionType, String appCode, Date startTime, String result, String description, Map<String, String[]> params, String function, String _class, String userName, String path, String ipAddress) {
        LogBeanDTO logB = new LogBeanDTO();
        logB.setLOG_TYPE(logType);
        logB.setACTION_TYPE(actionType);
        logB.setRESULT(!DataUtil.isNullOrEmpty(result) ? result : "null");
        logB.setDESCRIPTION(!DataUtil.isNullOrEmpty(description) ? description : "null");
        logB.setFUNCTION(!DataUtil.isNullOrEmpty(function) ? function : "null");
        logB.setCLASS(!DataUtil.isNullOrEmpty(_class) ? _class : "null");
        logB.setAPP_CODE(appCode);
        logB.setERROR_CODE("null");
        logB.setPATH(path);
        logB.setIP_ADDRESS(ipAddress);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:ms");
        Timestamp startTimeS = new Timestamp(startTime.getTime());
        Date date = new Date();
        logB.setSTART_TIME(df.format(startTime));
        Timestamp curTimes = new Timestamp(date.getTime());
        Long duration = curTimes.getTime() - startTimeS.getTime();
        logB.setDURATION(duration);
        logB.setUSERNAME(userName);
        String param = this.getAllParams(params);
        logB.setPARAM_LIST(param);
        return logB;
    }
}
