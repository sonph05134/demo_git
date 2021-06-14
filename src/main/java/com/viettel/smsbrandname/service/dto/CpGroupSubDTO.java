package com.viettel.smsbrandname.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.CpGroupSub} entity.
 */
public class CpGroupSubDTO implements Serializable {

    private Long id;

    private Instant birthday;

    private String name;

    private Long cpId;

    private Instant updateDate;

    private String address;

    private String note;

    private Long cpGroupId;

    private String cpCode;

    private Long sex;

    private String sexStr;

    private Instant createDate;

    private String msisdn;

    private String code;

    private String birthdayStr;

    private String error = "";

    private Long monthFreeSms;

    private String telcoCode;

    private Integer count;

    public String getTelcoCode() {
        return telcoCode;
    }

    public void setTelcoCode(String telcoCode) {
        this.telcoCode = telcoCode;
    }

    public Long getMonthFreeSms() {
        return monthFreeSms;
    }

    public void setMonthFreeSms(Long monthFreeSms) {
        this.monthFreeSms = monthFreeSms;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCpGroupId() {
        return cpGroupId;
    }

    public void setCpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpGroupSubDTO)) {
            return false;
        }

        return id != null && id.equals(((CpGroupSubDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpGroupSubDTO{" +
            "id=" + getId() +
            ", birthday='" + getBirthday() + "'" +
            ", name='" + getName() + "'" +
            ", cpId=" + getCpId() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", address='" + getAddress() + "'" +
            ", note='" + getNote() + "'" +
            ", cpGroupId=" + getCpGroupId() +
            ", cpCode='" + getCpCode() + "'" +
            ", sex=" + getSex() +
            ", createDate='" + getCreateDate() + "'" +
            ", msisdn='" + getMsisdn() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
