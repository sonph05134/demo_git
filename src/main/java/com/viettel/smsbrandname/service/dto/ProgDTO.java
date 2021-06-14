package com.viettel.smsbrandname.service.dto;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class ProgDTO extends PageDTO implements Serializable {

    private Long progId;

    private String progCode;

    private Long cpId;

    private String alias;

    private String status;

    private Instant createDate;

    private Instant finishDate;

    private Instant startDate;

    private Long deleted;

    private String link;

    @Lob
    private byte[] attachFile;

    private String attachFileContentType;
    private String aliasDetail;

    private Long type;

    private Long cpGroupId;

    private Long groupSex;

    private Instant sentSchedule;

    private String content;

    private String groupArray;

    private Long progType;

    private Long convertVn;

    private String createUser;

    private Instant updateDate;

    private String groupNameList;

    private Long forceSend;

    private Long getsubFilter;

    private Long catId;

    private Long maxSms;

    private Long aliasId;

    private String sendDay;

    private String exceptionDay;

    private Long sentTimeZone;

    private Long startTimeZone;

    private Long finishTimeZone;

    private Instant actualFinishDate;

    private Long hidePrefix;

    private Long hideSuffix;

    private Long paramUsed;

    private Long paramLength;

    private Long numTest;

    private String suffix;

    private Long isAdminUpload;

    private Long priority;

    private Long totalSub;

    private Long totalCharge;

    private Long totalSendSuccess;

    private Long totalSendFail;

    private Long totalSmsToday;

    private Long moneyMo;

    private Long moneyMt;

    private Long outsideReply;

    private Instant validReplyDate;

    private Long validReplyNum;

    private Instant updateSmsDate;

    private Long numTestSent;

    private Long refund;

    private Long createdModule;

    private String mtReportPath;

    private String fileUpload;

    private Long fileUploadStatus;

    private Long fileUploadType;

    private Long needApproveRun;

    private Long tested;

    private Instant testTime;

    private String testUser;

    private String listPhonenumber;

    private String listPhonenumberWithName;





    public Long getProgId() {
        return progId;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(byte[] attachFile) {
        this.attachFile = attachFile;
    }

    public String getAttachFileContentType() {
        return attachFileContentType;
    }

    public void setAttachFileContentType(String attachFileContentType) {
        this.attachFileContentType = attachFileContentType;
    }

    public String getAliasDetail() {
        return aliasDetail;
    }

    public void setAliasDetail(String aliasDetail) {
        this.aliasDetail = aliasDetail;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getCpGroupId() {
        return cpGroupId;
    }

    public void setCpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
    }

    public Long getGroupSex() {
        return groupSex;
    }

    public void setGroupSex(Long groupSex) {
        this.groupSex = groupSex;
    }

    public Instant getSentSchedule() {
        return sentSchedule;
    }

    public void setSentSchedule(Instant sentSchedule) {
        this.sentSchedule = sentSchedule;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupArray() {
        return groupArray;
    }

    public void setGroupArray(String groupArray) {
        this.groupArray = groupArray;
    }

    public Long getProgType() {
        return progType;
    }

    public void setProgType(Long progType) {
        this.progType = progType;
    }

    public Long getConvertVn() {
        return convertVn;
    }

    public void setConvertVn(Long convertVn) {
        this.convertVn = convertVn;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getGroupNameList() {
        return groupNameList;
    }

    public void setGroupNameList(String groupNameList) {
        this.groupNameList = groupNameList;
    }

    public Long getForceSend() {
        return forceSend;
    }

    public void setForceSend(Long forceSend) {
        this.forceSend = forceSend;
    }

    public Long getGetsubFilter() {
        return getsubFilter;
    }

    public void setGetsubFilter(Long getsubFilter) {
        this.getsubFilter = getsubFilter;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getMaxSms() {
        return maxSms;
    }

    public void setMaxSms(Long maxSms) {
        this.maxSms = maxSms;
    }

    public Long getAliasId() {
        return aliasId;
    }

    public void setAliasId(Long aliasId) {
        this.aliasId = aliasId;
    }

    public String getSendDay() {
        return sendDay;
    }

    public void setSendDay(String sendDay) {
        this.sendDay = sendDay;
    }

    public String getExceptionDay() {
        return exceptionDay;
    }

    public void setExceptionDay(String exceptionDay) {
        this.exceptionDay = exceptionDay;
    }

    public Long getSentTimeZone() {
        return sentTimeZone;
    }

    public void setSentTimeZone(Long sentTimeZone) {
        this.sentTimeZone = sentTimeZone;
    }

    public Long getStartTimeZone() {
        return startTimeZone;
    }

    public void setStartTimeZone(Long startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public Long getFinishTimeZone() {
        return finishTimeZone;
    }

    public void setFinishTimeZone(Long finishTimeZone) {
        this.finishTimeZone = finishTimeZone;
    }

    public Instant getActualFinishDate() {
        return actualFinishDate;
    }

    public void setActualFinishDate(Instant actualFinishDate) {
        this.actualFinishDate = actualFinishDate;
    }

    public Long getHidePrefix() {
        return hidePrefix;
    }

    public void setHidePrefix(Long hidePrefix) {
        this.hidePrefix = hidePrefix;
    }

    public Long getHideSuffix() {
        return hideSuffix;
    }

    public void setHideSuffix(Long hideSuffix) {
        this.hideSuffix = hideSuffix;
    }

    public Long getParamUsed() {
        return paramUsed;
    }

    public void setParamUsed(Long paramUsed) {
        this.paramUsed = paramUsed;
    }

    public Long getParamLength() {
        return paramLength;
    }

    public void setParamLength(Long paramLength) {
        this.paramLength = paramLength;
    }

    public Long getNumTest() {
        return numTest;
    }

    public void setNumTest(Long numTest) {
        this.numTest = numTest;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getIsAdminUpload() {
        return isAdminUpload;
    }

    public void setIsAdminUpload(Long isAdminUpload) {
        this.isAdminUpload = isAdminUpload;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getTotalSub() {
        return totalSub;
    }

    public void setTotalSub(Long totalSub) {
        this.totalSub = totalSub;
    }

    public Long getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Long totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Long getTotalSendSuccess() {
        return totalSendSuccess;
    }

    public void setTotalSendSuccess(Long totalSendSuccess) {
        this.totalSendSuccess = totalSendSuccess;
    }

    public Long getTotalSendFail() {
        return totalSendFail;
    }

    public void setTotalSendFail(Long totalSendFail) {
        this.totalSendFail = totalSendFail;
    }

    public Long getTotalSmsToday() {
        return totalSmsToday;
    }

    public void setTotalSmsToday(Long totalSmsToday) {
        this.totalSmsToday = totalSmsToday;
    }

    public Long getMoneyMo() {
        return moneyMo;
    }

    public void setMoneyMo(Long moneyMo) {
        this.moneyMo = moneyMo;
    }

    public Long getMoneyMt() {
        return moneyMt;
    }

    public void setMoneyMt(Long moneyMt) {
        this.moneyMt = moneyMt;
    }

    public Long getOutsideReply() {
        return outsideReply;
    }

    public void setOutsideReply(Long outsideReply) {
        this.outsideReply = outsideReply;
    }

    public Instant getValidReplyDate() {
        return validReplyDate;
    }

    public void setValidReplyDate(Instant validReplyDate) {
        this.validReplyDate = validReplyDate;
    }

    public Long getValidReplyNum() {
        return validReplyNum;
    }

    public void setValidReplyNum(Long validReplyNum) {
        this.validReplyNum = validReplyNum;
    }

    public Instant getUpdateSmsDate() {
        return updateSmsDate;
    }

    public void setUpdateSmsDate(Instant updateSmsDate) {
        this.updateSmsDate = updateSmsDate;
    }

    public Long getNumTestSent() {
        return numTestSent;
    }

    public void setNumTestSent(Long numTestSent) {
        this.numTestSent = numTestSent;
    }

    public Long getRefund() {
        return refund;
    }

    public void setRefund(Long refund) {
        this.refund = refund;
    }

    public Long getCreatedModule() {
        return createdModule;
    }

    public void setCreatedModule(Long createdModule) {
        this.createdModule = createdModule;
    }

    public String getMtReportPath() {
        return mtReportPath;
    }

    public void setMtReportPath(String mtReportPath) {
        this.mtReportPath = mtReportPath;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }

    public Long getFileUploadStatus() {
        return fileUploadStatus;
    }

    public void setFileUploadStatus(Long fileUploadStatus) {
        this.fileUploadStatus = fileUploadStatus;
    }

    public Long getFileUploadType() {
        return fileUploadType;
    }

    public void setFileUploadType(Long fileUploadType) {
        this.fileUploadType = fileUploadType;
    }

    public Long getNeedApproveRun() {
        return needApproveRun;
    }

    public void setNeedApproveRun(Long needApproveRun) {
        this.needApproveRun = needApproveRun;
    }

    public Long getTested() {
        return tested;
    }

    public void setTested(Long tested) {
        this.tested = tested;
    }

    public Instant getTestTime() {
        return testTime;
    }

    public void setTestTime(Instant testTime) {
        this.testTime = testTime;
    }

    public String getTestUser() {
        return testUser;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser;
    }

    public String getListPhonenumber() {
        return listPhonenumber;
    }

    public void setListPhonenumber(String listPhonenumber) {
        this.listPhonenumber = listPhonenumber;
    }

    public String getListPhonenumberWithName() {
        return listPhonenumberWithName;
    }

    public void setListPhonenumberWithName(String listPhonenumberWithName) {
        this.listPhonenumberWithName = listPhonenumberWithName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgDTO)) {
            return false;
        }

        return progId != null && progId.equals(((ProgDTO) o).progId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgDTO{" +
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
            ", listPhoneNumber='" + getListPhonenumber() + "'" +
            ", listPhoneNumberWithname='" + getListPhonenumberWithName() + "'" +
            "}";
    }

}
