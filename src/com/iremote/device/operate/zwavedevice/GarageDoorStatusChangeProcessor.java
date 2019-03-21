package com.iremote.device.operate.zwavedevice;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class GarageDoorStatusChangeProcessor extends ZWaveReportBaseProcessor {
    private int devicestauts;
    private int warning;
    private String message;
    private int status;

    @Override
    protected void parseReport() {
        super.parseReport();

        byte[] cmd = zrb.getCmd();
        status = cmd[2] & 0xff;
        if (status == 0xfd) {
            devicestauts = IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_NOT_INIT;
        } else if (status == 0xfe) {
            devicestauts = IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_OPENING;
            warning = IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_OPEN;
            message = IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN;
        } else if (status == 0xfc) {
            devicestauts = IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_CLOSING;
            message = IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE;
        } else if (status == 0x00) {
            devicestauts = 0;
            message = IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE;
        } else if (status == 0xff) {
            devicestauts = IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_OPEN;
            warning = IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_OPEN;
            message = IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN;
        } else {
            devicestauts = -1;
            warning = devicestauts;
            message = IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION;
        }
    }

    @Override
    protected void updateDeviceStatus() {
        if (zrb.getDevice().getStatus() == devicestauts) {
            dontpusmessage();
            dontsavenotification();
            return;
        }

        zrb.getDevice().setShadowstatus(devicestauts);
        zrb.getDevice().setStatus(devicestauts);
        if (devicestauts == IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_OPENING
                || devicestauts == IRemoteConstantDefine.DEVICE_STATUS_GARAGE_DOOR_OPEN) {
            if (!zdah.hasArmedByUserSetting())
                return;
            if (warning != 0) {
                if (Utils.isJsonArrayContaints(zrb.getDevice().getWarningstatuses(), warning)) {
                    warning = 0;
                    return;
                }

                if (!zdah.hasSetDelayAlarm()) {
                    super.appendWarningstatus(warning);
                } else {
                    createTimerTask(IRemoteConstantDefine.TASK_SENSOR_DELAY_ALARM, zrb,
                            IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN_DELAY_WARNING, warning);
                }
            }
        }
    }

    @Override
    protected void afterprocess() {
        String operate = TlvWrap.readString(zrb.getReport(), TagDefine.TAG_OPERATOR, TagDefine.TAG_HEAD_LENGTH);
        notification.setAppendmessage(operate);
    }

    @Override
    public String getMessagetype() {
        if (message != null) {
            return message;
        }
        return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
    }

    @Override
    protected IZwaveReportCache createCacheReport() {
        return new ZwaveReportCache(zrb.getCommandvalue(), IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, status);
    }
}
