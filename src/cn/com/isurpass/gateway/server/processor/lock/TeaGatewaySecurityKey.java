package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.common.encrypt.Tea;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.processor.BaseGatewaySecurityKey;

public class TeaGatewaySecurityKey extends BaseGatewaySecurityKey
{
	@Override
	public byte[] encryptKey1(byte[] key1, String token1, int time1) 
	{
		byte[] key10 = createkey10(token1 , time1);
		return Tea.encryptByTea(key1, key10);
	}

	@Override
	public byte[] decryptKey1(byte[] ekey1, String token1, int time1) 
	{
		byte[] key10 = createkey10(token1 , time1);
		return Tea.decryptByTea(ekey1, key10);
	}

	@Override
	public byte[] encryptKey2(byte[] key2, String token2, int time2) 
	{
		byte[] key20 = createkey20(token2 , time2);
		return Tea.encryptByTea(key2, key20);
	}

	@Override
	public byte[] decryptKey2(byte[] ekey2, String token2, int time2) 
	{
		byte[] key20 = createkey20(token2 , time2);
		return Tea.decryptByTea(ekey2, key20);
	}

	@Override
	public byte[] createkye3(String deviceid, String token1, byte[] securitykey, int time1) 
	{
		int i = time1 % 4 ;
		byte[] mb = new byte[4];
		TlvWrap.writeInt(mb, 0 , time1, 4);
		
		byte[] tb = token1.getBytes();
		byte[] sb = securitykey;

		switch(i)
		{
		case 0:
			return new byte[]{
					(byte)(tb[2] ^ mb[1] ^ sb[15] ^ 47),
					(byte)(tb[22] ^ mb[2] ^ sb[11] ^ 211),
					(byte)(tb[24] ^ mb[1] ^ sb[6] ^ 146),
					(byte)(tb[20] ^ mb[3] ^ sb[4] ^ 156),
					(byte)(tb[7] ^ mb[3] ^ sb[4] ^ 232),
					(byte)(tb[4] ^ mb[3] ^ sb[12] ^ 204),
					(byte)(tb[15] ^ mb[3] ^ sb[14] ^ 107),
					(byte)(tb[14] ^ mb[1] ^ sb[15] ^ 46),
					(byte)(tb[22] ^ mb[1] ^ sb[6] ^ 105),
					(byte)(tb[21] ^ mb[2] ^ sb[12] ^ 183),
					(byte)(tb[17] ^ mb[0] ^ sb[14] ^ 100),
					(byte)(tb[22] ^ mb[3] ^ sb[14] ^ 106),
					(byte)(tb[25] ^ mb[2] ^ sb[7] ^ 45),
					(byte)(tb[18] ^ mb[2] ^ sb[5] ^ 209),
					(byte)(tb[0] ^ mb[3] ^ sb[2] ^ 134),
					(byte)(tb[20] ^ mb[1] ^ sb[11] ^ 57),
			};
		case 1:
			return new byte[]{
					(byte)(tb[7] ^ mb[2] ^ sb[10] ^ 23),
					(byte)(tb[25] ^ mb[3] ^ sb[13] ^ 119),
					(byte)(tb[25] ^ mb[3] ^ sb[4] ^ 85),
					(byte)(tb[22] ^ mb[0] ^ sb[13] ^ 77),
					(byte)(tb[10] ^ mb[2] ^ sb[11] ^ 175),
					(byte)(tb[19] ^ mb[2] ^ sb[3] ^ 179),
					(byte)(tb[23] ^ mb[1] ^ sb[4] ^ 223),
					(byte)(tb[9] ^ mb[3] ^ sb[15] ^ 92),
					(byte)(tb[2] ^ mb[1] ^ sb[14] ^ 46),
					(byte)(tb[20] ^ mb[0] ^ sb[6] ^ 223),
					(byte)(tb[30] ^ mb[1] ^ sb[8] ^ 139),
					(byte)(tb[7] ^ mb[0] ^ sb[13] ^ 96),
					(byte)(tb[23] ^ mb[2] ^ sb[2] ^ 183),
					(byte)(tb[16] ^ mb[2] ^ sb[5] ^ 227),
					(byte)(tb[8] ^ mb[3] ^ sb[3] ^ 49),
					(byte)(tb[2] ^ mb[1] ^ sb[1] ^ 55),
			};
		case 2:
			return new byte[]{
					(byte)(tb[4] ^ mb[0] ^ sb[15] ^ 96),
					(byte)(tb[10] ^ mb[1] ^ sb[6] ^ 9),
					(byte)(tb[4] ^ mb[2] ^ sb[12] ^ 258),
					(byte)(tb[30] ^ mb[3] ^ sb[8] ^ 91),
					(byte)(tb[11] ^ mb[2] ^ sb[8] ^ 87),
					(byte)(tb[8] ^ mb[2] ^ sb[7] ^ 54),
					(byte)(tb[1] ^ mb[0] ^ sb[5] ^ 247),
					(byte)(tb[8] ^ mb[1] ^ sb[3] ^ 121),
					(byte)(tb[8] ^ mb[3] ^ sb[15] ^ 48),
					(byte)(tb[28] ^ mb[2] ^ sb[13] ^ 250),
					(byte)(tb[31] ^ mb[2] ^ sb[1] ^ 185),
					(byte)(tb[18] ^ mb[0] ^ sb[12] ^ 150),
					(byte)(tb[21] ^ mb[0] ^ sb[5] ^ 185),
					(byte)(tb[23] ^ mb[1] ^ sb[11] ^ 41),
					(byte)(tb[21] ^ mb[3] ^ sb[10] ^ 75),
					(byte)(tb[4] ^ mb[3] ^ sb[11] ^ 136)
			};
		case 3:
			return new byte[]{
					(byte)(tb[15] ^ mb[3] ^ sb[8] ^ 244),
					(byte)(tb[13] ^ mb[3] ^ sb[1] ^ 88),
					(byte)(tb[12] ^ mb[3] ^ sb[11] ^ 40),
					(byte)(tb[22] ^ mb[2] ^ sb[15] ^ 45),
					(byte)(tb[20] ^ mb[3] ^ sb[4] ^ 23),
					(byte)(tb[24] ^ mb[2] ^ sb[8] ^ 52),
					(byte)(tb[17] ^ mb[1] ^ sb[1] ^ 193),
					(byte)(tb[25] ^ mb[2] ^ sb[14] ^ 130),
					(byte)(tb[1] ^ mb[0] ^ sb[6] ^ 163),
					(byte)(tb[18] ^ mb[1] ^ sb[15] ^ 205),
					(byte)(tb[12] ^ mb[3] ^ sb[0] ^ 221),
					(byte)(tb[20] ^ mb[3] ^ sb[2] ^ 166),
					(byte)(tb[0] ^ mb[0] ^ sb[8] ^ 172),
					(byte)(tb[23] ^ mb[0] ^ sb[9] ^ 50),
					(byte)(tb[1] ^ mb[3] ^ sb[14] ^ 21),
					(byte)(tb[27] ^ mb[1] ^ sb[6] ^ 221),
			};
		}
		
		return null;
	}

}
