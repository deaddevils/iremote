package com.iremote.action.device;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.action.phoneuser.SetPhoneUserArmStatus;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.DeviceArmEvent;
import com.iremote.domain.Camera;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CameraService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class SetArmStatusAction extends SetPhoneUserArmStatus {
    private int zwavedeviceid;
    private int cameraid;
    private String deviceid;
    private ZWaveDevice zwaveDevice ;
    private Camera camera;
    private ZWaveDeviceService zds = new ZWaveDeviceService();
    private CameraService cs = new CameraService();
    private String message;

    public String execute()
    {
        initDevice();

        if(resultCode != ErrorCodeDefine.SUCCESS){
            return Action.SUCCESS;
        }

        DeviceArmEvent event = new DeviceArmEvent();
        event.setArmstatus(armstatus);
        event.setCameraid(cameraid);
        event.setZwavedeviceid(zwavedeviceid);
        JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_ARM_STATUS_CHANGE,event);
        NotificationHelper.pushDeviceArmstatusChangeEvent(armstatus , deviceid , zwavedeviceid,cameraid);
        saveOperationLog();
        return Action.SUCCESS;
    }


    private void saveOperationLog() {
    	Notification n = new Notification();
		n.setPhoneuserid(phoneuser.getPhoneuserid());
		n.setFamilyid(phoneuser.getFamilyid());
		n.setDeviceid(deviceid);
		n.setZwavedeviceid(zwavedeviceid);
		n.setCameraid(cameraid);	
		if ( armstatus == IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM )
			message = IRemoteConstantDefine.WARNING_TYPE_DEVICE_DISARM;
		else if ( armstatus == IRemoteConstantDefine.DSC_TO_ARM_STATUS_ARM)
			message = IRemoteConstantDefine.WARNING_TYPE_DEVICE_ARM;
		n.setMessage(message);
		
		if(zwaveDevice!=null){
			n.setName(zwaveDevice.getName());
			n.setNuid(zwaveDevice.getNuid());
			n.setDevicetype(zwaveDevice.getDevicetype());
			n.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
		}else{
			n.setName(camera.getName());
			n.setDevicetype(camera.getDevicetype());
			n.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA);
		}
		n.setAppendmessage(phoneuser.getPhonenumber());
		n.setReporttime(new Date());
		n.setWarningstatus(armstatus);
    	
    	JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);
	}


	private void initDevice(){
        if(zwavedeviceid > 0){
            zwaveDevice = zds.query(zwavedeviceid);
        }
        if(cameraid > 0){
            camera = cs.query(cameraid);
        }
        if(zwaveDevice == null && camera == null){
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return;
        }
        if(zwaveDevice != null){
            deviceid = zwaveDevice.getDeviceid();
        }else{
            deviceid = camera.getDeviceid();
        }

        if (armstatus != IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM) {
            checkDeviceStatus();
        }
    }

    private void checkDeviceStatus() {
        if ( zwaveDevice.getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE){
            return;
        }

        if ( !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR.contains(zwaveDevice.getDevicetype())
                && !IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zwaveDevice.getDevicetype())){
            return;
        }
        if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_SENSOR.contains(zwaveDevice.getDevicetype())
                && zwaveDevice.getStatus() != null
                && zwaveDevice.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE ){
            resultCode = ErrorCodeDefine.PHONEUSER_ARM_SOME_DOORS_OR_WINDOWS_IS_OPENING;
            return;
        }
        if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(zwaveDevice.getDevicetype())
                && zwaveDevice.getStatus() != null
                && zwaveDevice.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE ){
            resultCode = ErrorCodeDefine.PHONEUSER_ARM_SOME_DOORS_OR_WINDOWS_IS_OPENING;
            return;
        }
    }


    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setArmstatus(int armstatus) {
        this.armstatus = armstatus;
    }

    public void setCameraid(int cameraid) {
        this.cameraid = cameraid;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }
}
