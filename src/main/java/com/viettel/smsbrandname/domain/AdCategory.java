package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AdCategory.
 */
@Entity
@Table(name = "ad_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_category_seq")
    @SequenceGenerator(name = "ad_category_seq", sequenceName = "ad_category_seq", allocationSize = 1)
    private Long id;

    @Column(name = "cat_code")
    private String catCode;

    @Column(name = "cat_name")
    private String catName;

    @Column(name = "cat_desc")
    private String catDesc;

    @Column(name = "recycle")
    private Integer recycle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatCode() {
        return catCode;
    }

    public AdCategory catCode(String catCode) {
        this.catCode = catCode;
        return this;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getCatName() {
        return catName;
    }

    public AdCategory catName(String catName) {
        this.catName = catName;
        return this;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public AdCategory catDesc(String catDesc) {
        this.catDesc = catDesc;
        return this;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public AdCategory recycle(Integer recycle) {
        this.recycle = recycle;
        return this;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdCategory)) {
            return false;
        }
        return id != null && id.equals(((AdCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdCategory{" +
            "id=" + getId() +
            ", catCode='" + getCatCode() + "'" +
            ", catName='" + getCatName() + "'" +
            ", catDesc='" + getCatDesc() + "'" +
            ", recycle=" + getRecycle() +
            "}";
    }
}
