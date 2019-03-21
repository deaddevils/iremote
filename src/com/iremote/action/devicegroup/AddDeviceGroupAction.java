package com.iremote.action.devicegroup;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.PhoneUserDataPrivilegeCheckor;
import com.iremote.domain.DeviceGroup;
import com.iremote.domain.DeviceGroupDetail;
import com.iremote.domain.PhoneUser;
import com.iremote.service.DeviceGroupService;
import com.opensymphony.xwork2.Action;

public class AddDeviceGroupAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int devicegroupid;
	private String devicegroupname ;
	private String devicetype;
	private String icon;
	private String zwavedevices ;
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( StringUtils.isBlank(zwavedevices))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		JSONArray ja = JSON.parseArray(zwavedevices);
		if ( checkPrivilege(ja) == false )
			return Action.SUCCESS;
		
		saveDeviceGroup(ja);
		
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "adddevicegroup");
		if ( phoneuser != null )
			phoneuser.setLastupdatetime(new Date());

		return Action.SUCCESS;
	}
	
	private void saveDeviceGroup(JSONArray ja)
	{
		DeviceGroup dg = new DeviceGroup();
		dg.setDevicegroupname(devicegroupname);
		dg.setIcon(icon);
		dg.setPhoneuserid(phoneuser.getPhoneuserid());
		dg.setZwavedevices(new ArrayList<DeviceGroupDetail>());
		dg.setDevicetype(devicetype);
		
		for ( int i = 0 ; i < ja.size() ; i ++ )
		{
			DeviceGroupDetail dgd = new DeviceGroupDetail();
			dgd.setDevicegroup(dg);
			dgd.setZwavedeviceid(ja.getJSONObject(i).getIntValue("zwavedeviceid"));
			dgd.setChannelidsstr(ja.getJSONObject(i).getString("channelids"));
			
			dg.getZwavedevices().add(dgd);
		}
		
		DeviceGroupService dgs = new DeviceGroupService();
		devicegroupid = dgs.save(dg);
	}
	
	private boolean checkPrivilege(JSONArray ja )
	{
		PhoneUserDataPrivilegeCheckor dp = new PhoneUserDataPrivilegeCheckor(phoneuser);
		
		for ( int i = 0 ; i < ja.size() ; i ++ )
		{
			if ( dp.checkZWaveDeviceOperationPrivilege(ja.getJSONObject(i).getIntValue("zwavedeviceid")) == false )
			{
				this.resultCode = ErrorCodeDefine.NO_PRIVILEGE;
				return false;
			}
		}
		return true;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setDevicegroupname(String devicegroupname)
	{
		this.devicegroupname = devicegroupname;
	}

	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public void setZwavedevices(String zwavedevices)
	{
		this.zwavedevices = zwavedevices;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public int getDevicegroupid()
	{
		return devicegroupid;
	}
	
	
}
