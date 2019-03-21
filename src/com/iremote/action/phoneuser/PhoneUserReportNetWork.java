package com.iremote.action.phoneuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.taskmanager.TaskManager;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.UserInOut;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.NotificationService;
import com.iremote.service.UserInOutService;
import com.iremote.service.UserServiceMapService;
import com.iremote.task.notification.GooutWarning;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class PhoneUserReportNetWork {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	@SuppressWarnings("unused")
	private String network;
	private String ssid;
	private PhoneUser user ;
	
	public String execute()
	{
		
		user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		UserServiceMapService usms = new UserServiceMapService();
		if ( usms.query(user.getPhoneuserid(), user.getPlatform(), IRemoteConstantDefine.SERVICE_DEFINE_USER_POSITION) == null )
			return Action.SUCCESS;

		UserInOutService uis = new UserInOutService();
		List<UserInOut> uiol = uis.query(user.getPhoneuserid());
		
		List<Integer> pul = PhoneUserHelper.querybySharetoPhoneuserid(user.getPhoneuserid());
		
		IremotepasswordService rs = new IremotepasswordService();
		List<Remote> lst = rs.querybyPhoneUserid(pul);

		Set<String> nil = new HashSet<String>();
		
		if ( ssid != null && ssid.length() > 0 )
		{
			for ( Remote r : lst )
			{
				if ( ssid.equals(r.getSsid()))
					nil.add(r.getDeviceid());
			}
		}
		
		List<String> rl = new ArrayList<String>();
		List<String> gooutlst = new ArrayList<String>();
		NotificationService ns = new NotificationService();
		
		for ( UserInOut uio : uiol )
		{
			rl.add(uio.getDeviceid());
			if ( uio.getAction() == 0 && !nil.contains(uio.getDeviceid()))  // out & out 
				continue;
			if ( uio.getAction() == 1 && nil.contains(uio.getDeviceid()))   // in & in 
				continue;
			
			String message = "" ;
			if ( uio.getAction() == 0 && nil.contains(uio.getDeviceid()))   // out & in ;
			{
				message = IRemoteConstantDefine.WARNING_TYPE_GO_HOME;
				uio.setAction(1);
			}
			else if ( uio.getAction() == 1 && !nil.contains(uio.getDeviceid())) // int & out 
			{
				message = IRemoteConstantDefine.WARNING_TYPE_GO_OUT;
				uio.setAction(0);
				gooutlst.add(uio.getDeviceid());
			}
			Notification n = createNotification(uio.getDeviceid() , message);
			n.setPhoneuserid(user.getPhoneuserid());
			//ns.save(n);
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
		}
		
		nil.removeAll(rl);
		
		for ( String di : nil )
		{
			Notification n = createNotification(di , IRemoteConstantDefine.WARNING_TYPE_GO_HOME);
			//ns.save(n);
			n.setPhoneuserid(user.getPhoneuserid());
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
			
			UserInOut io = new UserInOut();
			io.setAction(1);
			io.setPhonenumber(user.getPhonenumber());
			io.setPhoneuserid(user.getPhoneuserid());
			io.setDeviceid(di);
			uis.save(io);
		}
		
		outsetalarm(gooutlst);
		
		return Action.SUCCESS;
	}
	
	private Notification createNotification(String deviceid , String message )
	{
		Notification n = new Notification();
		n.setDeviceid(deviceid);
		n.setMessage(message);
		n.setName(user.getPhonenumber());
		//n.setEclipseby(1); //Don't show get in or out message to users;
		return n ;
	}
	
	private void outsetalarm(List<String> gooutlst)
	{
		TaskManager.addTask(new GooutWarning(user , gooutlst));
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

}
