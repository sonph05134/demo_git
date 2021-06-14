package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.service.CPBrandService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.UserDTO;
import com.viettel.smsbrandname.service.dto.cpbrand.CPBrandSearchDTO;
import com.viettel.smsbrandname.service.dto.cpbrand.CPBrandTelcoDTO;
import com.viettel.smsbrandname.service.dto.cpbrand.CpBrandActionDTO;
import com.viettel.smsbrandname.service.dto.cpbrand.CpBrandResultDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.ErrorConstants;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/cp-management/brand")
public class CpBrandResource {

    private final CPBrandService cpBrandService;
    private final ResponseFactory responseFactory;
    private final UserService userService;

    public CpBrandResource(CPBrandService cpBrandService,
                           ResponseFactory responseFactory,
                           UserService userService) {
        this.cpBrandService = cpBrandService;
        this.responseFactory = responseFactory;
        this.userService = userService;
    }

    @GetMapping("tecols")
    public ResponseEntity<Object> getAll() {
        return responseFactory.success(cpBrandService.getTelcos(), CPBrandTelcoDTO.class);
    }

    @GetMapping("alias-group")
    public ResponseEntity<Object> getAliasGroup(@RequestParam Integer type) {
        return responseFactory.success(cpBrandService.getAliasGroup(type), ComboboxResponseDTO.class);
    }

    @GetMapping("ws")
    public ResponseEntity<Object> getMainWS(@RequestParam Integer status) {
        return responseFactory.success(cpBrandService.getWS(status), ComboboxResponseDTO.class);
    }

    @GetMapping("keep-fee")
    public ResponseEntity<Object> getKeepFee() {
        return responseFactory.success(cpBrandService.getKeepFee(), ComboboxResponseDTO.class);
    }

    @GetMapping("search")
    public ResponseEntity<Object> onSearch(@RequestParam Long cpId,
                                           @RequestParam Integer aliasType,
                                           @RequestParam String brandName,
                                           @RequestParam String telco,
                                           @RequestParam Integer start,
                                           @RequestParam Integer limit) {
        ApiResponseDTO apiResponseDTO;
        try {
            apiResponseDTO = cpBrandService.search(aliasType, brandName, telco, start, limit, cpId);
        } catch (Exception ex) {
            throw ex;
        }
        return responseFactory.success(apiResponseDTO, ApiResponseDTO.class);
    }

    @PostMapping("export-brands")
    public ResponseEntity<Object> exportBrands(@RequestBody CPBrandSearchDTO cpBrandSearchDTO) throws Exception {
        return ResponseEntity.ok().body(new InputStreamResource(cpBrandService.exportBrands(cpBrandSearchDTO)));
    }

    @PostMapping("active-or-inactive")
    public ResponseEntity<Object> activeOrInactiveBrand(
        @RequestBody CpBrandActionDTO cpBrandActionDTO, Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
            cpBrandActionDTO.setUserName(currentUser.getLogin());
            cpBrandService.activeOrInactiveBrand(cpBrandActionDTO);
            return responseFactory.success(null);
        } else {
            return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
        }
    }

    @PostMapping("delete")
    public ResponseEntity<Object> deleteCpBrand(
        @RequestBody CpBrandActionDTO cpBrandActionDTO, Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
            cpBrandActionDTO.setUserName(currentUser.getLogin());
            cpBrandService.deleteCpBrand(cpBrandActionDTO);
            return responseFactory.success(null);
        } else {
            return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
        }
    }

    @PostMapping("create")
    public ResponseEntity<Object> createBrand(
        @RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam String data,
        @RequestParam String commissionDefault,
        @RequestParam String checked,
        Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
            ApiResponseDTO apiResponseDTO = cpBrandService.createCpAlias(data, currentUser.getLogin(), currentUser.getId(), commissionDefault, checked, file);

            return responseFactory.success(apiResponseDTO, ApiResponseDTO.class);
        } else {
            return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
        }
    }

    @PostMapping("edit")
    public ResponseEntity<Object> editBrand(
        @RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam String data,
        @RequestParam String commissionDefault,
        @RequestParam String checked,
        Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
            ApiResponseDTO apiResponseDTO = cpBrandService.editCpAlias(data, currentUser.getLogin(), currentUser.getId(), commissionDefault, checked, file);

            return responseFactory.success(apiResponseDTO, ApiResponseDTO.class);
        } else {
            return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
        }
    }

    @PostMapping(value = "downloadAttach")
    public ResponseEntity<?> downloadCpAttach(@RequestParam String fileName) throws IOException {
        return ResponseEntity.ok().body(new InputStreamResource(cpBrandService.downloadAttachFile(fileName)));
    }

}
