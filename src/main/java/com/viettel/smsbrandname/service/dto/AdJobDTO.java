package com.viettel.smsbrandname.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.viettel.smsbrandname.domain.AdJob} entity.
 */
public class AdJobDTO implements Serializable {
    
    private Long id;

    private String jobName;

    private Integer status;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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
        if (!(o instanceof AdJobDTO)) {
            return false;
        }

        return id != null && id.equals(((AdJobDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdJobDTO{" +
            "id=" + getId() +
            ", jobName='" + getJobName() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
