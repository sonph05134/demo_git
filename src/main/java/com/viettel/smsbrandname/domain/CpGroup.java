package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CpGroup.
 */
@Entity
@Table(name = "cp_group")
public class CpGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CP_GROUP_SEQ")
    @SequenceGenerator(name = "CP_GROUP_SEQ", sequenceName = "CP_GROUP_SEQ", allocationSize = 1)
    @Column(name = "cp_group_id")
    private Long id;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "cp_code")
    private String cpCode;

    @Column(name = "cp_group_name")
    private String cpGroupName;

    @Column(name = "note")
    private String note;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "deleted")
    private Integer deleted;

    @Column(name = "create_user")
    private String createUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCpId() {
        return cpId;
    }

    public CpGroup cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public CpGroup cpCode(String cpCode) {
        this.cpCode = cpCode;
        return this;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getCpGroupName() {
        return cpGroupName;
    }

    public CpGroup cpGroupName(String cpGroupName) {
        this.cpGroupName = cpGroupName;
        return this;
    }

    public void setCpGroupName(String cpGroupName) {
        this.cpGroupName = cpGroupName;
    }

    public String getNote() {
        return note;
    }

    public CpGroup note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public CpGroup createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public CpGroup updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public CpGroup deleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public CpGroup createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpGroup)) {
            return false;
        }
        return id != null && id.equals(((CpGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpGroup{" +
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
