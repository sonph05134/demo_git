package com.viettel.smsbrandname.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.DateUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.TransLog} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransLogDTO implements Serializable {

    private Long transId;

    private Long cpId;

    private Instant transTime;

    private Long chanel;

    private Long amount;

    private String transNote;

    private String alias;

    private Long process;

    private Long balanceType;

    private Long balanceBefore;

    private Long balanceAfter;

    private String cpCode;

    private String cpName;

    private String currency;

    private String chanelView;

    private Long stt;

    public TransLogDTO() {
    }

    public TransLogDTO(Object[] objs) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.cpCode = DataUtil.safeToString(objs[0]);
        this.cpName = DataUtil.safeToString(objs[1]);
        this.chanelView = DataUtil.safeToString(objs[2]);
        this.amount = DataUtil.safeToLong(objs[3]);
        this.balanceBefore = DataUtil.safeToLong(objs[4]);
        this.balanceAfter = DataUtil.safeToLong(objs[5]);
        this.transNote = DataUtil.safeToString(objs[6]);
        Date date = DateUtil.stringToDate(DataUtil.safeToString(objs[7]), dateFormat);
        if (date != null) {
            this.transTime = date.toInstant();
        }
        this.currency = DataUtil.safeToString(objs[8]);
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Long getStt() {
        return stt;
    }

    public void setChanelView(String chanelView) {
        this.chanelView = chanelView;
    }

    public String getChanelView() {
        return chanelView;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCpCode() {
        return cpCode;
    }

    public String getCpName() {
        return cpName;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Instant getTransTime() {
        return transTime;
    }

    public void setTransTime(Instant transTime) {
        this.transTime = transTime;
    }

    public Long getChanel() {
        return chanel;
    }

    public void setChanel(Long chanel) {
        this.chanel = chanel;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTransNote() {
        return transNote;
    }

    public void setTransNote(String transNote) {
        this.transNote = transNote;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getProcess() {
        return process;
    }

    public void setProcess(Long process) {
        this.process = process;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public Long getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Long balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Long getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransLogDTO)) {
            return false;
        }

        return transId != null && transId.equals(((TransLogDTO) o).transId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransLogDTO{" +
            " transId=" + getTransId() +
            ", cpId=" + getCpId() +
            ", transTime='" + getTransTime() + "'" +
            ", chanel=" + getChanel() +
            ", amount=" + getAmount() +
            ", transNote='" + getTransNote() + "'" +
            ", alias='" + getAlias() + "'" +
            ", process=" + getProcess() +
            ", balanceType=" + getBalanceType() +
            ", balanceBefore=" + getBalanceBefore() +
            ", balanceAfter=" + getBalanceAfter() +
            "}";
    }
}
