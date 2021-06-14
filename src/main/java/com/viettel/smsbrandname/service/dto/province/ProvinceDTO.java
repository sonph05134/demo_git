package com.viettel.smsbrandname.service.dto.province;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ProvinceDTO {

    private Long provinceId;
    private String provinceName;
    private String provinceCode;
    private String provinceCodeBccs;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceCodeBccs() {
        return provinceCodeBccs;
    }

    public void setProvinceCodeBccs(String provinceCodeBccs) {
        this.provinceCodeBccs = provinceCodeBccs;
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

    public static List<ProvinceDTO> toDto(List<Object[]> listData) {
        List<ProvinceDTO> result = new ArrayList<>();
        if (listData != null && !listData.isEmpty()) {
            for (Object[] data : listData) {
                int i = 0;
                ProvinceDTO provinceDTO = new ProvinceDTO();
                provinceDTO.setProvinceId(DataUtil.safeToLong(data[i++]));
                provinceDTO.setProvinceName(DataUtil.safeToString(data[i++]));
                provinceDTO.setProvinceCode(DataUtil.safeToString(data[i++]));
                provinceDTO.setProvinceCodeBccs(DataUtil.safeToString(data[i++]));
                provinceDTO.setUserUpdated(DataUtil.safeToString(data[i++]));
                provinceDTO.setDateUpdatedString(DataUtil.safeToString(data[i++]));
                provinceDTO.setRn(DataUtil.safeToInt(data[i++]));
                result.add(provinceDTO);
            }
        }
        return result;
    }
}
