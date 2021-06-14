package com.viettel.smsbrandname.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.AdminMsisdn} entity.
 */
public class AdminMsisdnDTO implements Serializable {
    
    private Long id;

    private String userName;

    private String msisdn;

    private Instant insertTime;

    private Integer status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Instant getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Instant insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminMsisdnDTO)) {
            return false;
        }

        return id != null && id.equals(((AdminMsisdnDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminMsisdnDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", msisdn='" + getMsisdn() + "'" +
            ", insertTime='" + getInsertTime() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
