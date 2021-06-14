package com.viettel.smsbrandname.service.dto.cpbrand;

public class CpBrandHistoryDTO {

    private Integer count;
    private Integer approveBefore;
    private Integer approveAfter;
    private String description;
    private String reasonForDeny;
    private String userCreated;
    private String dateCreates;
    private Integer rowNum;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getApproveBefore() {
        return approveBefore;
    }

    public void setApproveBefore(Integer approveBefore) {
        this.approveBefore = approveBefore;
    }

    public Integer getApproveAfter() {
        return approveAfter;
    }

    public void setApproveAfter(Integer approveAfter) {
        this.approveAfter = approveAfter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReasonForDeny() {
        return reasonForDeny;
    }

    public void setReasonForDeny(String reasonForDeny) {
        this.reasonForDeny = reasonForDeny;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public String getDateCreates() {
        return dateCreates;
    }

    public void setDateCreates(String dateCreates) {
        this.dateCreates = dateCreates;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }
}
