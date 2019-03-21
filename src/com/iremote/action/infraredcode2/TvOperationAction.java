package com.iremote.action.infraredcode2;

import com.iremote.common.IRemoteConstantDefine;

public class TvOperationAction extends TvStbOperationAction 
{

	@Override
	public String execute() 
	{
		super.setDevicetype(IRemoteConstantDefine.DEVICE_TYPE_TV);
		return super.execute();
	}
	
}
