package com.viettel.smsbrandname.repository;

import com.viettel.smsbrandname.domain.AdPackage;
import com.viettel.smsbrandname.service.dto.AdPackageDTO;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdPackageCustomRepository {
    CommonResponseDTO search(String packageName, Integer type, Pageable pageable);

    Optional<AdPackage> findFirstByPackageNameAndStatus(String packageName);
//start sonph 10/04/2021
    Optional<AdPackage> findByNameAndType (String name,String type);
    Integer changeStatus(Long id,Integer status);
//end sonph 10/04/2021
}
