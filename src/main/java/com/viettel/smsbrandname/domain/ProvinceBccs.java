package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProvinceBccs.
 */
@Entity
@Table(name = "province_bccs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProvinceBccs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "provinceBccsSeq", sequenceName = "PROVINCE_BCCS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provinceBccsSeq")
    private Long id;

    @Column(name = "province_bccs_id")
    private Long provinceBccsId;

    @Column(name = "province_bccs_name")
    private String provinceBccsName;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "bccs_staff_code")
    private String bccsStaffCode;

    @Column(name = "user_updated")
    private String userUpdated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceBccsId() {
        return provinceBccsId;
    }

    public ProvinceBccs provinceBccsId(Long provinceBccsId) {
        this.provinceBccsId = provinceBccsId;
        return this;
    }

    public void setProvinceBccsId(Long provinceBccsId) {
        this.provinceBccsId = provinceBccsId;
    }

    public String getProvinceBccsName() {
        return provinceBccsName;
    }

    public ProvinceBccs provinceBccsName(String provinceBccsName) {
        this.provinceBccsName = provinceBccsName;
        return this;
    }

    public void setProvinceBccsName(String provinceBccsName) {
        this.provinceBccsName = provinceBccsName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public ProvinceBccs provinceId(Long provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getBccsStaffCode() {
        return bccsStaffCode;
    }

    public ProvinceBccs bccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
        return this;
    }

    public void setBccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public ProvinceBccs userUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public ProvinceBccs dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvinceBccs)) {
            return false;
        }
        return id != null && id.equals(((ProvinceBccs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvinceBccs{" +
            "id=" + getId() +
            ", provinceBccsId=" + getProvinceBccsId() +
            ", provinceBccsName='" + getProvinceBccsName() + "'" +
            ", provinceId=" + getProvinceId() +
            ", bccsStaffCode='" + getBccsStaffCode() + "'" +
            ", userUpdated='" + getUserUpdated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
