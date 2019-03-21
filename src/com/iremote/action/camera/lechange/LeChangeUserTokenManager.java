package com.iremote.action.camera.lechange;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Remote;
import com.iremote.service.PhoneUserService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;

public class LeChangeUserTokenManager
{
	private static CacheManager cacheManager ; 
	private static Cache<String, DahuaLeChangeTokenVo> dahualechagnecache ;
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String lechangecode;
	private String lechangemsg;
	private String devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC;
	private boolean isTypeChanged = false;
	
	static
	{

		CacheConfigurationBuilder<String, DahuaLeChangeTokenVo> cb = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, DahuaLeChangeTokenVo.class, ResourcePoolsBuilder.heap(10));
		cb.withExpiry(Expirations.timeToLiveExpiration(Duration.of(IRemoteConstantDefine.TOKEN_CACHE_EXPIRE_TIME, TimeUnit.HOURS)));
		
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("dahualechagnecache",cb.build()).build(); 
		
		cacheManager.init(); 

		dahualechagnecache = cacheManager.getCache("dahualechagnecache",String.class, DahuaLeChangeTokenVo.class);
	}

	public String getToken(PhoneUser phoneuser) {
		if (phoneuser.getUsertype() == IRemoteConstantDefine.PHONEUSER_USER_TYPE_MAIL){
			if (!isTypeChanged) {
				devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_ABROAD;
			}
			return getUserToken(phoneuser);
		}
		if ("86".equals(phoneuser.getCountrycode()) && phoneuser.getUsertype() == 0) {
			return getUserToken(phoneuser);
        }
		return getAccessToken(phoneuser.getPlatform(),phoneuser.getPhonenumber());
	}

    public String getToken(Remote remote) {
        PhoneUserService pus = new PhoneUserService();
        PhoneUser pu = pus.query(remote.getPhoneuserid());
        return getToken(pu);
    }

    public String getAccessToken(int platform , String phonenumber){
        return getToken(platform, phonenumber, 1);
    }

	public String getUserToken(PhoneUser phoneuser)
	{
		if ( phoneuser == null )
			return null;
		return getUserToken(phoneuser.getPlatform() , phoneuser.getPhonenumber() );
	}

    public String getUserToken(int platform, String phonumber) {
        return getToken(platform, phonumber, 2);
    }

    /**
     * tokentype =  1 , accessToken,else usertoken
     * @param platform
     * @param phonenumber
     * @param tokentype
     * @return
     */
	public String getToken(int platform , String phonenumber,int tokentype)
	{
		if ( StringUtils.isBlank(phonenumber))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return null ;
		}
		
		DahuaLeChangeTokenVo vo = dahualechagnecache.get(makekey(platform , phonenumber, devicetype));
		
		if ( vo != null && vo.getExpiretime().after(new Date()) )
			return vo.getToken();
		
		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(platform, devicetype);

		if ( lcr == null )
		{
			resultCode = ErrorCodeDefine.THIRDPART_NETWORK_FAILED;
			return null;
		}
        JSONObject rst=null;
        if (tokentype == 1) {
             rst = lcr.accessToken();
        } else {
            rst = lcr.userToken(phonenumber);
        }

		if ( rst != null )
		{
			lechangecode = lcr.getResultCode(rst);
			lechangemsg = lcr.getResultMsg(rst);
		}
		
		if ( !ErrorCodeDefine.SUCCESS_STR.equals(lechangecode))
		{
			resultCode = ErrorCodeDefine.THIRDPART_CALL_FAILED;
			return null ;
		}

        String token = null;
        if (tokentype == 1) {
            token = lcr.getData(rst, "accessToken");
        } else {
		 token = lcr.getData(rst, "userToken");
        }

		dahualechagnecache.put(makekey(platform , phonenumber, devicetype), new DahuaLeChangeTokenVo(token));
		
		return token; 
	}
	
	private String makekey(int platform , String phonenumber, String devicetype)
	{
		return String.format("%d_%s_%s", platform , phonenumber, devicetype);
	}

	public String getLechangecode()
	{
		return lechangecode;
	}

	public String getLechangemsg()
	{
		return lechangemsg;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setDevicetype(String devicetype) {
		if (StringUtils.isBlank(devicetype)) {
			return;
		}
		isTypeChanged = true;
		this.devicetype = devicetype;
	}
}
