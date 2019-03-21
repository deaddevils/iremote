package com.iremote.action.qrcode;

import com.iremote.common.processorstore.ClassMapper;

public class ScanCodeActionProcessorStore extends ClassMapper<IScanQrCodeProcessor>
{
	private static ScanCodeActionProcessorStore instance = new ScanCodeActionProcessorStore();
	
	
	public static ScanCodeActionProcessorStore getInstance()
	{
		return instance ;
	}
	
	private ScanCodeActionProcessorStore()
	{
		registProcessor("powerfreedevice", AddPowerfreeDeviceAction.class);
		registProcessor("gateway", AddGatewayAction.class);
		registProcessor("lockgateway", AddGatewayAction.class);
		registProcessor("airqulitygateway", AddGatewayAction.class);
		
	}
}
