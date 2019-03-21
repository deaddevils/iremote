package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestProperty;
import com.iremote.action.amazon.smarthome.AwsSmartHomeResponseValue;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.common.JSONUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

import java.util.List;

public class ThermostatControllerHandler extends OperationHandler {
    private static final String OPERATION_TYPE_SET_TARGET_TEMPERATURE = "SetTargetTemperature";
    private static final String OPERATION_TYPE_ADJUST_TARGET_TEMPERATURE = "AdjustTargetTemperature";
    private static final String OPERATION_TYPE_SET_THERMOSTAT_MODE = "SetThermostatMode";
    private static final String VALUE = "directive.payload.targetSetpoint.value";
    private static final String SCALE = "directive.payload.targetSetpoint.scale";
    private static final String VALUE_DELTA = "directive.payload.targetSetpointDelta.value";
    private static final String SCALE_DELTA = "directive.payload.targetSetpointDelta.scale";
    private static final String MODE_VALUE = "directive.payload.thermostatMode.value";
    public static final String CELSIUS = "CELSIUS";
    public static final String FAHRENHEIT = "FAHRENHEIT";
    public static final String[] MODE = new String[]{"", "HEAT", "COOL", "AUTO", "OFF", "", "ECO"};
    private double value;
    private String defaultScale = CELSIUS;
    private String operationtype;
    private String mode;
    private int HEAT = 1;
    private int COOL = 2;
    private int AUTO = 3;
    private int OFF = 4;
    private int ECO = 6;

    @Override
    public void process(String name, int zwavedeviceid, int channelid, JSONObject json, PhoneUser phoneuser) {
        operationtype = JSONUtil.getString(json, NAME_PATH);

        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null || zd.getStatuses() == null) {
            return;
        }
        List<Integer> statuses = JSONArray.parseArray(zd.getStatuses(), Integer.class);
        JSONObject jsonObject = new JSONObject();

        mode = MODE[statuses.get(0)];


        if (OPERATION_TYPE_SET_TARGET_TEMPERATURE.equals(operationtype)) {
            String scale = JSONUtil.getString(json, SCALE);
            String value0 = JSONUtil.getString(json, VALUE);

            try {
                value = Double.valueOf(value0);
            } catch (NumberFormatException e) {
                return;
            }

            jsonObject.put("mode", statuses.get(0));
            jsonObject.put("temperatureunit", 1);
            jsonObject.put("power", 5);
            jsonObject.put("fan", 0);

            if (!CELSIUS.equals(scale)) {
                defaultScale = FAHRENHEIT;
                value = (value - 32) / 1.8;
            }
            jsonObject.put("temperature", Double.valueOf(value).intValue());
            operate(zwavedeviceid, channelid, phoneuser, jsonObject);
        } else if (OPERATION_TYPE_ADJUST_TARGET_TEMPERATURE.equals(operationtype)) {
            String scale = JSONUtil.getString(json, SCALE_DELTA);
            String value0 = JSONUtil.getString(json, VALUE_DELTA);

            try {
                value = Double.valueOf(value0);
            } catch (NumberFormatException e) {
                return;
            }

            jsonObject.put("mode", statuses.get(0));
            jsonObject.put("temperatureunit", 1);
            jsonObject.put("power", 5);
            jsonObject.put("fan", 0);

            if (!CELSIUS.equals(scale)) {
                defaultScale = FAHRENHEIT;
                value = (value - 32) / 1.8;
            }
            value = statuses.get(2) + value;
            jsonObject.put("temperature", value);
            operate(zwavedeviceid, channelid, phoneuser, jsonObject);
        } else if (OPERATION_TYPE_SET_THERMOSTAT_MODE.equals(operationtype)) {
            mode = JSONUtil.getString(json, MODE_VALUE);
            if (MODE[OFF].equals(mode)) {
                jsonObject.put("power", 0);
                operate(zwavedeviceid, channelid, phoneuser, jsonObject);
            } else {
                switch (mode) {
                    case "HEAT":
                        jsonObject.put("mode", HEAT);
                        break;
                    case "COOL":
                        jsonObject.put("mode", COOL);
                        break;
                    case "AUTO":
                        jsonObject.put("mode", AUTO);
                        break;
                    case "ECO":
                        jsonObject.put("mode", ECO);
                        break;
                    default:
                        jsonObject.put("mode", AUTO);
                        break;
                }
                operate(zwavedeviceid, channelid, phoneuser, jsonObject);
            }
        }
    }

    private void operate(int zwavedeviceid, int channelid, PhoneUser phoneuser, JSONObject jsonObject) {
        DeviceOperationAction doa = new DeviceOperationAction();
        doa.setPhoneuser(phoneuser);
        doa.setChannel(channelid);
        doa.setCommand(jsonObject.toJSONString());
        doa.setZwavedeviceid(zwavedeviceid);
        doa.execute();
        resultCode = doa.getResultCode();
    }

    @Override
    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        if (defaultScale.equals(FAHRENHEIT)) {
            value = value * 1.8 + 32;
        }

        super.setProperty(directive);
        super.property.setName("targetSetpoint");
        super.property.setNamespace("Alexa.ThermostatController");
        AwsSmartHomeResponseValue ashrv = new AwsSmartHomeResponseValue();
        ashrv.setScale(defaultScale);
        ashrv.setValue(value);
        super.property.setValue(ashrv);

        AwsSmartHomeRequestProperty property1 = new AwsSmartHomeRequestProperty();
        property1.setNamespace("Alexa.ThermostatController");
        property1.setName("thermostatMode");
        property1.setValue(mode);
        super.context.getProperties().add(property1);
    }
}
