package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.AdApproveHisDTO;
import org.springframework.data.domain.Page;


public interface AdapproveHisService {
    Page<AdApproveHisDTO> findAdApproveHisByProgId(AdApproveHisDTO adApproveHisDTO);
}
