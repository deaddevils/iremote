package com.iremote.thirdpart.cobbe.event;

import com.iremote.common.GatewayUtils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.common.schedule.ScheduleManager;


public class SendPasswordForZufangProcessor extends RemoteOnlineEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return super.getDeviceid();
    }

    @Override
    public void run() {
        if (!GatewayUtils.isCobbeLock(super.getDeviceid())) {
            return;
        }

        SendDoorLockPasswordForZufangHelper sendDoorLockPassword = new SendDoorLockPasswordForZufangHelper(
                super.getDeviceid(), super.getEventtime(), super.getTaskIndentify());
        
//        sendDoorLockPassword.run();
        ScheduleManager.excutein(0, sendDoorLockPassword);
    }
}