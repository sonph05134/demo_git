package com.viettel.smsbrandname.service.dto;

import java.math.BigDecimal;

public class SMSCostDTO extends PageDTO {

    private String groupName;
    private Long aliasGroupType;
    private Long type;

    private BigDecimal price;
    private Long groupId;
    private BigDecimal priceUsd;
    private Long priceLevel;
    private Long cpAliasGroupId;
    private String nameType;
    private String nameAliasGroupType;

    private String formatPrice;
    private String formatPriceUsd;

    public String getFormatPriceUsd() {
        return formatPriceUsd;
    }

    public void setFormatPriceUsd(String formatPriceUsd) {
        this.formatPriceUsd = formatPriceUsd;
    }

    public String getFormatPrice() {
        return formatPrice;
    }

    public void setFormatPrice(String formatPrice) {
        this.formatPrice = formatPrice;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getNameAliasGroupType() {
        return nameAliasGroupType;
    }

    public void setNameAliasGroupType(String nameAliasGroupType) {
        this.nameAliasGroupType = nameAliasGroupType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Long getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Long priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Long getCpAliasGroupId() {
        return cpAliasGroupId;
    }

    public void setCpAliasGroupId(Long cpAliasGroupId) {
        this.cpAliasGroupId = cpAliasGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getAliasGroupType() {
        return aliasGroupType;
    }

    public void setAliasGroupType(Long aliasGroupType) {
        this.aliasGroupType = aliasGroupType;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
