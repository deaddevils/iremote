package com.iremote.infraredtrans;

import com.iremote.common.TagDefine;
import com.iremote.common.processorstore.ClassMapper;
import com.iremote.infraredtrans.LoginProcessor;

public class ProcessorStore extends ClassMapper<IRemoteRequestProcessor> {
	
	private static ProcessorStore instance = new ProcessorStore();

	public static ProcessorStore getInstance()
	{
		return instance ;
	}
	
	protected ProcessorStore()
	{
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_CONNECT , TagDefine.COMMAND_SUB_CLASS_GATEWAY_INFO_RESPONSE), GatewayInfoReportProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_VERSION , TagDefine.COMMAND_SUB_CLASS_DEVICE_VERSION_RESPONSE), DeviceVersionProcessor.class);
		//registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_VERSION , TagDefine.COMMAND_SUB_CLASS_DEVICE_UPGRADE_RESPONSE), DeviceUpgradeResultProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_VERSION , TagDefine.COMMAND_SUB_CLASS_DEVICE_UPGRADE_RESPONSE), DeviceUpgardePackageReportProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_VERSION , TagDefine.COMMAND_SUB_CLASS_DEVICE_UPGRADE_WIFI_PACKAGE), DeviceUpgardePackageReportProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_VERSION , TagDefine.COMMAND_SUB_CLASS_DEVICE_UPGRADE_LOCK_PACKAGE), DeviceUpgardePackageReportProcessor.class);

		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_LOGIN ,TagDefine.COMMAND_SUB_CLASS_GATEWAY_LOGIN_RESPONSE), LoginProcessor.class);
		registProcessor(makeKey(100 , 4), HearBeatProcessor.class);
		registProcessor(makeKey(100 , 7), GatewayInfoProcesssor.class);
		
		registProcessor(makeKey(101 , 3), SetRemoteOwerProcessor.class);
		registProcessor(makeKey(102 , 1), CurrentTimeProcessor.class);
		registProcessor(makeKey(103 , 1), SetRemoteIpProcessor.class);
		registProcessor(makeKey(103 , 3), RemotePowerTypeProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY_103 , TagDefine.COMMAND_SUB_CLASS_GATEWAY_QUERY_WEATHER), QueryWeatherProcessor.class);
		
		registProcessor(makeKey(104 , 3), RemoteSleepProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_GATEWAY , TagDefine.COMMAND_SUB_CLASS_GATEWAY_RESET_REQUEST), DeviceResetProcessor.class);
		
		registProcessor(makeKey(105 , 2), CommandResponseProcessor.class);
		registProcessor(makeKey(105 , 6), CommandResponseProcessor.class);
		registProcessor(makeKey(107, 3),SeriaNetReportProcessor.class);

		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_MANAGER , TagDefine.COMMAND_SUB_CLASS_ADD_ZWAVEDEVICE_REPORT), GatewayAddZWaveDeviceReportProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE_MANAGER , TagDefine.COMMAND_SUB_CLASS_START_ZWAVEDEVICE_ADDING_PROGRESS_RESPONSE), GatewayAddZWaveDeviceStepsProcessor.class);

		registProcessor(makeKey(TagDefine.COMMAND_SUB_CLASS_INFRAREKEYSTUDY , TagDefine.COMMAND_SUB_CLASS_INFRAREKEYSTUDY_RESPONSE), InfraredKeyStudyStepsProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_SUB_CLASS_INFRAREKEYSTUDY , TagDefine.COMMAND_SUB_CLASS_INFRAREKEYSTUDY_CODE), InfraredKeyStudyCodeProcessor.class);


		registProcessor(makeKey(4 , 2), CommandResponseProcessor.class);
		registProcessor(makeKey(6 , 2), TemperatureReportProcessor.class);
		
		registProcessor(makeKey(30 , 8), CommandResponseProcessor.class);
		registProcessor(makeKey(30 , 9), ZWaveReportProcessor.class);
		registProcessor(makeKey(30 , 20), QueryValidNuidProcessor.class);
		registProcessor(makeKey(30 , 22), DeviceMalfunctionProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE , TagDefine.COMMAND_SUB_CLASS_DEVICE_GROUP_REPORT), CommandResponseProcessor.class);
		registProcessor(makeKey(TagDefine.COMMAND_CLASS_DEVICE , TagDefine.COMMAND_SUB_CLASS_DEVICE_BATCH_COMMAND_REPORT), CommandResponseProcessor.class);
	}
	
	public IRemoteRequestProcessor getProcessor(byte[] b , int index)
	{
		if ( b == null || b.length - index < 2 )
			return null ;
		return getProcessor(makeKey(b[index] & 0xff , b[index + 1] & 0xff));
	}
	
	protected String makeKey(int cmd , int subcmd)
	{
		return String.format("%d_%d", cmd , subcmd);
	}
}
