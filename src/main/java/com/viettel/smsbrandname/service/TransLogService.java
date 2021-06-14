package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.MtSearchDTO;
import com.viettel.smsbrandname.service.dto.TransLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.TransLog}.
 */
public interface TransLogService {

    /**
     * Save a transLog.
     *
     * @param transLogDTO the entity to save.
     * @return the persisted entity.
     */
    TransLogDTO save(TransLogDTO transLogDTO);

    /**
     * Get all the transLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransLogDTO> findOne(Long id);

    /**
     * Delete the "id" transLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the transLog corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransLogDTO> search(String query, Pageable pageable);

    Page<TransLogDTO> searchHasfilter(String cpCode, Long chanel, String fromDate, String toDate, String currency, Pageable pageable);

    ByteArrayInputStream export(String cpCode, Long chanel, String fromDate, String toDate, String currency);
}
