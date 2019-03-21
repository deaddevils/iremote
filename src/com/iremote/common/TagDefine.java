package com.iremote.common;

public class TagDefine {

	public final static int TAG_HEAD_LENGTH = 4 ;
	public final static int TAG_LENGTH_1 = 1;
	public final static int TAG_LENGTH_2 = 2;
	public final static int TAG_LENGTH_4 = 4;
	
	public final static int COMMAND_CLASS_GATEWAY_CONNECT = 1 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_INFO_REQUEST = 1 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_INFO_RESPONSE = 2 ;
	
	public final static int COMMAND_CLASS_DEVICE = 30 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_COMMAND = 7 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_REPORT = 9 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_MALFUNCTION_RESPONSE = 23 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_GROUP_COMMAND = 48 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_GROUP_REPORT = 49 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_BATCH_COMMAND = 50 ;
	public final static int COMMAND_SUB_CLASS_DEVICE_BATCH_COMMAND_REPORT = 51 ;
	
	public final static int COMMAND_CLASS_GATEWAY_LOGIN = 100 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_LOGIN_REQUEST = 1 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_LOGIN_RESPONSE = 2 ;
	
	public final static int COMMAND_CLASS_GATEWAY_103 = 103 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_QUERY_WEATHER = 5 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_QUERY_WEATHER_RESPONSE = 6 ;
	
	public final static int COMMAND_CLASS_GATEWAY = 104 ;
	public final static int COMMAND_SUB_CLASS_GATEWAY_SLEEP = 1 ;
	
	public final static int COMMAND_SUB_CLASS_GATEWAY_RESET_REQUEST = 5;
	public final static int COMMAND_SUB_CLASS_GATEWAY_RESET_RESPONSE = 6;
	
	public final static int COMMAND_CLASS_DEVICE_VERSION = 0xfe;
	public final static int COMMAND_SUB_CLASS_DEVICE_UPGRADE_WIFI_PACKAGE = 0xf0;
	public final static int COMMAND_SUB_CLASS_DEVICE_UPGRADE_RESPONSE = 0xf1;
	public final static int COMMAND_SUB_CLASS_DEVICE_UPGRADE_LOCK_PACKAGE = 0xf2;
	public final static int COMMAND_SUB_CLASS_DEVICE_VERSION_REQUEST = 0xf3;
	public final static int COMMAND_SUB_CLASS_DEVICE_VERSION_RESPONSE = 0xf4;
	
	public final static int COMMAND_CLASS_DEVICE_MANAGER = 105 ;
	public final static int COMMAND_SUB_CLASS_ADD_ZWAVEDEVICE_REPORT = 9 ;
	public final static int COMMAND_SUB_CLASS_ADD_ZWAVEDEVICE_RESPONSE = 10 ;
	public final static int COMMAND_SUB_CLASS_START_ZWAVEDEVICE_ADDING_PROGRESS_REQUEST = 11 ;
	public final static int COMMAND_SUB_CLASS_START_ZWAVEDEVICE_ADDING_PROGRESS_RESPONSE = 12 ;
	public final static int COMMAND_SUB_CLASS_STOP_ZWAVEDEVICE_ADDING_PROGRESS_REQUEST = 13 ;
	public final static int COMMAND_SUB_CLASS_STOP_ZWAVEDEVICE_ADDING_PROGRESS_RESPONSE = 14 ;


	public final static int COMMAND_SUB_CLASS_INFRAREKEYSTUDY = 2 ;
	public final static int COMMAND_SUB_CLASS_INFRAREKEYSTUDY_REQUEST = 1 ;
	public final static int COMMAND_SUB_CLASS_INFRAREKEYSTUDY_RESPONSE = 2 ;
	public final static int COMMAND_SUB_CLASS_INFRAREKEYSTUDY_CODE = 3 ;


	public final static int COMMAND_CLASS_ENCRYPT = 106 ;
	public final static int COMMAND_SUB_CLASS_ENCRYPT_REQUEST = 1 ;
	public final static int COMMAND_SUB_CLASS_ENCRYPT_RESPONSE = 2 ;
	public final static int COMMAND_SUB_CLASS_SECURITYKEY_REQUEST = 3 ;
	public final static int COMMAND_SUB_CLASS_SECURITYKEY_RESPONSE = 4 ;
	
	public final static int TAG_RESULT = 1 ;
	public final static int TAG_GATEWAY_DEVICE_ID = 2 ;
	public final static int TAG_INT_VERSION = 3 ;
	public final static int TAG_STR_VERSION = 4 ;
	public final static int TAG_PASSWORD = 11;
	public final static int TAG_OPERATOR = 12 ;
	public final static int TAG_REMOTE_TYPE = 22;
	public final static int TAG_KEY1 = 23;
	public final static int TAG_KEY2 = 24;
	public final static int TAG_RANDOM = 25;
	public final static int TAG_REMOTE_CAPABILITY = 26;
	public final static int TAG_AES_ENCRYPT = 28 ;
	public final static int TAG_TEA_ENCRYPT = 29 ;
	public final static int TAG_SEQUENCE = 31 ;
	public final static int TAG_SECURITY_KEY = 32 ;
	public final static int TAG_WEATHER = 45;
	public final static int TAG_RELATIVE_HUMIDITY = 46;
	public final static int TAG_PM2_5 = 47;
	public final static int TAG_UV_INDEX = 48;
	public final static int TAG_TEMPERATURE = 61;
	public final static int TAG_ZWAVE_COMMAND = 70 ;
	public final static int TAG_NUID = 71 ;
	public final static int TAG_ZWAVE_COMMAND_REPORT = 74 ;
	public final static int TAG_OPERATION_TYPE = 79 ;
	public final static int TAG_MODEL = 80 ;
	public final static int TAG_PRODUCTOR = 81 ;
	public final static int TAG_DEVICE_SUPOORT_CLASSES = 82 ;
	public final static int TAG_SELF_DEFINE_MODEL = 84 ;
	public final static int TAG_SELF_DEFINE_RPODUCTOR = 85 ;
	public final static int TAG_GATEWAY_ADD_ZWAVE_DEVICE_STEP = 89 ;
	public final static int TAG_PACKAGE_COMMAND= 90 ;
	public final static int TAG_PACKAGE_COMMAND_SEQUENCE = 91 ;
	public final static int TAG_NUIDS = 92 ;
	public final static int TAG_DSC = 93 ;
	public final static int TAG_APN = 94 ;
	public final static int TAG_APN_ACCOUNT = 95 ;
	public final static int TAG_APN_PASSWORD = 96 ;
	public final static int TAG_TOKEN = 100;
	public final static int TAG_TIME = 104;
	public final static int TAG_GATEWAY_INFRAREKEYSTUDY_STEP = 1 ;
	public final static int TAG_NETWORK_INTENSITY = 109 ;
	public final static int TAG_APP_USER_TOKEN_ID = 110 ;
}
