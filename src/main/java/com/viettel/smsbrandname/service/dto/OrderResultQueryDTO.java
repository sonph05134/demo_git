package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DateUtil;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class OrderResultQueryDTO {
    private String cpName;
    private Double amount;
    private Double discountPercent;
    private Double vat;
    private String currency;
    private Long balanceType;
    private Long orderType;
    private Instant chargeTime;
    private Long chargeResult;
    private String provinceBccsName;
    private String orderCode;
    private String approveNote;
    private Instant approveTime;
    private Long bccsSaleId;
    private String contactEmail;
    private String balanceTypeView;
    private String orderTypeView;
    private String chargeResultView;
    private Long id;

    public String getBalanceTypeView() {
        return balanceTypeView;
    }

    public String getOrderTypeView() {
        return orderTypeView;
    }

    public String getChargeResultView() {
        return chargeResultView;
    }

    public void setBalanceTypeView(String balanceTypeView) {
        this.balanceTypeView = balanceTypeView;
    }

    public void setOrderTypeView(String orderTypeView) {
        this.orderTypeView = orderTypeView;
    }

    public void setChargeResultView(String chargeResultView) {
        this.chargeResultView = chargeResultView;
    }

    public OrderResultQueryDTO(Object[] objects) {
        int i = 0;
        this.cpName = DataUtil.safeToString(objects[i++]);
        this.amount = DataUtil.safeToDouble(objects[i++]);
        this.discountPercent = DataUtil.safeToDouble(objects[i++]);
        this.vat = DataUtil.safeToDouble(objects[i++]);
        this.currency = DataUtil.safeToString(objects[i++]);
        this.balanceType = DataUtil.safeToLong(objects[i++]);
        this.orderType = DataUtil.safeToLong(objects[i++]);
        Date dateTmp = DateUtil.stringToDate(DataUtil.safeToString(objects[i++]), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        if (dateTmp != null) {
            this.chargeTime = dateTmp.toInstant();
        }
        this.chargeResult = DataUtil.safeToLong(objects[i++]);

        this.provinceBccsName = DataUtil.safeToString(objects[i++]);
        this.orderCode = DataUtil.safeToString(objects[i++]);
        this.approveNote = DataUtil.safeToString(objects[i++]);
        String strTime = DataUtil.safeToString(objects[i++]);
        if (!DataUtil.isNullOrEmpty(strTime)) {
            Date dateTmp1 = DateUtil.stringToDate(strTime, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            if (dateTmp1 != null) {
                this.approveTime = dateTmp1.toInstant();
            }
        }
        this.bccsSaleId = DataUtil.safeToLong(objects[i++]);
        this.contactEmail = DataUtil.safeToString(objects[i++]);
        this.id = DataUtil.safeToLong(objects[i++]);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Instant chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }


    public Long getChargeResult() {
        return chargeResult;
    }

    public void setChargeResult(Long chargeResult) {
        this.chargeResult = chargeResult;
    }

    public String getProvinceBccsName() {
        return provinceBccsName;
    }

    public void setProvinceBccsName(String provinceBccsName) {
        this.provinceBccsName = provinceBccsName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public Instant getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Instant approveTime) {
        this.approveTime = approveTime;
    }

    public Long getBccsSaleId() {
        return bccsSaleId;
    }

    public void setBccsSaleId(Long bccsSaleId) {
        this.bccsSaleId = bccsSaleId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
