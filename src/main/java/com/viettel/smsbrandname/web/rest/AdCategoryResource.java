package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.AdCategory;
import com.viettel.smsbrandname.service.AdCategoryService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.service.dto.AdCategoryDTO;

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
 * REST controller for managing {@link com.viettel.smsbrandname.domain.AdCategory}.
 */
@RestController
@RequestMapping("/api/category")
public class AdCategoryResource extends StandardizeLogging {

    private final Logger log = LoggerFactory.getLogger(AdCategoryResource.class);

    private static final String ENTITY_NAME = "adCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdCategoryService adCategoryService;

    private final ResponseFactory iResponseFactory;

    public AdCategoryResource(AdCategoryService adCategoryService, ResponseFactory iResponseFactory) {
        super(AdCategoryResource.class);
        this.adCategoryService = adCategoryService;
        this.iResponseFactory = iResponseFactory;
    }

    /**
     * {@code POST  /ad-categories} : Create a new adCategory.
     *
     * @param adCategoryDTO the adCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adCategoryDTO, or with status {@code 400 (Bad Request)} if the adCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createAdCategory(@RequestBody AdCategoryDTO adCategoryDTO) {
        Date date = new Date();
        try{
            log.debug("REST request to save AdCategory : {}", adCategoryDTO);
//            Optional<AdCategory> optionalCode = adCategoryService.findByCode(adCategoryDTO.getCatCode());
//            Optional<AdCategory> optionalName = adCategoryService.findByName(adCategoryDTO.getCatName());
//            if (optionalCode.isPresent())
//                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "catCode");
//            if (optionalName.isPresent())
//                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "catName");
            AdCategoryDTO result = adCategoryService.save(adCategoryDTO);
            if(DataUtil.isNullObject(result)){
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,Constants.RESULT.ADD_FAIL_STR,date);
                return iResponseFactory.failBusiness(Constants.RESULT.ADD_FAIL_STR, "result.insert.fail");
            }
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS,Constants.RESULT.ADD_SUCCESS_STR,date);
            return iResponseFactory.success(result, AdCategoryDTO.class);
        } catch (Exception e){
            if(e instanceof BusinessException){
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,((BusinessException) e).getErrorCode(),date);
                return iResponseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code PUT  /ad-categories} : Updates an existing adCategory.
     *
     * @param adCategoryDTO the adCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the adCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateAdCategory(@RequestBody AdCategoryDTO adCategoryDTO){
        Date date = new Date();
        try{
            log.debug("REST request to update AdCategory : {}", adCategoryDTO);
//            Optional<AdCategory> optionalCode = adCategoryService.findByCode(adCategoryDTO.getCatCode());
//            Optional<AdCategory> optionalName = adCategoryService.findByName(adCategoryDTO.getCatName());
//            if (optionalCode.isPresent() && !optionalCode.get().getId().equals(adCategoryDTO.getId()))
//                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "catCode");
//            if (optionalName.isPresent() && !optionalName.get().getId().equals(adCategoryDTO.getId()))
//                return iResponseFactory.failBusiness(Constants.ERR_CONSTANTS.DUPLICATE, "catName");
            AdCategoryDTO result = adCategoryService.save(adCategoryDTO);
            if(DataUtil.isNullObject(result)){
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,Constants.RESULT.EDIT_FAIL_STR,date);
                return iResponseFactory.failBusiness(Constants.RESULT.EDIT_FAIL_STR, "result.update.fail");
            }
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, Constants.RESULT.EDIT_SUCCESS_STR, date);
            return iResponseFactory.success(result, AdCategoryDTO.class);
        }catch (BusinessException be){
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL,be.getErrorCode(),date);
            return iResponseFactory.failBusiness(be.getErrorCode(), be.getMessage());
        }
        catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code GET  /ad-categories} : get all the adCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adCategories in body.
     */
    @GetMapping("/search")
    public ResponseEntity<Object> getAllAdCategories(String catCode, String catName, Pageable pageable) {
        Date date = new Date();
        try{
            log.debug("REST request to get a page of AdCategories");
            CommonResponseDTO responseDTO = adCategoryService.findAll(catCode, catName, pageable);
            return iResponseFactory.success(responseDTO, CommonResponseDTO.class);
        } catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code GET  /ad-categories/:id} : get the "id" adCategory.
     *
     * @param id the id of the adCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<AdCategoryDTO> getAdCategory(@PathVariable Long id) {
        Date date = new Date();
        try{
            log.debug("REST request to get AdCategory : {}", id);
            Optional<AdCategoryDTO> adCategoryDTO = adCategoryService.findOne(id);
            return ResponseUtil.wrapOrNotFound(adCategoryDTO);
        } catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    /**
     * {@code DELETE  /ad-categories/:id} : delete the "id" adCategory.
     *
     * @param id the id of the adCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdCategory(@PathVariable Long id) {
        Date date = new Date();
        try{
            log.debug("REST request to delete AdCategory : {}", id);
            adCategoryService.delete(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, Constants.RESULT.DEL_SUCCESS_STR, date);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        } catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }

    @GetMapping(value = "/findAdCategoryByRecycle")
    public ResponseEntity<Object> findAdCategoryByRecycle() {
        Date date = new Date();
        try{
            List<ComboBean> lst = adCategoryService.findAdCategoryByRecycle();
            return iResponseFactory.success(lst, ComboBean.class);
        } catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION,ConstantsLog.TRANSACTION_STATUS.FAIL,date);
            throw e;
        }
    }
}
