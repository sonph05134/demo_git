package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class CommissionPercentCodeDTO {
    private String percentCode;
    private String note;

    public static List<CommissionPercentCodeDTO> toDto(List<Object[]> objects) {
        List<CommissionPercentCodeDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                CommissionPercentCodeDTO dto = new CommissionPercentCodeDTO();
                dto.setPercentCode(DataUtil.safeToString(obj[i++]));
                dto.setNote(DataUtil.safeToString(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

    public String getPercentCode() {
        return percentCode;
    }

    public void setPercentCode(String percentCode) {
        this.percentCode = percentCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
