package com.iremote.action.infraredcode;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.InfreredDeviceProductor;
import com.iremote.service.InfreredDeviceProductorService;
import com.opensymphony.xwork2.Action;

public class QueryProductorAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String devicetype;
	private List<InfreredDeviceProductor> productorlist;
	
	public String execute()
	{
		if ( StringUtils.isBlank(devicetype))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		InfreredDeviceProductorService svr = new InfreredDeviceProductorService();
		this.productorlist = svr.queryByDeviceType(devicetype);
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public List<InfreredDeviceProductor> getProductorlist()
	{
		return productorlist;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	
	
}
