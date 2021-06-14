package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.CfgFilterEntity;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdCategoryCustomRepository {
    CommonResponseDTO search(String catCode, String catName, Pageable pageable);

    List<ComboBean> findAdCategoryByRecycle();
}
