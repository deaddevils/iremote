package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;
import java.util.List;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DeviceResetProcessor implements IRemoteRequestProcessor
{
	private static final CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_GATEWAY , TagDefine.COMMAND_SUB_CLASS_GATEWAY_RESET_RESPONSE);
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		String deviceid = nbc.getDeviceid();
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);

		//WIFI lock reset , clear user , password , room appliance, do not clear owner and associations  
		if ( GatewayUtils.isCobbeLock(r) )
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.querybydeviceid(deviceid, IRemoteConstantDefine.DEVICE_NUID_WIFI_LOCK);
			
			if ( zd == null )
				return ct;
			
			clearPassword(zd);
			clearDoorUser(zd);
			
			if ( r.getPhoneuserid() != null )
			{
				RemoteOwnerChangeEvent re = new RemoteOwnerChangeEvent(deviceid , new Date() , r.getPhoneuserid(),0 , r.getPhonenumber() , null , System.currentTimeMillis());
			
				JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET, re);
				
				ZWaveDeviceEvent zde = new ZWaveDeviceEvent(); 
				zde.setZwavedeviceid(zd.getZwavedeviceid());
				zde.setDeviceid(zd.getDeviceid());
				zde.setEventtype(IRemoteConstantDefine.MESSAGE_TYPE_LOCK_RESET);
				zde.setEventtime(new Date());
				zde.setName(zd.getName());
				zde.setNuid(zd.getNuid());
				zde.setPhoneuserid(r.getPhoneuserid());
				zde.setTaskIndentify(re.getTaskIndentify());
				
				JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_TYPE_LOCK_RESET, zde);
			}
		}
		
		
		return ct;
	}
	
	private void clearPassword(ZWaveDevice zd )
	{
		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		List<DoorlockPassword> elst = dpsvr.queryByZwavedeviceid(zd.getZwavedeviceid());
		
		for ( DoorlockPassword dp : elst )
			dpsvr.delete(dp);
	}
	
	private void clearDoorUser(ZWaveDevice zd)
	{
		DoorlockUserService dus = new DoorlockUserService();
		List<DoorlockUser> lst = dus.querybyZwavedeviceid(zd.getZwavedeviceid());
		for ( DoorlockUser du : lst )
			dus.delete(du);

	}
}
