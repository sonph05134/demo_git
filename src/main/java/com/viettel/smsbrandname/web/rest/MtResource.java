package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.service.MtService;
import com.viettel.smsbrandname.service.dto.MtDTO;
import com.viettel.smsbrandname.service.dto.MtSearchDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.Mt}.
 */
@RestController
@RequestMapping("/api")
public class MtResource extends StandardizeLogging {

    private final Logger log = LoggerFactory.getLogger(MtResource.class);

    private static final String ENTITY_NAME = "mt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MtService mtService;

    private final ResponseFactory responseFactory;

    public MtResource(MtService mtService, ResponseFactory responseFactory) {
        super(MtResource.class);
        this.mtService = mtService;
        this.responseFactory = responseFactory;
    }

    /**
     * {@code POST  /mts} : Create a new mt.
     *
     * @param mtDTO the mtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mtDTO, or with status {@code 400 (Bad Request)} if the mt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mts")
    public ResponseEntity<MtDTO> createMt(@RequestBody MtDTO mtDTO) throws URISyntaxException {
        log.debug("REST request to save Mt : {}", mtDTO);
        if (mtDTO.getMtId() != null) {
            throw new BadRequestAlertException("A new mt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MtDTO result = mtService.save(mtDTO);
        return ResponseEntity.created(new URI("/api/mts/" + result.getMtId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getMtId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mts} : Updates an existing mt.
     *
     * @param mtDTO the mtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mtDTO,
     * or with status {@code 400 (Bad Request)} if the mtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mts")
    public ResponseEntity<MtDTO> updateMt(@RequestBody MtDTO mtDTO) throws URISyntaxException {
        log.debug("REST request to update Mt : {}", mtDTO);
        if (mtDTO.getMtId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MtDTO result = mtService.save(mtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mtDTO.getMtId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mts} : get all the mts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mts in body.
     */
    @GetMapping("/mts")
    public ResponseEntity<List<MtDTO>> getAllMts(Pageable pageable) {
        log.debug("REST request to get a page of Mts");
        Page<MtDTO> page = mtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mts/:id} : get the "id" mt.
     *
     * @param id the id of the mtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mts/{id}")
    public ResponseEntity<MtDTO> getMt(@PathVariable Long id) {
        log.debug("REST request to get Mt : {}", id);
        Optional<MtDTO> mtDTO = mtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mtDTO);
    }

    /**
     * {@code DELETE  /mts/:id} : delete the "id" mt.
     *
     * @param id the id of the mtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mts/{id}")
    public ResponseEntity<Void> deleteMt(@PathVariable Long id) {
        log.debug("REST request to delete Mt : {}", id);
        mtService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/mts?query=:query} : search for the mt corresponding
     * to the query.
     *
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @PostMapping("/mts/_search")
    public ResponseEntity<Object> searchMts(@RequestBody MtSearchDTO dto, Pageable pageable) {
        Date date = new Date();
        Page<MtDTO> page;
        try {
            page = mtService.searchHasFilter(dto, pageable);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return responseFactory.success(page, Page.class);
    }

    @PostMapping("/mts/export")
    public ResponseEntity<?> export(@RequestBody MtSearchDTO dto) {
        Date date = new Date();
        ByteArrayInputStream result;
        try {
            result = mtService.export(dto);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return ResponseEntity.ok().body(new InputStreamResource(result));
    }
}
