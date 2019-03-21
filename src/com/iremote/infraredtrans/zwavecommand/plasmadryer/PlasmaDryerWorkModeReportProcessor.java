package com.iremote.infraredtrans.zwavecommand.plasmadryer;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import org.apache.commons.lang.StringUtils;

public class PlasmaDryerWorkModeReportProcessor extends ZWaveReportBaseProcessor {
    private String messageType = IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;

    public PlasmaDryerWorkModeReportProcessor() {
        super();
        super.dontsavenotification();
    }

    @Override
    protected void updateDeviceStatus() {
        String statuses = zrb.getDevice().getStatuses();
        JSONArray jsonArray = JSONArray.parseArray(statuses);
        setStatuses(jsonArray);

        String jsonStatuses = jsonArray.toJSONString();
        if (StringUtils.equals(zrb.getDevice().getStatuses(), jsonStatuses)) {
            messageType = null;
        } else {
            zrb.getDevice().setStatuses(jsonStatuses);
        }
    }

    protected void setStatuses(JSONArray jsonArray) {
        jsonArray.set(0, zrb.getCmd()[4] & 0xff);
        jsonArray.set(1, zrb.getCmd()[5] & 0xff);
    }

    @Override
    public String getMessagetype() {
        return messageType;
    }
}
