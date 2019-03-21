package com.iremote.infraredtrans.zwavecommand.electricfence;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;

public class ElectricFenceParameterBatchReportProcessor extends ElectricFenceReportBaseProcessor {
    private Integer[][] values = new Integer[8][1];
    private String type;

    @Override
    protected void parseReport() {
        dontsavenotification();

        int length = zrb.getCmd()[4] & 0xff;
        int beginIndex = ((zrb.getCmd()[2] & 0xff) << 8) + (zrb.getCmd()[3] & 0xff);

        int j = 0;
        int index = 7;
        for (int i = beginIndex - 1; i < (beginIndex + length - 1) && i < values.length; i++, j++) {
            int index0 = j * 2 + index;
            int value = ((zrb.getCmd()[index0] & 0xff) << 8) + (zrb.getCmd()[index0 + 1] & 0xff);
            this.values[i][0] = value;
        }
    }

    @Override
    protected void updateDeviceStatus() {
        String statuses = zrb.getDevice().getStatuses();
        JSONArray jsonArray = JSONArray.parseArray(statuses);
        for (int i = 1; i < jsonArray.size(); i++) {
            if ((values[i - 1][0] != null && values[i - 1] != jsonArray.get(i)) || hasRecover) {
                jsonArray.remove(i);
                jsonArray.add(i, values[i - 1][0]);
                type = IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
            }
        }

        zrb.getDevice().setStatuses(jsonArray.toJSONString());
    }

    @Override
    public String getMessagetype() {
        return type;
    }
}
