package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import java.time.Instant;

public class CpGroupDetailDTO {
    private Long id;
    private Long cpId;
    private String cpCode;
    private String cpGroupName;
    private String note;
    private Instant createDate;
    private Instant updateDate;
    private Integer deleted;
    private String createUser;
    private Long freeSms;

    public CpGroupDetailDTO(Object[] objects) {
        int i = 0;
        this.id = DataUtil.safeToLong(objects[i++]);
        this.cpGroupName = DataUtil.safeToString(objects[i++]);
        this.cpCode = DataUtil.safeToString(objects[i++]);
        this.createDate = DataUtil.safeToInstant(objects[i++]);
        this.updateDate = DataUtil.safeToInstant(objects[i++]);
        this.createUser = DataUtil.safeToString(objects[i++]);
        this.note = DataUtil.safeToString(objects[i++]);
        this.cpId = DataUtil.safeToLong(objects[i++]);
        this.freeSms = DataUtil.safeToLong(objects[i++]);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getCpGroupName() {
        return cpGroupName;
    }

    public void setCpGroupName(String cpGroupName) {
        this.cpGroupName = cpGroupName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getFreeSms() {
        return freeSms;
    }

    public void setFreeSms(Long freeSms) {
        this.freeSms = freeSms;
    }
}
