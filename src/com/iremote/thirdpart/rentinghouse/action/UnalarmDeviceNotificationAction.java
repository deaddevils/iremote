package com.iremote.thirdpart.rentinghouse.action;

import com.iremote.action.notification.UnalarmNotificationAction;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.vo.ZWaveDeviceUnalarmEvent;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class UnalarmDeviceNotificationAction extends UnalarmNotificationAction {

	private int zwavedeviceid;
	private ThirdPart thirdpart ;
	private String employeename ; 
	
	@Override
	protected void init()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwavedevice =  zds.query(zwavedeviceid);
		
		this.setDeviceid(zwavedevice.getDeviceid());
		this.setNuid(zwavedevice.getNuid());

		IremotepasswordService is = new IremotepasswordService();
		remote = is.getIremotepassword(zwavedevice.getDeviceid());
		
		PhoneUserService pus = new PhoneUserService();
		if ( remote.getPhoneuserid() != null )
			phoneuser = pus.query(remote.getPhoneuserid());
	}

	@Override
	protected boolean checkprivilege() 
	{
		if ( phoneuser == null )
			return false ;
		
		if ( thirdpart.getThirdpartid() == IRemoteConstantDefine.DEFAULT_THIRDPART_ID )
			return true;
		
		ComunityRemoteService crs = new ComunityRemoteService();
		if ( crs.query(thirdpart.getThirdpartid(), super.getDeviceid()) == null )
			return false ;

		return true;
	}

	@Override
	protected void setUnalarmuser(Notification notification) 
	{
		notification.setAppendmessage(employeename);
		notification.setUnalarmphonenumber(thirdpart.getName());
		notification.setPhoneuserid(remote.getPhoneuserid());
	}
	
	@Override
	protected ZWaveDeviceUnalarmEvent createUnalarmEventObject(String message)
	{
		ZWaveDeviceUnalarmEvent ue = new ZWaveDeviceUnalarmEvent(zwavedevice.getZwavedeviceid() , zwavedevice.getDeviceid() , zwavedevice.getNuid() ,message,reporttime);
		ue.setEmployeename(employeename);
		return ue ;
	}
	

	@Override
	protected PhoneUser getPhoneUser() {
		return phoneuser;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	
	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

}
