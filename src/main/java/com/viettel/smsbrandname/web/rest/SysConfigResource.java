package com.viettel.smsbrandname.web.rest;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.domain.SysConfig;
import com.viettel.smsbrandname.service.SysConfigService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.UserDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorConstants;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("api/sys-config")
public class SysConfigResource extends StandardizeLogging {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private UserService userService;

    public SysConfigResource() {
        super(SysConfigResource.class);
    }

    @GetMapping("search")
    public ResponseEntity<Object> onSearch(@RequestParam String keyWord,
                                           @RequestParam Long module,
                                           @RequestParam Long deleted,
                                           @RequestParam Integer start,
                                           @RequestParam Integer limit) throws SQLException {
        Date date = new Date();
        try {
            return responseFactory.success(sysConfigService.onSearch(keyWord, module, deleted, start, limit), ApiResponseDTO.class);
        }
        catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }

    @PostMapping("save-edit")
    public ResponseEntity<Object> onSaveOrEdit(@RequestBody SysConfig sysConfig, Principal principal) {
        Date date = new Date();
        try {
            boolean isUpdate;
            if (DataUtil.isNullOrEmpty(sysConfig.getSysConfigId())) {
                isUpdate = false;
            } else {
                isUpdate = true;
            }
            if (principal instanceof AbstractAuthenticationToken) {
                UserDTO currentUser = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
                sysConfigService.saveOrEdit(sysConfig, currentUser.getLogin());
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
            throw e;
        }
    }

    @PostMapping("active-or-inactive")
    public ResponseEntity<Object> activeOrInactive(@RequestBody SysConfig sysConfig) {
        Date date = new Date();
        try {
            sysConfigService.activeOrInactive(sysConfig);
            return responseFactory.success(null);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw e;
        }
    }
}
