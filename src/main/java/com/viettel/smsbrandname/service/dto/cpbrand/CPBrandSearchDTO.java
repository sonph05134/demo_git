package com.viettel.smsbrandname.service.dto.cpbrand;

public class CPBrandSearchDTO {

    private Long cpId;

    private Integer aliasType;

    private String telco;

    private String brandName;

    private String start;

    private String limit;

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Integer getAliasType() {
        return aliasType;
    }

    public void setAliasType(Integer aliasType) {
        this.aliasType = aliasType;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
