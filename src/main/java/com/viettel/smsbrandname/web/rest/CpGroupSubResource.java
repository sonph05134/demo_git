package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.ExportUtils;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.CpGroupSubService;
import com.viettel.smsbrandname.service.dto.*;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.CpGroupSub}.
 */
@RestController
@RequestMapping("/api")
public class CpGroupSubResource {

    private final Logger log = LoggerFactory.getLogger(CpGroupSubResource.class);

    private static final String ENTITY_NAME = "cpGroupSub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExportUtils exportUtils;

    private final CpGroupSubService cpGroupSubService;

    public CpGroupSubResource(CpGroupSubService cpGroupSubService, ExportUtils exportUtils) {
        this.cpGroupSubService = cpGroupSubService;
        this.exportUtils = exportUtils;
    }

    /**
     * {@code POST  /cp-group-subs} : Create a new cpGroupSub.
     *
     * @param cpGroupSubDTO the cpGroupSubDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cpGroupSubDTO, or with status {@code 400 (Bad Request)} if the cpGroupSub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cp-group-subs")
    public ResponseEntity<CpGroupSubDTO> createCpGroupSub(@RequestBody CpGroupSubDTO cpGroupSubDTO) throws Exception {
        log.debug("REST request to save CpGroupSub : {}", cpGroupSubDTO);
        if (cpGroupSubDTO.getId() != null) {
            throw new BadRequestAlertException("A new cpGroupSub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CpGroupSubDTO result = cpGroupSubService.save(cpGroupSubDTO);
        return ResponseEntity.created(new URI("/api/cp-group-subs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cp-group-subs} : Updates an existing cpGroupSub.
     *
     * @param cpGroupSubDTO the cpGroupSubDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpGroupSubDTO,
     * or with status {@code 400 (Bad Request)} if the cpGroupSubDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cpGroupSubDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cp-group-subs")
    public ResponseEntity<CpGroupSubDTO> updateCpGroupSub(@RequestBody CpGroupSubDTO cpGroupSubDTO) throws Exception {
        log.debug("REST request to update CpGroupSub : {}", cpGroupSubDTO);
        if (cpGroupSubDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CpGroupSubDTO result = cpGroupSubService.save(cpGroupSubDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cpGroupSubDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cp-group-subs} : get all the cpGroupSubs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cpGroupSubs in body.
     */
    @GetMapping("/cp-group-subs")
    public ResponseEntity<List<CpGroupSubDTO>> getAllCpGroupSubs(Pageable pageable) {
        log.debug("REST request to get a page of CpGroupSubs");
        Page<CpGroupSubDTO> page = cpGroupSubService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cp-group-subs/:id} : get the "id" cpGroupSub.
     *
     * @param id the id of the cpGroupSubDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cpGroupSubDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cp-group-subs/{id}")
    public ResponseEntity<CpGroupSubDTO> getCpGroupSub(@PathVariable Long id) {
        log.debug("REST request to get CpGroupSub : {}", id);
        Optional<CpGroupSubDTO> cpGroupSubDTO = cpGroupSubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cpGroupSubDTO);
    }

    /**
     * {@code DELETE  /cp-group-subs/:id} : delete the "id" cpGroupSub.
     *
     * @param id the id of the cpGroupSubDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cp-group-subs/{id}")
    public ResponseEntity<Void> deleteCpGroupSub(@PathVariable Long id) {
        log.debug("REST request to delete CpGroupSub : {}", id);
        cpGroupSubService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/cp-group-subs/search")
    public ResponseEntity<List<CpGroupSubResultDTO>> onSearchCpGroupSubs(@RequestBody CpGroupSubDTO cpGroupSubDTO, Pageable pageable) {
        log.debug("REST request to search for a page of CpGroupSubs for query");
        Page<CpGroupSubResultDTO> page = cpGroupSubService.search(cpGroupSubDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/cp-group-subs/export")
    public ResponseEntity<Resource> onExportCpGroupSubs(@RequestBody CpGroupSubDTO cpGroupSubDTO) throws Exception {
        log.debug("REST request to search for a page of CpGroupSubs for query");
        List<CpGroupSubResultDTO> lstData = cpGroupSubService.onExport(cpGroupSubDTO);
        if(DataUtil.isNullOrEmpty(lstData)) {
            throw new BadRequestAlertException(Translator.toLocale("data.not.match"), ENTITY_NAME, "data.not.match");
        }
        List<ExcelColumn> lstColumn = cpGroupSubService.buildColumn();
        ExcelTitle excelTitle = new ExcelTitle(Translator.toLocale("cpGroupSub.export.title"), Translator.toLocale("cpGroupSub.export.dateExport"), "dd/MM/yyyy");
        ByteArrayInputStream streamResource = exportUtils.onExport(lstColumn, lstData, 3, 0, excelTitle, true);

        InputStreamResource resource = new InputStreamResource(streamResource);
        return ResponseEntity.ok()
            .header("filename", "Export.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

    @PostMapping(value = "/cp-group-subs/import-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> importCpGroupSub(@ModelAttribute CpGroupSubForm cpGroupSubForm) throws Exception {
        log.debug("REST request to importCpGroupSub : {}", cpGroupSubForm);
        if (DataUtil.isNullOrEmpty(cpGroupSubForm.getCpGroupId()) || Constants.DEFAULT_VALUE.equals(cpGroupSubForm.getCpGroupId())) {
            throw new BadRequestAlertException(Translator.toLocale("error.cpGroupSub.cpGroupId.required"), "CpGroupSub", "cpGroupSub.cpGroupId.required");
        }
        String fileName = cpGroupSubForm.getFileImport().getOriginalFilename();
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            throw new BadRequestAlertException(Translator.toLocale("error.cpGroupSub.fileImport.invalid"), "CpGroupSub", "cpGroupSub.fileImport.invalid");
        }
        ByteArrayInputStream file = cpGroupSubService.importCpGroupSub(cpGroupSubForm);
        InputStreamResource resources = new InputStreamResource(file);

        return ResponseEntity.ok()
            .header("filename", cpGroupSubForm.getFileResult())
            .header("message", cpGroupSubForm.getResult())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resources);
    }

    @GetMapping("/cp-group-subs/download-template")
    public ResponseEntity<Resource> downloadTemplate() throws Exception {
        List<CpGroupSubDTO> lstDatas = new ArrayList<>();
        CpGroupSubDTO dto = new CpGroupSubDTO();
        dto.setMsisdn("8498xxxxxxx");
        dto.setName("Nguyen van A");
        dto.setSexStr("Nam");
        dto.setBirthdayStr("20/11/1990");
        dto.setAddress("Dia chi 01");
        dto.setNote("Ghi chu 01");
        dto.setCode("123482");
        lstDatas.add(dto);
        List<ExcelColumn> lstColumn = cpGroupSubService.buildColumn();
        lstColumn.remove(0);

        String filename = "Template_XLS.xlsx";
        ByteArrayInputStream streamOutput = exportUtils.onExport(lstColumn, lstDatas, 0, 0, null, false);


        InputStreamResource resource = new InputStreamResource(streamOutput);
        return ResponseEntity.ok()
            .header("filename", filename)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

    @PutMapping("/cp-group-subs/delete-multi")
    public ResponseEntity<Void> deleteGroupSubResult(@RequestBody List<CpGroupSubDTO> lstCpGroupSub) {
        log.debug("REST request to search for a page of CpGroupSubs for query");
        this.cpGroupSubService.deleteAll(lstCpGroupSub);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, lstCpGroupSub.size() + "item")).build();
    }


}
