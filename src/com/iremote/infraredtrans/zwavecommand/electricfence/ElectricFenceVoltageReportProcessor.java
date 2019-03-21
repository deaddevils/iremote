package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;

public class ElectricFenceVoltageReportProcessor extends ElectricFenceReportBaseProcessor {
    protected int value;
    protected int typeIndex = 0;
    protected String type;

    @Override
    protected void parseReport() {
        dontsavenotification();

        int value1 = zrb.getCmd()[4] & 0xff;
        int value2 = zrb.getCmd()[5] & 0xff;

        value = (value1 << 8) + value2;
    }

    @Override
    protected void updateDeviceStatus() {
        String statuses = zrb.getDevice().getStatuses();
        JSONArray jsonArray = JSONArray.parseArray(statuses);
        if (jsonArray.getIntValue(typeIndex) != value || hasRecover) {
            jsonArray.remove(typeIndex);
            jsonArray.add(typeIndex, value);
            zrb.getDevice().setStatuses(jsonArray.toJSONString());
            type = IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
        }
    }

    @Override
    public String getMessagetype() {
        return type;
    }
}
