package com.iremote.thirdpart.cobbe.event;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZwaveDeviceActiveTime;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZwaveDeviceActiveTimeService;

import java.util.Date;
import java.util.List;

public class DoorlockFaultDetectProcessor extends RemoteOnlineEvent implements ITextMessageProcessor {
    public static final int NEXT_ACTIVE_TIME = 26 * 60 * 60 * 1000;

    @Override
    public String getTaskKey() {
        return super.getDeviceid();
    }

    @Override
    public void run() {
        RemoteService rs = new RemoteService();
        Remote remote = rs.getIremotepassword(super.getDeviceid());

        if (!GatewayUtils.isCobbeLock(super.getDeviceid())) {
            return;
        }

        Integer oid = remote.getPhoneuserid();
        if (oid == null || oid == 0) {
//            this.sendSleepCommand();
            return;
        }

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDevices = zds.queryByDevicetypeList(super.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_LIST_WHICH_HAS_TIMER_MALFUNCTION);

        if (zWaveDevices == null || zWaveDevices.size() == 0) {
            return;
        }

        ZwaveDeviceActiveTimeService zdats = new ZwaveDeviceActiveTimeService();

        for (ZWaveDevice zd : zWaveDevices) {
            ZwaveDeviceActiveTime zdat = zdats.query(zd.getZwavedeviceid());

            if (zdat == null) {
                zdat = new ZwaveDeviceActiveTime();
                zdat.setZwavedeviceid(zd.getZwavedeviceid());
                zdat.setCreatetime(new Date());
            } else if (zdat.getStatus() == IRemoteConstantDefine.DOOR_LOCK_STATUS_MALFUNCTION
                    && (zd.getStatus() == null || zd.getStatus() == IRemoteConstantDefine.DEVICE_STATUS_MALFUNCTION)){
                zd.setStatus(IRemoteConstantDefine.DEVICE_STATUS_NORMAL);
                JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_COBBE_DOOR_LOCK_STATUS,
                        createZwaveDeviceEvent(zd.getZwavedeviceid(), zd.getDeviceid(), zd.getDevicetype()));
            }

            zdat.setNextactivetime(new Date(System.currentTimeMillis() + NEXT_ACTIVE_TIME));
            zdat.setLastactivetime(new Date());
            zdat.setStatus(IRemoteConstantDefine.DOOR_LOCK_STATUS_NORMAL);
            zdats.saveOrUpdate(zdat);
        }
    }

    private ZWaveDeviceEvent createZwaveDeviceEvent(int zwavedeviceid, String deviceid, String deviceType) {
        ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
        zde.setZwavedeviceid(zwavedeviceid);
        zde.setDeviceid(deviceid);
        zde.setEventtime(new Date());
        zde.setDevicetype(deviceType);
        zde.setEventtype(IRemoteConstantDefine.WARNING_TYPE_RECOVER);
        return zde;
    }

    private void sendSleepCommand() {
        RemoteEvent re = new RemoteEvent(super.getDeviceid(), super.getEventtime(), super.getTaskIndentify());
        JMSUtil.sendmessage(IRemoteConstantDefine.DOOR_LOCK_SLEEP, re);
    }
}
