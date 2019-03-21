package com.iremote.gateway.mock;

import cn.com.isurpass.netty.NettyClient;

public class LockSimulator 
{
	public static void main(String arg[])
	{
		NettyClient nc = new NettyClient("127.0.0.1" , 8923 , new LockSimulatorHandlerInitializeer());
		nc.start();
		
	}
}
