package com.viettel.smsbrandname.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.Telco} entity.
 */
public class TelcoDTO implements Serializable {

    private Long telcoId;

    private Long id;

    private String telcoName;

    private String telcoCode;

    private String telcoPrefix;

    private Long isViettelGroup;

    private String prefixDetail;

    private Long sendType;

    private String groupName;

    private Long canFreeSms;

    private Long isVietnamese;

    public Long getTelcoId() {
        return telcoId;
    }

    public void setTelcoId(Long telcoId) {
        this.telcoId = telcoId;
    }

    public String getTelcoName() {
        return telcoName;
    }

    public void setTelcoName(String telcoName) {
        this.telcoName = telcoName;
    }

    public String getTelcoCode() {
        return telcoCode;
    }

    public void setTelcoCode(String telcoCode) {
        this.telcoCode = telcoCode;
    }

    public String getTelcoPrefix() {
        return telcoPrefix;
    }

    public void setTelcoPrefix(String telcoPrefix) {
        this.telcoPrefix = telcoPrefix;
    }

    public Long getIsViettelGroup() {
        return isViettelGroup;
    }

    public void setIsViettelGroup(Long isViettelGroup) {
        this.isViettelGroup = isViettelGroup;
    }

    public String getPrefixDetail() {
        return prefixDetail;
    }

    public void setPrefixDetail(String prefixDetail) {
        this.prefixDetail = prefixDetail;
    }

    public Long getSendType() {
        return sendType;
    }

    public void setSendType(Long sendType) {
        this.sendType = sendType;
    }

    public String getGroupName() {
        return groupName;
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

    public Long getIsVietnamese() {
        return isVietnamese;
    }

    public void setIsVietnamese(Long isVietnamese) {
        this.isVietnamese = isVietnamese;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelcoDTO)) {
            return false;
        }

        return telcoId != null && telcoId.equals(((TelcoDTO) o).telcoId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelcoDTO{" +
            " telcoId=" + getTelcoId() +
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
