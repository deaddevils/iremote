package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.common.JSONUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class ColorTemperatureControllerHandler extends OperationHandler {
    private static Log log = LogFactory.getLog(ColorTemperatureControllerHandler.class);

    private static final String OPERATION_TYPE_SET_COLOR_TEMPERATURE = "SetColorTemperature";
    private static final String OPERATION_TYPE_DECREASE_COLOR_TEMPERATURE = "DecreaseColorTemperature";
    private static final String OPERATION_TYPE_INCREASE_COLOR_TEMPERATURE = "IncreaseColorTemperature";
    private static final String COLOR_TEMPERATURE_IN_KELVIN = "directive.payload.colorTemperatureInKelvin";
    private Double col = 19.6;
    private int value;

    @Override
    public void process(String name, int zwavedeviceid, int channelid, JSONObject json, PhoneUser phoneuser) {
        String operationtype = JSONUtil.getString(json, NAME_PATH);

        Integer colorTemperatureInKelvin = JSONUtil.getInteger(json, COLOR_TEMPERATURE_IN_KELVIN);
        Integer warm;

        warm = getWarm(zwavedeviceid, operationtype, colorTemperatureInKelvin);
        if (warm == null) {
            log.info("warm value is null");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("power", 255);
        jsonObject.put("warm", warm);

        operate(zwavedeviceid, phoneuser, jsonObject);
    }

    private Integer getWarm(int zwavedeviceid, String operationtype, Integer colorTemperatureInKelvin) {
        Integer warm;
        if (operationtype.equals(OPERATION_TYPE_SET_COLOR_TEMPERATURE)) {
            if (colorTemperatureInKelvin > 6000) {
                colorTemperatureInKelvin = 6000;
            }
            value = colorTemperatureInKelvin;

            Double warmValue = (colorTemperatureInKelvin - 1000) / col;
            warm = warmValue.intValue();

        } else {
            ZWaveDeviceService zds = new ZWaveDeviceService();
            ZWaveDevice zd = zds.query(zwavedeviceid);
            if (zd == null || zd.getStatuses() == null) {
                return null;
            }

            List<Integer> list = JSONArray.parseArray(zd.getStatuses(), Integer.class);
            Integer warm255 = list.get(0);
            if ((operationtype.equals(OPERATION_TYPE_DECREASE_COLOR_TEMPERATURE))) {
                warm = warm255 - 45;
            } else {
                warm = warm255 + 45;
            }
            value = (int) (warm * col) + 1000;
        }

        if (warm > 255) {
            warm = 255;
        }
        if (warm < 0) {
            warm = 0;
        }
        return warm;
    }

    private void operate(int zwavedeviceid, PhoneUser phoneuser, JSONObject jsonObject) {
        DeviceOperationAction doa = new DeviceOperationAction();
        doa.setZwavedeviceid(zwavedeviceid);
        doa.setCommand(jsonObject.toJSONString());
        doa.setPhoneuser(phoneuser);
        doa.execute();
        resultCode = doa.getResultCode();
    }

    @Override
    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        super.setProperty(directive);
        property.setNamespace("Alexa.ColorTemperatureController");
        property.setName("colorTemperatureInKelvin");
        property.setValue(value);
    }
}
