package cn.com.isurpass.gateway.server.processor;

public interface IGatewaySecurityKey 
{
	byte[] encryptKey1(byte[] key1 , String token1 , int time1 );
	byte[] decryptKey1(byte[] ekey1 , String token1 , int time1 );
	byte[] encryptKey2(byte[] key2 , String token2 , int time2 );
	byte[] decryptKey2(byte[] ekey2 , String token2 , int time2 );
	byte[] createkye3(String deviceid , String token1 , byte[] securitykey , int time1 );
}
