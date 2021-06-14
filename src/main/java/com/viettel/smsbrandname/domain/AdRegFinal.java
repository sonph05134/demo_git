package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AdRegFinal.
 */
@Entity
@Table(name = "ad_reg_final")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdRegFinal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_reg_final_seq")
    @SequenceGenerator(name = "ad_reg_final_seq", sequenceName = "ad_reg_final_seq", allocationSize = 1)
    private Long id;

    @Column(name = "msisdn")
    private Long msisdn;

    @Column(name = "insert_time")
    private Instant insertTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public AdRegFinal msisdn(Long msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Instant getInsertTime() {
        return insertTime;
    }

    public AdRegFinal insertTime(Instant insertTime) {
        this.insertTime = insertTime;
        return this;
    }

    public void setInsertTime(Instant insertTime) {
        this.insertTime = insertTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdRegFinal)) {
            return false;
        }
        return id != null && id.equals(((AdRegFinal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdRegFinal{" +
            "id=" + getId() +
            ", msisdn='" + getMsisdn() + "'" +
            ", insertTime='" + getInsertTime() + "'" +
            "}";
    }
}
