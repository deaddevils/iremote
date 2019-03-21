package com.iremote.thirdpart.wcj.action;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class SetLockFingerprintUserAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;
	private int usercode ;
	private int asynch = 0;
	private String validfrom;
	private String validthrough;
	private Integer weekday;
	private String starttime ;
	private String endtime;
	private ZWaveDevice lock;
	private ThirdPart thirdpart;
	private PhoneUser phoneuser;
	private int usertype = 3 ; //3 Fingerprint

	private DoorlockPasswordService dpsvr = new DoorlockPasswordService();
	private DoorlockPassword doorlockpassword;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		
		if ( check() == false )
			return Action.SUCCESS;
		
		updateFingerprint();
		
		saveFingerprint();
		
		if (asynch == 0 )
			DoorlockPasswordTaskManager.addTask(lock.getDeviceid(), new SetPasswordThread(lock.getZwavedeviceid() , (byte)usercode, (byte)3));
		else 
		{
			SetPasswordThread pt = new SetPasswordThread(doorlockpassword);
			
			pt.run();
			this.resultCode = pt.getResultCode();
		}
		
		return Action.SUCCESS;
	}
	
	private void updateFingerprint()
	{

		DoorlockPassword dp = dpsvr.queryLatestActivePassword(zwavedeviceid, usertype,usercode);
		if ( dp != null )
			dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
	}
	
	private void saveFingerprint()
	{
		doorlockpassword = new DoorlockPassword();
		doorlockpassword.setZwavedeviceid(zwavedeviceid);
		doorlockpassword.setPassword("");
		doorlockpassword.setValidfrom(Utils.parseTime(validfrom));
		doorlockpassword.setValidthrough(Utils.parseTime(validthrough));
		doorlockpassword.setWeekday(weekday);
		doorlockpassword.setStarttime(starttime);
		doorlockpassword.setEndtime(endtime);
		doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
		doorlockpassword.setUsercode(usercode);
		doorlockpassword.setUsertype(usertype);
		if(lock.getNuid()<256){
			doorlockpassword.setLocktype(1);
		}else{
			doorlockpassword.setLocktype(2);
		}

		if ( asynch == 0 )
			dpsvr.save(doorlockpassword);
	}
	
	private boolean check()
	{
		if ( lock == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}
		
		if ( lock.getStatus() != null && lock.getStatus() == -1 )
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		
		if ( ConnectionManager.isOnline(lock.getDeviceid()) == false )
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}

		return true;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setValidfrom(String validfrom)
	{
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough)
	{
		this.validthrough = validthrough;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setUsercode(int usercode)
	{
		this.usercode = usercode;
	}

	public void setAsynch(int asynch)
	{
		this.asynch = asynch;
	}
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
