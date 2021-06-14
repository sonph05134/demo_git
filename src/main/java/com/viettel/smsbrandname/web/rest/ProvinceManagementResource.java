package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.Province;
import com.viettel.smsbrandname.domain.ProvinceBccs;
import com.viettel.smsbrandname.service.ProvinceBccsService;
import com.viettel.smsbrandname.service.ProvinceService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.UserDTO;
import com.viettel.smsbrandname.service.dto.province.ProvinceManagementDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.service.dto.response.ComboboxResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorConstants;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("api/management/province")
public class ProvinceManagementResource extends StandardizeLogging {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private ProvinceBccsService provinceBCCSService;

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private UserService userService;

    public ProvinceManagementResource() {
        super(ProvinceManagementResource.class);
    }

    @GetMapping("search")
    public ResponseEntity<Object> onSearch(@RequestParam Long provinceId,
                                           @RequestParam Integer start,
                                           @RequestParam Integer limit,
                                           @RequestParam Double timeZone) {
        Date date = new Date();
        try {
            ProvinceManagementDTO provinceManagementDTO = new ProvinceManagementDTO();
            provinceManagementDTO.setProvinces(provinceService.onSearch(provinceId, start, limit, timeZone));
            provinceManagementDTO.setProvinceBCCSs(provinceBCCSService.onSearch(provinceId, start, limit, timeZone));

            return responseFactory.success(provinceManagementDTO, ProvinceManagementDTO.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("search-province")
    public ResponseEntity<Object> onSearchProvince(@RequestParam Long provinceId,
                                                   @RequestParam Integer start,
                                                   @RequestParam Integer limit,
                                                   @RequestParam Double timeZone) {

        return responseFactory.success(provinceService.onSearch(provinceId, start, limit, timeZone), ApiResponseDTO.class);
    }

    @GetMapping("search-province-bccs")
    public ResponseEntity<Object> onSearchProvinceBCCS(@RequestParam Long provinceId,
                                                       @RequestParam Integer start,
                                                       @RequestParam Integer limit,
                                                       @RequestParam Double timeZone) {

        return responseFactory.success(provinceBCCSService.onSearch(provinceId, start, limit, timeZone), ApiResponseDTO.class);
    }

    @PostMapping("save-edit")
    public ResponseEntity<Object> onSaveOrEdit(@RequestBody Province province, Principal principal) {
        Date date = new Date();
        try {
            boolean isUpdate;
            if (DataUtil.isNullOrEmpty(province.getProvinceId())) {
                isUpdate = false;
            } else {
                isUpdate = true;
            }
            if (principal instanceof AbstractAuthenticationToken) {
                UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
                provinceService.saveOrEdit(province, currentUser.getLogin());
                if (!isUpdate) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertOk", date);
                } else {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "updateOk", date);
                }
                return responseFactory.success(null);
            } else {
                return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
            }
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("delete")
    public ResponseEntity<Object> onDelete(@RequestBody Province province, Principal principal) {
        Date date = new Date();
        try {
            if (principal instanceof AbstractAuthenticationToken) {
                UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
                provinceService.delete(province, currentUser.getLogin());
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
                return responseFactory.success(null);
            } else {
                return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @GetMapping("province-not-in-bccs")
    public ResponseEntity<Object> getProvinceNotInBccs() {
        return responseFactory.success(provinceBCCSService.getLstProvinceNotInProvinceBccs(), ComboboxResponseDTO.class);
    }

    @PostMapping("save-edit-bccs")
    public ResponseEntity<Object> onSaveOrEditBccs(@RequestBody ProvinceBccs provinceBccs, Principal principal) {
        Date date = new Date();
        try {
            boolean isUpdate;
            if (DataUtil.isNullOrEmpty(provinceBccs.getProvinceId())) {
                isUpdate = false;
            } else {
                isUpdate = true;
            }
            if (principal instanceof AbstractAuthenticationToken) {
                UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
                provinceBCCSService.saveOrEdit(provinceBccs, currentUser.getLogin());
                if (!isUpdate) {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "insertOk", date);
                } else {
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "updateOk", date);
                }
                return responseFactory.success(null);
            } else {
                return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
            }
        }
        catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("delete-bccs")
    public ResponseEntity<Object> onDeleteBccs(@RequestBody ProvinceBccs provinceBCCS, Principal principal) {
        Date date = new Date();
        try {
            if (principal instanceof AbstractAuthenticationToken) {
                UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
                provinceBCCSService.deleteBccs(provinceBCCS, currentUser.getLogin());
                writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "deleteOk", date);
                return responseFactory.success(null);
            } else {
                return responseFactory.failBusiness(ErrorConstants.NOT_FOUND_USER, Translator.toLocale("not.found.username"));
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                return responseFactory.failBusiness(((BusinessException) e).getErrorCode(), e.getMessage());
            }
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}

