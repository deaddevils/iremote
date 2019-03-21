package com.iremote.infraredtrans.zwavecommand.doorlock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ThirdPart;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;
import com.iremote.service.ThirdPartService;

public class DoorlockReportProcessor extends DoorlockStatusReportBase
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DoorlockReportProcessor.class);
	
	private int status ;
	private String message ;
	private String operator ;

	@Override
	protected void parseReport()
	{
		status = zrb.getCommandvalue().getValue() ;
		
		if ( !isValidReport())
			return ;
		
		if ( this.isOpenreport())
		{
			status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN;
			message = IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
		}
		else 
		{
			status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE;
			message = IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE;
		}
	}

	protected void updateDeviceStatus()
	{		
		if ( !isValidReport())
			return ;

		this.operator = zrb.getOperator();
		
		zrb.getDevice().setShadowstatus(status);
		if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
			zrb.getDevice().setStatus(status);

		if (this.isOpenreport())
		{
			if (!zdah.hasArmedByUserSetting())
				return;
			if (!zdah.hasSetDelayAlarm())
				super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
			else
				createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb,
						IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		}
	}

	@Override
	protected void afterprocess() 
	{
		super.afterprocess();
		super.notification.setEclipseby(0);  //door lock open event should not be eclipsed.
	}

	protected String getOperateorName()
	{
		if ( operator == null || operator.length() == 0 )
			return operator;
		
		ThirdPartService tps = new ThirdPartService();
		
		ThirdPart tp = tps.query(operator);
		if ( tp == null || tp.getName() == null || tp.getName().length() == 0 )
			return operator;
		
		return tp.getName();
	}
	
	@Override
	protected JSONObject getAppendMessage() 
	{
		String username = getOperateorName();
		
		if ( StringUtils.isBlank(username))
			return super.getAppendMessage();
		
		JSONObject json = new JSONObject();
		json.put("username", username);
		json.put("usertype", IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_REMTOE);
		return json ;
	}

	private boolean isValidReport()
	{
		if ( status != 0xff && status != 0x00  && status != 0x01 && status != 0x10  && status != 0x11 && status != 0x20  && status != 0x21 )
			return false; 
		return true;
	}
	
	private boolean isOpenreport() 
	{
		int status = zrb.getCommandvalue().getValue() ;
		return (  status == 0x00  || status == 0x01 || status == 0x10  || status == 0x11 || status == 0x20  || status == 0x21 );
	}

	@Override
	public String getMessagetype() 
	{
		if ( !isValidReport())
			return null ;
		
		if ( this.isOpenreport())
			return IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
		else 
			return IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		if ( !isValidReport())
			return null ;
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , status);
	}

	//All reports of door locks should be processed , include duplicate reports , except this one .
	@Override
	protected boolean isDuplicate(IZwaveReportCache current)
	{
		super.isDuplicate(current);

		return super.duplicated ;
	}
}
