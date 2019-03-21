package com.iremote.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.net.ssl.KeyManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.com.isurpass.camera.dahua.server.DahuaReportForAndroidProcessorInitializer;
import cn.com.isurpass.nbiot.action.aliiot.AliIoTAccessTokenManager;
import cn.com.isurpass.thread.DoorLockFaultDetectThread;
import com.iremote.common.jms.vo.LockPasswordRefreshed;
import com.iremote.event.association.*;
import com.iremote.event.device.doorlock.PushCobbeDoorLockStatus;
import com.iremote.event.device.doorlock.PushForbidRemoteOpenDoorLock;
import com.iremote.event.pushmessage.*;
import com.iremote.infraredtrans.*;
import com.iremote.thirdpart.cobbe.event.*;
import com.iremote.thirdpart.rentinghouse.event.*;
import com.iremote.thirdpart.tecus.event.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;
import org.xsocket.connection.IConnection.FlushMode;

import com.iremote.action.camera.lechange.LeChangeRequestManagerStore;
import com.iremote.action.device.InitTimer;
import com.iremote.action.helper.OemProductorHelper;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.SessionTrackerManager;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.message.MessageManager;
import com.iremote.common.push.PushMessage;
import com.iremote.common.push.PushMessageThread;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.common.sms.SMSInterface;
import com.iremote.common.sms.SMSManageThread;
import com.iremote.common.thread.ThreadManager;
import com.iremote.domain.OemProductor;
import com.iremote.domain.SystemParameter;
import com.iremote.event.camera.PushCameraStatus;
import com.iremote.event.camera.PushCameraWarningMessage;
import com.iremote.event.device.ClearDoorlockAssociationOnRemoteOwnerChange;
import com.iremote.event.device.ClearRoomInfoOnRemoteOwnerChange;
import com.iremote.event.device.OnDeleteInfraredDevice;
import com.iremote.event.device.OnDeleteZWaveDevice;
import com.iremote.event.device.doorlock.QueryDoorlockFunctionVersionProcessor;
import com.iremote.event.dsc.*;
import com.iremote.event.gateway.AutoCreateDevice;
import com.iremote.event.gateway.ClearDataOnGatewayOwnerChange;
import com.iremote.event.gateway.ClearDeviceGroupDetailOnGatewayOwnerChange;
import com.iremote.event.gateway.ClearDoorlockAssociationOnRemoteDelete;
import com.iremote.event.gateway.ClearRoomInfoOnRemoteDelete;
import com.iremote.event.gateway.NotifyGatewayOnlineEventConsumerProcessor;
import com.iremote.event.gateway.UdpGatewayOnlineProcessor;
import com.iremote.event.user.ShareRelationshipChanged;
import com.iremote.event.user.UserArmStatueChangePrecessor;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.OemProductorService;
import com.iremote.service.SystemParameterService;
import com.iremote.thirdpart.dorlink.event.DorlinkEventHelper;
import com.iremote.thirdpart.dorlink.event.*;
import com.iremote.thirdpart.rentinghouse.event.AddzWaveDeviceStepProcessor;
import com.iremote.thirdpart.rentinghouse.event.DeleteRemoteProcessor;
import com.iremote.thirdpart.rentinghouse.event.DeviceInfoChangeProcessor;
import com.iremote.thirdpart.rentinghouse.event.DeviceStatusChangeProcessor;
import com.iremote.thirdpart.rentinghouse.event.DoorlockOpenProcessor;
import com.iremote.thirdpart.rentinghouse.event.InfoChangeProcessor;
import com.iremote.thirdpart.rentinghouse.event.MeterStatusChangeProcessor;
import com.iremote.thirdpart.rentinghouse.event.RemoteOfflineProcessor;
import com.iremote.thirdpart.rentinghouse.event.RemoteOnlineProcessor;
import com.iremote.thirdpart.rentinghouse.event.RemoteOwnerChangeEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ScenepanelEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceBatteryEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceBatteryLowEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceEventProcessor;
import com.iremote.thirdpart.rentinghouse.event.ZWaveDeviceWarningProcessor;
import com.iremote.thirdpart.rentinghouse.task.ExpireZwaveDeviceShareTask;
import com.iremote.thirdpart.wcj.action.DoorlockPasswordManagerThread;
import com.iremote.thirdpart.wcj.action.DoorlockPasswordTaskManager;
import com.iremote.thirdpart.wcj.event.DoorlockMessageProcessor;
import com.iremote.thirdpart.wcj.report.ReportManager;

import cn.com.isurpass.camera.dahua.eventprocessor.DahuaCameraReportProcessor;
import cn.com.isurpass.camera.dahua.server.DahuaReportProcessorInitializer;
import cn.com.isurpass.gateway.server.GatewayHandlerInitializer;
import cn.com.isurpass.gateway.server.LockHandlerInitializer;
import cn.com.isurpass.gateway.udpserver.UdpLockHandlerInitializer;
import cn.com.isurpass.nbiot.request.NbiotAccessTokenManager;
import cn.com.isurpass.netty.NettyServer;
import cn.com.isurpass.netty.NettyUdpServer;
import cn.com.isurpass.pushmessage.pusher.ThirdpartMessagePusherStore;
import cn.com.isurpass.pushmessage.server.LoginHandler;
import cn.com.isurpass.serverlog.ServerRuntimeInfoLoger;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import net.sf.json.JSONArray;

public class RemoteServer implements Filter {

	private static Log log = LogFactory.getLog(RemoteServer.class);
	
	//public static final RemoteHandler remoteHandler = new RemoteHandler();
	public static final RemoteHandler2 remoteHandler2 = new RemoteHandler2();
	//private int iremoteport1 = 8920;
	private int iremoteport2 = 8921;
	private IServer socketserver1  ;
	private IServer socketserver2  ;
	private NettyServer dahuacamerareportserver;
	private NettyServer dahuacamerareportserver2;
	private NettyServer dahuacamerareportserver3;
	private NettyServer gatewayserver;
	private NettyServer lockserver;
	private NettyUdpServer udplockserver;
	  
	@Override
	public void destroy() 
	{
		try
		{
			//ConnectionManager.destory();
			//socketserver1.close();
			socketserver2.close();
			 
			if ( dahuacamerareportserver != null )
				dahuacamerareportserver.destroy();
			if ( dahuacamerareportserver2 != null )
				dahuacamerareportserver2.destroy();
			if ( dahuacamerareportserver3 != null )
				dahuacamerareportserver3.destroy();
			if ( gatewayserver != null )
				gatewayserver.destroy();
			if ( lockserver != null )
				lockserver.destroy();
			if ( udplockserver != null )
				udplockserver.destroy();
			
			
			ThreadManager.stopall();
//			dahuacamerareportserver.destroy();
//			gatewayserver.destroy();
//			lockserver.destroy();
			DoorlockPasswordTaskManager.shutdown();
			LoginHandler.shutdown(); 
			ReportManager.shutdown();
			JMSUtil.close();
			ReportProcessor.shutdown();
			ThirdpartMessagePusherStore.shutdown();
			ZWaveReportProcessor.shutdown();
			SynchronizeRequestHelper.shutdown();
			PushMessage.shutdown();
			
			ScheduleManager.shutdown();

			HibernateUtil.destroyall();
			//Utils.sleep(10*1000);
		}
		catch (IOException e)   
		{     
			log.error(e.getMessage() , e);  
		}
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		log.info(String.format("Server Start at %s" , Utils.formatTime( ServerRuntime.getInstance().getServerStartTime())));
		readconfigure();
		
		initJMS();
		initWorkerThread();
		initListenPort();
		initTimer();
	}

	public void initWorkerThread()
	{
		ThreadManager.keepRunning("SMSSendThread", SMSManageThread.getInstance());
		ThreadManager.keepRunning("SessionTrackerManager", SessionTrackerManager.getInstance());
		ThreadManager.keepRunning("ExpireZwaveDeviceShareTask", new ExpireZwaveDeviceShareTask());
		ThreadManager.keepRunning("SynchronizeObjectClear", new SynchronizeObjectClear());
		if (ServerRuntime.getInstance().getReadctccToken() == 1) {
			ThreadManager.keepRunning("NbiotAccessTokenManager", NbiotAccessTokenManager.getInstance());
			ThreadManager.keepRunning("AliIoTAccessTokenManager", AliIoTAccessTokenManager.getInstance());
		}
		
//		ScheduleManager.excuteEvery(600, 0, "DOOR_LOCK_PASSWORD_SEND", new DoorlockPasswordManagerThread(null));
		
		ScheduleManager.excuteEvery(600, 1, "SERVER_RUNTIME_LOG", new ServerRuntimeInfoLoger());
		ScheduleManager.excuteEvery(600, 2, "DOOR_LOCK_FAULT_DETECT", new DoorLockFaultDetectThread());
		ScheduleManager.excuteEvery(600, 3, "DOOR_LOCK_PASSWORD_SEND_2", new SendZwaveDoorLockPasswordProcessor());
	}
	
	public void initJMS()
	{
		JMSUtil.init();
		
		if ( ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_AMETA)
			initTriggerAlarmForAmetaProcessor();
		else
			initTriggerAlarmProcessor();

		initThirdpartPushProcessor();
		initPushWcjMessageProcessor();
		initAssociationProcessor();
		initAppPushProcessor();
		//initTecusEventProcessor();
		//initDorlinkEventProcessor();
		initInfoQueryProcessor();
		initCameraProcessor();
		initClearProcessor();
		initLockProcessor();
		
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,NotifyGatewayOnlineEventConsumerProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_UDP_GATEWAY_ONLINE,UdpGatewayOnlineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, DeviceReportNotificationAssociation.class);

	}
	
	private void initClearProcessor()
	{
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE,OnDeleteZWaveDevice.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_DELETE_INFRARED_DEVICE,OnDeleteInfraredDevice.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE,ClearRoomInfoOnRemoteDelete.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,ClearRoomInfoOnRemoteOwnerChange.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET,ClearRoomInfoOnRemoteOwnerChange.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,ClearDataOnGatewayOwnerChange.class);
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,AutoCreateDevice.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,CreateWifiLock.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE,ClearDeviceGroupDetailOnGatewayOwnerChange.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,ClearDeviceGroupDetailOnGatewayOwnerChange.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET,ClearDeviceGroupDetailOnGatewayOwnerChange.class);
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE,ClearDoorlockAssociationOnRemoteDelete.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,ClearDoorlockAssociationOnRemoteOwnerChange.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET,ClearDoorlockAssociationOnRemoteOwnerChange.class);
	}
	
	private void initCameraProcessor()
	{
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.DAHUA_CAMERA_REPORT, DahuaCameraReportProcessor.class);

		
	}
	
	private void initLockProcessor()
	{
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,CobbeGatewayLoginEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.DOOR_LOCK_SEND_PASSOWRD,SendPasswordForZufangProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,DoorlockFaultDetectProcessor.class);

		//JMSUtil.registMessageCosumer(IRemoteConstantDefine.DOOR_LOCK_SEND_PASSOWRD,SendPassword.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.DOOR_LOCK_SLEEP,SendSleepCommand.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,DoorlockOpenbyTempPassword.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DUPLICATED,DoorlockOpenbyTempPassword.class);

	}
	
	private void initInfoQueryProcessor()
	{
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,QueryDoorlockFunctionVersionProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE, QueryDSCStatus.class);
	}

	private void initTriggerAlarmProcessor()
	{		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVE_IN_DELAY_WARNING, TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING, TriggerAlarmOnArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE,TriggerAlarmOnArmStatus.class);
		
		if ( ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
			return ;
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,CloseGas.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,TriggerAlarm.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,TriggerAlarm.class);		
	}

	private void initTriggerAlarmForAmetaProcessor()
	{
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVE_IN_DELAY_WARNING, TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING, TriggerAlarmOnArmStatusForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE,TriggerAlarmOnArmStatusForAmeta.class);

//		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_DSC_PARTITION_WARNING_STATUS,TriggerZoneAlarmForAmeta.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,CloseGas.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,TriggerAlarmForAmetaProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,TriggerAlarmForAmetaProcessor.class);
	}

	private void initPushWcjMessageProcessor()
	{
		if ( ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
			return ;
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,DoorlockMessageProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,DoorlockMessageProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY,DoorlockMessageProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,DoorlockMessageProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION,DoorlockMessageProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_RECOVER,DoorlockMessageProcessor.class);
	}
	
	private void initAssociationProcessor()
	{
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS, DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS,AirQualityReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEOUT,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_METER_STATUS,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSGAE_TYPE_SCENE_PANNEL_TRIGGER,ScenePanelReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_BELL_RING, DoorLockBellRingAssociation.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_ASSOCIATION, DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GATEWAY_TEMPERATUREA_ASSOCIATION, GatewayTemperatureAssociation.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,WarningReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,WarningReportAssociation.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,WarningReportAssociation.class);
		JMSUtil.registMessageCosumer("unalarm" + IRemoteConstantDefine.WARNING_TYPE_SMOKE,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer("unalarm" + IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,DeviceReportAssociation.class);
		JMSUtil.registMessageCosumer("unalarm" + IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,DeviceReportAssociation.class);
	}

	private void initThirdpartPushProcessor()
	{
		if ( ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
			return ;
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS,DeviceStatusChangeProcessor.class);
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,DeviceStatusChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING, DeviceStatusChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE,DeviceStatusChangeProcessor.class);
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DEVICE_INFO_CHANGED,DeviceInfoChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED,InfoChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_TYPE_REMOTE_RESET,InfoChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_TYPE_LOCK_RESET,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE,DeleteRemoteProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE,RemoteOfflineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,RemoteOnlineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_TYPE_LOCK_NETWORK_INFO,RemoteNetworkChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_RECOVER,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_BATTERY,ZWaveDeviceBatteryEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,RemoteOwnerChangeEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,DoorlockOpenProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, DoorlockOpenProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DUPLICATED,DoorlockOpenProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,DoorlockOpenProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_METER_STATUS,MeterStatusChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVE_IN_DELAY_WARNING, ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEOUT,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_COBBE_DOOR_LOCK_STATUS,ZWaveDeviceEventProcessor.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSGAE_TYPE_SCENE_PANNEL_TRIGGER,ScenepanelEventProcessor.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY,ZWaveDeviceBatteryLowEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_NOT_CLOSE,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS, PushThirdpartDSCPartitionArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_ARM_STATUS, PushThirdpartDSCPartitionArmStatus.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_USER_ARM, UserArmStatueChangePrecessor.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_USER_INHOME_ARM, UserArmStatueChangePrecessor.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_USER_DISARM, UserArmStatueChangePrecessor.class);
        
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,ZWaveDeviceWarningProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_Add_ZWAVE_DEVICE_STEP,AddzWaveDeviceStepProcessor.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.ADDING_FINGER_PRINT_USER_STATUS, ZWaveFingerPrintEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.ADD_LOCK_USER_RESULT, AddLockUserResultEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.DELETE_LOCK_USER_RESULT, DeleteLockUserResultEventProcessor.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DSC_KEY_ALARM, ZWaveDeviceEventProcessor.class);

		JMSUtil.registMessageCosumer("unalarm" + IRemoteConstantDefine.NOTIFICATION_DSC_KEY_ALARM, ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_TAMPLE,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_SMOKE,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_SOS,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_MOVEIN,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,ZWaveDeviceEventProcessor.class);	
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY,ZWaveDeviceEventProcessor.class);	
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM,ZWaveDeviceEventProcessor.class); 
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_DISCONNECTION,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_SHORTCIRCUIT,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_TAMPLER,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_INSTRUSION,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_CONTACT,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_SAMELINESHORTCIRCUIT,ZWaveDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_TYPE_EFANCE_POWEROFF,ZWaveDeviceEventProcessor.class);	
	}
	
	private void initAppPushProcessor()
	{

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_ARM_STATUS_CHANGE,DeviceArmStatusChangeProcessor.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_SHARE_RELA_CHANGED,ShareRelationshipChanged.class);

		//if ( user armed ) warning message and warning sms then message 
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DELAY_WARNING, PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVE_IN_DELAY_WARNING, PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING, PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM,PushMessageAccordingtoArmStatus.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DSC_ALARM,PushDeviceStatus.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_DIS_CONNECTION, PushMessageAccordingtoArmStatusByElectricFence.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_SHORT_CIRCUIT, PushMessageAccordingtoArmStatusByElectricFence.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_TAMPLER, PushMessageAccordingtoArmStatusByElectricFence.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_INSTRUSION, PushMessageAccordingtoArmStatusByElectricFence.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_CONTACT, PushMessageAccordingtoArmStatusByElectricFence.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_SAME_LINE_SHORT_CIRCUIT, PushMessageAccordingtoArmStatusByElectricFence.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_POWER_OFF, PushMessageAccordingtoArmStatusByElectricFence.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE,PushCameraWarningMessage.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.CAMERA_WARNING_TYPE_MOVE,PushCameraStatus.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DSC_KEY_ALARM, PushDSCKeyAlarmMessage.class);
		JMSUtil.registMessageCosumer("unalarm" + IRemoteConstantDefine.NOTIFICATION_DSC_KEY_ALARM, PushWaringMessage2.class);

        JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_DSC_PARTITION_ARM_STATUS, PushDSCPartitionArmStatus.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DSC_PARTITION_ARM_STATUS, PushDSCPartitionArmStatusNotification.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_DSC_PARTITION_WARNING_STATUS, PushDSCPartitionWarningStatus.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.SET_CHANNEL_ENABLE_STATUS_TASK,SetChannelEnableStatusTask.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.SET_ARM_WITH_NO_DELAY,SetArmWithNoDelay.class);
        JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVNET_PUSH_SUB_ZWAVEDEVICE_STATUS, PushChannelStatus.class);

        JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_LOCK_PASSWORD_REFRESHED, PushLockPasswordRefreshed.class);

        //push warning message to app and send warning sms
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY,PushWarningMessage.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_NOT_CLOSE,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,PushAlarmMessageOnBullyLockOpen.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN_DUPLICATED,PushAlarmMessageOnBullyLockOpen.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,PushWarningNotificationandSms.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,PushWarningNotificationandSms.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_BATTERY,PushDeviceBattery.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS,PushDeviceStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS, PushSubDeviceStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_METER_STATUS,PushDeviceStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE,PushWarningMessage.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEOUT,PushWarningMessage.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE,PushWarningMessage.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_COBBE_DOOR_LOCK_STATUS, PushCobbeDoorLockStatus.class);
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_CAMERA_OFFLINE,PushCameraStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_CAMERA_ONLINE,PushCameraStatus.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSGAE_TYPE_SCENE_PANNEL_TRIGGER,PushDeviceStatus.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_REMOTE_UNLOCK_FORBIDDEN, PushForbidRemoteOpenDoorLock.class);
//		if ( ServerRuntime.getInstance().getSystemcode() != IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
//		{
//			JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,PushWarningMessage.class);
//			JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,PushWarningMessage.class);
//			JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,PushWarningMessage.class);
//		}
	}
	
	public void initTimer()
	{
		Thread t = new Thread(new InitTimer());
		t.start();
	}
	  
	@Deprecated
	private void initDorlinkEventProcessor()
	{
		if ( ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_NORTH_AMERICAN)
			return ;
		if ( DorlinkEventHelper.queryThirdpartid() == 0 )
			return ;
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED,DorlinkInfoChangeProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_DELETE_REMOTE,DorlinkUserDeleteRemoteProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE,DorlinkUserRemoteOfflineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE,DorlinkUserRemoteOnlineProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_RECOVER,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_BATTERY,DorlinkUserDeviceBatteryEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,DorlinkRemoteOwnerChangeEventProcessor.class);

		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY,DorlinkUserDeviceEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,DorlinkUserDeviceEventProcessor.class);

		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_TAMPLE,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_SMOKE,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_SOS,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_MOVEIN,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,DorlinkUserUnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,DorlinkUserUnwarningEventProcessor.class);	
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,DorlinkUserUnwarningEventProcessor.class);
	}
	
	private void initTecusEventProcessor()
	{
		if ( !Utils.hasArmFunction(ServerRuntime.getInstance().getSystemcode()))
			return ;
		
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_MOVEIN,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY,WarningEventProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,WarningEventProcessor.class);

		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_TAMPLE,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_SMOKE,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_SOS,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_MOVEIN,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_OPEN_INSIDE,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR,UnwarningEventProcessor.class);
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,UnwarningEventProcessor.class);	
		JMSUtil.registMessageCosumer("unalarm"+IRemoteConstantDefine.WARNING_TYPE_POWER_OVER_LOAD,UnwarningEventProcessor.class);

		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_TAMPLE,TriggerAlarmProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SOS,TriggerAlarmProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_SMOKE,TriggerAlarmProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK,TriggerAlarmProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_WATER_LEAK,TriggerAlarmProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES,TriggerAlarmProcessor.class);
		JMSUtil.registMessageCosumer(IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR,TriggerAlarmProcessor.class);
	}
	
	private void initListenPort()
	{
//		try
//		{
//			InetAddress address=InetAddress.getByName("0.0.0.0"); 
//			socketserver1 = new Server(address, iremoteport1, remoteHandler);
//			socketserver1.setFlushmode(FlushMode.ASYNC);
//			socketserver1.setConnectionTimeoutMillis(5000);
//			socketserver1.setIdleTimeoutMillis(180000);
//			socketserver1.start();
//		}
//		catch(Throwable t)
//		{
//			log.error(t.getMessage(), t);
//		}
		
		try
		{
			InetAddress address=InetAddress.getByName("0.0.0.0"); 
			socketserver2 = new Server(address, iremoteport2, remoteHandler2);
			socketserver2.setFlushmode(FlushMode.ASYNC);
			socketserver2.setConnectionTimeoutMillis(5000);
			socketserver2.setIdleTimeoutMillis(180000);
			socketserver2.start();
		}
		catch(Throwable t)
		{
			log.error(t.getMessage(), t);
		}
		
		dahuacamerareportserver = new NettyServer(ServerRuntime.getInstance().getDahuacameraserverport() , new DahuaReportProcessorInitializer( createCASslContext("iremote.isurpass.com.cn.jks", "String1234567")));
		dahuacamerareportserver.start();
		
		dahuacamerareportserver2 = new NettyServer(9101 , new DahuaReportProcessorInitializer());
		dahuacamerareportserver2.start();

		dahuacamerareportserver3 = new NettyServer(9102 , new DahuaReportForAndroidProcessorInitializer(createCASslContext("iremote.isurpass.com.cn.jks", "String1234567")));
		dahuacamerareportserver3.start();

		gatewayserver= new NettyServer(ServerRuntime.getInstance().getGatewayserverport() , new GatewayHandlerInitializer());
		gatewayserver.start();

		lockserver= new NettyServer(ServerRuntime.getInstance().getLockserverport() , new LockHandlerInitializer());
		lockserver.start();
		
		udplockserver = new NettyUdpServer(ServerRuntime.getInstance().getUdplockserverport() , new UdpLockHandlerInitializer());
		udplockserver.start();
}
	
	public void readconfigure()
	{
		HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
		
		SystemParameterService svr = new SystemParameterService();
		
		int p = svr.getIntValue("remoteport1");
//		if ( p != -1 )
//			iremoteport1 = p ;
		p = svr.getIntValue("remoteport2");
		if ( p != -1 )
			iremoteport2 = p ;

//		IremotepasswordService ips = new IremotepasswordService();
//		ips.initRemoteStatus();
		
		MessageManager.init();
		initServerRuntime();
		initSMScount();
		OemProductorHelper.initOemProducotr();
		initNeoProductor();
		initLeedarsonProductor();
		initSirenProductor();
		HibernateUtil.closeSession();
	}
	
	private void initNeoProductor() {
		try{
			SystemParameterService systemParameterService = new SystemParameterService();
			String neoproductor = systemParameterService.getStringValue("neoproductor");
			Set<String> neoproductorset = new TreeSet<>();
			if("".equals(neoproductor)||neoproductor==null){
				log.error("The productor data of NEO load failed, loading the initial data from the code!");
				List<String> asList = Arrays.asList("025800030083","025800031083","025800032083","025800033083","025800034083","025800035083","025800036083","025800037083","025800038083","02580003008d","02580003108d","02580003208d","02580003308d","02580003408d","02580003508d","02580003608d","02580003708d","02580003808d","025800030023","025800031023","025800032023","025800033023","025800034023","025800035023","025800036023","025800037023","025800030086");
				neoproductorset = new TreeSet<>(asList);
			}else{
				JSONArray json = JSONArray.fromObject(neoproductor);
				neoproductorset.addAll(json);
			}
			ServerRuntime.getInstance().setNeoproductorset(neoproductorset);
		}catch(Exception e){
			log.error(e.getMessage() , e);
		}
	}

	private void initLeedarsonProductor() {
		try{
			SystemParameterService systemParameterService = new SystemParameterService();
			String leeproductor = systemParameterService.getStringValue("leedarsonproductor");
			Set<String> leeproductorset = new TreeSet<>();
			if("".equals(leeproductor)||leeproductor==null){
				log.error("The productor data of Leedarson load failed, loading the initial data from the code!");
				List<String> asList = Arrays.asList("030002000009","038402000009");
				leeproductorset = new TreeSet<>(asList);
			}else{
				JSONArray json = JSONArray.fromObject(leeproductor);
				leeproductorset.addAll(json);
			}
			ServerRuntime.getInstance().setLeedarsonproductorset(leeproductorset);
		}catch(Exception e){
			log.error(e.getMessage() , e);
		}
	}
	private void initSirenProductor(){
		try{
			SystemParameterService systemParameterService = new SystemParameterService();
			String sirenproductor = systemParameterService.getStringValue("sirenproductor");//siren
			Set<String> sirenproductorset = new TreeSet<>();
			if("".equals(sirenproductor)||sirenproductor==null){
				log.error("The productor data of Siren load failed, loading the initial data from the code!");
				List<String> asList = Arrays.asList("025800030088","025800031088","025800032088","025800033088","025800034088","025800036088","025800037088","025800038088");
				sirenproductorset = new TreeSet<>(asList);
			}else{
				JSONArray json = JSONArray.fromObject(sirenproductor);
				sirenproductorset.addAll(json);
			}
			ServerRuntime.getInstance().setSirenproductorset(sirenproductorset);
		}catch(Exception e){
			log.error(e.getMessage() , e);
		}
	}
		
	
	private void initServerRuntime()
	{
		SystemParameterService sps = new SystemParameterService();
		List<SystemParameter> lst = sps.query();
		for ( SystemParameter sp : lst )
		{
			if ( PropertyUtils.isWriteable(ServerRuntime.getInstance(), sp.getKey()))
			{
				try {
					if ( sp.getStrvalue() != null )
						PropertyUtils.setProperty(ServerRuntime.getInstance(), sp.getKey(), sp.getStrvalue());
					else if ( sp.getIntvalue() != null )
						PropertyUtils.setProperty(ServerRuntime.getInstance(), sp.getKey(), sp.getIntvalue());
				} catch (Throwable t) {
					log.error(t.getMessage() , t);
				} 
			}
		}
	}

	private void initSMScount()
	{
		SystemParameterService sps = new SystemParameterService();
		ServerRuntime.getInstance().setDefaultsmscount(sps.getIntValue("default_sms_count", 30));
		ServerRuntime.getInstance().setDefaultcallcount(sps.getIntValue("default_call_count" , 0 ));
	}

	private static final String CERTIFICATE_FILE_BASE_DIR_1 = "E:\\key\\";
	private static final String CERTIFICATE_FILE_BASE_DIR_2 = "/opt/tools/sslkey/";

	private SslContext createCASslContext(String keyfile , String password)
	{
		String CERTIFICATE_FILE_BASE_DIR ;
		
		File f = new File(CERTIFICATE_FILE_BASE_DIR_1);
		if ( f.exists() )
			CERTIFICATE_FILE_BASE_DIR = CERTIFICATE_FILE_BASE_DIR_1;
		else 
			CERTIFICATE_FILE_BASE_DIR = CERTIFICATE_FILE_BASE_DIR_2;
		
        try
		{
//			return SslContextBuilder.forServer(new File(CERTIFICATE_FILE_BASE_DIR + "sslt.isurpass.com.cn_bundle.crt") 
//					, new File(CERTIFICATE_FILE_BASE_DIR + "sslt.isurpass.com.cn_pkcs8.key"))
//					.build();
			
	        KeyManagerFactory keyManagerFactory = null;
	        KeyStore keyStore = KeyStore.getInstance("JKS");
	        keyStore.load(new FileInputStream(CERTIFICATE_FILE_BASE_DIR + keyfile), password.toCharArray());
	        keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
	        keyManagerFactory.init(keyStore,password.toCharArray());
	        SslContext sslContext = SslContextBuilder.forServer(keyManagerFactory).build();
	        return sslContext;
		} 
        catch (Throwable e)
		{
			log.error(e.getMessage(),e);
		}
		return null;
	}
}
