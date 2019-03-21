package com.iremote.infraredtrans.zwavecommand.doorlock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.domain.Card;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;
import com.iremote.service.CardService;
import com.iremote.service.DoorlockUserService;

public class DoorlockSelfDefineOpenwithCardReportProcessor extends DoorlockStatusReportBase
{
	private static Log log = LogFactory.getLog(DoorlockSelfDefineOpenwithCardReportProcessor.class);
	
	protected int usercode;
	protected String username ;

	@Override
	protected String getOperateorName()
	{
		return username;
	}

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

	protected void initusercode()
	{
		byte[] uc = new byte[8];
		System.arraycopy(zrb.getCmd(), 9, uc, 0, uc.length);
		String suc = JWStringUtils.toHexString(uc).toUpperCase();
		log.info(suc);
		
		String sha1key = DigestUtils.sha1Hex(suc);
		
		CardService cs = new CardService();
		Card c = cs.queryCardbykey(sha1key, Utils.getRemotePlatform(zrb.getDeviceid()));
		
		if ( c == null )
			return ;
		
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.querybyCardid(zrb.getDevice().getZwavedeviceid(), c.getCardid());
		if ( du == null )
			return ;
		
		username = du.getUsername();
		usercode = du.getUsercode();

	}
	
	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
	}

	@Override
	protected JSONObject getAppendMessage()
	{
		if ( usercode == 0 )
			return super.getAppendMessage();
		JSONObject json = new JSONObject();
		json.put("usercode", usercode);
		json.put("username", username);
		json.put("usertype", IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD_0X20);
		return json ;
	}
	
	@Override
	protected IZwaveReportCache createCacheReport()
	{
		return new ZwaveReportCache(zrb.getCommandvalue() , IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS , IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
	}
}
