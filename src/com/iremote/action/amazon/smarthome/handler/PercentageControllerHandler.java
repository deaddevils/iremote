package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.common.JSONUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

import java.util.HashMap;
import java.util.Map;

public class PercentageControllerHandler extends OperationHandler {
    private static final String PERCENTAGE = "directive.payload.percentage";
    private static final String PERCENTAGEDELTA = "directive.payload.percentageDelta";
    private static final String OPERATION_TYPE_ADJUST_PERCENTAGE = "AdjustPercentage";
    private static final String OPERATION_TYPE_SET_PERCENTAGE = "SetPercentage";
    public static final int MAX_PERCENTAGE_VALUE = 99;
    private String value = null;
    @Override
    public void process(String name, int zwavedeviceid, int channelid, JSONObject json,PhoneUser phoneuser) {
        String operationtype = JSONUtil.getString(json, NAME_PATH);
        if (OPERATION_TYPE_SET_PERCENTAGE.equals(operationtype)) {
            value = JSONUtil.getString(json, PERCENTAGE);
        } else if (OPERATION_TYPE_ADJUST_PERCENTAGE.equals(operationtype)) {
            String value0 = JSONUtil.getString(json, PERCENTAGEDELTA);
            value = getValue(zwavedeviceid, value0);
        } else {
            return;
        }
        if (value == null) {
            return ;
        }
        if (Integer.valueOf(value) > MAX_PERCENTAGE_VALUE){
            value = "99";
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("operation", "open");
        jsonObject.put("value", value);
        String cmd = JSON.toJSONString(jsonObject);
        DeviceOperationAction doa = new DeviceOperationAction();
        doa.setPhoneuser(phoneuser);
        doa.setCommand(cmd);
        doa.setZwavedeviceid(zwavedeviceid);
        doa.execute();
        resultCode = doa.getResultCode();
    }

    @Override
    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        super.setProperty(directive);
        super.property.setValue(value);
        super.property.setName("percentage");
    }

    private String getValue(int id, String value0) {
        ZWaveDeviceService zwds = new ZWaveDeviceService();
        ZWaveDevice zd = zwds.query(id);
        Integer value = Integer.valueOf(value0);
        Integer nowvalue = zd.getStatus();
        int i = value + nowvalue;
        if (i > 99) {
            return "99";
        }
        if (i < 0) {
            return "0";
        }
        return String.valueOf(i);
    }
}
