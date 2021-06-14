package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.MaintainFee;
import com.viettel.smsbrandname.service.dto.MaintainFeeDTO;
import org.springframework.data.domain.Page;

public interface MaintainFeeService {

    Page<MaintainFee> search(MaintainFeeDTO maintainFee);

    void save(MaintainFeeDTO maintainFee);

    void delete(Long id);
}
