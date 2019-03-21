package com.iremote.test.data;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;

public class ConstDefine_Gas 
{
	public static final int NUID_GAS = 3 ;
	public static final String[][] FILTER_GAS = new String[][]{new String[]{Notification.class.getName() , 
																			"deviceid" , ConstDefine.DEVICE_ID,
																			"nuid" , String.valueOf(NUID_GAS),
																			"message" , IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,},
															   new String[]{EventtoThirdpart.class.getName(),
																	   		"deviceid" , ConstDefine.DEVICE_ID,
																	   		"type" , IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,
																	   		"thirdpartid" , "1"},
															   new String[]{EventtoThirdpart.class.getName(),
																   		"deviceid" , ConstDefine.DEVICE_ID,
																   		"type" , IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,
																   		"thirdpartid" , "2"},
	};
	
	public static final String[] INIT_GAS_DEVICE = new String[]{"status" , "0" , 
																"statuses" , "" , 
																"warningstatuses" , ""};
	public static final String[] INIT_GAS_DEVICE_FILTER = new String[]{ZWaveDevice.class.getName() , 
																		"deviceid" , ConstDefine.DEVICE_ID,
																		"nuid" , String.valueOf(NUID_GAS)};
	
	public static final byte[] GAS_ALARM = new byte[]{(byte)156,(byte)2,(byte)8,(byte)2,(byte)255,(byte)0,(byte)0};	
}
