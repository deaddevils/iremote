package com.iremote.event.pushmessage;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.LockPasswordRefreshed;
import com.iremote.common.push.PushMessage;

import java.util.Arrays;

public class PushLockPasswordRefreshed extends LockPasswordRefreshed implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        JSONObject json = new JSONObject();
        json.put("zwavedeviceid", getZwavedeviceid());
        PushMessage.pushWarningMessage(getAlias(), getPlatform(), json);
    }
}
