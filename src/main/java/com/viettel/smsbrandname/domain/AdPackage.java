package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AdPackage.
 */
@Entity
@Table(name = "ad_package")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_PACKAGE_SEQ")
    @SequenceGenerator(name = "AD_PACKAGE_SEQ", sequenceName = "AD_PACKAGE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public AdPackage packageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getType() {
        return type;
    }

    public AdPackage type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public AdPackage status(Integer status) {
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
        if (!(o instanceof AdPackage)) {
            return false;
        }
        return id != null && id.equals(((AdPackage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdPackage{" +
            "id=" + getId() +
            ", packageName='" + getPackageName() + "'" +
            ", type=" + getType() +
            ", status=" + getStatus() +
            "}";
    }
}
