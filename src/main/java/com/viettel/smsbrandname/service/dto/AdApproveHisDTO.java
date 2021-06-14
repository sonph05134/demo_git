package com.viettel.smsbrandname.service.dto;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.domain.AdApproveHis;

import javax.persistence.Column;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class AdApproveHisDTO extends PageDTO{
    private Long id;
    private String approver;
    private String stateBefore;
    private String stateAfter;
    private String timeChange;
    private String note;
    private Long progId;


    public  List<AdApproveHisDTO> toDto(List<Object[]> objects) {
        List<AdApproveHisDTO> result = new ArrayList<>();
        if (!DataUtil.isNullOrEmpty(objects)) {
            objects.stream().forEach(obj -> {
                int i = 0;
                AdApproveHisDTO dto = new AdApproveHisDTO();
                dto.setId(DataUtil.safeToLong(obj[i++]));
                dto.setApprover(DataUtil.safeToString(obj[i++]));
                dto.setStateBefore(DataUtil.safeToString(obj[i++]));
                dto.setStateAfter(DataUtil.safeToString(obj[i++]));
                dto.setTimeChange(DataUtil.safeToString(obj[i++]));
                dto.setNote(DataUtil.safeToString(obj[i++]));
                dto.setProgId(DataUtil.safeToLong(obj[i++]));
                result.add(dto);
            });
        }
        return result;
    }

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

    public String getStateBefore() {
        return stateBefore;
    }

    public void setStateBefore(String stateBefore) {
        this.stateBefore = stateBefore;
    }

    public String getStateAfter() {
        return stateAfter;
    }

    public void setStateAfter(String stateAfter) {
        this.stateAfter = stateAfter;
    }

    public String getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(String timeChange) {
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
