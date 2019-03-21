package com.iremote.test.data;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;

public class ConstDefine_Lock 
{
	
	public static final int NUID_LOCK = 2 ;
	public static final int ZWAVEDEVICEID_LOCK = 1 ;
	
	public static final String[] INIT_DOOR_LOCK_DEVICE = new String[]{"status" , "0" , 
																		"statuses" , "" , 
																		"warningstatuses" , ""};
	public static final String[] INIT_DOOR_LOCK_DEVICE_FILTER = new String[]{ZWaveDevice.class.getName() , 
																	"deviceid" , ConstDefine.DEVICE_ID,
																	"nuid" , String.valueOf(NUID_LOCK)};
	
	public static final byte[] DOOR_LOCK_OPEN_1 = new byte[]{98,3,0,0,2,(byte)254,(byte)254};
	public static final String[] FILTER_LOCK_OPEN_1_NONTIFICATION = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_1)};
	
	public static final String[] FILTER_LOCK_OPEN_TP_BG = new String[]{EventtoThirdpart.class.getName() , 
																"thirdpartid" , String.valueOf(ConstDefine.THRIDPART_ID_BACKGROUD),	
																"zwavedeviceid" , String.valueOf(ZWAVEDEVICEID_LOCK),
																"type" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN
																};
	
	public static final String[] FILTER_LOCK_OPEN_TP_TEST = new String[]{EventtoThirdpart.class.getName() , 
																		"thirdpartid" , String.valueOf(ConstDefine.THRIDPART_ID_TEST),	
																		"zwavedeviceid" , String.valueOf(ZWAVEDEVICEID_LOCK),
																		"type" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN
																		};
	
	public static final byte[] DOOR_LOCK_OPEN_2 = new byte[]{98,3,1};
	public static final String[] FILTER_LOCK_OPEN_2 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_2)};
	
	public static final byte[] DOOR_LOCK_OPEN_3 = new byte[]{113,5,19,3,0,(byte)255,6,6,1,1};
	public static final String[] FILTER_LOCK_OPEN_3 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_3)};
	
	public static final byte[] DOOR_LOCK_OPEN_4 = new byte[]{113,5,22,1,0,(byte)255,6,2,0};
	public static final String[] FILTER_LOCK_OPEN_4 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_4)};
	
	public static final byte[] DOOR_LOCK_OPEN_5 = new byte[]{113,5,6,6,0,(byte)255,6,6,14,0,99,3,0,1,0,0,0,0,0,0,0,0};
	public static final String[] FILTER_LOCK_OPEN_5 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_5)};
	
	public static final byte[] DOOR_LOCK_OPEN_6 = new byte[]{(byte)128,7,0,(byte)177,16,1,3,0,22,6,0,0,0,0,0,0,0,16,7,15,15,15};
	public static final String[] FILTER_LOCK_OPEN_6 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_6)};
	
	public static final byte[] DOOR_LOCK_OPEN_7 = new byte[]{(byte)128,7,0,(byte)176,16,1,3,0,21,116,101,109,112,117,115,101,114,16,7,19,10,38};
	public static final String[] FILTER_LOCK_OPEN_7 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_OPEN_7)};
	
	public static final byte[] DOOR_LOCK_CLOSE_1 = new byte[]{98,3,(byte)255,0,0,(byte)254,(byte)254};
	public static final String[] FILTER_LOCK_CLOSE_1 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_CLOSE_1)};
	public static final String[] FILTER_LOCK_CLOSE_TP_TEST = new String[]{EventtoThirdpart.class.getName() , 
															"thirdpartid" , String.valueOf(ConstDefine.THRIDPART_ID_TEST),	
															"zwavedeviceid" , String.valueOf(ZWAVEDEVICEID_LOCK),
															"type" , IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE
															};
	
	public static final byte[] DOOR_LOCK_CLOSE_2 = new byte[]{98,3,(byte)255};
	public static final String[] FILTER_LOCK_CLOSE_2 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_CLOSE_2)};
	
	public static final byte[] DOOR_LOCK_CLOSE_3 = new byte[]{113,5,18,0,0,(byte)255,6,5,0};
	public static final String[] FILTER_LOCK_CLOSE_3 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_CLOSE_3)};
	
	public static final byte[] DOOR_LOCK_CLOSE_4 = new byte[]{113,5,21,1,0,(byte)255,6,1,0};
	public static final String[] FILTER_LOCK_CLOSE_4 = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_CLOSE_4)};

	public static final byte[] DOOR_LOCK_KEY_ERROR = new byte[]{(byte)128,7,0,(byte)224,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final String[] FILTER_LOCK_KEY_ERROR = new String[]{Notification.class.getName() , 
															"deviceid" , ConstDefine.DEVICE_ID,
															"nuid" , String.valueOf(NUID_LOCK),
															"message" , IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,
															"orimessage" , Utils.byteArraytoString(DOOR_LOCK_KEY_ERROR)};
	
	public static final String[] FILTER_LOCK_KEY_ERROR_TP_BG = new String[]{EventtoThirdpart.class.getName() , 
																	"thirdpartid" , String.valueOf(ConstDefine.THRIDPART_ID_BACKGROUD),	
																	"zwavedeviceid" , String.valueOf(ZWAVEDEVICEID_LOCK),
																	"type" , IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR
																	};
	
	public static final byte[] DOOR_LOCK_PASSWORD_ERROR_1 = new byte[]{113,5,(byte)161,1,0,(byte)255,6,16,0};
	public static final String[] FILTER_LOCK_PASSWORD_ERROR_1 = new String[]{Notification.class.getName() , 
																"deviceid" , ConstDefine.DEVICE_ID,
																"nuid" , String.valueOf(NUID_LOCK),
																"message" , IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,
																"orimessage" , Utils.byteArraytoString(DOOR_LOCK_PASSWORD_ERROR_1)};
	
	public static final String[] FILTER_LOCK_PASSWORD_ERROR_TP_BG = new String[]{EventtoThirdpart.class.getName() , 
																"thirdpartid" , String.valueOf(ConstDefine.THRIDPART_ID_BACKGROUD),	
																"zwavedeviceid" , String.valueOf(ZWAVEDEVICEID_LOCK),
																"type" , IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES
																};
	
	public static final byte[] DOOR_LOCK_PASSWORD_ERROR_2 = new byte[]{(byte)128,7,0,(byte)224,16,1,6,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0};
	public static final String[] FILTER_LOCK_PASSWORD_ERROR_2 = new String[]{Notification.class.getName() , 
																"deviceid" , ConstDefine.DEVICE_ID,
																"nuid" , String.valueOf(NUID_LOCK),
																"message" , IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,
																"orimessage" , Utils.byteArraytoString(DOOR_LOCK_PASSWORD_ERROR_2)};
	
	public static final byte[][] report = new byte[][]{DOOR_LOCK_OPEN_1,DOOR_LOCK_CLOSE_1,
														DOOR_LOCK_OPEN_2,DOOR_LOCK_CLOSE_2,
														DOOR_LOCK_OPEN_3,DOOR_LOCK_CLOSE_3,
														DOOR_LOCK_OPEN_4,DOOR_LOCK_CLOSE_4,
														DOOR_LOCK_OPEN_5,DOOR_LOCK_CLOSE_4,
														DOOR_LOCK_OPEN_6,DOOR_LOCK_CLOSE_4,
														DOOR_LOCK_OPEN_7,
														DOOR_LOCK_PASSWORD_ERROR_1,
														DOOR_LOCK_KEY_ERROR,
														DOOR_LOCK_PASSWORD_ERROR_2};
														
	public static final String[][] FILTER = new String[][]{FILTER_LOCK_OPEN_1_NONTIFICATION,FILTER_LOCK_CLOSE_1,
															FILTER_LOCK_OPEN_2,FILTER_LOCK_CLOSE_2,
															FILTER_LOCK_OPEN_3,FILTER_LOCK_CLOSE_3,
															FILTER_LOCK_OPEN_4,
															FILTER_LOCK_OPEN_5,
															FILTER_LOCK_KEY_ERROR,
															FILTER_LOCK_PASSWORD_ERROR_1,
															FILTER_LOCK_PASSWORD_ERROR_2,
															FILTER_LOCK_OPEN_TP_BG,
															FILTER_LOCK_KEY_ERROR_TP_BG,
															FILTER_LOCK_PASSWORD_ERROR_TP_BG};
}
