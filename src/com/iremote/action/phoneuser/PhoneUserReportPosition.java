package com.iremote.action.phoneuser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserInOutService;
import com.iremote.service.UserServiceMapService;
import com.iremote.task.notification.GooutWarning;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class PhoneUserReportPosition {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int longitude;
	private int latitude;
	private PhoneUser user ;

	public String execute()
	{
		
		user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		UserServiceMapService usms = new UserServiceMapService();
		if ( usms.query(user.getPhoneuserid(), user.getPlatform(), IRemoteConstantDefine.SERVICE_DEFINE_USER_POSITION) == null )
			return Action.SUCCESS;
		
		PhoneUserService svr = new PhoneUserService();
		PhoneUser u = svr.query(user.getPhoneuserid());
		
		u.setLongitude(longitude);
		u.setLatitude(latitude);
		
		UserInOutService uis = new UserInOutService();
		List<UserInOut> uiol = uis.query(user.getPhoneuserid());
		
		List<Integer> pul = PhoneUserHelper.querybySharetoPhoneuserid(user.getPhoneuserid());
		
		IremotepasswordService rs = new IremotepasswordService();
		List<Remote> lst = rs.querybyPhoneUserid(pul);
		
		Set<String> inset = filterRemote(lst , 200);
		Set<String> nset = filterRemote(lst , 500);
		nset.removeAll(inset);
		Set<String> allset = new HashSet<String>() ;
		for ( Remote r : lst )
			allset.add(r.getDeviceid());
		
		Set<String> outset = new HashSet<String>(allset);
		outset.removeAll(inset);
		outset.removeAll(nset);
		
		List<String> rl = new ArrayList<String>();
		List<String> gooutlst = new ArrayList<String>();
		
		NotificationService ns = new NotificationService();
		for ( UserInOut io : uiol )
		{
			if ( !allset.contains(io.getDeviceid()))
			{
				uis.delete(io);
				continue;
			}
			
			rl.add(io.getDeviceid());
			if ( io.getAction() == 1 )  // in , discard GPS report
				continue;
			if ( io.getAction() == 0 && outset.contains(io.getDeviceid())) // out & out 
				continue;
//			if ( io.getAction() == 1 && inset.contains(io.getDeviceid())) // in & in 
//				continue;
			if ( nset.contains(io.getDeviceid()))   // middle area , not change status.
				continue ;
			
			String message = "" ;
			if ( io.getAction() == 0 && inset.contains(io.getDeviceid())) // out & in
			{
				message = IRemoteConstantDefine.WARNING_TYPE_GO_HOME;
				io.setAction(1);
			}
//			if ( io.getAction() == 1 && outset.contains(io.getDeviceid())) // int & out
//			{
//				message = IRemoteConstantDefine.WARNING_TYPE_GO_OUT;
//				io.setAction(0);
//				gooutlst.add(io.getDeviceid());
//			}

			Notification n = createNotification(io.getDeviceid() , message);
			n.setPhoneuserid(user.getPhoneuserid());
			//ns.save(n);
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
		}
		
		inset.removeAll(rl);
		
		for ( String di : inset )
		{
			Notification n = createNotification(di , IRemoteConstantDefine.WARNING_TYPE_GO_HOME);
			n.setPhoneuserid(user.getPhoneuserid());
			//n.save(n);
			n.setPhoneuserid(user.getPhoneuserid());
			JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_GO_HOME, n);
			
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
	
	private void outsetalarm(List<String> gooutlst)
	{
		TaskManager.addTask(new GooutWarning(user , gooutlst));
	}
	
	private Set<String> filterRemote(List<Remote> lst ,  int r)
	{
		Set<String> set = new HashSet<String>();
		
		for ( Remote rt  : lst )
		{
			if ( Math.abs(rt.getLatitude() - this.latitude) <= r 
					&& Math.abs(rt.getLongitude() - this.longitude) <= r )
				set.add(rt.getDeviceid());
		}
		return set;
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
	
	public synchronized int getResultCode() {
		return resultCode;
	}
	public synchronized void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public synchronized void setLatitude(int latitude) {
		this.latitude = latitude;
	}
}
