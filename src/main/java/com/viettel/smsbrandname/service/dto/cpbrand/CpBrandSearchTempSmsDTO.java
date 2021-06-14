package com.viettel.smsbrandname.service.dto.cpbrand;

public class CpBrandSearchTempSmsDTO {

    private Long cpAliasId;
    private Integer status;
    private Integer approve;
    private String template;
    private Integer telcoApprove;
    private Integer limit;
    private Integer start;

    public Long getCpAliasId() {
        return cpAliasId;
    }

    public void setCpAliasId(Long cpAliasId) {
        this.cpAliasId = cpAliasId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getTelcoApprove() {
        return telcoApprove;
    }

    public void setTelcoApprove(Integer telcoApprove) {
        this.telcoApprove = telcoApprove;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
