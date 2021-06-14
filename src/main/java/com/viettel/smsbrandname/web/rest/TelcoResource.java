package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.Partner;
import com.viettel.smsbrandname.domain.Telco;
import com.viettel.smsbrandname.service.TelcoService;
import com.viettel.smsbrandname.service.dto.PageDTO;
import com.viettel.smsbrandname.service.dto.TelcoDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.http.auth.BasicUserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/telco")
public class TelcoResource extends StandardizeLogging {
    private final Logger log = LoggerFactory.getLogger(TelcoResource.class);

    private static final String ENTITY_NAME = "telco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private TelcoService telcoService;


    @Autowired
    private ResponseFactory iResponseFactory;

    public TelcoResource() {
        super(TelcoResource.class);
    }

    @PostMapping("search")
    public ResponseEntity<Object> search(@RequestBody PageDTO page) {
        Date date = new Date();
        try {
            return iResponseFactory.success(telcoService.getAll(page), Page.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody Telco telco) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(telco.getId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        try {
            telcoService.save(telco);
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertSuccess", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "updateSuccess", date);
            }
            return iResponseFactory.success(telco, Object.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("delete")
    public ResponseEntity<Object> delete(@RequestParam Long id) {
        Date date = new Date();
        try {
            telcoService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
            return iResponseFactory.success(id, Long.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    /**
     * {@code POST  /telcos} : Create a new telco.
     *
     * @param telcoDTO the telcoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telcoDTO, or with status {@code 400 (Bad Request)} if the telco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telcos")
    public ResponseEntity<TelcoDTO> createTelco(@RequestBody TelcoDTO telcoDTO) throws URISyntaxException {
        log.debug("REST request to save Telco : {}", telcoDTO);
        if (telcoDTO.getTelcoId() != null) {
            throw new BadRequestAlertException("A new telco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelcoDTO result = telcoService.save(telcoDTO);
        return ResponseEntity.created(new URI("/api/telcos/" + result.getTelcoId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getTelcoId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telcos} : Updates an existing telco.
     *
     * @param telcoDTO the telcoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telcoDTO,
     * or with status {@code 400 (Bad Request)} if the telcoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telcoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telcos")
    public ResponseEntity<TelcoDTO> updateTelco(@RequestBody TelcoDTO telcoDTO) throws URISyntaxException {
        log.debug("REST request to update Telco : {}", telcoDTO);
        if (telcoDTO.getTelcoId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TelcoDTO result = telcoService.save(telcoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telcoDTO.getTelcoId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /telcos} : get all the telcos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telcos in body.
     */
    @GetMapping("/telcos")
    public ResponseEntity<List<TelcoDTO>> getAllTelcos(Pageable pageable) {
        log.debug("REST request to get a page of Telcos");
        Page<TelcoDTO> page = telcoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /telcos/:id} : get the "id" telco.
     *
     * @param id the id of the telcoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telcoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telcos/{id}")
    public ResponseEntity<TelcoDTO> getTelco(@PathVariable Long id) {
        log.debug("REST request to get Telco : {}", id);
        Optional<TelcoDTO> telcoDTO = telcoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telcoDTO);
    }

    /**
     * {@code DELETE  /telcos/:id} : delete the "id" telco.
     *
     * @param id the id of the telcoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telcos/{id}")
    public ResponseEntity<Void> deleteTelco(@PathVariable Long id) {
        log.debug("REST request to delete Telco : {}", id);
        telcoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/telcos?query=:query} : search for the telco corresponding
     * to the query.
     *
     * @param query the query of the telco search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/telcos")
    public ResponseEntity<List<TelcoDTO>> searchTelcos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Telcos for query {}", query);
        Page<TelcoDTO> page = telcoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("telcos/search-all")
    public ResponseEntity<List<TelcoDTO>> searchBrandName(){
        List<TelcoDTO> result = telcoService.searchAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
