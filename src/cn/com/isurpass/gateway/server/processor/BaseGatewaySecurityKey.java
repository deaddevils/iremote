package cn.com.isurpass.gateway.server.processor;

import com.iremote.infraredtrans.tlv.TlvWrap;

public abstract class BaseGatewaySecurityKey implements IGatewaySecurityKey
{
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
	
	protected byte[] createkey10(String token1 , int time1)
	{
		byte[] kb = token1.getBytes();
		byte[] tb = new byte[4] ;
		TlvWrap.writeInt(tb, 0, time1, 4);
		
		byte[] key10 = new byte[]{
				(byte)(kb[3] ^ tb[2] ^ 140),
				(byte)(kb[24] ^ tb[0] ^ 133),
				(byte)(kb[15] ^ tb[1] ^ 60),
				(byte)(kb[17] ^ tb[3] ^ 62),
				(byte)(kb[22] ^ tb[2] ^ 175),
				(byte)(kb[22] ^ tb[1] ^ 35),
				(byte)(kb[5] ^ tb[2] ^ 248),
				(byte)(kb[10] ^ tb[3] ^ 46),
				(byte)(kb[13] ^ tb[1] ^ 87),
				(byte)(kb[8] ^ tb[2] ^ 89),
				(byte)(kb[5] ^ tb[3] ^ 105),
				(byte)(kb[17] ^ tb[3] ^ 52),
				(byte)(kb[26] ^ tb[1] ^ 206),
				(byte)(kb[12] ^ tb[2] ^ 112),
				(byte)(kb[23] ^ tb[1] ^ 46),
				(byte)(kb[1] ^ tb[1] ^ 218)
		};
		return key10;
	}
	
	protected byte[] createkey20(String token2 , int time2)
	{
		byte[] kb = token2.getBytes();
		byte[] tb = new byte[4] ;
		TlvWrap.writeInt(tb, 0, time2, 4);
		
		byte[] key20 = new byte[]{
				(byte)(kb[9] ^ tb[1] ^ 137),
				(byte)(kb[19] ^ tb[2] ^ 200),
				(byte)(kb[10] ^ tb[2] ^ 74),
				(byte)(kb[25] ^ tb[0] ^ 212),
				(byte)(kb[17] ^ tb[1] ^ 219),
				(byte)(kb[11] ^ tb[0] ^ 11),
				(byte)(kb[25] ^ tb[2] ^ 87),
				(byte)(kb[2] ^ tb[0] ^ 178),
				(byte)(kb[5] ^ tb[0] ^ 23),
				(byte)(kb[12] ^ tb[2] ^ 4),
				(byte)(kb[10] ^ tb[0] ^ 99),
				(byte)(kb[19] ^ tb[3] ^ 12),
				(byte)(kb[19] ^ tb[0] ^ 2),
				(byte)(kb[12] ^ tb[3] ^ 59),
				(byte)(kb[4] ^ tb[1] ^ 70),
				(byte)(kb[8] ^ tb[2] ^ 9),
		};
		return key20;
	}
}
