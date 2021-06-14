package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.repository.AdApproveHisRepository;
import com.viettel.smsbrandname.service.AdapproveHisService;
import com.viettel.smsbrandname.service.dto.AdApproveHisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AdapproveHisServiceImpl implements AdapproveHisService {
    @Autowired
    AdApproveHisRepository adApproveHisRepository;
    @Override
    public Page<AdApproveHisDTO> findAdApproveHisByProgId(AdApproveHisDTO adApproveHisDTO) {
        return adApproveHisRepository.findAdApproveHisByProgId(adApproveHisDTO);
    }
}
