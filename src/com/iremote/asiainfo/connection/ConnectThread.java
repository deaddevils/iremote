package com.iremote.asiainfo.connection;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.java_websocket.drafts.Draft_17;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.vo.AuthRequest;
import com.iremote.asiainfo.vo.BusinessData;
import com.iremote.common.IRemoteConstantDefine;

public class ConnectThread implements Runnable {

	private WebSocketClientConnection conn;
		
	private IOException exception ;
	
	public void launchWebsocketServer() throws IOException
	{
		if ( conn != null && conn.isOpen() )
			conn.close();
		try {
			getAccessToken();
			conn = new WebSocketClientConnection(new URI(IRemoteConstantDefine.ASIA_INFO_WEBSOCKET_URL)  ,new Draft_17() );
			conn.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAccessToken()
	{
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken("");
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		AuthRequest ar = new AuthRequest();
		ar.setApplyTime(sf.format(new Date()));
		ar.setPartyCode(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		ar.setSecret("123456");
		
		data.setMessage(ar);
		
		String str = AsiainfoHttpHelper.httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL  + "partner/partnerGetAccessToken" ,data);
		JSONObject json = (JSONObject)JSON.parse(str);
		if ( json.containsKey("message") )
		{
			json = (JSONObject)JSON.parse(json.getString("message"));
			if ( json.containsKey("accessToken"))
			{
				WebSocketClientConnection.setAccessToken(json.getString("accessToken"));
				int et = json.getIntValue("expTime");
				long ett = System.currentTimeMillis() + ( et - 600 ) * 1000;
				WebSocketClientConnection.setExpiretime(ett);
			}
		}
	}
	
	@Override
	public void run() {
		
		try
		{
			launchWebsocketServer();
		}
		catch(IOException e)
		{
			exception = e;
		}
	}

	public WebSocketClientConnection getConn() {
		return conn;
	}

	public void setConn(WebSocketClientConnection conn) {
		this.conn = conn;
	}

	public IOException getException() {
		return exception;
	}
	
	
}
