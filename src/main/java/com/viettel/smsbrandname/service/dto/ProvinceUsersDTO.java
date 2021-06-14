package com.viettel.smsbrandname.service.dto;


import com.viettel.smsbrandname.commons.DataUtil;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class ProvinceUsersDTO {
    private Long id;

    private Long provinceId;

    private String username;

    private Long userType;

    private Integer status;

    private Integer isParent;

    private String provinceName;

    private String fullName;

    private String cellPhone;

    private String email;

    private String staffCode;

    private Long rn;

    private String passWord;

    private String provinceCode;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getRn() {
        return rn;
    }

    public void setRn(Long rn) {
        this.rn = rn;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public static List<ProvinceUsersDTO> toDto(List<Object[]> objects) {
        List<ProvinceUsersDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.forEach(obj -> {
                int i = 0;
                ProvinceUsersDTO dto = new ProvinceUsersDTO();
                dto.setId(DataUtil.safeToLong(obj[i++]));
                dto.setProvinceName(DataUtil.safeToString(obj[i++]));
                dto.setUsername(DataUtil.safeToString(obj[i++]));
                dto.setProvinceId(DataUtil.safeToLong(obj[i++]));
                dto.setUserType(DataUtil.safeToLong(obj[i++]));
                dto.setIsParent(DataUtil.safeToInt(obj[i++]));
                dto.setStatus(DataUtil.safeToInt(obj[i++]));
                dto.setRn(DataUtil.safeToLong(obj[i++]));
//                dto.setFullName(DataUtil.safeToString(obj[i++]));
//                dto.setCellPhone(DataUtil.safeToString(obj[i++]));
//                dto.setEmail(DataUtil.safeToString(obj[i++]));
//                dto.setStaffCode(DataUtil.safeToString(obj[i++]));

                result.add(dto);
            });
        }
        return result;
    }

    public static ProvinceUsersDTO toLoginDto(List<Object[]> objects) {
        ProvinceUsersDTO dto = new ProvinceUsersDTO();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.forEach(obj -> {
                int i = 0;
                dto.setId(DataUtil.safeToLong(obj[i++]));
                dto.setUsername(DataUtil.safeToString(obj[i++]));
                dto.setUserType(DataUtil.safeToLong(obj[i++]));
                dto.setProvinceId(DataUtil.safeToLong(obj[i++]));
                dto.setIsParent(DataUtil.safeToInt(obj[i++]));
                dto.setProvinceCode(DataUtil.safeToString(obj[i++]));
            });
        } else {
            Long defaultValue = -1L;
            dto.setId(defaultValue);
            dto.setIsParent(DataUtil.safeToInt(defaultValue));
            dto.setProvinceId(defaultValue);
        }
        return dto;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
}
