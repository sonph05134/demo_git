package com.viettel.smsbrandname.service.dto;

import java.io.Serializable;

public class MtSearchDTO implements Serializable {
    private String receiver;
    private Long cpId;
    private String cpCode;
    private String sender;
    private Long aliasType;
    private String fromDate;
    private String toDate;
    private Long telcoId;

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

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public Long getAliasType() {
        return aliasType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public Long getTelcoId() {
        return telcoId;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setAliasType(Long aliasType) {
        this.aliasType = aliasType;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setTelcoId(Long telcoId) {
        this.telcoId = telcoId;
    }
}
