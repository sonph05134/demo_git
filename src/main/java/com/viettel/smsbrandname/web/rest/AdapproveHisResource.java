package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.service.AdapproveHisService;
import com.viettel.smsbrandname.service.dto.AdApproveHisDTO;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/AdApproveHis")
public class AdapproveHisResource extends StandardizeLogging {
    @Autowired
    AdapproveHisService adapproveHisService;
    @Autowired
    private ResponseFactory iResponseFactory;

    public AdapproveHisResource() {
        super(AdapproveHisResource.class);
    }

    @PostMapping(value = "/findAdApproveHisByProgId")
    public ResponseEntity<Object> findAdApproveHisByProgId(@RequestBody AdApproveHisDTO adApproveHisDTO) {
        Date date = new Date();
        Page<AdApproveHisDTO> lst;
        try {
            lst = adapproveHisService.findAdApproveHisByProgId(adApproveHisDTO);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return iResponseFactory.success(lst, Page.class);
    }

}
