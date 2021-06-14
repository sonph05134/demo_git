package com.viettel.cmsmobilebrandname.mobile.rest;

import com.viettel.cmsmobilebrandname.mobile.dto.FilterDTOMB;
import com.viettel.cmsmobilebrandname.mobile.service.MBFilterService;
import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/mobile/filter")
public class MBFilterResource extends StandardizeLogging {

    @Autowired
    private MBFilterService filterService;

    @Autowired
    ResponseFactory responseFactory;

    public MBFilterResource( ) {
        super(MBFilterResource.class);
    }


    @PostMapping(value = "add",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFilterForMobile(@Valid @RequestBody FilterDTOMB filterDTOMB){
        Date date = new Date();
        try {
            if(filterService.addFilterForMobile(filterDTOMB) == true){
                return responseFactory.success(Translator.toLocale("add.filter.success") +" " +  filterDTOMB.getKeyword() ,String.class);
            }else {
                return responseFactory.failure(Translator.toLocale("add.filter.failed")+ " " +  filterDTOMB.getKeyword(),String.class);
            }
        }catch (Exception e){
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }

    }

    @PostMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@Valid @RequestBody FilterDTOMB filterDTOMB){
        Date date = new Date();
        try {
            if(filterService.updateFilterForMobile(filterDTOMB) == true){
                return responseFactory.success(Translator.toLocale("filter.update.success") + " " + filterDTOMB.getKeyword() ,String.class);
            }else {
                return responseFactory.failure(Translator.toLocale("filter.update.failure") + " " + filterDTOMB.getKeyword(),String.class);
            }

        }catch (Exception exception) {
            writeErrorLog(exception, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw  exception;
        }

    }

    @PostMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestBody FilterDTOMB filterDTOMB){
        Date date = new Date();

        try {
            filterService.delete(filterDTOMB.getCfgFilterId());
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "AdminFilterManagerDAO_saveOrUpdateCfgFilter(delete)_Success",date);
            return responseFactory.success(Translator.toLocale("filter.remove.Success") ,String.class);
        }catch (Exception exception){
            writeErrorLog(exception, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            return responseFactory.failure(Translator.toLocale("filter.remove.failure") ,String.class);
        }
    }

    @PostMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestBody FilterDTOMB filterDTOMB, Pageable pageable) {
        Date date = new Date();
        try {
        Page<FilterDTOMB> result = filterService.searchFilter(filterDTOMB, pageable);

            return responseFactory.success(result,Page.class);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
        }


    }

