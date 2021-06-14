package com.viettel.smsbrandname.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "price_level")
public class PriceLevel implements Serializable {

    @SequenceGenerator(name = "priceLevelSeq", sequenceName = "PRICE_LEVEL_SEQ", allocationSize = 1)
    @Id
    @Column(name = "PRICE_LEVEL_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "priceLevelSeq")
    private Long priceLevelId;

    @Column(name = "PRICE_LEVEL_NAME")
    private String priceLevelName;

    @Column(name = "NUM_SMS")
    private Long numSms;

    @Column(name = "TYPE")
    private Integer type;

    public Long getPriceLevelId() {
        return priceLevelId;
    }

    public void setPriceLevelId(Long priceLevelId) {
        this.priceLevelId = priceLevelId;
    }

    public String getPriceLevelName() {
        return priceLevelName;
    }

    public void setPriceLevelName(String priceLevelName) {
        this.priceLevelName = priceLevelName;
    }

    public Long getNumSms() {
        return numSms;
    }

    public void setNumSms(Long numSms) {
        this.numSms = numSms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
