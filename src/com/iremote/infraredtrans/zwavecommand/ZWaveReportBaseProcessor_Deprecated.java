package com.iremote.infraredtrans.zwavecommand;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.taskmanager.IMulitReportTask;
import com.iremote.common.taskmanager.TaskManager;
import com.iremote.domain.Notification;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.NotificationService;
import com.iremote.task.devicereaction.DeviceAssociationSceneTaskProcessor;

@Deprecated
public abstract class ZWaveReportBaseProcessor_Deprecated implements IZwaveReportProcessor {
	


	private static Log log = LogFactory.getLog(ZWaveReportBaseProcessor_Deprecated.class);
	
	protected static final String[] ALARM_MESSAGE = new String[]{IRemoteConstantDefine.WARNING_TYPE_TAMPLE , 
																IRemoteConstantDefine.WARNING_TYPE_SMOKE, 
																IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK , 
																"" ,"" ,
																IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK};



	protected int overtime = 5 ;
	protected Notification notification;

	protected ZwaveReportBean zrb ;
	protected boolean finished = false ;
	protected boolean executed = false ;
	
	@Override
	public void setReport(ZwaveReportBean zrb) 
	{
		this.zrb = zrb ;
		
	}
	
	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void run() 
	{
		try {
			executed = true;
			process();
			finished = true;
		} catch (Throwable e) {
			log.error(e.getMessage() , e);
		} 
	}

	@Override
	public boolean merge(IMulitReportTask task) {
		return false;
	}

	@Override
	public boolean isReady() 
	{
		return true;
	}

	@Override
	public boolean isExecuted() {
		return executed;
	}
	
	public void process() throws BufferOverflowException, IOException 
	{
		try
		{
			if ( zrb.init() == false )
			{
				log.info("init failed.");
				return ;
			}
			
			recover();
			
			processZwaveReport();
		}
		catch(Throwable t )
		{
			log.error(t.getMessage() , t);
		}
		
	}
	
	public long getTaskIndentify()
	{
		return zrb.getReportid();
	}
	
	private void recover()
	{
		if ( zrb.getDevice().getStatus() == null || zrb.getDevice().getStatus() != -1 )// not a malfunction device
			return ;
				
		Notification n = new Notification(zrb.getDevice() , IRemoteConstantDefine.WARNING_TYPE_RECOVER);
		n.setReporttime(new Date());
		
		NotificationService ns = new NotificationService();
		//ns.save(n);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

		if ( zrb.getPhoneuser() != null )
			NotificationHelper.pushmessage(n, zrb.getPhoneuser().getPhoneuserid(), catDevicename());
		
		zrb.getDevice().setStatus(0);
		if (IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zrb.getDevice().getDevicetype()))
		{
			zrb.getDevice().setStatus(255);// door lock default status is closed.
			zrb.getDevice().setShadowstatus(zrb.getDevice().getStatus());
		}
		
		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_RECOVER, new ZWaveDeviceEvent(zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getDeviceid() , zrb.getDevice().getNuid() ,n.getMessage(), n.getReporttime() , zrb.getReportid()));
	}

	protected abstract void processZwaveReport();
	
	protected void pushdevicestatus()
	{
		NotificationHelper.pushDeviceStatus(zrb.getDevice() , zrb.getReporttime() , this.zrb.getOperator());
	}
	
	protected void pushtothirdpart()
	{
		if (notification == null || notification.getEclipseby() != 0  )
			return ;
		//ReportManager.addReport(zrb.getRemote(),zrb.getDevice(), notification);
	}

	protected void pushmessage()
	{
		if (notification == null || notification.getEclipseby() != 0  )
			return ;
		
		zrb.getNotificationsender().pushmessage(notification, zrb.getPhoneuser(), catDevicename());
	}

	protected void pushNotification()
	{
		if ( zrb.getDevice().getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			return ;
		
		if ( notification.getEclipseby() != 0 )
			return ;
		
		notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_ARM);
		
		NotificationHelper.pushWarningNotification(notification, catDevicename());
	}
	
	protected void pushJSMMessage()
	{
		if (notification != null 
				&& notification.getStatus() != IRemoteConstantDefine.NOTIFICATION_STATUS_DEVICE_DISABLE 
				&& notification.getEclipseby() == 0 ) 
			JMSUtil.sendmessage(notification.getMessage(), new ZWaveDeviceEvent(zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getDeviceid() , zrb.getDevice().getNuid() ,notification.getMessage(), notification.getReporttime() , zrb.getReportid()));
	}
	
	protected void startAssociationScene(int status)
	{
		if ( zrb.getDevice().getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			return ;
		if ( IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR.equals(zrb.getOperator()) )
			return ;
		if ( zrb.getOperatesource() == 3 || zrb.getOperatesource() == 4 )
			return ;
		if ( notification != null && notification.getEclipseby() != 0 )
			return ;
		TaskManager.addTask(new DeviceAssociationSceneTaskProcessor(zrb.getDevice() , zrb.getCommandvalue().getChannelid() , status , zrb.getReportid()));
	}

	protected String catDevicename()
	{
		String dn = zrb.getDevice().getName() ; 
		if ( dn == null )
			dn = " ";
		if ( zrb.getRemote() != null && zrb.getRemote().getName() != null && zrb.getRemote().getName().length() > 0 )
			return String.format("%s(%s)", dn , zrb.getRemote().getName());
		return dn ;
	}
	
	protected void savenotification(String message)
	{
		notification = new Notification();
		notification.setMessage(message);
		notification.setDeviceid(zrb.getDeviceid());
		notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		notification.setDevicetype(zrb.getDevice().getDevicetype());
		notification.setNuid(zrb.getDevice().getNuid());
		notification.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
		notification.setReporttime(zrb.getReporttime());
		notification.setOrimessage(byteArraytoString(zrb.getCmd()));
		notification.setName(zrb.getDevice().getName());
		if ( !IRemoteConstantDefine.ASSOCIATION_SCENE_TASK_OPERATOR.equals(zrb.getOperator()))
			notification.setAppendmessage(zrb.getOperator());
		
		NotificationService svr = new NotificationService();
		
		Notification on = svr.query(notification);
		if (on != null && on.getMessage().equals(notification.getMessage()) )
		{
			Calendar c = Calendar.getInstance();
			c.setTime(on.getReporttime());
			c.add(Calendar.MINUTE, overtime );

			if (  c.getTime().after(notification.getReporttime()))
				notification.setEclipseby(on.getNotificationid());
		}
		if ( zrb.getDevice().getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_DEVICE_DISABLE);
		else if ( zrb.getPhoneuser() != null )
		{
			int armstatus = PhoneUserHelper.getPhoneuserArmStatus(zrb.getPhoneuser());
			if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM);
			else if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM )
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_INHOME_ARM);
		}
		//svr.save(notification);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);

	}
	
	private String byteArraytoString(byte[] b)
	{
		StringBuffer sb = new StringBuffer("[");
		String prefix = "";
		if ( b != null && b.length > 0 )
		{
			for ( int i = 0 ; i < b.length ; i ++ )
			{
				sb.append(prefix).append(b[i] & 0xff);
				prefix = ",";
			}
		}
		sb.append("]");
		return sb.toString();
	}

	protected void triggeAlarm()
	{
		if ( zrb.getDevice().getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
			return ;

		if ( notification.getEclipseby() != 0 )
			return ;
	
		zrb.getNotificationsender().triggeralarm(zrb.getPhoneuser(), notification);
		
	}

	protected Date parseReportTime(byte[] request)
	{
		int t = TlvWrap.readInt(request, 104, 4);
		if ( t == Integer.MIN_VALUE || t < 365 * 24 * 3600 )  
			return new Date();
		return Utils.parseTime(t);
	}

}
