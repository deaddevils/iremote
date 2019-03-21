package com.iremote.thirdpart.cobbe.event;

import java.util.Random;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandParser;
import com.iremote.common.commandclass.CommandValue;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DoorlockOpenbyTempPassword extends ZWaveDeviceEvent implements ITextMessageProcessor
{	
	private final static Byte[] filter = new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1,null,null,null,null,0x15}; 
	@Override
	public void run()
	{	
		if ( !GatewayUtils.isCobbeLock(super.getDeviceid()) )
			return ;
		
		CommandValue commandvalue = CommandParser.parse(TlvWrap.readTag(super.getReport(), 70, 4));
		if ( commandvalue == null ||  !Utils.isByteMatch(filter , commandvalue.getCmd()))
			return ;
		
		int usertype = commandvalue.getCmd()[8] & 0xff ;
		if ( usertype != 0x15 ) //password
			return ;
		
		int usercode = commandvalue.getCmd()[9] & 0xff ;	
		if ( usercode == 0 )
			return ;
		
		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		
		DoorlockPassword dp = dpsvr.queryLatestActivePassword(super.getZwavedeviceid(), 1,usercode);
		if ( dp == null || dp.getPasswordtype() != IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP)
			return ;
		
		dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_USED);
		
		Random r = new Random(System.currentTimeMillis());
		DoorlockPassword doorlockpassword = DoorLockPasswordHelper.createLockTempPassword(super.getZwavedeviceid(), usercode, String.format("%06d", r.nextInt(1000000)));

		dpsvr.save(doorlockpassword);
		
		DoorLockPasswordHelper.BroadcaseSendPassowordEvent(super.getDeviceid());
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}
	


}
