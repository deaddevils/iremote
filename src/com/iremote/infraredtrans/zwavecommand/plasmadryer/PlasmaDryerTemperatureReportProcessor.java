package com.iremote.infraredtrans.zwavecommand.plasmadryer;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.Temperature;
import com.iremote.common.Utils;

public class PlasmaDryerTemperatureReportProcessor extends PlasmaDryerWorkModeReportProcessor {
    @Override
    protected void setStatuses(JSONArray jsonArray) {
        Temperature temperature = new Temperature(zrb.getCmd()).calculate();
        jsonArray.set(2, temperature.getC());
        jsonArray.set(3, temperature.getF());
    }
}
