package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;

public class SetRemoteIpProcessor implements IRemoteRequestProcessor {

private static CommandTlv RESULT_SUCCESS = new CommandTlv(103 , 2);
	
	private static Log log = LogFactory.getLog(CommandTlv.class);
	
	static 
	{
		RESULT_SUCCESS.addUnit(new TlvIntUnit(1,0,2));
	}
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc)throws BufferOverflowException, IOException 
	{
		String uuid = nbc.getDeviceid();
				
		try
		{
			IremotepasswordService svr = new IremotepasswordService();
			Remote r = svr.getIremotepassword(uuid);
						
			if ( r != null )
			{
				String ip = TlvWrap.readString(request, 17, 4);
				if ( ip == null || ip.length() == 0 )
					ip = TlvWrap.readString(request, 14, 4);

				String oip = r.getIp();
				r.setIp(ip);
				
				String version =  TlvWrap.readString(request, 4, 4);
				if ( version == null || version.length() == 0 )
					version = r.getVersion();
				if ( version == null || version.length() == 0 )
					version = "1.0.9"; 
				r.setVersion(version);
				
				int iv = TlvWrap.readInt(request, 3, 4);
				if ( iv == Integer.MIN_VALUE )
					iv = r.getIversion();
				if ( iv == 0 )
					iv = 0x0009;
				r.setIversion(iv);
				
				int ntw = TlvWrap.readInt(request, 108, 4);
				if ( ntw != Integer.MIN_VALUE )
					r.setNetwork(ntw);
				
				int ntwi = TlvWrap.readInt(request, 109, 4);
				if ( ntwi != Integer.MIN_VALUE )
					r.setNetworkintensity(ntwi);
				
				
				if ( (oip == null || !oip.equals(r.getIp()))
						&& r.getPhoneuserid() != null 
						&& !GatewayUtils.isCobbeLock(r))
					sendinfochangemessage(r.getPhoneuserid() , uuid);
			}
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
			return null ;
		}
		
		return RESULT_SUCCESS;
	}

	private void sendinfochangemessage(int owner , String deviceid)
	{
		PhoneUserService pus = new PhoneUserService();
		PhoneUserHelper.sendInfoChangeMessage(pus.query(owner));
	}
}
