package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.ZWaveSubDeviceService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public abstract class ZWaveSubReportBaseProcessor extends ZWaveReportBaseProcessor {
    private static Log log = LogFactory.getLog(ZWaveSubReportBaseProcessor.class);

    protected boolean hasRecover;
    protected ZWaveSubDevice zWaveSubDevice;

    protected abstract int getChannelId();

    @Override
    protected void setOldStatuses() {
        ZWaveSubDeviceService service = new ZWaveSubDeviceService();
        zWaveSubDevice = service.queryByZwavedeviceidAndChannelid(zrb.getDevice().getZwavedeviceid(), getChannelId());
        if (zWaveSubDevice != null) {
            super.oldstatus = zWaveSubDevice.getStatus();
        }
    }

    @Override
    protected void pushMessage() {
        if (zWaveSubDevice == null) {
            super.pushMessage();
            return;
        }

        if (!this.pushmessage
                || zrb.getDevice().getEnablestatus() == IRemoteConstantDefine.DEVICE_ENABLE_STATUS_DISENABLE)
            return;

        String mt = this.getMessagetype();
        if (mt == null || mt.length() == 0)
            return;

        ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
        zde.setName(zrb.getDevice().getName() + "(" + zWaveSubDevice.getName() + ")");
        zde.setEventtype(mt);
        zde.setReport(zrb.getReport());
        zde.setDeviceid(zrb.getDevice().getDeviceid());
        zde.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
        zde.setSubdeviceid(zWaveSubDevice.getZwavesubdeviceid());
        zde.setEnablestatus(zWaveSubDevice.getEnablestatus());
        zde.setStatus(zWaveSubDevice.getStatus());
        zde.setStatuses(zWaveSubDevice.getStatuses());
        zde.setDevicetype(zrb.getDevice().getDevicetype());
        zde.setWarningstatus(super.warningstatus);
        zde.setWarningstatuses(zWaveSubDevice.getWarningstatuses());
        zde.setEventtime(zrb.getReporttime());
        zde.setChannel(getChannelId());
        zde.setTaskIndentify(zrb.getReportid());
        zde.setAppendmessage(getAppendMessage());
        zde.setOldstatus(this.oldstatus);
        zde.setOldstatuses(this.oldstatuses);
        zde.setOldshadowstatus(oldshadowstatus);
        zde.setApptokenid(zrb.getApptokenid());

        processMessage(zde);
        JMSUtil.sendmessage(mt, zde);
    }

    @Override
    protected void recover() {
        if (zrb.getDevice().getStatus() == null || zrb.getDevice().getStatus() != -1)// not a malfunction device
            return;
        hasRecover = true;

        Notification n = new Notification(zrb.getDevice(), IRemoteConstantDefine.WARNING_TYPE_RECOVER);
        n.setReporttime(new Date());
        n.setPhoneuserid(zrb.getPhoneuserid());
        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

        zrb.getDevice().setStatus(Utils.getDeviceDefaultStatus(zrb.getDevice().getDevicetype()));

        JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_RECOVER, new ZWaveDeviceEvent(zrb.getDevice().getZwavedeviceid(), zrb.getDevice().getDeviceid(), zrb.getDevice().getNuid(), n.getMessage(), n.getReporttime(), zrb.getReportid()));
    }

    @Override
    protected void afterprocess() {
        ZWaveDeviceEvent event = new ZWaveDeviceEvent();
        try {
            PropertyUtils.copyProperties(event, zrb.getDevice());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
        event.setEventtype(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS);
        event.setEventtime(zrb.getReporttime());
        event.setReport(zrb.getReport());
        event.setAppendmessage(getAppendMessage());
        event.setDevicetype(zrb.getDevice().getDevicetype());
        event.setTaskIndentify(zrb.getReportid());
        event.setChannel(zrb.getCommandvalue().getChannelid());
        event.setApptokenid(zrb.getApptokenid());
        event.setWarningstatus(this.warningstatus);
        
        JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, event);
    }
}
