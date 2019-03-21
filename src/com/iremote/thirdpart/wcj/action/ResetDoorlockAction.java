package com.iremote.thirdpart.wcj.action;

import com.iremote.action.device.doorlock.DoorlockPasswordHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class ResetDoorlockAction 
{
	private final static Byte[] REPORT = new Byte[]{(byte)0x80,0x07,0x00,0x60,0x10,0x01,0x02};
	private final static byte[] REQUEST = new byte[]{(byte)0x80,0x07,0x00,0x60,0x10,0x01,0x01};
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;

	private ZWaveDevice lock;
	private ThirdPart thirdpart;
	private PhoneUser phoneuser;

	private int expireSecond = 10;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		
		resultCode = DoorlockPasswordHelper.checkZwaveDevice(thirdpart, phoneuser, lock);

		if ( resultCode != ErrorCodeDefine.SUCCESS)
			return Action.SUCCESS;
		
		resultCode = ErrorCodeDefine.TIME_OUT;
		
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(REQUEST, lock.getNuid());

		ZwaveReportRequestWrap rst = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), ct, REPORT, expireSecond, 0);

		if ( rst.getResponse() != null )
			onReport(rst.getResponse());
		else 
			this.resultCode = rst.getAckresult();
		
		if ( this.resultCode == ErrorCodeDefine.SUCCESS)
		{
			DoorlockUserService dus = new DoorlockUserService();
			dus.deletebyZwavedeviceid(zwavedeviceid);
		}
		
		return Action.SUCCESS;
	}

	private void onReport(byte[] report)
	{
		if ( report[7] == 0 ) // 1:success , 0: failed
			resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
		else 
			resultCode = ErrorCodeDefine.SUCCESS;
	}


	public int getResultCode()
	{
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
}
