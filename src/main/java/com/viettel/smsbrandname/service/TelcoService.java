package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.TelcoDTO;

import com.viettel.smsbrandname.domain.Telco;
import com.viettel.smsbrandname.service.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TelcoService {

    Page<Telco> getAll(PageDTO pageDTO);

    void save(Telco telco);

    void delete(Long id);

    /**
     * Save a telco.
     *
     * @param telcoDTO the entity to save.
     * @return the persisted entity.
     */
    TelcoDTO save(TelcoDTO telcoDTO);

    /**
     * Get all the telcos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TelcoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" telco.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelcoDTO> findOne(Long id);


    /**
     * Search for the telco corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TelcoDTO> search(String query, Pageable pageable);

    List<TelcoDTO> searchAll();
}
