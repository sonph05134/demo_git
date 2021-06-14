package com.viettel.smsbrandname.service.dto;

import org.springframework.web.multipart.MultipartFile;

public class CpGroupSubForm {
    private Long cpGroupId;
    private Long cpId;
    private MultipartFile fileImport;
    private String result;
    private String fileResult;

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public String getFileResult() {
        return fileResult;
    }

    public void setFileResult(String fileResult) {
        this.fileResult = fileResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getCpGroupId() {
        return cpGroupId;
    }

    public void setCpGroupId(Long cpGroupId) {
        this.cpGroupId = cpGroupId;
    }

    public MultipartFile getFileImport() {
        return fileImport;
    }

    public void setFileImport(MultipartFile fileImport) {
        this.fileImport = fileImport;
    }
}
