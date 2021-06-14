package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.CpGroupDTO;

import com.viettel.smsbrandname.service.dto.CpGroupDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.CpGroup}.
 */
public interface CpGroupService {

    /**
     * Save a cpGroup.
     *
     * @param cpGroupDTO the entity to save.
     * @return the persisted entity.
     */
    CpGroupDTO save(CpGroupDTO cpGroupDTO);

    /**
     * Get all the cpGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CpGroupDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cpGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CpGroupDTO> findOne(Long id);

    /**
     * Delete the "id" cpGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id, Long cpId);

    /**
     * Search for the cpGroup corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CpGroupDTO> search(String query, Pageable pageable);

    Page<CpGroupDetailDTO> onSearch(CpGroupDTO cpGroupDTO, Pageable pageable);
}
