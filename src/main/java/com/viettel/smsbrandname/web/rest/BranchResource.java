package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.service.BranchService;
import com.viettel.smsbrandname.service.dto.ComboBean;
import com.viettel.smsbrandname.service.dto.CommonResponseDTO;
import com.viettel.smsbrandname.service.dto.CpSearchDTO;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/branch")
public class BranchResource {

    @Autowired
    private BranchService branchService;

    @Autowired
    private ResponseFactory iResponseFactory;

    @GetMapping("getBranch")
    public ResponseEntity<?> search(Principal principal) {
        return iResponseFactory.success(branchService.getListBranch(principal), ComboBean.class);
    }
}
