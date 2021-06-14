package com.viettel.smsbrandname.domain;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Autogen class Entity: Create Entity For Table Name Cfg_filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:26 ICT 2020
 */
@Entity
@Table(name = "CFG_FILTER")
public class CfgFilterEntity implements Serializable{

    @Id
    @GeneratedValue(generator = "CFG_FILTER_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CFG_FILTER_SEQ_GEN", sequenceName = "CFG_FILTER_SEQ", allocationSize = 1)
    @Column(name = "CFG_FILTER_ID")
    private Long cfgFilterId;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "STATUS")
    private Long status;

    public Long getCfgFilterId() {
        return cfgFilterId;
    }

    public void setCfgFilterId(Long cfgFilterId) {
        this.cfgFilterId = cfgFilterId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
