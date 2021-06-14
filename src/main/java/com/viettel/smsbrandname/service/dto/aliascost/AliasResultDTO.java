package com.viettel.smsbrandname.service.dto.aliascost;

import com.viettel.smsbrandname.commons.DataUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AliasResultDTO {

    private Long aliasCostId;
    private String telcoName;
    private Long aliasCostType;
    private Long regAliasCost;
    private Long cancelAliasCost;
    private Long keepAliasCost;
    private Long regAliasCostUsd;
    private Long cancelAliasCostUsd;
    private Long keepAliasCostUsd;
    private String modifiedDate;
    private String aliasTelco;
    private Date createDate;
    private Integer rn;
    private String aliasCostTypeName;

    public Long getAliasCostId() {
        return aliasCostId;
    }

    public void setAliasCostId(Long aliasCostId) {
        this.aliasCostId = aliasCostId;
    }

    public String getTelcoName() {
        return telcoName;
    }

    public void setTelcoName(String telcoName) {
        this.telcoName = telcoName;
    }

    public Long getAliasCostType() {
        return aliasCostType;
    }

    public void setAliasCostType(Long aliasCostType) {
        this.aliasCostType = aliasCostType;
    }

    public Long getRegAliasCost() {
        return regAliasCost;
    }

    public void setRegAliasCost(Long regAliasCost) {
        this.regAliasCost = regAliasCost;
    }

    public Long getCancelAliasCost() {
        return cancelAliasCost;
    }

    public void setCancelAliasCost(Long cancelAliasCost) {
        this.cancelAliasCost = cancelAliasCost;
    }

    public Long getKeepAliasCost() {
        return keepAliasCost;
    }

    public void setKeepAliasCost(Long keepAliasCost) {
        this.keepAliasCost = keepAliasCost;
    }

    public Long getRegAliasCostUsd() {
        return regAliasCostUsd;
    }

    public void setRegAliasCostUsd(Long regAliasCostUsd) {
        this.regAliasCostUsd = regAliasCostUsd;
    }

    public Long getCancelAliasCostUsd() {
        return cancelAliasCostUsd;
    }

    public void setCancelAliasCostUsd(Long cancelAliasCostUsd) {
        this.cancelAliasCostUsd = cancelAliasCostUsd;
    }

    public Long getKeepAliasCostUsd() {
        return keepAliasCostUsd;
    }

    public void setKeepAliasCostUsd(Long keepAliasCostUsd) {
        this.keepAliasCostUsd = keepAliasCostUsd;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAliasTelco() {
        return aliasTelco;
    }

    public void setAliasTelco(String aliasTelco) {
        this.aliasTelco = aliasTelco;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getRn() {
        return rn;
    }

    public void setRn(Integer rn) {
        this.rn = rn;
    }

    public String getAliasCostTypeName() {
        return aliasCostTypeName;
    }

    public void setAliasCostTypeName(String aliasCostTypeName) {
        this.aliasCostTypeName = aliasCostTypeName;
    }

    public static List<AliasResultDTO> toDto(List<Object[]> listData) {
        List<AliasResultDTO> result = new ArrayList<>();
        if (listData != null && !listData.isEmpty()) {
            for (Object[] data : listData) {
                int i = 0;
                AliasResultDTO aliasResultDTO = new AliasResultDTO();
                aliasResultDTO.setAliasCostId(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setTelcoName(DataUtil.safeToString(data[i++]));
                aliasResultDTO.setAliasTelco(DataUtil.safeToString(data[i++]));
                aliasResultDTO.setAliasCostType(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setAliasCostTypeName(DataUtil.safeToString(data[i++]));
                aliasResultDTO.setRegAliasCost(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setCancelAliasCost(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setKeepAliasCost(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setRegAliasCostUsd(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setCancelAliasCostUsd(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setKeepAliasCostUsd(DataUtil.safeToLong(data[i++]));
                aliasResultDTO.setModifiedDate(DataUtil.safeToString(data[i++]));
                aliasResultDTO.setCreateDate((Date) data[i++]);
                aliasResultDTO.setRn(DataUtil.safeToInt(data[i++]));
                result.add(aliasResultDTO);
            }
        }
        return result;
    }
}
