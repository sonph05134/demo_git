package com.viettel.smsbrandname.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AdJob.
 */
@Entity
@Table(name = "ad_job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_job_seq")
    @SequenceGenerator(name = "ad_job_seq", sequenceName = "ad_job_seq", allocationSize = 1)
    private Long id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public AdJob jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getStatus() {
        return status;
    }

    public AdJob status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdJob)) {
            return false;
        }
        return id != null && id.equals(((AdJob) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdJob{" +
            "id=" + getId() +
            ", jobName='" + getJobName() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
