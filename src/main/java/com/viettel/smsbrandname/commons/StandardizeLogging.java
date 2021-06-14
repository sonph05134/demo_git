package com.viettel.smsbrandname.commons;

import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.StandardizeLoggingService;
import com.viettel.smsbrandname.service.dto.LogBeanDTO;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.viettel.log.util.ConstantsLog; //add lib StandardizeLog

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class StandardizeLogging {

    @Value("${emailConf.techEmail}")
    private String techEmail;

    protected Logger logger;

    @Autowired
    StandardizeLoggingService standardizeLoggingService;

    //tam thoi set = true de ko gui email
    private Boolean TEST_SYSTEM = true;

    public StandardizeLogging(Class subClass) {
        logger = LoggerFactory.getLogger(subClass);
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

    public void writeKPILog(Long errorCode, String mess, boolean success, Date startTime) {
        String info;
        String className = "";
        String function = "";
        Date date = new Date();
        StackTraceElement trace;
        try {
            Thread currentThread = Thread.currentThread();
            if (currentThread != null && currentThread.getStackTrace() != null && currentThread.getStackTrace().length > 2) {
                trace = currentThread.getStackTrace()[2];
                className = trace.getClassName();
                function = trace.getMethodName();
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
        standardizeLoggingService.writeKPILog(errorCode, mess, success, startTime, className, function);
    }

    public void writeErrorLog(Exception e, Integer errorCode, String transactionStatus, Date startTime) {

        LogBeanDTO fcba = new LogBeanDTO();

        fcba.setAPPLICATION_CODE(ConstantsLog.APP_CODE.APPLICATION_CODE); // application_code
        fcba.setSERVICE_CODE(ConstantsLog.APP_CODE.CMSAdmin); // service_code

        String sessionId = "";
        String ipPortCurrentNode = "";
        String ipPortParentNode = "";
        String userName = SecurityUtils.getCurrentUserLogin().get();
        HttpServletRequest request = RequestContextHolder.getRequestAttributes() != null ? ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
        if (request != null) {
            sessionId = !DataUtil.isNullOrEmpty(request.getRequestedSessionId()) ? request.getRequestedSessionId() : request.getSession().getId();
            ipPortParentNode = !DataUtil.isNullOrEmpty(request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
            ipPortCurrentNode = request.getServerName() + ":" + request.getServerPort();
        }
        fcba.setSESSION_ID(sessionId);
        fcba.setIP_PORT_PARENT_NODE(ipPortParentNode);
        fcba.setIP_PORT_CURRENT_NODE(ipPortCurrentNode);
        fcba.setREQUEST_CONTENT(this.getAllParams(request.getParameterMap()));
        fcba.setRESPONSE_CONTENT(null);

        if (startTime == null) {
            startTime = new Date();
        }
        fcba.setSTART_TIME_D(startTime);
        fcba.setERROR_CODE_I(errorCode);

        StackTraceElement trace;
        String className = "";
        String function = "";
        if (Thread.currentThread() != null && Thread.currentThread().getStackTrace() != null && Thread.currentThread().getStackTrace().length > 2) {
            trace = Thread.currentThread().getStackTrace()[2];
            className = trace.getClassName();
            function = trace.getMethodName();
        }
        e.printStackTrace();
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String errorDescription = sw.toString();

        if (errorDescription.length() > 3000) {
            errorDescription = errorDescription.substring(0, 3000);
        }

        fcba.setERROR_DESCRIPTION(errorDescription);
        fcba.setTRANSACTION_STATUS(transactionStatus);
        fcba.setACTION_NAME(className + "_" + function);
        fcba.setUSERNAME(userName);
        fcba.setACCOUNT(null);

        String errorMessage = writeLogWithFCABsStandard(fcba);
        logger.error(errorMessage);

        try {
            String strMailContent = Constants.MAIL_FORMAT_ERROR.replace("{CONTENT}", errorMessage);
            String lstMailTo[] = new String[]{techEmail};
            MailUtils mailUtils = new MailUtils();
            if (!this.TEST_SYSTEM) {
                mailUtils.send(lstMailTo, null, null, "[SMS - CSM Admin] Lỗi Exception từ dịch vụ", strMailContent, null, null);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void writeInfoLog(String transactionStatus, String responseContent, Date startTime) {

        LogBeanDTO fcba = new LogBeanDTO();

        fcba.setAPPLICATION_CODE(ConstantsLog.APP_CODE.APPLICATION_CODE); // application_code
        fcba.setSERVICE_CODE(ConstantsLog.APP_CODE.CMSAdmin); // service_code

        String sessionId = "";
        String ipPortCurrentNode = "";
        String ipPortParentNode = "";
        String userName = SecurityUtils.getCurrentUserLogin().get();
        HttpServletRequest request = RequestContextHolder.getRequestAttributes() != null ? ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
        if (request != null) {
            sessionId = !DataUtil.isNullOrEmpty(request.getRequestedSessionId()) ? request.getRequestedSessionId() : request.getSession().getId();
            ipPortParentNode = !DataUtil.isNullOrEmpty(request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
            ipPortCurrentNode = request.getServerName() + ":" + request.getServerPort();
        }
        fcba.setSESSION_ID(sessionId);
        fcba.setIP_PORT_PARENT_NODE(ipPortParentNode);
        fcba.setIP_PORT_CURRENT_NODE(ipPortCurrentNode);
        fcba.setREQUEST_CONTENT(this.getAllParams(request.getParameterMap()));
        fcba.setRESPONSE_CONTENT(responseContent);

        if (startTime == null) {
            startTime = new Date();
        }
        fcba.setSTART_TIME_D(startTime);
        if ("0".equals(transactionStatus)) {
            fcba.setERROR_CODE_I(null);
        } else {
            fcba.setERROR_CODE_I(2);
        }

        StackTraceElement trace;
        String className = "";
        String function = "";
        if (Thread.currentThread() != null && Thread.currentThread().getStackTrace() != null && Thread.currentThread().getStackTrace().length > 2) {
            trace = Thread.currentThread().getStackTrace()[2];
            className = trace.getClassName();
            function = trace.getMethodName();
        }

        fcba.setERROR_DESCRIPTION(null);
        fcba.setTRANSACTION_STATUS(transactionStatus);
        fcba.setACTION_NAME(className + "_" + function);
        fcba.setUSERNAME(userName);
        fcba.setACCOUNT(null);

        String message = writeLogWithFCABsStandard(fcba);
        logger.info(message);
    }

    private String writeLogWithFCABsStandard(LogBeanDTO fcba) {
        try {
            String log = "";
            String applicationCode = fcba.getAPPLICATION_CODE();
            String serviceCode = fcba.getSERVICE_CODE();
            String sessionID = fcba.getSESSION_ID();
            String ipPortParentNode = fcba.getIP_PORT_PARENT_NODE();
            String ipPortCurrentNode = fcba.getIP_PORT_CURRENT_NODE();
            String param = fcba.getREQUEST_CONTENT();
            String responseContent = fcba.getRESPONSE_CONTENT();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            Date startTimeD = fcba.getSTART_TIME_D();
            Timestamp startTimeTS = new Timestamp(startTimeD.getTime());
            String startTime = df.format(startTimeD);
            Date endTimeD = new Date();
            String endTime = df.format(endTimeD);
            Timestamp endTimeTS = new Timestamp(endTimeD.getTime());
            Long duration = endTimeTS.getTime() - startTimeTS.getTime();
            Integer errorCode = fcba.getERROR_CODE_I();
            String errorDescription = fcba.getERROR_DESCRIPTION();
            String transactionStatus = fcba.getTRANSACTION_STATUS();
            String actionName = fcba.getACTION_NAME();
            String username = fcba.getUSERNAME();
            String account = fcba.getACCOUNT();
            log = "" + applicationCode + "|" + serviceCode + "|" + sessionID + "|" + ipPortParentNode + "|" + ipPortCurrentNode + "|" + param + "|" + responseContent + "|" + startTime + "|" + endTime + "|" + duration + "|" + errorCode + "|" + errorDescription + "|" + transactionStatus + "|" + actionName + "|" + username + "|" + account + "" + "";
            return log;
        } catch (Exception ex) {
            logger.error(ex.toString(), ex);
            return "errorWriteLog";
        }
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

    public void writeInfoLog(String message) {
        logger.info(message);
    }
}
