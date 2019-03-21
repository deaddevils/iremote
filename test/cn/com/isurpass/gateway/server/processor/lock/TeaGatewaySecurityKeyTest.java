package cn.com.isurpass.gateway.server.processor.lock;

import com.iremote.common.Utils;
import com.iremote.common.encrypt.Tea;

public class TeaGatewaySecurityKeyTest extends TeaGatewaySecurityKey
{
	
	public static void main(String arg[])
	{
		TeaGatewaySecurityKeyTest t = new TeaGatewaySecurityKeyTest();
		t.test();

	}
	
	public void test()
	{
		Utils.print("k10" , this.createkey10("cl4fPr80MKtUCeQduyG0pOv3Ce4UMH3j", 1503732306));
	}
	
	public static void main1(String arg[])
	{
//		Tea t = new Tea();
//		byte[] te1 = t.encryptByTea(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}, "fksafdsafjkdafdafdaf1312421421fvzxvwf".getBytes());
//		
//		Utils.print("", te1);
//		
//		byte[] re1 = t.decryptByTea(te1, "fksafdsafjkdafdafdaf1312421421fvzxvwf".getBytes());
//		
//		Utils.print("", re1);
		
//		TeaGatewaySecurityKey sk = new TeaGatewaySecurityKey();
//		byte[] ek1 = sk.encryptKey1(new byte[]{1,2,3,4,5,6,7,8,(byte)219,10,11,12,(byte)213,14,15,16,(byte)217,18,19,20}, "fksafdsafjkdafdafdaf1312421421fvzxvwf", 32);
//		
//		Utils.print("", ek1);
//		
//		byte[] r = sk.decryptKey1(ek1, "fksafdsafjkdafdafdaf1312421421fvzxvwf", 32);
//		
//		Utils.print("", r);
		
		
		TeaGatewaySecurityKey sk = new TeaGatewaySecurityKey();
		
		String deviceid = "iRemote2005000001339";
		String token1 = "cl4fPr80MKtUCeQduyG0pOv3Ce4UMH3j";
		byte[] securitykey = new byte[]{10, -113, 66, 101, -5, -78, -59, 36, 89, 9, -108, -88, 26, 38, -108, -94};
		int time1 = 1503732300;
		for ( int i = 0 ; i < 4 ; i ++ )
		{
			int time = time1 + i ;
			System.out.println(time);
			byte[] ek1 = sk.createkye3(deviceid, token1, securitykey, time);
			Utils.print("", ek1);
		}
		
		
	}
}
