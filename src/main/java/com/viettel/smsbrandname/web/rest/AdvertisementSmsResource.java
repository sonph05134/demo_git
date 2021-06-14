package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.Prog;
import com.viettel.smsbrandname.service.ProgService;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.dto.VirtualAccountDTO;
import com.viettel.smsbrandname.service.dto.cpprog.CPProgAdvertisementSearchDTO;
import com.viettel.smsbrandname.service.dto.cpprog.ProgAdApproveHisDTO;
import com.viettel.smsbrandname.service.dto.response.CPProgAdvertisementResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BadRequestAlertException;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("api/management/advertisement-sms")
public class AdvertisementSmsResource extends StandardizeLogging {
    private static final String ENTITY_NAME = "prog";

    @Autowired
    private ProgService progService;
    @Autowired
    ResponseFactory responseFactory;

    public AdvertisementSmsResource() {
        super(AdvertisementSmsResource.class);
    }

    @PostMapping("/search")
    public ResponseEntity<Object> onSearch(@RequestBody CPProgAdvertisementSearchDTO searchDTO) {
        Date date = new Date();
        CPProgAdvertisementResponseDTO responseDTO;
        try {
            responseDTO = progService.searchAdvertisement(searchDTO);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return responseFactory.success(responseDTO, CPProgAdvertisementResponseDTO.class);
    }

    @PostMapping(value = "/exportAdvertisementSms")
    public ResponseEntity<?> exportAdvertisementSmsCp(@RequestBody CPProgAdvertisementSearchDTO searchDTO, Principal principal) {
        Date date = new Date();
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = progService.export(searchDTO, principal);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return ResponseEntity.ok().body(new InputStreamResource(byteArrayInputStream));
    }

    @PostMapping("/updateSMSgAdvertisementenDeny")
    public ResponseEntity<?> updateSMSgAdvertisementenDeny(@RequestBody List<ProgAdApproveHisDTO> progAdApproveHisDTO) throws Exception {
        if (progAdApproveHisDTO.size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("advertisement.update.notExit"), ENTITY_NAME, "idexists");
        }
        progService.updateSMSgAdvertisement(progAdApproveHisDTO, false);
        return responseFactory.success(Prog.class);
    }

    @PostMapping("/updateSMSgAdvertisementenYes")
    public ResponseEntity<?> updateSMSgAdvertisementenYes(@RequestBody List<ProgAdApproveHisDTO> progAdApproveHisDTO) throws Exception {
        if (progAdApproveHisDTO.size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("advertisement.update.notExit"), ENTITY_NAME, "idexists");
        }
        progService.updateSMSgAdvertisement(progAdApproveHisDTO, true);
        return responseFactory.success(Prog.class);
    }

    @PostMapping("/updateSMSgAdvertisements")
    public ResponseEntity<?> updateSMSgAdvertisement(@RequestBody List<ProgAdApproveHisDTO> progAdApproveHisDTO) throws Exception {
        if (progAdApproveHisDTO.size() == 0) {
            throw new BadRequestAlertException(Translator.toLocale("advertisement.update.notExit"), ENTITY_NAME, "idexists");
        }
        progService.updateSMSgAdvertisement(progAdApproveHisDTO, true);
        return responseFactory.success(Prog.class);
    }

    @PostMapping("/geTattachFile")
    public ResponseEntity<?> geTattachFile(@RequestBody CPProgAdvertisementSearchDTO searchDTO, Principal principal) throws Exception {
        InputStream targetStream = new ByteArrayInputStream(progService.geTattachFile(searchDTO.getCatId()));
        return ResponseEntity.ok().body(new InputStreamResource(targetStream));

    }

}
