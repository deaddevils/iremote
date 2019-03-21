package com.iremote.asiainfo.connection;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.CommonResponse;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.thread.KeyCrashException;
import com.iremote.common.thread.SynchronizeRequestManager;

public class HeartBeatHandler implements Runnable {
	
	private static Log log = LogFactory.getLog(HeartBeatHandler.class);
	
	private static int failedtimes = 0 ;
	
	private static void reconnect() throws IOException, TimeoutException
	{
		log.info("connect");
		WebSocketWrap.launchWebsocketServer();
	}
	
	private static void heartbeat()  throws IOException, TimeoutException
	{
		try 
		{
			AsiainfoMessage msg = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_HEARTBEAT);
			
			JSONObject json = new JSONObject();
			//json.put("accessToken", WebSocketClientConnection.getAccessToken());
			msg.setMessageinfo(json.toJSONString());
			
			SynchronizeRequestManager.regist(msg.getSequence(), "Asininfo_request");
			WebSocketWrap.writeMessage(msg);
			CommonResponse response = (CommonResponse) SynchronizeRequestManager.getResponse(msg.getSequence(), "Asininfo_request");
			if ( response == null )
				increaseFailedtimes();
			else if ( response.getReplyResult() != 0 )
				reconnect();
			else if ( WebSocketClientConnection.getExpiretime() < System.currentTimeMillis() )
				ConnectThread.getAccessToken();
		} 
		catch (IOException e) 
		{
			increaseFailedtimes();
		}
		catch (TimeoutException e) 
		{
			increaseFailedtimes();
		} catch (KeyCrashException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}


	private static void increaseFailedtimes() throws IOException, TimeoutException
	{
		failedtimes ++ ;
		if ( failedtimes >= 3 )
		{
			failedtimes = 0 ;
			reconnect();
		}
	}

	@Override
	public void run() 
	{
		for ( ; ; )
		{
			try
			{
				if ( WebSocketWrap.isOpen() )
				{
					heartbeat();
				}
				else 
				{
					reconnect();
				}
			}
			catch ( SocketTimeoutException e)
			{
				log.error(e.toString());
			}
			catch (Throwable t) 
			{
				log.error("", t);
			}
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				log.error("" , e);
				return ;
			}
		}
	}
}
