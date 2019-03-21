package com.iremote.action.camera.lechange;

import com.iremote.common.IRemoteConstantDefine;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

public class QueryLecChangeCameraStatusAction
{
	private static Log log = LogFactory.getLog(QueryLecChangeCameraStatusAction.class);
			
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private Integer cameraid;
	private String applianceuuid;
	private Integer status ;
	private String deviceModel ;
	private String version;
	private String ability;
	private String canBeUpgrade;
	private Integer csStatus;
	private Integer alarmStatus;
	private String channelPicUrl;
	private String ssid ;
	private String lechangecode;
	private String lechangemsg;
	private String breathingLightStatus ;
	private String direction;
	private Boolean alarmPlanEnable;
	private PhoneUser phoneuser;
	private Remote remote ;
	private Camera camera;
	private String devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC;

	public String execute()
	{
		if ( queryRemote() == false )
			return Action.SUCCESS;

		if (camera != null && camera.getDevicetype() != null) {
			devicetype = camera.getDevicetype();
		}

		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		
		String token = null ;
		if ( remote == null )
			token = tm.getToken(phoneuser);
		else 
			token = tm.getToken(remote);
		
		if ( token == null )
		{
			this.resultCode = tm.getResultCode();
			this.lechangecode = tm.getLechangecode();
			this.lechangemsg = tm.getLechangemsg();
			return Action.SUCCESS ;
		}
		
		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(phoneuser.getPlatform(), devicetype);
		
		if ( lcr == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}
		
		JSONObject rst = lcr.queryDeviceInfo(applianceuuid,  token);
		
		String s = lcr.getData(rst, "status");
		if ( StringUtils.isNotBlank(s))
		{
			status = Integer.valueOf(s);
			updateCameraStatus(status);
		}
		deviceModel = lcr.getData(rst, "deviceModel");
		version = lcr.getData(rst, "version");
		ability = lcr.getData(rst, "ability");
		canBeUpgrade = lcr.getData(rst, "canBeUpgrade");
		deviceModel = lcr.getData(rst, "deviceModel");
		
		if ( rst.containsKey("result")
				&& rst.getJSONObject("result").containsKey("data")
				&& rst.getJSONObject("result").getJSONObject("data").containsKey("channels"))
		{
			JSONArray ca = rst.getJSONObject("result").getJSONObject("data").getJSONArray("channels");
			if ( ca.size() > 0 )
			{
				JSONObject cl = ca.getJSONObject(0);
				csStatus = cl.getInteger("csStatus");
				alarmStatus = cl.getInteger("alarmStatus");
				channelPicUrl = cl.getString("channelPicUrl");
			}
		}
		
		this.lechangecode = lcr.getResultCode(rst);
		this.lechangemsg = lcr.getResultMsg(rst);
			
		rst = lcr.queryCurrentDeviceWifi(applianceuuid, token);
		
		ssid = lcr.getData(rst, "ssid");
		
		rst = lcr.queryBreathingLightStatus(applianceuuid, token);
		this.breathingLightStatus = lcr.getData(rst, "status");
		
		rst = lcr.queryFrameReverseStatus(applianceuuid, token);
		this.direction = lcr.getData(rst, "direction");
		
		rst = lcr.queryDeviceAlarmPlan(applianceuuid, token);
		try
		{
			if ( rst.containsKey("result")
					&& rst.getJSONObject("result").containsKey("data")
					&& rst.getJSONObject("result").getJSONObject("data").containsKey("rules"))
				alarmPlanEnable = rst.getJSONObject("result").getJSONObject("data").getJSONArray("rules").getJSONObject(0).getBoolean("enable");
		}
		catch(Throwable t )
		{
			log.error(t.getMessage() , t);
		}
		return Action.SUCCESS;
	}
	
	private void updateCameraStatus(Integer status)
	{
		if ( status == null || camera == null)
			return ;
		
		if ( status.equals(camera.getStatus()))
			return ;
		switch(status)
		{
		case 0:
			camera.setStatus(IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION);
			break;
		case 1:
		case 3:
			camera.setStatus(IRemoteConstantDefine.DEVICE_STATUS_NORMAL);
			break;
		}
		
	}
	
	private boolean queryRemote()
	{
		if ( cameraid != null )
		{
			CameraService cs = new CameraService();
			camera = cs.query(cameraid);
			if ( camera == null )
			{
				resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
				return false;
			}
			else 
			{
				applianceuuid = camera.getApplianceuuid();
				RemoteService rs = new RemoteService();
				remote = rs.getIremotepassword(camera.getDeviceid());
			}
		}
		else if ( StringUtils.isNotBlank(applianceuuid))
		{
			CameraService cs = new CameraService(); 
			camera = cs.querybyapplianceuuid(applianceuuid);
			if ( camera == null )
			{
				resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
				return false;
			}
			else 
			{
				RemoteService rs = new RemoteService();
				remote = rs.getIremotepassword(camera.getDeviceid());
			}
		}
		else 
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}
		return true;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public Integer getStatus()
	{
		return status;
	}
	public String getLechangecode()
	{
		return lechangecode;
	}
	public String getLechangemsg()
	{
		return lechangemsg;
	}
	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public String getDeviceModel()
	{
		return deviceModel;
	}

	public String getVersion()
	{
		return version;
	}

	public String getCanBeUpgrade()
	{
		return canBeUpgrade;
	}

	public Integer getCsStatus()
	{
		return csStatus;
	}

	public Integer getAlarmStatus()
	{
		return alarmStatus;
	}

	public String getChannelPicUrl()
	{
		return channelPicUrl;
	}

	public String getSsid()
	{
		return ssid;
	}

	public void setApplianceuuid(String applianceuuid)
	{
		this.applianceuuid = applianceuuid;
	}

	public String getAbility()
	{
		return ability;
	}

	public String getBreathingLightStatus()
	{
		return breathingLightStatus;
	}

	public String getDirection()
	{
		return direction;
	}

	public Boolean getAlarmPlanEnable()
	{
		return alarmPlanEnable;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
}
