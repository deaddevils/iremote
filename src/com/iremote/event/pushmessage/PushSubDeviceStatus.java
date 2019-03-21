package com.iremote.event.pushmessage;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.Utils;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class PushSubDeviceStatus extends ZWaveDeviceEvent implements ITextMessageProcessor {

    @Override
    public void run()
    {
        setWarningstatus(Utils.getJsonArrayLastValue(getWarningstatuses()));
        NotificationHelper.pushSubDeviceStatus(this, getEventtime() , TlvWrap.readString(getReport(), 12, 4));
    }

    @Override
    public String getTaskKey()
    {
        return getDeviceid();
    }

}
