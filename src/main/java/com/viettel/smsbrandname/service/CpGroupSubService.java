package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;

import com.viettel.smsbrandname.service.dto.CpGroupSubForm;
import com.viettel.smsbrandname.service.dto.CpGroupSubResultDTO;
import com.viettel.smsbrandname.service.dto.ExcelColumn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.CpGroupSub}.
 */
public interface CpGroupSubService {

    /**
     * Save a cpGroupSub.
     *
     * @param cpGroupSubDTO the entity to save.
     * @return the persisted entity.
     */
    CpGroupSubDTO save(CpGroupSubDTO cpGroupSubDTO) throws Exception;

    /**
     * Get all the cpGroupSubs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CpGroupSubDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cpGroupSub.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CpGroupSubDTO> findOne(Long id);

    /**
     * Delete the "id" cpGroupSub.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<CpGroupSubResultDTO> search(CpGroupSubDTO cpGroupSubDTO, Pageable pageable);

    List<CpGroupSubResultDTO> onExport(CpGroupSubDTO cpGroupSubDTO);

    ByteArrayInputStream importCpGroupSub(CpGroupSubForm cpGroupSubForm) throws Exception;

    List<ExcelColumn> buildColumn();

    void deleteAll(List<CpGroupSubDTO> lstGroupSub);
}
