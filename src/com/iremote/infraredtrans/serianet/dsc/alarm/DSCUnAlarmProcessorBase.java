package com.iremote.infraredtrans.serianet.dsc.alarm;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.TimerTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.TimerTaskService;

import java.util.Date;

public abstract class DSCUnAlarmProcessorBase extends DSCAlarmProcessorBase0 {
    @Override
    protected void updateDeviceStatus() {
        if (Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), getWarningStatus())) {
            zWaveDevice.setWarningstatuses(Utils.jsonArrayRemove(zWaveDevice.getWarningstatuses(), getWarningStatus()));
            if (!Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), getDelayWarningStatus())) {
                zWaveDevice.setWarningstatuses(Utils.jsonArrayAppend(zWaveDevice.getWarningstatuses(), getDelayWarningStatus()));
            }
            createTimerTask(zWaveDevice, getTimerTaskType());
        }
    }

    private void createTimerTask(ZWaveDevice zWaveDevice, int taskType) {
        TimerTaskService timerTaskService = new TimerTaskService();
        TimerTask task = timerTaskService.queryByTypeAndObjid(taskType, zWaveDevice.getZwavedeviceid());
        long date = System.currentTimeMillis() + 60 * 60 * 1000;
        if (task == null) {
            task = new TimerTask();
            task.setObjid(zWaveDevice.getZwavedeviceid());
            task.setDeviceid(zWaveDevice.getDeviceid());
            task.setExcutetime(new Date(date));
            task.setExpiretime(new Date(date + 20 * 60 * 1000));
            task.setCreatetime(new Date());
            task.setType(taskType);

            timerTaskService.save(task);
        } else {
            task.setExcutetime(new Date(date));
            task.setExpiretime(new Date(date + 20 * 60 * 1000));
            timerTaskService.update(task);

            ScheduleManager.cancelJob(task.getTimertaskid(), IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK);
        }
        ScheduleManager.excuteWithOutSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, task);
    }

    @Override
    protected String getMessageKey() {
        return "unalarm" + IRemoteConstantDefine.NOTIFICATION_DSC_KEY_ALARM;
    }
}
