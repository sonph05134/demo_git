package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class AdjustCommissionHisSearchDTO {

    private Long cpId;
    private String updatedDate;
    private String userName;
    private String oldValue;
    private String newValue;
    private String reason;

    public static List<AdjustCommissionHisSearchDTO> toDto(List<Object[]> objects) {
        List<AdjustCommissionHisSearchDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                AdjustCommissionHisSearchDTO dto = new AdjustCommissionHisSearchDTO();
                dto.setCpId(DataUtil.safeToLong(obj[i++]));
                dto.setUpdatedDate(DataUtil.safeToString(obj[i++]));
                dto.setUserName(DataUtil.safeToString(obj[i++]));
                dto.setOldValue(DataUtil.safeToString(obj[i++]));
                dto.setNewValue(DataUtil.safeToString(obj[i++]));
                dto.setReason(DataUtil.safeToString(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
