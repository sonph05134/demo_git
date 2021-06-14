package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpDTO {

    Long cpId;

    String cpName;

    @Pattern(regexp = "[A-Za-z0-9]_",message = "{cpCode.cp}")
    String cpCode;

    Long status;

    String cpSysid;

    String channel;

    @Pattern(regexp = "[^smsbrand_]",message = "{userName.cp}")
    String userName;

    Long moReceiveType;

    String moWs;

    String moRule;

    Long approveType;

    String passSmpp;

    Long tps;

    Date beginDate;

    Date expireDate;

    String staffCode;

    String staffName;

    Long groupType;

    String ip;

    Long notFilter;

    @Pattern(regexp = "PRE | POST",message = "{}")
    String chargeType;

    Long balance;

    Long checkAlias;

    Long checkFee;

    String cpinfo;

    @Pattern(regexp = "[^84]{11,12}",message = "{warnContact.cp}")
    @Pattern(regexp = "[0-9]",message = "{warnContact.cp.number}")
    String warnContact;

    String taxCode;

    String representative;

    String address;

    Long provinceId;

    Long receiverFlg;

    @Pattern(regexp = "USD|VND ",message = "{currency.cp}")
    String currency;

    String orderNo;

    Long adBalance;


    @NotEmpty(message = "{cp.wsUsername}")
    String wsUsername;

    String acceptSearchData;

    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*]{8,50}",message = "{cp.wsPassword}")
    String wsPassword;


    Long allowBiProg;

    String router;

    Long accountType;

    Long freeSms;

    Long warnBalance;

    Long adWarnBalance;

    Long topupLimit;

    String attachFile;

    String cpGroup;

    Date lastWarnTime;

    Long provinceUserId;

    String commissionPercentCode;

    String commissionPercentNote;

    Long amPercent;

    String telesaleName;

    String telesaleCode;

    Long realtimePermit;

    Long districtId;

    Long sendWithOther;

    String userCreated;

    Date dateCreated;

    String userUpdated;

    Date dateUpdated;

    Long parentCpId;

    Long limitedDays;

    String commissionBk;

    String cpNameRoman;

    Long lockStatus;

    Long alertSmstotal;

    String alertEmail;

    String alertTel;

    Long filterVip;

    Long filter1313;

    Long transferLimit;

    String wsToken;

    String ussdCallbackEndpoint;

    @Email(message = "{email.cp}")
    String email;

    Long ussdEnabled;

    Long ussdBalance;

    Long monthCommission;

    String monthCommissionNote;

    String monthCommissionAttachFile;

    String staffEmail;

    Long syncRevenueBccs;

    String projectCode;

    String projectName;

    String provinceName;

    String districtName;

    String provinceUserName;

    @Pattern(regexp = "[a-zA-Z0-9!@#$%^&*]{8,50}",message = "{cp.wsPassword}")
    String password;

    String reason;

    String approveTypeName;

    String statusName;

    String oldValueCommissionPercentCode;

    Boolean isWsPassChange;

    Boolean confirmExistedCp;

    public static List<CpDTO> toDto(List<Object[]> objects) {
        List<CpDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                CpDTO dto = new CpDTO();
                dto.setCpId(DataUtil.safeToLong(obj[i++]));
                dto.setCpCode(DataUtil.safeToString(obj[i++]));
                dto.setCpName(DataUtil.safeToString(obj[i++]));
                dto.setChannel(DataUtil.safeToString(obj[i++]));
                dto.setUserName(DataUtil.safeToString(obj[i++]));
                dto.setApproveType(DataUtil.safeToLong(obj[i++]));
                dto.setStatus(DataUtil.safeToLong(obj[i++]));
                dto.setChargeType(DataUtil.safeToString(obj[i++]));
                dto.setCurrency(DataUtil.safeToString(obj[i++]));
                dto.setBalance(DataUtil.safeToLong(obj[i++]));
                dto.setAdBalance(DataUtil.safeToLong(obj[i++]));
                dto.setProvinceName(DataUtil.safeToString(obj[i++]));
                dto.setDistrictName(DataUtil.safeToString(obj[i++]));
                dto.setAddress(DataUtil.safeToString(obj[i++]));
                dto.setTaxCode(DataUtil.safeToString(obj[i++]));
                dto.setAttachFile(DataUtil.safeToString(obj[i++]));
                dto.setMonthCommissionAttachFile(DataUtil.safeToString(obj[i++]));
                dto.setDateCreated((Date) obj[i++]);
                result.add(dto);
            });
        }
        return result;
    }

    public static List<CpDTO> toCheckDto(List<Object[]> objects) {
        List<CpDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                CpDTO dto = new CpDTO();
                dto.setCpId(DataUtil.safeToLong(obj[i++]));
                dto.setCpCode(DataUtil.safeToString(obj[i++]));
                dto.setCpName(DataUtil.safeToString(obj[i++]));
                dto.setChannel(DataUtil.safeToString(obj[i++]));
                dto.setUserName(DataUtil.safeToString(obj[i++]));
                dto.setApproveType(DataUtil.safeToLong(obj[i++]));
                dto.setStatus(DataUtil.safeToLong(obj[i++]));
                dto.setChargeType(DataUtil.safeToString(obj[i++]));
                dto.setCurrency(DataUtil.safeToString(obj[i++]));
                dto.setBalance(DataUtil.safeToLong(obj[i++]));
                dto.setAdBalance(DataUtil.safeToLong(obj[i++]));
                dto.setProvinceName(DataUtil.safeToString(obj[i++]));
                dto.setDistrictName(DataUtil.safeToString(obj[i++]));
                dto.setAddress(DataUtil.safeToString(obj[i++]));
                dto.setWsUsername(DataUtil.safeToString(obj[i++]));
                dto.setCpSysid(DataUtil.safeToString(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

    public String getCommissionPercentNote() {
        return commissionPercentNote;
    }

    public void setCommissionPercentNote(String commissionPercentNote) {
        this.commissionPercentNote = commissionPercentNote;
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

    public Long getApproveType() {
        return approveType;
    }

    public void setApproveType(Long approveType) {
        this.approveType = approveType;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAdBalance() {
        return adBalance;
    }

    public void setAdBalance(Long adBalance) {
        this.adBalance = adBalance;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceUserName() {
        return provinceUserName;
    }

    public void setProvinceUserName(String provinceUserName) {
        this.provinceUserName = provinceUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAcceptSearchData() {
        return acceptSearchData;
    }

    public void setAcceptSearchData(String acceptSearchData) {
        this.acceptSearchData = acceptSearchData;
    }

    public String getCpSysid() {
        return cpSysid;
    }

    public void setCpSysid(String cpSysid) {
        this.cpSysid = cpSysid;
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

    public Long getReceiverFlg() {
        return receiverFlg;
    }

    public void setReceiverFlg(Long receiverFlg) {
        this.receiverFlg = receiverFlg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApproveTypeName() {
        return approveTypeName;
    }

    public void setApproveTypeName(String approveTypeName) {
        this.approveTypeName = approveTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOldValueCommissionPercentCode() {
        return oldValueCommissionPercentCode;
    }

    public void setOldValueCommissionPercentCode(String oldValueCommissionPercentCode) {
        this.oldValueCommissionPercentCode = oldValueCommissionPercentCode;
    }

    public Boolean getWsPassChange() {
        return isWsPassChange;
    }

    public void setWsPassChange(Boolean wsPassChange) {
        isWsPassChange = wsPassChange;
    }

    public Boolean getConfirmExistedCp() {
        return confirmExistedCp;
    }

    public void setConfirmExistedCp(Boolean confirmExistedCp) {
        this.confirmExistedCp = confirmExistedCp;
    }
}
