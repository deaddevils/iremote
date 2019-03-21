package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;

public class DSCAuxiliaryAlarmRestoralProcessor extends DSCUnAlarmProcessorBase {
    @Override
    protected String getMessageType() {
        return IRemoteConstantDefine.WARNING_TYPE_DSC_UN_ALARM_AUXILIARY_KEY_ALARM;
    }

    @Override
    protected Integer getWarningStatus() {
        return IRemoteConstantDefine.DSC_WARNING_TYP_AUXILIARY_ALARM;
    }

    @Override
    protected int getTimerTaskType() {
        return IRemoteConstantDefine.TASK_DSC_A_KEY_UN_ALARM;
    }
}
