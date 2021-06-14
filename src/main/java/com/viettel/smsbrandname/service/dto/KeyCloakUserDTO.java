package com.viettel.smsbrandname.service.dto;

public class KeyCloakUserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String provinceCode;
    private String cpCode;
    private Boolean isEnable;
    private Long provinceId;
    private Long cpId;

    public KeyCloakUserDTO() {
    }

    public KeyCloakUserDTO(String username, String firstName, String lastName, String provinceCode, String cpCode, Boolean isEnable) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.provinceCode = provinceCode;
        this.cpCode = cpCode;
        this.isEnable = isEnable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }
}
