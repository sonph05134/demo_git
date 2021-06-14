package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class DistrictDTO {
    private Long districtId;
    private String districtName;

    public static List<DistrictDTO> toDto(List<Object[]> objects) {
        List<DistrictDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                DistrictDTO dto = new DistrictDTO();
                dto.setDistrictId(DataUtil.safeToLong(obj[i++]));
                dto.setDistrictName(DataUtil.safeToString(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
