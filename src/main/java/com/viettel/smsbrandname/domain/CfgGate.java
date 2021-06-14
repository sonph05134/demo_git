package com.viettel.smsbrandname.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CFG_GATE")
public class CfgGate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CFG_GATE_ID")
    private String cfgGateId;
    @Column(name = "IPADDRESS")
    private String ipAddress;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private Integer status;

    public CfgGate() {
    }

    public CfgGate(String cfgGateId) {
        this.cfgGateId = cfgGateId;
    }

    public String getCfgGateId() {
        return cfgGateId;
    }

    public void setCfgGateId(String cfgGateId) {
        this.cfgGateId = cfgGateId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipaddress) {
        this.ipAddress = ipaddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
