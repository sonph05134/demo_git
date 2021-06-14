package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ProvinceDTO {

    private Long provinceId;
    private String provinceName;

    public static List<ProvinceDTO> toDto(List<Object[]> objects) {
        List<ProvinceDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                ProvinceDTO dto = new ProvinceDTO();
                dto.setProvinceId(DataUtil.safeToLong(obj[i++]));
                dto.setProvinceName(DataUtil.safeToString(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

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
}
