package com.iremote.task.timertask.processor;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.TimerTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class DSCKeyDelayUnAlarmProcessor implements Runnable {
    private static Log log = LogFactory.getLog(DSCKeyDelayUnAlarmProcessor.class);
    private Integer timerTaskId;
    private ZWaveDevice zWaveDevice;
    private int delayWarningStatus;

    public DSCKeyDelayUnAlarmProcessor(Integer timerTaskId) {
        this.timerTaskId = timerTaskId;
    }

    public DSCKeyDelayUnAlarmProcessor(ZWaveDevice zWaveDevice, int delayWarningStatus) {
        this.zWaveDevice = zWaveDevice;
        this.delayWarningStatus = delayWarningStatus;
    }

    @Override
    public void run() {
        if (!init()) {
            return;
        }

        if (Utils.isJsonArrayContaints(zWaveDevice.getWarningstatuses(), delayWarningStatus)) {
            String warningStatuses = Utils.jsonArrayRemove(zWaveDevice.getWarningstatuses(), delayWarningStatus);
            zWaveDevice.setWarningstatuses(warningStatuses);
            pushDeviceStatusMessage();
        }
    }

    private void pushDeviceStatusMessage() {
        ZWaveDeviceEvent event = new ZWaveDeviceEvent();
        try {
            PropertyUtils.copyProperties(event, zWaveDevice);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        event.setEventtime(new Date());
        JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS, event);
    }

    private boolean init() {
        if (timerTaskId != null) {
            TimerTaskService service = new TimerTaskService();
            TimerTask task = service.query(timerTaskId);
            if (task == null) {
                return false;
            }
            service.delete(task);
            ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();
            zWaveDevice = zWaveDeviceService.query(task.getObjid());
            // taskType + 95 = delayWarningStatus
            // 6 + 95 = 101
            delayWarningStatus = task.getType() + 95;
        }

        return zWaveDevice != null;
    }

    public int getWarningStatus(int delayWarningStatus) {
        return delayWarningStatus = 95;
    }
}
