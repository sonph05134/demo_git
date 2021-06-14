package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.AdRegFinal;
import com.viettel.smsbrandname.service.AdRegFinalService;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.AdRegFinalDTO;

import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.AdRegFinal}.
 */
@RestController
@RequestMapping("/api/ad-reg-final")
public class AdRegFinalResource {

    private final Logger log = LoggerFactory.getLogger(AdRegFinalResource.class);

    private static final String ENTITY_NAME = "adRegFinal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdRegFinalService adRegFinalService;

    private final ResponseFactory iResponseFactory;

    public AdRegFinalResource(AdRegFinalService adRegFinalService, ResponseFactory iResponseFactory) {
        this.adRegFinalService = adRegFinalService;
        this.iResponseFactory = iResponseFactory;
    }

    /**
     * {@code POST  /ad-reg-finals} : Create a new adRegFinal.
     *
     * @param adRegFinalDTO the adRegFinalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adRegFinalDTO, or with status {@code 400 (Bad Request)} if the adRegFinal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createAdRegFinal(@RequestBody AdRegFinalDTO adRegFinalDTO) {
        log.debug("REST request to save AdRegFinal : {}", adRegFinalDTO);
        if (adRegFinalService.findByMsisdn(adRegFinalDTO.getMsisdn()).isPresent())
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "msisdn");
        AdRegFinalDTO result = adRegFinalService.save(adRegFinalDTO);
        return iResponseFactory.success(result, AdRegFinalDTO.class);
    }

    /**
     * {@code PUT  /ad-reg-finals} : Updates an existing adRegFinal.
     *
     * @param adRegFinalDTO the adRegFinalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adRegFinalDTO,
     * or with status {@code 400 (Bad Request)} if the adRegFinalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adRegFinalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateAdRegFinal(@RequestBody AdRegFinalDTO adRegFinalDTO) {
        log.debug("REST request to update AdRegFinal : {}", adRegFinalDTO);
        Optional<AdRegFinal> optional = adRegFinalService.findByMsisdn(adRegFinalDTO.getMsisdn());
        if (optional.isPresent() && !optional.get().getId().equals(adRegFinalDTO.getId()))
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "msisdn");
        AdRegFinalDTO result = adRegFinalService.save(adRegFinalDTO);
        return iResponseFactory.success(result, AdRegFinalDTO.class);
    }

    /**
     * {@code GET  /ad-reg-finals} : get all the adRegFinals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adRegFinals in body.
     */
    @GetMapping("/search")
    public ResponseEntity<Object> getAllAdRegFinals(Long msisdn, Pageable pageable) {
        log.debug("REST request to get a page of AdRegFinals");
        CommonResponseDTO responseDTO = adRegFinalService.findAll(msisdn, pageable);
        return iResponseFactory.success(responseDTO, CommonResponseDTO.class);
    }

    /**
     * {@code GET  /ad-reg-finals/:id} : get the "id" adRegFinal.
     *
     * @param id the id of the adRegFinalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adRegFinalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<AdRegFinalDTO> getAdRegFinal(@PathVariable Long id) {
        log.debug("REST request to get AdRegFinal : {}", id);
        Optional<AdRegFinalDTO> adRegFinalDTO = adRegFinalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adRegFinalDTO);
    }

    /**
     * {@code DELETE  /ad-reg-finals/:id} : delete the "id" adRegFinal.
     *
     * @param id the id of the adRegFinalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAdRegFinal(@PathVariable Long id) {
        log.debug("REST request to delete AdRegFinal : {}", id);
        adRegFinalService.delete(id);
        return iResponseFactory.success(Object.class);
    }
}
