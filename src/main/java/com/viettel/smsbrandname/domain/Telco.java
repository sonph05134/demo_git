package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Telco.
 */
@Entity
@Table(name = "telco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Telco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELCO_ID_SEQ")
    @SequenceGenerator(sequenceName = "TELCO_ID_SEQ", allocationSize = 1, name = "TELCO_ID_SEQ")
    @Column(name = "TELCO_ID")
    private Long id;
    @Column(name = "TELCO_NAME")
    private String telcoName;

    @Column(name = "telco_code")
    private String telcoCode;

    @Column(name = "telco_prefix")
    private String telcoPrefix;

    @Column(name = "is_viettel_group")
    private Long isViettelGroup;

    @Column(name = "prefix_detail")
    private String prefixDetail;

    @Column(name = "send_type")
    private Long sendType;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "can_free_sms")
    private Long canFreeSms;

    @Column(name = "is_vietnamese")
    private Long isVietnamese;

    // jhipster-needle-entity-add-field - JHipster will add fields here


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTelcoName() {
        return telcoName;
    }


    public Telco telcoName(String telcoName) {
        this.telcoName = telcoName;
        return this;
    }

    public void setTelcoName(String telcoName) {
        this.telcoName = telcoName;
    }

    public String getTelcoCode() {
        return telcoCode;
    }


    public Telco telcoCode(String telcoCode) {
        this.telcoCode = telcoCode;
        return this;
    }

    public void setTelcoCode(String telcoCode) {
        this.telcoCode = telcoCode;
    }

    public String getTelcoPrefix() {
        return telcoPrefix;
    }


    public Telco telcoPrefix(String telcoPrefix) {
        this.telcoPrefix = telcoPrefix;
        return this;
    }

    public void setTelcoPrefix(String telcoPrefix) {
        this.telcoPrefix = telcoPrefix;
    }

    public Long getIsViettelGroup() {
        return isViettelGroup;
    }


    public Telco isViettelGroup(Long isViettelGroup) {
        this.isViettelGroup = isViettelGroup;
        return this;
    }

    public void setIsViettelGroup(Long isViettelGroup) {
        this.isViettelGroup = isViettelGroup;
    }


    public String getPrefixDetail() {
        return prefixDetail;
    }

    public Telco prefixDetail(String prefixDetail) {
        this.prefixDetail = prefixDetail;
        return this;
    }

    public void setPrefixDetail(String prefixDetail) {
        this.prefixDetail = prefixDetail;
    }

    public Long getSendType() {
        return sendType;
    }

    public Telco sendType(Long sendType) {
        this.sendType = sendType;
        return this;
    }

    public void setSendType(Long sendType) {
        this.sendType = sendType;
    }

    public String getGroupName() {
        return groupName;
    }

    public Telco groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getCanFreeSms() {
        return canFreeSms;
    }

    public void setCanFreeSms(Long canFreeSms) {
        this.canFreeSms = canFreeSms;
    }

    public Telco canFreeSms(Long canFreeSms) {
        this.canFreeSms = canFreeSms;
        return this;
    }


    public Long getIsVietnamese() {
        return isVietnamese;
    }

    public Telco isVietnamese(Long isVietnamese) {
        this.isVietnamese = isVietnamese;
        return this;
    }

    public void setIsVietnamese(Long isVietnamese) {
        this.isVietnamese = isVietnamese;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telco)) {
            return false;
        }
        return id != null && id.equals(((Telco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telco{" +
            " telcoId=" + getId() +
            ", telcoName='" + getTelcoName() + "'" +
            ", telcoCode='" + getTelcoCode() + "'" +
            ", telcoPrefix='" + getTelcoPrefix() + "'" +
            ", isViettelGroup=" + getIsViettelGroup() +
            ", prefixDetail='" + getPrefixDetail() + "'" +
            ", sendType=" + getSendType() +
            ", groupName='" + getGroupName() + "'" +
            ", canFreeSms=" + getCanFreeSms() +
            ", isVietnamese=" + getIsVietnamese() +
            "}";
    }
}
