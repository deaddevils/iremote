package com.iremote.event.device.doorlock;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;

public class PushForbidRemoteOpenDoorLock extends ZWaveDeviceEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return super.getDeviceid();
    }

    @Override
    public void run() {
        PhoneUserService pus = new PhoneUserService();
        PhoneUser phoneUser = pus.query(getPhoneuserid());

        NotificationHelper.pushTypeMessage(this, phoneUser, getName(), getEventtype());
    }
}
