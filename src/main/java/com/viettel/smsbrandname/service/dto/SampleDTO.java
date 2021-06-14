package com.viettel.smsbrandname.service.dto;

public class SampleDTO {
    public SampleDTO(String msg){
        this.msg=msg;
    }
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
