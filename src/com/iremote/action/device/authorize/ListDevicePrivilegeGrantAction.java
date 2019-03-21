package com.iremote.action.device.authorize;

import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.DeviceShareSource;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.ZWaveDeviceShareService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class ListDevicePrivilegeGrantAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid ;
	private List<ZWaveDeviceShare>  shares;
	
	public String execute()
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		
		shares = zdss.querybyZwaveDeviceid(zwavedeviceid, DeviceShareSource.phoneusertemp);
		
		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public List<ZWaveDeviceShare> getShares()
	{
		return shares;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	
	
}
