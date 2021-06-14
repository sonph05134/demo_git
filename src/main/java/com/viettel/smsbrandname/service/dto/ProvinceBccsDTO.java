package com.viettel.smsbrandname.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.ProvinceBccs} entity.
 */
public class ProvinceBccsDTO implements Serializable {
    
    private Long id;

    private Long provinceBccsId;

    private String provinceBccsName;

    private Long provinceId;

    private String bccsStaffCode;

    private String userUpdated;

    private Instant dateUpdated;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceBccsId() {
        return provinceBccsId;
    }

    public void setProvinceBccsId(Long provinceBccsId) {
        this.provinceBccsId = provinceBccsId;
    }

    public String getProvinceBccsName() {
        return provinceBccsName;
    }

    public void setProvinceBccsName(String provinceBccsName) {
        this.provinceBccsName = provinceBccsName;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getBccsStaffCode() {
        return bccsStaffCode;
    }

    public void setBccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvinceBccsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProvinceBccsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvinceBccsDTO{" +
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
