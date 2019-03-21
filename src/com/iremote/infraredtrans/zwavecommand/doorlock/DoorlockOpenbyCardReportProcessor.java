package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.Card;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;
import com.iremote.service.CardService;
import com.iremote.service.DoorlockUserService;

public class DoorlockOpenbyCardReportProcessor extends DoorlockStatusReportBase
{
	private String operatorname ;
	
	@Override
	protected String getOperateorName()
	{
		return operatorname;
	}

	@Override
	protected void updateDeviceStatus()
	{
		if ( zrb.getCmd()[14] != 0 )
		{
			this.dontpusmessage();
			this.dontsavenotification();
			return ;
		}
		
		zrb.getDevice().setShadowstatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		zrb.getDevice().setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN);
		
		int usercode = (zrb.getCmd()[3] & 0xff) * 256 + (zrb.getCmd()[4] & 0xff);
		
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(zrb.getDevice().getZwavedeviceid(), IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD, usercode);
		if ( du != null )
			operatorname = du.getUsername();
		else 
			operatorname = String.format("%s%d", MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser())),usercode);

		checkSequence(du.getCardid());
	}
	
	private void checkSequence(int cardid)
	{
		if ( cardid == 0 || zrb.getCmd().length < 25 )
			return ;
	
		int sequence = Utils.readint(zrb.getCmd() , 21 , 25);
		if ( sequence == 0 )
			return;
		
		CardService cs = new CardService();
		Card c = cs.query(cardid);
		
		if ( c.getCardsequence() != 0 && c.getCardsequence() >= sequence )
		{
			JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DOOR_CONTROL_CLONE_CARD, c);
		}
		else 
			c.setCardsequence(sequence);
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
