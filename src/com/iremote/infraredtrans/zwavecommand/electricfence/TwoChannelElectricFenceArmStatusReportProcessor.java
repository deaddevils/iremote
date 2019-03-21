package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class TwoChannelElectricFenceArmStatusReportProcessor extends ElectricFenceReportBaseProcessor {
    private static Log log = LogFactory.getLog(TwoChannelElectricFenceArmStatusReportProcessor.class);
    private String type;
    private ZWaveSubDevice zWaveSubDevice;
    private int channelId;
    private int status;
    private boolean changeLastUpdateTime = false;
    private String message;

    @Override
    protected void parseReport() {
        channelId = zrb.getCommandvalue().getChannelid();
        status = zrb.getCmd()[6] & 0xff;
    }

    @Override
    protected void updateDeviceStatus() {

        List<ZWaveSubDevice> zWaveSubDevices = zrb.getDevice().getzWaveSubDevices();
        for (ZWaveSubDevice zWaveSubDevice : zWaveSubDevices) {
            if (zWaveSubDevice.getChannelid() == channelId && (!Integer.valueOf(status).equals(zWaveSubDevice.getStatus()) || hasRecover)) {
                getMessage(status);
                this.zWaveSubDevice = zWaveSubDevice;
                setDeviceOldStatus();
                type = IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS;
                zWaveSubDevice.setStatus(status);
                changeLastUpdateTime = true;
                break;
            }
        }

        sendDeviceStatus();
    }

    private void sendDeviceStatus() {
        //sent when two channel electric fence recover for malfunction
        if (hasRecover && zWaveSubDevice != null) {
            ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
            try {
                PropertyUtils.copyProperties(zde, zWaveSubDevice.getZwavedevice());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            zde.setReport(zrb.getReport());
            zde.setEventtime(new Date());

            JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, zde);
        }
    }

    private void getMessage(int status) {
        if (status == 255) {
            message = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_ARM;
        } else {
            message = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_DIS_ARM;
        }
    }

    protected void setDeviceOldStatus() {
        super.oldstatus = zWaveSubDevice.getStatus();
        super.oldstatuses = zWaveSubDevice.getStatuses();
    }

    @Override
    protected void updatelastactivetime() {
        if (changeLastUpdateTime) {
            zrb.getDevice().setLastactivetime(new Date());
        }
    }

    @Override
    protected void processMessage(ZWaveDeviceStatusChange zde) {
        super.processMessage(zde);
        ZWaveDevice zd = new ZWaveDevice();
        try {
            PropertyUtils.copyProperties(zde, zd);
        } catch (Throwable t) {
            log.error(t.getMessage() , t);
        }

        zde.setEventtype(type);
        zde.setReport(zrb.getReport());
        zde.setDeviceid(zrb.getDevice().getDeviceid());
        zde.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
        zde.setSubdeviceid(zWaveSubDevice.getZwavesubdeviceid());
        zde.setStatus(zWaveSubDevice.getStatus());
        zde.setStatuses(zWaveSubDevice.getStatuses());
        zde.setWarningstatuses(zWaveSubDevice.getWarningstatuses());
        zde.setEventtime(zrb.getReporttime());
        zde.setDevicetype(zrb.getDevice().getDevicetype());
        zde.setChannel(zWaveSubDevice.getChannelid());
        zde.setOldstatus(super.oldstatus);
        zde.setOldstatuses(super.oldstatuses);
    }

    @Override
    protected void afterprocess() {
        super.afterprocess();
        notification.setMessage(message);
        if (zWaveSubDevice == null) {
            return;
        }

        JSONObject json = new JSONObject();
        json.put(IRemoteConstantDefine.CHANNEL_NAME, zWaveSubDevice.getName());
        json.put(IRemoteConstantDefine.CHANNELID, zWaveSubDevice.getChannelid());

        notification.setAppendjson(json);
    }

    @Override
    public String getMessagetype() {
        return type;
    }
}
