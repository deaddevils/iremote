package com.iremote.event.dsc;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.SubdeviceStatusEvent;

public class PushChannelStatus extends SubdeviceStatusEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return deviceid;
    }

    @Override
    public void run() {
        NotificationHelper.pushChannelStatus(this,getEventtime());
    }
}
