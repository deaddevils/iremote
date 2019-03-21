package com.iremote.task.timertask.processor;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.TimerTask;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.TimerTaskService;
import com.iremote.service.ZWaveSubDeviceService;

public class ChannelDelayAlarmingProcessor implements Runnable {
    private TimerTask timerTask;
    ZWaveSubDevice zWaveSubDevice;
    private Integer timerTaskId;

    public ChannelDelayAlarmingProcessor(Integer timerTaskId) {
        this.timerTaskId = timerTaskId;
    }

    public ChannelDelayAlarmingProcessor(ZWaveSubDevice zWaveSubDevice) {
        this.zWaveSubDevice = zWaveSubDevice;
    }

    private boolean init() {
        TimerTaskService tts = new TimerTaskService();
        if (timerTaskId == null) {
            return false;
        }
        this.timerTask = tts.query(timerTaskId);
        if (timerTask != null) {
            int zwavesubdeviceid = timerTask.getObjid();
            ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
            zWaveSubDevice = zsds.queryByid(zwavesubdeviceid);
            tts.delete(timerTask);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void run() {
        if (init()) {
            return;
        }
        if (zWaveSubDevice == null || zWaveSubDevice.getWarningstatuses() == null) {
            return;
        }
        JSONArray jsonArray = JSONArray.parseArray(zWaveSubDevice.getWarningstatuses());
        if (jsonArray.contains(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_ARARM_DELAY)) {
            jsonArray.removeIf(z -> IRemoteConstantDefine.DSC_ZWAVE_SUB_DEVICE_WARNING_STATUS_ARARM_STATUS.contains(z));
            jsonArray.add(IRemoteConstantDefine.DSC_ZWAVE_SUB_DEIVCE_WARNING_STATUS_NORMAL);
            zWaveSubDevice.setWarningstatuses(jsonArray.toJSONString().replaceAll(" ", ""));

            PushHelper.pushZwaveSubDeviceStatusMessage(zWaveSubDevice);
        }
    }
}
