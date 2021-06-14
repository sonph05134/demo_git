package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.AdJob;
import com.viettel.smsbrandname.service.AdJobService;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.AdJobDTO;

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
 * REST controller for managing {@link com.viettel.smsbrandname.domain.AdJob}.
 */
@RestController
@RequestMapping("/api/job")
public class AdJobResource {

    private final Logger log = LoggerFactory.getLogger(AdJobResource.class);

    private static final String ENTITY_NAME = "adJob";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdJobService adJobService;

    private final ResponseFactory iResponseFactory;

    public AdJobResource(AdJobService adJobService, ResponseFactory iResponseFactory) {
        this.adJobService = adJobService;
        this.iResponseFactory = iResponseFactory;
    }

    /**
     * {@code POST  /ad-jobs} : Create a new adJob.
     *
     * @param adJobDTO the adJobDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adJobDTO, or with status {@code 400 (Bad Request)} if the adJob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createAdJob(@RequestBody AdJobDTO adJobDTO) {
        log.debug("REST request to save AdJob : {}", adJobDTO);
        if (adJobService.findByName(adJobDTO.getJobName()).isPresent())
            return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "jobName");
        AdJobDTO result = adJobService.save(adJobDTO);
        return iResponseFactory.success(result, AdJobDTO.class);
    }

    /**
     * {@code PUT  /ad-jobs} : Updates an existing adJob.
     *
     * @param adJobDTO the adJobDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adJobDTO,
     * or with status {@code 400 (Bad Request)} if the adJobDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adJobDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateAdJob(@RequestBody AdJobDTO adJobDTO) {
        log.debug("REST request to update AdJob : {}", adJobDTO);
        Optional<AdJob> optional = adJobService.findByName(adJobDTO.getJobName());
        if (0 != adJobDTO.getStatus()) {
            if (optional.isPresent() && !optional.get().getId().equals(adJobDTO.getId()))
                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "jobName");
        }
        AdJobDTO result = adJobService.save(adJobDTO);
        return iResponseFactory.success(result, AdJobDTO.class);
    }

    /**
     * {@code GET  /ad-jobs} : get all the adJobs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adJobs in body.
     */
    @GetMapping("/search")
    public ResponseEntity<Object> getAllAdJobs(String jobName, Pageable pageable) {
        log.debug("REST request to get a page of AdJobs");
        CommonResponseDTO responseDTO = adJobService.findAll(jobName, pageable);
        return iResponseFactory.success(responseDTO, CommonResponseDTO.class);
    }

    /**
     * {@code GET  /ad-jobs/:id} : get the "id" adJob.
     *
     * @param id the id of the adJobDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adJobDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<AdJobDTO> getAdJob(@PathVariable Long id) {
        log.debug("REST request to get AdJob : {}", id);
        Optional<AdJobDTO> adJobDTO = adJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adJobDTO);
    }

    /**
     * {@code DELETE  /ad-jobs/:id} : delete the "id" adJob.
     *
     * @param id the id of the adJobDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAdJob(@PathVariable Long id) {
        log.debug("REST request to delete AdJob : {}", id);
        adJobService.delete(id);
        return iResponseFactory.success(Object.class);
    }
}
