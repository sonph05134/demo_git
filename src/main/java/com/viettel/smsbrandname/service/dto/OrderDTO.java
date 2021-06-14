package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.domain.Orders;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link Orders} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    private String partnerId;

    private String transId;

    private String provider;

    private String serviceName;

    private String errorCode;

    private String errorMessage;

    private Instant time;

    private Long chargeResult;

    private Long balanceType;

    private String orderCode;

    private Long orderType;

    private Long cpId;

    private Long amount;

    private String contactName;

    private String contactPhone;

    private Long discountPercent;

    private Long paymentValue;

    private String fileUrl;

    private String fileName;

    private String note;

    private String approveUser;

    private Instant approveTime;

    private String approveNote;

    private String chargeUser;

    private Instant chargeTime;

    private String chargeNote;

    private String bccsSaleId;

    private String contactEmail;

    private String adminFileUrl;

    private String adminFileName;

    private Long deleted;

    private String financeFileUrl;

    private String financeFileName;

    private Long bccsBranchId;

    private Long chargeFail;

    private Long approveBccsFail;

    private String bccsResponse;

    private String paymentStatus;

    private String bccsStaffCode;

    private String cpCode;

    private Long exchangeRate;

    private Long vat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Long getChargeResult() {
        return chargeResult;
    }

    public void setChargeResult(Long chargeResult) {
        this.chargeResult = chargeResult;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Long getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Long discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Long getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Long paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public Instant getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Instant approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public Instant getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Instant chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeNote() {
        return chargeNote;
    }

    public void setChargeNote(String chargeNote) {
        this.chargeNote = chargeNote;
    }

    public String getBccsSaleId() {
        return bccsSaleId;
    }

    public void setBccsSaleId(String bccsSaleId) {
        this.bccsSaleId = bccsSaleId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAdminFileUrl() {
        return adminFileUrl;
    }

    public void setAdminFileUrl(String adminFileUrl) {
        this.adminFileUrl = adminFileUrl;
    }

    public String getAdminFileName() {
        return adminFileName;
    }

    public void setAdminFileName(String adminFileName) {
        this.adminFileName = adminFileName;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getFinanceFileUrl() {
        return financeFileUrl;
    }

    public void setFinanceFileUrl(String financeFileUrl) {
        this.financeFileUrl = financeFileUrl;
    }

    public String getFinanceFileName() {
        return financeFileName;
    }

    public void setFinanceFileName(String financeFileName) {
        this.financeFileName = financeFileName;
    }

    public Long getBccsBranchId() {
        return bccsBranchId;
    }

    public void setBccsBranchId(Long bccsBranchId) {
        this.bccsBranchId = bccsBranchId;
    }

    public Long getChargeFail() {
        return chargeFail;
    }

    public void setChargeFail(Long chargeFail) {
        this.chargeFail = chargeFail;
    }

    public Long getApproveBccsFail() {
        return approveBccsFail;
    }

    public void setApproveBccsFail(Long approveBccsFail) {
        this.approveBccsFail = approveBccsFail;
    }

    public String getBccsResponse() {
        return bccsResponse;
    }

    public void setBccsResponse(String bccsResponse) {
        this.bccsResponse = bccsResponse;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBccsStaffCode() {
        return bccsStaffCode;
    }

    public void setBccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", partnerId='" + getPartnerId() + "'" +
            ", transId='" + getTransId() + "'" +
            ", provider='" + getProvider() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", errorCode='" + getErrorCode() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", time='" + getTime() + "'" +
            ", chargeResult=" + getChargeResult() +
            ", balanceType=" + getBalanceType() +
            ", orderCode='" + getOrderCode() + "'" +
            ", orderType=" + getOrderType() +
            ", cpId=" + getCpId() +
            ", amount=" + getAmount() +
            ", contactName='" + getContactName() + "'" +
            ", contactPhone='" + getContactPhone() + "'" +
            ", discountPercent=" + getDiscountPercent() +
            ", paymentValue=" + getPaymentValue() +
            ", fileUrl='" + getFileUrl() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", note='" + getNote() + "'" +
            ", approveUser='" + getApproveUser() + "'" +
            ", approveTime='" + getApproveTime() + "'" +
            ", approveNote='" + getApproveNote() + "'" +
            ", chargeUser='" + getChargeUser() + "'" +
            ", chargeTime='" + getChargeTime() + "'" +
            ", chargeNote='" + getChargeNote() + "'" +
            ", bccsSaleId='" + getBccsSaleId() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", adminFileUrl='" + getAdminFileUrl() + "'" +
            ", adminFileName='" + getAdminFileName() + "'" +
            ", deleted=" + getDeleted() +
            ", financeFileUrl='" + getFinanceFileUrl() + "'" +
            ", financeFileName='" + getFinanceFileName() + "'" +
            ", bccsBranchId=" + getBccsBranchId() +
            ", chargeFail=" + getChargeFail() +
            ", approveBccsFail=" + getApproveBccsFail() +
            ", bccsResponse='" + getBccsResponse() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", bccsStaffCode='" + getBccsStaffCode() + "'" +
            ", cpCode='" + getCpCode() + "'" +
            ", exchangeRate=" + getExchangeRate() +
            ", vat=" + getVat() +
            "}";
    }
}
