package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TransLog.
 */
@Entity
@Table(name = "trans_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "trans_id")
    private Long transId;

    @Column(name = "cp_id")
    private Long cpId;

    @Column(name = "trans_time")
    private Instant transTime;

    @Column(name = "chanel")
    private Long chanel;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "trans_note")
    private String transNote;

    @Column(name = "alias")
    private String alias;

    @Column(name = "process")
    private Long process;

    @Column(name = "balance_type")
    private Long balanceType;

    @Column(name = "balance_before")
    private Long balanceBefore;

    @Column(name = "balance_after")
    private Long balanceAfter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getTransId() {
        return transId;
    }

    public TransLog transId(Long transId) {
        this.transId = transId;
        return this;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getCpId() {
        return cpId;
    }

    public TransLog cpId(Long cpId) {
        this.cpId = cpId;
        return this;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public Instant getTransTime() {
        return transTime;
    }

    public TransLog transTime(Instant transTime) {
        this.transTime = transTime;
        return this;
    }

    public void setTransTime(Instant transTime) {
        this.transTime = transTime;
    }

    public Long getChanel() {
        return chanel;
    }

    public TransLog chanel(Long chanel) {
        this.chanel = chanel;
        return this;
    }

    public void setChanel(Long chanel) {
        this.chanel = chanel;
    }

    public Long getAmount() {
        return amount;
    }

    public TransLog amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTransNote() {
        return transNote;
    }

    public TransLog transNote(String transNote) {
        this.transNote = transNote;
        return this;
    }

    public void setTransNote(String transNote) {
        this.transNote = transNote;
    }

    public String getAlias() {
        return alias;
    }

    public TransLog alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getProcess() {
        return process;
    }

    public TransLog process(Long process) {
        this.process = process;
        return this;
    }

    public void setProcess(Long process) {
        this.process = process;
    }

    public Long getBalanceType() {
        return balanceType;
    }

    public TransLog balanceType(Long balanceType) {
        this.balanceType = balanceType;
        return this;
    }

    public void setBalanceType(Long balanceType) {
        this.balanceType = balanceType;
    }

    public Long getBalanceBefore() {
        return balanceBefore;
    }

    public TransLog balanceBefore(Long balanceBefore) {
        this.balanceBefore = balanceBefore;
        return this;
    }

    public void setBalanceBefore(Long balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Long getBalanceAfter() {
        return balanceAfter;
    }

    public TransLog balanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
        return this;
    }

    public void setBalanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransLog)) {
            return false;
        }
        return transId != null && transId.equals(((TransLog) o).transId);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransLog{" +
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
