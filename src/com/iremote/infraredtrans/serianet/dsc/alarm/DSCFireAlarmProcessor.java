package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;

public class DSCFireAlarmProcessor extends DSCAlarmProcessorBase {
    @Override
    protected String getMessageType() {
        return IRemoteConstantDefine.WARNING_TYPE_DSC_FIRE_KEY_ALARM;
    }

    @Override
    protected Integer getWarningStatus() {
        return IRemoteConstantDefine.DSC_WARNING_TYP_FIRE_ALARM;
    }

    @Override
    protected int getTimerTaskType() {
        return IRemoteConstantDefine.TASK_DSC_F_KEY_UN_ALARM;
    }
}
