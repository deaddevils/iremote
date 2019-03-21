package com.iremote.action.device.doorlock;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;
import com.opensymphony.xwork2.Action;

public class JwzhDeleteDoorlockCardAction
{
	private ZWaveDevice zWaveDevice;
	private DoorlockUser doorlockUser;

	private static Byte[] REPORT_DELETE_CARD_REPORT = new Byte[]{(byte)0x80,0x09,0x04};
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute() throws InterruptedException
	{
		byte[] command = DoorlockPasswordHelper.createDeleteCardCommand(doorlockUser.getUsercode());
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(command, zWaveDevice.getNuid());
		
		ZwaveReportRequestWrap rst = ZwaveReportRequestManager.sendRequest(zWaveDevice.getDeviceid(), zWaveDevice.getNuid(), ct, REPORT_DELETE_CARD_REPORT, 5, 0);

		if ( rst.getResponse() != null )
			onReport(rst.getResponse());
		else if ( rst.getAckresult() != ErrorCodeDefine.SUCCESS )
			this.resultCode = rst.getAckresult();
		else 
			this.resultCode = ErrorCodeDefine.TIME_OUT;
		
		if ( resultCode == ErrorCodeDefine.SUCCESS)
			deletedoorlockuser();

		return Action.SUCCESS;
	}
	
	private void deletedoorlockuser()
	{
		DoorlockUserService doorlockUserService = new DoorlockUserService();
		doorlockUserService.delete(doorlockUser);
	}

	private void onReport(byte[] report)
	{
		if ( report[3] == 1 )
			resultCode = ErrorCodeDefine.SUCCESS ;
		else 
			resultCode = ErrorCodeDefine.UNKNOW_ERROR;
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
