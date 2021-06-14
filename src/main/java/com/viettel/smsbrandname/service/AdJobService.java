package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.AdJob;
import com.viettel.smsbrandname.service.dto.AdJobDTO;

import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.AdJob}.
 */
public interface AdJobService {

    /**
     * Save a adJob.
     *
     * @param adJobDTO the entity to save.
     * @return the persisted entity.
     */
    AdJobDTO save(AdJobDTO adJobDTO);

    /**
     * Get all the adJobs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    CommonResponseDTO findAll(String jobName,Pageable pageable);


    /**
     * Get the "id" adJob.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdJobDTO> findOne(Long id);

    /**
     * Delete the "id" adJob.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<AdJob> findByName(String jobName);
}
