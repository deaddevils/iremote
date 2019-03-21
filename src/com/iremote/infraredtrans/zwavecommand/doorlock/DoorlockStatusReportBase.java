package com.iremote.infraredtrans.zwavecommand.doorlock;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;

public abstract class DoorlockStatusReportBase extends ZWaveReportBaseProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DoorlockStatusReportBase.class);
	protected boolean duplicated = false ;
	
	@Override
	public void run() 
	{
		//discard reports from wake-up gateways without report time . 
		if ( GatewayUtils.isCobbeLock(this.zrb.getRemote()))
		{
			if ( zrb.getIntreportime() == 0 )
			{
				this.dontsavenotification();
				return ;
			}
		}
		super.run();
		
		DeviceHelper.readDeviceProductor(zrb.getDevice());
	}

	
	protected void afterprocess()
	{
		this.notification.setAppendmessage(getOperateorName());
	}
	
	protected abstract String getOperateorName();


	//All reports of door locks should be processed , include duplicate reports.
	@Override
	protected boolean isDuplicate(IZwaveReportCache current)
	{
		duplicated = super.isDuplicate(current);

		return false ;
	}

	//But duplicate reports do not push JMS message , except a message to update database.
	@Override
	protected void pushMessage()
	{
		if ( !duplicated)
			super.pushMessage();
		else 
		{
			pushDuplicatedMessage();
			log.info("duplicated report , do not push jms message");
		}
	}
	
	protected void pushDuplicatedMessage()
	{
		if ( !this.pushmessage 
				|| zrb.getDevice().getEnablestatus() ==  IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			return ;
		
		String mt = this.getMessagetype();
		if ( mt == null || mt.length() == 0 )
			return; 
		
		mt += "_duplicated";
		
		ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
		try {
			PropertyUtils.copyProperties(zde, zrb.getDevice());
		} catch (Throwable t) {
			log.error(t.getMessage() , t);
		}
		
		zde.setWarningstatus(this.warningstatus);
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
		zde.setApptokenid(zrb.getApptokenid());
		JMSUtil.sendmessage(mt, zde);
	}
	
}
