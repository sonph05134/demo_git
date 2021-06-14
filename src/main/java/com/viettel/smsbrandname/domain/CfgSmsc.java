package com.viettel.smsbrandname.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CFG_SMSC")
public class CfgSmsc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CFG_SMSC_ID")
    private String cfgSmscId;
    @Column(name = "IP")
    private String ip;
    @Column(name = "PORT")
    private Long port;
    @Column(name = "SYSTEM_ID")
    private String systemId;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ADDR_RANGE")
    private String addrRange;
    @Column(name = "MAX_SMS_LENGTH")
    private Long maxSmsLength;
    @Column(name = "NUM_SESSION")
    private Long numSession;
    @Column(name = "BIND_OPTIONS")
    private String bindOptions;
    @Column(name = "MSG_PER_SECOND")
    private Short msgPerSecond;
    @Column(name = "ENABLE")
    private Short enable;
    //tuyenhv4 them 2 truong params, express
//    @Column(name = "PARAMS")
//    private Short params;
//    @Column(name = "EXPRESS")
//    private Short express;
//    public Short getParams() {
//        return params;
//    }
//    public void setParams(Short params) {
//        this.params = params;
//    }
//    public Short getExpress() {
//        return express;
//    }
//    public void setExpress(Short express) {
//        this.express = express;
//    }

    public CfgSmsc() {
    }

    public CfgSmsc(String cfgSmscId) {
        this.cfgSmscId = cfgSmscId;
    }

    public String getCfgSmscId() {
        return cfgSmscId;
    }

    public void setCfgSmscId(String cfgSmscId) {
        this.cfgSmscId = cfgSmscId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddrRange() {
        return addrRange;
    }

    public void setAddrRange(String addrRange) {
        this.addrRange = addrRange;
    }

    public Long getMaxSmsLength() {
        return maxSmsLength;
    }

    public void setMaxSmsLength(Long maxSmsLength) {
        this.maxSmsLength = maxSmsLength;
    }

    public Long getNumSession() {
        return numSession;
    }

    public void setNumSession(Long numSession) {
        this.numSession = numSession;
    }

    public String getBindOptions() {
        return bindOptions;
    }

    public void setBindOptions(String bindOptions) {
        this.bindOptions = bindOptions;
    }

    public Short getMsgPerSecond() {
        return msgPerSecond;
    }

    public void setMsgPerSecond(Short msgPerSecond) {
        this.msgPerSecond = msgPerSecond;
    }

    public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cfgSmscId != null ? cfgSmscId.hashCode() : 0);
        return hash;
    }

}

