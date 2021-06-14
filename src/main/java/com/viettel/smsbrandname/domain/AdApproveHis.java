package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name ="ad_approve_his")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdApproveHis implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @SequenceGenerator(name = "adApproveHisSeq", sequenceName = "ad_approve_his_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adApproveHisSeq")
    private Long id;
    @Column(name="approver")
    private String approver;
    @Column(name="state_before")
    private Integer stateBefore;
    @Column(name="state_after")
    private Integer stateAfter;
    @Column(name="time_change")
    private Instant timeChange;
    @Column(name="note")
    private String note;
    @Column(name="prog_id")
    private Long progId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Integer getStateBefore() {
        return stateBefore;
    }

    public void setStateBefore(Integer stateBefore) {
        this.stateBefore = stateBefore;
    }

    public Integer getStateAfter() {
        return stateAfter;
    }

    public void setStateAfter(Integer stateAfter) {
        this.stateAfter = stateAfter;
    }

    public Instant getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(Instant timeChange) {
        this.timeChange = timeChange;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getProgId() {
        return progId;
    }

    public void setProgId(Long progId) {
        this.progId = progId;
    }
}
