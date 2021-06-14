package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.service.dto.AdApproveHisDTO;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.List;



public interface AdApproveHisRepository {
    Page<AdApproveHisDTO> findAdApproveHisByProgId(AdApproveHisDTO adApproveHisDTO);
}
