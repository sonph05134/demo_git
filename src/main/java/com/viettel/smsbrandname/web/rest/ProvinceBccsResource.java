package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.service.ProvinceBccsService;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.ProvinceBccsDTO;

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
 * REST controller for managing {@link com.viettel.smsbrandname.domain.ProvinceBccs}.
 */
@RestController
@RequestMapping("/api")
public class ProvinceBccsResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceBccsResource.class);

    private static final String ENTITY_NAME = "provinceBccs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvinceBccsService provinceBccsService;

    public ProvinceBccsResource(ProvinceBccsService provinceBccsService) {
        this.provinceBccsService = provinceBccsService;
    }

    /**
     * {@code POST  /province-bccs} : Create a new provinceBccs.
     *
     * @param provinceBccsDTO the provinceBccsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provinceBccsDTO, or with status {@code 400 (Bad Request)} if the provinceBccs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/province-bccs")
    public ResponseEntity<ProvinceBccsDTO> createProvinceBccs(@RequestBody ProvinceBccsDTO provinceBccsDTO) throws URISyntaxException {
        log.debug("REST request to save ProvinceBccs : {}", provinceBccsDTO);
        if (provinceBccsDTO.getId() != null) {
            throw new BadRequestAlertException("A new provinceBccs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvinceBccsDTO result = provinceBccsService.save(provinceBccsDTO);
        return ResponseEntity.created(new URI("/api/province-bccs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /province-bccs} : Updates an existing provinceBccs.
     *
     * @param provinceBccsDTO the provinceBccsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provinceBccsDTO,
     * or with status {@code 400 (Bad Request)} if the provinceBccsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provinceBccsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/province-bccs")
    public ResponseEntity<ProvinceBccsDTO> updateProvinceBccs(@RequestBody ProvinceBccsDTO provinceBccsDTO) throws URISyntaxException {
        log.debug("REST request to update ProvinceBccs : {}", provinceBccsDTO);
        if (provinceBccsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProvinceBccsDTO result = provinceBccsService.save(provinceBccsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provinceBccsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /province-bccs} : get all the provinceBccs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provinceBccs in body.
     */
    @GetMapping("/province-bccs")
    public ResponseEntity<List<ProvinceBccsDTO>> getAllProvinceBccs(Pageable pageable) {
        log.debug("REST request to get a page of ProvinceBccs");
        Page<ProvinceBccsDTO> page = provinceBccsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/province-bccs/all")
    public ResponseEntity<List<ProvinceBccsDTO>> getAllProvinceBccs() {
        log.debug("REST request to get a page of ProvinceBccs");
        List<ProvinceBccsDTO> list = provinceBccsService.findAll();
        HttpHeaders headers = HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, applicationName);
        return ResponseEntity.ok().headers(headers).body(list);
    }

    /**
     * {@code GET  /province-bccs/:id} : get the "id" provinceBccs.
     *
     * @param id the id of the provinceBccsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provinceBccsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/province-bccs/{id}")
    public ResponseEntity<ProvinceBccsDTO> getProvinceBccs(@PathVariable Long id) {
        log.debug("REST request to get ProvinceBccs : {}", id);
        Optional<ProvinceBccsDTO> provinceBccsDTO = provinceBccsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provinceBccsDTO);
    }

    /**
     * {@code DELETE  /province-bccs/:id} : delete the "id" provinceBccs.
     *
     * @param id the id of the provinceBccsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/province-bccs/{id}")
    public ResponseEntity<Void> deleteProvinceBccs(@PathVariable Long id) {
        log.debug("REST request to delete ProvinceBccs : {}", id);
        provinceBccsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
