package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.service.dto.AdminMsisdnDTO;

import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.viettel.smsbrandname.domain.AdminMsisdn}.
 */
public interface AdminMsisdnService {

    /**
     * Save a adminMsisdn.
     *
     * @param adminMsisdnDTO the entity to save.
     * @return the persisted entity.
     */
    AdminMsisdnDTO save(AdminMsisdnDTO adminMsisdnDTO);
    /**
     * Get all the adminMsisdns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    CommonResponseDTO findAll(String userName, String msisdn, Pageable pageable);


    /**
     * Get the "id" adminMsisdn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdminMsisdnDTO> findOne(Long id);

    /**
     * Delete the "id" adminMsisdn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<String> getLstPrefix();

    Boolean checkExisted(String userName,Long id);
}
