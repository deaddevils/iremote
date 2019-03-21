package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;

public class DSCAuxiliaryAlarmProcessor extends DSCAlarmProcessorBase {
    @Override
    protected String getMessageType() {
        return IRemoteConstantDefine.WARNING_TYPE_DSC_AUXILIARY_KEY_ALARM;
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
