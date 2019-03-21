package cn.com.isurpass.nbiot.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.JWStringUtils;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.common.http.AsyncHttpResponse;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

import cn.com.isurpass.nbiot.request.NbiotAccessTokenManager;
import cn.com.isurpass.nbiot.request.NbiotHttpBuilder;

public class NoiotConnectionWrap implements IConnectionContext
{
	private static Log log = LogFactory.getLog(NoiotConnectionWrap.class);
	
	protected static int DEFAULT_TIME_OUT = 30 ;
	protected Remoter remote ;
	protected boolean open = true ;
	protected long timeouttime = System.currentTimeMillis() + DEFAULT_TIME_OUT * 1000 ;
	protected String nbiotdeviceid ;
	
	public NoiotConnectionWrap(String nbiotdeviceid)
	{
		super();
		this.nbiotdeviceid = nbiotdeviceid;
	}

	@Override
	public void setAttachment(Remoter remote)
	{
		this.remote = remote;
	}
	
	public void refresh()
	{
		timeouttime = System.currentTimeMillis() + DEFAULT_TIME_OUT * 1000 ;
	}

	@Override
	public Remoter getAttachment()
	{
		return remote;
	}

	@Override
	public String getDeviceid()
	{
		return remote.getUuid();
	}

	@Override
	public String getRemoteAddress()
	{
		return "nbiot";
	}

	@Override
	public void setIdleTimeoutMillis(int timeoutmillis)
	{
		
	}

	@Override
	public long getIdleTimeoutMillis()
	{
		return DEFAULT_TIME_OUT * 1000;
	}

	@Override
	public boolean isTimeout()
	{
		return this.timeouttime < System.currentTimeMillis();
	}

	@Override
	public boolean isOpen()
	{
		return open;
	}

	@Override
	public void close() throws IOException
	{
		open = false ;
		this.timeouttime = System.currentTimeMillis() - 1 ; 
	}

	@Override
	public void sendData(CommandTlv ct)
	{
		if ( open == false )
			return ;
		JSONObject json = new JSONObject();
		json.put("deviceId", this.nbiotdeviceid);
		json.put("expireTime", 0);
		json.put("command", new JSONObject());
		json.getJSONObject("command").put("serviceId", "Connectivity");
		json.getJSONObject("command").put("method", "SET_CONNECTIVITY_STATE");
		json.getJSONObject("command").put("paras", new JSONObject());
		
		byte[] b = ct.getByte();
		
		if ( log.isInfoEnabled())
			Utils.print(String.format("Send data to %s ", this.getDeviceid()) , b);
		
		int c = 0 ;
		for ( int i = 0 ; i < b.length - 1 ; i ++ )
			c =  ( c ^ b[i]);
		b[b.length -1] = (byte)c;
		
		json.getJSONObject("command").getJSONObject("paras").put("rawData", JWStringUtils.toHexString(b));
		
		NbiotHttpBuilder hb = new NbiotHttpBuilder();
		hb.setUrl("iocm/app/cmd/v1.4.0/deviceCommands?appId=" + ServerRuntime.getInstance().getCtccNBiotAppId());
		hb.appendHearder("app_key", ServerRuntime.getInstance().getCtccNBiotAppId());
		hb.appendHearder("Authorization", NbiotAccessTokenManager.getInstance().getAuthorization());
		String rs = json.toJSONString();
		log.info(rs);
		
		hb.postJson(rs , new AsyncHttpResponse());
	}

	@Override
	public void flush()
	{
		
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getConnectionHashCode() 
	{
		return 0;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if ( !this.getClass().isInstance(obj))
			return false ;
		
		NoiotConnectionWrap other = (NoiotConnectionWrap)obj;
		
		if ( StringUtils.isBlank(this.nbiotdeviceid))
			return false ;
		return this.nbiotdeviceid.equals(other.nbiotdeviceid);
	}

}
