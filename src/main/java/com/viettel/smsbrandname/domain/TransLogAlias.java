/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.smsbrandname.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 *
 * @author duonglt16
 */
@Entity
@Table(name = "TRANS_LOG_ALIAS")
public class TransLogAlias {

    private Long cpId;
    private Long transLogAliasId;
    private Date transTime;
    private BigDecimal amount;
    private String alias;
    private int aliasType;
    private long process;
    private long progId;

    @Column(name = "PROCESS")
    public long getProcess() {
        return process;
    }


    public void setProcess(long process) {
        this.process = process;
    }

    @Column(name = "PROG_ID")
    public long getProgId() {
        return progId;
    }

    public void setProgId(long progId) {
        this.progId = progId;
    }

    @Column(name = "ALIAS", length = 100)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    @Column(name = "CP_ID", precision = 22, scale = 0)
    public Long getCpId() {
        return cpId;
    }


    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    @SequenceGenerator(name = "generator", sequenceName = "TRANS_LOG_ALIAS_ID_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "generator")
    @Column(name = "TRANS_LOG_ALIAS_ID", unique = true, nullable = false, precision = 22, scale = 0)

    public Long getTransLogAliasId() {
        return transLogAliasId;
    }

    public void setTransLogAliasId(Long transLogAliasId) {
        this.transLogAliasId = transLogAliasId;
    }


    @Column(name = "TRANS_TIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    @Column(name = "AMOUNT", precision = 22, scale = 0)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "ALIAS_TYPE", precision = 22, scale = 0)
    public int getAliasType() {
        return aliasType;
    }

    public void setAliasType(int aliasType) {
        this.aliasType = aliasType;
    }

}
