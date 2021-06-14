package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CpGroupSub.
 */
@Entity
@Table(name = "cp_group_sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CpGroupSub implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CP_GROUP_SUB_SEQ")
    @SequenceGenerator(name = "CP_GROUP_SUB_SEQ", sequenceName = "CP_GROUP_SUB_SEQ", allocationSize = 1)
    @Column(name = "cp_group_sub_id")
    private Long id;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "name")
    private String name;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "cp_group_id")
    private Long cpGroupId;

    @Column(name = "cp_code")
    private String cpCode;

    @Column(name = "sex")
    private Long sex;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "code")
    private String code;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public CpGroupSub birthday(Instant birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public CpGroupSub name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCpId() {
        return cpId;
    }

    public CpGroupSub cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public CpGroupSub updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getAddress() {
        return address;
    }

    public CpGroupSub address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public CpGroupSub note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCpGroupId() {
        return cpGroupId;
    }

    public CpGroupSub cpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
        return this;
    }

    public void setCpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public CpGroupSub cpCode(String cpCode) {
        this.cpCode = cpCode;
        return this;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getSex() {
        return sex;
    }

    public CpGroupSub sex(Long sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public CpGroupSub createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public CpGroupSub msisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCode() {
        return code;
    }

    public CpGroupSub code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CpGroupSub)) {
            return false;
        }
        return id != null && id.equals(((CpGroupSub) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CpGroupSub{" +
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
