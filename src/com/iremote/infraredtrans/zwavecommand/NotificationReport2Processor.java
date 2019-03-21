package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;

public class NotificationReport2Processor extends ZWaveReportBaseProcessor {
    private String message;

    @Override
    protected void parseReport() {
        int type = zrb.getCmd()[6] & 0xff;
        if (type == 1) {
            message = IRemoteConstantDefine.WARNING_TYPE_SMOKE;
        } else if (type == 2) {
            message = IRemoteConstantDefine.WARNING_TYPE_GAS_LEAK;
        }
    }

    @Override
    protected void updateDeviceStatus() {
        zrb.getDevice().setShadowstatus(0xff);
        zrb.getDevice().setStatus(0xff);
        super.appendWarningstatus(0xff);
    }

    @Override
    public String getMessagetype() {
        if (super.warningstatus != null) {
            return message;
        }
        return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
    }
}
