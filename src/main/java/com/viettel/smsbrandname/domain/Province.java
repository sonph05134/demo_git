/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author duonglt16
 */
@Entity
@Table(name = "PROVINCE")
public class Province {

    private Long provinceId;
    private String provinceName;
    private String provinceCode;
    private String userName;
    private String provinceCodeBCCS;
    private String userUpdated;
    private Date dateUpdated;

    public Province() {
    }

    @Column(name = "PROVINCE_CODE")
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    @SequenceGenerator(name = "PROVINCE_SEQ", sequenceName = "PROVINCE_SEQ", allocationSize = 1)
    @Id
    @Column(name = "PROVINCE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROVINCE_SEQ")
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "PROVINCE_NAME")
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name = "PROVINCE_CODE_BCCS")
    public String getProvinceCodeBCCS() {
        return provinceCodeBCCS;
    }

    public void setProvinceCodeBCCS(String provinceCodeBCCS) {
        this.provinceCodeBCCS = provinceCodeBCCS;
    }

    @Column(name = "USER_UPDATED")
    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    @Column(name = "DATE_UPDATED")
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
