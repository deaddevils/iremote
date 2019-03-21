package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class DeleteLockUserResultEventProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {

    @SuppressWarnings("unused")
    private static Log log = LogFactory.getLog(DeleteLockUserResultEventProcessor.class);

    @Override
    public void run()
    {
        List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(super.getDeviceid());

        for ( Integer tpid : tidlst )
        {
            if ( tpid == null || tpid == 0 )
                continue ;

            EventtoThirdpart etd = new EventtoThirdpart();

            etd.setThirdpartid(tpid);
            etd.setType(getEventtype());
            etd.setDeviceid(getDeviceid());
            etd.setEventtime(getEventtime());
            etd.setZwavedeviceid(getZwavedeviceid());
            etd.setWarningstatus(super.getWarningstatus());
            etd.setWarningstatuses(super.getWarningstatuses());
            
            appendEvent(etd);

            EventtoThirdpartService svr = new EventtoThirdpartService();
            svr.save(etd);
        }
    }

    private void appendEvent(EventtoThirdpart etd) {
        Integer userCode = getAppendmessage().getInteger("userCode");
        if (userCode != null) {
            etd.setIntparam(userCode);
        }
        JSONObject json = new JSONObject();
        json.put("resultCode", getAppendmessage().getIntValue("resultCode"));
        etd.setObjparam(json.toJSONString());
    }

    @Deprecated
    protected Integer queryThirdpartid()
    {
        ComunityRemoteService crs = new ComunityRemoteService();
        ComunityRemote cr = crs.querybyDeviceid(getDeviceid());
        if ( cr == null )
            return null;
        return cr.getThirdpartid();
    }

    @Override
    public String getTaskKey()
    {
        return getDeviceid();
    }
}
