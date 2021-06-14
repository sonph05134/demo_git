package com.viettel.smsbrandname.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CP_ALIAS_TMP")
public class CPAliasTmp implements Serializable {

    @SequenceGenerator(name = "cpAliasTmpIdSeq", sequenceName = "CP_ALIAS_TMP_ID_SEQ", allocationSize = 1)
    @Id
    @Column(name = "CP_ALIAS_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cpAliasTmpIdSeq")
    private Long cpAliasId;
    @Column(name = "CP_ALIAS")
    private String cpAlias;
    @Column(name = "STATUS")
    private Short status;
    @Column(name = "APPROVE")
//0: new, 1: pending, 2: approve, 3: deny, 4: pendingCancel, 5: approveCancel, 6: denyCancel, 7: pendingEdit, 8: denyEdit, 9: pendingRestore, 10: denyRestore
    private Short approve;
    @Column(name = "CP_ID")
    private Long cpId;
    @Column(name = "TYPE")
    private Short type;
    @Column(name = "TELCO")
    private String telco;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "ALIAS_TYPE")
    private Long aliasType;
    @Column(name = "OPTIONAL_KEEP_FEE")
    private Long optionalKeepFee;
    @Column(name = "KEEP_FEE")
    private Long keepFee;
    @Column(name = "LAST_CHARGE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChargeTime;
    @Column(name = "GROUP_TYPE")
    private Long groupType;
    @Column(name = "WEBSERVICE")
    private String webservice;
    @Column(name = "WEBSERVICE_BACKUP")
    private String webserviceBackup;
    @Column(name = "INACTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inactiveDate;
    @Column(name = "ATTACH_FILE")
    private String attachFile;

    @Column(name = "TIME_REPEAT")
    private Long timeRepeat;
    @Column(name = "ACC_UPDATE")
    private String accUpdate;
    @Column(name = "CHECK_BLOCK_SPAM")
    private String checkBlockSpam;
    @Column(name = "FILE_PATH_CREATE")
    private String filePathCreate;
    @Column(name = "FILE_PATH_CANCEL")
    private String filePathCancel;
    @Column(name = "REASON_FOR_DENY")
    private String reasonForDeny;
    @Column(name = "REASON_FOR_CANCEL")
    private String reasonForCancel;
    @Column(name = "REASON_FOR_DENY_CANCEL")
    private String reasonForDenyCancel;
    @Column(name = "REASON_FOR_DENY_EDIT")
    private String reasonForDenyEdit;
    @Column(name = "REASON_FOR_DENY_RESTORE")
    private String reasonForDenyRestore;
    @Column(name = "USER_APPROVE")
    private String userApprove;
    @Column(name = "USER_CREATED")
    private String userCreated;
    @Column(name = "DATE_APPROVE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApprove;
    @Column(name = "ACCEPT_PARENTCP_SEND")
    private Short acceptParentCpSend;
    @Column(name = "CHECK_DUPLICATE")
    private Short checkDuplicate;
    @Column(name = "COMPANY_NAME")
    private String companyName;
    @Column(name = "MONTH_KEEP_FEE")
    private Long monthKeepFee;
    @Column(name = "NUMBER_SMS_CHECK_SPAM")
    private Long numberSmsCheckSpam;
    @Column(name = "DATE_PENDING")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePending;
    @Column(name = "DATE_DENYCANCEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDenycancel;
    @Column(name = "DATE_DENY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeny;
    @Column(name = "DATE_PENDINGCANCEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePendingcancel;
    @Column(name = "DATE_APPROVECANCEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApprovecancel;
    @Column(name = "DATE_PENDINGEDIT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePendingedit;
    @Column(name = "DATE_DENYEDIT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDenyedit;
    @Column(name = "DATE_PENDINGRESTORE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePendingrestore;
    @Column(name = "DATE_DENYRESTORE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDenyrestore;
    @Column(name = "LAST_CHARGE_STATUS")
    private Short lastChargeStatus;
    @Column(name = "PROCESS_ID")
    private Short processId;
    @Column(name = "PROCESS_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date processTime;

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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getApprove() {
        return approve;
    }

    public void setApprove(Short approve) {
        this.approve = approve;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Date getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Date lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Long getGroupType() {
        return groupType;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
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

    public Date getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(Date inactiveDate) {
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

    public String getAccUpdate() {
        return accUpdate;
    }

    public void setAccUpdate(String accUpdate) {
        this.accUpdate = accUpdate;
    }

    public String getCheckBlockSpam() {
        return checkBlockSpam;
    }

    public void setCheckBlockSpam(String checkBlockSpam) {
        this.checkBlockSpam = checkBlockSpam;
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

    public String getReasonForDeny() {
        return reasonForDeny;
    }

    public void setReasonForDeny(String reasonForDeny) {
        this.reasonForDeny = reasonForDeny;
    }

    public String getReasonForCancel() {
        return reasonForCancel;
    }

    public void setReasonForCancel(String reasonForCancel) {
        this.reasonForCancel = reasonForCancel;
    }

    public String getReasonForDenyCancel() {
        return reasonForDenyCancel;
    }

    public void setReasonForDenyCancel(String reasonForDenyCancel) {
        this.reasonForDenyCancel = reasonForDenyCancel;
    }

    public String getReasonForDenyEdit() {
        return reasonForDenyEdit;
    }

    public void setReasonForDenyEdit(String reasonForDenyEdit) {
        this.reasonForDenyEdit = reasonForDenyEdit;
    }

    public String getReasonForDenyRestore() {
        return reasonForDenyRestore;
    }

    public void setReasonForDenyRestore(String reasonForDenyRestore) {
        this.reasonForDenyRestore = reasonForDenyRestore;
    }

    public String getUserApprove() {
        return userApprove;
    }

    public void setUserApprove(String userApprove) {
        this.userApprove = userApprove;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateApprove() {
        return dateApprove;
    }

    public void setDateApprove(Date dateApprove) {
        this.dateApprove = dateApprove;
    }

    public Short getAcceptParentCpSend() {
        return acceptParentCpSend;
    }

    public void setAcceptParentCpSend(Short acceptParentCpSend) {
        this.acceptParentCpSend = acceptParentCpSend;
    }

    public Short getCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(Short checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Date getDatePending() {
        return datePending;
    }

    public void setDatePending(Date datePending) {
        this.datePending = datePending;
    }

    public Date getDateDenycancel() {
        return dateDenycancel;
    }

    public void setDateDenycancel(Date dateDenycancel) {
        this.dateDenycancel = dateDenycancel;
    }

    public Date getDateDeny() {
        return dateDeny;
    }

    public void setDateDeny(Date dateDeny) {
        this.dateDeny = dateDeny;
    }

    public Date getDatePendingcancel() {
        return datePendingcancel;
    }

    public void setDatePendingcancel(Date datePendingcancel) {
        this.datePendingcancel = datePendingcancel;
    }

    public Date getDateApprovecancel() {
        return dateApprovecancel;
    }

    public void setDateApprovecancel(Date dateApprovecancel) {
        this.dateApprovecancel = dateApprovecancel;
    }

    public Date getDatePendingedit() {
        return datePendingedit;
    }

    public void setDatePendingedit(Date datePendingedit) {
        this.datePendingedit = datePendingedit;
    }

    public Date getDateDenyedit() {
        return dateDenyedit;
    }

    public void setDateDenyedit(Date dateDenyedit) {
        this.dateDenyedit = dateDenyedit;
    }

    public Date getDatePendingrestore() {
        return datePendingrestore;
    }

    public void setDatePendingrestore(Date datePendingrestore) {
        this.datePendingrestore = datePendingrestore;
    }

    public Date getDateDenyrestore() {
        return dateDenyrestore;
    }

    public void setDateDenyrestore(Date dateDenyrestore) {
        this.dateDenyrestore = dateDenyrestore;
    }

    public Short getLastChargeStatus() {
        return lastChargeStatus;
    }

    public void setLastChargeStatus(Short lastChargeStatus) {
        this.lastChargeStatus = lastChargeStatus;
    }

    public Short getProcessId() {
        return processId;
    }

    public void setProcessId(Short processId) {
        this.processId = processId;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }
}
