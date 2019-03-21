package com.iremote.task.timertask.processor;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.*;
import com.iremote.service.*;

public class CameraDelayAccordingHelper extends BaseDelayAlarmingHelper {
    private Camera camera;

    public CameraDelayAccordingHelper(Camera camera) {
        this.camera = camera;
        super.init(camera.getDeviceid());
    }

    public boolean hasArmed() {
        return camera != null && super.hasArmed();
    }

    public boolean hasCapabilityAlarmWhenHomeArmed() {
        DeviceCapabilityService dcs = new DeviceCapabilityService();
        DeviceCapability dc = dcs.queryByCamera(camera, IRemoteConstantDefine.DEVICE_CAPABILITY_ALARM_WHEN_HOME_ARMED);
        return dc != null;
    }

    public boolean hasArmedByUserSetting() {
        if (!hasPhoneuser || !PhoneUserHelper.hasArmFunction(phoneUser)) {
            return false;
        }

        if (camera.getArmstatus() == IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM){
            armStatus = IRemoteConstantDefine.PHONEUSER_ARM_STATUS_DISARM;
            return false;
        }

        return hasCapabilityAlarmWhenHomeArmed() ? isOutHomeArm() : hasArmed();
    }

    public boolean hasSetDelayAlarm() {
        DeviceCapabilityService dsc = new DeviceCapabilityService();
        DeviceCapability dc = dsc.queryByCamera(camera, IRemoteConstantDefine.DEVICE_CAPABILITY_DELAY_TIME_CAPABILITCODE);
        if (dc == null || Integer.valueOf(dc.getCapabilityvalue()) == 0) {
            return false;
        }
        deviceDelayTime = Integer.valueOf(dc.getCapabilityvalue());
        return true;
    }

    protected boolean deviceHasPartition() {
        boolean hasPartition = camera.getPartitionid() != null;
        if (hasPartition) {
            super.setPartitionArmStatus(camera.getPartitionid());
        }
        return hasPartition;
    }
}
