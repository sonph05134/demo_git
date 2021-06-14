package com.viettel.smsbrandname.service.dto;


import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpBrandResultDTO {

    private String aliasTypeName;
    private String alias;
    private String groupTypeName;
    private String telco;
    private String createDateStr;
    private String statusName;
    private Long timeRepeat;
    private Integer RN;
    private Integer status;
    private Long cpAliasTmpId;
    private Long cpAliasId;

    private Long aliasType;
    private String attachFile;
    private Short checkDuplicate;
    private String checkBlockSpam;
    private Date inactiveDate;
    private String webserviceBackup;
    private Long monthKeepFee;
    private Long optionalKeepFee;
    private Long groupType;
    private Long keepFee;
    private String webservice;
    private Long numberSmsCheckSpam;
    private Integer acceptParentCpSend;
    private String taxCode;
    private Integer cpId;
    private String companyName;

    public String getAliasTypeName() {
        return aliasTypeName;
    }

    public void setAliasTypeName(String aliasTypeName) {
        this.aliasTypeName = aliasTypeName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGroupTypeName() {
        return groupTypeName;
    }

    public void setGroupTypeName(String groupTypeName) {
        this.groupTypeName = groupTypeName;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getTimeRepeat() {
        return timeRepeat;
    }

    public void setTimeRepeat(Long timeRepeat) {
        this.timeRepeat = timeRepeat;
    }

    public Integer getRN() {
        return RN;
    }

    public void setRN(Integer RN) {
        this.RN = RN;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCpAliasTmpId() {
        return cpAliasTmpId;
    }

    public void setCpAliasTmpId(Long cpAliasTmpId) {
        this.cpAliasTmpId = cpAliasTmpId;
    }

    public Long getCpAliasId() {
        return cpAliasId;
    }

    public void setCpAliasId(Long cpAliasId) {
        this.cpAliasId = cpAliasId;
    }

    public Long getAliasType() {
        return aliasType;
    }

    public void setAliasType(Long aliasType) {
        this.aliasType = aliasType;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public Short getCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(Short checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public String getCheckBlockSpam() {
        return checkBlockSpam;
    }

    public void setCheckBlockSpam(String checkBlockSpam) {
        this.checkBlockSpam = checkBlockSpam;
    }

    public Date getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(Date inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public String getWebserviceBackup() {
        return webserviceBackup;
    }

    public void setWebserviceBackup(String webserviceBackup) {
        this.webserviceBackup = webserviceBackup;
    }

    public Long getMonthKeepFee() {
        return monthKeepFee;
    }

    public void setMonthKeepFee(Long monthKeepFee) {
        this.monthKeepFee = monthKeepFee;
    }

    public Long getOptionalKeepFee() {
        return optionalKeepFee;
    }

    public void setOptionalKeepFee(Long optionalKeepFee) {
        this.optionalKeepFee = optionalKeepFee;
    }

    public Long getGroupType() {
        return groupType;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
    }

    public Long getKeepFee() {
        return keepFee;
    }

    public void setKeepFee(Long keepFee) {
        this.keepFee = keepFee;
    }

    public String getWebservice() {
        return webservice;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public Long getNumberSmsCheckSpam() {
        return numberSmsCheckSpam;
    }

    public void setNumberSmsCheckSpam(Long numberSmsCheckSpam) {
        this.numberSmsCheckSpam = numberSmsCheckSpam;
    }

    public Integer getAcceptParentCpSend() {
        return acceptParentCpSend;
    }

    public void setAcceptParentCpSend(Integer acceptParentCpSend) {
        this.acceptParentCpSend = acceptParentCpSend;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static List<CpBrandResultDTO> toDto(List<Object[]> listData) {
        List<CpBrandResultDTO> result = new ArrayList<>();
        if (listData != null && !listData.isEmpty()) {
            for (Object[] data : listData) {
                int i = 0;
                CpBrandResultDTO cpBrandResultDTO = new CpBrandResultDTO();
                cpBrandResultDTO.setAliasTypeName(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setAlias(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setGroupTypeName(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setTelco(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setCreateDateStr(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setStatusName(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setTimeRepeat(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setRN(DataUtil.safeToInt(data[i++]));
                cpBrandResultDTO.setStatus(DataUtil.safeToInt(data[i++]));
                cpBrandResultDTO.setCpAliasTmpId(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setCpAliasId(DataUtil.safeToLong(data[i++]));

                cpBrandResultDTO.setAliasType(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setAttachFile(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setCheckDuplicate((short) DataUtil.safeToInt(data[i++]));
                cpBrandResultDTO.setCheckBlockSpam(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setInactiveDate(data[i++] != null ? (Date) data[15] : null);
                cpBrandResultDTO.setWebserviceBackup(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setMonthKeepFee(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setOptionalKeepFee(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setGroupType(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setKeepFee(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setWebservice(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setNumberSmsCheckSpam(DataUtil.safeToLong(data[i++]));
                cpBrandResultDTO.setAcceptParentCpSend(DataUtil.safeToInt(data[i++]));
                cpBrandResultDTO.setTaxCode(DataUtil.safeToString(data[i++]));
                cpBrandResultDTO.setCpId(DataUtil.safeToInt(data[i++]));
                cpBrandResultDTO.setCompanyName(DataUtil.safeToString(data[i++]));
                result.add(cpBrandResultDTO);
            }
        }
        return result;
    }
}
