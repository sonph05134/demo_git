package com.viettel.smsbrandname.service.dto.province;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ProvinceBccsDTO {

    private Long provinceId;
    private String provinceName;
    private Long id;
    private Long provinceBccsId;
    private String provinceBccsName;
    private String bccsStaffCode;
    private String userUpdated;
    private String dateUpdatedString;
    private Integer rn;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceBccsId() {
        return provinceBccsId;
    }

    public void setProvinceBccsId(Long provinceBccsId) {
        this.provinceBccsId = provinceBccsId;
    }

    public String getProvinceBccsName() {
        return provinceBccsName;
    }

    public void setProvinceBccsName(String provinceBccsName) {
        this.provinceBccsName = provinceBccsName;
    }

    public String getBccsStaffCode() {
        return bccsStaffCode;
    }

    public void setBccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public String getDateUpdatedString() {
        return dateUpdatedString;
    }

    public void setDateUpdatedString(String dateUpdatedString) {
        this.dateUpdatedString = dateUpdatedString;
    }

    public Integer getRn() {
        return rn;
    }

    public void setRn(Integer rn) {
        this.rn = rn;
    }

    public static List<ProvinceBccsDTO> toDto(List<Object[]> listData) {
        List<ProvinceBccsDTO> result = new ArrayList<>();
        if (listData != null && !listData.isEmpty()) {
            for (Object[] data : listData) {
                int i = 0;
                ProvinceBccsDTO provinceBCCSDTO = new ProvinceBccsDTO();
                provinceBCCSDTO.setProvinceId(DataUtil.safeToLong(data[i++]));
                provinceBCCSDTO.setProvinceName(DataUtil.safeToString(data[i++]));
                provinceBCCSDTO.setId(DataUtil.safeToLong(data[i++]));
                provinceBCCSDTO.setProvinceBccsId(DataUtil.safeToLong(data[i++]));
                provinceBCCSDTO.setProvinceBccsName(DataUtil.safeToString(data[i++]));
                provinceBCCSDTO.setBccsStaffCode(DataUtil.safeToString(data[i++]));
                provinceBCCSDTO.setUserUpdated(DataUtil.safeToString(data[i++]));
                provinceBCCSDTO.setDateUpdatedString(DataUtil.safeToString(data[i++]));
                provinceBCCSDTO.setRn(DataUtil.safeToInt(data[i++]));
                result.add(provinceBCCSDTO);
            }
        }
        return result;
    }
}
