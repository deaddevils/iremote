package com.iremote.test.data;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;

public class ConstDefine_DoorSensor {
	
	public static final int NUID_DOORSENSOR = 4 ;
	
	public static final String[] INIT_DOORSENSOR_DEVICE = new String[]{"status" , "0" , 
																"statuses" , "" , 
																"warningstatuses" , ""};
	public static final String[] INIT_DOORSENSOR_DEVICE_FILTER = new String[]{ZWaveDevice.class.getName() , 
																	"deviceid" , ConstDefine.DEVICE_ID,
																	"nuid" , String.valueOf(NUID_DOORSENSOR)};
	
	public static final String[][] FILTER_DOORSENSOR = new String[][]{new String[]{Notification.class.getName() , 
																			"deviceid" , ConstDefine.DEVICE_ID,
																			"nuid" , String.valueOf(NUID_DOORSENSOR),
																			"message" , IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,},
															   new String[]{EventtoThirdpart.class.getName(),
																	   		"deviceid" , ConstDefine.DEVICE_ID,
																	   		"type" , IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,}
	};
	
	public static final byte[] DOOR_OPEN = new byte[]{32,1,(byte)255};	

}
