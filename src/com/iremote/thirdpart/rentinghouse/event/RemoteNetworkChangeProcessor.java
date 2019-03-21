package com.iremote.thirdpart.rentinghouse.event;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteNetworkChangedEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

import java.util.List;

public class RemoteNetworkChangeProcessor extends RemoteNetworkChangedEvent implements ITextMessageProcessor {
    private int zWaveDeviceId;
    private EventtoThirdpartService service;

    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(super.getDeviceid());
        if (!init()) {
            return;
        }
        for ( Integer tpid : tidlst )
        {
            if ( tpid == null || tpid == 0 )
                continue ;

            EventtoThirdpart etd = new EventtoThirdpart();

            etd.setThirdpartid(tpid);
            etd.setType(IRemoteConstantDefine.MESSAGE_TYPE_LOCK_NETWORK_INFO);
            etd.setDeviceid(getDeviceid());
            etd.setEventtime(getEventtime());
            etd.setZwavedeviceid(zWaveDeviceId);
            etd.setObjparam(getNetworkInfo());

            service.save(etd);
        }
    }

    private boolean init() {
        Integer id = DeviceHelper.getUniqueZWaveDeviceId(getDeviceid());
        if (id == null) {
            return false;
        }
        zWaveDeviceId = id;
        service = new EventtoThirdpartService();
        return true;
    }

    private String getNetworkInfo() {
        JSONObject json = new JSONObject();
        json.put("network", getNetwork());
        json.put("networkintensity", getNetworkIntensity());
        return json.toJSONString();
    }
}
