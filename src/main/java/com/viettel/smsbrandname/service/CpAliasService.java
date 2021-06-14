package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.CpAlias}.
 */
public interface CpAliasService {

    /**
     * Save a cpAlias.
     *
     * @param cpAliasDTO the entity to save.
     * @return the persisted entity.
     */
    CpAliasDTO save(CpAliasDTO cpAliasDTO);

    /**
     * Get all the cpAliases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CpAliasDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cpAlias.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CpAliasDTO> findOne(Long id);

    /**
     * Delete the "id" cpAlias.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ComboBean> searchBrandName(Long cpId);
}
