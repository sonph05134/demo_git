package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.service.dto.province.ProvinceBccsDTO;

import java.sql.Clob;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SysConfigDTO {

    private Long sysConfigId;
    private String configGroup;
    private String configCode;
    private String configName;
    private String configValue;
    private String userUpdated;
    private String dateUpdateds;
    private String note;
    private Long deleted;
    private Long module;
    private Long rn;

    public Long getSysConfigId() {
        return sysConfigId;
    }

    public void setSysConfigId(Long sysConfigId) {
        this.sysConfigId = sysConfigId;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(String configGroup) {
        this.configGroup = configGroup;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(String userUpdated) {
        this.userUpdated = userUpdated;
    }

    public String getDateUpdateds() {
        return dateUpdateds;
    }

    public void setDateUpdateds(String dateUpdateds) {
        this.dateUpdateds = dateUpdateds;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public Long getRn() {
        return rn;
    }

    public void setRn(Long rn) {
        this.rn = rn;
    }

    public static List<SysConfigDTO> toDto(List<Object[]> listData) throws SQLException {
        List<SysConfigDTO> result = new ArrayList<>();
        if (listData != null && !listData.isEmpty()) {
            for (Object[] data : listData) {
                int i = 0;
                SysConfigDTO sysConfigDTO = new SysConfigDTO();
                sysConfigDTO.setSysConfigId(DataUtil.safeToLong(data[i++]));
                sysConfigDTO.setConfigGroup(DataUtil.safeToString(data[i++]));
                sysConfigDTO.setConfigCode(DataUtil.safeToString(data[i++]));
                sysConfigDTO.setConfigName(DataUtil.safeToString(data[i++]));
                sysConfigDTO.setUserUpdated(DataUtil.safeToString(data[i++]));
                sysConfigDTO.setDateUpdateds(DataUtil.safeToString(data[i++]));
                sysConfigDTO.setDeleted(DataUtil.safeToLong(data[i++]));
                sysConfigDTO.setModule(DataUtil.safeToLong(data[i++]));
                sysConfigDTO.setRn(DataUtil.safeToLong(data[i++]));

                String value = "";
                if (data[i++] != null) {
                    Clob clob = (Clob) data[9];
                    value = clob.getSubString(1, (int) clob.length());
                }
                sysConfigDTO.setConfigValue(value);

                String note = "";
                if (data[i++] != null) {
                    Clob clob = (Clob) data[10];
                    note = clob.getSubString(1, (int) clob.length());
                }
                sysConfigDTO.setNote(note);
                result.add(sysConfigDTO);
            }
        }
        return result;
    }
}
