package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.service.dto.aliascost.AliasResultDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceLevelDTO {

    private Long priceLevelId;
    private String priceLevelName;
    private Long numSms;
    private Integer type;
    private Integer rn;
    private String typeName;

    public Long getPriceLevelId() {
        return priceLevelId;
    }

    public void setPriceLevelId(Long priceLevelId) {
        this.priceLevelId = priceLevelId;
    }

    public String getPriceLevelName() {
        return priceLevelName;
    }

    public void setPriceLevelName(String priceLevelName) {
        this.priceLevelName = priceLevelName;
    }

    public Long getNumSms() {
        return numSms;
    }

    public void setNumSms(Long numSms) {
        this.numSms = numSms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRn() {
        return rn;
    }

    public void setRn(Integer rn) {
        this.rn = rn;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static List<PriceLevelDTO> toDto(List<Object[]> listData) {
        List<PriceLevelDTO> result = new ArrayList<>();
        if (listData != null && !listData.isEmpty()) {
            for (Object[] data : listData) {
                int i = 0;
                PriceLevelDTO priceLevelDTO = new PriceLevelDTO();
                priceLevelDTO.setPriceLevelId(DataUtil.safeToLong(data[i++]));
                priceLevelDTO.setPriceLevelName(DataUtil.safeToString(data[i++]));
                priceLevelDTO.setNumSms(DataUtil.safeToLong(data[i++]));
                priceLevelDTO.setType(DataUtil.safeToInt(data[i++]));
                priceLevelDTO.setRn(DataUtil.safeToInt(data[i++]));
                priceLevelDTO.setTypeName(DataUtil.safeToString(data[i++]));
                result.add(priceLevelDTO);
            }
        }
        return result;
    }
}
