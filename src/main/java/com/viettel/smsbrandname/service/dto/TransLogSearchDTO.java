package com.viettel.smsbrandname.service.dto;

public class TransLogSearchDTO {
    private String cpCode;
    private Long chanel;
    private String fromDate;
    private String toDate;
    private String currency;

    public String getCpCode() {
        return cpCode;
    }

    public Long getChanel() {
        return chanel;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public void setChanel(Long chanel) {
        this.chanel = chanel;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
