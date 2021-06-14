package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.domain.CfgFilterEntity;
import com.viettel.smsbrandname.service.FilterService;
import com.viettel.smsbrandname.service.dto.FilterDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Autogen class: Lop thao tac danh sach Filter
 *
 * @author ToolGen
 * @date Thu Dec 10 17:32:21 ICT 2020
 */
@RestController
@RequestMapping("/api/filter")
public class FilterResource extends StandardizeLogging {

    @Autowired
    private FilterService filterService;

    @Autowired
    ResponseFactory responseFactory;

    public FilterResource() {
        super(FilterResource.class);
    }

    /**
     * Lay du lieu cac filter
     *
     * @param filterDTO object filter DTO
     * @return
     */
    @PostMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestBody FilterDTO filterDTO) {
        Date date = new Date();
        try {
            Page<CfgFilterEntity> page = filterService.search(filterDTO);
            return responseFactory.success(page, Page.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    /**
     * Add new record
     *
     * @param entity new entity received from client
     * @return entity what added to database
     */
    @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody CfgFilterEntity entity) {
        Date date = new Date();
        boolean isUpdate;
        if (DataUtil.isNullOrEmpty(entity.getCfgFilterId())) {
            isUpdate = false;
        } else {
            isUpdate = true;
        }
        try {
            filterService.add(entity);
            if (!isUpdate) {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "AdminFilterManagerDAO_saveOrUpdateCfgFilter(add)_Success", date);
            } else {
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "AdminFilterManagerDAO_saveOrUpdateCfgFilter(update)_Success", date);
            }
            return responseFactory.success(entity, CfgFilterEntity.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    /**
     * Delete entity out of database(not update status)
     *
     * @param id
     */
    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Date date = new Date();
        try {
            filterService.deleteById(id);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "AdminFilterManagerDAO_deleteCfgFilter_Success", date);
            return responseFactory.success(id, Long.class);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
