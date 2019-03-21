package cn.com.isurpass.gateway.server.processor.gateway;

import com.iremote.common.Utils;

public class AesGatewaySecurityKeyTest2 extends AesGatewaySecurityKey
{
	private static byte[] token1 = new byte[]{78 ,52, 80, 113 ,85, 67 ,66, 97, 108, 85, 88 ,78 ,57 ,76 ,105, 119 ,99 ,90 ,100 ,53 ,105 ,117 ,72 ,90, 99, 104, 70, 72, 57, 66, 98 ,57};
	private static int time1 = 1547478622;
	private static byte[] key1_en = new byte[]{67,(byte)222,31,106,(byte)182,(byte)218,(byte)246,(byte)246,(byte)227,(byte)174,(byte)176,78,26,(byte)162,(byte)140,(byte)235,111,20,65,110,66,(byte)145,14,(byte)134,100,(byte)152,(byte)235,114,69,29,(byte)165,86};
	
	
	public static void main(String arg[])
	{
		AesGatewaySecurityKeyTest2 t = new AesGatewaySecurityKeyTest2();
		t.test();
	}
	
	public void test()
	{
		byte[] key1 = this.decryptKey1(key1_en, new String(token1), time1);
		
		Utils.print("", key1);
	}
}
