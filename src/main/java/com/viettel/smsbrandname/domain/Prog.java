package com.viettel.smsbrandname.domain;

/**
 * Created by pmvt-os-chc-132 on 12/25/2020.
 */
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Prog.
 */
@Entity
@Table(name = "prog")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(false)
public class Prog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "progSeq", sequenceName = "PROG_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progSeq")
    @Column(name = "prog_id")
    private Long progId;

    @Column(name = "prog_code")
    private String progCode;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "alias")
    private String alias;

    @Column(name = "status")
    private String status;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "finish_date")
    private Instant finishDate;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "deleted")
    private Long deleted;

    @Column(name = "link")
    private String link;

    @Lob
    @Column(name = "attach_file")
    private byte[] attachFile;

//    @Column(name = "attach_file_content_type")
//    private String attachFileContentType;

    @Column(name = "alias_detail")
    private String aliasDetail;

    @Column(name = "type")
    private String type;

    @Column(name = "cp_group_id")
    private Long cpGroupId;

    @Column(name = "group_sex")
    private Long groupSex;

    @Column(name = "sent_schedule")
    private Instant sentSchedule;

    @Column(name = "content")
    private String content;

    @Column(name = "group_array")
    private String groupArray;

    @Column(name = "prog_type")
    private Long progType;

    @Column(name = "convert_vn")
    private Long convertVn;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "group_name_list")
    private String groupNameList;

    @Column(name = "force_send")
    private Long forceSend;

    @Column(name = "getsub_filter")
    private Long getsubFilter;

    @Column(name = "cat_id")
    private Long catId;

    @Column(name = "max_sms")
    private Long maxSms;

    @Column(name = "alias_id")
    private Long aliasId;

    @Column(name = "send_day")
    private String sendDay;

    @Column(name = "exception_day")
    private String exceptionDay;

    @Column(name = "sent_time_zone")
    private Long sentTimeZone;

    @Column(name = "start_time_zone")
    private Long startTimeZone;

    @Column(name = "finish_time_zone")
    private Long finishTimeZone;

    @Column(name = "actual_finish_date")
    private Instant actualFinishDate;

    @Column(name = "hide_prefix")
    private Long hidePrefix;

    @Column(name = "hide_suffix")
    private Long hideSuffix;

    @Column(name = "param_used")
    private Long paramUsed;

    @Column(name = "param_length")
    private Long paramLength;

    @Column(name = "num_test")
    private Long numTest;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "is_admin_upload")
    private Long isAdminUpload;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "total_sub")
    private Long totalSub;

    @Column(name = "total_charge")
    private Long totalCharge;

    @Column(name = "total_send_success")
    private Long totalSendSuccess;

    @Column(name = "total_send_fail")
    private Long totalSendFail;

    @Column(name = "total_sms_today")
    private Long totalSmsToday;

    @Column(name = "money_mo")
    private Long moneyMo;

    @Column(name = "money_mt")
    private Long moneyMt;

    @Column(name = "outside_reply")
    private Long outsideReply;

    @Column(name = "valid_reply_date")
    private Instant validReplyDate;

    @Column(name = "valid_reply_num")
    private Long validReplyNum;

    @Column(name = "update_sms_date")
    private Instant updateSmsDate;

    @Column(name = "num_test_sent")
    private Long numTestSent;

    @Column(name = "refund")
    private Long refund;

    @Column(name = "created_module")
    private Long createdModule;

    @Column(name = "mt_report_path")
    private String mtReportPath;

    @Column(name = "file_upload")
    private String fileUpload;

    @Column(name = "file_upload_status")
    private Long fileUploadStatus;

    @Column(name = "file_upload_type")
    private Long fileUploadType;

    @Column(name = "need_approve_run")
    private Long needApproveRun;

    @Column(name = "tested")
    private Long tested;

    @Column(name = "test_time")
    private Instant testTime;

    @Column(name = "test_user")
    private String testUser;

//    @Column(name = "list_phone")
//    private String listPhoneLong;

    @Column(name = "LIST_PHONENUMBER")
    private String listPhoneLongWithname;

    @Column(name = "LIST_PHONENUMBER_WITHNAME")
    private String listPhoneNumberWithname;

    public String getListPhoneNumberWithname() {
        return listPhoneNumberWithname;
    }

    public void setListPhoneNumberWithname(String listPhoneNumberWithname) {
        this.listPhoneNumberWithname = listPhoneNumberWithname;
    }

    public Long getProgId() {
        return progId;
    }

    public Prog progId(Long progId) {
        this.progId = progId;
        return this;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public String getProgCode() {
        return progCode;
    }

    public Prog progCode(String progCode) {
        this.progCode = progCode;
        return this;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public Long getCpId() {
        return cpId;
    }

    public Prog cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getAlias() {
        return alias;
    }

    public Prog alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStatus() {
        return status;
    }

    public Prog status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Prog createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public Prog finishDate(Instant finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Prog startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Long getDeleted() {
        return deleted;
    }

    public Prog deleted(Long deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getLink() {
        return link;
    }

    public Prog link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getAttachFile() {
        return attachFile;
    }

    public Prog attachFile(byte[] attachFile) {
        this.attachFile = attachFile;
        return this;
    }

    public void setAttachFile(byte[] attachFile) {
        this.attachFile = attachFile;
    }

//    public String getAttachFileContentType() {
//        return attachFileContentType;
//    }
//
//    public Prog attachFileContentType(String attachFileContentType) {
//        this.attachFileContentType = attachFileContentType;
//        return this;
//    }
//
//    public void setAttachFileContentType(String attachFileContentType) {
//        this.attachFileContentType = attachFileContentType;
//    }

    public String getAliasDetail() {
        return aliasDetail;
    }

    public Prog aliasDetail(String aliasDetail) {
        this.aliasDetail = aliasDetail;
        return this;
    }

    public void setAliasDetail(String aliasDetail) {
        this.aliasDetail = aliasDetail;
    }

    public String getType() {
        return type;
    }

    public Prog type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCpGroupId() {
        return cpGroupId;
    }

    public Prog cpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
        return this;
    }

    public void setCpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
    }

    public Long getGroupSex() {
        return groupSex;
    }

    public Prog groupSex(Long groupSex) {
        this.groupSex = groupSex;
        return this;
    }

    public void setGroupSex(Long groupSex) {
        this.groupSex = groupSex;
    }

    public Instant getSentSchedule() {
        return sentSchedule;
    }

    public Prog sentSchedule(Instant sentSchedule) {
        this.sentSchedule = sentSchedule;
        return this;
    }

    public void setSentSchedule(Instant sentSchedule) {
        this.sentSchedule = sentSchedule;
    }

    public String getContent() {
        return content;
    }

    public Prog content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupArray() {
        return groupArray;
    }

    public Prog groupArray(String groupArray) {
        this.groupArray = groupArray;
        return this;
    }

    public void setGroupArray(String groupArray) {
        this.groupArray = groupArray;
    }

    public Long getProgType() {
        return progType;
    }

    public Prog progType(Long progType) {
        this.progType = progType;
        return this;
    }

    public void setProgType(Long progType) {
        this.progType = progType;
    }

    public Long getConvertVn() {
        return convertVn;
    }

    public Prog convertVn(Long convertVn) {
        this.convertVn = convertVn;
        return this;
    }

    public void setConvertVn(Long convertVn) {
        this.convertVn = convertVn;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Prog createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public Prog updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getGroupNameList() {
        return groupNameList;
    }

    public Prog groupNameList(String groupNameList) {
        this.groupNameList = groupNameList;
        return this;
    }

    public void setGroupNameList(String groupNameList) {
        this.groupNameList = groupNameList;
    }

    public Long getForceSend() {
        return forceSend;
    }

    public Prog forceSend(Long forceSend) {
        this.forceSend = forceSend;
        return this;
    }

    public void setForceSend(Long forceSend) {
        this.forceSend = forceSend;
    }

    public Long getGetsubFilter() {
        return getsubFilter;
    }

    public Prog getsubFilter(Long getsubFilter) {
        this.getsubFilter = getsubFilter;
        return this;
    }

    public void setGetsubFilter(Long getsubFilter) {
        this.getsubFilter = getsubFilter;
    }

    public Long getCatId() {
        return catId;
    }

    public Prog catId(Long catId) {
        this.catId = catId;
        return this;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getMaxSms() {
        return maxSms;
    }

    public Prog maxSms(Long maxSms) {
        this.maxSms = maxSms;
        return this;
    }

    public void setMaxSms(Long maxSms) {
        this.maxSms = maxSms;
    }

    public Long getAliasId() {
        return aliasId;
    }

    public Prog aliasId(Long aliasId) {
        this.aliasId = aliasId;
        return this;
    }

    public void setAliasId(Long aliasId) {
        this.aliasId = aliasId;
    }

    public String getSendDay() {
        return sendDay;
    }

    public Prog sendDay(String sendDay) {
        this.sendDay = sendDay;
        return this;
    }

    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    public String getExceptionDay() {
        return exceptionDay;
    }

    public Prog exceptionDay(String exceptionDay) {
        this.exceptionDay = exceptionDay;
        return this;
    }

    public void setExceptionDay(String exceptionDay) {
        this.exceptionDay = exceptionDay;
    }

    public Long getSentTimeZone() {
        return sentTimeZone;
    }

    public Prog sentTimeZone(Long sentTimeZone) {
        this.sentTimeZone = sentTimeZone;
        return this;
    }

    public void setSentTimeZone(Long sentTimeZone) {
        this.sentTimeZone = sentTimeZone;
    }

    public Long getStartTimeZone() {
        return startTimeZone;
    }

    public Prog startTimeZone(Long startTimeZone) {
        this.startTimeZone = startTimeZone;
        return this;
    }

    public void setStartTimeZone(Long startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public Long getFinishTimeZone() {
        return finishTimeZone;
    }

    public Prog finishTimeZone(Long finishTimeZone) {
        this.finishTimeZone = finishTimeZone;
        return this;
    }

    public void setFinishTimeZone(Long finishTimeZone) {
        this.finishTimeZone = finishTimeZone;
    }

    public Instant getActualFinishDate() {
        return actualFinishDate;
    }

    public Prog actualFinishDate(Instant actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
        return this;
    }

    public void setActualFinishDate(Instant actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Long getHidePrefix() {
        return hidePrefix;
    }

    public Prog hidePrefix(Long hidePrefix) {
        this.hidePrefix = hidePrefix;
        return this;
    }

    public void setHidePrefix(Long hidePrefix) {
        this.hidePrefix = hidePrefix;
    }

    public Long getHideSuffix() {
        return hideSuffix;
    }

    public Prog hideSuffix(Long hideSuffix) {
        this.hideSuffix = hideSuffix;
        return this;
    }

    public void setHideSuffix(Long hideSuffix) {
        this.hideSuffix = hideSuffix;
    }

    public Long getParamUsed() {
        return paramUsed;
    }

    public Prog paramUsed(Long paramUsed) {
        this.paramUsed = paramUsed;
        return this;
    }

    public void setParamUsed(Long paramUsed) {
        this.paramUsed = paramUsed;
    }

    public Long getParamLength() {
        return paramLength;
    }

    public Prog paramLength(Long paramLength) {
        this.paramLength = paramLength;
        return this;
    }

    public void setParamLength(Long paramLength) {
        this.paramLength = paramLength;
    }

    public Long getNumTest() {
        return numTest;
    }

    public Prog numTest(Long numTest) {
        this.numTest = numTest;
        return this;
    }

    public void setNumTest(Long numTest) {
        this.numTest = numTest;
    }

    public String getSuffix() {
        return suffix;
    }

    public Prog suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getIsAdminUpload() {
        return isAdminUpload;
    }

    public Prog isAdminUpload(Long isAdminUpload) {
        this.isAdminUpload = isAdminUpload;
        return this;
    }

    public void setIsAdminUpload(Long isAdminUpload) {
        this.isAdminUpload = isAdminUpload;
    }

    public Long getPriority() {
        return priority;
    }

    public Prog priority(Long priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getTotalSub() {
        return totalSub;
    }

    public Prog totalSub(Long totalSub) {
        this.totalSub = totalSub;
        return this;
    }

    public void setTotalSub(Long totalSub) {
        this.totalSub = totalSub;
    }

    public Long getTotalCharge() {
        return totalCharge;
    }

    public Prog totalCharge(Long totalCharge) {
        this.totalCharge = totalCharge;
        return this;
    }

    public void setTotalCharge(Long totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Long getTotalSendSuccess() {
        return totalSendSuccess;
    }

    public Prog totalSendSuccess(Long totalSendSuccess) {
        this.totalSendSuccess = totalSendSuccess;
        return this;
    }

    public void setTotalSendSuccess(Long totalSendSuccess) {
        this.totalSendSuccess = totalSendSuccess;
    }

    public Long getTotalSendFail() {
        return totalSendFail;
    }

    public Prog totalSendFail(Long totalSendFail) {
        this.totalSendFail = totalSendFail;
        return this;
    }

    public void setTotalSendFail(Long totalSendFail) {
        this.totalSendFail = totalSendFail;
    }

    public Long getTotalSmsToday() {
        return totalSmsToday;
    }

    public Prog totalSmsToday(Long totalSmsToday) {
        this.totalSmsToday = totalSmsToday;
        return this;
    }

    public void setTotalSmsToday(Long totalSmsToday) {
        this.totalSmsToday = totalSmsToday;
    }

    public Long getMoneyMo() {
        return moneyMo;
    }

    public Prog moneyMo(Long moneyMo) {
        this.moneyMo = moneyMo;
        return this;
    }

    public void setMoneyMo(Long moneyMo) {
        this.moneyMo = moneyMo;
    }

    public Long getMoneyMt() {
        return moneyMt;
    }

    public Prog moneyMt(Long moneyMt) {
        this.moneyMt = moneyMt;
        return this;
    }

    public void setMoneyMt(Long moneyMt) {
        this.moneyMt = moneyMt;
    }

    public Long getOutsideReply() {
        return outsideReply;
    }

    public Prog outsideReply(Long outsideReply) {
        this.outsideReply = outsideReply;
        return this;
    }

    public void setOutsideReply(Long outsideReply) {
        this.outsideReply = outsideReply;
    }

    public Instant getValidReplyDate() {
        return validReplyDate;
    }

    public Prog validReplyDate(Instant validReplyDate) {
        this.validReplyDate = validReplyDate;
        return this;
    }

    public void setValidReplyDate(Instant validReplyDate) {
        this.validReplyDate = validReplyDate;
    }

    public Long getValidReplyNum() {
        return validReplyNum;
    }

    public Prog validReplyNum(Long validReplyNum) {
        this.validReplyNum = validReplyNum;
        return this;
    }

    public void setValidReplyNum(Long validReplyNum) {
        this.validReplyNum = validReplyNum;
    }

    public Instant getUpdateSmsDate() {
        return updateSmsDate;
    }

    public Prog updateSmsDate(Instant updateSmsDate) {
        this.updateSmsDate = updateSmsDate;
        return this;
    }

    public void setUpdateSmsDate(Instant updateSmsDate) {
        this.updateSmsDate = updateSmsDate;
    }

    public Long getNumTestSent() {
        return numTestSent;
    }

    public Prog numTestSent(Long numTestSent) {
        this.numTestSent = numTestSent;
        return this;
    }

    public void setNumTestSent(Long numTestSent) {
        this.numTestSent = numTestSent;
    }

    public Long getRefund() {
        return refund;
    }

    public Prog refund(Long refund) {
        this.refund = refund;
        return this;
    }

    public void setRefund(Long refund) {
        this.refund = refund;
    }

    public Long getCreatedModule() {
        return createdModule;
    }

    public Prog createdModule(Long createdModule) {
        this.createdModule = createdModule;
        return this;
    }

    public void setCreatedModule(Long createdModule) {
        this.createdModule = createdModule;
    }

    public String getMtReportPath() {
        return mtReportPath;
    }

    public Prog mtReportPath(String mtReportPath) {
        this.mtReportPath = mtReportPath;
        return this;
    }

    public void setMtReportPath(String mtReportPath) {
        this.mtReportPath = mtReportPath;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public Prog fileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
        return this;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public Long getFileUploadStatus() {
        return fileUploadStatus;
    }

    public Prog fileUploadStatus(Long fileUploadStatus) {
        this.fileUploadStatus = fileUploadStatus;
        return this;
    }

    public void setFileUploadStatus(Long fileUploadStatus) {
        this.fileUploadStatus = fileUploadStatus;
    }

    public Long getFileUploadType() {
        return fileUploadType;
    }

    public Prog fileUploadType(Long fileUploadType) {
        this.fileUploadType = fileUploadType;
        return this;
    }

    public void setFileUploadType(Long fileUploadType) {
        this.fileUploadType = fileUploadType;
    }

    public Long getNeedApproveRun() {
        return needApproveRun;
    }

    public Prog needApproveRun(Long needApproveRun) {
        this.needApproveRun = needApproveRun;
        return this;
    }

    public void setNeedApproveRun(Long needApproveRun) {
        this.needApproveRun = needApproveRun;
    }

    public Long getTested() {
        return tested;
    }

    public Prog tested(Long tested) {
        this.tested = tested;
        return this;
    }

    public void setTested(Long tested) {
        this.tested = tested;
    }

    public Instant getTestTime() {
        return testTime;
    }

    public Prog testTime(Instant testTime) {
        this.testTime = testTime;
        return this;
    }

    public void setTestTime(Instant testTime) {
        this.testTime = testTime;
    }

    public String getTestUser() {
        return testUser;
    }

    public Prog testUser(String testUser) {
        this.testUser = testUser;
        return this;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser;
    }

    public String getListPhoneLongWithname() {
        return listPhoneLongWithname;
    }

    public Prog listPhoneLongWithname(String listPhoneLongWithname) {
        this.listPhoneLongWithname = listPhoneLongWithname;
        return this;
    }

    public void setListPhoneLongWithname(String listPhoneLongWithname) {
        this.listPhoneLongWithname = listPhoneLongWithname;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prog)) {
            return false;
        }
        return progId != null && progId.equals(((Prog) o).progId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prog{" +
            ", progId=" + getProgId() +
            ", progCode='" + getProgCode() + "'" +
            ", cpId=" + getCpId() +
            ", alias='" + getAlias() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", deleted=" + getDeleted() +
            ", link='" + getLink() + "'" +
            ", attachFile='" + getAttachFile() + "'" +
            ", aliasDetail='" + getAliasDetail() + "'" +
            ", type=" + getType() +
            ", cpGroupId=" + getCpGroupId() +
            ", groupSex=" + getGroupSex() +
            ", sentSchedule='" + getSentSchedule() + "'" +
            ", content='" + getContent() + "'" +
            ", groupArray='" + getGroupArray() + "'" +
            ", progType=" + getProgType() +
            ", convertVn=" + getConvertVn() +
            ", createUser='" + getCreateUser() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", groupNameList='" + getGroupNameList() + "'" +
            ", forceSend=" + getForceSend() +
            ", getsubFilter=" + getGetsubFilter() +
            ", catId=" + getCatId() +
            ", maxSms=" + getMaxSms() +
            ", aliasId=" + getAliasId() +
            ", sendDay='" + getSendDay() + "'" +
            ", exceptionDay='" + getExceptionDay() + "'" +
            ", sentTimeZone=" + getSentTimeZone() +
            ", startTimeZone=" + getStartTimeZone() +
            ", finishTimeZone=" + getFinishTimeZone() +
            ", actualFinishDate='" + getActualFinishDate() + "'" +
            ", hidePrefix=" + getHidePrefix() +
            ", hideSuffix=" + getHideSuffix() +
            ", paramUsed=" + getParamUsed() +
            ", paramLength=" + getParamLength() +
            ", numTest=" + getNumTest() +
            ", suffix='" + getSuffix() + "'" +
            ", isAdminUpload=" + getIsAdminUpload() +
            ", priority=" + getPriority() +
            ", totalSub=" + getTotalSub() +
            ", totalCharge=" + getTotalCharge() +
            ", totalSendSuccess=" + getTotalSendSuccess() +
            ", totalSendFail=" + getTotalSendFail() +
            ", totalSmsToday=" + getTotalSmsToday() +
            ", moneyMo=" + getMoneyMo() +
            ", moneyMt=" + getMoneyMt() +
            ", outsideReply=" + getOutsideReply() +
            ", validReplyDate='" + getValidReplyDate() + "'" +
            ", validReplyNum=" + getValidReplyNum() +
            ", updateSmsDate='" + getUpdateSmsDate() + "'" +
            ", numTestSent=" + getNumTestSent() +
            ", refund=" + getRefund() +
            ", createdModule=" + getCreatedModule() +
            ", mtReportPath='" + getMtReportPath() + "'" +
            ", fileUpload='" + getFileUpload() + "'" +
            ", fileUploadStatus=" + getFileUploadStatus() +
            ", fileUploadType=" + getFileUploadType() +
            ", needApproveRun=" + getNeedApproveRun() +
            ", tested=" + getTested() +
            ", testTime='" + getTestTime() + "'" +
            ", testUser='" + getTestUser() + "'" +
            ", listPhoneLongWithname='" + getListPhoneLongWithname() + "'" +
            "}";
    }
}
