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

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

public class TwoChannelElectricFenceParameterBatchReportProcessor extends ElectricFenceReportBaseProcessor {
    private static Log log = LogFactory.getLog(TwoChannelElectricFenceParameterBatchReportProcessor.class);
    private Integer[][] values = new Integer[8][1];
    private String type;
    private int channelId;
    private ZWaveSubDevice zWaveSubDevice;
    private boolean needChange;

    @Override
    protected void parseReport() {
        dontsavenotification();

        int length = zrb.getCmd()[8] & 0xff;
        int beginIndex = ((zrb.getCmd()[6] & 0xff) << 8) + (zrb.getCmd()[7] & 0xff);
        channelId = zrb.getCommandvalue().getChannelid();

        int j = 0;
        int index = 11;
        for (int i = beginIndex - 1; i < (beginIndex + length - 1) && i < values.length; i++, j++) {
            int index0 = j * 2 + index;
            int value = ((zrb.getCmd()[index0] & 0xff) << 8) + (zrb.getCmd()[index0 + 1] & 0xff);
            this.values[i][0] = value;
        }
    }

    @Override
    protected void updateDeviceStatus() {
        List<ZWaveSubDevice> zWaveSubDevices = zrb.getDevice().getzWaveSubDevices();
        for (ZWaveSubDevice zWaveSubDevice : zWaveSubDevices) {
            if (channelId == zWaveSubDevice.getChannelid()) {
                this.zWaveSubDevice = zWaveSubDevice;
                String statuses = zWaveSubDevice.getStatuses();
                JSONArray jsonArray = JSONArray.parseArray(statuses);

                for (int i = 1; i < jsonArray.size(); i++) {
                    if ((values[i - 1][0] != null && values[i - 1] != jsonArray.get(i)) || hasRecover) {
                        jsonArray.remove(i);
                        jsonArray.add(i, values[i - 1][0]);
                        needChange = true;
                        type = IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS;
                    }
                }

                zWaveSubDevice.setStatuses(jsonArray.toJSONString());
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
        zde.setDevicetype(zrb.getDevice().getDevicetype());
        zde.setChannel(zWaveSubDevice.getChannelid());
        zde.setWarningstatuses(zWaveSubDevice.getWarningstatuses());
        zde.setEventtime(zrb.getReporttime());
    }

    /*@Override
    protected void updatelastactivetime() {
        if (needChange) {
            zrb.getDevice().setLastactivetime(new Date());
        }
    }*/


   /* @Override
    protected void afterprocess() {
        super.afterprocess();
        if (zWaveSubDevice == null) {
            return;
        }

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
