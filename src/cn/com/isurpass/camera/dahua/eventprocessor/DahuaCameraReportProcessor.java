package cn.com.isurpass.camera.dahua.eventprocessor;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.CameraHelper;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.CameraWarningType;
import com.iremote.common.constant.DahuaCameraReportType;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.service.CameraService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.TimerTaskService;
import com.iremote.task.timertask.processor.CameraDelayAccordingHelper;

public class DahuaCameraReportProcessor extends CameraEvent implements ITextMessageProcessor
{
	private PhoneUser phoneuser ;
	private int armstatus ;
	private Camera camera;
	private CameraDelayAccordingHelper cdah;
	
	@Override
	public void run()
	{
		CameraService cs = new CameraService();
		camera = cs.querybyapplianceuuid(super.getCamerauuid());
		
		if ( camera == null )
			return ;
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(camera.getDeviceid());
		
		if ( r == null )
			return ;
		
		PhoneUserService pus = new PhoneUserService();
		if ( r.getPhoneuserid() != null )
			phoneuser = pus.query(r.getPhoneuserid());
		
		if ( DahuaCameraReportType.call.getType().equals(super.getType()))
			processCallEvent(camera , r);
		else if (DahuaCameraReportType.alarm.getType().equals(super.getType()))
			processAlarmEvent(camera , r);
		else if (DahuaCameraReportType.move.getType().equals(super.getType()))
			processMoveEvent(camera ,r);
	}
	
	private void processMoveEvent(Camera c , Remote r)
	{
		Notification notification = createNotification(c ,r);
		notification.setMessage(IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE);
		
        if (phoneuser != null) {
            int warningstatus = 0;

            cdah = new CameraDelayAccordingHelper(camera);

            if (cdah.hasArmedByUserSetting()) {
            	if (!Utils.isJsonArrayContaints(camera.getWarningstatuses(), CameraWarningType.move.getWarningtype())) {
		            warningstatus = CameraWarningType.move.getWarningtype();
	            }

				if (!cdah.hasSetDelayAlarm()) {
					if (warningstatus != 0) {
						camera.setWarningstatuses(Utils.jsonArrayAppend(camera.getWarningstatuses(), CameraWarningType.move.getWarningtype()));
					}
				JMSUtil.sendmessage(notification.getMessage(), createCameraEvent(notification, c, r.getPhoneuserid(), warningstatus));
				} else {
					createTimerTask(IRemoteConstantDefine.TASK_CAMER_DELAY_RESTORE,
							IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE, camera, warningstatus);
//					return;
				}
            } else {
		        JMSUtil.sendmessage(notification.getMessage(), createCameraEvent(notification, c, r.getPhoneuserid(), warningstatus));
            }
        }

		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

	private void createTimerTask(int type, String msg, Camera camera, int todevicestatus) {
		TimerTaskService tts = new TimerTaskService();
		TimerTask timerTask = tts.queryByTypeAndObjid(type, camera.getCameraid());
		if (timerTask != null ){
			if (timerTask.getExcutetime().getTime() > System.currentTimeMillis()) {
				return;
			}
		}else{
			timerTask = new TimerTask();
		}

		timerTask.setCreatetime(new Date());
		long date = System.currentTimeMillis() + cdah.getDeviceDelayTime() * 1000;
		timerTask.setExcutetime(new Date(date));
		timerTask.setExpiretime(new Date(date + IRemoteConstantDefine.TIMER_TASK_EXPIRE_TIME));
		timerTask.setObjid(camera.getCameraid());
		timerTask.setType(type);
		timerTask.setDeviceid(camera.getDeviceid());

		JSONObject json = new JSONObject();
//		json.put("reportid", zrb.getReportid());
		json.put("msg", msg);
		json.put("todevicestatus", todevicestatus);
		json.put("armstatus", cdah.getArmStatus());
		timerTask.setJsonpara(json.toJSONString());

		ScheduleManager.excuteWithSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
	}

	private CameraEvent createCameraEvent(Notification notification,Camera camera , int phoneuserid , int warningstatus)
	{
		CameraEvent ce = CameraHelper.createCameraEvent(camera);
		ce.setEventtime(new Date());
		ce.setPhoneuserid(phoneuserid);
		ce.setEventtype(notification.getMessage());
		ce.setWarningstatus(warningstatus);
		return ce ;
	}
	
	private void processCallEvent(Camera c , Remote r)
	{	
		if ( phoneuser == null )
			return ;
		Notification notification = createNotification(c, r);
		notification.setMessage(IRemoteConstantDefine.WARNING_TYPE_DOOR_BELL_RING);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);

		NotificationHelper.pushDoorbellRingMessage(c , new Date(), phoneuser);
	}
	
	private void processAlarmEvent(Camera c , Remote r)
	{
		Notification notification = createNotification(c ,r);
		notification.setMessage(IRemoteConstantDefine.WARNING_TYPE_TAMPLE);

		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
		
		NotificationHelper.pushWarningNotification(notification, c.getName() , IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM);
	}
	
	private Notification createNotification(Camera c , Remote r)
	{
		Notification notification = new Notification();
		
		notification.setDeviceid(r.getDeviceid());
		notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA);
		notification.setDevicetype(c.getDevicetype());
		notification.setCameraid(c.getCameraid());
		notification.setReporttime(new Date());
		notification.setName(c.getName());
		notification.setPhoneuserid(r.getPhoneuserid());

		if ( phoneuser != null )
		{
			armstatus = PhoneUserHelper.getPhoneuserArmStatus(phoneuser);
			if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM )
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM);
			else if ( armstatus == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_SLEEPING_ARM )
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_INHOME_ARM);
			else 
				notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_USER_ARM);
		}
		else 
			notification.setStatus(IRemoteConstantDefine.NOTIFICATION_STATUS_NO_OWNER);
		return notification;
	}
	
	@Override
	public String getTaskKey()
	{
		return super.getCamerauuid();
	}


}
