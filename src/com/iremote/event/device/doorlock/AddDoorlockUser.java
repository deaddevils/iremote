package com.iremote.event.device.doorlock;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandParser;
import com.iremote.common.commandclass.CommandValue;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DoorlockUserService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

@Deprecated
public class AddDoorlockUser extends ZWaveDeviceEvent implements ITextMessageProcessor
{
	private final static Byte[] filter = new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null}; 
	@Override
	public void run()
	{	
		if ( GatewayUtils.isCobbeLock(super.getDeviceid())  )
			return ;
		
		CommandValue commandvalue = CommandParser.parse(TlvWrap.readTag(super.getReport(), 70, 4));
		if ( commandvalue == null ||  !Utils.isByteMatch(filter , commandvalue.getCmd()))
			return ;
		
		int usertype = commandvalue.getCmd()[8] & 0xff ;
		int usercode = commandvalue.getCmd()[9] & 0xff ;
		if ( usercode == 0 )
			return ;
		
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(super.getZwavedeviceid(), IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD, usercode);
		
		if ( du != null )
			return ;
		
		if ( usertype == 0x15 ) //password
		{
			DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		
			DoorlockPassword dp = dpsvr.queryLatestActivePassword(super.getZwavedeviceid(),1, usercode);
			
			if ( dp != null && dp.getPasswordtype() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP )
				return ;
		}
		
		du = new DoorlockUser();
		du.setUsercode(usercode);
		du.setUsertype(usertype);
		du.setZwavedeviceid(super.getZwavedeviceid());
		du.setUsername(getDefaultUsername(usertype , usercode));
		
		dus.save(du);
		
	}
	
	private String getDefaultUsername(int usertype , int usercode)
	{
		String mk = null ;
		switch( usertype )
		{
		case 0x00: 
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_KEY_USER ;
			break;
		case 0x15 :
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER;
			break;
		case 0x16 :
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FINGER_USER;
			break;
		case 0x20:
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER;
			break;
		default :
			return null;
		}
		return String.format("%s%d",MessageManager.getmessage(mk,0, IRemoteConstantDefine.DEFAULT_LANGUAGE) , usercode);
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}
}
