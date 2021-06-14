package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.service.dto.SampleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SampleResource {
    static final Logger LOG = LoggerFactory.getLogger(SampleResource.class);
    @GetMapping("/sayhello")
    public ResponseEntity<SampleDTO> sayHello(){
        LOG.info("Getting Say Hello Message!!");
        return ResponseEntity.ok(new SampleDTO("Test"));
    }
}


