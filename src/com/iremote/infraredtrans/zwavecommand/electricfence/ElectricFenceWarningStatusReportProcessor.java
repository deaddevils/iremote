package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.jms.vo.ZWaveDeviceStatusChange;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class ElectricFenceWarningStatusReportProcessor extends ElectricFenceReportBaseProcessor {
    private static Log log = LogFactory.getLog(ElectricFenceWarningStatusReportProcessor.class);

    private boolean needProcess = false;
    private ZWaveSubDevice zWaveSubDevice;

    private String[] types = new String[]{
            null,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_DIS_CONNECTION,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_SHORT_CIRCUIT,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_TAMPLER,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_INSTRUSION,
            null,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_CONTACT,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_SAME_LINE_SHORT_CIRCUIT,
            IRemoteConstantDefine.NOTIFICATION_TYPE_ELECTRIC_FENCE_POWER_OFF,
    };

    @Override
    protected void updateDeviceStatus() {
        int warningstatus = zrb.getCmd()[8] & 0xff;
        if (types[warningstatus] == null) {
            super.dontpusmessage();
            super.dontsavenotification();
        }

        ZWaveDevice zWaveDevice = zrb.getDevice();

        if (warningstatus < types.length && types[warningstatus] != null) {
            if (IRemoteConstantDefine.DEVICE_TYPE_ELECTRIC_FENCE.equals(zWaveDevice.getDevicetype())) {
                super.appendWarningstatus(warningstatus);
            } else {
                int channel = zrb.getCmd()[7] & 0xff;
                for (ZWaveSubDevice zWaveSubDevice : zWaveDevice.getzWaveSubDevices()) {
                    if (zWaveSubDevice.getChannelid() == channel) {
                        this.zWaveSubDevice = zWaveSubDevice;
                        needProcess = true;
                        appendWarningstatus(warningstatus);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void appendWarningstatus(int warningstatus) {
        if (!needProcess) {
            super.appendWarningstatus(warningstatus);
            return;
        }

        if (Utils.isJsonArrayContaints(zWaveSubDevice.getWarningstatuses(), warningstatus)) {
            super.eclipsed = true;
            return ;
        }
        super.warningstatus = warningstatus;
        zWaveSubDevice.setWarningstatuses(Utils.jsonArrayAppend(zWaveSubDevice.getWarningstatuses(), warningstatus));
    }

    @Override
    protected void processMessage(ZWaveDeviceStatusChange zde) {
        super.processMessage(zde);
        if (!needProcess) {
            return;
        }
        if (hasRecover) {
            zde.setReport(zrb.getReport());
            zde.setOldstatus(zrb.getDevice().getStatus());
            JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, zde);
        }

        ZWaveDevice zWaveDevice = new ZWaveDevice();
        try {
            PropertyUtils.copyProperties(zde, zWaveDevice);
        } catch (Throwable t) {
            log.error(t.getMessage() , t);
        }

        zde.setName(zrb.getDevice().getName()+"("+zWaveSubDevice.getName()+")");
        zde.setEventtype(getMessagetype());
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
    }

    @Override
    protected void afterprocess() {
        super.afterprocess();
        if (!needProcess) {
            return;
        }

        JSONObject json = new JSONObject();
        json.put("channelname", zWaveSubDevice.getName());
        json.put("channelid", zWaveSubDevice.getChannelid());

        notification.setAppendjson(json);
    }

    @Override
    public String getMessagetype() {
        if (warningstatus != null && warningstatus < types.length) {
            return types[warningstatus];
        }
        return null;
    }
}
