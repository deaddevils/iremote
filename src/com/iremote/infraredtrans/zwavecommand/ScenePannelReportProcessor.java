package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class ScenePannelReportProcessor extends ZWaveReportBaseProcessor 
{

	public ScenePannelReportProcessor()
	{
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		this.zrb.getCommandvalue().setChannelid(zrb.getCmd()[2]);
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.MESSGAE_TYPE_SCENE_PANNEL_TRIGGER;
	}

}
