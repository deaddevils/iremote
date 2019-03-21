package com.iremote.gateway.mock.processor;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.security.SecureRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.processor.IGatewaySecurityKey;
import cn.com.isurpass.gateway.server.processor.gateway.AesGatewaySecurityKey;

public class GatewaySimulatorReportSecurityKeyProcessor implements IRemoteRequestProcessor
{
	private static Log log = LogFactory.getLog(GatewaySimulatorReportSecurityKeyProcessor.class);
	
	public static final byte[] SECURITY_KEY = new byte[]{10, -113, 66, 101, -5, -78, -59, 36, 89, 9, -108, -88, 26, 38, -108, -94};
	private IGatewaySecurityKey skutil =  new AesGatewaySecurityKey();
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String t2 = TlvWrap.readString(request, TagDefine.TAG_TOKEN, 4);
		
		nbc.getAttachment().setToken2(t2);;
		
		int time2 = TlvWrap.readInt(request, TagDefine.TAG_TIME, 4);
		
		nbc.getAttachment().setTime1(time2);
		
		byte[] ek2 = TlvWrap.readTag(request,  TagDefine.TAG_KEY2, 4);
		
		byte[] k2 = getSecurityKey().decryptKey2(ek2, t2, time2);
		
		nbc.getAttachment().setKey2(k2);

		SecureRandom sr = new SecureRandom();
		
		CommandTlv ct = new CommandTlv(106 ,4 );
		ct.addUnit(new TlvIntUnit(25 , sr.nextInt() , 4));
		ct.addUnit(new TlvByteUnit(32 , SECURITY_KEY));
		ct.addUnit(new TlvIntUnit(25 , sr.nextInt() , 4));
		ct.addUnit(new TlvIntUnit(25 , sr.nextInt() , 4));
				
		CommandTlv ct2 = new CommandTlv(TagDefine.COMMAND_CLASS_ENCRYPT , TagDefine.COMMAND_SUB_CLASS_ENCRYPT_REQUEST);
		ct2.addUnit(new TlvByteUnit(getEncryptTag() , encrypt(nbc.getAttachment().getKey2() , ct.getByte())));
		
		
		log.info("token2:" + t2);
		log.info(String.format("time2:%d" , time2));
		
		Utils.print("key2:", k2);
		Utils.print("ekey2:", ek2);
		
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
	
	protected IGatewaySecurityKey getSecurityKey()
	{
		return skutil;
	}
}
