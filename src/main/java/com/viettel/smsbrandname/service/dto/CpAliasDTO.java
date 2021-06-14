package com.viettel.smsbrandname.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.CpAlias} entity.
 */
public class CpAliasDTO implements Serializable {

    private Long cpAliasId;

    private String cpAlias;

    private Long status;

    private Long cpId;

    private Long type;

    private Long groupType;

    private String telco;

    private Instant createDate;

    private Instant endDate;

    private Instant updateDate;

    private String webservice;

    private String webserviceBackup;

    private Long aliasType;

    private Long optionalKeepFee;

    private Long keepFee;

    private Instant lastChargeTime;

    private Long lastChargeStatus;

    private Long processId;

    private Instant processTime;

    private String accUpdate;

    private Long checkDuplicate;

    private Instant inactiveDate;

    private String attachFile;

    private Long timeRepeat;

    private String checkBlockSpam;

    private Long acceptParentcpSend;

    private String filePathCreate;

    private String filePathCancel;

    private String companyName;

    private String accCreate;

    private Long monthKeepFee;

    private Long numberSmsCheckSpam;

    private Long cpAliasTmpId;

    private String taxCode;

    private String companyNameRoman;

    private Long ussdEnabled;

    private String companyAddress;

    private String businessRegistration;

    private String companyPhoneNumber;

    private String companyFaxNumber;

    private String companyEmail;

    public Long getCpAliasId() {
        return cpAliasId;
    }

    public void setCpAliasId(Long cpAliasId) {
        this.cpAliasId = cpAliasId;
    }

    public String getCpAlias() {
        return cpAlias;
    }

    public void setCpAlias(String cpAlias) {
        this.cpAlias = cpAlias;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getGroupType() {
        return groupType;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getWebservice() {
        return webservice;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public String getWebserviceBackup() {
        return webserviceBackup;
    }

    public void setWebserviceBackup(String webserviceBackup) {
        this.webserviceBackup = webserviceBackup;
    }

    public Long getAliasType() {
        return aliasType;
    }

    public void setAliasType(Long aliasType) {
        this.aliasType = aliasType;
    }

    public Long getOptionalKeepFee() {
        return optionalKeepFee;
    }

    public void setOptionalKeepFee(Long optionalKeepFee) {
        this.optionalKeepFee = optionalKeepFee;
    }

    public Long getKeepFee() {
        return keepFee;
    }

    public void setKeepFee(Long keepFee) {
        this.keepFee = keepFee;
    }

    public Instant getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Instant lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Long getLastChargeStatus() {
        return lastChargeStatus;
    }

    public void setLastChargeStatus(Long lastChargeStatus) {
        this.lastChargeStatus = lastChargeStatus;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Instant getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Instant processTime) {
        this.processTime = processTime;
    }

    public String getAccUpdate() {
        return accUpdate;
    }

    public void setAccUpdate(String accUpdate) {
        this.accUpdate = accUpdate;
    }

    public Long getCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(Long checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public Instant getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(Instant inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public Long getTimeRepeat() {
        return timeRepeat;
    }

    public void setTimeRepeat(Long timeRepeat) {
        this.timeRepeat = timeRepeat;
    }

    public String getCheckBlockSpam() {
        return checkBlockSpam;
    }

    public void setCheckBlockSpam(String checkBlockSpam) {
        this.checkBlockSpam = checkBlockSpam;
    }

    public Long getAcceptParentcpSend() {
        return acceptParentcpSend;
    }

    public void setAcceptParentcpSend(Long acceptParentcpSend) {
        this.acceptParentcpSend = acceptParentcpSend;
    }

    public String getFilePathCreate() {
        return filePathCreate;
    }

    public void setFilePathCreate(String filePathCreate) {
        this.filePathCreate = filePathCreate;
    }

    public String getFilePathCancel() {
        return filePathCancel;
    }

    public void setFilePathCancel(String filePathCancel) {
        this.filePathCancel = filePathCancel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccCreate() {
        return accCreate;
    }

    public void setAccCreate(String accCreate) {
        this.accCreate = accCreate;
    }

    public Long getMonthKeepFee() {
        return monthKeepFee;
    }

    public void setMonthKeepFee(Long monthKeepFee) {
        this.monthKeepFee = monthKeepFee;
    }

    public Long getNumberSmsCheckSpam() {
        return numberSmsCheckSpam;
    }

    public void setNumberSmsCheckSpam(Long numberSmsCheckSpam) {
        this.numberSmsCheckSpam = numberSmsCheckSpam;
    }

    public Long getCpAliasTmpId() {
        return cpAliasTmpId;
    }

    public void setCpAliasTmpId(Long cpAliasTmpId) {
        this.cpAliasTmpId = cpAliasTmpId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCompanyNameRoman() {
        return companyNameRoman;
    }

    public void setCompanyNameRoman(String companyNameRoman) {
        this.companyNameRoman = companyNameRoman;
    }

    public Long getUssdEnabled() {
        return ussdEnabled;
    }

    public void setUssdEnabled(Long ussdEnabled) {
        this.ussdEnabled = ussdEnabled;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getBusinessRegistration() {
        return businessRegistration;
    }

    public void setBusinessRegistration(String businessRegistration) {
        this.businessRegistration = businessRegistration;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyFaxNumber() {
        return companyFaxNumber;
    }

    public void setCompanyFaxNumber(String companyFaxNumber) {
        this.companyFaxNumber = companyFaxNumber;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpAliasDTO)) {
            return false;
        }

        return cpAliasId != null && cpAliasId.equals(((CpAliasDTO) o).cpAliasId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpAliasDTO{" +
            " cpAliasId=" + getCpAliasId() +
            ", cpAlias='" + getCpAlias() + "'" +
            ", status=" + getStatus() +
            ", cpId=" + getCpId() +
            ", type=" + getType() +
            ", groupType=" + getGroupType() +
            ", telco='" + getTelco() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", webservice='" + getWebservice() + "'" +
            ", webserviceBackup='" + getWebserviceBackup() + "'" +
            ", aliasType=" + getAliasType() +
            ", optionalKeepFee=" + getOptionalKeepFee() +
            ", keepFee=" + getKeepFee() +
            ", lastChargeTime='" + getLastChargeTime() + "'" +
            ", lastChargeStatus=" + getLastChargeStatus() +
            ", processId=" + getProcessId() +
            ", processTime='" + getProcessTime() + "'" +
            ", accUpdate='" + getAccUpdate() + "'" +
            ", checkDuplicate=" + getCheckDuplicate() +
            ", inactiveDate='" + getInactiveDate() + "'" +
            ", attachFile='" + getAttachFile() + "'" +
            ", timeRepeat=" + getTimeRepeat() +
            ", checkBlockSpam='" + getCheckBlockSpam() + "'" +
            ", acceptParentcpSend=" + getAcceptParentcpSend() +
            ", filePathCreate='" + getFilePathCreate() + "'" +
            ", filePathCancel='" + getFilePathCancel() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", accCreate='" + getAccCreate() + "'" +
            ", monthKeepFee=" + getMonthKeepFee() +
            ", numberSmsCheckSpam=" + getNumberSmsCheckSpam() +
            ", cpAliasTmpId=" + getCpAliasTmpId() +
            ", taxCode='" + getTaxCode() + "'" +
            ", companyNameRoman='" + getCompanyNameRoman() + "'" +
            ", ussdEnabled=" + getUssdEnabled() +
            ", companyAddress='" + getCompanyAddress() + "'" +
            ", businessRegistration='" + getBusinessRegistration() + "'" +
            ", companyPhoneNumber='" + getCompanyPhoneNumber() + "'" +
            ", companyFaxNumber='" + getCompanyFaxNumber() + "'" +
            ", companyEmail='" + getCompanyEmail() + "'" +
            "}";
    }
}
