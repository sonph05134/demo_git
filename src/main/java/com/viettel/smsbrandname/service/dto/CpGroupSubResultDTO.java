package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class CpGroupSubResultDTO {
    private Long id;
    private Instant birthday;
    private String birthdayStr;
    private String name;
    private Long cpId;
    private Instant updateDate;
    private String address;
    private String note;
    private Long cpGroupId;
    private String cpCode;
    private Long sex;
    private String sexStr;
    private Long freeSms;
    private Instant createDate;
    private String msisdn;
    private String code;
    private String cpGroupName;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public CpGroupSubResultDTO() {
    }

    public String getCpGroupName() {
        return cpGroupName;
    }

    public void setCpGroupName(String cpGroupName) {
        this.cpGroupName = cpGroupName;
    }

    public CpGroupSubResultDTO(Object[] objects) {
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.cpId = DataUtil.safeToLong(objects[i++]);
        this.cpCode = DataUtil.safeToString(objects[i++]);
        this.msisdn = DataUtil.safeToString(objects[i++]);
        this.note = DataUtil.safeToString(objects[i++]);
        this.createDate = DataUtil.safeToInstant(objects[i++]);
        this.updateDate = DataUtil.safeToInstant(objects[i++]);
        this.name = DataUtil.safeToString(objects[i++]);
        this.address = DataUtil.safeToString(objects[i++]);
        this.sex = DataUtil.safeToLong(objects[i++]);
        if (this.sex == null) {
            this.sex = -1L;
        }
        this.birthday = DataUtil.safeToInstant(objects[i++]);
        this.code = DataUtil.safeToString(objects[i++]);
        this.cpGroupName = DataUtil.safeToString(objects[i++]);
        this.cpGroupId = DataUtil.safeToLong(objects[i++]);
        this.freeSms = DataUtil.safeToLong(objects[i++]);
    }

    public void formatValue() {
        if (Constants.SEX.MALE.equals(this.sex)) {
            this.sexStr = Translator.toLocale("cpGroupSub.sex.male");
        } else if (Constants.SEX.FEMALE.equals(this.sex)) {
            this.sexStr = Translator.toLocale("cpGroupSub.sex.female");
        } else {
            this.sexStr = "";
        }
        if (birthday != null) {
            this.birthdayStr = sdf.format(Date.from(birthday));
        }
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public Long getFreeSms() {
        return freeSms;
    }

    public void setFreeSms(Long freeSms) {
        this.freeSms = freeSms;
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
}

