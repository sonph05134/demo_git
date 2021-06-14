package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class StaffSearchDTO {

    private String staffCode;
    private String name;
    private Integer page;
    private Integer pageSize;

    public static List<StaffSearchDTO> toDto(List<Object[]> objects) {
        List<StaffSearchDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                StaffSearchDTO dto = new StaffSearchDTO();
                dto.setStaffCode(DataUtil.safeToString(obj[i++]));
                dto.setName(DataUtil.safeToString(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
