package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Mt.
 */
@Entity
@Table(name = "mt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "mt_id")
    private Long mtId;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "mt_time")
    private Instant mtTime;

    @Column(name = "msg_type")
    private Long msgType;

    @Column(name = "msg_len")
    private Long msgLen;

    @Column(name = "status")
    private Long status;

    @Column(name = "message")
    private String message;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "cp_code")
    private String cpCode;

    @Column(name = "num_mt")
    private Long numMt;

    @Column(name = "mt_type")
    private Long mtType;

    @Column(name = "sum_status")
    private Long sumStatus;

    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "webservice")
    private String webservice;

    @Column(name = "alias_id")
    private Long aliasId;

    @Column(name = "alias_group_type")
    private Long aliasGroupType;

    @Column(name = "telco_id")
    private Long telcoId;

    @Column(name = "prog_id")
    private Long progId;

    @Column(name = "alias_type")
    private Long aliasType;

    @Column(name = "sub_type")
    private Long subType;

    @Column(name = "price")
    private Long price;

    @Column(name = "input_request_id")
    private Long inputRequestId;

    @Column(name = "reg_dlr")
    private Long regDlr;

    @Column(name = "input_module")
    private String inputModule;

    @Column(name = "dlr_status")
    private Long dlrStatus;

    @Column(name = "cp_mt_id")
    private String cpMtId;

    @Column(name = "refund")
    private Long refund;

    @Column(name = "smsc_log")
    private Long smscLog;

    @Column(name = "smsc_log_quannd_8")
    private Long smscLogQuannd8;

    @Column(name = "quannd_8_check")
    private Long quannd8Check;

    @Column(name = "promotion_alias_id")
    private Long promotionAliasId;

    @Column(name = "undo_promotion")
    private Long undoPromotion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getMtId() {
        return mtId;
    }

    public Mt mtId(Long mtId) {
        this.mtId = mtId;
        return this;
    }

    public void setMtId(Long mtId) {
        this.mtId = mtId;
    }

    public String getSender() {
        return sender;
    }

    public Mt sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Mt receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Instant getMtTime() {
        return mtTime;
    }

    public Mt mtTime(Instant mtTime) {
        this.mtTime = mtTime;
        return this;
    }

    public void setMtTime(Instant mtTime) {
        this.mtTime = mtTime;
    }

    public Long getMsgType() {
        return msgType;
    }

    public Mt msgType(Long msgType) {
        this.msgType = msgType;
        return this;
    }

    public void setMsgType(Long msgType) {
        this.msgType = msgType;
    }

    public Long getMsgLen() {
        return msgLen;
    }

    public Mt msgLen(Long msgLen) {
        this.msgLen = msgLen;
        return this;
    }

    public void setMsgLen(Long msgLen) {
        this.msgLen = msgLen;
    }

    public Long getStatus() {
        return status;
    }

    public Mt status(Long status) {
        this.status = status;
        return this;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Mt message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCpId() {
        return cpId;
    }

    public Mt cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public Mt cpCode(String cpCode) {
        this.cpCode = cpCode;
        return this;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getNumMt() {
        return numMt;
    }

    public Mt numMt(Long numMt) {
        this.numMt = numMt;
        return this;
    }

    public void setNumMt(Long numMt) {
        this.numMt = numMt;
    }

    public Long getMtType() {
        return mtType;
    }

    public Mt mtType(Long mtType) {
        this.mtType = mtType;
        return this;
    }

    public void setMtType(Long mtType) {
        this.mtType = mtType;
    }

    public Long getSumStatus() {
        return sumStatus;
    }

    public Mt sumStatus(Long sumStatus) {
        this.sumStatus = sumStatus;
        return this;
    }

    public void setSumStatus(Long sumStatus) {
        this.sumStatus = sumStatus;
    }

    public Long getRequestId() {
        return requestId;
    }

    public Mt requestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getWebservice() {
        return webservice;
    }

    public Mt webservice(String webservice) {
        this.webservice = webservice;
        return this;
    }

    public void setWebservice(String webservice) {
        this.webservice = webservice;
    }

    public Long getAliasId() {
        return aliasId;
    }

    public Mt aliasId(Long aliasId) {
        this.aliasId = aliasId;
        return this;
    }

    public void setAliasId(Long aliasId) {
        this.aliasId = aliasId;
    }

    public Long getAliasGroupType() {
        return aliasGroupType;
    }

    public Mt aliasGroupType(Long aliasGroupType) {
        this.aliasGroupType = aliasGroupType;
        return this;
    }

    public void setAliasGroupType(Long aliasGroupType) {
        this.aliasGroupType = aliasGroupType;
    }

    public Long getTelcoId() {
        return telcoId;
    }

    public Mt telcoId(Long telcoId) {
        this.telcoId = telcoId;
        return this;
    }

    public void setTelcoId(Long telcoId) {
        this.telcoId = telcoId;
    }

    public Long getProgId() {
        return progId;
    }

    public Mt progId(Long progId) {
        this.progId = progId;
        return this;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public Long getAliasType() {
        return aliasType;
    }

    public Mt aliasType(Long aliasType) {
        this.aliasType = aliasType;
        return this;
    }

    public void setAliasType(Long aliasType) {
        this.aliasType = aliasType;
    }

    public Long getSubType() {
        return subType;
    }

    public Mt subType(Long subType) {
        this.subType = subType;
        return this;
    }

    public void setSubType(Long subType) {
        this.subType = subType;
    }

    public Long getPrice() {
        return price;
    }

    public Mt price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getInputRequestId() {
        return inputRequestId;
    }

    public Mt inputRequestId(Long inputRequestId) {
        this.inputRequestId = inputRequestId;
        return this;
    }

    public void setInputRequestId(Long inputRequestId) {
        this.inputRequestId = inputRequestId;
    }

    public Long getRegDlr() {
        return regDlr;
    }

    public Mt regDlr(Long regDlr) {
        this.regDlr = regDlr;
        return this;
    }

    public void setRegDlr(Long regDlr) {
        this.regDlr = regDlr;
    }

    public String getInputModule() {
        return inputModule;
    }

    public Mt inputModule(String inputModule) {
        this.inputModule = inputModule;
        return this;
    }

    public void setInputModule(String inputModule) {
        this.inputModule = inputModule;
    }

    public Long getDlrStatus() {
        return dlrStatus;
    }

    public Mt dlrStatus(Long dlrStatus) {
        this.dlrStatus = dlrStatus;
        return this;
    }

    public void setDlrStatus(Long dlrStatus) {
        this.dlrStatus = dlrStatus;
    }

    public String getCpMtId() {
        return cpMtId;
    }

    public Mt cpMtId(String cpMtId) {
        this.cpMtId = cpMtId;
        return this;
    }

    public void setCpMtId(String cpMtId) {
        this.cpMtId = cpMtId;
    }

    public Long getRefund() {
        return refund;
    }

    public Mt refund(Long refund) {
        this.refund = refund;
        return this;
    }

    public void setRefund(Long refund) {
        this.refund = refund;
    }

    public Long getSmscLog() {
        return smscLog;
    }

    public Mt smscLog(Long smscLog) {
        this.smscLog = smscLog;
        return this;
    }

    public void setSmscLog(Long smscLog) {
        this.smscLog = smscLog;
    }

    public Long getSmscLogQuannd8() {
        return smscLogQuannd8;
    }

    public Mt smscLogQuannd8(Long smscLogQuannd8) {
        this.smscLogQuannd8 = smscLogQuannd8;
        return this;
    }

    public void setSmscLogQuannd8(Long smscLogQuannd8) {
        this.smscLogQuannd8 = smscLogQuannd8;
    }

    public Long getQuannd8Check() {
        return quannd8Check;
    }

    public Mt quannd8Check(Long quannd8Check) {
        this.quannd8Check = quannd8Check;
        return this;
    }

    public void setQuannd8Check(Long quannd8Check) {
        this.quannd8Check = quannd8Check;
    }

    public Long getPromotionAliasId() {
        return promotionAliasId;
    }

    public Mt promotionAliasId(Long promotionAliasId) {
        this.promotionAliasId = promotionAliasId;
        return this;
    }

    public void setPromotionAliasId(Long promotionAliasId) {
        this.promotionAliasId = promotionAliasId;
    }

    public Long getUndoPromotion() {
        return undoPromotion;
    }

    public Mt undoPromotion(Long undoPromotion) {
        this.undoPromotion = undoPromotion;
        return this;
    }

    public void setUndoPromotion(Long undoPromotion) {
        this.undoPromotion = undoPromotion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mt)) {
            return false;
        }
        return mtId != null && mtId.equals(((Mt) o).mtId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mt{" +
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
