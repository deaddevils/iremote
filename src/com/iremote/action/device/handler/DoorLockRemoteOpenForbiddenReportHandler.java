package com.iremote.action.device.handler;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

import java.util.Date;

public class DoorLockRemoteOpenForbiddenReportHandler implements IZwaveReportConsumer {
    boolean finished = false;
    Integer phoneuserid;
    @Override
    public void reportArrive(String deviceid, int nuid, byte[] report) {
        finished = true;

        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zWaveDevice = zds.querybydeviceid(deviceid, nuid);
        if (zWaveDevice == null) {
            return;
        }

        pushMessage(zWaveDevice);

        saveNotification(zWaveDevice);
    }

    private void saveNotification(ZWaveDevice zWaveDevice) {
        Notification notification = new Notification();

        notification.setWarningstatus(zWaveDevice.getWarningstatus());
        notification.setMessage(IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_REMOTE_UNLOCK_FORBIDDEN);
        notification.setDeviceid(zWaveDevice.getDeviceid());
        notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
        notification.setDevicetype(zWaveDevice.getDevicetype());
        notification.setNuid(zWaveDevice.getNuid());
        notification.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
        notification.setReporttime(new Date());
        notification.setName(zWaveDevice.getName());
        notification.setPhoneuserid(phoneuserid);

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    private void pushMessage(ZWaveDevice zWaveDevice) {
        String msg = IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_REMOTE_UNLOCK_FORBIDDEN;
        ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
        zde.setNuid(zWaveDevice.getNuid());
        zde.setDeviceid(zWaveDevice.getDeviceid());
        zde.setApplianceid(zWaveDevice.getApplianceid());
        zde.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
        zde.setWarningstatus(zWaveDevice.getWarningstatus());
        zde.setWarningstatuses(zWaveDevice.getWarningstatuses());
        zde.setDevicetype(zWaveDevice.getDevicetype());
        zde.setStatus(zWaveDevice.getStatus());
        zde.setEventtype(msg);
        zde.setEventtime(new Date());
        zde.setName(zWaveDevice.getName());
        
        RemoteService rs = new RemoteService();
        phoneuserid = rs.queryOwnerId(zWaveDevice.getDeviceid());

        zde.setPhoneuserid(phoneuserid);

        JMSUtil.sendmessage(msg, zde);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
