package cn.com.isurpass.nbiot.request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.common.http.HttpBuilder;

public class NbiotAccessTokenManager implements Runnable 
{
	private static Log log = LogFactory.getLog(NbiotAccessTokenManager.class);
	private static NbiotAccessTokenManager instance = new NbiotAccessTokenManager();

	private String accessToken ;
	private String mStrRefreshToken;

	private String authorization;
	private long expiretime = 0 ;
	private long refreshtime = 0;
	
	public static NbiotAccessTokenManager getInstance()
	{
		return instance ;
	}

	private NbiotAccessTokenManager()
	{
		
	}
	
	public synchronized String getAuthorization()
	{
		return authorization;
	}
	
	private synchronized void login()
	{

		HttpBuilder hb = new HttpBuilder();
		String str = hb.setUrl(NbiotHttpBuilder.BASE_URL + "iocm/app/sec/v1.1.0/login")
						.setCerfile(NbiotHttpBuilder.SELFCERTPATH, NbiotHttpBuilder.SELFCERTPWD)
						.setTruststore(NbiotHttpBuilder.TRUSTCAPATH, NbiotHttpBuilder.TRUSTCAPWD)
						.appendParameter("appId",ServerRuntime.getInstance().getCtccNBiotAppId())
						.appendParameter("secret",ServerRuntime.getInstance().getCtccNBiotSecret())
						.post();
		
		JSONObject json = JSON.parseObject(str);
		accessToken = json.getString("accessToken");
		mStrRefreshToken = json.get("refreshToken").toString();
		
		authorization = json.get("tokenType").toString() + " " + json.get("accessToken").toString();
		expiretime = System.currentTimeMillis() + json.getIntValue("expiresIn") * 1000;
		log.info(json.getIntValue("expiresIn"));
		refreshtime = expiretime - 10 * 60 * 1000;
		if ( refreshtime < System.currentTimeMillis())
			refreshtime = expiretime;
	}

	@Override
	public void run() 
	{
		for ( ; ; )
		{
			if ( ServerRuntime.getInstance().getReadctccToken() != 1 )  // avoid multiple servers get token at one time by setting in db, since only one token is valid .
				Utils.sleep(10 * 60 * 1000);
			else if ( refreshtime < System.currentTimeMillis())
					login();
			else
			{
				long st = refreshtime - System.currentTimeMillis();
				if ( st > 0)
					Utils.sleep(st);
			}

		}
	}
}
