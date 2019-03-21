package com.iremote.infraredtrans.zwavecommand.plasmadryer;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class PlasmaDryerStatusReportProcessor extends ZWaveReportBaseProcessor {
    @Override
    protected void updateDeviceStatus() {
        int status = zrb.getCommandvalue().getValue() ;

        if ( status != 0xff && status != 0x00 )
            return;
        if ( oldstatus != null && oldstatus == status )
            this.dontpusmessage();
        zrb.getDevice().setStatus(status);
        zrb.getDevice().setShadowstatus(status);
    }

    @Override
    public String getMessagetype() {
        return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
    }
}
