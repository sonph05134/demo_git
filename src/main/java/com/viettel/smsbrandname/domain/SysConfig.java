package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "SYS_CONFIG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysConfig implements Serializable {

    @Id
    @Column(name = "SYS_CONFIG_ID")
    @SequenceGenerator(name = "provinceBccsSeq", sequenceName = "PROVINCE_BCCS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provinceBccsSeq")
    private Long sysConfigId;

    @Column(name = "CONFIG_GROUP")
    private String configGroup;

    @Column(name = "CONFIG_CODE")
    private String configCode;

    @Column(name = "CONFIG_NAME")
    private String configName;

    @Column(name = "DELETED")
    private Long deleted;

    @Column(name = "MODULE")
    private Long module;

    @Column(name = "USER_UPDATED")
    private String userUpdated;

    @Column(name = "DATE_UPDATED")
    private Instant dateUpdated;

    @Column(name = "NOTE")
    @Lob
    private String note;

    @Column(name = "CONFIG_VALUE")
    @Lob
    private String configValue;

    public Long getSysConfigId() {
        return sysConfigId;
    }

    public void setSysConfigId(Long sysConfigId) {
        this.sysConfigId = sysConfigId;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(String configGroup) {
        this.configGroup = configGroup;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
