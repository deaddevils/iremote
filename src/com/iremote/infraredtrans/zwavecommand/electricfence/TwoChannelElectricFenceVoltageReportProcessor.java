package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.alibaba.fastjson.JSONArray;
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

public class TwoChannelElectricFenceVoltageReportProcessor extends ElectricFenceReportBaseProcessor {
    private static Log log = LogFactory.getLog(TwoChannelElectricFenceVoltageReportProcessor.class);
    protected int channelId;
    protected int value;
    protected int typeIndex = 0;
    protected String type;
    protected boolean needChange = false;
    protected ZWaveSubDevice zWaveSubDevice;

    @Override
    protected void parseReport() {
        dontsavenotification();

        int value1 = zrb.getCmd()[8] & 0xff;
        int value2 = zrb.getCmd()[9] & 0xff;

        value = (value1 << 8) + value2;
        channelId = zrb.getCommandvalue().getChannelid();
    }

    @Override
    protected void updateDeviceStatus() {
        List<ZWaveSubDevice> zWaveSubDevices = zrb.getDevice().getzWaveSubDevices();
        for (ZWaveSubDevice zWaveSubDevice : zWaveSubDevices) {
            if (channelId == zWaveSubDevice.getChannelid()) {
                String statuses = zWaveSubDevice.getStatuses();
                JSONArray jsonArray = JSONArray.parseArray(statuses);
                if (jsonArray.getIntValue(typeIndex) != value || hasRecover) {
                    this.zWaveSubDevice = zWaveSubDevice;
                    jsonArray.remove(typeIndex);
                    jsonArray.add(typeIndex, value);
                    zWaveSubDevice.setStatuses(jsonArray.toJSONString());
                    setDeviceOldStatus();

                    needChange = true;
                    type = IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS;
                }
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

    private void setDeviceOldStatus() {
        super.oldstatus = zWaveSubDevice.getStatus();
        super.oldstatuses = zWaveSubDevice.getStatuses();
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
        zde.setDeviceid(zrb.getDevice().getDeviceid());
        zde.setReport(zrb.getReport());
        zde.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
        zde.setSubdeviceid(zWaveSubDevice.getZwavesubdeviceid());
        zde.setStatus(zWaveSubDevice.getStatus());
        zde.setStatuses(zWaveSubDevice.getStatuses());
        zde.setWarningstatuses(zWaveSubDevice.getWarningstatuses());
        zde.setDevicetype(zrb.getDevice().getDevicetype());
        zde.setChannel(zWaveSubDevice.getChannelid());
        zde.setEventtime(zrb.getReporttime());
        zde.setOldstatus(super.oldstatus);
        zde.setOldstatuses(super.oldstatuses);
    }

    @Override
    protected void updatelastactivetime() {
        if (needChange) {
            zrb.getDevice().setLastactivetime(new Date());
        }
    }

    /*@Override
    protected void afterprocess() {
        if (zWaveSubDevice == null) {
            return ;
        }

        super.afterprocess();
        JSONObject json = new JSONObject();
        json.put(IRemoteConstantDefine.CHANNEL_NAME, zWaveSubDevice.getName());
        json.put(IRemoteConstantDefine.CHANNELID, zWaveSubDevice.getChannelid());

        notification.setAppendjson(json);
    }*/

    @Override
    public String getMessagetype() {
        return type;
    }
}
