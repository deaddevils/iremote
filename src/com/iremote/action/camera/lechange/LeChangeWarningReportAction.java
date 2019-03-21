package com.iremote.action.camera.lechange;

import java.util.*;

import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.*;
import com.iremote.domain.TimerTask;
import com.iremote.service.TimerTaskService;
import com.iremote.task.timertask.processor.CameraDelayAccordingHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.camera.CameraHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.RequestHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.CameraWarningType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;
import com.iremote.service.CameraService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;


public class LeChangeWarningReportAction
{
	private static Log log = LogFactory.getLog(LeChangeWarningReportAction.class);
	private static final String TYPE_WARNING_INFRARED = "0";
	private static final String TYPE_WARNING_MOVE = "1";
	private static final String TYPE_EVENT_ONLINE = "11";
	private static final String TYPE_EVENT_OFFLINE = "12";
	private static Set<String> TYPE_SET = new HashSet<>();
	private static Map<String, String> MSG_TYPE_MAP = new HashMap<>();
	private CameraDelayAccordingHelper cdah;
	
	private Camera camera ;
	private Remote remote;
	private Notification notification;
	private int phoneuserid ;
	private PhoneUser phoneuser ;
	private String did ;
	private String type;
	private Integer warningstatus;
	private boolean pushMesage = true;
	
	public String execute()
	{
		if ( init() == false )
			return Action.SUCCESS;
		
		if ( TYPE_WARNING_INFRARED.equals(type) )
			onWarningMove();
		else if ( TYPE_WARNING_MOVE.equals(type) )
			onWarningMove();
		else if ( TYPE_EVENT_ONLINE.equals(type) )
			onCameraOnline();
		else if ( TYPE_EVENT_OFFLINE.equals(type) )
			onCameraOffline();
		
		if ( notification != null)
		{
			//NotificationHelper.pushmessage(notification, phoneuserid, camera.getName());
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
			if (pushMesage) {
				JMSUtil.sendmessage(notification.getMessage(), createCameraEvent());
			}
		}
		return Action.SUCCESS;
	}
	
	private CameraEvent createCameraEvent()
	{
		CameraEvent ce = CameraHelper.createCameraEvent(camera);
		ce.setEventtime(new Date());
		ce.setPhoneuserid(phoneuser.getPhoneuserid());
		ce.setEventtype(notification.getMessage());
		ce.setWarningstatus(warningstatus);
		return ce ;
	}
	
	private boolean init()
	{
		String str = RequestHelper.readParameter();
		log.info(str);
		
		JSONObject json = JSON.parseObject(str);
		
		did = json.getString("did");
		type = getString(json,"type","msgType");
		if ( StringUtils.isBlank(did) ||  StringUtils.isBlank(type))
			return false;
		       
		if ( !TYPE_SET.contains(type))
			return false;
		
		CameraService cs = new CameraService();
		camera = cs.querybyapplianceuuid(did);
		
		if ( camera == null )
		{
			log.info("not found");
			return false;
		}
		
		RemoteService rs  = new RemoteService();
		remote = rs.getIremotepassword(camera.getDeviceid());
		
		if ( remote == null || remote.getPhoneuserid() == null || remote.getPhoneuserid() == 0 )
		{
			log.info("no owner");
			return false;
		}
		
		phoneuserid = remote.getPhoneuserid();
		
		PhoneUserService pus = new PhoneUserService();
		phoneuser = pus.query(phoneuserid);
		
		return true ;
	}

	private String getString(JSONObject json, String type, String subType) {
		return StringUtils.isNotBlank(json.getString(type))
				? json.getString(type)
				: MSG_TYPE_MAP.get(json.getString(subType));
	}

	private boolean isInArmStatus()
	{
		return PhoneUserHelper.hasArmFunction(phoneuser) 
				&& PhoneUserHelper.getPhoneuserArmStatus(phoneuser) == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM;
	}
	
	private void onWarningMove()
	{
	/*	if ( Utils.isJsonArrayContaints(camera.getWarningstatuses(), CameraWarningType.move.getWarningtype()))
			return ;
*/
		createNotification(IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE);

		cdah = new CameraDelayAccordingHelper(camera);
		if (!cdah.hasArmedByUserSetting()) {
			return;
		}

		if (!cdah.hasSetDelayAlarm()) {
			if ( Utils.isJsonArrayContaints(camera.getWarningstatuses(), CameraWarningType.move.getWarningtype()))
				return ;

			camera.setWarningstatuses(Utils.jsonArrayAppend(camera.getWarningstatuses(), CameraWarningType.move.getWarningtype()));
			warningstatus = CameraWarningType.move.getWarningtype();
		}else{
			warningstatus = CameraWarningType.move.getWarningtype();
			pushMesage = false;
			createTimerTask(IRemoteConstantDefine.TASK_CAMER_DELAY_RESTORE,
					IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE, camera, warningstatus);
		}
	}

	private void createTimerTask(int type, String msg, Camera camera, Integer todevicestatus) {
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

	private void onCameraOnline()
	{
		camera.setStatus(IRemoteConstantDefine.DEVICE_STATUS_NORMAL);
		createNotification(IRemoteConstantDefine.WARNING_TYPE_CAMERA_ONLINE);
	}
	
	private void onCameraOffline()
	{
		camera.setStatus(IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION);
		createNotification(IRemoteConstantDefine.WARNING_TYPE_CAMERA_OFFLINE);
	}
	
	private void createNotification(String message)
	{
		notification = new Notification(camera ,message);
		notification.setReporttime(new Date());
		notification.setPhoneuserid(remote.getPhoneuserid());
	}

	
	static
	{
		TYPE_SET.add(TYPE_WARNING_INFRARED);
		TYPE_SET.add(TYPE_WARNING_MOVE);
		TYPE_SET.add(TYPE_EVENT_ONLINE);
		TYPE_SET.add(TYPE_EVENT_OFFLINE);

		MSG_TYPE_MAP.put("videoMotion", TYPE_WARNING_MOVE);
		MSG_TYPE_MAP.put("online", TYPE_EVENT_ONLINE);
		MSG_TYPE_MAP.put("offline", TYPE_EVENT_OFFLINE);
	}

}
