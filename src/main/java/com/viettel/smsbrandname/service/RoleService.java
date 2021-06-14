package com.viettel.smsbrandname.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    Keycloak keycloak;

    @Value("${keycloak.realm}")
    String realm;

    public void createRole(String roleName) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);

        keycloak.realm(realm)
            .roles()
            .create(roleRepresentation);
    }

    public List<RoleRepresentation> findAll() {
        return keycloak.realm(realm)
            .roles()
            .list();
    }

    public RoleRepresentation findByName(String roleName) {
        return keycloak.realm(realm)
            .roles()
            .get(roleName)
            .toRepresentation();
    }

}
