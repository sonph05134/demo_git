package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.AdPackage;
import com.viettel.smsbrandname.service.dto.AdPackageDTO;

import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.AdPackage}.
 */
public interface AdPackageService {

    /**
     * Save a adPackage.
     *
     * @param adPackageDTO the entity to save.
     * @return the persisted entity.
     */
    AdPackageDTO save(AdPackageDTO adPackageDTO);

    /**
     * Get all the adPackages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    CommonResponseDTO findAll(String packageName, Integer type, Pageable pageable);


    /**
     * Get the "id" adPackage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdPackageDTO> findOne(Long id);

    /**
     * Delete the "id" adPackage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<AdPackage> findByName(String packageName);
//start sonph 10/04/2021
    Optional<AdPackage> findByNameAndType(String name,String type);

    Integer changeStatus(Long id,Integer status);
//end sonph 10/04/2021
}
