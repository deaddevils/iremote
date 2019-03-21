package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.domain.DoorlockUser;
import com.iremote.service.DoorlockUserService;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.ThirdPart;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;
import com.iremote.service.ThirdPartService;

public class DoorlockOpenReportProcessor extends DoorlockStatusReportBase
{
	protected int usercode;
	protected String username ;
	protected boolean isStandardLock = false;

	@Override
	protected void updateDeviceStatus()
	{
		initusercode();
		zrb.getDevice().setShadowstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
			zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		if (!zdah.hasArmedByUserSetting())
			return;
		if (!zdah.hasSetDelayAlarm())
			super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		else
			createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb,
					IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}

	protected void afterprocess()
	{
		this.notification.setEclipseby(0);
		super.afterprocess();
		if (isStandardZwaveDoorLock() && (zrb.getCmd()[7] & 0xff) == 6) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("edit", "lockusername");
			this.notification.setAppendjson(jsonObject);
		}
	}


	protected void initusercode()
	{
		if ( (zrb.getCmd()[7] & 0xff) == 2 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_KEY_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
		else if ( (zrb.getCmd()[7] & 0xff) == 4 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
		else if ( (zrb.getCmd()[7] & 0xff) == 6 )
		{
			if (isStandardLock) {
				usercode = (zrb.getCmd()[3] & 0xff);
			} else {
				usercode = (zrb.getCmd()[9] & 0xff);
			}
			DoorlockUserService dus = new DoorlockUserService();
			DoorlockUser du = dus.query(zrb.getDevice().getZwavedeviceid(), IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD & 0xff, usercode);
			if (du != null && du.getUsername() != null) {
				username = du.getUsername();
			} else {
				username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser()));
				username += usercode ;
			}
		}
	}

	protected JSONObject getAppendMessage()
	{
		JSONObject json = new JSONObject();
		json.put("usercode", usercode);
		json.put("username", username);
		return json ;
	}

	protected String getOperateorName()
	{
		if ( StringUtils.isEmpty(username) )
			return this.getOperateorName(this.zrb.getOperator());
		else
			return username;
	}

	protected String getOperateorName(String operator)
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
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}

	@Override
	protected boolean isDuplicate(IZwaveReportCache current)
	{
		isStandardLock = isStandardZwaveDoorLock();

		if (!isStandardLock) {
			return super.isDuplicate(current);
		} else {
			super.isDuplicate(current);
			return super.duplicated ;
		}
	}
}
