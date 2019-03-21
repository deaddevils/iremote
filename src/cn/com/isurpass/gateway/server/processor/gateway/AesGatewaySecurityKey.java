package cn.com.isurpass.gateway.server.processor.gateway;

import com.iremote.common.md5.MD5Util;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.processor.BaseGatewaySecurityKey;

public class AesGatewaySecurityKey extends BaseGatewaySecurityKey 
{
	@Override
	public byte[] encryptKey1(byte[] key1, String token1, int time1) 
	{
		byte[] key10 = createkey10(token1 , time1);
		return GatewayAES.encrypt(key10, key1);
	}

	@Override
	public byte[] decryptKey1(byte[] ekey1, String token1, int time1) 
	{
		byte[] key10 = createkey10(token1 , time1);
		return GatewayAES.decrypt(key10, ekey1);
	}

	@Override
	public byte[] encryptKey2(byte[] key2, String token2, int time2) 
	{
		byte[] key20 = createkey20(token2 , time2);
		return GatewayAES.encrypt(key20, key2);
	}

	@Override
	public byte[] decryptKey2(byte[] ekey2, String token2, int time2) 
	{
		byte[] key20 = createkey20(token2 , time2);
		return GatewayAES.decrypt(key20, ekey2);
	}

	@Override
	public byte[] createkye3(String deviceid, String token1, byte[] securitykey, int time1) 
	{
		int i = time1 % 10 ;
		byte[] mb = new byte[4];
		TlvWrap.writeInt(mb, 0 , time1, 4);
		
		byte[] db = deviceid.getBytes();
		byte[] tb = token1.getBytes();
		byte[] sb = securitykey;
		
		switch(i)
		{
		case 0:
			return MD5Util.md5(arraycat(db , sb , tb) , 86);
		case 1:
			return MD5Util.md5(arraycat(tb , db , sb) , 79);
		case 2:
			return MD5Util.md5(arraycat(mb , sb , tb) , 74);
		case 3:
			return MD5Util.md5(arraycat(tb , mb , sb) , 90);
		case 4:
			return MD5Util.md5(arraycat(sb , tb, db) , 112);
		case 5:
			return MD5Util.md5(arraycat(sb , tb , mb) , 66);
		case 6:
			return MD5Util.md5(arraycat(db , sb , mb) , 93);
		case 7:
			return MD5Util.md5(arraycat(mb , db , sb) , 102);
		case 8:
			return MD5Util.md5(arraycat(sb , mb , db) , 66);
		case 9:
			return MD5Util.md5(arraycat(db , sb , tb) , 88);
		}
		
		return null;
	}

}
