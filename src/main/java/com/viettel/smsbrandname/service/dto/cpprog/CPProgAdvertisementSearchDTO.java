package com.viettel.smsbrandname.service.dto.cpprog;

import java.time.Instant;
import java.util.Date;


public class CPProgAdvertisementSearchDTO {
    private  String cpId;
    private String progCode;
    private  Long catId;
    private  String progStatus;
    private Date progCreateDate;
    private  Date progSentSchedule;
    private  String userName;
    private Integer currentPage;
    private Integer pageSize;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getProgCode() {
        return progCode;
    }

    public void setProgCode(String progCode) {
        this.progCode = progCode;
    }

    public String getProgStatus() {
        return progStatus;
    }

    public void setProgStatus(String progStatus) {
        this.progStatus = progStatus;
    }

    public Date getProgCreateDate() {
        return progCreateDate;
    }

    public void setProgCreateDate(Date progCreateDate) {
        this.progCreateDate = progCreateDate;
    }

    public Date getProgSentSchedule() {
        return progSentSchedule;
    }

    public void setProgSentSchedule(Date progSentSchedule) {
        this.progSentSchedule = progSentSchedule;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
