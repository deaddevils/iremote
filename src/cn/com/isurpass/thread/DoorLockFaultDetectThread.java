package cn.com.isurpass.thread;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZwaveDeviceActiveTime;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZwaveDeviceActiveTimeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoorLockFaultDetectThread implements Runnable {
    @Override
    public void run() {
        ZwaveDeviceActiveTimeService zdats = new ZwaveDeviceActiveTimeService();
        List<ZwaveDeviceActiveTime> zwaveDeviceActiveTimes = zdats.queryNeedChangetoFaultDetectDevices();
        ArrayList<Integer> zwavedeviceids = new ArrayList<>();
        for (ZwaveDeviceActiveTime zdat : zwaveDeviceActiveTimes) {
            zdat.setStatus(IRemoteConstantDefine.DOOR_LOCK_STATUS_MALFUNCTION);
            zwavedeviceids.add(zdat.getZwavedeviceid());
        }

        if (zwavedeviceids.size() != 0) {
            ZWaveDeviceService zds = new ZWaveDeviceService();
            List<ZWaveDevice> zWaveDeviceList = zds.query(zwavedeviceids);
            for (ZWaveDevice zd : zWaveDeviceList) {
                zd.setStatus(IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION);
                writeNotification(zd);
                JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_COBBE_DOOR_LOCK_STATUS,
                        createZwaveDeviceEvent(zd.getZwavedeviceid(), zd.getDeviceid(), zd.getDevicetype()));
            }
        }
    }

    private void writeNotification(ZWaveDevice zWaveDevice) {
        Notification notification = new Notification();
        RemoteService remoteService = new RemoteService();

        Integer phoneuserid = remoteService.queryOwnerId(zWaveDevice.getDeviceid());

        notification.setName(zWaveDevice.getName());
        notification.setDeviceid(zWaveDevice.getDeviceid());
        notification.setReporttime(new Date());
        notification.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
        notification.setNuid(zWaveDevice.getNuid());
        notification.setMajortype(zWaveDevice.getMajortype());
        notification.setMessage(IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION);
        notification.setDevicetype(zWaveDevice.getDevicetype());

        notification.setPhoneuserid(phoneuserid);
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    private ZWaveDeviceEvent createZwaveDeviceEvent(int zwavedeviceid, String deviceid, String deviceType) {
        ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
        zde.setZwavedeviceid(zwavedeviceid);
        zde.setDeviceid(deviceid);
        zde.setEventtime(new Date());
        zde.setDevicetype(deviceType);
        zde.setEventtype(IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION);
        return zde;
    }
}
