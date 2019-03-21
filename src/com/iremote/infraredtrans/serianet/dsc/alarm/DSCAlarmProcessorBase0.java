package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.Notification;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.serianet.SeriaNetReportBaseProcessor;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public abstract class DSCAlarmProcessorBase0 extends SeriaNetReportBaseProcessor {
    private static Log log = LogFactory.getLog(DSCAlarmProcessorBase0.class);
    protected ZWaveDevice zWaveDevice;
    protected Integer phoneUserId;
    protected boolean newWarning;

    @Override
    protected void init() {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDeviceList = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zWaveDeviceList == null || zWaveDeviceList.size() == 0) {
            initSucceed = false;
            return;
        }

        zWaveDevice = zWaveDeviceList.get(0);
        RemoteService remoteService = new RemoteService();
        phoneUserId = remoteService.queryOwnerId(zWaveDevice.getDeviceid());
    }

    @Override
    protected void pushMessage() {
        if (StringUtils.isBlank(getMessageType()))
            return;

        ZWaveDeviceStatusChange zde = new ZWaveDeviceStatusChange();
        try {
            PropertyUtils.copyProperties(zde, zWaveDevice);
        } catch (Throwable t) {
            log.error(t.getMessage() , t);
        }

        zde.setWarningstatus(getWarningStatus0());
        zde.setEventtime(new Date());

        zde.setEventtype(getMessageType());
        zde.setDevicetype(zWaveDevice.getDevicetype());
        JMSUtil.sendmessage(getMessageKey(), zde);
    }

    private Integer getWarningStatus0(){
        return newWarning ? getWarningStatus() : 0;
    }

    @Override
    protected void writeLog() {
        Notification notification = new Notification();

        notification.setMessage(this.getMessageType());
        notification.setDeviceid(zWaveDevice.getDeviceid());
        notification.setMajortype(IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE);
        notification.setDevicetype(zWaveDevice.getDevicetype());
        notification.setNuid(zWaveDevice.getNuid());
        notification.setZwavedeviceid(zWaveDevice.getZwavedeviceid());
        notification.setReporttime(new Date());
        notification.setName(zWaveDevice.getName());
        notification.setPhoneuserid(phoneUserId);
        notification.setWarningstatus(getWarningStatus0());

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
    }

    protected Integer getDelayWarningStatus() {
        if (getWarningStatus() == null) {
            return null;
        }
        return getWarningStatus() + 100;
    }
    
    protected abstract String getMessageType();
    protected abstract Integer getWarningStatus();
    protected abstract String getMessageKey();
    protected abstract int getTimerTaskType();
}
