package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.TimerTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.TimerTaskService;

public abstract class DSCAlarmProcessorBase extends DSCAlarmProcessorBase0 {
    @Override
    protected void updateDeviceStatus() {
        if (!Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), getWarningStatus())) {
            newWarning = true;
            cancelTimerTask(zWaveDevice, getTimerTaskType());
            removeDelayWarningStatus();
            zWaveDevice.setWarningstatuses(Utils.jsonArrayAppend(zWaveDevice.getWarningstatuses(), getWarningStatus()));
        }
    }

    private void removeDelayWarningStatus() {
        zWaveDevice.setWarningstatuses(Utils.jsonArrayRemove(zWaveDevice.getWarningstatuses(), getDelayWarningStatus()));
    }

    private void cancelTimerTask(ZWaveDevice zWaveDevice, int timerTaskType) {
        if (Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), getDelayWarningStatus())) {
            TimerTaskService tts = new TimerTaskService();
            TimerTask timerTask = tts.queryByTypeAndObjid(timerTaskType, zWaveDevice.getZwavedeviceid());
            if (timerTask != null){
                ScheduleManager.cancelJob(timerTask.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
                tts.delete(timerTask);
            }
        }
    }

    @Override
    protected String getMessageKey() {
        return IRemoteConstantDefine.NOTIFICATION_DSC_KEY_ALARM;
    }
}
