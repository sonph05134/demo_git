package com.viettel.smsbrandname.service;

import com.viettel.log.util.ConstantsLog;
import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.StandardizeLogging;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.Cp;
import com.viettel.smsbrandname.security.SecurityUtils;
import com.viettel.smsbrandname.service.dto.CpDTO;
import com.viettel.smsbrandname.service.dto.KeyCloakUserDTO;
import com.viettel.smsbrandname.web.rest.errors.BusinessException;
import com.viettel.smsbrandname.web.rest.errors.ErrorCodeResponse;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeyCloakUserService extends StandardizeLogging {

    private final Keycloak keycloak;

    private final RoleService roleService;

    @Value("${keycloak.realm}")
    String realm;

    public KeyCloakUserService(Keycloak keycloak, RoleService roleService) {
        super(KeyCloakUserService.class);
        this.keycloak = keycloak;
        this.roleService = roleService;
    }

    public Response create(KeyCloakUserDTO keyCloakUserDTO, Cp cpEntity) {
        Date date = new Date();
        Integer statusCode;
        Response response;
        String userId;
        try {
            CredentialRepresentation pass = preparePasswordRepresentation(keyCloakUserDTO.getPassword());
            UserRepresentation userRepresentation = prepareUserRepresentation(pass, keyCloakUserDTO);
            response = keycloak
                .realm(realm)
                .users()
                .create(userRepresentation);
            statusCode = response.getStatusInfo().getStatusCode();
            if (!DataUtil.isNullOrEmpty(statusCode)) {
                if (Integer.valueOf(HttpStatus.CREATED.value()).equals(statusCode)) {
                    userId = search(keyCloakUserDTO.getUsername()).size() > 0 ? search(keyCloakUserDTO.getUsername()).get(0).getId() : "";
                    addRealmLevelRoles(userId, cpEntity);
                    writeInfoLog("Create User KeyCloak Success: " + keyCloakUserDTO.getUsername());
                } else if (Integer.valueOf(HttpStatus.CONFLICT.value()).equals(statusCode)) {
                    throw new BusinessException(ErrorCodeResponse.CONFLICT_KEYCLOAK_USER);
                } else if (Integer.valueOf(HttpStatus.BAD_REQUEST.value()).equals(statusCode)) {
                    throw new BusinessException(ErrorCodeResponse.BAD_REQUEST_KEYCLOAK_USER);
                } else {
                    writeInfoLog("Create User KeyCloak False: " + keyCloakUserDTO.getUsername() + "-" + statusCode);
                    writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, Constants.RESULT.ADD_FAIL_STR, date);
                    throw new BusinessException(Constants.ERR_CONSTANTS.ROLLBACK);
                }
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new BusinessException(Constants.ERR_CONSTANTS.ROLLBACK);
        }
        return response;
    }

    public CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    public UserRepresentation prepareUserRepresentation(CredentialRepresentation credentialRepresentation, KeyCloakUserDTO keyCloakUserDTO) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(keyCloakUserDTO.getUsername());
        newUser.setCredentials(Arrays.asList(credentialRepresentation));
        newUser.setEnabled(keyCloakUserDTO.getEnable());
        newUser.setFirstName(keyCloakUserDTO.getFirstName());
        newUser.setLastName(keyCloakUserDTO.getLastName());
        Map<String, List<String>> attributes = new HashMap<>();
        if (!DataUtil.isNullOrEmpty(keyCloakUserDTO.getProvinceId()))
            attributes.put("provinceId", Arrays.asList(keyCloakUserDTO.getProvinceId().toString()));
        if (!DataUtil.isNullOrEmpty(keyCloakUserDTO.getProvinceCode()))
            attributes.put("provinceCode", Arrays.asList(keyCloakUserDTO.getProvinceCode()));
        if (!DataUtil.isNullOrEmpty(keyCloakUserDTO.getCpCode()))
            attributes.put("cpCode", Arrays.asList(keyCloakUserDTO.getCpCode()));
        if (!DataUtil.isNullOrEmpty(keyCloakUserDTO.getCpId()))
            attributes.put("cpId", Arrays.asList(keyCloakUserDTO.getCpId().toString()));
        newUser.setAttributes(attributes);
        return newUser;
    }


    public void assignRealmLevelRole(String userId, List<String> roles) {
        Date date = new Date();
        try {
            List<RoleRepresentation> addRoles = new ArrayList<>();
            roles.stream().forEach(role -> {
                RoleRepresentation roleRepresentation = roleService.findByName(role);
                addRoles.add(roleRepresentation);
            });
            keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(addRoles);
            writeInfoLog("Create ROLE KeyCloak Success: " + userId + "_" + roles.stream().collect(Collectors.joining(",")));
        } catch (Exception ex) {
            writeInfoLog("Create ROLE KeyCloak False: " + userId + "_" + ex.getMessage());
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, Constants.RESULT.ADD_FAIL_STR, date);
            throw ex;
        }
    }

    public void assignClientLevelRole(String clientId, String userId, String roleName) {
        //get ClientRepresentation
        ClientRepresentation clientRepresentation = keycloak.realm(realm)
            .clients()
            .findByClientId(clientId)
            .get(0);

        // Get client level role
        RoleRepresentation userClientRole = keycloak.realm(realm)
            .clients()
            .get(clientRepresentation.getId())
            .roles()
            .get(roleName)
            .toRepresentation();

        //assign Client-Level Role
        keycloak.realm(realm)
            .users()
            .get(userId)
            .roles()
            .clientLevel(clientRepresentation.getId())
            .add(Arrays.asList(userClientRole));

    }

    public List<UserRepresentation> search(String username) {
        Date date = new Date();
        List<UserRepresentation> user;
        try {
            user = keycloak.realm(realm).users().search(username, true);
            writeInfoLog("Search User KeyCloak Success: " + username);
        } catch (Exception ex) {
            writeInfoLog("Search User KeyCloak False: " + username);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return user;
    }

    public void resetPassword(String userId, String password) {
        Date date = new Date();
        try {
            CredentialRepresentation credentialRepresentation = preparePasswordRepresentation(password);
            keycloak.realm(realm).users().get(userId).resetPassword(credentialRepresentation);
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

    public void update(UserRepresentation userRepresentation) {
        Date date = new Date();
        try {
            keycloak.realm(realm).users().get(userRepresentation.getId()).update(userRepresentation);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "changeSuccess", date);
        } catch (Exception ex) {
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "changeFalse", date);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

    public Response delete(String userId) {
        Date date = new Date();
        Response response;
        try {
            response = keycloak.realm(realm).users().delete(userId);
            Integer statusCode = response.getStatusInfo().getStatusCode();
            if (Integer.valueOf(HttpStatus.NO_CONTENT.value()).equals(statusCode)) {
                writeInfoLog("Delete User KeyCloak Success: " + userId);
            } else {
                writeInfoLog("Delete User KeyCloak False: " + userId + "-" + statusCode);
            }
        } catch (Exception ex) {
            writeInfoLog("Delete User KeyCloak False: " + userId);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
        return response;
    }

    private void addRealmLevelRoles(String userId, Cp cpEntity) {
        List<String> arrRealmRoles = new ArrayList<>();
        if (Long.valueOf(0).equals(cpEntity.getAccountType())) {
            if (Constants.VND.equalsIgnoreCase(cpEntity.getCurrency())) {
                arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP);
            }
            if (Constants.USD.equalsIgnoreCase(cpEntity.getCurrency())) {
                arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP_USD);
            }
        }
        if (Long.valueOf(1).equals(cpEntity.getAccountType())) {
            arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP_LIMIT);
        }
        if (Long.valueOf(1).equals(cpEntity.getRealtimePermit())) {
            arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP_LBS);
        }
        if (arrRealmRoles.size() > 0) {
            assignRealmLevelRole(userId, arrRealmRoles);
        }
    }

    public void updateRealmLevelRoles(String userId, CpDTO cpEntity) {
        Date date = new Date();
        try {
            List<String> arrRealmRoles = new ArrayList<>();
            List<RoleRepresentation> roles = keycloak.realm(realm).users().get(userId).roles().realmLevel().listAll();
            if (!DataUtil.isNullOrEmpty(roles)) {
                roles.stream().forEach(roleRepresentation -> arrRealmRoles.add(roleRepresentation.getName()));
                arrRealmRoles.removeIf(role -> role.equalsIgnoreCase(Constants.REALM_ROLE.WEBPUBLIC_CP_LIMIT));
                arrRealmRoles.removeIf(role -> role.equalsIgnoreCase(Constants.REALM_ROLE.WEBPUBLIC_CP_LBS));
                arrRealmRoles.removeIf(role -> role.equalsIgnoreCase(Constants.REALM_ROLE.WEBPUBLIC_CP) && Long.valueOf(0).equals(cpEntity.getAccountType()));
                arrRealmRoles.removeIf(role -> role.equalsIgnoreCase(Constants.REALM_ROLE.WEBPUBLIC_CP_USD) && Long.valueOf(0).equals(cpEntity.getAccountType()));
                if (Long.valueOf(0).equals(cpEntity.getAccountType())) {
                    if (Constants.VND.equalsIgnoreCase(cpEntity.getCurrency())) {
                        arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP);
                    }
                    if (Constants.USD.equalsIgnoreCase(cpEntity.getCurrency())) {
                        arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP_USD);
                    }
                }
                if (Long.valueOf(1).equals(cpEntity.getAccountType())) {
                    arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP_LIMIT);
                }
                if (Long.valueOf(1).equals(cpEntity.getRealtimePermit())) {
                    arrRealmRoles.add(Constants.REALM_ROLE.WEBPUBLIC_CP_LBS);
                }
                if (arrRealmRoles.size() > 0) {
                    updateRealmLevelRole(userId, arrRealmRoles);
                }
            }
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

    private void updateRealmLevelRole(String userId, List<String> roles) {
        Date date = new Date();
        List<RoleRepresentation> oldRoles = new ArrayList<>();
        try {
            List<RoleRepresentation> currentRoles = new ArrayList<>();
            List<RoleRepresentation> allRoles = roleService.findAll();
            oldRoles = keycloak.realm(realm)
                .users()
                .get(userId)
                .roles().realmLevel().listAll();
            //remove role cu
            keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .remove(oldRoles);

            roles.stream().forEach(role -> {
                RoleRepresentation roleRepresentation = allRoles.stream().filter(obj -> obj.getName().equalsIgnoreCase(role)).findFirst().orElse(null);
                currentRoles.add(roleRepresentation);
            });

            //add lai tu dau
            keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(currentRoles);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, "changeSuccess", date);
        } catch (Exception ex) {
            //rollback lai role ban dau neu update fail
            keycloak.realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(oldRoles);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.FAIL, "changeFalse", date);
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw ex;
        }
    }

    public Boolean checkDeletePermission() {
        String userName = SecurityUtils.getCurrentUserLogin().get();
        String userId = !DataUtil.isNullOrEmpty(search(userName)) ? search(userName).get(0).getId() : null;
        if (!DataUtil.isNullOrEmpty(userId)) {
            List<RoleRepresentation> currentRoles = keycloak.realm(realm).users().get(userId).roles().realmLevel().listAll();
            Optional<RoleRepresentation> optional = currentRoles.stream().filter(roleRepresentation -> roleRepresentation.getName().equalsIgnoreCase(Constants.REALM_ROLE.DELETE_CP)).findFirst();
            if (optional.isPresent()) {
                return true;
            }
        }
        return false;
    }

    public List<RoleRepresentation> getUserRealmRoles(String userName) {
        Date date = new Date();
        List<RoleRepresentation> list = new ArrayList<>();
        try {
            String userId = !DataUtil.isNullOrEmpty(search(userName)) ? search(userName).get(0).getId() : null;
            if (!DataUtil.isNullOrEmpty(userId))
                list = keycloak
                    .realm(realm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .listAll();
        } catch (Exception ex) {
            writeErrorLog(ex, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new BusinessException(ErrorCodeResponse.UNKNOWN);
        }
        return list;
    }
}
