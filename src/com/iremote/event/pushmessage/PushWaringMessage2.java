package com.iremote.event.pushmessage;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushWaringMessage2 extends ZWaveDeviceEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        NotificationHelper.pushmessage(this, getZwavedeviceid(), null , getEventtype());
    }
}
