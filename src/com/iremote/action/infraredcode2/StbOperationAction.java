package com.iremote.action.infraredcode2;

import com.iremote.common.IRemoteConstantDefine;

public class StbOperationAction extends TvStbOperationAction 
{
	@Override
	public String execute() 
	{
		super.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_STB);
		return super.execute();
	}
}
