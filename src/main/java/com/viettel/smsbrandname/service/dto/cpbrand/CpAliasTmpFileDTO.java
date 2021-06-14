package com.viettel.smsbrandname.service.dto.cpbrand;

public class CpAliasTmpFileDTO {

    private Long cpAliasId;
    private String filePathCreate;
    private String filePathCancel;

    public Long getCpAliasId() {
        return cpAliasId;
    }

    public void setCpAliasId(Long cpAliasId) {
        this.cpAliasId = cpAliasId;
    }

    public String getFilePathCreate() {
        return filePathCreate;
    }

    public void setFilePathCreate(String filePathCreate) {
        this.filePathCreate = filePathCreate;
    }

    public String getFilePathCancel() {
        return filePathCancel;
    }

    public void setFilePathCancel(String filePathCancel) {
        this.filePathCancel = filePathCancel;
    }
}
