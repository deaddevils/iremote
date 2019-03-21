package com.iremote.action.camera;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Camera;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.RoomAppliance;
import com.iremote.service.CameraService;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.RemoteService;
import com.iremote.service.RoomApplianceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "camera", parameter = "cameraid")
public class EditCameraAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int cameraid;
	private String name;
	private PhoneUser phoneuser ;
	private int delay;
	private int inhomenotalarm;
	
	public String execute()
	{
		if ( StringUtils.isBlank(name))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		RemoteService rs = new RemoteService();
		List<String> ldid = rs.queryDeviceidbyPhoneUserid(phoneuser.getPhoneuserid());
		
		CameraService cs = new CameraService();
		List<Camera> lst = cs.querybydeviceid(ldid);
		
		for ( Camera c : lst )
			if ( c.getCameraid() != cameraid && name.equals(c.getName()) )
			{
				resultCode = ErrorCodeDefine.NAME_IS_EXIST;
				return Action.SUCCESS;
			}
		Camera c = cs.query(cameraid);
		
		if ( c == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		c.setName(name);
		
		RoomApplianceService roomApplianceService = new RoomApplianceService();
		RoomAppliance ra = roomApplianceService.querybyCameraid(cameraid);
		
		if ( ra != null )
			ra.setName(name);
		DeviceCapabilityService dcs = new DeviceCapabilityService();
		DeviceCapability query = dcs.queryByCamera(c, IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
		DeviceCapability query2 = dcs.queryByCamera(c, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
		if(query!=null){
			query.setCapabilityvalue(String.valueOf(delay));
		}else{
			DeviceCapability deviceCapability = new DeviceCapability(c,IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
			deviceCapability.setCapabilityvalue(String.valueOf(delay));
			dcs.saveOrUpdate(deviceCapability);
		}
		if(inhomenotalarm==1&&query2==null){
			DeviceCapability deviceCapability = new DeviceCapability(c,IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
			dcs.save(deviceCapability);
		}else if(inhomenotalarm==0&&query2!=null){
			dcs.delete(query2);
		}
		
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setCameraid(int cameraid)
	{
		this.cameraid = cameraid;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setInhomenotalarm(int inhomenotalarm) {
		this.inhomenotalarm = inhomenotalarm;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}
