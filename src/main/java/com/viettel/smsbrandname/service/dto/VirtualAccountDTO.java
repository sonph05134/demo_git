package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class VirtualAccountDTO extends PageDTO{

    private final Logger logger = LoggerFactory.getLogger(VirtualAccountDTO.class);

    private Date fromDate;
    private Date toDate;
    private Date dateChange;
    private Integer balanceType;
    private String cpCode;
    private Integer status;
    private String statusName;
    private String cpName;
    private String typeCP;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private BigDecimal currentBalance;
    private String note;
    private String currency;
    private BigDecimal rate;
    private String discount;
    private BigDecimal amount;
    private String branch;
    private String userModified;
    private Long cpId;
    private String attachFile;
    private Long isBccsOk;
    private Long statusBccsUsd;
    private BigDecimal adBalance;
    private String branchName;
    private Long id;
    private Long hasCommission;
    private Long commissionBccsOk;
    private String saleTransId;
    private String payMethod;
    private String balanceName;
    private MultipartFile file;
    private final NumberFormat formatter = new DecimalFormat("###.######");

    public Long getId() {
        return id;
    }

    public String getBalanceName() {
        return balanceName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setBalanceName(String balanceName) {
        this.balanceName = balanceName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getHasCommission() {
        return hasCommission;
    }

    public void setHasCommission(Long hasCommission) {
        this.hasCommission = hasCommission;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Long getCommissionBccsOk() {
        return commissionBccsOk;
    }

    public void setCommissionBccsOk(Long commissionBccsOk) {
        this.commissionBccsOk = commissionBccsOk;
    }

    public String getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(String saleTransId) {
        this.saleTransId = saleTransId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public VirtualAccountDTO() {
    }

    public BigDecimal getAdBalance() {
        return adBalance;
    }

    public void setAdBalance(BigDecimal adBalance) {
        this.adBalance = adBalance;
    }

    public Long getCpId() {
        return cpId;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public Long getIsBccsOk() {
        return isBccsOk;
    }

    public void setIsBccsOk(Long isBccsOk) {
        this.isBccsOk = isBccsOk;
    }

    public Long getStatusBccsUsd() {
        return statusBccsUsd;
    }

    public void setStatusBccsUsd(Long statusBccsUsd) {
        this.statusBccsUsd = statusBccsUsd;
    }

    public Date getDateChange() {
        return dateChange;
    }

    public void setDateChange(Date dateChange) {
        this.dateChange = dateChange;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getTypeCP() {
        return typeCP;
    }

    public void setTypeCP(String typeCP) {
        this.typeCP = typeCP;
    }

    public BigDecimal getBalanceBefore() {
    return convertCurrency(balanceBefore);
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return convertCurrency(balanceAfter);
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public BigDecimal getCurrentBalance() {
        return convertCurrency(currentBalance);
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return convertCurrency(amount);
    }

    public BigDecimal getAmountForCharge() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUserModified() {
        return userModified;
    }

    public void setUserModified(String userModified) {
        this.userModified = userModified;
    }

    private BigDecimal convertCurrency(BigDecimal amount) {
        if (currency != null && Constants.CURRENCY_USD.equals(currency) && amount != null) {
            amount = amount.divide(new BigDecimal(Constants.USD_LENGTH));
        } else if (amount == null) {
            amount = BigDecimal.ZERO;
        }
        return amount;
    }
}
