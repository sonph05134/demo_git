package com.viettel.smsbrandname.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.AdRegFinal} entity.
 */
public class AdRegFinalDTO implements Serializable {

    private Long id;

    private Long msisdn;

    private Instant insertTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Instant getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Instant insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdRegFinalDTO)) {
            return false;
        }

        return id != null && id.equals(((AdRegFinalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdRegFinalDTO{" +
            "id=" + getId() +
            ", msisdn='" + getMsisdn() + "'" +
            ", insertTime='" + getInsertTime() + "'" +
            "}";
    }
}
