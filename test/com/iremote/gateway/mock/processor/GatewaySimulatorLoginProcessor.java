package com.iremote.gateway.mock.processor;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.security.SecureRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.md5.MD5Util;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;
import cn.com.isurpass.gateway.server.processor.gateway.AesGatewaySecurityKey;

public class GatewaySimulatorLoginProcessor implements IRemoteRequestProcessor
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(GatewaySimulatorLoginProcessor.class);
	private IGatewaySecurityKey skutil =  new AesGatewaySecurityKey();
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String t1 = TlvWrap.readString(request, TagDefine.TAG_TOKEN, 4);
		
		nbc.getAttachment().setToken(t1);
		
		int time1 = TlvWrap.readInt(request, TagDefine.TAG_TIME, 4);
		
		nbc.getAttachment().setTime1(time1);
		
		byte[] ek1 = TlvWrap.readTag(request,  TagDefine.TAG_KEY1, 4);
		
		byte[] k1 = getSecurityKey().decryptKey1(ek1, t1, time1);
		
		nbc.getAttachment().setKey1(k1);
		
		nbc.getAttachment().setKey3(getSecurityKey().createkye3(nbc.getDeviceid(), t1, GatewaySimulatorReportSecurityKeyProcessor.SECURITY_KEY, time1));
		
		SecureRandom sr = new SecureRandom();
		
		CommandTlv ct = new CommandTlv(100 ,2 );
		ct.addUnit(new TlvByteUnit(2 , nbc.getDeviceid().getBytes()));
		ct.addUnit(new TlvByteUnit(101 ,MD5Util.md5(arraycat(nbc.getDeviceid().getBytes(), GatewaySimulatorReportSecurityKeyProcessor.SECURITY_KEY, t1.getBytes()), 129)));
		ct.addUnit(new TlvIntUnit(105 , 60 , 4));
		ct.addUnit(new TlvIntUnit(25 , sr.nextInt() , 4));
		ct.addUnit(new TlvIntUnit(3 , 60 , 4));
		ct.addUnit(new TlvByteUnit(4 , "1.2.21a".getBytes()));
		ct.addUnit(new TlvIntUnit(25 , sr.nextInt() , 4));
		ct.addUnit(new TlvIntUnit(108 , 1 , 4));
		ct.addUnit(new TlvIntUnit(109 , 100 , 4));
		ct.addUnit(new TlvIntUnit(25 , sr.nextInt() , 4));
		
		CommandTlv ct2 = new CommandTlv(TagDefine.COMMAND_CLASS_ENCRYPT , TagDefine.COMMAND_SUB_CLASS_ENCRYPT_REQUEST);
		ct2.addUnit(new TlvByteUnit(getEncryptTag() , encrypt(nbc.getAttachment().getKey1() , ct.getByte())));
		
		return ct2;
	}
	
	protected int getEncryptTag() 
	{
		return TagDefine.TAG_AES_ENCRYPT;
	}

	protected byte[] encrypt(byte[] key, byte[] content) 
	{
		return AES.encrypt(key , content);
	}
	
	protected byte[] arraycat(byte[] a1 , byte[] a2 , byte[] a3)
	{
		if ( a1 == null || a2 == null || a3 == null )
			return null;
		byte[] r = new byte[a1.length + a2.length + a3.length];
		System.arraycopy(a1, 0, r, 0, a1.length);
		System.arraycopy(a2, 0, r, a1.length, a2.length);
		System.arraycopy(a3, 0, r, a1.length + a2.length, a3.length);
		return r ;
	}
	
	protected IGatewaySecurityKey getSecurityKey()
	{
		return skutil;
	}
}
