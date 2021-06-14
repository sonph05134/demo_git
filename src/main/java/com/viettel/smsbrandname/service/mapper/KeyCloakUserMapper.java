package com.viettel.smsbrandname.service.mapper;

import com.viettel.smsbrandname.service.dto.KeyCloakUserDTO;
import org.keycloak.models.UserModel;

public class KeyCloakUserMapper {
    public KeyCloakUserDTO mapToUserDto(UserModel um) {
        return new KeyCloakUserDTO(
            um.getUsername(),
            um.getFirstName(),
            um.getLastName(),
            um.getAttribute("provinceCode").get(0),
            um.getAttribute("cpCode").get(0),
            um.isEnabled()
        );
    }
}
