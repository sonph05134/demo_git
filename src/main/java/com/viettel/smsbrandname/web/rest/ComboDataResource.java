package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.service.ComboService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/combo-value")
public class ComboDataResource {

    @Autowired
    private ComboService comboService;

    @Autowired
    private ResponseFactory iResponseFactory;

    @GetMapping(value = "priceLevel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPriceLevel() {
        return iResponseFactory.success(comboService.getListPriceLevel(), ComboBean.class);
    }

    @GetMapping(value = "smsc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSMSC() {
        return iResponseFactory.success(comboService.getSMSC(), ComboBean.class);
    }

    @GetMapping(value = "gateway", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getGateway() {
        return iResponseFactory.success(comboService.getGateway(), ComboBean.class);
    }

}
