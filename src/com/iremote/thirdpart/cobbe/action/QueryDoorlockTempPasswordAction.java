package com.iremote.thirdpart.cobbe.action;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid", "deviceid"})
public class QueryDoorlockTempPasswordAction
{

	private int resultCode = ErrorCodeDefine.SUCCESS; 
	private String deviceid ;
	private int nuid = IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK;
	private String password ;
	private int zwavedeviceid ;
	private int usercode ;
	private String validthrough;
	
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		ZWaveDevice zd ;
		if ( zwavedeviceid == 0 )	
			zd = zds.querybydeviceid(deviceid, nuid);
		else 
			zd = zds.query(zwavedeviceid);
		
		if ( zd == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}

		zwavedeviceid = zd.getZwavedeviceid();

		DoorlockPasswordService svr = new DoorlockPasswordService();
		List<DoorlockPassword> lst = svr.queryActivePassword(zd.getZwavedeviceid());
	
		for ( DoorlockPassword dlp : lst )
		{
			if ( ! Utils.isLockTempPassordforSMSSend(dlp.getUsercode()) ) 
				continue;
			if ( dlp.getPhonenumber() != null )
				continue ;
			if ( dlp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING
					|| dlp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING)
				continue;
			
			if ( thirdpart != null )
				dlp.setPhonenumber(thirdpart.getName());
			else if ( phoneuser != null )
				dlp.setPhonenumber(phoneuser.getPhonenumber());
			else 
				dlp.setPhonenumber(" ");
			if ( StringUtils.isNotBlank(this.validthrough))
				dlp.setValidthrough(Utils.parseTime(this.validthrough));
			else 
				dlp.setValidthrough(Utils.currentTimeAdd(Calendar.HOUR_OF_DAY, 24));
			dlp.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
			
			
			password = AES.decrypt2Str(dlp.getPassword());
			usercode = dlp.getUsercode();
									
			return Action.SUCCESS;
		}
		
		resultCode = ErrorCodeDefine.DOORLOCK_PASSWORD_EXHAUST;
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public String getPassword()
	{
		return password;
	}
	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}
	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

	public int getUsercode()
	{
		return usercode;
	}

	public void setValidthrough(String validthrough)
	{
		this.validthrough = validthrough;
	}
	
	
}
