package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.service.AdminMsisdnService;
import com.viettel.smsbrandname.service.VSAUserService;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.dto.VSAUserDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.AdminMsisdnDTO;

import com.viettel.smsbrandname.web.rest.errors.BusinessException;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.AdminMsisdn}.
 */
@RestController
@RequestMapping("/api/isdn")
public class AdminMsisdnResource extends StandardizeLogging {

    private final Logger log = LoggerFactory.getLogger(AdminMsisdnResource.class);

    private static final String ENTITY_NAME = "adminMsisdn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminMsisdnService adminMsisdnService;

    private final ResponseFactory iResponseFactory;

    private final VSAUserService vsaUserService;

    public AdminMsisdnResource(AdminMsisdnService adminMsisdnService, ResponseFactory iResponseFactory, VSAUserService vsaUserService) {
        // Start 01/04/2021
        super(AdminMsisdnResource.class);
        // End 01/04/2021
        this.adminMsisdnService = adminMsisdnService;
        this.iResponseFactory = iResponseFactory;
        this.vsaUserService = vsaUserService;
    }

    /**
     * {@code POST  /admin-msisdns} : Create a new adminMsisdn.
     *
     * @param adminMsisdnDTO the adminMsisdnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminMsisdnDTO, or with status {@code 400 (Bad Request)} if the adminMsisdn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createAdminMsisdn(@RequestBody AdminMsisdnDTO adminMsisdnDTO) {
        Date date = new Date();
        try {
            log.debug("REST request to save AdminMsisdn : {}", adminMsisdnDTO);
            // Start 01-04-2021
            AdminMsisdnDTO result = adminMsisdnService.save(adminMsisdnDTO);
            if(DataUtil.isNullObject(result)){
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,Constants.RESULT.ADD_FAIL_STR,date);
                return iResponseFactory.failBusiness(Constants.RESULT.ADD_FAIL_STR, "result.insert.fail");
            }
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS,Constants.RESULT.ADD_SUCCESS_STR,date);
            // End 01-04-2021
            return iResponseFactory.success(result, AdminMsisdnDTO.class);
        }catch (Exception e){
            if(e instanceof BusinessException){
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,((BusinessException) e).getErrorCode(),date);
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e,ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code PUT  /admin-msisdns} : Updates an existing adminMsisdn.
     *
     * @param adminMsisdnDTO the adminMsisdnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminMsisdnDTO,
     * or with status {@code 400 (Bad Request)} if the adminMsisdnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminMsisdnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateAdminMsisdn(@RequestBody AdminMsisdnDTO adminMsisdnDTO) {
        log.debug("REST request to update AdminMsisdn : {}", adminMsisdnDTO);
        Date date = new Date();
        try {
            AdminMsisdnDTO result = adminMsisdnService.save(adminMsisdnDTO);
            if(DataUtil.isNullObject(result)){
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,Constants.RESULT.EDIT_FAIL_STR,date);
                return iResponseFactory.failBusiness(Constants.RESULT.ADD_FAIL_STR, "result.update.fail");
            }
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, Constants.RESULT.EDIT_SUCCESS_STR, date);
            return iResponseFactory.success(result, AdminMsisdnDTO.class);
        }catch (BusinessException be){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,be.getErrorCode(),date);
            return iResponseFactory.failBusiness(be.getErrorCode(), be.getMessage());
        }catch(Exception ex){
            writeErrorLog(ex,ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw ex;
        }
    }

    /**
     * {@code GET  /admin-msisdns} : get all the adminMsisdns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminMsisdns in body.
     */
    @GetMapping("/search")
    public ResponseEntity<Object> getAllAdminMsisdns(String userName, String msisdn, Pageable pageable) {
        Date date = new Date();
        try {
            log.debug("REST request to get a page of AdminMsisdns");
            CommonResponseDTO responseDTO = adminMsisdnService.findAll(userName, msisdn, pageable);
            return iResponseFactory.success(responseDTO, CommonResponseDTO.class);
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    /**
     * {@code GET  /admin-msisdns/:id} : get the "id" adminMsisdn.
     *
     * @param id the id of the adminMsisdnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminMsisdnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<AdminMsisdnDTO> getAdminMsisdn(@PathVariable Long id) {
        Date date = new Date();
        try {
        log.debug("REST request to get AdminMsisdn : {}", id);
        Optional<AdminMsisdnDTO> adminMsisdnDTO = adminMsisdnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminMsisdnDTO);
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    /**
     * {@code DELETE  /admin-msisdns/:id} : delete the "id" adminMsisdn.
     *
     * @param id the id of the adminMsisdnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdminMsisdn(@PathVariable Long id) {
        Date date = new Date();
        try {
            log.debug("REST request to delete AdminMsisdn : {}", id);
            adminMsisdnService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, Constants.RESULT.DEL_SUCCESS_STR, date);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("/find-all-username")
    public ResponseEntity<Object> findAllUsername() {
        Date date = new Date();
        try {
            log.debug("REST request to findAllUsername method ");
            List<VSAUserDTO> dtoList = vsaUserService.findAll();
            return iResponseFactory.success(dtoList, VSAUserDTO.class);
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
