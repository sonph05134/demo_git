package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.service.CpAliasService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.CpAliasDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.CpAlias}.
 */
@RestController
@RequestMapping("/api")
public class CpAliasResource {

    private final Logger log = LoggerFactory.getLogger(CpAliasResource.class);

    private static final String ENTITY_NAME = "cpAlias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CpAliasService cpAliasService;

    public CpAliasResource(CpAliasService cpAliasService) {
        this.cpAliasService = cpAliasService;
    }

    /**
     * {@code POST  /cp-aliases} : Create a new cpAlias.
     *
     * @param cpAliasDTO the cpAliasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cpAliasDTO, or with status {@code 400 (Bad Request)} if the cpAlias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cp-aliases")
    public ResponseEntity<CpAliasDTO> createCpAlias(@RequestBody CpAliasDTO cpAliasDTO) throws URISyntaxException {
        log.debug("REST request to save CpAlias : {}", cpAliasDTO);
        if (cpAliasDTO.getCpAliasId() != null) {
            throw new BadRequestAlertException("A new cpAlias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CpAliasDTO result = cpAliasService.save(cpAliasDTO);
        return ResponseEntity.created(new URI("/api/cp-aliases/" + result.getCpAliasId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCpAliasId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cp-aliases} : Updates an existing cpAlias.
     *
     * @param cpAliasDTO the cpAliasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpAliasDTO,
     * or with status {@code 400 (Bad Request)} if the cpAliasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cpAliasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cp-aliases")
    public ResponseEntity<CpAliasDTO> updateCpAlias(@RequestBody CpAliasDTO cpAliasDTO) throws URISyntaxException {
        log.debug("REST request to update CpAlias : {}", cpAliasDTO);
        if (cpAliasDTO.getCpAliasId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CpAliasDTO result = cpAliasService.save(cpAliasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cpAliasDTO.getCpAliasId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cp-aliases} : get all the cpAliases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cpAliases in body.
     */
    @GetMapping("/cp-aliases")
    public ResponseEntity<List<CpAliasDTO>> getAllCpAliases(Pageable pageable) {
        log.debug("REST request to get a page of CpAliases");
        Page<CpAliasDTO> page = cpAliasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cp-aliases/:id} : get the "id" cpAlias.
     *
     * @param id the id of the cpAliasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cpAliasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cp-aliases/{id}")
    public ResponseEntity<CpAliasDTO> getCpAlias(@PathVariable Long id) {
        log.debug("REST request to get CpAlias : {}", id);
        Optional<CpAliasDTO> cpAliasDTO = cpAliasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cpAliasDTO);
    }

    /**
     * {@code DELETE  /cp-aliases/:id} : delete the "id" cpAlias.
     *
     * @param id the id of the cpAliasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cp-aliases/{id}")
    public ResponseEntity<Void> deleteCpAlias(@PathVariable Long id) {
        log.debug("REST request to delete CpAlias : {}", id);
        cpAliasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/cp-aliases/search-brand-name")
    public ResponseEntity<List<ComboBean>> searchBrandName(Long cpId){
        List<ComboBean> result = cpAliasService.searchBrandName(cpId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
