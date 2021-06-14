package com.viettel.smsbrandname.service.dto.cpprog;


import java.io.Serializable;
import java.util.List;

public class ProgAdApproveHisDTO implements Serializable {
    private Long progId;
    private String note;
    private String user;
    private String statusNew;
    private String statusOld;
    private String needApproveRun;
    private Long hidePrefix;
    private Long hideSuffix;

    public Long getProgId() {
        return progId;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatusNew() {
        return statusNew;
    }

    public void setStatusNew(String statusNew) {
        this.statusNew = statusNew;
    }

    public String getStatusOld() {
        return statusOld;
    }

    public void setStatusOld(String statusOld) {
        this.statusOld = statusOld;
    }

    public String getNeedApproveRun() {
        return needApproveRun;
    }

    public void setNeedApproveRun(String needApproveRun) {
        this.needApproveRun = needApproveRun;
    }

    public Long getHidePrefix() {
        return hidePrefix;
    }

    public void setHidePrefix(Long hidePrefix) {
        this.hidePrefix = hidePrefix;
    }

    public Long getHideSuffix() {
        return hideSuffix;
    }

    public void setHideSuffix(Long hideSuffix) {
        this.hideSuffix = hideSuffix;
    }
}
