package com.iremote.asiainfo.connection;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSONObject;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.helper.ByteWrap;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class WebSocketClientConnection extends WebSocketClient
{
	private static Log log = LogFactory.getLog(WebSocketClientConnection.class);
	private static ExecutorService executor = Executors.newCachedThreadPool();
	
	private static String accessToken;
	private static long expiretime ;
	
	public WebSocketClientConnection(URI serverURI) {
		super(serverURI);
	}

	public WebSocketClientConnection(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) 
	{
		try {
			login();
		} catch (TimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void onMessage(String message) 
	{
		log.info("Recevie message");
		log.info(message);
	}
	
	@Override
	public void onMessage(ByteBuffer bytes) 
	{
		byte[] b = bytes.array();
		//Utils.print("Recevie message from asia_info" , b , b.length);
		
		byte[][] req = Utils.splitRequest(b, b.length);
		
		for ( int i = 0 ; i < req.length ; i ++ )
		{
			b = req[i];
			//Utils.print(b , b.length);
			
			ByteWrap bw = new ByteWrap(b);
			AsiainfoMessage message = AsiainfoMessageHelper.decodeMessage(bw);
			//System.out.println(JSON.toJSON(message));
			
			//log.info(message.getMessageid());
			
			executor.execute(new MessageProcessThread(message));
		}
	}
	


	@Override
	public void onClose(int code, String reason, boolean remote) 
	{
		
	}

	@Override
	public void onError(Exception ex) 
	{
		log.error(ex.getMessage(), ex);
	}

	private void login() throws TimeoutException, IOException
	{
		AsiainfoMessage msg = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_AUTH);
		JSONObject json = new JSONObject();
		json.put("accessToken", WebSocketClientConnection.accessToken);
		msg.setMessageinfo(json.toJSONString());
		WebSocketWrap.writeMessage(msg);
	}

	public static void setAccessToken(String accessToken) {
		WebSocketClientConnection.accessToken = accessToken;
	}

	public static String getAccessToken() {
		return accessToken;
	}

	public static long getExpiretime() {
		return expiretime;
	}

	public static void setExpiretime(long expiretime) {
		WebSocketClientConnection.expiretime = expiretime;
	}
	
	
}
