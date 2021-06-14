package com.viettel.smsbrandname.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DateUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.Mt} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MtDTO implements Serializable {

    private Long mtId;

    private String sender;

    private String receiver;

    private Instant mtTime;

    private Long msgType;

    private Long msgLen;

    private Long status;

    private String message;

    private Long cpId;

    private String cpCode;

    private Long numMt;

    private Long mtType;

    private Long sumStatus;

    private Long requestId;

    private String webservice;

    private Long aliasId;

    private Long aliasGroupType;

    private Long telcoId;

    private Long progId;

    private Long aliasType;

    private Long subType;

    private Long price;

    private Long inputRequestId;

    private Long regDlr;

    private String inputModule;

    private Long dlrStatus;

    private String cpMtId;

    private Long refund;

    private Long smscLog;

    private Long smscLogQuannd8;

    private Long quannd8Check;

    private Long promotionAliasId;

    private Long undoPromotion;

    private Long stt;

    private String statusView;

    private String telcoName;

    private String aliasTypeView;

    private String mtTimeString;

    public MtDTO() {
    }

    public MtDTO(Object[] objs) {
        Date dateTmp = DateUtil.stringToDate(DataUtil.safeToString(objs[0]), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if (dateTmp != null) {
            this.mtTime = dateTmp.toInstant();
        }
        this.receiver = DataUtil.safeToString(objs[1]);
        this.sender = DataUtil.safeToString(objs[2]);
        this.message = DataUtil.safeToString(objs[3]);
        this.numMt = DataUtil.safeToLong(objs[4]);
        this.statusView = DataUtil.safeToString(objs[5]);
        this.cpCode = DataUtil.safeToString(objs[6]);
        this.aliasTypeView = DataUtil.safeToString(objs[7]);
        this.webservice = DataUtil.safeToString(objs[8]);
        this.telcoName = DataUtil.safeToString(objs[9]);
    }

    public String getAliasTypeView() {
        return aliasTypeView;
    }

    public void setAliasTypeView(String aliasTypeView) {
        this.aliasTypeView = aliasTypeView;
    }

    public String getTelcoName() {
        return telcoName;
    }

    public void setTelcoName(String telcoName) {
        this.telcoName = telcoName;
    }

    public String getStatusView() {
        return statusView;
    }

    public void setStatusView(String statusView) {
        this.statusView = statusView;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Long getMtId() {
        return mtId;
    }

    public void setMtId(Long mtId) {
        this.mtId = mtId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Instant getMtTime() {
        return mtTime;
    }

    public void setMtTime(Instant mtTime) {
        this.mtTime = mtTime;
    }

    public Long getMsgType() {
        return msgType;
    }

    public void setMsgType(Long msgType) {
        this.msgType = msgType;
    }

    public Long getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(Long msgLen) {
        this.msgLen = msgLen;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getNumMt() {
        return numMt;
    }

    public void setNumMt(Long numMt) {
        this.numMt = numMt;
    }

    public Long getMtType() {
        return mtType;
    }

    public void setMtType(Long mtType) {
        this.mtType = mtType;
    }

    public Long getSumStatus() {
        return sumStatus;
    }

    public void setSumStatus(Long sumStatus) {
        this.sumStatus = sumStatus;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getWebservice() {
        return webservice;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public Long getAliasId() {
        return aliasId;
    }

    public void setAliasId(Long aliasId) {
        this.aliasId = aliasId;
    }

    public Long getAliasGroupType() {
        return aliasGroupType;
    }

    public void setAliasGroupType(Long aliasGroupType) {
        this.aliasGroupType = aliasGroupType;
    }

    public Long getTelcoId() {
        return telcoId;
    }

    public void setTelcoId(Long telcoId) {
        this.telcoId = telcoId;
    }

    public Long getProgId() {
        return progId;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public Long getAliasType() {
        return aliasType;
    }

    public void setAliasType(Long aliasType) {
        this.aliasType = aliasType;
    }

    public Long getSubType() {
        return subType;
    }

    public void setSubType(Long subType) {
        this.subType = subType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getInputRequestId() {
        return inputRequestId;
    }

    public void setInputRequestId(Long inputRequestId) {
        this.inputRequestId = inputRequestId;
    }

    public Long getRegDlr() {
        return regDlr;
    }

    public void setRegDlr(Long regDlr) {
        this.regDlr = regDlr;
    }

    public String getInputModule() {
        return inputModule;
    }

    public void setInputModule(String inputModule) {
        this.inputModule = inputModule;
    }

    public Long getDlrStatus() {
        return dlrStatus;
    }

    public void setDlrStatus(Long dlrStatus) {
        this.dlrStatus = dlrStatus;
    }

    public String getCpMtId() {
        return cpMtId;
    }

    public void setCpMtId(String cpMtId) {
        this.cpMtId = cpMtId;
    }

    public Long getRefund() {
        return refund;
    }

    public void setRefund(Long refund) {
        this.refund = refund;
    }

    public Long getSmscLog() {
        return smscLog;
    }

    public void setSmscLog(Long smscLog) {
        this.smscLog = smscLog;
    }

    public Long getSmscLogQuannd8() {
        return smscLogQuannd8;
    }

    public void setSmscLogQuannd8(Long smscLogQuannd8) {
        this.smscLogQuannd8 = smscLogQuannd8;
    }

    public Long getQuannd8Check() {
        return quannd8Check;
    }

    public void setQuannd8Check(Long quannd8Check) {
        this.quannd8Check = quannd8Check;
    }

    public Long getPromotionAliasId() {
        return promotionAliasId;
    }

    public void setPromotionAliasId(Long promotionAliasId) {
        this.promotionAliasId = promotionAliasId;
    }

    public Long getUndoPromotion() {
        return undoPromotion;
    }

    public void setUndoPromotion(Long undoPromotion) {
        this.undoPromotion = undoPromotion;
    }

    public String getMtTimeString() {
        return mtTimeString;
    }

    public void setMtTimeString(String mtTimeString) {
        this.mtTimeString = mtTimeString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MtDTO)) {
            return false;
        }

        return mtId != null && mtId.equals(((MtDTO) o).mtId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MtDTO{" +
            " mtId=" + getMtId() +
            ", sender='" + getSender() + "'" +
            ", receiver='" + getReceiver() + "'" +
            ", mtTime='" + getMtTime() + "'" +
            ", msgType=" + getMsgType() +
            ", msgLen=" + getMsgLen() +
            ", status=" + getStatus() +
            ", message='" + getMessage() + "'" +
            ", cpId=" + getCpId() +
            ", cpCode='" + getCpCode() + "'" +
            ", numMt=" + getNumMt() +
            ", mtType=" + getMtType() +
            ", sumStatus=" + getSumStatus() +
            ", requestId=" + getRequestId() +
            ", webservice='" + getWebservice() + "'" +
            ", aliasId=" + getAliasId() +
            ", aliasGroupType=" + getAliasGroupType() +
            ", telcoId=" + getTelcoId() +
            ", progId=" + getProgId() +
            ", aliasType=" + getAliasType() +
            ", subType=" + getSubType() +
            ", price=" + getPrice() +
            ", inputRequestId=" + getInputRequestId() +
            ", regDlr=" + getRegDlr() +
            ", inputModule='" + getInputModule() + "'" +
            ", dlrStatus=" + getDlrStatus() +
            ", cpMtId='" + getCpMtId() + "'" +
            ", refund=" + getRefund() +
            ", smscLog=" + getSmscLog() +
            ", smscLogQuannd8=" + getSmscLogQuannd8() +
            ", quannd8Check=" + getQuannd8Check() +
            ", promotionAliasId=" + getPromotionAliasId() +
            ", undoPromotion=" + getUndoPromotion() +
            "}";
    }
}
