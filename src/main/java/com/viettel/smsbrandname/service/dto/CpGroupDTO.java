package com.viettel.smsbrandname.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.CpGroup} entity.
 */
public class CpGroupDTO implements Serializable {
    
    private Long id;

    private Long cpId;

    private String cpCode;

    private String cpGroupName;

    private String note;

    private Instant createDate;

    private Instant updateDate;

    private Integer deleted;

    private String createUser;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((CpGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpGroupDTO{" +
            "id=" + getId() +
            ", cpId=" + getCpId() +
            ", cpCode='" + getCpCode() + "'" +
            ", cpGroupName='" + getCpGroupName() + "'" +
            ", note='" + getNote() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", deleted=" + getDeleted() +
            ", createUser='" + getCreateUser() + "'" +
            "}";
    }
}
