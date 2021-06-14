package com.viettel.smsbrandname.service.dto.cpbrand;

public class CpBrandActionDTO {

    private Long cpAliasID;
    private Long cpAliasTmpID;
    private String actionKeyword;
    private String userName;
    private Integer action;

    public Long getCpAliasID() {
        return cpAliasID;
    }

    public void setCpAliasID(Long cpAliasID) {
        this.cpAliasID = cpAliasID;
    }

    public Long getCpAliasTmpID() {
        return cpAliasTmpID;
    }

    public void setCpAliasTmpID(Long cpAliasTmpID) {
        this.cpAliasTmpID = cpAliasTmpID;
    }

    public String getActionKeyword() {
        return actionKeyword;
    }

    public void setActionKeyword(String actionKeyword) {
        this.actionKeyword = actionKeyword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
