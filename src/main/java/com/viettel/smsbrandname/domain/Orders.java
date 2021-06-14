package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Order.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "trans_id")
    private String transId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "jhi_time")
    private Instant time;

    @Column(name = "charge_result")
    private Long chargeResult;

    @Column(name = "balance_type")
    private Long balanceType;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_type")
    private Long orderType;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "discount_percent")
    private Long discountPercent;

    @Column(name = "payment_value")
    private Long paymentValue;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "note")
    private String note;

    @Column(name = "approve_user")
    private String approveUser;

    @Column(name = "approve_time")
    private Instant approveTime;

    @Column(name = "approve_note")
    private String approveNote;

    @Column(name = "charge_user")
    private String chargeUser;

    @Column(name = "charge_time")
    private Instant chargeTime;

    @Column(name = "charge_note")
    private String chargeNote;

    @Column(name = "bccs_sale_id")
    private String bccsSaleId;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "admin_file_url")
    private String adminFileUrl;

    @Column(name = "admin_file_name")
    private String adminFileName;

    @Column(name = "deleted")
    private Long deleted;

    @Column(name = "finance_file_url")
    private String financeFileUrl;

    @Column(name = "finance_file_name")
    private String financeFileName;

    @Column(name = "bccs_branch_id")
    private Long bccsBranchId;

    @Column(name = "charge_fail")
    private Long chargeFail;

    @Column(name = "approve_bccs_fail")
    private Long approveBccsFail;

    @Column(name = "bccs_response")
    private String bccsResponse;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "bccs_staff_code")
    private String bccsStaffCode;

    @Column(name = "cp_code")
    private String cpCode;

    @Column(name = "exchange_rate")
    private Long exchangeRate;

    @Column(name = "vat")
    private Long vat;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public Orders partnerId(String partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTransId() {
        return transId;
    }

    public Orders transId(String transId) {
        this.transId = transId;
        return this;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getProvider() {
        return provider;
    }

    public Orders provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Orders serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Orders errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Orders errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getTime() {
        return time;
    }

    public Orders time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Long getChargeResult() {
        return chargeResult;
    }

    public Orders chargeResult(Long chargeResult) {
        this.chargeResult = chargeResult;
        return this;
    }

    public void setChargeResult(Long chargeResult) {
        this.chargeResult = chargeResult;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public Orders balanceType(Long balanceType) {
        this.balanceType = balanceType;
        return this;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Orders orderCode(String orderCode) {
        this.orderCode = orderCode;
        return this;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getOrderType() {
        return orderType;
    }

    public Orders orderType(Long orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public Long getCpId() {
        return cpId;
    }

    public Orders cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Long getAmount() {
        return amount;
    }

    public Orders amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getContactName() {
        return contactName;
    }

    public Orders contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Orders contactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Long getDiscountPercent() {
        return discountPercent;
    }

    public Orders discountPercent(Long discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public void setDiscountPercent(Long discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Long getPaymentValue() {
        return paymentValue;
    }

    public Orders paymentValue(Long paymentValue) {
        this.paymentValue = paymentValue;
        return this;
    }

    public void setPaymentValue(Long paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Orders fileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public Orders fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNote() {
        return note;
    }

    public Orders note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public Orders approveUser(String approveUser) {
        this.approveUser = approveUser;
        return this;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public Instant getApproveTime() {
        return approveTime;
    }

    public Orders approveTime(Instant approveTime) {
        this.approveTime = approveTime;
        return this;
    }

    public void setApproveTime(Instant approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public Orders approveNote(String approveNote) {
        this.approveNote = approveNote;
        return this;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public Orders chargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
        return this;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public Instant getChargeTime() {
        return chargeTime;
    }

    public Orders chargeTime(Instant chargeTime) {
        this.chargeTime = chargeTime;
        return this;
    }

    public void setChargeTime(Instant chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeNote() {
        return chargeNote;
    }

    public Orders chargeNote(String chargeNote) {
        this.chargeNote = chargeNote;
        return this;
    }

    public void setChargeNote(String chargeNote) {
        this.chargeNote = chargeNote;
    }

    public String getBccsSaleId() {
        return bccsSaleId;
    }

    public Orders bccsSaleId(String bccsSaleId) {
        this.bccsSaleId = bccsSaleId;
        return this;
    }

    public void setBccsSaleId(String bccsSaleId) {
        this.bccsSaleId = bccsSaleId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Orders contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAdminFileUrl() {
        return adminFileUrl;
    }

    public Orders adminFileUrl(String adminFileUrl) {
        this.adminFileUrl = adminFileUrl;
        return this;
    }

    public void setAdminFileUrl(String adminFileUrl) {
        this.adminFileUrl = adminFileUrl;
    }

    public String getAdminFileName() {
        return adminFileName;
    }

    public Orders adminFileName(String adminFileName) {
        this.adminFileName = adminFileName;
        return this;
    }

    public void setAdminFileName(String adminFileName) {
        this.adminFileName = adminFileName;
    }

    public Long getDeleted() {
        return deleted;
    }

    public Orders deleted(Long deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getFinanceFileUrl() {
        return financeFileUrl;
    }

    public Orders financeFileUrl(String financeFileUrl) {
        this.financeFileUrl = financeFileUrl;
        return this;
    }

    public void setFinanceFileUrl(String financeFileUrl) {
        this.financeFileUrl = financeFileUrl;
    }

    public String getFinanceFileName() {
        return financeFileName;
    }

    public Orders financeFileName(String financeFileName) {
        this.financeFileName = financeFileName;
        return this;
    }

    public void setFinanceFileName(String financeFileName) {
        this.financeFileName = financeFileName;
    }

    public Long getBccsBranchId() {
        return bccsBranchId;
    }

    public Orders bccsBranchId(Long bccsBranchId) {
        this.bccsBranchId = bccsBranchId;
        return this;
    }

    public void setBccsBranchId(Long bccsBranchId) {
        this.bccsBranchId = bccsBranchId;
    }

    public Long getChargeFail() {
        return chargeFail;
    }

    public Orders chargeFail(Long chargeFail) {
        this.chargeFail = chargeFail;
        return this;
    }

    public void setChargeFail(Long chargeFail) {
        this.chargeFail = chargeFail;
    }

    public Long getApproveBccsFail() {
        return approveBccsFail;
    }

    public Orders approveBccsFail(Long approveBccsFail) {
        this.approveBccsFail = approveBccsFail;
        return this;
    }

    public void setApproveBccsFail(Long approveBccsFail) {
        this.approveBccsFail = approveBccsFail;
    }

    public String getBccsResponse() {
        return bccsResponse;
    }

    public Orders bccsResponse(String bccsResponse) {
        this.bccsResponse = bccsResponse;
        return this;
    }

    public void setBccsResponse(String bccsResponse) {
        this.bccsResponse = bccsResponse;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Orders paymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBccsStaffCode() {
        return bccsStaffCode;
    }

    public Orders bccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
        return this;
    }

    public void setBccsStaffCode(String bccsStaffCode) {
        this.bccsStaffCode = bccsStaffCode;
    }

    public String getCpCode() {
        return cpCode;
    }

    public Orders cpCode(String cpCode) {
        this.cpCode = cpCode;
        return this;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public Orders exchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getVat() {
        return vat;
    }

    public Orders vat(Long vat) {
        this.vat = vat;
        return this;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
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
