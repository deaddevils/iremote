package com.iremote.thirdpart.rentinghouse.event;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.notification.UnalarmCameraNotificationAction;
import com.iremote.action.notification.UnalarmNotificationAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.DeviceArmEvent;
import com.iremote.domain.Camera;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class DeviceArmStatusChangeProcessor extends DeviceArmEvent implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DeviceArmStatusChangeProcessor.class);



	@Override
	public void run()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		CameraService cs = new CameraService();

		ZWaveDevice zwaveDevice = null;
		Camera camera = null;
		String deviceid = null;
		if(getZwavedeviceid() > 0){
			zwaveDevice = zds.query(getZwavedeviceid());
		}else{
			camera = cs.query(getCameraid());
		}

		if(zwaveDevice == null && camera == null){
			return;
		}
		if(zwaveDevice != null){
			zwaveDevice.setArmstatus(getArmstatus());
			deviceid = zwaveDevice.getDeviceid();
		}
		if(camera != null){
			camera.setArmstatus(getArmstatus());
			deviceid = camera.getDeviceid();
		}
		unalarm(getArmstatus(),zwaveDevice,camera);
		NotificationHelper.pushDeviceArmstatusChangeEvent( getArmstatus() , deviceid , getZwavedeviceid(), getCameraid());
	}


	private void unalarm(int armstatus,ZWaveDevice zwaveDevice, Camera camera){
		if(armstatus != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM){
			return;
		}
		if(zwaveDevice != null){
			UnalarmNotificationAction unalarmNotificationAction = new UnalarmNotificationAction();
			unalarmNotificationAction.setZwavedeviceid(zwaveDevice.getZwavedeviceid());
			unalarmNotificationAction.setPhoneuser(getUser(zwaveDevice.getDeviceid()));
			unalarmNotificationAction.execute();
		}else if(camera != null){
			UnalarmCameraNotificationAction unalarmCameraNotificationAction = new UnalarmCameraNotificationAction();
			unalarmCameraNotificationAction.setCameraid(camera.getCameraid());
			unalarmCameraNotificationAction.setPhoneuser(getUser(camera.getDeviceid()));
			unalarmCameraNotificationAction.execute();
		}
	}

	private PhoneUser getUser(String deviceid){
		RemoteService rs = new RemoteService();
		PhoneUserService pus = new PhoneUserService();
		Integer phoneuserid = rs.queryOwnerId(deviceid);
		return pus.query(phoneuserid);
	}

	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}
