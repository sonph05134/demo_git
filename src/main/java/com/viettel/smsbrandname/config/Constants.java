package com.viettel.smsbrandname.config;

/**
 * Application constants.
 */
public final class Constants {
    public interface STATUS {
        Integer ACTIVE = 1;
    }

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
   public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,50}$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String CURRENCY_VND = "VND";
    public static final String CURRENCY_USD = "USD";
    public static final Integer CURRENCY_VND_VAL = 0;
    public static final Integer CURRENCY_USD_VAL = 1;
    public static final Integer USD_LENGTH = 1000000;
    public static final long CUSTOMER_CARE = 0;
    public static final long ADVERTISE = 1;
    public static final String HEADER_VALUE_NUMBER = "-1";
    public static final String TRANSFER = "2";
    public static final String RESULT_VALIDATE_MSG = "RESULT_VALIDATE_MSG";
    public static final Long DEFAULT_VALUE = -1L;
    public static final String TIME_ZONE_DEFAULT = "GMT+7";
    public static final int WIDTH = 255;

    public interface BCCS_SALE_TRANS_TYPE { //38: CSKH; 40: Quang cao

        public static final String CUSTOMERCARE = "38";
        public static final String ADVERTISE = "40";
    }

    //CP_ALIAS_TMP_APPROVE
    public static final short CP_ALIAS_TMP_APPROVE_NEW = 0;
    public static final short CP_ALIAS_TMP_APPROVE_PENDING = 1;
    public static final short CP_ALIAS_TMP_APPROVE_APPROVE = 2;
    public static final short CP_ALIAS_TMP_APPROVE_DENY = 3;
    public static final short CP_ALIAS_TMP_APPROVE_PENDING_CANCEL = 4;
    public static final short CP_ALIAS_TMP_APPROVE_APPROVE_CANCEL = 5;
    public static final short CP_ALIAS_TMP_APPROVE_DENY_CANCEL = 6;
    public static final short CP_ALIAS_TMP_APPROVE_PENDING_EDIT = 7;
    public static final short CP_ALIAS_TMP_APPROVE_DENY_EDIT = 8;
    public static final short CP_ALIAS_TMP_APPROVE_PENDING_RESTORE = 9;
    public static final short CP_ALIAS_TMP_APPROVE_DENY_RESTORE = 10;

    // CP_ALIAS_ALIAS_TYPE
    //1:Quang cao, 0:Cham soc khach hang

    public static final short CP_ALIAS_ALIAS_TYPE_ADVERTISE = 1;
    public static final short CP_ALIAS_ALIAS_TYPE_CUSTOMER_CARE = 0;

    public static final short CP_ALIAS_EXITS_TAX_CODE_OR_COMPANY_NAME = 112;
    public static final short SUCCESS = 200;

    public static final short INACTIVE = 0;
    public static final short ACTIVE = 1;

    public static final short PRICE_LEVEL_TYPE_LESS = 0;
    public static final short PRICE_LEVEL_TYPE_EQUAL_GREATER = 1;

    private Constants() {
    }

    public static final char DEFAULT_ESCAPE_CHAR = '&';
    public static final char DEFAULT_CONTAINS_CHAR = '%';

    public static final Integer TIME_TYPE_DATE = 1;
    public static final Integer TIME_TYPE_MONTH = 2;
    public static final Integer TIME_TYPE_QUARTER = 3;
    public static final Integer TIME_TYPE_YEAR = 4;


    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String TIME_FORMAT_TO_SECOND = "yyyyMMddhhmmss";
    public static final String TIME_FORMAT_TO_MILISECOND = "yyyyMMddHHmmssSSS";
    public static final String VND = "VND";
    public static final String USD = "USD";
    public static final String PRE = "PRE";

    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_INACTIVE = 0;

    public static final String DELETE = "delete";
    public static final Integer ACCOUNT_LEVEL_DISTRICT = 0;
    public static final Long ACCOUNT_TYPE_FINANCE = 1L;


    public interface ERR_CONSTANTS {
        String DUPLICATE = "existCheck";
        String PATTERN = "pattern";
        String ROLLBACK = "rollback";
        String FAILURE =  "failure";
    }

    public interface SEX {
        Long MALE = 1L;
        Long FEMALE = 2L;
    }

    public static interface TRANSACTION_STATUS {
        public static final String SUCCESS = "0";
        public static final String FAIL = "1";
    }

    public interface RESULT {
        public static final String ADD_SUCCESS_STR = "insertOk";
        public static final String EDIT_SUCCESS_STR = "updateOk";
        public static final String DEL_SUCCESS_STR = "deleteOk";
        public static final String ADD_FAIL_STR = "insertFail";
        public static final String EDIT_FAIL_STR = "updateFail";
        public static final String DEL_FAIL_STR = "deleteFail";
        public static final String EXISTS_DATA = "isExistsData";
        public static final String SWITCH_SUCCESS = "switchOk";
        public static final String SWITCH_FAIL = "switchFail";
        public static final String ERROR = "error";
        public static final String SOURCE_ENABLE_ALL_FAIL = "enableAllFail";
        public static final String SOURCE_DISABLE_ALL_FAIL = "disableAllFail";
        public static final int OPEN_SOURCE_OK = 1;
        public static final int OPEN_SOURCE_FAIL = 0;

        public static final class ADCATEGORY{
            public static final String CAT_CODE_NULL = "fieldNullcatCode";
            public static final String CAT_NAME_NULL = "fieldNullcatCode";
            public static final String TO_LONG_VALUE = "fieldtooLongCategory";
            public static final String CAT_CODE_EXISTED = "exitsCategoryCode";
            public static final String CAT_NAME_EXISTED = "exitsCategoryName";
        }
        public static final class AD_PACKAGE{
            public static final String PACK_AGE_NAME_NULL = "nullpackageName";
            public static final String TO_LONG_VALUE = "tooLargeValue";
            public static final String PACK_AGE_NAME_EXISTED = "existsData";
            public static final String ID_NULL_OR_ZERO = "idNullOrZero";
            public static final String STATUS_INVALID = "statusInvalid";
        }
    }

    public interface REALM_ROLE {
        public static final String WEBPUBLIC_CP = "WEBPUBLIC_CP";
        public static final String WEBPUBLIC_CP_USD = "WEBPUBLIC_CP_USD";
        public static final String WEBPUBLIC_CP_LIMIT = "WEBPUBLIC_CP_LIMIT";
        public static final String WEBPUBLIC_CP_LBS = "WEBPUBLIC_CP_LBS";
        public static final String DELETE_CP = "bulkcms.admin.cpDelete";
    }

    public static final String MAIL_FORMAT_ERROR = ""
        + "<html>\n"
        + "	<head> <title></title> </head>\n"
        + "	<body>\n"
        + "		<p>Nội dung lỗi:<strong>{CONTENT}</strong></p>\n"
        + "	</body> \n"
        + "</html>";

    public static final String MAIL_FORMAT_CP_REG = ""
        + "<html>\n"
        + "	<head> <title></title> </head>\n"
        + "	<body>\n"
        + "		<p>Dear all,</p>\n"
        + "		<p>Hệ thống SMS Brandname gửi thông tin KH đăng ký mới. Đề nghị các đồng chí vào kiểm tra thông tin.</p>\n"
        + "		<p>Thông tin KH:</p>\n"
        + "		<ul>\n"
        + "			<li>Mã KH: <strong>{CP_CODE}</strong></li>\n"
        + "			<li>Tên KH: <strong>{CP_NAME}</strong></li>\n"
        + "			<li>Địa chỉ: <strong>{ADDRESS}</strong></li>\n"
        + "			<li>Nhân viên phát triển: <strong>{STAFF}</strong></li>\n"
        + "		</ul>\n"
        + "	</body> \n"
        + "</html>";
}
