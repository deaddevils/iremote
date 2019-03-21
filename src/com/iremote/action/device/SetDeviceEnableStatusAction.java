package com.iremote.action.device;


import java.util.Date;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class SetDeviceEnableStatusAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int enablestatus;
	private String deviceid;
	private int nuid ;
	
	public String execute()
	{
		PhoneUser u = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXIST;
			return Action.SUCCESS;
		}
		
		if ( r.getPhoneuserid() == null || r.getPhoneuserid() != u.getPhoneuserid() )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice d = zds.querybydeviceid(deviceid, nuid);
		
		if ( d != null )
			d.setEnablestatus(enablestatus);
		
		notification(r , d , u);
		
		return Action.SUCCESS;
	}

	private void notification(Remote r , ZWaveDevice d , PhoneUser u)
	{
		String message ;
		if ( enablestatus == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE )
			message = IRemoteConstantDefine.WARNING_TYPE_DEVICE_DISABLE;
		else 
			message = IRemoteConstantDefine.WARNING_TYPE_DEVICE_ENABLE;
		
		Notification n = new Notification(d , message);
		n.setReporttime(new Date());
		n.setAppendmessage(u.getPhonenumber());
		n.setPhoneuserid(r.getPhoneuserid());
		
		//NotificationService ns = new NotificationService();
		//ns.save(n);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
		NotificationHelper.pushmessage(n, u.getPhoneuserid(), NotificationHelper.catDevicename(r, d));
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid) {
		this.nuid = nuid;
	}
}
