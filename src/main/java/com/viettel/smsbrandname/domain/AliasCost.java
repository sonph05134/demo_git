package com.viettel.smsbrandname.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ALIAS_COST")
public class AliasCost implements Serializable {
    @SequenceGenerator(name = "aliasCostIdSeq", sequenceName = "ALIAS_COST_ID_SEQ", allocationSize = 1)
    @Id
    @Column(name = "ALIAS_COST_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aliasCostIdSeq")
    private Long aliasCostId;
    @Column(name = "ALIAS_TELCO")
    private String aliasTelco;
    @Column(name = "REG_ALIAS_COST")
    private Long regAliasCost;
    @Column(name = "CANCEL_ALIAS_COST")
    private Long cancelAliasCost;
    @Column(name = "KEEP_ALIAS_COST")
    private Long keepAliasCost;
    @Column(name = "CREATE_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date modifiedDate;
    @Column(name = "REG_ALIAS_COST_USD")
    private Long regAliasCostUSD;
    @Column(name = "CANCEL_ALIAS_COST_USD")
    private Long cancelAliasCostUSD;
    @Column(name = "KEEP_ALIAS_COST_USD")
    private Long keepAliasCostUSD;
    @Column(name = "ALIAS_COST_TYPE")
    private Long aliasCostType;

    public Long getRegAliasCostUSD() {
        return regAliasCostUSD;
    }

    public void setRegAliasCostUSD(Long regAliasCostUSD) {
        this.regAliasCostUSD = regAliasCostUSD;
    }

    public Long getCancelAliasCostUSD() {
        return cancelAliasCostUSD;
    }

    public void setCancelAliasCostUSD(Long cancelAliasCostUSD) {
        this.cancelAliasCostUSD = cancelAliasCostUSD;
    }

    public Long getKeepAliasCostUSD() {
        return keepAliasCostUSD;
    }

    public void setKeepAliasCostUSD(Long keepAliasCostUSD) {
        this.keepAliasCostUSD = keepAliasCostUSD;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public AliasCost() {
    }

    public AliasCost(Long aliasCostId) {
        this.aliasCostId = aliasCostId;
    }

    public Long getAliasCostId() {
        return aliasCostId;
    }

    public void setAliasCostId(Long aliasCostId) {
        this.aliasCostId = aliasCostId;
    }

    public String getAliasTelco() {
        return aliasTelco;
    }

    public void setAliasTelco(String aliasTelco) {
        this.aliasTelco = aliasTelco;
    }

    public Long getRegAliasCost() {
        return regAliasCost;
    }

    public void setRegAliasCost(Long regAliasCost) {
        this.regAliasCost = regAliasCost;
    }

    public Long getCancelAliasCost() {
        return cancelAliasCost;
    }

    public void setCancelAliasCost(Long cancelAliasCost) {
        this.cancelAliasCost = cancelAliasCost;
    }

    public Long getKeepAliasCost() {
        return keepAliasCost;
    }

    public void setKeepAliasCost(Long keepAliasCost) {
        this.keepAliasCost = keepAliasCost;

    }

    public Long getAliasCostType() {
        return aliasCostType;
    }

    public void setAliasCostType(Long aliasCostType) {
        this.aliasCostType = aliasCostType;
    }

}
