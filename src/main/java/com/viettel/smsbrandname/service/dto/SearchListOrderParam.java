package com.viettel.smsbrandname.service.dto;

import java.time.Instant;

public class SearchListOrderParam {
    private Long balancetype;
    private Long ordertype;
    private Long chargeresult;
    private String staffname;
    private Long provincebccsid;
    private Instant fromDate;
    private Instant toDate;
    public Long getBalancetype() {
        return balancetype;
    }

    public void setBalancetype(Long balancetype) {
        this.balancetype = balancetype;
    }

    public Long getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Long ordertype) {
        this.ordertype = ordertype;
    }

    public Long getChargeresult() {
        return chargeresult;
    }

    public void setChargeresult(Long chargeresult) {
        this.chargeresult = chargeresult;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public Long getProvincebccsid() {
        return provincebccsid;
    }

    public void setProvincebccsid(Long provincebccsid) {
        this.provincebccsid = provincebccsid;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }
}
