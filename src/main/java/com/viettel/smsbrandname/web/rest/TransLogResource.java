package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.service.TransLogService;
import com.viettel.smsbrandname.service.dto.MtSearchDTO;
import com.viettel.smsbrandname.service.dto.TransLogSearchDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.TransLogDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.TransLog}.
 */
@RestController
@RequestMapping("/api")
public class TransLogResource {

    private final Logger log = LoggerFactory.getLogger(TransLogResource.class);

    private static final String ENTITY_NAME = "transLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransLogService transLogService;

    public TransLogResource(TransLogService transLogService) {
        this.transLogService = transLogService;
    }

    /**
     * {@code POST  /trans-logs} : Create a new transLog.
     *
     * @param transLogDTO the transLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transLogDTO, or with status {@code 400 (Bad Request)} if the transLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trans-logs")
    public ResponseEntity<TransLogDTO> createTransLog(@RequestBody TransLogDTO transLogDTO) throws URISyntaxException {
        log.debug("REST request to save TransLog : {}", transLogDTO);
        if (transLogDTO.getTransId() != null) {
            throw new BadRequestAlertException("A new transLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransLogDTO result = transLogService.save(transLogDTO);
        return ResponseEntity.created(new URI("/api/trans-logs/" + result.getTransId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTransId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trans-logs} : Updates an existing transLog.
     *
     * @param transLogDTO the transLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transLogDTO,
     * or with status {@code 400 (Bad Request)} if the transLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trans-logs")
    public ResponseEntity<TransLogDTO> updateTransLog(@RequestBody TransLogDTO transLogDTO) throws URISyntaxException {
        log.debug("REST request to update TransLog : {}", transLogDTO);
        if (transLogDTO.getTransId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransLogDTO result = transLogService.save(transLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transLogDTO.getTransId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trans-logs} : get all the transLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transLogs in body.
     */
    @GetMapping("/trans-logs")
    public ResponseEntity<List<TransLogDTO>> getAllTransLogs(Pageable pageable) {
        log.debug("REST request to get a page of TransLogs");
        Page<TransLogDTO> page = transLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trans-logs/:id} : get the "id" transLog.
     *
     * @param id the id of the transLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trans-logs/{id}")
    public ResponseEntity<TransLogDTO> getTransLog(@PathVariable Long id) {
        log.debug("REST request to get TransLog : {}", id);
        Optional<TransLogDTO> transLogDTO = transLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transLogDTO);
    }

    /**
     * {@code DELETE  /trans-logs/:id} : delete the "id" transLog.
     *
     * @param id the id of the transLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trans-logs/{id}")
    public ResponseEntity<Void> deleteTransLog(@PathVariable Long id) {
        log.debug("REST request to delete TransLog : {}", id);
        transLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/trans-logs?query=:query} : search for the transLog corresponding
     * to the query.
     *
     * @param query    the query of the transLog search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/trans-logs")
    public ResponseEntity<List<TransLogDTO>> searchTransLogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TransLogs for query {}", query);
        Page<TransLogDTO> page = transLogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/trans-logs/search")
    public ResponseEntity<Page<TransLogDTO>> searchTransLogs(@RequestBody TransLogSearchDTO dto,
                                                             Pageable pageable) {
        log.debug("REST request to search for a page of TransLogSearchDTO for query {}", dto);
        Page<TransLogDTO> result = transLogService.searchHasfilter(dto.getCpCode(), dto.getChanel(), dto.getFromDate(), dto.getToDate(), dto.getCurrency(), pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        //return ResponseEntity.ok().headers(headers).body(result.getContent());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/trans-logs/export")
    public ResponseEntity<?> export(@RequestBody TransLogSearchDTO dto) {
        ByteArrayInputStream result = transLogService.export(dto.getCpCode(), dto.getChanel(), dto.getFromDate(), dto.getToDate(), dto.getCurrency());
        return ResponseEntity.ok().body(new InputStreamResource(result));

    }
}
