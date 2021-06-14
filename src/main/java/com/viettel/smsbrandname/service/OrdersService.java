package com.viettel.smsbrandname.service;

import com.viettel.smsbrandname.domain.Orders;
import com.viettel.smsbrandname.service.dto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * Service Interface for managing {@link Orders}.
 */
public interface OrdersService {

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    OrderDTO save(OrderDTO orderDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDTO> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the order corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */

    Page<OrderResultQueryDTO> search(SearchListOrderParam searchListOrderParam, Pageable pageable);
    ByteArrayInputStream export(SearchListOrderParam searchListOrderParam);
    OrderDetailDTO searchDetail(Long id);
}
