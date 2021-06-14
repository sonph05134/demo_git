package com.viettel.smsbrandname.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.*;

@Entity
@Table(name = "CP")
public class Cp implements Serializable {

    @Id
    @GeneratedValue(generator = "CP_SEQ_GEN")
    @SequenceGenerator(name = "CP_SEQ_GEN", sequenceName = "CP_ID_SEQ", allocationSize = 1)
    @Column(name = "CP_ID")
    Long cpId;

    @Column(name = "CP_NAME")
    String cpName;


    @Column(name = "CP_CODE")
    String cpCode;

    @Column(name = "STATUS")
    Long status;

    @Column(name = "CP_SYSID")
    String cpSysid;

    @Column(name = "CHANNEL")
    String channel;

    @Column(name = "USER_NAME")
    String userName;

    @Column(name = "MO_RECEIVE_TYPE")
    Long moReceiveType;

    @Column(name = "MO_WS")
    String moWs;

    @Column(name = "MO_RULE")
    String moRule;

    @Column(name = "APPROVE_TYPE")
    Long approveType;

    @Column(name = "PASS_SMPP")
    String passSmpp;

    @Column(name = "TPS")
    Long tps;

    @Column(name = "BEGIN_DATE")
    Date beginDate;

    @Column(name = "EXPIRE_DATE")
    Date expireDate;

    @Column(name = "STAFF_CODE")
    String staffCode;

    @Column(name = "STAFF_NAME")
    String staffName;

    @Column(name = "GROUP_TYPE")
    Long groupType;

    @Column(name = "IP")
    String ip;

    @Column(name = "NOT_FILTER")
    Long notFilter;

    @Column(name = "CHARGE_TYPE")
    String chargeType;

    @Column(name = "BALANCE")
    Long balance;

    @Column(name = "CHECK_ALIAS")
    Long checkAlias;

    @Column(name = "CHECK_FEE")
    Long checkFee;

    @Column(name = "CPINFO")
    String cpinfo;

    @Column(name = "WARN_CONTACT")
    String warnContact;

    @Column(name = "TAX_CODE")
    String taxCode;

    @Column(name = "REPRESENTATIVE")
    String representative;

    @Column(name = "ADDRESS")
    String address;

    @Column(name = "PROVINCE_ID")
    Long provinceId;

    @Column(name = "RECEIVER_FLG")
    Long receiverFlg;

    @Column(name = "CURRENCY")
    String currency;

    @Column(name = "ORDER_NO")
    String orderNo;

    @Column(name = "AD_BALANCE")
    Long adBalance;

    @Column(name = "WS_USERNAME")
    String wsUsername;

    @Column(name = "WS_PASSWORD")
    String wsPassword;

    @Column(name = "ALLOW_BI_PROG")
    Long allowBiProg;

    @Column(name = "ROUTER")
    String router;

    @Column(name = "ACCOUNT_TYPE")
    Long accountType;

    @Column(name = "FREE_SMS")
    Long freeSms;

    @Column(name = "WARN_BALANCE")
    Long warnBalance;

    @Column(name = "AD_WARN_BALANCE")
    Long adWarnBalance;

    @Column(name = "TOPUP_LIMIT")
    Long topupLimit;

    @Column(name = "ATTACH_FILE")
    String attachFile;

    @Column(name = "CP_GROUP")
    String cpGroup;

    @Column(name = "LAST_WARN_TIME")
    Date lastWarnTime;

    @Column(name = "PROVINCE_USER_ID")
    Long provinceUserId;

    @Column(name = "COMMISSION_PERCENT_CODE")
    String commissionPercentCode;

    @Column(name = "AM_PERCENT")
    Long amPercent;

    @Column(name = "TELESALE_NAME")
    String telesaleName;

    @Column(name = "TELESALE_CODE")
    String telesaleCode;

    @Column(name = "REALTIME_PERMIT")
    Long realtimePermit;

    @Column(name = "DISTRICT_ID")
    Long districtId;

    @Column(name = "SEND_WITH_OTHER")
    Long sendWithOther;

    @Column(name = "USER_CREATED")
    String userCreated;

    @Column(name = "DATE_CREATED")
    Date dateCreated;

    @Column(name = "USER_UPDATED")
    String userUpdated;

    @Column(name = "DATE_UPDATED")
    Date dateUpdated;

    @Column(name = "PARENT_CP_ID")
    Long parentCpId;

    @Column(name = "LIMITED_DAYS")
    Long limitedDays;

    @Column(name = "COMMISSION_BK")
    String commissionBk;

    @Column(name = "CP_NAME_ROMAN")
    String cpNameRoman;

    @Column(name = "LOCK_STATUS")
    Long lockStatus;

    @Column(name = "ALERT_SMSTOTAL")
    Long alertSmstotal;

    @Column(name = "ALERT_EMAIL")
    String alertEmail;

    @Column(name = "ALERT_TEL")
    String alertTel;

    @Column(name = "FILTER_VIP")
    Long filterVip;

    @Column(name = "FILTER_1313")
    Long filter1313;

    @Column(name = "TRANSFER_LIMIT")
    Long transferLimit;

    @Column(name = "WS_TOKEN")
    String wsToken;

    @Column(name = "USSD_CALLBACK_ENDPOINT")
    String ussdCallbackEndpoint;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "USSD_ENABLED")
    Long ussdEnabled;

    @Column(name = "USSD_BALANCE")
    Long ussdBalance;

    @Column(name = "MONTH_COMMISSION")
    Long monthCommission;

    @Column(name = "MONTH_COMMISSION_NOTE")
    String monthCommissionNote;

    @Column(name = "MONTH_COMMISSION_ATTACH_FILE")
    String monthCommissionAttachFile;

    @Column(name = "STAFF_EMAIL")
    String staffEmail;

    @Column(name = "SYNC_REVENUE_BCCS")
    Long syncRevenueBccs;

    @Column(name = "PROJECT_CODE")
    String projectCode;

    @Column(name = "PROJECT_NAME")
    String projectName;
    @Column(name = "ACCEPT_SEARCH_DATA")
    String acceptSearchData;

    public Cp() {
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getCpSysid() {
        return cpSysid;
    }

    public void setCpSysid(String cpSysid) {
        this.cpSysid = cpSysid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMoReceiveType() {
        return moReceiveType;
    }

    public void setMoReceiveType(Long moReceiveType) {
        this.moReceiveType = moReceiveType;
    }

    public String getMoWs() {
        return moWs;
    }

    public void setMoWs(String moWs) {
        this.moWs = moWs;
    }

    public String getMoRule() {
        return moRule;
    }

    public void setMoRule(String moRule) {
        this.moRule = moRule;
    }

    public Long getApproveType() {
        return approveType;
    }

    public void setApproveType(Long approveType) {
        this.approveType = approveType;
    }

    public String getPassSmpp() {
        return passSmpp;
    }

    public void setPassSmpp(String passSmpp) {
        this.passSmpp = passSmpp;
    }

    public Long getTps() {
        return tps;
    }

    public void setTps(Long tps) {
        this.tps = tps;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getGroupType() {
        return groupType;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getNotFilter() {
        return notFilter;
    }

    public void setNotFilter(Long notFilter) {
        this.notFilter = notFilter;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getCheckAlias() {
        return checkAlias;
    }

    public void setCheckAlias(Long checkAlias) {
        this.checkAlias = checkAlias;
    }

    public Long getCheckFee() {
        return checkFee;
    }

    public void setCheckFee(Long checkFee) {
        this.checkFee = checkFee;
    }

    public String getCpinfo() {
        return cpinfo;
    }

    public void setCpinfo(String cpinfo) {
        this.cpinfo = cpinfo;
    }

    public String getWarnContact() {
        return warnContact;
    }

    public void setWarnContact(String warnContact) {
        this.warnContact = warnContact;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getReceiverFlg() {
        return receiverFlg;
    }

    public void setReceiverFlg(Long receiverFlg) {
        this.receiverFlg = receiverFlg;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getAdBalance() {
        return adBalance;
    }

    public void setAdBalance(Long adBalance) {
        this.adBalance = adBalance;
    }

    public String getWsUsername() {
        return wsUsername;
    }

    public void setWsUsername(String wsUsername) {
        this.wsUsername = wsUsername;
    }

    public String getWsPassword() {
        return wsPassword;
    }

    public void setWsPassword(String wsPassword) {
        this.wsPassword = wsPassword;
    }

    public Long getAllowBiProg() {
        return allowBiProg;
    }

    public void setAllowBiProg(Long allowBiProg) {
        this.allowBiProg = allowBiProg;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    public Long getFreeSms() {
        return freeSms;
    }

    public void setFreeSms(Long freeSms) {
        this.freeSms = freeSms;
    }

    public Long getWarnBalance() {
        return warnBalance;
    }

    public void setWarnBalance(Long warnBalance) {
        this.warnBalance = warnBalance;
    }

    public Long getAdWarnBalance() {
        return adWarnBalance;
    }

    public void setAdWarnBalance(Long adWarnBalance) {
        this.adWarnBalance = adWarnBalance;
    }

    public Long getTopupLimit() {
        return topupLimit;
    }

    public void setTopupLimit(Long topupLimit) {
        this.topupLimit = topupLimit;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getCpGroup() {
        return cpGroup;
    }

    public void setCpGroup(String cpGroup) {
        this.cpGroup = cpGroup;
    }

    public Date getLastWarnTime() {
        return lastWarnTime;
    }

    public void setLastWarnTime(Date lastWarnTime) {
        this.lastWarnTime = lastWarnTime;
    }

    public Long getProvinceUserId() {
        return provinceUserId;
    }

    public void setProvinceUserId(Long provinceUserId) {
        this.provinceUserId = provinceUserId;
    }

    public String getCommissionPercentCode() {
        return commissionPercentCode;
    }

    public void setCommissionPercentCode(String commissionPercentCode) {
        this.commissionPercentCode = commissionPercentCode;
    }

    public Long getAmPercent() {
        return amPercent;
    }

    public void setAmPercent(Long amPercent) {
        this.amPercent = amPercent;
    }

    public String getTelesaleName() {
        return telesaleName;
    }

    public void setTelesaleName(String telesaleName) {
        this.telesaleName = telesaleName;
    }

    public String getTelesaleCode() {
        return telesaleCode;
    }

    public void setTelesaleCode(String telesaleCode) {
        this.telesaleCode = telesaleCode;
    }

    public Long getRealtimePermit() {
        return realtimePermit;
    }

    public void setRealtimePermit(Long realtimePermit) {
        this.realtimePermit = realtimePermit;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getSendWithOther() {
        return sendWithOther;
    }

    public void setSendWithOther(Long sendWithOther) {
        this.sendWithOther = sendWithOther;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getParentCpId() {
        return parentCpId;
    }

    public void setParentCpId(Long parentCpId) {
        this.parentCpId = parentCpId;
    }

    public Long getLimitedDays() {
        return limitedDays;
    }

    public void setLimitedDays(Long limitedDays) {
        this.limitedDays = limitedDays;
    }

    public String getCommissionBk() {
        return commissionBk;
    }

    public void setCommissionBk(String commissionBk) {
        this.commissionBk = commissionBk;
    }

    public String getCpNameRoman() {
        return cpNameRoman;
    }

    public void setCpNameRoman(String cpNameRoman) {
        this.cpNameRoman = cpNameRoman;
    }

    public Long getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Long lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Long getAlertSmstotal() {
        return alertSmstotal;
    }

    public void setAlertSmstotal(Long alertSmstotal) {
        this.alertSmstotal = alertSmstotal;
    }

    public String getAlertEmail() {
        return alertEmail;
    }

    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }

    public String getAlertTel() {
        return alertTel;
    }

    public void setAlertTel(String alertTel) {
        this.alertTel = alertTel;
    }

    public Long getFilterVip() {
        return filterVip;
    }

    public void setFilterVip(Long filterVip) {
        this.filterVip = filterVip;
    }

    public Long getFilter1313() {
        return filter1313;
    }

    public void setFilter1313(Long filter1313) {
        this.filter1313 = filter1313;
    }

    public Long getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(Long transferLimit) {
        this.transferLimit = transferLimit;
    }

    public String getWsToken() {
        return wsToken;
    }

    public void setWsToken(String wsToken) {
        this.wsToken = wsToken;
    }

    public String getUssdCallbackEndpoint() {
        return ussdCallbackEndpoint;
    }

    public void setUssdCallbackEndpoint(String ussdCallbackEndpoint) {
        this.ussdCallbackEndpoint = ussdCallbackEndpoint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUssdEnabled() {
        return ussdEnabled;
    }

    public void setUssdEnabled(Long ussdEnabled) {
        this.ussdEnabled = ussdEnabled;
    }

    public Long getUssdBalance() {
        return ussdBalance;
    }

    public void setUssdBalance(Long ussdBalance) {
        this.ussdBalance = ussdBalance;
    }

    public Long getMonthCommission() {
        return monthCommission;
    }

    public void setMonthCommission(Long monthCommission) {
        this.monthCommission = monthCommission;
    }

    public String getMonthCommissionNote() {
        return monthCommissionNote;
    }

    public void setMonthCommissionNote(String monthCommissionNote) {
        this.monthCommissionNote = monthCommissionNote;
    }

    public String getMonthCommissionAttachFile() {
        return monthCommissionAttachFile;
    }

    public void setMonthCommissionAttachFile(String monthCommissionAttachFile) {
        this.monthCommissionAttachFile = monthCommissionAttachFile;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public Long getSyncRevenueBccs() {
        return syncRevenueBccs;
    }

    public void setSyncRevenueBccs(Long syncRevenueBccs) {
        this.syncRevenueBccs = syncRevenueBccs;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAcceptSearchData() {
        return acceptSearchData;
    }

    public void setAcceptSearchData(String acceptSearchData) {
        this.acceptSearchData = acceptSearchData;
    }
}
