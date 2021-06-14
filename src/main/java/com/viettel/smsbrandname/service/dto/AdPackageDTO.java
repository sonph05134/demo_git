package com.viettel.smsbrandname.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.AdPackage} entity.
 */
public class AdPackageDTO implements Serializable {
    
    private Long id;

    private String packageName;

    private Integer type;

    private Integer status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdPackageDTO)) {
            return false;
        }

        return id != null && id.equals(((AdPackageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdPackageDTO{" +
            "id=" + getId() +
            ", packageName='" + getPackageName() + "'" +
            ", type=" + getType() +
            ", status=" + getStatus() +
            "}";
    }
}
