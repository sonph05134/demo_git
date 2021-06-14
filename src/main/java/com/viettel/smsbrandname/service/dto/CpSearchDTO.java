package com.viettel.smsbrandname.service.dto;

public class CpSearchDTO {
    private String cpCode;
    private String cpName;
    private String userName;
    private String chargeType;
    private String currency;
    private Long status;
    private Long provinceId;
    private String cpAlias;
    private Long sendWithOther;
    private Integer page;
    private Integer pageSize;
    private Long provinceUserId;

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCpAlias() {
        return cpAlias;
    }

    public void setCpAlias(String cpAlias) {
        this.cpAlias = cpAlias;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getSendWithOther() {
        return sendWithOther;
    }

    public void setSendWithOther(Long sendWithOther) {
        this.sendWithOther = sendWithOther;
    }

    public Long getProvinceUserId() {
        return provinceUserId;
    }

    public void setProvinceUserId(Long provinceUserId) {
        this.provinceUserId = provinceUserId;
    }
}
