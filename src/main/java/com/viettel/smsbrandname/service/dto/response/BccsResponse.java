package com.viettel.smsbrandname.service.dto.response;

public class BccsResponse {

    private String responseCode;
    private String result;
    private Boolean success;
    public BccsResponse() {
    }

    public BccsResponse(String responseCode, String result) {
        this.responseCode = responseCode;
        this.result = result;
    }

    public BccsResponse(String responseCode, String result, Boolean success) {
        this.responseCode = responseCode;
        this.result = result;
        this.success = success;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
