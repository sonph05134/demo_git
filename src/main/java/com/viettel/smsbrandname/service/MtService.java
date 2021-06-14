package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.MtDTO;

import com.viettel.smsbrandname.service.dto.MtSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.Mt}.
 */
public interface MtService {

    /**
     * Save a mt.
     *
     * @param mtDTO the entity to save.
     * @return the persisted entity.
     */
    MtDTO save(MtDTO mtDTO);

    /**
     * Get all the mts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MtDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MtDTO> findOne(Long id);

    /**
     * Delete the "id" mt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the mt corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MtDTO> search(String query, Pageable pageable);

    Page<MtDTO> searchHasFilter(MtSearchDTO dto, Pageable pageable);

    ByteArrayInputStream export(MtSearchDTO dto);
}
