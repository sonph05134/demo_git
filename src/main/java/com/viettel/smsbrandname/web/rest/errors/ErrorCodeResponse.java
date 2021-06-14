package com.viettel.smsbrandname.web.rest.errors;

import com.viettel.smsbrandname.commons.Translator;

public enum ErrorCodeResponse {
    UNKNOWN("100", "error.haveSomeError"),
    ERR_EXIST("101", "error.exist"),
    CP_ALIAS_EXIST("102", "error.cp.alias.exist"),
    CP_NOT_EXIST("103", "cp.notExist"),
    FILE_WRONG_TYPE("104", "file.wrong.type"),
    FILE_TOO_LARGE("105", "file.too.large"),
    REFUND_FAIL("106", "virtual.refund.fail"),
    AMOUNT_GREATER_20("107", "virtual.refund.amountGreaterThan20"),
    REDUCE_MONEY_FAIL("108", "virtual.refund.errorReduceMoney"),
    UPDATE_TRANS_FAIL("109", "virtual.update.trans.fail"),
    RETRY_FAIL("110", "virtual.retry.trans.fail"),
    FILE_NOT_EXIST("111", "virtual.file.not.exist"),
    TAX_CODE_OR_COMPANY_NAME_EXIST("112", "error.cp.company.name.or.tax.code.exist"),
    EDIT_ALIAS_FAIL("113", "edit.alias.fail"),
    CREATE_ALIAS_FAIL("114", "create.alias.fail"),
    SPECIAL_CHARACTERS("118", "error.special.characters"),
    ERR_NOT_EXISTED("120", "error.notExist"),
    ACTIVE_CONNECTION_FAIL("121", "smsc-gateway.update-error"),
    INACTIVE_GATEWAY_FAIL("122", "error.gateway.inactive"),
    INACTIVE_SMSC_FAIL("123", "error.smsc.inactive"),
    PROVINCE_IS_MAPPED("124", "provinceIsMapped"),
    NOT_EXIST("125", "error.not.exist"),
    EXISTS_PROVINCE_NOT_IN_PROVINCE_BCCS("126", "isExistsProvinceNotInProvinceBccs"),
    INVALID_ACCOUNT_LEVEL("127", "invalidAccountLevel"),
    INVALID_ACCOUNT_TYPE_FINANCE("128", "invalid-account-type-finance"),
    SAVE_FILE_FAIL("129", "error.save_file.fail"),
    DATA_NOT_MATCH("130", "data.not.match"),
    NOT_MONEY("131", "not.money"),
    MONEY_CANNOT_BE_DEDUCTED("132", "money.cannot.be.deducted"),
    ERR_NAME_EXIST("133", "error.exist"),
    EXIST_CP_WITH_PROVINCE("134", "isExistsCPWithProvince"),
    EXPORT_NOT_EXISTED("135", "export.notExist"),
    CANT_ENCRYPT_PWS("136", "cp.cant-encrypt"),

    GATEWAY_EXIST("138", "gateway-exist"),
    SAVE_GATEWAY_FAIL("139", "error.gateway.save"),
    DELETE_GATEWAY_FAIL("140", "error.gateway.delete"),
    GATEWAY_CHANGE_STATUS_FAIL("141", "error.gateway.changeStatus"),
    SMSC_EXIST("142", "error.smsc.exist"),
    SMSC_DELETE_FAIL("143", "error.smsc.delete"),
    SMSC_DELETE_IN_USE("144", "error.smsc.inUse"),
    SMSC_CHANGE_STATUS_FAIL("145", "error.smsc.changeStatus"),
    SMS_COST_NULL_GROUP_NAME("146", "error.smsCost.nullGroupName"),
    SMS_COST_NULL_PRICE("147", "error.smsCost.nullPrice"),
    SMS_COST_INSERT_FAIL("148", "error.smsCost.insertFail"),
    SMS_COST_PRICE_TOO_LARGE("149", "error.smsCost.priceTooLarge"),
    SMS_COST_DELETE_FAIL("150", "error.smsCost.deleteFail"),
    SMSC_GATE_EXIST("151", "error.smscGate.exist"),
    SMSC_GATE_SAVE_FAIL("152", "error.smscGate.saveFail"),
    SMSC_GATE_NOT_EXIST("153", "error.smscGate.notExist"),
    SMSC_GATE_CHANGE_STATUS_FAIL("154", "error.smscGate.changeStatus"),
    ALIAS_COST_REG_ALIAS_COST_NULL("155", "error.aliasCost.regAliasCostNull"),
    ALIAS_COST_SAVE_FAILED("156", "error.aliasCost.saveFail"),
    ALIAS_COST_CANCEL_ALIAS_COST_NULL("157", "error.aliasCost.cancelAliasCostNull"),
    ALIAS_COST_KEEP_ALIAS_COST_NULL("158", "error.aliasCost.keepAliasCostNull"),
    ALIAS_COST_EXIST("159", "error.aliasCost.exist"),
    ALIAS_COST_DELETE_FAIL("160", "error.aliasCost.delete"),
    PRICE_LEVEL_SAVE_FAIL("161", "error.priceLevel.save"),
    PRICE_LEVEL_DELETE_FAIL("162", "error.priceLevel.delete"),
    SYS_CONFIG_SAVE_FAIL("163", "error.sysConfig.save"),
    SAVE_FAIL("164", "error.save"),
    DELETE_FAIL("165", "error.delete"),
    STATUS_ILLEGAL("166", "error.status.illegal"),
    FEE_VALUE_INVALID("167", "error.maintainFee.feeValue"),
    BAD_REQUEST_KEYCLOAK_USER("400", "cp.bad-request-keycloak-user"),
    CONFLICT_KEYCLOAK_USER("409", "cp.conflict-keycloak-user"),
    CANT_DELETE_CP("137", "cp.cant-delete-cp"),
    CP_NOT_EXIST_IN_CHARGING("168", "cp.notExistInCharging"),
    DELETE_SAVE_DISCOUNT_PROVINCE_FAIL("169", "error.delete.save.discountProvince");

    private final String message;

    private final String errorCode;

    ErrorCodeResponse(String errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageI18N() {
        return Translator.toLocale(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
