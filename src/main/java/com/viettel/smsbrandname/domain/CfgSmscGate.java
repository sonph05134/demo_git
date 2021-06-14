package com.viettel.smsbrandname.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CFG_SMSC_GATE")
public class CfgSmscGate implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "cfgSmscGateIdSeq", sequenceName = "CFG_SMSC_GATE_ID_SEQ", allocationSize = 1)
    @Id
    @Column(name = "CFG_SMSC_GATE_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cfgSmscGateIdSeq")
    private Long cfgSmscGateId;
    @Column(name = "DECRIPTION")
    private String description;
    @Column(name = "STATUS")
    private Short status;
    @Column(name = "CFG_GATE_ID")
    private String cfgGateId;
    @Column(name = "CFG_SMSC_ID")
    private String cfgSmscId;

    public CfgSmscGate() {
    }

    public CfgSmscGate(Long cfgSmscGateId) {
        this.cfgSmscGateId = cfgSmscGateId;
    }

    public Long getCfgSmscGateId() {
        return cfgSmscGateId;
    }

    public void setCfgSmscGateId(Long cfgSmscGateId) {
        this.cfgSmscGateId = cfgSmscGateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCfgGateId() {
        return cfgGateId;
    }

    public void setCfgGateId(String cfgGateId) {
        this.cfgGateId = cfgGateId;
    }

    public String getCfgSmscId() {
        return cfgSmscId;
    }

    public void setCfgSmscId(String cfgSmscId) {
        this.cfgSmscId = cfgSmscId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cfgSmscGateId != null ? cfgSmscGateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgSmscGate)) {
            return false;
        }
        CfgSmscGate other = (CfgSmscGate) object;
        if ((this.cfgSmscGateId == null && other.cfgSmscGateId != null) || (this.cfgSmscGateId != null && !this.cfgSmscGateId.equals(other.cfgSmscGateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.viettel.subgw.database.BO.CfgSmscGate[cfgSmscGateId=" + cfgSmscGateId + "]";
    }

}

