package com.iremote.infraredtrans.zwavecommand.dsc;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

@Deprecated
public class DSCAlarmReportProcessor extends ZWaveReportBaseProcessor
{
	private static Log log = LogFactory.getLog(DSCAlarmReportProcessor.class);
	
	private int channelid;
	private int status;
	private String message;
	private String combindname ;
		
	@Override
	protected void updateDeviceStatus()
	{
		if ( zrb.getCmd().length < 3 )
			return ;
		
		channelid = zrb.getCommandvalue().getChannelid();
		status = (zrb.getCmd()[6] & 0xff);
		
		if ( status == 0xff)
		{
			super.appendWarningstatus(channelid);
			message = IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM ;
		}
		else if ( status == 0 )
		{
			zrb.getDevice().setWarningstatuses(Utils.jsonArrayRemove(zrb.getDevice().getWarningstatuses(), channelid));
			message = "unalarm" + IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM ;
		}  
		
		combindname = this.createname();
	}

	@Override
	public String getMessagetype()
	{
		return message;
	}
	
	@Override
	protected void pushMessage()
	{
		if ( !this.pushmessage 
				|| zrb.getDevice().getEnablestatus() ==  IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			return ;
		
		String mt = this.getMessagetype();
		if ( mt == null || mt.length() == 0 )
			return; 
		
//		if ( status == 0 )
//			mt = IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
		
		ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
		try {
			PropertyUtils.copyProperties(zde, zrb.getDevice());
		} catch (Throwable t) {
			log.error(t.getMessage() , t);
		}
		zde.setName(combindname);
		zde.setWarningstatus(this.channelid);
		zde.setEventtime(zrb.getReporttime());
		zde.setEventtype(mt);
		zde.setReport(zrb.getReport());
		zde.setAppendmessage(getAppendMessage());
		zde.setDevicetype(zrb.getDevice().getDevicetype());
		zde.setTaskIndentify(zrb.getReportid());
		zde.setChannel(zrb.getCommandvalue().getChannelid());
		zde.setOldstatus(this.oldstatus);
		zde.setOldstatuses(this.oldstatuses);
		zde.setOldshadowstatus(oldshadowstatus);
		
		
		JMSUtil.sendmessage(mt, zde);
	}
	
	@Override
	protected void afterprocess()
	{
		if ( this.notification == null )
			return ;
		this.notification.setName(combindname);

	}
	
	private String createname()
	{
		String name = this.zrb.getDevice().getName();
		if ( name == null )
			name = "";
		if ( this.zrb.getDevice().getzWaveSubDevices() == null )
			return name;
		for ( ZWaveSubDevice zds : this.zrb.getDevice().getzWaveSubDevices())
		{
			if ( zds.getChannelid() == this.channelid)
			{
				if ( StringUtils.isNotBlank(zds.getName() ))
				{
					return String.format("%s %s", name , zds.getName());
				}
			}
		}
		return name;
	}
	
	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue());
	}
}
