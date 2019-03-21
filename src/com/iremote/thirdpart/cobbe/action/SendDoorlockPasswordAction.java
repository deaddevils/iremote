package com.iremote.thirdpart.cobbe.action;

import java.util.Calendar;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.sms.SMSInterface;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid" , "deviceid" , "nuid"})
public class SendDoorlockPasswordAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid;
	private int nuid ;
	private String countrycode;
	private String phonenumber ;
	private PhoneUser phoneuser;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.querybydeviceid(deviceid, nuid);
		if ( zd == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		DoorlockPasswordService svr = new DoorlockPasswordService();
		List<DoorlockPassword> lst = svr.queryActivePassword(zd.getZwavedeviceid());
	
		for ( DoorlockPassword dlp : lst )
		{
			if ( ! Utils.isLockTempPassordforSMSSend(dlp.getUsercode()) ) 
				continue;
			if ( dlp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING
					|| dlp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED)
				continue;
			if ( dlp.getPhonenumber() != null )
				continue ;
			
			dlp.setPhonenumber(phonenumber);
			dlp.setValidthrough(Utils.currentTimeAdd(Calendar.HOUR_OF_DAY, 24));
			dlp.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
			
			MessageParser message = MessageManager.getMessageParser(IRemoteConstantDefine.MESSAGE_TYPE_TEMP_PASSWORD, phoneuser.getPlatform(), phoneuser.getLanguage());
			
			message.getParameter().put("phonenumber", phoneuser.getPhonenumber());
			message.getParameter().put("password", AES.decrypt2Str(dlp.getPassword()));
			
			//message = String.format(message ,phoneuser.getPhonenumber(), AES.decrypt2Str(dlp.getPassword()));
			
			SMSInterface.sendSMS(countrycode, phonenumber, message, phoneuser.getPlatform());
						
			return Action.SUCCESS;
		}
		
		resultCode = ErrorCodeDefine.DOORLOCK_PASSWORD_EXHAUST;
		
		return Action.SUCCESS;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setCountrycode(String countrycode)
	{
		this.countrycode = countrycode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}

