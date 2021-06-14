package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.ProvinceBccs;
import com.viettel.smsbrandname.service.dto.ProvinceBccsDTO;

import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.ProvinceBccs}.
 */
public interface ProvinceBccsService {

    /**
     * Save a provinceBccs.
     *
     * @param provinceBccsDTO the entity to save.
     * @return the persisted entity.
     */
    ProvinceBccsDTO save(ProvinceBccsDTO provinceBccsDTO);

    /**
     * Get all the provinceBccs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvinceBccsDTO> findAll(Pageable pageable);

    List<ProvinceBccsDTO> findAll();
    /**
     * Get the "id" provinceBccs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvinceBccsDTO> findOne(Long id);

    /**
     * Delete the "id" provinceBccs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ApiResponseDTO onSearch(Long provinceId, Integer start, Integer limit, Double timeZone);

    List<ComboboxResponseDTO> getLstProvinceNotInProvinceBccs();

    void saveOrEdit(ProvinceBccs provinceBCCS, String username);

    void deleteBccs(ProvinceBccs provinceBCCS, String username);
}
