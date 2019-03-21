package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;

public class RemotePowerTypeProcessor implements IRemoteRequestProcessor {
	
	private static CommandTlv RESULT_SUCCESS = new CommandTlv(103 , 4);
	static 
	{
		RESULT_SUCCESS.addUnit(new TlvIntUnit(1,0,2));
	}
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String deviceid = nbc.getDeviceid();
		
		int powertype =  TlvWrap.readInt(request, 63, 4);
		
		if ( powertype == IRemoteConstantDefine.REMOTE_POWER_TYPE_UNKNOW )
			return RESULT_SUCCESS;
		
		Integer battery = TlvWrap.readInteter(request, 64, 4);
		
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword(deviceid);
		
		Integer ot = remote.getPowertype();
		remote.setPowertype(powertype);
		if ( battery != null )
			remote.setBattery(battery);
		
		if (  ot != null && ot != powertype 
				&& remote.getRemotetype() == IRemoteConstantDefine.IREMOTE_TYPE_NORMAL )
			NotificationHelper.pushRemotePowertypeMessage(remote);
		
		return RESULT_SUCCESS;
	}

}
