package com.iremote.action.device.doorlock;

import java.util.Calendar;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class SendCurrentTimeAction {
	private int zwavedeviceid;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute()
	{
		ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();
		ZWaveDevice zWaveDevice = zWaveDeviceService.query(zwavedeviceid);
		
		if(zWaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		Calendar ct = Calendar.getInstance();
		byte[] b = new byte[]{(byte)0x8B,0x01,
							(byte)(ct.get(Calendar.YEAR) / 256 ),
							(byte)(ct.get(Calendar.YEAR) % 256 ),
							(byte)(ct.get(Calendar.MONTH) + 1 ),
							(byte)ct.get(Calendar.DAY_OF_MONTH),
							(byte)ct.get(Calendar.HOUR_OF_DAY),
							(byte)ct.get(Calendar.MINUTE),
							(byte)ct.get(Calendar.SECOND)};
		
		byte[] rp = SynchronizeRequestHelper.synchronizeRequest(zWaveDevice.getDeviceid(), DoorlockPasswordHelper.createCommandTlv(b, zWaveDevice.getNuid()) , 10);
		
		if ( rp == null )
			resultCode = ErrorCodeDefine.GATEWAY_ERROR_CODE_BUSSING;
		   
		return Action.SUCCESS;
	}
	
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public int getResultCode() {
		return resultCode;
	}
	
	
}
