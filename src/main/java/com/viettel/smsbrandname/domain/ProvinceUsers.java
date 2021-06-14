package com.viettel.smsbrandname.domain;

import javax.persistence.*;

@Entity
@Table(name = "province_users")
public class ProvinceUsers {

    @Id
    @SequenceGenerator(name = "provinceUsersSeq", sequenceName = "PROVINCE_USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provinceUsersSeq")
    private Long id;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_type")
    private Long userType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_parent")
    private Integer isParent;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }
}
