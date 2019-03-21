package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;

public class GatewayInfoProcesssor implements IRemoteRequestProcessor
{
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		String ssid = TlvWrap.readString(request, 13, 4);
		
		if ( StringUtils.isBlank(ssid) )
			return null;
		
		String deviceid = nbc.getDeviceid();
		if ( deviceid == null )
			return null ;
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if (r == null )
			return null ;
		
		if ( ssid.equals(r.getSsid()))
			return null ;
		
		r.setSsid(ssid);
		
		if ( r.getPhoneuserid() != null )
		{
			PhoneUserService pus = new PhoneUserService();
			PhoneUserHelper.sendInfoChangeMessage(pus.query(r.getPhoneuserid()));
		}
		
		return null;
	}

}
