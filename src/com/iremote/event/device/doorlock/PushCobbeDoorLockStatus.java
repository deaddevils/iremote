package com.iremote.event.device.doorlock;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushCobbeDoorLockStatus extends ZWaveDeviceEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return super.getDeviceid();
    }

    @Override
    public void run() {
        JSONObject json = new JSONObject();
        json.put("type", "warning");
        json.put("message", super.getEventtype());
        json.put("deviceid", super.getDeviceid());
        json.put("zwavedeviceid", super.getZwavedeviceid());
        json.put("majortype", super.getMajortype());
        json.put("devicetype", super.getDevicetype());
        json.put("reporttime", super.getEventtime());

        NotificationHelper.pushmessage(this, json);
    }
}
