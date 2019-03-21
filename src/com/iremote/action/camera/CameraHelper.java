package com.iremote.action.camera;

import com.iremote.common.IRemoteConstantDefine;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.action.camera.lechange.LeChangeInterface;
import com.iremote.action.camera.lechange.LeChangeUserTokenManager;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.jms.vo.CameraEvent;
import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;

public class CameraHelper
{
	private static Log log = LogFactory.getLog(CameraHelper.class);
	private static  Logger logdahua = Logger.getLogger("dahualechange");
	
	public static String createVirtualDeviceId(int platform , String productorid ,String applianceuuid )
	{
		return String.format("V%dC%s%s",platform, productorid , applianceuuid).toUpperCase();
	}
	
	public static boolean unbindDahuaLechangeCamera(PhoneUser phoneuser , String camerauuid)
	{
		return unbindDahuaLechangeCamera(phoneuser, camerauuid, IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC);
	}

	public static boolean unbindDahuaLechangeCamera(PhoneUser phoneuser, String camerauuid, String devicetype) {
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		String token = tm.getToken(phoneuser);

		if ( token == null )
			return false ;

		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(phoneuser.getPlatform(), devicetype);

		if ( lcr == null )
			return false;

		JSONObject rst = lcr.unbindDevice(camerauuid, token);
		if ( rst == null )
			return false ;

		if ( !ErrorCodeDefine.SUCCESS_STR.equals(lcr.getResultCode(rst)))
			return false ;

		String lechangecode = lcr.getResultCode(rst);
		String lechangemsg = lcr.getResultMsg(rst);

		logdahua.error(String.format("%s,%s(%d),unbind,result=%s,%s", camerauuid,phoneuser.getPhonenumber(),phoneuser.getPhoneuserid() , lechangecode,lechangemsg));

		return true;
	}


	public static CameraEvent createCameraEvent(Camera c)
	{
		CameraEvent ce = new CameraEvent();
		try {
			PropertyUtils.copyProperties(ce, c);
		} catch (Throwable t) {
			log.error(t.getMessage()  , t );
		} 
		
		ce.setCamerauuid(c.getApplianceuuid());

		return ce ;
	}
	
	public static void main(String arg[])
	{
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(0);
		//JSONObject rst = lcr.bindDevice("3E03A6BPAK01904", "C649F2", tm.getUserToken(0, "15603068107"));
		JSONObject rst = lcr.unbindDevice("3E03A6BPAK01904", tm.getUserToken(0, "15603068107"));
		System.out.println(rst.toJSONString());
	}
}
