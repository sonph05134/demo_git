package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.AdPackage;
import com.viettel.smsbrandname.service.AdPackageService;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.AdPackageDTO;

import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.parameters.P;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

/**
 * REST controller for managing {@link com.viettel.smsbrandname.domain.AdPackage}.
 */
@RestController
@RequestMapping("/api/package")
public class AdPackageResource extends StandardizeLogging {

    private final Logger log = LoggerFactory.getLogger(AdPackageResource.class);

    private static final String ENTITY_NAME = "adPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdPackageService adPackageService;

    private final ResponseFactory iResponseFactory;

    public AdPackageResource(AdPackageService adPackageService, ResponseFactory iResponseFactory) {
        super(AdPackageResource.class);
        this.adPackageService = adPackageService;
        this.iResponseFactory = iResponseFactory;
    }

    /**
     * {@code POST  /ad-packages} : Create a new adPackage.
     *
     * @param adPackageDTO the adPackageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adPackageDTO, or with status {@code 400 (Bad Request)} if the adPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createAdPackage(@RequestBody AdPackageDTO adPackageDTO) {
        Date date = new Date();
        try {
            log.debug("REST request to save AdPackage : {}", adPackageDTO);
//            if (adPackageService.findByName(adPackageDTO.getPackageName()).isPresent())
//                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "packageName");
            AdPackageDTO result = adPackageService.save(adPackageDTO);
            if(DataUtil.isNullObject(result)){
                writeInfoLog(Constants.TRANSACTION_STATUS.FAIL,Constants.RESULT.ADD_FAIL_STR,date);
                return iResponseFactory.failBusiness(Constants.RESULT.ADD_FAIL_STR, "result.insert.fail");
            }
            writeInfoLog(Constants.TRANSACTION_STATUS.SUCCESS,Constants.RESULT.ADD_SUCCESS_STR,date);
            return iResponseFactory.success(result, AdPackageDTO.class);
        }catch (BusinessException be){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,be.getErrorCode(),date);
            return iResponseFactory.failBusiness(be.getErrorCode(), be.getMessage());
        } catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, Constants.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code PUT  /ad-packages} : Updates an existing adPackage.
     *
     * @param adPackageDTO the adPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adPackageDTO,
     * or with status {@code 400 (Bad Request)} if the adPackageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateAdPackage(@RequestBody AdPackageDTO adPackageDTO) throws URISyntaxException {
        Date date = new Date();
        try {
            log.debug("REST request to update AdPackage : {}", adPackageDTO);
//            Optional<AdPackage> optional = adPackageService.findByName(adPackageDTO.getPackageName());
//            if (optional.isPresent() && !optional.get().getId().equals(adPackageDTO.getId()))
//                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "packageName");
            AdPackageDTO result = adPackageService.save(adPackageDTO);
            if(DataUtil.isNullObject(result)){
                writeInfoLog(Constants.TRANSACTION_STATUS.FAIL,Constants.RESULT.EDIT_FAIL_STR,date);
                return iResponseFactory.failBusiness(Constants.RESULT.EDIT_FAIL_STR, "result.update.fail");
            }
            writeInfoLog(Constants.TRANSACTION_STATUS.SUCCESS,Constants.RESULT.EDIT_FAIL_STR,date);
            return iResponseFactory.success(result, AdPackageDTO.class);
        }catch (BusinessException be){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,be.getErrorCode(),date);
            return iResponseFactory.failBusiness(be.getErrorCode(), be.getMessage());
        } catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, Constants.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code GET  /ad-packages} : get all the adPackages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adPackages in body.
     */
    @GetMapping("/search")
    public ResponseEntity<Object> getAllAdPackages(String packageName, Integer type, Pageable pageable) {
        log.debug("REST request to get a page of AdPackages");
        CommonResponseDTO responseDTO = adPackageService.findAll(packageName, type, pageable);
        return iResponseFactory.success(responseDTO, CommonResponseDTO.class);
    }

    /**
     * {@code GET  /ad-packages/:id} : get the "id" adPackage.
     *
     * @param id the id of the adPackageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adPackageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<AdPackageDTO> getAdPackage(@PathVariable Long id) {
        log.debug("REST request to get AdPackage : {}", id);
        Optional<AdPackageDTO> adPackageDTO = adPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adPackageDTO);
    }

    /**
     * {@code DELETE  /ad-packages/:id} : delete the "id" adPackage.
     *
     * @param id the id of the adPackageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteAdPackage(@PathVariable Long id) {
        log.debug("REST request to delete AdPackage : {}", id);
        adPackageService.delete(id);
        return iResponseFactory.success(Object.class);
    }
    @PutMapping("/changeStatus")
    public ResponseEntity<Object> changeStatus(@RequestBody AdPackageDTO adPackageDTO){
        Date date = new Date();
        try{
           Integer result =  adPackageService.changeStatus(adPackageDTO.getId(),adPackageDTO.getStatus());
            if(DataUtil.isNullOrZero(result)){
                writeInfoLog(Constants.TRANSACTION_STATUS.FAIL,Constants.RESULT.SWITCH_FAIL,date);
                return iResponseFactory.failBusiness(Constants.RESULT.SWITCH_FAIL, "result.switch.fail");
            }
            writeInfoLog(Constants.TRANSACTION_STATUS.SUCCESS,Constants.RESULT.SWITCH_SUCCESS,date);
            return iResponseFactory.success(Object.class);
        }catch (BusinessException be){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,be.getErrorCode(),date);
            return iResponseFactory.failBusiness(be.getErrorCode(), be.getMessage());
        }
        catch(Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, Constants.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }
}
