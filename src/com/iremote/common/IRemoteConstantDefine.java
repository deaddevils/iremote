package com.iremote.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IRemoteConstantDefine {

	public static final int DIS_ABLE_WEEK = 128;

	public static final int CAMERA_NEED_NOT_AUTH_CODE = 0;
	public static final int CAMERA_NEED_AUTH_CODE = 1;

    public static final int NOTIFICATION_ECLIPSE_BY_NORMAL = 0;
    public static final int NOTIFICATION_ECLIPSE_BY_PARTITION_STATUS_DO_NOT_NEED_CHANGE = 5;

	public static final int REMOTE_STATUS_OFFLINE = 0 ;
	public static final int REMOTE_STATUS_ONLINE = 1 ;
	public static final int REMOTE_STATUS_SLEEP = 2 ;
	public static final int REMOTE_STATUS_FORBIDDEN = 3 ;
	
	public static final int DOORLOCKUSER_TYPE_PASSWORD = 1;
	public static final int DOORLOCKUSER_TYPE_CARD = 2;
    public static final int DOORLOCKUSER_TYPE_FINGERPRINT = 3;
    
	public static final int SCENE_TIME_TYPE_SUNRISE = 1;
	public static final int SCENE_TIME_TYPE_SUNSET = 2;

	public static final int DSC_TO_ARM_STATUS_DIS_ARM = 0;
	public static final int DSC_TO_ARM_STATUS_ARM = 1;

	public static final int PARTITION_STATUS_DIS_ARM = 0;
    public static final int PARTITION_STATUS_OUT_HOME = 1;
    public static final int PARTITION_STATUS_IN_HOME = 3;
    public static final int PARTITION_ARM_STATUS_EXIT_DELAY_IN_PROGRESS = 4;
    public static final int PARTITION_ARM_STATUS_ENTRY_DELAY_IN_PROGRESS = 5;

	public static final int DSC_CHANNEL_READY = 0;
	public static final int DSC_CHANNEL_NOT_READY = 1;

	public static final int DOOR_LOCK_STATUS_MALFUNCTION = 0;
	public static final int DOOR_LOCK_STATUS_NORMAL = 1;

	public static final int DSC_PARTITION_READY = 0;
	public static final int DSC_PARTITION_NOT_READY = 1;

	public static final int DEFAULT_USER_CODE = 0;

    public static final int DSC_PARTITION_ARLAM_STATUS_NOT_IN_PROCESSOR = 0;
    public static final int DSC_PARTITION_ARLAM_STATUS_IN_PROCESSOR = 1;
    public static final int DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS = 2;
    public static final int DSC_PARTITION_ARLAM_STATUS_PASSWORD_ERROR = 10;
    public static final int DSC_PARTITION_ARLAM_STATUS_PARTITION_NOT_READY = 11;
    public static final int DSC_PARTITION_ARLAM_STATUS_PROCESSOR_FAIL = 12;
    public static final int DSC_PARTITION_ARLAM_STATUS_PROCESSOR_TIMEOUT = 13;
    public static final int DSC_PARTITION_ARLAM_STATUS_PROCESSOR_NEED_PASSWORD = 14;
    public static final int DSC_PARTITION_ARLAM_STATUS_PROCESSOR_PARTITION_NOT_EXSIT = 16;

    public static final int DEVICE_CAPABILITY_CODE_STANDARD_ZWAVE_DOOR_LOCK = 13;
    public static final int DEVICE_CAPABILITY_CODE_BAUD_RATE = 1;

    public static final int TASK_ZWAVE_PARTITION_DELAY_ARM = 1;
    public static final int TASK_PHONEUSER_DELAY_ARM= 2;
    public static final int TASK_SENSOR_DELAY_ALARM = 3;
    public static final int TASK_DSC_CHANNEL_ALARM_RESTORE = 4;
    public static final int TASK_CAMER_DELAY_RESTORE = 5;
    public static final int TASK_DSC_F_KEY_UN_ALARM = 6;
    public static final int TASK_DSC_A_KEY_UN_ALARM = 7;
    public static final int TASK_DSC_P_KEY_UN_ALARM = 8;

	public static final int HEART_BEAT_PUSH_TAG_MAX_COUNT = 10;
	public static final int HEART_BEAT_LONG_MSG_TIME = 45 * 60 * 1000;

	public static final int TIMER_TASK_EXPIRE_TIME = 20 * 60 * 1000;

	public static final int DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE = 310;
	public static final int DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED = 311;

	public static final int DEVICE_CAPABILITY_DRESS_HELPER_AREA_ID = 1;

	public static final int REMOTE_POWER_TYPE_BATTERY = 1 ;
	public static final int REMOTE_POWER_TYPE_UNKNOW = 2 ;

	public static final int USER_SHARE_STATUS_WAIT_FOR_RESPONSE = 0 ;
	public static final int USER_SHARE_STATUS_NORMAL = 1 ;

	public static final int USER_SHARE_TYPE_NORMAL = 0;//share type :friend
	public static final int USER_SHARE_TYPE_FAMILY = 1;

	public static final int USER_SHARE_DEVICE_TYPE_ALL = 0;
	public static final int USER_SHARE_DEVICE_TYPE_SPECIFY = 1;

	public static final int RANDCODE_TYPE_PHONEUSER_REGISTE = 1;
	public static final int RANDCODE_TYPE_PHONEUSER_RESETPASSWORD = 2;
	public static final int RANDCODE_TYPE_MAILUSER_REGISTE = 3;
	public static final int RANDCODE_TYPE_MAILUSER_RESETPASSWORD = 4;

    public static final int USER_STATUS_ENABLED = 0;
    public static final int USER_STATUS_DISABLE = 1;

    public static final int DOOR_LOCK_ASSOCIATION_OBJ_TYPE_EXECUTE_SCENE = 1;
    public static final int DOOR_LOCK_ASSOCIATION_OBJ_TYPE_DISARM_PARTITION = 2;
    public static final int DOOR_LOCK_ASSOCIATION_OBJ_TYPE_IN_HOME_ARM_PARTITION = 3;
    public static final int DOOR_LOCK_ASSOCIATION_OBJ_TYPE_OUT_HOME_ARM_PARTITION = 4;

    public static final int PASSWORD_USER_TYPE_PASSWORD_USER = 1;
	public static final int PASSWORD_USER_TYPE_CARD_USER = 2;
	public static final int PASSWORD_USER_TYPE_FINGERPRINT_USER = 3;
	public static final int CARD_TYPE_MF = 11;
	public static final int CARD_TYPE_ID = 12;
	public static final int SEND_PASSWORD_TYPE_ASYNCH = 0;
	public static final int SEND_PASSWORD_TYPE_SYNCH = 1;

	public static final int AUTH_QR_CODE_STATUS_VALID = 0;
	public static final int AUTH_QR_CODE_STATUS_USED = 2;

	public static final String MATH_SYMBOL_EQ = "=";
	public static final String MATH_SYMBOL_GT = ">";
	public static final String MATH_SYMBOL_GE = ">=";
	public static final String MATH_SYMBOL_LT = "<";
	public static final String MATH_SYMBOL_LE = "<=";

	public static final String SESSION_USER = "IREMOTE_USER";
	public static final String SESSION_TOKEN_ID = "IREMOTE_TOKEN_ID";
	public static final String SESSION_THIRDPART = "SESSION_THIRDPART";
	public static final String DEFAULT_LANGUAGE = "zh_CN";
	public static final String DEFAULT_UNCH_LANGUAGE = "en_US";
	public static final String DEFAULT_FRCA_LANGUAGE = "fr_CA";
	public static final String DEFAULT_ZHHK_LANGUAGE = "zh_HK"; 
	public static final String DEFAULT_VIVN_LANGUAGE = "vi_VN";

	public static final String DEVICE_MAJORTYPE_ZWAVE = "zWave";
	public static final String DEVICE_MAJORTYPE_INFRARED = "infrared";
	public static final String DEVICE_MAJORTYPE_CAMERA = "camera";
	public static final String MAJORTYPE_PARTITION = "partition";

	public static final String DEVICE_TYPE_AC = "AC";
	public static final String DEVICE_TYPE_TV = "TV";
	public static final String DEVICE_TYPE_STB = "STB";
	public static final String DEVICE_TYPE_SMOKE = "1";
	public static final String DEVICE_TYPE_WATER = "2";
	public static final String DEVICE_TYPE_GAS = "3";
	public static final String DEVICE_TYPE_DOOR_SENSOR = "4";
	public static final String DEVICE_TYPE_DOOR_LOCK = "5";
	public static final String DEVICE_TYPE_MOVE = "6";
	public static final String DEVICE_TYPE_BINARY_SWITCH = "7";
	public static final String DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH = "8";
	public static final String DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH = "9";
	public static final String DEVICE_TYPE_ALARM = "10";
	public static final String DEVICE_TYPE_OUTLET = "11";
	public static final String DEVICE_TYPE_ROBOTIC_ARM = "12";
	public static final String DEVICE_TYPE_CURTAIN = "13";
	public static final String DEVICE_TYPE_AIR_CONDITIONER = "14";
	public static final String DEVICE_TYPE_TUJIA_ELECTRIC_METER = "15";
	public static final String DEVICE_TYPE_SOS_ALARM = "16";
	public static final String DEVICE_TYPE_WATER_METER = "17";
	public static final String DEVICE_TYPE_PEEPHOLE = "18";
	public static final String DEVICE_TYPE_LOCK_CYLINDER = "19";
	public static final String DEVICE_TYPE_DIMMER = "20";
	public static final String DEVICE_TYPE_DOOR_CONTROL = "22";
	public static final String DEVICE_TYPE_AIR_QUALITY = "23";
	public static final String DEVICE_TYPE_SCENE_PANNEL = "24";
	public static final String DEVICE_TYPE_2_CHANNEL_SCENE_PANNEL = "25";
	public static final String DEVICE_TYPE_3_CHANNEL_SCENE_PANNEL = "26";
	public static final String DEVICE_TYPE_4_CHANNEL_SCENE_PANNEL = "27";
	public static final String DEVICE_TYPE_AIR_CONDITIONER_OUT = "28";
	public static final String DEVICE_TYPE_NEW_WIND = "29";
	public static final String DEVICE_TYPE_KARAOK = "30";
	public static final String DEVICE_TYPE_POWER_FREE_SWITCH = "31";
	public static final String DEVICE_TYPE_POWER_FREE_2_CHANNEL_SWITCH = "32";
	public static final String DEVICE_TYPE_POWER_FREE_3_CHANNEL_SWITCH = "33";
	public static final String DEVICE_TYPE_POWER_FREE_4_CHANNEL_SWITCH = "34";
	public static final String DEVICE_TYPE_POWER_FREE_6_CHANNEL_SCENE_PANNEL = "35";
	public static final String DEVICE_TYPE_DEHUMIDIFY = "36";
	public static final String DEVICE_TYPE_DEHUMIDIFY_OUT = "37";
	public static final String DEVICE_TYPE_FRESH_AIR_OUT = "38";
	public static final String DEVICE_TYPE_BGM = "39";
	public static final String DEVICE_TYPE_FRESH_AIR_IN = "40";
	public static final String DEVICE_TYPE_2_CHANNEL_SWITCH_SCENE_PANNEL = "41";
	public static final String DEVICE_TYPE_3_CHANNEL_SWITCH_SCENE_PANNEL = "42";
	public static final String DEVICE_TYPE_4_CHANNEL_SWITCH_SCENE_PANNEL = "43";
	public static final String DEVICE_TYPE_ARM_STATUS_SETTER = "45";
	public static final String DEVICE_TYPE_RGB_COLOR_SWITCH = "46";
	public static final String DEVICE_TYPE_DSC = "47";

	public static final String DEVICE_TYPE_1_GAN_PORTABLE_SCENE_PANNEL = "48";
	public static final String DEVICE_TYPE_2_GAN_PORTABLE_SCENE_PANNEL = "49";
	public static final String DEVICE_TYPE_4_GAN_PORTABLE_SCENE_PANNEL = "50";
	public static final String DEVICE_TYPE_HEATING_CONTROLLER = "51";
	public static final String DEVICE_TYPE_3_GAN_PORTABLE_SCENE_PANNEL = "52";
	public static final String DEVICE_TYPE_FINGERPRING = "53";

	public static final String DEVICE_TYPE_GARAGEDOOR = "54";
	public static final String DEVICE_TYPE_WALLREADER = "55";

	public static final String DEVICE_TYPE_ELECTRIC_FENCE = "56";
	public static final String DEVICE_TYPE_2_CHANNEL_ELECTRIC_FENCE = "57";

	public static final String DEVICE_TYPE_DRESS_HELPER = "60";
	public static final String DEVICE_TYPE_PASS_THROUGH_DEVICE = "61";
	public static final String DEVICE_TYPE_INFRARED_PASS_THROUGH_DEVICE = "62";
	public static final String DEVICE_TYPE_PLASMA_DRYER = "63";
	public static final String DEVICE_TYPE_4_CHANNEL_BINARY_SWITCH = "64";

	public static final List<String> DEVICE_TYPE_LIST_LIKE_DOOR_LOCK = Arrays.asList(DEVICE_TYPE_DOOR_LOCK, DEVICE_TYPE_WALLREADER);
	public static final List<String> DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR = Arrays.asList(DEVICE_TYPE_DOOR_SENSOR, DEVICE_TYPE_GARAGEDOOR);
	public static final List<String> DEVICE_TYPE_LIST_WHICH_HAS_ASSOCIATION = Arrays.asList(DEVICE_TYPE_DOOR_LOCK, DEVICE_TYPE_WALLREADER);
    public static final List<String> DEVICE_TYPE_LIST_WHICH_HAS_TIMER_MALFUNCTION = Arrays.asList(DEVICE_TYPE_DOOR_LOCK, DEVICE_TYPE_WALLREADER, DEVICE_TYPE_DRESS_HELPER);

	public static final String JWZH_ZWAVE_PRODUCTOR2 = "00";
	public static final String JWZH_ZWAVE_PRODUCTOR = "021c";
	public static final String JWZH_CHUANGJIA_PRODUCTOR_PREFIX = "015e";
	public static final String JWZH_CHUANGJIA_PRODUCTOR_PREFIX_2 = "015e80770001";

	public static final int DSC_ZONE_WARNING_TYPE_FIRE = 110;
	public static final int DSC_ZONE_WARNING_TYPE_DEDICAL = 100;
	public static final int DSC_ZONE_WARNING_TYPE_SMOKE = 111;
	public static final int DSC_ZONE_WARNING_TYPE_WATERLEAKAGE = 154;
	public static final int DSC_ZONE_WARNING_TYPE_GASDETECTED = 151;
	public static final int DSC_ZONE_WARNING_TYPE_BURGLARY = 130;
	public static final int DSC_ZONE_WARNING_TYPE_SOS = 120;
	public static final int DSC_ZONE_WARNING_TYPE_DOORWINDOW = 131;
	public static final int DSC_ZONE_WARNING_TYPE_MOTION = 132;

	public static final int DSC_WARNING_TYP_FIRE_ALARM = 1;
	public static final int DSC_WARNING_TYP_AUXILIARY_ALARM = 2;
	public static final int DSC_WARNING_TYP_PANIC_ALARM = 3;

	public static final List<Integer> DSC_WARNING_TYPE_3_ALARM_KEYS = Arrays.asList(DSC_WARNING_TYP_FIRE_ALARM, DSC_WARNING_TYP_PANIC_ALARM, DSC_WARNING_TYP_AUXILIARY_ALARM);

	public static final String WARNING_TYPE_DSC_FIRE_KEY_ALARM = "dscfkeyalarm";
	public static final String WARNING_TYPE_DSC_AUXILIARY_KEY_ALARM = "dscakeyalarm";
	public static final String WARNING_TYPE_DSC_PANIC_KEY_ALARM = "dscpkeyalarm";
	public static final String WARNING_TYPE_DSC_UN_ALARM_FIRE_KEY_ALARM = "unalarmdscfkeyalarm";
	public static final String WARNING_TYPE_DSC_UN_ALARM_AUXILIARY_KEY_ALARM = "unalarmdscakeyalarm";
	public static final String WARNING_TYPE_DSC_UN_ALARM_PANIC_KEY_ALARM = "unalarmdscpkeyalarm";
	public static final String NOTIFICATION_DSC_KEY_ALARM = "dsckeyalarm";

	public static final List<String> DSC_ZONE_WARNING_TYPES_NOT_SAFE = Arrays.asList("154", "120", "111", "151", "110");

	public static final int DEVICE_KARAOK_MODE_TV = 1;
	public static final int DEVICE_KARAOK_MODE_CINEMA = 2;
	public static final int DEVICE_KARAOK_MODE_KARAOKE = 3;
	public static final int DEVICE_KARAOK_MODE_MUSIC = 4;

	public static final int DEVICE_KARAOK_VOLUME_MUSIC = 1;
	public static final int DEVICE_KARAOK_VOLUME_MIC = 2;
	public static final int DEVICE_KARAOK_VOLUME_EFFECTS = 3;

	public static final String DEVICE_KARAOK_DEFAULT_STATUES = "[1,50,50,50]";

	public static final int DEVICE_NEW_WIND_HIGH_GEAR = 255;
	public static final int DEVICE_NEW_WIND_DOWN_GEAR = 0;

	public static final int DEVICE_NEW_WIND_SWITCH = 0x02;
	public static final int DEVICE_NEW_WIND_GEAR = 0x01;

	public static final int DEVICE_STATUS_MALFUNCTION = -1 ;
	public static final int DEVICE_STATUS_NORMAL = 0 ;

	public static final int DEVICE_NUID_WIFI_LOCK = 11201;
	public static final int DEVICE_NUID_DSC = 11401;

	public static final String DEVICE_OPERATION_ON = "on";
	public static final String DEVICE_OPERATION_OFF = "off";
	public static final String DEVICE_OPERATION_OPEN = "open";
	public static final String DEVICE_OPERATION_CLOSE = "close";
	public static final String DEVICE_OPERATION_STOP = "stop";
	public static final String DEVICE_OPERATION_SET_STATUS = "setstatus";
	public static final String DEVICE_OPERATION_E_FENCE_ARM = "efencearm";
	public static final String DEVICE_OPERATION_E_FENCE_DIS_ARM = "efencedisarm";
	public static final String DEVICE_OPERATION_SEND_CMD = "sendcmd";

	public static final String INTERNATIONAL_DIALING_CODE_CHINE = "86";
	public static final String INTERNATIONAL_DIALING_CODE_NORTH_AMERICA = "1";
	public static final String INTERNATIONAL_DIALING_CODE_AUSTRALIA = "61";
	public static final String INTERNATIONAL_DIALING_CODE_NEWZEALAND = "64";
	public static final String INTERNATIONAL_DIALING_CODE_INDIA = "91";

	public static final int NETWORK_WIFI = 0 ;
	public static final int NETWORK_GPRS = 1 ;
	public static final int NETWORK_WIRED = 2 ;
	public static final int NETWORK_CDMA_1X = 3;
	public static final int NETWORK_TD_SCDMA = 4;
	public static final int NETWORK_WCDMA = 5;
	public static final int NETWORK_EVDO = 6;
	public static final int NETWORK_TD_LTE = 7;
	public static final int NETWORK_FDD_LTE = 8;
	public static final int NETWORK_NBIOT = 9;
    public static final List<Integer> NETWORK_3G = Arrays.asList(NETWORK_GPRS, NETWORK_CDMA_1X, NETWORK_TD_SCDMA
            , NETWORK_WCDMA, NETWORK_EVDO, NETWORK_TD_LTE, NETWORK_FDD_LTE);

	public static final int BACKLOCK = 1 ;
	public static final int FAHRENHEIT = 1 ;

	public static final int DEFAULT_TIME_OUT_SECOND = 8 ;
	
	public static final int ADDING_FINGER_PRINT_USER_STATUS_SUCCESS = 7;

	public static final String DOOR_LOCK_TEMP_USER = "tempuser";
	public static final String DOOR_LOCK_USER = "nmusr";
	public static final String DOOR_LOCK_ADMIN_USER = "adminusr";

	public static final String MESSAGE_KEY_DOOR_LOCK_TEMP_USER = "templockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_USER = "lockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_KEY_USER = "keylockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_CARD_USER = "cardlockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER = "passwordlockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_FINGER_USER = "fingerprintslockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_ADMIN_USER = "lockadmin";
	public static final String MESSAGE_KEY_DOOR_LOCK_FACE_USER = "facelockuser";
	public static final String MESSAGE_KEY_DOOR_LOCK_AUTO_CLOSE = "lockautoclose";
	public static final String MESSAGE_KEY_DOOR_LOCK_BLUE_TOOTH_USER = "bluetoothuser";
	public static final String MESSAGE_LOCK_PASSWORD_REFRESHED = "lockpasswordrefreshed";

	public static final String SYNC_KEY_SETPASSWORD = "SETPASSWORD";
	public static final String SYNC_KEY_SETTIMESPAN = "SETTIMESPAN";
	public static final String SYNC_KEY_DELETE_PASSWORD = "DELETEPASSWORD";


	public static final String WARNING_TYPE_TAMPLE ="tampleralarm";
	public static final String WARNING_TYPE_SOS ="sos";
	public static final String WARNING_TYPE_UNALARM_TAMPLE ="unalarmtampleralarm";
	public static final String WARNING_TYPE_SMOKE ="smoke";
	public static final String WARNING_TYPE_GAS_LEAK ="gasleak";
	public static final String WARNING_TYPE_WATER_LEAK ="waterleak";
	public static final String WARNING_TYPE_DOOR_LOCK_OPEN ="doorlockopen";
	public static final String EVENT_TYPE_DOOR_LOCK_CLOSE ="doorlockclose";
	public static final String WARNING_TYPE_DOOR_LOCK_OPEN_DUPLICATED ="doorlockopen_duplicated";
	public static final String WARNING_TYPE_RESTORE = "restore";

	public static final String WARNING_TYPE_DOOR_BELL_RING ="doorbellring";
	public static final String WARNING_TYPE_PEEPHOLE_BELL_RING ="peepholebellring";
	public static final String WARNING_TYPE_SWITCH_OPEN ="open";
	public static final String WARNING_TYPE_SWITCH_CLOSE ="close";
	public static final String WARNING_TYPE_CURTAIN_OPEN ="open";
	public static final String WARNING_TYPE_CURTAIN_CLOSE ="close";
	public static final String WARNING_TYPE_DIMMER_OPEN ="open";
	public static final String WARNING_TYPE_DIMMER_CLOSE ="close";
	public static final String WARNING_TYPE_SOS_OPEN ="open";
	public static final String WARNING_TYPE_SOS_CLOSE ="close";
	public static final String DEVICE_STATUS_OPEN = "open";
	public static final String DEVICE_STATUS_CLOSE = "close";
	public static final String WARNING_TYPE_POWER ="power";

	public static final String WARNING_TYPE_MODE ="mode";
	public static final String WARNING_TYPE_FAN ="fan";

	public static final String CAMERA_WARNING_TYPE_MOVE ="cameradectectmove";

	public static final String WARNING_TYPE_MOVEIN ="movein";
	public static final String WARNING_TYPE_MOVEOUT ="moveout";
	public static final String WARNING_TYPE_DOOR_OPEN ="dooropen";
	public static final String WARNING_TYPE_DOOR_CLOSE ="doorclose";
	public static final String WARNING_TYPE_DOOR_OPEN_DELAY_WARNING = "dooropendelaywarning";
	public static final String WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING = "doorlockopendelaywarning";
	public static final String WARNING_TYPE_MOVE_IN_DELAY_WARNING = "moveindelaywarning";
	public static final String WARNING_TYPE_DOOR_WARNING_OPEN ="doorwarningopen";
	public static final String WARNING_TYPE_REMOTE_OFFLINE ="remoteoffline";
	public static final String WARNING_TYPE_REMOTE_ONLINE ="remoteonline";
	public static final String WARNING_TYPE_REMOTE_NET_WORK_CHANGED ="remoteNetworkChanged";
	public static final String WARNING_TYPE_REMOTE_NET_WORK_CHANGED_SUFFIX_3G ="_3G";
	public static final String WARNING_TYPE_REMOTE_NET_WORK_CHANGED_SUFFIX_WIFI ="_WIFI";
	public static final String WARNING_TYPE_UDP_GATEWAY_ONLINE ="udpgatewayonline";
	public static final String WARNING_TYPE_CAMERA_OFFLINE ="cameraoffline";
	public static final String WARNING_TYPE_CAMERA_ONLINE ="cameraonline";
	public static final String WARNING_TYPE_LOW_BATTERY ="lowbattery";
	public static final String WARNING_TYPE_MALFUNCTION ="malfunction";
	public static final String WARNING_TYPE_RECOVER ="recovery";
	public static final String WARNING_TYPE_GO_HOME ="gohome";
	public static final String WARNING_TYPE_GO_OUT ="goout";
	public static final String WARNING_TYPE_DEVICE_ENABLE ="enabledevice";
	public static final String WARNING_TYPE_DEVICE_DISABLE ="disabledevice";
	public static final String WARNING_TYPE_USER_ARM ="arm";
	public static final String WARNING_TYPE_USER_INHOME_ARM ="inhomearm";
	public static final String WARNING_TYPE_USER_DISARM ="disarm";
	public static final String WARNING_TYPE_PASSWORD_ERROR_5_TIMES ="passworderror5times";
	public static final String WARNING_TYPE_LOCK_NOT_CLOSE ="doorlocknotclose";
	public static final String WARNING_TYPE_LOCK_BULLY ="bulliedopenlock";
	public static final String WARNING_TYPE_LOCK_KEY_ERROR ="lockkeyerror";
	public static final String WARNING_TYPE_LOCK_KEY_EVENT ="lockkeyevent";
	public static final String WARNING_TYPE_LOCK_LOCK_ERROR ="locklockerror";
	public static final String MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE = "lockopeninside";
	public static final String WARNING_TYPE_REMOTE_BATTERY_POWERD ="remotebatterypowerd";
	public static final String WARNING_TYPE_POWER_OVER_LOAD ="poweroverload";
	public static final String DAHUA_CAMERA_REPORT = "dohuacamerareport";
	public static final String WARNING_TYPE_DSC_ALARM = "dscalarm";
	public static final String WARNING_TYPE_LEVEL = "level";
	public static final String WARNING_PARTITION_ARM = "partitionarm";
	public static final String MESSAGE_PARTITION_DIS_ARM_USER_CODE = "partitiondisarmusercode";
	public static final String MESSAGE_PARTITION_ARM_USER_CODE = "partitionarmusercode";
	public static final String MESSAGE_PARTITION_ARM = "partitionarm";
	public static final String MESSAGE_PARTITION_HOME_ARM = "partitioninhomearm";
	public static final String MESSAGE_PARTITION_DIS_ARM = "partitiondisarm";
	public static final String MESSAGE_PARTITION_ARM_WITHOUT_CODE = "partitionarmwithoutcode";
	public static final String ADDING_FINGER_PRINT_USER_STATUS = "addingfingerprintuserstatus";
	public static final String MESSAGE_TYPE_EFANCE_DISCONNECTION = "efencedisconnection";
	public static final String MESSAGE_TYPE_EFANCE_SHORTCIRCUIT = "efenceshortcircuit";
	public static final String MESSAGE_TYPE_EFANCE_TAMPLER = "efencetampler";
	public static final String MESSAGE_TYPE_EFANCE_INSTRUSION = "efenceinstrusion";
	public static final String MESSAGE_TYPE_EFANCE_CONTACT = "efencecontact";
	public static final String MESSAGE_TYPE_EFANCE_SAMELINESHORTCIRCUIT = "efencesamelineshortcircuit";
	public static final String MESSAGE_TYPE_EFANCE_POWEROFF = "efencepoweroff";

	public static final String MESSAGE_TYPE_LOCK_NETWORK_INFO = "locknetworkinfo";

	public static final String WARNING_TYPE_DOOR_CONTROL_CLONE_CARD ="clonecard";

	public static final String MESSAGE_TYPE_REMOTE_RESET = "factoryreset";
	public static final String MESSAGE_TYPE_LOCK_RESET = "lockfactoryreset";

	public static final String WARNING_TYPE_DEVICE_ASSOCIATION = "deviceassociation";
	public static final String WARNING_TYPE_GATEWAY_TEMPERATUREA_ASSOCIATION = "gatewaytemperatureaassociation";
	public static final String WARNING_TYPE_COBBE_DOOR_LOCK_STATUS ="cobbedoorlockstatus";
	public static final String WARNING_TYPE_DEVICE_STATUS ="devicestatus";
	public static final String WARNING_TYPE_METER_STATUS ="meterstatus";
	public static final String WARNING_TYPE_BATTERY ="battery";
	public static final String WARNING_TYPE_GOOUT_WARNING ="gooutwarning";
	public static final String WARNING_TYPE_ARM_STATUS_CHANGE ="armstatuschange";
    public static final String WARNING_TYPE_DEVICE_ARM_STATUS_CHANGE ="devicearmstatuschange";
    public static final String WARNING_TYPE_ELECTRIC_CURRENT ="electriccurrent";
	public static final String WARNING_TYPE_TEMPERATURE ="temperature";
	public static final String WARNING_TYPE_HUMIDITY ="humidity";
	public static final String WARNING_TYPE_ARM_FAILED ="armfailed";
	public static final String WARNING_TYPE_ARM_FAILED2 ="armfailed2";
    public static final String WARNING_TYPE_ARM_SUCCESS_DEVICE_FAILED = "armsuccessdevicefailed";
	public static final String WARNING_TYPE_DOOR_LOCK_REMOTE_UNLOCK_FORBIDDEN = "remoteunlockforbidden";
	public static final String NOTIFICATION_TYPE_PARTITION_PASSWORD_WRONG = "partitionpasswordwrong";
	public static final String NOTIFICATION_TYPE_SCENE_SET_PARTITION_ARMSTATUS_FAILED = "setpartitionarmstatusfailed";
	public static final String WARNING_TYPE_DEVICE_ARM = "armdevice";
	public static final String WARNING_TYPE_DEVICE_DISARM = "disarmdevice";
	public static final String NOTIFICATION_TYPE_ASSOCIATE_PARTITION_ARM_FAIED= "associatepartitionarmfailed";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_DIS_CONNECTION= "efencedisconnection";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_SHORT_CIRCUIT= "efenceshortcircuit";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_TAMPLER= "efencetampler";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_INSTRUSION= "efenceinstrusion";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_CONTACT = "efencecontact";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_SAME_LINE_SHORT_CIRCUIT = "efencesamelineshortcircuit";
	public static final String NOTIFICATION_TYPE_ELECTRIC_FENCE_POWER_OFF = "efencepoweroff";
	public static final String NOTIFICATION_TYPE_DOOR_LOCK_OPEN_BY_BLUE_TOOTH = "doorlockopenbybluetooth";
	public static final String NOTIFICATION_TYPE_BLUE_TOOTH_SEQUENCE_CHANGED = "bluetoothsequencechanged";
	public static final String NOTIFICATION_TYPE_BLUE_TOOTH_KEY_REFRESH = "bluetoothkeyrefresh";
	public static final String NOTIFICATION_TYPE_LOCK_TIMES_SYNCHRONIZED = "locktimesynchronized";

	public static final String DEVICE_ARMSTATUS ="armstatus";

	public static final String WARNING_TYPE_SMS_RUN_OUT = "smsrunout";
	public static final String MESSGAE_TYPE_DOORBELL_RING = "doorbellring";
	public static final String MESSGAE_TYPE_RAND_CODE = "randcode";
	public static final String MESSGAE_TYPE_INVATION = "invation";
	public static final String MESSGAE_TYPE_SCENE_PANNEL_TRIGGER = "scenepanneltrigger";
	public static final String MESSGAE_TYPE_TRIGGER = "trigger";
	public static final String MESSGAE_TYPE_INVATION_SUBJECT = "invation_subject";
	public static final String MESSGAE_TYPE_INVATION_CONTENT = "invation_content";

	public static final String SET_CHANNEL_ENABLE_STATUS_TASK = "setchannelenablestatustask";
	public static final String SET_ARM_WITH_NO_DELAY = "setarmwithnodelay";
	public static final String ADD_LOCK_USER_RESULT = "addlockuserresult";
	public static final String DELETE_LOCK_USER_RESULT = "deletelockuserresult";

	public static final String MESSGAE_TYPE_CHANNEL = "channel";
	public static final String MESSAGE_TYPE_DSC_CHANNEL = "dscchannel";
	public static final String MESSAGE_TYPE_DSC_PARTITION = "partition";

	public static final int WARNING_ARM_STATUS_FIRE = 110;

	public static final String MESSAGE_TYPE_TEMP_PASSWORD = "sendtemppassword";

	public static final String ASSOCIATION_SCENE_TASK_OPERATOR = "associationtask";
	public static final String DEVICE_TIMER_TASK_OPERATOR = "devicetimertask";

	public static final String SYSTEMPARAMETER_USER_MAIL_SERVER_HOST = "user_mail_server_host";
	public static final String SYSTEMPARAMETER_USER_MAIL_SERVER_POST = "user_mail_server_post";
	public static final String SYSTEMPARAMETER_USER_MAIL_USERNAME = "user_mail_username";
	public static final String SYSTEMPARAMETER_USER_MAIL_PASSWORD = "user_mail_password";
	public static final String SYSTEMPARAMETER_USER_MAIL_FROMADDRESS = "user_mail_fromaddress";
	public static final String SYSTEMPARAMETER_USER_MAIL_VALIDATE = "user_mail_validate";

	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_SERVER_HOST = "support_mail_server_host";
	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_SERVER_POST = "support_mail_server_post";
	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_USERNAME = "support_mail_username";
	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_PASSWORD = "support_mail_password";
	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_FROMADDRESS = "support_mail_fromaddress";
	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_VALIDATE = "support_mail_validate";
	public static final String SYSTEMPARAMETER_SUPPORT_MAIL_TOADDRESS = "support_mail_toaddress";

	public static final String AUTH_TYPE_LECHANGE_AUTHORIZE = "lechangeauthorize";

	public static final String SYSTEMPARAMETER_DOMAIN_NAME = "domain_name";


	public static final String MESSGAE_MAILREGIST_SUBJECT = "mailregist_subject";
	public static final String MESSGAE_MAILREGIST_CONTENT = "mailregist_content";
	public static final String MESSGAE_MAILRESETPASSWORD_SUBJECT = "mailresetpassword_subject";
	public static final String MESSGAE_MAILRESETPASSWORD_CONTENT = "mailresetpassword_content";

	public static final int OPERATOR_TYPE_DEVICE_REPORT = 1 ;
	public static final int OPERATOR_TYPE_APP_USER = 2 ;
	public static final int OPERATOR_TYPE_SCENE = 3 ;
	public static final int OPERATOR_TYPE_ASSCIATION = 4 ;
	public static final int OPERATOR_TYPE_THIRD_PARTER = 5 ;
	public static final int OPERATOR_TYPE_TIMER = 6 ;
	public static final int OPERATOR_TYPE_DEVICE_GROUP = 7 ;

	public static final int SCENE_EXECUTE_TYPE_USER_TRIGGER = 1;
	public static final int SCENE_EXECUTE_TYPE_ASSOCIATION = 2;
	public static final int SCENE_EXECUTE_TYPE_TIMER = 3;
	public static final int SCENE_EXECUTE_TYPE_SCENE_CONDITIONS = 4;
	public static final int SCENE_EXECUTE_TYPE_TRIGGER_BY_SCENE = 5;

	public static final int DEVICE_CAPABILITY_LOCK_AUTO_LOCK = 1 ;
	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_BLUE_TOOTH_NAME = 20;
	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_MAC_ADDRESS = 21;
	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_1 = 22;
	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2 = 23;
	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_3 = 24;
	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE = 25;
	public static final int DEVICE_CAPABILITY_LOCK_SUPPORT_BLUE_TOOTH = 30;
	public static final int DEVICE_CAPABILITY_LOCK_SUPPORT_NB = 31;
	public static final int DEVICE_CAPABILITY_LOCK_SUPPORT_WIFI = 32;
	public static final int DEVICE_CAPABILITY_LOCK_SUPPORT_ZWAVE = 33;
	public static final List DEVICE_CAPABILITY_DIA_ABLE_SHOW_FOR_APP = Arrays.asList(
			DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_1,
			DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_KEY_2,
			DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_SEQUENCE);
	public static final List DEVICE_CAPABILITY_LOCK_TYPE_EXCEPT_BLUE_TOOTH = Arrays.asList(
			DEVICE_CAPABILITY_LOCK_SUPPORT_NB,
			DEVICE_CAPABILITY_LOCK_SUPPORT_WIFI,
			DEVICE_CAPABILITY_LOCK_SUPPORT_ZWAVE);

	public static final int DEVICE_CAPABILITY_BLUE_TOOTH_LOCK_DEFAULT_SEQUENCE = 0;

	public static final int CAPABILITY_DEVICE_COLOR = 300 ;
	public static final int CAPABILITY_CHANNEL_COLOR_1 = 301 ;
	public static final int CAPABILITY_CHANNEL_COLOR_2 = 302 ;
	public static final int CAPABILITY_CHANNEL_COLOR_3 = 303 ;
	public static final int CAPABILITY_CHANNEL_COLOR_4 = 304 ;
	public static final int CAPABILITY_CHANNEL_COLOR_5 = 305 ;
	public static final int CAPABILITY_CHANNEL_COLOR_6 = 306 ;
	public static final String TASK_GROUP_NAME_TIMER_TASK = "timertask";

	public static final String EVENT_DELETE_ZWAVE_DEVICE = "deletezwavedevice";
	public static final String EVENT_DELETE_INFRARED_DEVICE = "deleteinfrareddevice";
	public static final String EVENT_Add_ZWAVE_DEVICE_STEP = "addzwavedevicestep";
	public static final String EVENT_DSC_PARTITION_ARM_STATUS = "dscpartitionarmstatus";
	public static final String EVENT_DSC_PARTITION_WARNING_STATUS = "dscpartitionwarningstatus";
	public static final String NOTIFICATION_DSC_ARM_STATUS_CHANGE = "partitionarmstatuschange";
	public static final String NOTIFICATION_DSC_PARTITION_WARNING_STATUS = "partitionwarningstatus";
	public static final String NOTIFICATION_DSC_ALRAM = "dscalarm";
	public static final String NOTIFICATION_UN_DSC_ALRAM = "unalarmdscalarm";
	public static final String EVNET_PUSH_SUB_ZWAVEDEVICE_STATUS = "pushsubdevicestatus";
	public static final String NOTIFICATION_SUB_DEVICE_STATUS = "subdevicestatus";
	public static final String NOTIFICATION_DSC_PARTITION_ARM_STATUS = "dscpartitionarmstatusnotification";
	public static final String PARTITION_ARM_SUCCESS = "partitionarmsuccess";

	public static final String SOUND_DOOR_BELL_RING = "doorbellring.caf";

	public static final int WARNING_SEND_TYPE_NOT_SEND = 1;
	public static final int WARNING_SEND_TYPE_SMS = 2;
	public static final int WARNING_SEND_TYPE_CALL = 3;
	public static final int WARNING_SEND_TYPE_NOTIFICATION = 4;
	public static final int WARNING_SEND_TYPE_MAIL = 5;
	
	public static final int USDRINOUT_OUT = 0;
	public static final int USDRINOUT_IN = 1;

	public static final int GATEWAY_TYPE_NORMAL = 0 ;
	public static final int GATEWAY_TYPE_COBBE_LOCK = 1 ;
	public static final int GATEWAY_TYPE_TONGXIN_LOCK = 2 ;
	public static final int GATEWAY_TYPE_ACCESS_CONTROL = 3 ;
	public static final int GATEWAY_TYPE_AIR_QUALITY = 23;
	public static final int GATEWAY_TYPE_DRESS_HELPER = 60;

	public static final int TYPE_INFRARED_OPERATE_CODE = 1;
	public static final int TYPE_OTHER_OPERATE_CODE = 2;

    public static final int PASS_THROUGH_DEVICE_CMD_INDEX_POWER_OFF = 0;
    public static final int PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON = 1;
	public static final int PASS_THROUGH_DEVICE_CMD_INDEX_POWER_ON_AND_OFF = 2;

	public static final int PASS_THROUGH_DEVICE_CMD_TYPE_OF_BYTE_ARRAY = 1;

	public static final int DEVICE_STATUS_POWER_OFF = 0;
	public static final int DEVICE_STATUS_POWER_ON = 255;
	public static final int DEVICE_STATUS_DOOR_SENSOR_OPEN = 255 ;
	public static final int DEVICE_STATUS_DOOR_SENSOR_CLOSE = 0 ;
	public static final int DEVICE_STATUS_DOOR_LOCK_OPEN = 1 ;
	public static final int DEVICE_STATUS_DOOR_LOCK_OPEN_0 = 0 ;
	public static final int DEVICE_STATUS_DOOR_LOCK_CLOSE = 255 ;
	public static final int DEVICE_STATUS_ALARM_CLOSE = 0 ;
	public static final int DEVICE_STATUS_ALARM_ALARM = 255 ;
	public static final int DEVICE_STATUS_TAMPLE_ALARM = 251 ;
	public static final int LOCK_PASSWORD_ERROR_5_TIMES = 300 ;
	public static final int DEVICE_STATUS_SMOKE = 255 ;
	public static final int DEVICE_STATUS_WATER_LEAK = 255 ;
	public static final int DEVICE_STATUS_GAS_LEAK = 255 ;
	public static final int DEVICE_STATUS_DOOR_OPEN = 255 ;
	public static final int DEVICE_STATUS_DOOR_CLOSE = 0 ;
	public static final int DEVICE_STATUS_MOVE_OUT = 0 ;
	public static final int DEVICE_STATUS_MOVE_IN = 255 ;
	public static final int DEVICE_STATUS_SWITCH_OPEN = 255 ;
	public static final int DEVICE_STATUS_SWITCH_CLOSE = 0 ;
	public static final int DEVICE_STATUS_CURTAIN_OPEN = 99 ;
	public static final int DEVICE_STATUS_CURTAIN_CLOSE = 0 ;
	public static final int DEVICE_STATUS_DIMMER_OPEN = 99 ;
	public static final int DEVICE_STATUS_DIMMER_CLOSE = 0 ;
	public static final int DEVICE_STATUS_SOS_OPEN = 2 ;
	public static final int DEVICE_STATUS_SOS_CLOSE = 1 ;
	public static final int DEVICE_STATUS_NEW_WIND_OPEN = 255 ;
	public static final int DEVICE_STATUS_NEW_WIND_CLOSE = 0 ;
	public static final int DEVICE_STATUS_NEW_WIND_HIGH = 255 ;
	public static final int DEVICE_STATUS_NEW_WIND_LOW = 0 ;
	public static final int DEVICE_STATUS_RESTORE = 0;
	public static final int DEVICE_STATUS_GARAGE_DOOR_OPEN = 255;
	public static final int DEVICE_STATUS_GARAGE_DOOR_CLOSE = 0;
	public static final int DEVICE_STATUS_GARAGE_DOOR_CLOSING = 252;
	public static final int DEVICE_STATUS_GARAGE_DOOR_OPENING = 254;
	public static final int DEVICE_STATUS_GARAGE_DOOR_NOT_INIT = 253;
	public static final int DOOR_LOCK_BELL_RING = 280;
	public static final int DEVICE_STATUS_ELECTRIC_FENCE_ARM = 255;
	public static final int DEVICE_STATUS_ELECTRIC_FENCE_DIS_ARM = 0;
	public static final int DEVICE_STATUS_PLASMA_DRYER_ON = 255;
	public static final int DEVICE_STATUS_PLASMA_DRYER_OFF = 0;

	public static final int LOCK_KEY_ERROR = 301 ;
	public static final int LOCK_PASSWORD_LOCK_BULLY = 302 ;
	public static final int LOCK_NOT_CLOSE = 303 ;
	public static final int LOCK_KEY_EVENT = 304 ;
	public static final int LOCK_LOCK_ERROR = 305 ;
	public static final int LOCK_OPEN_INSIDE = 306 ;
	public static final int POWER_OVER_LOAD = 310 ;

	public static final int PLATFORM_ZHJW = 0;
	public static final int PLATFORM_ASININFO = 1;
	public static final int PLATFORM_CHUANGJIA = 2;
	public static final int PLATFORM_DORLINK = 3;
	public static final int PLATFORM_NORTH_AMERICAN = 4;
	//public static final int PLATFORM_NORTH_COBBE = 5;
	public static final int PLATFORM_XIAOHU = 6;
	public static final int PLATFORM_SINGAPORE = 7;
	public static final int PLATFORM_KEEMPLE = 8;
	public static final int PLATFORM_AMETA = 9;
	public static final int PLATFORM_JIACHENGZHIHUI = 17;

	public static final String THIRDPARTER_DORLINK_ALL = "tp_dorlink_all";
	public static final String THIRDPARTER_AMETA_ALL = "tp_ameta_all";

	public static final int DEVICE_ENABLE_STATUS_DISENABLE = 1;
	public static final int DEVICE_ENABLE_STATUS_ENABLE = 0;
	public static final int DEVICE_ENABLE_STATUS_SCHEDULE = 2;

	public static final int NOTIFICATION_STATUS_USER_ARM = 0 ;
	public static final int NOTIFICATION_STATUS_DEVICE_DISABLE = 1 ;
	public static final int NOTIFICATION_STATUS_USER_DISARM = 2 ;
	public static final int NOTIFICATION_STATUS_USER_INHOME_ARM = 3 ;
	public static final int NOTIFICATION_STATUS_NO_OWNER = 4 ;

	public static final int NOTIFICATION_NOTIFY_ME = 0 ;
	public static final int NOTIFICATION_DO_NOT_NOTIFY_ME = 1 ;

	public static final int PHONEUSER_ARM_STATUS_ARM = 1 ;
	public static final int PHONEUSER_ARM_STATUS_DISARM = 0 ;
	public static final int PHONEUSER_ARM_STATUS_SLEEPING_ARM = 3 ;

	public static final int PHONEUSER_USER_TYPE_NORMAL = 0 ;
	public static final int PHONEUSER_USER_TYPE_RENTINGHOUSE_ADMIN = 1 ;
	public static final int PHONEUSER_USER_TYPE_PROJECTION_TEMP_USER = 2 ;
	public static final int PHONEUSER_USER_TYPE_MAIL = 4 ;

	public static final String CAMERA_DEVICE_TYPE_DOMESTIC = "2";
	public static final String CAMERA_DEVICE_TYPE_ABROAD = "9";

	public static final int TOKEN_CACHE_EXPIRE_TIME = 24;

	public static final int SERVICE_DEFINE_USER_POSITION = 1 ;

	public static final String DEFAULT_COUNTRYCODE = "86";

	public static final int SMS_RANDCODE_TYPE_REGIST = 1 ;
	public static final int SMS_RANDCODE_TYPE_RESET_PASSWORD = 2 ;

	public static final String QUARTZ_GROUP_DEVICE_TIMER = "QUARTZ_GROUP_DEVICE_TIMER";

	public static final int DEVICE_TIMER_EXECUTOR_GATEWAY = 0 ;
	public static final int DEVICE_TIMER_EXECUTOR_SERVER = 1 ;

	public static final int DEVICE_TIMER_STATUS_INVALID = 0 ;
	public static final int DEVICE_TIMER_STATUS_VALID = 1 ;

	public static final int IREMOTE_TYPE_NORMAL = 0 ;
	public static final int IREMOTE_TYPE_COBBE_WIFI_LOCK = 1 ;
	public static final int IREMOTE_TEYP_TONGXING = 2 ;
	public static final int IREMOTE_TEYP_AIRQUILITY = 23 ;
	public static final int IREMOTE_TYPE_FINGERPRINTREADER = 53;
	public static final int IREMOTE_TYPE_DRESS_HELPER = 60;

	public static final int DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING = 1 ;
	public static final int DOOR_LOCK_PASSWORD_SYN_STATUS_SENT = 2 ;
	public static final int DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED = 3 ;
	public static final int DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET = 4 ;
	public static final int DOOR_LOCK_PASSWORD_SYN_DATE_FAILED_MANY_TIMES = 5 ;

	public static final int DOOR_LOCK_SYN_PASSWORD_AND_DATE_SUCCESS = 1;
	public static final int DOOR_LOCK_SYN_PASSWORD_SUCCESS_DATE_FAIL = 2;
	public static final int DOOR_LOCK_SYN_DATE_SUCCESS = 3;
	public static final int DOOR_LOCK_SYN_FAIED = 4;

	public static final int DOOR_TYPE_ZWAVE_LOCK = 1;
	public static final int DOOR_TYPE_COBBE_LOCK = 2;

	public static final int DOOR_LOCK_SYN_DELETE_SUCCESS = 1;
	public static final int DOOR_LOC_SYN_DELETE_FAILED = 2;

	public static final int DOOR_LOCK_USERTYPE_PASSWORD = 0x15 ;
	public static final int DOOR_LOCK_USERTYPE_FINGERPRINT = 0x16 ;
	public static final int DOOR_LOCK_USERTYPE_CARD = 0x20 ;
	public static final int DOOR_LOCK_USERTYPE_OPEN_INSIDE = 0x28 ;

	public static final int DOOR_LOCK_PASSWORD_TYPE_NORMAL = 1 ;
	public static final int DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP = 2 ;

	public static final int DOOR_LOCK_PASSWORD_STATUS_ACTIVE = 1 ;
	public static final int DOOR_LOCK_PASSWORD_STATUS_ECLIPSED = 2 ;
	public static final int DOOR_LOCK_PASSWORD_STATUS_USED = 3 ;
	public static final int DOOR_LOCK_PASSWORD_STATUS_DELETE = 9 ;

	public static final int DOOR_LOCK_CAPABILITY_PASSWORD_TIME = 9 ;
	public static final int DOOR_LOCK_CAPABILITY_FINGERPRINT_TIME = 10 ;
	public static final int DOOR_LOCK_CAPABILITY_CARD_TIME = 11 ;
	public static final int DOOR_LOCK_CAPABILITY_FORBID_REMOTE_OPEN = 12;

	public static final int DOOR_LOCK_USER_TYPE_PASSWORD = 0x15;
	public static final int DOOR_LOCK_USER_TYPE_FINGERPRINT = 0x16;
	public static final int DOOR_LOCK_USER_TYPE_CARD_0X20 = 0x20;
	public static final int DOOR_LOCK_USER_TYPE_CARD = 0x20;
	public static final int DOOR_LOCK_USER_TYPE_REMTOE = 0x21;

	public static final int DOOR_LOCK_USER_CODE_TEMP_PASSWORD = 242;

	public static final int DOOR_LOCK_USER_TYPE_CARD_10 = 32;
	public static final int DOOR_LOCK_USER_FINGERPRINT = 22;
	public static final int DOOR_LOCK_USER_PASSWORD = 21;

	public static final String DOOR_LOCK_SEND_PASSOWRD = "doorlocksendpassword";
	public static final String DOOR_LOCK_SLEEP = "doorlocksleep";

	public static final String HIBERNATE_SESSION_GUI = "hibernatesessiongui";
	public static final String HIBERNATE_SESSION_REMOTE = "hibernatesessionremote";

	public static final String CHANNEL_NAME = "channelname";

	public static final String CHANNELID = "channelid";
	public static final String OPERATION = "operation";
	public static final String VALUE = "value";
	public static final String POWER = "power";
	public static final String FAN = "fan";
	public static final String MODE = "mode";
	public static final String DURATION = "duration";

	public static final String[] ZWAVA_CREATE_TVL_DOORLOCK = {"createLockCommand","2"};
	public static final String[] ZWAVA_CREATE_TVL_CURTAIN = {"createMultilevelSwitchCommand","2"};
	public static final String[] ZWAVA_CREATE_TVL_DIMMER = {"createMultilevelSwitchCommand","2"};
	public static final String[] ZWAVA_CREATE_TVL_SWITCH = {"createSwitchCommand","2"};

	public static final String AIR_QUALITY_STATUS_CLEAR = "clear";
	public static final String AIR_QUALITY_STATUS_POLLUTE = "pollute";

	public static final int APP_JPUSH_NO = 0;
	public static final int APP_JPUSH_YES = 1;

	public static final int JPUSH_BUILDER_1 = 1;
	public static final int JPUSH_BUILDER_2 = 2;
	public static final int JPUSH_BUILDER_3 = 3;

	public static final String JPUSH_SOUND_1 = "ring1.mp3";
	public static final String JPUSH_SOUND_2 = "ring2.mp3";
	public static final String JPUSH_SOUND_3 = "ring3.mp3";

	
	public static final int SCENE_TYPE_SCENE = 1;
	public static final int SCENE_TYPE_ASSOCIATION = 2;
	public static final int SCENE_TYPE_TIMER = 3;

	public static final int SCENE_ENABLESTATUS_NO = 0;
	public static final int SCENE_ENABLESTATUS_YES = 1;

	public static final int DEFAULT_THIRDPART_ID = 1; 
	
	public static final int DOORLOCK_USER_TYPE_ORDINARY = 1;
	public static final int DOORLOCK_USER_TYPE_ALARM = 2;

	public static final int GATEWAY_CAPABILITY_AP_MODE = 3 ;
	public static final int GATEWAY_CAPABILITY_NOT_SUPPORT_NETWORK = 8;
	public static final int GATEWAY_CAPABILITY_NOT_SUPPORT_FOR_NETWORK = 10003 ;
	public static final int GATEWAY_CAPABILITY_GATEWAY_HEART_BEAT = 20000 ;

	public static final int DSC_PARTITION_WARNING_STATUS_NORMAL = 0;
	public static final int DSC_PARTITION_WARNING_STATUS_ARM= 1;

	public static final int DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_NORMAL = 0;
	public static final int DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ALARM= 1;
	public static final int DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ARARM_DELAY= 2;
	public static final List<Integer> DSC_ZWAVE_SUB_DEVICE_WARNING_STATUS_ARARM_STATUS = Arrays.asList(0, 1, 2);

	public static final String ALI_APPCODE = "APPCODE 78ad21d98ef6429291a0f22417375520";
	public static final String ALI_SMS_SIGNNAME = "\u7BA1\u5BB6\u63D0\u9192";

	public static String DEVICE_OPERATE_URI ="/device/adop";
	public static final String DEVICE_AUTHRIZE = "deviceauthrize";
	public static final String DEVICE_AUTHRIZE_SHORTURL = "deviceauthrizeshorturl";

	/*************************Notification Message*******************************/
	public static final String QUEUE_TASK_REMOTE_MESSAGE = "remotemessage";

	public static final String NOTIFICATION_SHARE_REQUEST = "sharerequest";
	public static final String NOTIFICATION_SHARE_INVITATION = "shareinvitation";
	public static final String NOTIFICATION_SHARE_INVITATION_ACCEPTED = "shareinvitationaccepted";
	public static final String NOTIFICATION_SHARE_INVITATION_REJECTED = "shareinvitationrejected";
	public static final String NOTIFICATION_SHARE_INVITATION_DELETE = "shareinvitationdeleted";

	public static final String NOTIFICATION_INFO_CHANGED = "infochanged";
	public static final String NOTIFICATION_DEVICE_INFO_CHANGED = "deviceinfochanged";
	public static final String NOTIFICATION_OWNER_CHANGED = "iremoteownerchanged";
	public static final String NOTIFICATION_DELETE_REMOTE = "deleteremote";
	public static final String NOTIFICATION_SHARE_RELA_CHANGED = "sharerelationshipchanged";
	public static final String NOTIFICATION_REPORT_SHARE = "notificationreportshare";
	public static final String NOTIFICATION_SCENE_EXECUTE = "sceneexecute";
	public static final Map<String , String> DEVICE_WARNING_MESSAGE_MAP = new HashMap<String , String>();

	static
	{
		DEVICE_WARNING_MESSAGE_MAP.put("1_255", IRemoteConstantDefine.WARNING_TYPE_SMOKE);
		DEVICE_WARNING_MESSAGE_MAP.put("1_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);

		DEVICE_WARNING_MESSAGE_MAP.put("2_255", IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK);
		DEVICE_WARNING_MESSAGE_MAP.put("2_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);

		DEVICE_WARNING_MESSAGE_MAP.put("3_255", IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK);
		DEVICE_WARNING_MESSAGE_MAP.put("3_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);

		DEVICE_WARNING_MESSAGE_MAP.put("4_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);
		DEVICE_WARNING_MESSAGE_MAP.put("4_255", IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN);

		DEVICE_WARNING_MESSAGE_MAP.put("5_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);
		DEVICE_WARNING_MESSAGE_MAP.put("5_1", IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN);
		DEVICE_WARNING_MESSAGE_MAP.put("5_300", IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES);
		DEVICE_WARNING_MESSAGE_MAP.put("5_301", IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR);
		DEVICE_WARNING_MESSAGE_MAP.put("5_302", IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY);
		DEVICE_WARNING_MESSAGE_MAP.put("5_303", IRemoteConstantDefine.WARNING_TYPE_LOCK_NOT_CLOSE);
		DEVICE_WARNING_MESSAGE_MAP.put("5_304", IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT);
		DEVICE_WARNING_MESSAGE_MAP.put("5_305", IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR);
		DEVICE_WARNING_MESSAGE_MAP.put("5_306", IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE);

		DEVICE_WARNING_MESSAGE_MAP.put("6_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);
		DEVICE_WARNING_MESSAGE_MAP.put("6_255", IRemoteConstantDefine.WARNING_TYPE_MOVEIN);

		DEVICE_WARNING_MESSAGE_MAP.put("11_310", IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD);

		DEVICE_WARNING_MESSAGE_MAP.put("16_255", IRemoteConstantDefine.WARNING_TYPE_SOS);

		DEVICE_WARNING_MESSAGE_MAP.put("19_300", IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES);
		DEVICE_WARNING_MESSAGE_MAP.put("19_301", IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR);
		DEVICE_WARNING_MESSAGE_MAP.put("19_302", IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY);

		DEVICE_WARNING_MESSAGE_MAP.put("55_251", IRemoteConstantDefine.WARNING_TYPE_TAMPLE);
		DEVICE_WARNING_MESSAGE_MAP.put("55_1", IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN);
		DEVICE_WARNING_MESSAGE_MAP.put("55_300", IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES);
		DEVICE_WARNING_MESSAGE_MAP.put("55_301", IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR);
		DEVICE_WARNING_MESSAGE_MAP.put("55_302", IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY);
		DEVICE_WARNING_MESSAGE_MAP.put("55_303", IRemoteConstantDefine.WARNING_TYPE_LOCK_NOT_CLOSE);
		DEVICE_WARNING_MESSAGE_MAP.put("55_304", IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT);
		DEVICE_WARNING_MESSAGE_MAP.put("55_305", IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR);
		DEVICE_WARNING_MESSAGE_MAP.put("55_306", IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE);
		DEVICE_WARNING_MESSAGE_MAP.put("56_1", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_DISCONNECTION);
		DEVICE_WARNING_MESSAGE_MAP.put("56_2", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_SHORTCIRCUIT);
		DEVICE_WARNING_MESSAGE_MAP.put("56_3", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_TAMPLER);
		DEVICE_WARNING_MESSAGE_MAP.put("56_4", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_INSTRUSION);
		DEVICE_WARNING_MESSAGE_MAP.put("56_6", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_CONTACT);
		DEVICE_WARNING_MESSAGE_MAP.put("56_7", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_SAMELINESHORTCIRCUIT);
		DEVICE_WARNING_MESSAGE_MAP.put("56_8", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_POWEROFF);
		DEVICE_WARNING_MESSAGE_MAP.put("57_1", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_DISCONNECTION);
		DEVICE_WARNING_MESSAGE_MAP.put("57_2", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_SHORTCIRCUIT);
		DEVICE_WARNING_MESSAGE_MAP.put("57_3", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_TAMPLER);
		DEVICE_WARNING_MESSAGE_MAP.put("57_4", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_INSTRUSION);
		DEVICE_WARNING_MESSAGE_MAP.put("57_6", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_CONTACT);
		DEVICE_WARNING_MESSAGE_MAP.put("57_7", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_SAMELINESHORTCIRCUIT);
		DEVICE_WARNING_MESSAGE_MAP.put("57_8", IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_POWEROFF);

		DEVICE_WARNING_MESSAGE_MAP.put("54_254", IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN);
		DEVICE_WARNING_MESSAGE_MAP.put("54_255", IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN);

		DEVICE_WARNING_MESSAGE_MAP.put("1_255", IRemoteConstantDefine.WARNING_TYPE_SMOKE);
		DEVICE_WARNING_MESSAGE_MAP.put("2_255", IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK);
		DEVICE_WARNING_MESSAGE_MAP.put("3_255", IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK);
		DEVICE_WARNING_MESSAGE_MAP.put("16_255", IRemoteConstantDefine.WARNING_TYPE_SOS);

		DEVICE_WARNING_MESSAGE_MAP.put("11_310", IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD);
		DEVICE_WARNING_MESSAGE_MAP.put("camera_501", IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE);
	}

	/*************************AsiaInfo*******************************/
	public static String[] MODLE = new String[]{"","JWZH-O-2015-1" ,
			"JWZH-O-2015-3" ,
			"JWZH-O-2015-2",
			"JWZH-O-2015-4",
			"JWZH-O-2015-0-5",
			"JWZH-O-2015-5",
			"JWZH-O-2015-6-1",
			"JWZH-O-2015-6-2",
			"JWZH-O-2015-6-3",
			"JWZH-O-2015-7",
			"JWZH-O-2015-11"};
	public static String[] DEVICE_CATEGORIES = new String[]{"" , "A002" , "A004", "A003" , "A005" , "A008" , "A006" , "" , "" , "" , "" , "A007"};
	public static String[] DEVICE_CLASS = new String[]{"" , "A002001" , "A004001", "A003001" , "A005001" , "A008001" , "A006001" , "" , "" , "" , "" , "A007001"};

	public static final String CLIENT_CODE_OF_SYSTEM_ID = "50000";
	public static final int ASIAINFO_MSG_ID_ASIAINFO_COMMON_RESPONSE = 0x2001;
	public static final int ASIAINFO_MSG_ID_DEVICE_REPORT = 0x2005;
	public static final int ASIAINFO_MSG_ID_DEVICE_ONLINE_REPORT = 0x200b;
	public static final int ASIAINFO_MSG_ID_DEVICE_RESET = 0x200c;
	public static final int ASIAINFO_MSG_ID_AUTH = 0X6FFF;
	public static final int ASIAINFO_MSG_ID_COMMON_RESPONSE = 0x6001;
	public static final int ASIAINFO_MSG_ID_HEARTBEAT = 0x6002;
	public static final int ASIAINFO_MSG_ID_SET_COMMAND = 0x6003;
	public static final int ASIAINFO_MSG_ID_REPORT_PARSE = 0x6006;
	public static final int ASIAINFO_MSG_ID_DEVICE_INFO_FILING = 0x6009;
	public static final int ASIAINFO_MSG_ID_NON_SMART_DEVICE_INFO_FILING = 0x6010;
	public static final int ASIAINFO_MSG_ID_UNREGIST_DEVICE = 0x600a;

	public static final int ASIAINFO_MSG_ID_DEVICE_REACTION = 0x6050;

	public static final String ASIA_INFO_BASE_URL = "http://202.111.54.111:80/teleshgateway/";
	public static final String ASIA_INFO_APP_SERVER_URL = "https://202.111.54.112/appserver/";
	public static final String ASIA_INFO_WEBSOCKET_URL = "ws://202.111.54.111:80/teleshgateway/ws";


}
