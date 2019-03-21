package com.iremote.thirdpart.wcj.connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.http.HttpUtil;
import com.iremote.thirdpart.wcj.domain.WcjServer;

public class Connection {

	private static Log log = LogFactory.getLog(Connection.class);
	
	private static String loginuri = "/vkyapi_auth.php";
	private String token ;
	
	public void sendreport(WcjServer server , String uri ,  JSONObject report)
	{
		if ( token == null || token.length() == 0 )
			login(server);
		if ( token == null || token.length() == 0 )
			return ;
		String url = server.getServerurl() + uri;
		
		report.put("token", token);
		int rc = sendreport(url , report);
		if ( rc == 30003 || rc == 30004 || rc == 30005 )
		{
			login(server);
			report.put("token", token);
			rc = sendreport(url ,report);
		}
		if ( rc != ErrorCodeDefine.SUCCESS )
			log.error(String.format("Report failed , thirdpartid = %d , url = %s , name = %s , report = %s ", server.getThirdpartid() , url , server.getLoginname() , report.toJSONString()));
	}
	
	private int sendreport(String url , JSONObject report)
	{
		String rst = HttpUtil.httprequest(url, report);
		if ( rst == null || rst.length() == 0 )
			return ErrorCodeDefine.UNKNOW_ERROR;

		JSONObject rj = JSON.parseObject(rst);
		if ( !rj.containsKey("resultCode") )
			return ErrorCodeDefine.UNKNOW_ERROR;
		return rj.getIntValue("resultCode") ;
	}
	
	private void login(WcjServer server)
	{
		token = null; 
		String url = server.getServerurl() + loginuri;
		JSONObject p = new JSONObject();
		p.put("code", server.getLoginname());
		p.put("password", server.getPassword());
		
		String rst = HttpUtil.httprequest(url, p);
		
		if ( rst == null || rst.length() == 0 )
		{
			log.error(String.format("login failed , thirdpartid = %d , url = %s , name = %s " , server.getThirdpartid() , url , server.getLoginname()));
			return ;
		}
		JSONObject rj = JSON.parseObject(rst);
		if ( !rj.containsKey("resultCode") || rj.getIntValue("resultCode") != ErrorCodeDefine.SUCCESS || !rj.containsKey("token") )
		{
			log.error(String.format("login failed , thirdpartid = %d , url = %s , name = %s " , server.getThirdpartid() , url , server.getLoginname()));
			return ;
		}
		token = rj.getString("token");
	}
}
