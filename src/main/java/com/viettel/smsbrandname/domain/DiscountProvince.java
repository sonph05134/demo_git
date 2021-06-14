package com.viettel.smsbrandname.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOUNT_PROVINCE")
public class DiscountProvince {

    @Id
    @Column(name="ROWID")
    String rowid;

    @Column(name = "discount_id")
    private Long discountId;

    @Column(name = "province_id")
    private Long provinceId;

    public DiscountProvince() {
    }

    public DiscountProvince(Long discountId, Long provinceId) {
        this.discountId = discountId;
        this.provinceId = provinceId;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
}
