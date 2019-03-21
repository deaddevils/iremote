package com.iremote.thirdpart.wswy;

import java.util.Calendar;
import java.util.Date;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.Camera;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.CameraService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.NotificationService;
import com.iremote.service.PhoneUserService;
import com.opensymphony.xwork2.Action;

@Deprecated
public class MoveDetectedAction 
{
	private static int overtime = 5 ;
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String applianceuuid;
	private String reporttime;
	private Camera camera;
	private Date time;
	private PhoneUser phoneuser ;
	private Remote remote;
	
	public String execute()
	{
		CameraService cs = new CameraService();
		
		camera = cs.querybyapplianceuuid(applianceuuid);
		
		if ( camera == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT ;
			return Action.SUCCESS;
		}
		
		time = Utils.parseTime(reporttime);
		
		readRemoteandPhoneuser();
		
		Notification n = savenotification(IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE);
		
		if ( n == null )
			return Action.SUCCESS;
		
		
		if ( phoneuser != null )
			NotificationHelper.pushmessage(n, phoneuser.getPhoneuserid(), catDevicename());
		
		return Action.SUCCESS;
	}
	
	private void readRemoteandPhoneuser()
	{
		IremotepasswordService svr = new IremotepasswordService();
		remote = svr.getIremotepassword(camera.getDeviceid());
	
		if ( remote != null && remote.getPhoneuserid() != null )
		{
			PhoneUserService pus = new PhoneUserService();
			phoneuser = pus.query(remote.getPhoneuserid());
		}
	}
	
	private String catDevicename()
	{
		String dn = camera.getName() ; 
		if ( dn == null )
			dn = " ";
		if ( remote != null && remote.getName() != null && remote.getName().length() > 0 )
			return String.format("%s(%s)", dn , remote.getName());
		return dn ;
	}
	
	private Notification savenotification(String message)
	{
		Notification n = new Notification();
		n.setMessage(message);
		n.setDeviceid(camera.getDeviceid());
		n.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA);
		n.setDevicetype(camera.getDevicetype());
		n.setReporttime(time);
		n.setName(camera.getName());
		n.setApplianceid(camera.getApplianceid());
		
		NotificationService svr = new NotificationService();
		
		Notification on = svr.query(n);
		if (on != null && on.getMessage().equals(n.getMessage()) )
		{
			Calendar c = Calendar.getInstance();
			c.setTime(on.getReporttime());
			c.add(Calendar.MINUTE, overtime );

			if (  c.getTime().after(n.getReporttime()))
				n.setEclipseby(on.getNotificationid());
		}
		
		if ( phoneuser != null && phoneuser.getArmstatus() == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM)
			n.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM);
		else if (  camera.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE )
			n.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_DEVICE_DISABLE);
		
//		svr.save(n);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

		if ( n.getEclipseby() == 0 && n.getStatus() == 0 )
			return n ;
		return null ;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setApplianceuuid(String applianceuuid) {
		this.applianceuuid = applianceuuid;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}


}
