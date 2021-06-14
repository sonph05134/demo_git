package com.viettel.smsbrandname.domain;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.dto.MaintainFeeDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ALIAS_KEEP_FEE")
public class MaintainFee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alias_keep_fee_seq")
    @SequenceGenerator(sequenceName = "alias_keep_fee_seq", allocationSize = 1, name = "alias_keep_fee_seq")
    @Column(name = "FEE_ID")
    private Long id;

    @Column(name = "FEE_VALUE")
    private BigDecimal feeValue;

    @Column(name = "CURRENCY")
    private Integer currency;

    @Column(name = "NOTE")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFeeValue() {
        if (Constants.CURRENCY_USD_VAL == currency) {
            return feeValue.divide(new BigDecimal(Constants.USD_LENGTH));
        }
        return feeValue;
    }

    public void setFeeValue(BigDecimal feeValue) {
        this.feeValue = feeValue;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static MaintainFee convertFromDTO(MaintainFeeDTO maintainFeeDTO) {
        MaintainFee maintainFee = new MaintainFee();
        maintainFee.setId(maintainFeeDTO.getId());
        maintainFee.setCurrency(maintainFeeDTO.getCurrency());
        maintainFee.setNote(maintainFeeDTO.getNote());
        maintainFee.setFeeValue(new BigDecimal(maintainFeeDTO.getFeeValue()));
        return maintainFee;
    }
}
