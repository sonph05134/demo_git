/*
 * Copyright 2016 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.smsbrandname.service.impl;

import com.viettel.security.PassTranformer;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Protocol;
import com.viettel.smsbrandname.service.BccsService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.response.BccsResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class BccsServiceImpl implements BccsService {
    private final Logger logger = LoggerFactory.getLogger(BccsServiceImpl.class);

    @Value("${bccs.bccs_url}")
    private String url;
    @Value("${bccs.bccs_user}")
    private String usernameBulk;
    @Value("${bccs.bccs_pass}")
    private String passwordBulk;
    @Value("${bccs.bccs_user_ams}")
    private String usernameAms;
    @Value("${bccs.bccs_pass_ams}")
    private String passwordAms;
    @Value("${bccs.bccs_url_ExternalServiceForSale}")
    private String urlServiceForSale;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String saveSaleTransBulkSms(String custName, String companyName, String address, String tin,
                                       String contractNo, String orderNo, String amountTrans, String discountAmountTrans, String branchId,
                                       XMLGregorianCalendar saleTransDate, String telNumber, String saleTransType) {
        long startTime = System.currentTimeMillis();
        String saleTransId = null;
        tin = DataUtil.convertTaxCodeSInvoice(tin);
        logger.info("Call BCCS.saveSaleTransBulkSms with params: {" + "custName=" + custName + ", companyName=" + companyName
            + ", address=" + address + ", tin=" + tin + ", contractNo=" + contractNo + ", orderNo=" + orderNo
            + ", amountTrans=" + amountTrans + ", discountAmountTrans=" + discountAmountTrans
            + ", branchId=" + branchId + ", saleTransDate=" + saleTransDate.toString() + ", telNumber=" + telNumber
            + ", saleTransType=" + saleTransType + "}");

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://process.wsim.viettel.com/\">"
            + "<soapenv:Header/>"
            + "<soapenv:Body>"
            + "<proc:saveSaleTransBulkSms>"
            + "<custName><![CDATA[" + companyName + "]]></custName>"
            + "<companyName><![CDATA[" + custName + "]]></companyName>"
            + "<Address><![CDATA[" + address + "]]></Address>"
            + "<Tin>" + tin + "</Tin>"
            + "<contractNo>" + contractNo + "</contractNo>"
            + "<orderNo>" + orderNo + "</orderNo>"
            + "<amountTrans>" + amountTrans + "</amountTrans>"
            + "<discountAmountTrans>" + discountAmountTrans + "</discountAmountTrans>"
            + "<branchId>" + branchId + "</branchId>"
            + "<saleTransDate>" + saleTransDate + "</saleTransDate>"
            + "<telNumber>" + telNumber + "</telNumber>"
            + "<saleTransType>" + saleTransType + "</saleTransType>"
            + "<username>" + "###USERNAME###" + "</username>"
            + "<password>" + "###PASSWORD###" + "</password>"
            + "</proc:saveSaleTransBulkSms>"
            + "</soapenv:Body>"
            + "</soapenv:Envelope>";

        logger.info("Call BCCS.saveSaleTransBulkSms request: " + request);
        request = request.replace("###USERNAME###", usernameBulk).replace("###PASSWORD###", passwordBulk);
        PostMethod post = null;
        try {
            Protocol protocol = new Protocol(url);
            HttpClient httpclient = new HttpClient();
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(20000);
            conPars.setSoTimeout(60000);
            post = new PostMethod(protocol.getUrl());

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", "");
            httpclient.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            String response = null;
            if (is != null) {
                response = DataUtil.getStringFromInputStream(is);
            }
            logger.info("Call BCCS.saveSaleTransBulkSms response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<responseCode>")) {
                    int start = response.indexOf("<responseCode>") + "<responseCode>".length();
                    int end = response.lastIndexOf("</responseCode>");
                    String responseCode = response.substring(start, end);
                    if ("0".equalsIgnoreCase(responseCode)) {
                        int startSaleTransId = response.indexOf("<amsSaleTransId>") + "<amsSaleTransId>".length();
                        int endSaleTransId = response.lastIndexOf("</amsSaleTransId>");
                        saleTransId = response.substring(startSaleTransId, endSaleTransId);
                        logger.info("Call BCCS.saveSaleTransBulkSms amsSaleTransId=" + saleTransId);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        logger.info("Finish call BCCS.saveSaleTransBulkSms in " + (System.currentTimeMillis() - startTime) + " ms");
        return saleTransId;
    }

    @Override
    public XMLGregorianCalendar getCurrentDate() {
        XMLGregorianCalendar currentDate = null;
        try {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            currentDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return currentDate;
    }

    @Override
    public Long getOrderNoSeq(String seq) {
        String sql = "select " + seq + ".nextval from dual";
        return ((BigDecimal) entityManager.createNativeQuery(sql).getSingleResult()).longValue();
    }

    @Override
    public String saveSaleTransBulkSmsFull(String custName, String companyName, String address, String tin,
                                           String contractNo, String orderNo, String amountTrans, String discountAmountTrans, String branchId,
                                           XMLGregorianCalendar saleTransDate, String telNumber, String saleTransType, String email, String payMethod) {
        long startTime = System.currentTimeMillis();

        try {
            this.usernameBulk = PassTranformer.decrypt(usernameBulk);
            this.passwordBulk = PassTranformer.decrypt(passwordBulk);
        }catch (Exception e) {
            logger.error("decrypt user/password fail", e);
        }

        String saleTransId = null;
        tin = DataUtil.convertTaxCodeSInvoice(tin);
        logger.info("Call BCCS.saveSaleTransBulkSmsFull with params: {" + "custName=" + custName + ", companyName=" + companyName
            + ", address=" + address + ", tin=" + tin + ", contractNo=" + contractNo + ", orderNo=" + orderNo
            + ", amountTrans=" + amountTrans + ", discountAmountTrans=" + discountAmountTrans
            + ", branchId=" + branchId + ", saleTransDate=" + saleTransDate.toString() + ", telNumber=" + telNumber
            + ", saleTransType=" + saleTransType + "}");

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://process.wsim.viettel.com/\">"
            + "<soapenv:Header/>"
            + "<soapenv:Body>"
            + "<proc:saveSaleTransBulkSmsFull>"
            + "<custName><![CDATA[" + companyName + "]]></custName>"
            + "<companyName><![CDATA[" + custName + "]]></companyName>"
            + "<Address><![CDATA[" + address + "]]></Address>"
            + "<Tin>" + tin + "</Tin>"
            + "<contractNo>" + contractNo + "</contractNo>"
            + "<orderNo>" + orderNo + "</orderNo>"
            + "<amountTrans>" + amountTrans + "</amountTrans>"
            + "<discountAmountTrans>" + discountAmountTrans + "</discountAmountTrans>"
            + "<branchId>" + branchId + "</branchId>"
            + "<saleTransDate>" + saleTransDate + "</saleTransDate>"
            + "<telNumber>" + telNumber + "</telNumber>"
            + "<saleTransType>" + saleTransType + "</saleTransType>"
            + "<email>" + email + "</email>"
            + "<payMethod>" + payMethod + "</payMethod>"
            + "<username>" + "###USERNAME###" + "</username>"
            + "<password>" + "###PASSWORD###" + "</password>"
            + "</proc:saveSaleTransBulkSmsFull>"
            + "</soapenv:Body>"
            + "</soapenv:Envelope>";

        logger.info("Call BCCS.saveSaleTransBulkSmsFull request: " + request);
        request = request.replace("###USERNAME###", usernameBulk).replace("###PASSWORD###", passwordBulk);
        PostMethod post = null;
        try {
            Protocol protocol = new Protocol(url);
            HttpClient httpclient = new HttpClient();
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(20000);
            conPars.setSoTimeout(60000);
            post = new PostMethod(protocol.getUrl());

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", "");
            httpclient.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            String response = null;
            if (is != null) {
                response = DataUtil.getStringFromInputStream(is);
            }
            logger.info("Call BCCS.saveSaleTransBulkSmsFull response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<responseCode>")) {
                    int start = response.indexOf("<responseCode>") + "<responseCode>".length();
                    int end = response.lastIndexOf("</responseCode>");
                    String responseCode = response.substring(start, end);
                    if ("0".equalsIgnoreCase(responseCode)) {
                        int startSaleTransId = response.indexOf("<amsSaleTransId>") + "<amsSaleTransId>".length();
                        int endSaleTransId = response.lastIndexOf("</amsSaleTransId>");
                        saleTransId = response.substring(startSaleTransId, endSaleTransId);
                        logger.info("Call BCCS.saveSaleTransBulkSmsFull amsSaleTransId=" + saleTransId);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        logger.info("Finish call BCCS.saveSaleTransBulkSmsFull in " + (System.currentTimeMillis() - startTime) + " ms");
        return saleTransId;
    }

    @Override
    public int receiveBulkSmsResult(String amsSaleTransId) {
        Date date = new Date();
        int result = -1;
        long startTime = System.currentTimeMillis();
        logger.info("Call BCCS.receiveBulkSmsResult with params: {" + "amsSaleTransId=" + amsSaleTransId + "}");

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://process.wsim.viettel.com/\">"
            + "<soapenv:Header/>"
            + "<soapenv:Body>"
            + "<proc:receiveBulkSmsResult>"
            + "<amsSaleTransId>" + amsSaleTransId + "</amsSaleTransId>"
            + "<username>" + "###USERNAME###" + "</username>"
            + "<password>" + "###PASSWORD###" + "</password>"
            + "</proc:receiveBulkSmsResult>"
            + "</soapenv:Body>"
            + "</soapenv:Envelope>";

        logger.info("Call BCCS.receiveBulkSmsResult request: " + request);
        request = request.replace("###USERNAME###", usernameBulk).replace("###PASSWORD###", passwordBulk);
        PostMethod post = null;
        try {
            Protocol protocol = new Protocol(url);
            HttpClient httpclient = new HttpClient();
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(20000);
            conPars.setSoTimeout(60000);
            post = new PostMethod(protocol.getUrl());

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", "");
            httpclient.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            String response = null;
            if (is != null) {
                response = DataUtil.getStringFromInputStream(is);
            }
            logger.info("Call BCCS.receiveBulkSmsResult response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<responseCode>")) {
                    int start = response.indexOf("<responseCode>") + "<responseCode>".length();
                    int end = response.lastIndexOf("</responseCode>");
                    String responseCode = response.substring(start, end);
                    if ("OK".equalsIgnoreCase(responseCode)) {
                        result = 0;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        logger.info("Finish call BCCS.receiveBulkSmsResult in " + (System.currentTimeMillis() - startTime) + " ms");
        return result;
    }

    public int receiveAmsResult(String amsSaleTransId) {
        Date date = new Date();
        long startTime = System.currentTimeMillis();
        int result = -1;
        logger.info("Call BCCS.receiveAmsResult with params: {" + "amsSaleTransId=" + amsSaleTransId + "}");

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://process.wsim.viettel.com/\">"
            + "<soapenv:Header/>"
            + "<soapenv:Body>"
            + "<proc:receiveAmsResult>"
            + "<amsSaleTransId>" + amsSaleTransId + "</amsSaleTransId>"
            + "<username>" + "###USERNAME###" + "</username>"
            + "<password>" + "###PASSWORD###" + "</password>"
            + "</proc:receiveAmsResult>"
            + "</soapenv:Body>"
            + "</soapenv:Envelope>";

        logger.info("Call BCCS.receiveAmsResult request: " + request);
        request = request.replace("###USERNAME###", usernameAms).replace("###PASSWORD###", passwordAms);
        PostMethod post = null;
        try {
            Protocol protocol = new Protocol(url);
            HttpClient httpclient = new HttpClient();
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(20000);
            conPars.setSoTimeout(60000);
            post = new PostMethod(protocol.getUrl());

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", "");
            httpclient.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            String response = null;
            if (is != null) {
                response = DataUtil.getStringFromInputStream(is);
            }
            logger.info("Call BCCS.receiveAmsResult response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<responseCode>")) {
                    int start = response.indexOf("<responseCode>") + "<responseCode>".length();
                    int end = response.lastIndexOf("</responseCode>");
                    String responseCode = response.substring(start, end);
                    if ("OK".equalsIgnoreCase(responseCode)) {
                        result = 0;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        logger.info("Finish call BCCS.receiveAmsResult in " + (System.currentTimeMillis() - startTime) + " ms");
        return result;
    }

    @Override
    public BccsResponse saveSaleTransBulkSmsFullToBccsResult(String custName, String companyName, String address, String tin, String contractNo, String orderNo, String amountTrans, String discountAmountTrans, String branchId, XMLGregorianCalendar saleTransDate, String telNumber, String saleTransType, String email, String payMethod) {
        Date date = new Date();
        long startTime = System.currentTimeMillis();
        BccsResponse bccsResponse = null;
        tin = DataUtil.convertTaxCodeSInvoice(tin);
        logger.info("Call BCCS.saveSaleTransBulkSmsFull with params: {" + "custName=" + custName + ", companyName=" + companyName
            + ", address=" + address + ", tin=" + tin + ", contractNo=" + contractNo + ", orderNo=" + orderNo
            + ", amountTrans=" + amountTrans + ", discountAmountTrans=" + discountAmountTrans
            + ", branchId=" + branchId + ", saleTransDate=" + saleTransDate.toString() + ", telNumber=" + telNumber
            + ", saleTransType=" + saleTransType + "}");

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://process.wsim.viettel.com/\">"
            + "<soapenv:Header/>"
            + "<soapenv:Body>"
            + "<proc:saveSaleTransBulkSmsFull>"
            + "<custName><![CDATA[" + companyName + "]]></custName>"
            + "<companyName><![CDATA[" + custName + "]]></companyName>"
            + "<Address><![CDATA[" + address + "]]></Address>"
            + "<Tin>" + tin + "</Tin>"
            + "<contractNo>" + contractNo + "</contractNo>"
            + "<orderNo>" + orderNo + "</orderNo>"
            + "<amountTrans>" + amountTrans + "</amountTrans>"
            + "<discountAmountTrans>" + discountAmountTrans + "</discountAmountTrans>"
            + "<branchId>" + branchId + "</branchId>"
            + "<saleTransDate>" + saleTransDate + "</saleTransDate>"
            + "<telNumber>" + telNumber + "</telNumber>"
            + "<saleTransType>" + saleTransType + "</saleTransType>"
            + "<email>" + email + "</email>"
            + "<payMethod>" + payMethod + "</payMethod>"
            + "<username>" + "###USERNAME###" + "</username>"
            + "<password>" + "###PASSWORD###" + "</password>"
            + "</proc:saveSaleTransBulkSmsFull>"
            + "</soapenv:Body>"
            + "</soapenv:Envelope>";

        logger.info("Call BCCS.saveSaleTransBulkSmsFull request: " + request);
        request = request.replace("###USERNAME###", usernameBulk).replace("###PASSWORD###", passwordBulk);
        PostMethod post = null;
        try {
            Protocol protocol = new Protocol(url);
            HttpClient httpclient = new HttpClient();
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(20000);
            conPars.setSoTimeout(60000);
            post = new PostMethod(protocol.getUrl());

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", "");
            httpclient.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            String response = null;
            if (is != null) {
                response = DataUtil.getStringFromInputStream(is);
            }
            logger.info("Call BCCS.saveSaleTransBulkSmsFull response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<responseCode>")) {
                    int start = response.indexOf("<responseCode>") + "<responseCode>".length();
                    int end = response.lastIndexOf("</responseCode>");
                    String responseCode = response.substring(start, end);
                    String result;
                    if ("0".equalsIgnoreCase(responseCode)) {
                        int startSaleTransId = response.indexOf("<amsSaleTransId>") + "<amsSaleTransId>".length();
                        int endSaleTransId = response.lastIndexOf("</amsSaleTransId>");
                        result = response.substring(startSaleTransId, endSaleTransId);
                        logger.info("Call BCCS.saveSaleTransBulkSmsFull amsSaleTransId=" + result);
                    } else {
                        int startDesc = response.indexOf("<description>") + "<description>".length();
                        int endDesc = response.lastIndexOf("</description>");
                        result = response.substring(startDesc, endDesc);
                        logger.info("Call BCCS.saveSaleTransBulkSmsFull description=" + result);
                    }
                    bccsResponse = new BccsResponse(responseCode, result);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        logger.info("Finish call BCCS.saveSaleTransBulkSmsFull in " + (System.currentTimeMillis() - startTime) + " ms");
        return bccsResponse;
    }

    @Override
    public List<ComboBean> getLstProject() {
        Date date = new Date();
        List<ComboBean> lstMethod = new ArrayList<ComboBean>();
        long startTime = System.currentTimeMillis();
        logger.info("Call BCCS.findProjectCategoryManagementListByProjectCodeOrName");

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.product.bccs.viettel.com/\">\n" +
            "      <soapenv:Header>\n" +
            "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
            "         <wsse:UsernameToken wsu:Id=\"UsernameToken-32ce50e8-4a5d-4040-af71-c3428d92daa7\">\n" +
            "            <wsse:Username>product</wsse:Username>\n" +
            "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">product</wsse:Password>\n" +
            "         </wsse:UsernameToken>\n" +
            "      </wsse:Security>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:findProjectCategoryManagementListByProjectCodeOrName>\n" +
            "         <!--Optional:-->\n" +
            "         <projectCodeOrName></projectCodeOrName>\n" +
            "         <!--Optional:-->\n" +
            "         <limit>1000000000</limit>\n" +
            "      </ser:findProjectCategoryManagementListByProjectCodeOrName>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

        logger.info("Call BCCS.findProjectCategoryManagementListByProjectCodeOrName request: " + request);
        PostMethod post = null;

        try {

            Protocol protocol = new Protocol(urlServiceForSale);
            HttpClient httpclient = new HttpClient();
            HttpConnectionManager conMgr = httpclient.getHttpConnectionManager();
            HttpConnectionManagerParams conPars = conMgr.getParams();
            conPars.setConnectionTimeout(20000);
            conPars.setSoTimeout(60000);
            post = new PostMethod(protocol.getUrl());

            RequestEntity entity = new StringRequestEntity(request, "text/xml", "UTF-8");
            post.setRequestEntity(entity);
            post.setRequestHeader("SOAPAction", "");
            httpclient.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            String response = null;
            if (is != null) {
                response = getStringFromInputStream(is);
            }
            logger.info("Call BCCS.findProjectCategoryManagementListByProjectCodeOrName response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<name>")) {
                    Document document = parseXmlString(response);
                    NodeList nodeListBranch = document.getElementsByTagName("return");
                    if (nodeListBranch != null) {
                        for (int i = 0; i < nodeListBranch.getLength(); i++) {
                            Node nodeBranch = nodeListBranch.item(i);
                            if (nodeBranch != null) {
                                NodeList nodeListChild = nodeBranch.getChildNodes();
                                if (nodeListChild != null) {
                                    String methodValue = "";
                                    String methodName = "";
                                    for (int j = 0; j < nodeListChild.getLength(); j++) {
                                        Node item = nodeListChild.item(j);
                                        if (item != null) {
                                            String nodeName = item.getNodeName();
                                            String content = item.getTextContent();
                                            if ("name".equalsIgnoreCase(nodeName)) {
                                                methodName = content;
                                            } else if ("projectCode".equalsIgnoreCase(nodeName)) {
                                                methodValue = content;
                                            }
                                        }
                                    }
                                    ComboBean method = new ComboBean();
                                    method.setValue(methodValue);
                                    method.setLabel(methodValue + " - " + methodName);
                                    lstMethod.add(method);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        logger.info("Finish call BCCS.findProjectCategoryManagementListByProjectCodeOrName in " + (System.currentTimeMillis() - startTime) + " ms");
        return lstMethod;
    }

    private String getStringFromInputStream(InputStream is) {
        Date date = new Date();
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }

        return sb.toString();
    }

    public Document parseXmlString(String inputString) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbFactory.setXIncludeAware(false);
            dbFactory.setExpandEntityReferences(false);

            DocumentBuilder db = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(inputString));
            return db.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
