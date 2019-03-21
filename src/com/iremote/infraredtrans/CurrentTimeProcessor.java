package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;

import cn.com.isurpass.iremote.webconsole.user.Login;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class CurrentTimeProcessor implements IRemoteRequestProcessor {

    @Override
    public CommandTlv process(byte[] request, IConnectionContext nbc)
            throws BufferOverflowException, IOException {

        CommandTlv ct = null;
        Remoter r = (Remoter) nbc.getAttachment();

        if (r.getNextLongMsgHeartBeatTime() <= System.currentTimeMillis()){
            ct = LoginProcessor.createTime();
            r.setNextLongMsgHeartBeatTime(System.currentTimeMillis() + IRemoteConstantDefine.HEART_BEAT_LONG_MSG_TIME);
        } else {
            ct = new CommandTlv(103, 4);
        }

        if (r.isHaslogin()) {
            if (r.getHeartBeatPushTagCount() > 0) {
                ct.addUnit(new TlvIntUnit(1, 0, 1));
                r.setHeartBeatPushTagCount(r.getHeartBeatPushTagCount() - 1);
            }
        }

        return ct;
        }

}
