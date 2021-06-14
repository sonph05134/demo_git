package com.viettel.smsbrandname.domain;

import com.viettel.smsbrandname.config.Constants;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CP_ALIAS_GROUP")
public class SMSCost {

    private BigDecimal price;
    private Long groupId;
    private BigDecimal priceUsd;
    private Long priceLevel;
    private String groupName;
    private Long aliasGroupType;
    private Long cpAliasGroupId;
    private Long type;




    @Column(name = "PRICE", precision = 20, scale = 0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "GROUP_ID", precision = 10, scale = 0)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "PRICE_USD", precision = 20, scale = 0)
    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public void setPriceUsdToDB() {
        BigDecimal usdLength = new BigDecimal(Constants.USD_LENGTH);
        this.priceUsd = this.priceUsd.multiply(usdLength);
    }

    @Column(name = "PRICE_LEVEL", precision = 2, scale = 0)
    public Long getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Long priceLevel) {
        this.priceLevel = priceLevel;
    }

    @Column(name = "GROUP_NAME", length = 100)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "ALIAS_GROUP_TYPE", precision = 1, scale = 0)
    public Long getAliasGroupType() {
        return aliasGroupType;
    }

    public void setAliasGroupType(Long aliasGroupType) {
        this.aliasGroupType = aliasGroupType;
    }

    @SequenceGenerator(name = "CP_ALIAS_GROUP_SEQ", sequenceName = "CP_ALIAS_GROUP_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CP_ALIAS_GROUP_SEQ")
    @Column(name = "CP_ALIAS_GROUP_ID", unique = true, nullable = false, precision = 10, scale = 0)
    public Long getCpAliasGroupId() {
        return cpAliasGroupId;
    }

    public void setCpAliasGroupId(Long cpAliasGroupId) {
        this.cpAliasGroupId = cpAliasGroupId;
    }

    @Column(name = "TYPE", precision = 2, scale = 0)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }


}
