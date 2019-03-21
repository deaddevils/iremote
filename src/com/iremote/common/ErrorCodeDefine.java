package com.iremote.common;

public class ErrorCodeDefine {

public static final int SUCCESS = 0 ;
public static final String SUCCESS_STR = "0" ;
public static final int DEVICE_NOT_EXIST = 10001;
public static final int CODE_LIBERAY_NOT_FOUND = 10002;
public static final int UNKNOW_ERROR = 10005;
public static final int TRANSOFFLINE = 10004;
public static final int TIME_OUT = 10006;
public static final int REMOTE_PASSWORD_ERROR = 10007;
public static final int CODELIBERAY_ID_ERROR = 10008;

public static final int USERNAME_CRASH = 10009;
public static final int USERNAME_OR_PASSWORD_WRONG = 10010;
public static final int SESSION_TIMEOUT =10012;

public static final int USE_BACKUP_DATA_NOT_EXSIT =10013;
public static final int USERNAME_NOT_EXSIT =10016;
public static final int USERNAME_OR_ANSWER_WRONG =10017;

public static final int SYSTEM_BUSY = 10020;
public static final int NO_PRIVILEGE = 10022 ;
public static final int TARGET_NOT_EXSIT = 10023;

public static final int USER_HAS_REGISTED  = 10100;
public static final int USER_HAS_NOT_REGISTED  = 10101;
public static final int EXCEED_MAX_REQUEST_TIMES = 10103;
public static final int RAND_CODE_ERROR = 10104;
public static final int RAND_CODE_TIME_OUT = 10105;

public static final int	SMS_SEND_FAILED = 10200;
public static final int	PUSH_MESSAGE_SEND_FAILED =10201;

public static final int	USER_HAS_NOT_REGISTED_2 = 10210;
public static final int	SHARE_INVITATION_EXPIRED = 10211;
public static final int	SHARE_INVITATION_DELETED = 10212;
public static final int	SHARE_INVITATION_ACCEPTED = 10213;

public static final int	SYNCHRONIZED_DATA_CRASH = 10220;

public static final int EXECUTE_STATUS_NOT_START = 30000;
public static final int USERNAME_OR_PASSWORD_WRONG_3 = 30010;
public static final int SESSION_TIMEOUT_3 =30012;

public static final int NOT_SUPPORT =30021;
public static final int NO_PRIVILEGE_3 =30022;

public static final int USER_HAD_RECEIVED_FAMILI_SHARING = 30212;
public static final int OTHER_HAD_RECEIVED_FAMILI_SHARING = 30213;
public static final int ALREADY_HAS_HIGHER_LEVER_SHARING = 30215;

public static final int TOKEN_ERROR = 30300;
public static final int DEVICE_NOT_EXSIT = 30311;
public static final int DEVICE_OFFLINE = 30312;
public static final int WAKEUP_DEVICE = 30313;
public static final int DEVICE_RETURN_ERROR = 30314;
public static final int PARMETER_ERROR = 30315;
public static final int DEVICE_HAS_EXIST = 30316;
public static final int SECURITYECODE_PHONENUMBER_NOT_MATCH = 30317;
public static final int UNKOWN_DEVICE_TYPE = 30318;
public static final int DEVICE_NOT_READY = 30319;
public static final int PASSWORD_REQUIRED = 30320;
public static final int DSC_PASSWORD_ERROR = 30321;
public static final int PARTITION_BUSY = 30322;
public static final int PARTITION_CANNOT_DELETE = 30323;
public static final int DSC_CANNOT_BE_ADDED_TO_PARTITION = 30324;
public static final int NON_SENSOR_CANNOT_BE_ADDED_TO_PARTITION = 30325;

public static final int HTTPS_REQUIRED = 30400;
public static final int USER_SHARE_CANNOT_RAISE_FAMILY_SHARE = 30410 ;
public static final int USER_SHARE_CAN_ACCEPT_ONLY_ONE_FAMILY_SHARE = 30411 ;
public static final int USER_SHARE_SAME_OR_SUPER_ITEM_EXISTS = 30412 ;

public static final int PHONEUSER_ARM_SOME_DOORS_OR_WINDOWS_IS_OPENING = 30500;


public static final int MAIL_HAS_REGISTED = 30501;
public static final int MAIL_FORMAT_ERROR = 30502;
public static final int MAIL_REGISTED_FAIL = 30503;
public static final int MAIL_SEND_FAIL = 30504;

public static final int DOORLOCK_SETPASSWORD_FAILED = 30510;
public static final int DOORLOCK_SETPASSWORD_CLASH = 30511;
public static final int DOORLOCK_PASSWORD_EXHAUST = 30512;
public static final int DOORLOCK_PASSWORD_DUPLICATE = 30513;
public static final int DOORLOCK_FORBID_REMOTE_OPEN = 30514;

public static final int FINGERPRINT_SET_FAILED = 30515;//0
public static final int FINGERPRINT_IS_FULL = 30516;//2
public static final int FINGERPRINT_DEVICE_NORESPONSE = 30517;//3
public static final int FINGERPRINT_WRITE_FAILED = 30518;//4
public static final int FINGERPRINT_STORAGE_FAILED = 30519;//5

public static final int DOORLOCK_PASSWORDSUCCESS_VALIDTIMEFAILED = 30580;
public static final int CANNOT_FIND_DOORLOCKUSER = 30581;
public static final int DOORLOCK_USER_DELETE_FAILED = 30582;

public static final int AUTH_IMAGE_CODE_ERROR = 30520;

public static final int COMMUNITY_ADMIN_LOGIN_NAME_INVALID = 30530;

public static final int ENCYRPTED_COMMUNICATION_NOT_SUPPORT = 30540 ;
public static final int MUST_USE_ENCYRPTED_COMMUNICATION = 30541 ;
public static final int DECYRPTE_FAILED = 30542 ;

public static final int GATEWAY_ERROR_CODE_BUSSING = 10024 ;

public static final int SCENE_NOT_EXIST = 30543;
public static final int STATUS_CARD_OVERFLOW = 30544;
public static final int NAME_IS_EXIST = 30545;
public static final int ASSOCIATIONSCENE_NOT_EXIST = 30546;
public static final int PHONE_USER_HAS_NOT_SET_CITY = 30547;

public static final int THIRDPART_NETWORK_FAILED = 30550 ;
public static final int THIRDPART_CALL_FAILED = 30551 ;

public static final int GATEWAY_BUSSING = 30560 ;

public static final int INFRARED_CODE_LIBRARY_NOT_EXISTS = 30570 ;

public static final int INFRAREDKEY_NOT_EXIST = 30660 ;

public static final int DEVICE_NOT_AUTHORIZED = 30670;
public static final int APPLIANCE_HAD_ADDED = 30671;
public static final int QR_CODE_INVALID = 30672;
public static final int QR_CODE_HAD_USED = 30673;
}
