package com.viettel.smsbrandname.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.AdCategory} entity.
 */
public class AdCategoryDTO implements Serializable {
    
    private Long id;

    private String catCode;

    private String catName;

    private String catDesc;

    private Integer recycle;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdCategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((AdCategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdCategoryDTO{" +
            "id=" + getId() +
            ", catCode='" + getCatCode() + "'" +
            ", catName='" + getCatName() + "'" +
            ", catDesc='" + getCatDesc() + "'" +
            ", recycle=" + getRecycle() +
            "}";
    }
}
