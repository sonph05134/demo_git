package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.AdRegFinal;
import com.viettel.smsbrandname.service.dto.AdRegFinalDTO;

import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.AdRegFinal}.
 */
public interface AdRegFinalService {

    /**
     * Save a adRegFinal.
     *
     * @param adRegFinalDTO the entity to save.
     * @return the persisted entity.
     */
    AdRegFinalDTO save(AdRegFinalDTO adRegFinalDTO);

    /**
     * Get all the adRegFinals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    CommonResponseDTO findAll(Long msisdn,Pageable pageable);


    /**
     * Get the "id" adRegFinal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdRegFinalDTO> findOne(Long id);

    /**
     * Delete the "id" adRegFinal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<AdRegFinal> findByMsisdn(Long msisdn);
}
