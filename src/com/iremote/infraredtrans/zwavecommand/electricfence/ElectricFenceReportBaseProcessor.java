package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.Notification;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

import java.util.Date;

public abstract class ElectricFenceReportBaseProcessor extends ZWaveReportBaseProcessor {
    protected boolean hasRecover = false;
    @Override
    protected void recover() {
        if ( zrb.getDevice().getStatus() == null || zrb.getDevice().getStatus() != -1 )// not a malfunction device
            return ;
        hasRecover = true;

        Notification n = new Notification(zrb.getDevice() , IRemoteConstantDefine.WARNING_TYPE_RECOVER);
        n.setReporttime(new Date());
        n.setPhoneuserid(zrb.getPhoneuserid());

        zrb.getDevice().setStatus(Utils.getDeviceDefaultStatus(zrb.getDevice().getDevicetype()));

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

        JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_RECOVER, new ZWaveDeviceEvent(zrb.getDevice().getZwavedeviceid() , zrb.getDevice().getDeviceid() , zrb.getDevice().getNuid() ,n.getMessage(), n.getReporttime() , zrb.getReportid()));
    }
}
