package com.iremote.action.device.doorlock;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;
import com.opensymphony.xwork2.Action;

public class ChuangjiaDeleteDoorlockCardAction 
{
	private ZWaveDevice zWaveDevice;
	private DoorlockUser doorlockUser;

	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute() throws InterruptedException
	{
		sendcommand();

		if ( resultCode == ErrorCodeDefine.SUCCESS)
			deletedoorlockuser();

		return Action.SUCCESS;
	}
	
	private void deletedoorlockuser()
	{
		DoorlockUserService doorlockUserService = new DoorlockUserService();
		doorlockUserService.delete(doorlockUser);
	}

	private void sendcommand() 
	{
		byte[] command = new byte[]{(byte)0x80,0x07,0x00,(byte)0x90,0x10,0x01,0x09,(byte)doorlockUser.getUsercode(),0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(command, zWaveDevice.getNuid());

		Byte[] rk = new Byte[]{(byte)0x80,0x07,0x00,(byte)0x90,0x10,0x01,0x0A,(byte)doorlockUser.getUsercode()};

		ZwaveReportRequestWrap rst = ZwaveReportRequestManager.sendRequest(zWaveDevice.getDeviceid(), zWaveDevice.getNuid(), ct, rk, 0 , 0 );
		
		if ( rst.getResponse() != null )
			reportArrive(rst.getResponse());
		else 
			this.resultCode = rst.getAckresult();
	}
	
	public void reportArrive(byte[] report)
	{
		if ( report == null )
			this.resultCode = ErrorCodeDefine.TIME_OUT;
		else if ( report[8] == 1 )
			resultCode = ErrorCodeDefine.SUCCESS ;
		else if ( report[8] == 0 )
			resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
	}
	

	public int getResultCode() {
		return resultCode;
	}

	public void setzWaveDevice(ZWaveDevice zWaveDevice)
	{
		this.zWaveDevice = zWaveDevice;
	}

	public void setDoorlockUser(DoorlockUser doorlockUser)
	{
		this.doorlockUser = doorlockUser;
	}


}
