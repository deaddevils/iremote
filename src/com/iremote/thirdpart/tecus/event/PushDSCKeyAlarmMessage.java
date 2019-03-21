package com.iremote.thirdpart.tecus.event;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;

public class PushDSCKeyAlarmMessage extends ZWaveDeviceEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        if (getWarningstatus() == null || getWarningstatus() == 0) {
            NotificationHelper.pushmessage(this);
        } else {
            NotificationHelper.pushDSCKeyAlarmNotification(this, getZwavedeviceid());
        }
    }
}
