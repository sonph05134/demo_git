package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.AdCategory;
import com.viettel.smsbrandname.service.dto.AdCategoryDTO;

import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.AdCategory}.
 */
public interface AdCategoryService {

    /**
     * Save a adCategory.
     *
     * @param adCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    AdCategoryDTO save(AdCategoryDTO adCategoryDTO) throws BusinessException;

    /**
     * Get all the adCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    CommonResponseDTO findAll(String catCode, String catName, Pageable pageable);


    /**
     * Get the "id" adCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" adCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ComboBean> findAdCategoryByRecycle();

    Optional<AdCategory> findByCode(String code);

    Optional<AdCategory> findByName(String name);
}
