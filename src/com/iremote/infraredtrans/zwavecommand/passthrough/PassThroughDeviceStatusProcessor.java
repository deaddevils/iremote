package com.iremote.infraredtrans.zwavecommand.passthrough;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class PassThroughDeviceStatusProcessor extends ZWaveReportBaseProcessor {
    @Override
    protected void parseReport() {
        this.dontsavenotification();
    }

    @Override
    protected void updateDeviceStatus() {
        int status,s = zrb.getCmd()[5] & 0xff;
        if (s == 0x00) {
            status = IRemoteConstantDefine.DEVICE_STATUS_POWER_OFF;
        } else if (s == 0x01) {
            status = IRemoteConstantDefine.DEVICE_STATUS_POWER_ON;
        } else {
            return;
        }
        zrb.getDevice().setStatus(status);
    }

    @Override
    public String getMessagetype() {
        return oldstatus != zrb.getDevice().getStatus()
                ? IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS
                : null;
    }
}
