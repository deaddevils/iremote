package cn.com.isurpass.gateway.server.processor;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.common.TagDefine;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.RemoteService;

public abstract class BaseSecurityKeyProcessor implements IRemoteRequestProcessor 
{

	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		byte[] sk = TlvWrap.readTag(request, TagDefine.TAG_SECURITY_KEY, 4);
		
		if ( sk == null )
			return null ;
		
		String deviceid = nbc.getDeviceid();
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
			return null;
		
		r.setSecritykey(sk);
		
		nbc.getAttachment().setKey3(getSecurityKey().createkye3(deviceid, nbc.getAttachment().getToken(), sk, nbc.getAttachment().getTime1()));
		
		return null;
	}

	protected abstract IGatewaySecurityKey getSecurityKey();
}
