package cn.com.isurpass.gateway.server.processor.gateway;

import com.iremote.common.Utils;

public class AesGatewaySecurityKeyTest extends AesGatewaySecurityKey
{
	private static byte[] key1 = new byte[]{23, 76, 16, 117, (byte)129, (byte)196, 89, 54, 27, (byte)214, (byte)168, 60, (byte)239, (byte)147, 114 ,16};
	private static byte[] key2 = new byte[]{67, 23, (byte)221 ,(byte)164 ,(byte)167 ,(byte)200, 37, (byte)232 ,19 ,47 ,93, 49 ,(byte)163, 29, (byte)224 ,61};
	private static String token1 = "cl4fPr80MKtUCeQduyG0pOv3Ce4UMH3j";
	private static String token2 = "iF0HzRfu7zNFKKZOmrl426OJ0Nj6QT4V";
	private int time1 = 1503732306;
	private int time2 = 1503735364;
	private static String deviceid = "iRemote2005000001339";
	private static byte[] securitykey = new byte[]{10, -113, 66, 101, -5, -78, -59, 36, 89, 9, -108, -88, 26, 38, -108, -94};
	
	
	public static void main(String arg[])
	{
		AesGatewaySecurityKeyTest t = new AesGatewaySecurityKeyTest();
		t.test();

	}
	
	public void test()
	{
		//Utils.print("k10" , this.createkey10("cl4fPr80MKtUCeQduyG0pOv3Ce4UMH3j", 1503732306));
		
		//Utils.print("" , this.encryptKey1(key1, token1, time1));
		Utils.print("" , this.encryptKey2(key2, token2, time2));
	}
	
	public static void main1(String arg[])
	{
		AesGatewaySecurityKey sk = new AesGatewaySecurityKey();
		String deviceid = "iRemote2005000001339";
		String token1 = "cl4fPr80MKtUCeQduyG0pOv3Ce4UMH3j";
		byte[] securitykey = new byte[]{10, -113, 66, 101, -5, -78, -59, 36, 89, 9, -108, -88, 26, 38, -108, -94};
		int time1 = 1503732300;
		for ( int i = 0 ; i < 10 ; i ++ )
		{
			int time = time1 + i ;
			System.out.print("time1:");
			System.out.println(time);
			byte[] ek1 = sk.createkye3(deviceid, token1, securitykey, time);
			Utils.print("", ek1);
		}
	}
}
