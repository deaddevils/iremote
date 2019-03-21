package com.iremote.action.device;

import java.util.Date;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.service.InfraredDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "infrareddevice", parameter = "infrareddeviceid")
public class DeleteInfraredDeviceAction
{
	private int resultCode = ErrorCodeDefine.PARMETER_ERROR;
	private int infrareddeviceid ;
	private PhoneUser phoneuser;

	public String execute()
	{
		InfraredDeviceService ids = new InfraredDeviceService();
		InfraredDevice id = ids.query(infrareddeviceid);
		
		if ( id == null )
			return Action.SUCCESS;
		
		DeviceHelper.clearInfraredDevice(id);
		ids.delete(id);
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(id.getDeviceid() , new Date() , 0) );

		this.resultCode = ErrorCodeDefine.SUCCESS;
		
		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setInfrareddeviceid(int infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	
}
