package com.viettel.cmsmobilebrandname.mobile.dto;

import com.viettel.smsbrandname.service.dto.PageDTO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Autogen class DTO: Lop thao tac danh sach Filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:21 ICT 2020
 */
public class FilterDTOMB extends PageDTO {

    Long cfgFilterId;

    @NotNull(message = "{filter.keyword.notNull}")
    @NotEmpty(message = "{filter.keyword.NotEmpty}")
    @Length(max = 200,message = "{filter.keyword.number}")
    String keyword;

    Long status;

    String statusName;

    public Long getCfgFilterId() {
        return cfgFilterId;
    }

    public void setCfgFilterId(Long cfgFilterId) {
        this.cfgFilterId = cfgFilterId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
