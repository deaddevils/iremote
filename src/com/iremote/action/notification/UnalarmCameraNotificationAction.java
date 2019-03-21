package com.iremote.action.notification;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.camera.CameraHelper;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Camera;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "camera", parameter = "cameraid")
public class UnalarmCameraNotificationAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int cameraid ;
	private Camera camera;
	private PhoneUser phoneuser;
	
	public String execute()
	{
		if (camera == null) {
			CameraService cs = new CameraService();
			camera = cs.query(cameraid);
		}
		
		if ( camera == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( StringUtils.isBlank(camera.getWarningstatuses() ))
			return Action.SUCCESS;
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(camera.getDeviceid());
		
		JSONArray ja = JSON.parseArray(camera.getWarningstatuses());
		for ( int i = 0 ; i < ja.size()	; i ++ )
		{
			String message = Utils.unalarmmessage(IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA , ja.getInteger(i));
			
			if ( message != null )
			{
				Notification n = new Notification(camera , message);
				
				if ( r != null )
					n.setPhoneuserid(r.getPhoneuserid());
				
				n.setUnalarmphonenumber(phoneuser.getPhonenumber());
				n.setUnalarmphoneuserid(phoneuser.getPhoneuserid());
				n.setReporttime(new Date());
				
				JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
				JMSUtil.sendmessage(message, createUnalarmEventObject(message));
			}
		}
		
		camera.setWarningstatuses(null);
		
		DeviceHelper.unalarmAlarmDevice(phoneuser);
		
		return Action.SUCCESS;
	}

	private CameraEvent createUnalarmEventObject(String message)
	{
		CameraEvent ce = CameraHelper.createCameraEvent(camera);
		ce.setEventtime(new Date());
		ce.setPhoneuserid(phoneuser.getPhoneuserid());
		ce.setEventtype(message);

		return ce ;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setCameraid(int cameraid) {
		this.cameraid = cameraid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	void setCamera(Camera camera){
		this.camera = camera;
	}
}
