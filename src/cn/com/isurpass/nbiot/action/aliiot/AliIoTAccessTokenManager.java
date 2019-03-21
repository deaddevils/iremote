package cn.com.isurpass.nbiot.action.aliiot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.JSONUtil;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AliIoTAccessTokenManager implements Runnable
{
    private static Log log = LogFactory.getLog(AliIoTAccessTokenManager.class);
    private static AliIoTAccessTokenManager instance = new AliIoTAccessTokenManager();

    private String authorization;

    private long expiretime = 0;
    private long refreshtime = 0;

    public static AliIoTAccessTokenManager getInstance()
    {
        return instance ;
    }

    private AliIoTAccessTokenManager() {}

    public synchronized String getAuthorization() {
        return authorization;
    }

    private synchronized void login()
    {
        String token = AliIoTHttpBuilder.getToken();
        if (StringUtils.isBlank(token)) {
            log.info("Get aliIoT's cloudToken failed");
            return;
        }
        JSONObject json = JSON.parseObject(token);

        authorization = JSONUtil.getString(json, "data.cloudToken");
        expiretime = System.currentTimeMillis() + JSONUtil.getInteger(json,"data.expireIn");
        log.info(JSONUtil.getInteger(json,"expireIn"));
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