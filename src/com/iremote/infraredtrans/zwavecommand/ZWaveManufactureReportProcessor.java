package com.iremote.infraredtrans.zwavecommand;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;

public class ZWaveManufactureReportProcessor extends ZWaveReportBaseProcessor
{
	
	public ZWaveManufactureReportProcessor()
	{
		super();
		this.dontpusmessage();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		byte[] pb = new byte[6];
		System.arraycopy(zrb.getCmd(), 2, pb, 0, 6);
		String p = JWStringUtils.toHexString(pb);
		
		if ( StringUtils.isNotBlank(p) && !p.equals(zrb.getDevice().getProductor()))
		{
			zrb.getDevice().setProductor(p);
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zrb.getDeviceid() , new Date() , zrb.getReportid()) );
		}
		
		if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.equals(zrb.getDevice().getDevicetype()))
			DeviceHelper.readDeviceProductor(zrb.getDevice());
	}

	@Override
	public String getMessagetype()
	{
		return null;
	}

}
