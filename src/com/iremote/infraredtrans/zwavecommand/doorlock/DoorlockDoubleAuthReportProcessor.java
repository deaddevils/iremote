package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;
import com.iremote.service.DoorlockUserService;

public class DoorlockDoubleAuthReportProcessor extends DoorlockStatusReportBase
{
	protected String username ;
	protected int[] usercode = new int[8];
	protected int[] usertype = new int[8];
	protected String message = IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
	
	@Override
	protected void updateDeviceStatus()
	{
		initusername();
		zrb.getDevice().setShadowstatus(IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD);
		if ( !DoorlockReportHelper.isDoorlockWarning(zrb.getDevice()) )
			zrb.getDevice().setStatus(IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD);
		if (!zdah.hasArmedByUserSetting())
			return;
		if (!zdah.hasSetDelayAlarm())
			super.appendWarningstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		else
			createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb,
					IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}

	protected void initusername()
	{
		initusercode();
		String name1 = getDoorlockUserName(usercode[0], usertype[0], zrb.getDevice());
		String name2 = getDoorlockUserName(usercode[1], usertype[1], zrb.getDevice());
		
		username = name1 + "," + name2;
	}
	
	public String getDoorlockUserName(int usercode,int usertype,ZWaveDevice zwaveDevice){
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(zwaveDevice.getZwavedeviceid(), usertype, usercode);
		if(du == null){
			du = new DoorlockUser();
			du.setZwavedeviceid(zwaveDevice.getZwavedeviceid());
			du.setUsercode(usercode);
			du.setUsertype(usertype);
			String mk = null ;
			if(usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD)
				mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER;
			else if(usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT)
				mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FINGER_USER;
			else if(usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD)
				mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER;
			
			String username = String.format("%s%d",MessageManager.getmessage(mk,0, getLanguange()) , usercode);
			du.setUsername(username);
			du.setCardid(0);
			du.setAlarmtype(IRemoteConstantDefine.DOORLOCK_USER_TYPE_ORDINARY);
			dus.save(du);
		}
		return du.getUsername();
	}
	
	private void initusercode()
	{
		int ut = zrb.getCmd()[8] & 0xff ;
		usercode[0] = zrb.getCmd()[9] & 0xff ;
		usercode[1] = zrb.getCmd()[10] & 0xff ;
		
		if ( ut == 0x21){
			usertype[0] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD ;
			usertype[1] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT ;
		}else if ( ut == 0x22){
			usertype[0] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD ;
			usertype[1] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD ;
		}else if ( ut == 0x23){
			usertype[0] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT ;
			usertype[1] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD ;
		}else if ( ut == 0x24){
			usertype[0] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD ;
			usertype[1] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD ;
		}else if ( ut == 0x25){
			usertype[0] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT ;
			usertype[1] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT ;
		}else if ( ut == 0x26){
			usertype[0] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD ;
			usertype[1] = IRemoteConstantDefine.DOOR_LOCK_USERTYPE_CARD ;
		}

	}
	
	protected String getLanguange()
	{
		return PhoneUserHelper.getLanguange(zrb.getPhoneuser());
	}

	@Override
	protected String getOperateorName()
	{
		return username;
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}
}
