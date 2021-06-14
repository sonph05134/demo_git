package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AdminMsisdn.
 */
@Entity
@Table(name = "admin_msisdn")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminMsisdn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMIN_MSISDN_SEQ")
    @SequenceGenerator(name = "ADMIN_MSISDN_SEQ", sequenceName = "ADMIN_MSISDN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "insert_time")
    private Instant insertTime;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public AdminMsisdn userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public AdminMsisdn msisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Instant getInsertTime() {
        return insertTime;
    }

    public AdminMsisdn insertTime(Instant insertTime) {
        this.insertTime = insertTime;
        return this;
    }

    public void setInsertTime(Instant insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getStatus() {
        return status;
    }

    public AdminMsisdn status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminMsisdn)) {
            return false;
        }
        return id != null && id.equals(((AdminMsisdn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminMsisdn{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", msisdn='" + getMsisdn() + "'" +
            ", insertTime='" + getInsertTime() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
