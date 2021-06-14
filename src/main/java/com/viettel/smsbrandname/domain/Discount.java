package com.viettel.smsbrandname.domain;

import javax.persistence.*;

@Entity
@Table(name = "DISCOUNT")
public class Discount {
    @Id
    @Column(name = "DISCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_id_seq")
    @SequenceGenerator(sequenceName = "discount_id_seq", allocationSize = 1, name = "discount_id_seq")
    private Long id;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "DISCOUNT_VALUE")
    private Long discountValue;

    @Column(name = "ENABLED")
    private Integer enabled;

    @Column(name = "DESCRIPTION")
    private String descriptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Long discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
