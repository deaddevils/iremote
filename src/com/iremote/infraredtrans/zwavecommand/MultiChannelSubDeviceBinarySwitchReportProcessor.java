package com.iremote.infraredtrans.zwavecommand;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;

public class MultiChannelSubDeviceBinarySwitchReportProcessor extends ZWaveSubReportBaseProcessor {
    private String message;

    public MultiChannelSubDeviceBinarySwitchReportProcessor() {
        super();
        super.dontsavenotification();
    }

    @Override
    protected int getChannelId() {
        return zrb.getCommandvalue().getChannelid();
    }

    protected void updateDeviceStatus()
    {
        if (zrb.getPhoneuser() != null) {
            DeviceHelper.createSubDevice(zrb.getDevice(), zrb.getRemote().getPlatform(), zrb.getPhoneuser().getLanguage());
            if (zWaveSubDevice == null) {
                zWaveSubDevice = DeviceHelper.findZWaveSubDevice(zrb.getDevice().getzWaveSubDevices(), getChannelId());
            }
        }

        int status = zrb.getCommandvalue().getValue() ;

        if ( status != 0xff && status != 0x00 )
            return;

        if (zWaveSubDevice.getStatus() == null || status != zWaveSubDevice.getStatus()) {
            message = IRemoteConstantDefine.NOTIFICATION_SUB_DEVICE_STATUS;
            zWaveSubDevice.setStatus(status);
        }
    }

    @Override
    public String getMessagetype() {
        return message;
    }
}