package com.viettel.smsbrandname.web.rest;

import com.viettel.smsbrandname.domain.SysConfig;
import com.viettel.smsbrandname.service.ProvinceUserService;
import com.viettel.smsbrandname.service.UserService;
import com.viettel.smsbrandname.service.dto.ProvinceUsersDTO;
import com.viettel.smsbrandname.service.dto.response.ApiResponseDTO;
import com.viettel.smsbrandname.web.rest.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/province-user")
public class ProvinceUserResource {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private ProvinceUserService provinceUserService;

    @Autowired
    private UserService userService;

    @GetMapping("search")
    public ResponseEntity<Object> onSearch(@RequestParam Long provinceId,
                                           @RequestParam String username,
                                           @RequestParam String email,
                                           @RequestParam String keyWord,
                                           @RequestParam Integer start,
                                           @RequestParam Integer limit) {
        return responseFactory.success(provinceUserService.onSearch(provinceId, username, email, keyWord, start, limit), ApiResponseDTO.class);
    }

    @PostMapping("save-edit")
    public ResponseEntity<Object> onSaveOrEdit(@RequestBody ProvinceUsersDTO provinceUsersDTO) {
        provinceUserService.saveOrEdit(provinceUsersDTO);
        return responseFactory.success(null);
    }

    @PostMapping("active-or-inactive")
    public ResponseEntity<Object> activeOrInactive(@RequestBody ProvinceUsersDTO provinceUsersDTO) {
        provinceUserService.activeOrInactive(provinceUsersDTO);
        return responseFactory.success(null);
    }
}
