package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CpAlias.
 */
@Entity
@Table(name = "cp_alias")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CpAlias implements Serializable {

    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "cpAliasIdSeq", sequenceName = "CP_ALIAS_ID_SEQ", allocationSize = 1)
    @Id
    @Column(name = "CP_ALIAS_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cpAliasIdSeq")
    private Long cpAliasId;

    @Column(name = "cp_alias")
    private String cpAlias;

    @Column(name = "status")
    private Long status;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "ACCEPT_PARENTCP_SEND")
    private Long acceptParentCpSend;

    @Column(name = "type")
    private Long type;

    @Column(name = "group_type")
    private Long groupType;

    @Column(name = "telco")
    private String telco;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "webservice")
    private String webservice;

    @Column(name = "webservice_backup")
    private String webserviceBackup;

    @Column(name = "alias_type")
    private Long aliasType;

    @Column(name = "optional_keep_fee")
    private Long optionalKeepFee;

    @Column(name = "keep_fee")
    private Long keepFee;

    @Column(name = "last_charge_time")
    private Instant lastChargeTime;

    @Column(name = "last_charge_status")
    private Long lastChargeStatus;

    @Column(name = "process_id")
    private Long processId;

    @Column(name = "process_time")
    private Instant processTime;

    @Column(name = "acc_update")
    private String accUpdate;

    @Column(name = "check_duplicate")
    private Long checkDuplicate;

    @Column(name = "inactive_date")
    private Instant inactiveDate;

    @Column(name = "attach_file")
    private String attachFile;

    @Column(name = "time_repeat")
    private Long timeRepeat;

    @Column(name = "check_block_spam")
    private String checkBlockSpam;

    @Column(name = "file_path_create")
    private String filePathCreate;

    @Column(name = "file_path_cancel")
    private String filePathCancel;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "acc_create")
    private String accCreate;

    @Column(name = "month_keep_fee")
    private Long monthKeepFee;

    @Column(name = "number_sms_check_spam")
    private Long numberSmsCheckSpam;

    @Column(name = "cp_alias_tmp_id")
    private Long cpAliasTmpId;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "company_name_roman")
    private String companyNameRoman;

    @Column(name = "ussd_enabled")
    private Long ussdEnabled;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "business_registration")
    private String businessRegistration;

    @Column(name = "company_phone_number")
    private String companyPhoneNumber;

    @Column(name = "company_fax_number")
    private String companyFaxNumber;

    @Column(name = "company_email")
    private String companyEmail;

    @Transient
    private String inactiveDateStr;

    @Transient
    private String telcoCode;

    public String getTelcoCode() {
        return telcoCode;
    }

    public void setTelcoCode(String telcoCode) {
        this.telcoCode = telcoCode;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Long getAcceptParentCpSend() {
        return acceptParentCpSend;
    }

    public void setAcceptParentCpSend(Long acceptParentCpSend) {
        this.acceptParentCpSend = acceptParentCpSend;
    }

    public Long getCpAliasId() {
        return cpAliasId;
    }

    public CpAlias cpAliasId(Long cpAliasId) {
        this.cpAliasId = cpAliasId;
        return this;
    }

    public void setCpAliasId(Long cpAliasId) {
        this.cpAliasId = cpAliasId;
    }

    public String getCpAlias() {
        return cpAlias;
    }

    public CpAlias cpAlias(String cpAlias) {
        this.cpAlias = cpAlias;
        return this;
    }

    public void setCpAlias(String cpAlias) {
        this.cpAlias = cpAlias;
    }

    public Long getStatus() {
        return status;
    }

    public CpAlias status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getCpId() {
        return cpId;
    }

    public CpAlias cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Long getType() {
        return type;
    }

    public CpAlias type(Long type) {
        this.type = type;
        return this;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getGroupType() {
        return groupType;
    }

    public CpAlias groupType(Long groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
    }

    public String getTelco() {
        return telco;
    }

    public CpAlias telco(String telco) {
        this.telco = telco;
        return this;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public CpAlias createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public CpAlias endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public CpAlias updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getWebservice() {
        return webservice;
    }

    public CpAlias webservice(String webservice) {
        this.webservice = webservice;
        return this;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public String getWebserviceBackup() {
        return webserviceBackup;
    }

    public CpAlias webserviceBackup(String webserviceBackup) {
        this.webserviceBackup = webserviceBackup;
        return this;
    }

    public void setWebserviceBackup(String webserviceBackup) {
        this.webserviceBackup = webserviceBackup;
    }

    public Long getAliasType() {
        return aliasType;
    }

    public CpAlias aliasType(Long aliasType) {
        this.aliasType = aliasType;
        return this;
    }

    public void setAliasType(Long aliasType) {
        this.aliasType = aliasType;
    }

    public Long getOptionalKeepFee() {
        return optionalKeepFee;
    }

    public CpAlias optionalKeepFee(Long optionalKeepFee) {
        this.optionalKeepFee = optionalKeepFee;
        return this;
    }

    public void setOptionalKeepFee(Long optionalKeepFee) {
        this.optionalKeepFee = optionalKeepFee;
    }

    public Long getKeepFee() {
        return keepFee;
    }

    public CpAlias keepFee(Long keepFee) {
        this.keepFee = keepFee;
        return this;
    }

    public void setKeepFee(Long keepFee) {
        this.keepFee = keepFee;
    }

    public Instant getLastChargeTime() {
        return lastChargeTime;
    }

    public CpAlias lastChargeTime(Instant lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
        return this;
    }

    public void setLastChargeTime(Instant lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Long getLastChargeStatus() {
        return lastChargeStatus;
    }

    public CpAlias lastChargeStatus(Long lastChargeStatus) {
        this.lastChargeStatus = lastChargeStatus;
        return this;
    }

    public void setLastChargeStatus(Long lastChargeStatus) {
        this.lastChargeStatus = lastChargeStatus;
    }

    public Long getProcessId() {
        return processId;
    }

    public CpAlias processId(Long processId) {
        this.processId = processId;
        return this;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Instant getProcessTime() {
        return processTime;
    }

    public CpAlias processTime(Instant processTime) {
        this.processTime = processTime;
        return this;
    }

    public void setProcessTime(Instant processTime) {
        this.processTime = processTime;
    }

    public String getAccUpdate() {
        return accUpdate;
    }

    public CpAlias accUpdate(String accUpdate) {
        this.accUpdate = accUpdate;
        return this;
    }

    public void setAccUpdate(String accUpdate) {
        this.accUpdate = accUpdate;
    }

    public Long getCheckDuplicate() {
        return checkDuplicate;
    }

    public CpAlias checkDuplicate(Long checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
        return this;
    }

    public void setCheckDuplicate(Long checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public Instant getInactiveDate() {
        return inactiveDate;
    }

    public CpAlias inactiveDate(Instant inactiveDate) {
        this.inactiveDate = inactiveDate;
        return this;
    }

    public void setInactiveDate(Instant inactiveDate) {
        this.inactiveDate = inactiveDate;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public CpAlias attachFile(String attachFile) {
        this.attachFile = attachFile;
        return this;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public Long getTimeRepeat() {
        return timeRepeat;
    }

    public CpAlias timeRepeat(Long timeRepeat) {
        this.timeRepeat = timeRepeat;
        return this;
    }

    public void setTimeRepeat(Long timeRepeat) {
        this.timeRepeat = timeRepeat;
    }

    public String getCheckBlockSpam() {
        return checkBlockSpam;
    }

    public CpAlias checkBlockSpam(String checkBlockSpam) {
        this.checkBlockSpam = checkBlockSpam;
        return this;
    }

    public void setCheckBlockSpam(String checkBlockSpam) {
        this.checkBlockSpam = checkBlockSpam;
    }




    public String getFilePathCreate() {
        return filePathCreate;
    }

    public CpAlias filePathCreate(String filePathCreate) {
        this.filePathCreate = filePathCreate;
        return this;
    }

    public void setFilePathCreate(String filePathCreate) {
        this.filePathCreate = filePathCreate;
    }

    public String getFilePathCancel() {
        return filePathCancel;
    }

    public CpAlias filePathCancel(String filePathCancel) {
        this.filePathCancel = filePathCancel;
        return this;
    }

    public void setFilePathCancel(String filePathCancel) {
        this.filePathCancel = filePathCancel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CpAlias companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccCreate() {
        return accCreate;
    }

    public CpAlias accCreate(String accCreate) {
        this.accCreate = accCreate;
        return this;
    }

    public void setAccCreate(String accCreate) {
        this.accCreate = accCreate;
    }

    public Long getMonthKeepFee() {
        return monthKeepFee;
    }

    public CpAlias monthKeepFee(Long monthKeepFee) {
        this.monthKeepFee = monthKeepFee;
        return this;
    }

    public void setMonthKeepFee(Long monthKeepFee) {
        this.monthKeepFee = monthKeepFee;
    }

    public Long getNumberSmsCheckSpam() {
        return numberSmsCheckSpam;
    }

    public CpAlias numberSmsCheckSpam(Long numberSmsCheckSpam) {
        this.numberSmsCheckSpam = numberSmsCheckSpam;
        return this;
    }

    public void setNumberSmsCheckSpam(Long numberSmsCheckSpam) {
        this.numberSmsCheckSpam = numberSmsCheckSpam;
    }

    public Long getCpAliasTmpId() {
        return cpAliasTmpId;
    }

    public CpAlias cpAliasTmpId(Long cpAliasTmpId) {
        this.cpAliasTmpId = cpAliasTmpId;
        return this;
    }

    public void setCpAliasTmpId(Long cpAliasTmpId) {
        this.cpAliasTmpId = cpAliasTmpId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public CpAlias taxCode(String taxCode) {
        this.taxCode = taxCode;
        return this;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCompanyNameRoman() {
        return companyNameRoman;
    }

    public CpAlias companyNameRoman(String companyNameRoman) {
        this.companyNameRoman = companyNameRoman;
        return this;
    }

    public void setCompanyNameRoman(String companyNameRoman) {
        this.companyNameRoman = companyNameRoman;
    }

    public Long getUssdEnabled() {
        return ussdEnabled;
    }

    public CpAlias ussdEnabled(Long ussdEnabled) {
        this.ussdEnabled = ussdEnabled;
        return this;
    }

    public void setUssdEnabled(Long ussdEnabled) {
        this.ussdEnabled = ussdEnabled;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public CpAlias companyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getBusinessRegistration() {
        return businessRegistration;
    }

    public CpAlias businessRegistration(String businessRegistration) {
        this.businessRegistration = businessRegistration;
        return this;
    }

    public void setBusinessRegistration(String businessRegistration) {
        this.businessRegistration = businessRegistration;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public CpAlias companyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
        return this;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyFaxNumber() {
        return companyFaxNumber;
    }

    public CpAlias companyFaxNumber(String companyFaxNumber) {
        this.companyFaxNumber = companyFaxNumber;
        return this;
    }

    public void setCompanyFaxNumber(String companyFaxNumber) {
        this.companyFaxNumber = companyFaxNumber;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public CpAlias companyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
        return this;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public String getInactiveDateStr() {
        return inactiveDateStr;
    }

    public void setInactiveDateStr(String inactiveDateStr) {
        this.inactiveDateStr = inactiveDateStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpAlias)) {
            return false;
        }
        return cpAliasId != null && cpAliasId.equals(((CpAlias) o).cpAliasId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpAlias{" +
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
            ", acceptParentcpSend=" + getAcceptParentCpSend() +
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
