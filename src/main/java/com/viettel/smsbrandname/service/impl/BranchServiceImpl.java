package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Protocol;
import com.viettel.smsbrandname.repository.ProvinceBCCSCustomRepository;
import com.viettel.smsbrandname.service.BranchService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);


    @Value("${bccs.bccs_user}")
    private String bccsUser;

    @Value("${bccs.bccs_pass}")
    private String bccsPass;

    @Value("${bccs.bccs_url}")
    private String bccsUrl;

    @Autowired
    private ProvinceBCCSCustomRepository provinceBCCSCustomRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<ComboBean> getListBranch(Principal principal) {
        Date date = new Date();
        List<ComboBean> lstBranch = new ArrayList<ComboBean>();
        long startTime = System.currentTimeMillis();
        List<ComboBean> lstProvinceBccs = provinceBCCSCustomRepository.getLstProvinceBccs(getUserName(principal));
        log.info("Call BCCS.getLstBranch");
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://process.wsim.viettel.com/\">"
            + "<soapenv:Header/>"
            + "<soapenv:Body>"
            + "<proc:getListBranch>"
            + "<branchCode>" + "" + "</branchCode>"
            + "<branchName>" + "" + "</branchName>"
            + "<username>" + "###USERNAME###" + "</username>"
            + "<password>" + "###PASSWORD###" + "</password>"
            + "</proc:getListBranch>"
            + "</soapenv:Body>"
            + "</soapenv:Envelope>";

        log.info("Call BCCS.getLstBranch request: " + request);
        request = request.replace("###USERNAME###", bccsUser).replace("###PASSWORD###", bccsPass);
        PostMethod post = null;
        try {
            Protocol protocol = new Protocol(bccsUrl);
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
            log.info("Call BCCS.getLstBranch response: " + response);

            if (response != null && !"".equals(response)) {
                if (response.contains("<responseCode>")) {
                    int start = response.indexOf("<responseCode>") + "<responseCode>".length();
                    int end = response.lastIndexOf("</responseCode>");
                    String responseCode = response.substring(start, end);
                    if ("0".equalsIgnoreCase(responseCode)) {
                        Document document = DataUtil.parseXmlString(response);
                        NodeList nodeListBranch = document.getElementsByTagName("lst");
                        if (nodeListBranch != null) {
                            for (int i = 0; i < nodeListBranch.getLength(); i++) {
                                Node nodeBranch = nodeListBranch.item(i);
                                if (nodeBranch != null) {
                                    NodeList nodeListChild = nodeBranch.getChildNodes();
                                    if (nodeListChild != null) {
                                        String shopId = "";
                                        String shopCode = "";
                                        String shopName = "";
                                        for (int j = 0; j < nodeListChild.getLength(); j++) {
                                            Node item = nodeListChild.item(j);
                                            if (item != null) {
                                                String nodeName = item.getNodeName();
                                                String content = item.getTextContent();
                                                if ("shopCode".equalsIgnoreCase(nodeName)) {
                                                    shopCode = content;
                                                } else if ("shopId".equalsIgnoreCase(nodeName)) {
                                                    shopId = content;
                                                } else if ("shopName".equalsIgnoreCase(nodeName)) {
                                                    shopName = content;
                                                }
                                            }
                                        }
                                        Boolean checkProvinceUser = false;
                                        //Kiem tra va gan chi nhanh theo user - ThienDL(23052018)
                                        if (lstProvinceBccs != null) {
                                            for (ComboBean item : lstProvinceBccs) {
                                                if (DataUtil.validString(item.getValue()) && item.getValue().equals(shopId)) {
                                                    checkProvinceUser = true;
                                                    break;
                                                }
                                            }
                                        }
                                        if (lstProvinceBccs == null || checkProvinceUser) {
                                            ComboBean branch = new ComboBean();
                                            branch.setValue(shopId);
                                            branch.setLabel(shopCode + " --- " + shopName);
                                            lstBranch.add(branch);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }

        log.info("Result call BCCS.getLstBranch get total branch=" + lstBranch.size());
        log.info("Finish call BCCS.getLstBranch in " + (System.currentTimeMillis() - startTime) + " ms");
        return lstBranch;
    }

    private String getUserName(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal).getLogin();
        }
        return null;
    }
}
