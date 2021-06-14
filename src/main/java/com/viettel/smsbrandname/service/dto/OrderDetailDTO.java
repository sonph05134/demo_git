package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;

public class OrderDetailDTO {
    private String cpName;
    private String address;
    private String taxCode;
    private String orderNo;
    private String orderCode;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String balanceTypeView;
    private Long amount;
    private Long discountPercent;
    private Long discountAmount;// tien chiet khau = amount*discountPercent
    private Long amountGood;// tien hang = Tien nop / ((vat + 100)/100)
    private Long vat;// thue
    private Long vatAmount;// Tien nop - tien hang
    private Long paymentValue;// Tien nop
    private Long exchangeRate; // ti gia
    private String currency; //loai tien te
    private String fileName;
    private String note;
    private String chargeResultView;
    private String approveUser;
    private String adminFileName;
    private String chargeUser;
    private String financeFileName;
    private String provinceBccsName;
    private String approveNote;
    private Long chargeResult;

    public OrderDetailDTO(Object[] objs){
        int i = 0;
        this.cpName = DataUtil.safeToString(objs[i++]);
        this.address = DataUtil.safeToString(objs[i++]);
        this.taxCode = DataUtil.safeToString(objs[i++]);
        this.orderNo = DataUtil.safeToString(objs[i++]);
        this.orderCode = DataUtil.safeToString(objs[i++]);
        this.contactName = DataUtil.safeToString(objs[i++]);
        this.contactPhone = DataUtil.safeToString(objs[i++]);
        this.contactEmail = DataUtil.safeToString(objs[i++]);
        this.balanceTypeView = DataUtil.safeToString(objs[i++]);
        this.amount = DataUtil.safeToLong(objs[i++]);
        this.discountPercent = DataUtil.safeToLong(objs[i++]);
        this.discountAmount = DataUtil.safeToLong(objs[i++]);// tien chiet khau = amount*discountPercent
        this.amountGood = DataUtil.safeToLong(objs[i++]);// tien hang = Tien nop / ((vat + 100)/100)
        this.vat = DataUtil.safeToLong(objs[i++]);// thue
        this.vatAmount = DataUtil.safeToLong(objs[i++]);// Tien nop - tien hang
        this.paymentValue = DataUtil.safeToLong(objs[i++]);// Tien nop
        this.exchangeRate = DataUtil.safeToLong(objs[i++]); // ti gia
        this.currency = DataUtil.safeToString(objs[i++]); //loai tien te
        this.fileName = DataUtil.safeToString(objs[i++]);
        this.note = DataUtil.safeToString(objs[i++]);
        this.chargeResultView = DataUtil.safeToString(objs[i++]);
        this.approveUser = DataUtil.safeToString(objs[i++]);
        this.adminFileName = DataUtil.safeToString(objs[i++]);
        this.chargeUser = DataUtil.safeToString(objs[i++]);
        this.financeFileName = DataUtil.safeToString(objs[i++]);
        this.provinceBccsName = DataUtil.safeToString(objs[i++]);
        this.approveNote = DataUtil.safeToString(objs[i++]);
        this.chargeResult = DataUtil.safeToLong(objs[i++]);
    }

    public Long getChargeResult() {
        return chargeResult;
    }

    public void setChargeResult(Long chargeResult) {
        this.chargeResult = chargeResult;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setBalanceTypeView(String balanceTypeView) {
        this.balanceTypeView = balanceTypeView;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setDiscountPercent(Long discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setAmountGood(Long amountGood) {
        this.amountGood = amountGood;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public void setVatAmount(Long vatAmount) {
        this.vatAmount = vatAmount;
    }

    public void setPaymentValue(Long paymentValue) {
        this.paymentValue = paymentValue;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setChargeResultView(String chargeResultView) {
        this.chargeResultView = chargeResultView;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public void setAdminFileName(String adminFileName) {
        this.adminFileName = adminFileName;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public void setFinanceFileName(String financeFileName) {
        this.financeFileName = financeFileName;
    }

    public void setProvinceBccsName(String provinceBccsName) {
        this.provinceBccsName = provinceBccsName;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public String getCpName() {
        return cpName;
    }

    public String getAddress() {
        return address;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getBalanceTypeView() {
        return balanceTypeView;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getDiscountPercent() {
        return discountPercent;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public Long getAmountGood() {
        return amountGood;
    }

    public Long getVat() {
        return vat;
    }

    public Long getVatAmount() {
        return vatAmount;
    }

    public Long getPaymentValue() {
        return paymentValue;
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFileName() {
        return fileName;
    }

    public String getNote() {
        return note;
    }

    public String getChargeResultView() {
        return chargeResultView;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public String getAdminFileName() {
        return adminFileName;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public String getFinanceFileName() {
        return financeFileName;
    }

    public String getProvinceBccsName() {
        return provinceBccsName;
    }

    public String getApproveNote() {
        return approveNote;
    }
}
