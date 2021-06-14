package com.viettel.smsbrandname.service.dto.cpbrand;

public class CpBrandApproveDTO {

    private Short action;
    private String cpAliasIds;

    public Short getAction() {
        return action;
    }

    public void setAction(Short action) {
        this.action = action;
    }

    public String getCpAliasIds() {
        return cpAliasIds;
    }

    public void setCpAliasIds(String cpAliasIds) {
        this.cpAliasIds = cpAliasIds;
    }
}
