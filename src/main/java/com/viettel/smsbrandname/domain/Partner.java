package com.viettel.smsbrandname.domain;

import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.dto.MaintainFeeDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PARTNER")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
    @SequenceGenerator(sequenceName = "partner_seq", allocationSize = 1, name = "partner_seq")
    @Column(name = "PARTNER_ID")
    private Long id;

    @Column(name = "PARTNER_CODE")
    private String code;

    @Column(name = "PARTNER_NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
