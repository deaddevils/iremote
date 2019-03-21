package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.NumberUtil;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ThirdPartService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

public class DoorlockSelfDefineOpenwithPasswordReportProcessor extends DoorlockStatusReportBase
{
	protected int usercode;
	protected String username ;
	protected int usertype;
	protected String message = IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;

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
	}

	protected void initusercode()
	{
		usertype = zrb.getCmd()[8] & 0xff ;
		byte[] uc = new byte[8];
		System.arraycopy(zrb.getCmd(), 9, uc, 0, uc.length);
		String suc = new String(uc);
		
		if ( IRemoteConstantDefine.DOOR_LOCK_TEMP_USER.equals(suc) )
		{
			usercode = 0xF2;
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_TEMP_USER,0, getLanguange());
		}
		else if ( IRemoteConstantDefine.DOOR_LOCK_ADMIN_USER.equals(suc) )
		{
			usercode = 0xF0;
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_ADMIN_USER, 0,getLanguange());
		}
		else if ( suc != null && suc.startsWith(IRemoteConstantDefine.DOOR_LOCK_USER))
		{
			usercode = Integer.valueOf(suc.replace(IRemoteConstantDefine.DOOR_LOCK_USER, "")) ;
			username = String.format("%s%d",MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER,0, getLanguange()) , usercode);
		}
		else if ( usertype == 0x00 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_KEY_USER, 0,getLanguange());
		else if ( usertype == 0x15 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER, 0,getLanguange());
		else if ( usertype == 0x16 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FINGER_USER, 0,getLanguange());
		else if ( usertype == 0x20 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER, 0,getLanguange());
		else if ( usertype == 0x27 )
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FACE_USER, 0,getLanguange());
		else if (usertype == 0x29) {
			username = createUserName(getPhoneUserName(),
					MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_BLUE_TOOTH_USER, 0, getLanguange()));
		}
	}

	protected String createUserName(String name, String username){
		return name == null ? username : String.format("%s(%s)", name, username);
	}

	protected String getPhoneUserName() {
		int phoneUserId = NumberUtil.byte4ToInt(zrb.getCmd(), 9);
		PhoneUser user = new PhoneUserService().query(phoneUserId);
		return user == null ? null : StringUtils.isBlank(user.getName()) ? user.getPhonenumber() : user.getName();
	}

	protected JSONObject getAppendMessage()
	{
		JSONObject json = new JSONObject();
		json.put("usercode", usercode);
		json.put("username", username);
		json.put("usertype", usertype);
		return json ;
	}

	
	protected String getOperateorName()
	{
		if ( usercode == 0 && ( username == null  || username.length() == 0 ) )
			return this.getOperateorName(this.zrb.getOperator());
		else 
			return username;
	}
	
	protected String getLanguange()
	{
		return PhoneUserHelper.getLanguange(zrb.getPhoneuser());
	}
	
	private String getOperateorName(String operator)
	{
		ThirdPartService tps = new ThirdPartService();
		
		if ( operator == null || operator.length() == 0 )
			return operator;
		ThirdPart tp = tps.query(operator);
		if ( tp == null || tp.getName() == null || tp.getName().length() == 0 )
			return operator;
		
		return tp.getName();
	}

	@Override
	public String getMessagetype() {

		return message;
	}
		
	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}
}
