package com.iremote.event.dsc;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.DSCEvent;

public class PushDSCPartitionWarningStatus extends DSCEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return super.getDeviceid();
    }

    @Override
    public void run() {
        NotificationHelper.pushDSCPartitionWarningStatus(this, getEventtime());
    }
}
