package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.domain.Prog;
import com.viettel.smsbrandname.service.ProgService;
import com.viettel.smsbrandname.service.dto.CareCustomerSmsSearchDTO;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CpDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/management/care-customer-sms")
public class CareCustomerSmsResource {

    @Autowired
    private ProgService progService;

    @Autowired
    private ResponseFactory iResponseFactory;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "careCustomerSms";

    @PostMapping(value = "/search")
    public ResponseEntity<Object> onSearch(@RequestBody CareCustomerSmsSearchDTO careCustomerSmsSearchDTO)
    {
        Page<CareCustomerSmsSearchDTO> page =  progService.search(careCustomerSmsSearchDTO);
        return iResponseFactory.success(page, Page.class);
    }

    @GetMapping(value = "/lstCheckBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(){
        List<ComboBean> lst = progService.getListCheckBox();
        return iResponseFactory.success(lst, ComboBean.class);
    }
    @PostMapping(value = "/getById")
    public ResponseEntity<Object> getByIdViewDeltail(@RequestBody CareCustomerSmsSearchDTO careCustomerSmsSearchDTO){
        return iResponseFactory.success(progService.getCareCustomerSmsSearchDTOById(careCustomerSmsSearchDTO),CareCustomerSmsSearchDTO.class);
    }
    @PostMapping(value = "/approved")
    public ResponseEntity<Object> doApproved(@RequestBody List<CareCustomerSmsSearchDTO> listCareCustomerSmsSearchDTO) throws URISyntaxException {
        List<Prog> lstResult= new ArrayList<>();
        for(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO : listCareCustomerSmsSearchDTO){
            lstResult.add(progService.updateProg(careCustomerSmsSearchDTO, false));
        }
        return ResponseEntity.created(new URI("/api/management/care-customer-sms/approved" + lstResult.get(0).getProgId().toString()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, lstResult.get(0).getProgId().toString()))
            .body(lstResult);
    }
    @PostMapping(value = "/refuse")
    public ResponseEntity<Object> doRefuse(@RequestBody List<CareCustomerSmsSearchDTO> listCareCustomerSmsSearchDTO) throws  URISyntaxException{
       List<Prog> lstResult= new ArrayList<>();
        for(CareCustomerSmsSearchDTO careCustomerSmsSearchDTO : listCareCustomerSmsSearchDTO){
            lstResult.add(progService.updateProg(careCustomerSmsSearchDTO, true));
        }
        return ResponseEntity.created(new URI("/api/management/care-customer-sms/reFuse" + lstResult.get(0).getProgId().toString()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, lstResult.get(0).getProgId().toString()))
            .body(lstResult);
    }
    @PostMapping(value = "/export")
    public ResponseEntity<?> exportAdvertisementSmsCp(@RequestBody CareCustomerSmsSearchDTO careCustomerSmsSearchDTO) {
        return ResponseEntity.ok().body(new InputStreamResource(progService.exportListCareCustmoerSms(careCustomerSmsSearchDTO)));
    }
}
