package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;

public class DSCPanicAlarmRestoralProcessor extends DSCUnAlarmProcessorBase {
    @Override
    protected String getMessageType() {
        return IRemoteConstantDefine.WARNING_TYPE_DSC_UN_ALARM_PANIC_KEY_ALARM;
    }

    @Override
    protected Integer getWarningStatus() {
        return IRemoteConstantDefine.DSC_WARNING_TYP_PANIC_ALARM;
    }

    @Override
    protected int getTimerTaskType() {
        return IRemoteConstantDefine.TASK_DSC_P_KEY_UN_ALARM;
    }
}
