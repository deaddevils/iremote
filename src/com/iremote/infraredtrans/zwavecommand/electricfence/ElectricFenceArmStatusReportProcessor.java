package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.iremote.common.IRemoteConstantDefine;

public class ElectricFenceArmStatusReportProcessor extends ElectricFenceReportBaseProcessor {
    private String type;
    private String message;

    @Override
    protected void updateDeviceStatus() {
        int status = zrb.getCmd()[2] & 0xff;

        if (zrb.getDevice().getStatus() != status || hasRecover) {
            getMessage(status);
            type = IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
            zrb.getDevice().setStatus(status);
        }
    }

    private void getMessage(int status) {
        if (status == 255) {
            message = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_ARM;
        } else {
            message = IRemoteConstantDefine.DEVICE_OPERATION_E_FENCE_DIS_ARM;
        }
    }

    @Override
    protected void afterprocess() {
        notification.setMessage(message);
    }

    @Override
    public String getMessagetype() {
        return type;
    }
}
