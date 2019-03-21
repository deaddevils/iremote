package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JSONUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class BrightnessControllerHandler extends OperationHandler {
    private static Log log = LogFactory.getLog(BrightnessControllerHandler.class);

    private int value;
    private static final String OPERATION_TYPE_SET_BRIGHTNESS = "SetBrightness";
    private static final String OPERATION_TYPE_ADJUST_BRIGHTNESS = "AdjustBrightness";
    private static final String BRIGHTNESS = "directive.payload.brightness";
    private static final String BRIGHTNESSDELTA = "directive.payload.brightnessDelta";
    private Double col = 2.55;

    @Override
    public void process(String name, int zwavedeviceid, int channelid, JSONObject json, PhoneUser phoneuser) {
        String operationtype = JSONUtil.getString(json, NAME_PATH);
        Integer bright;

        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        if (IRemoteConstantDefine.DEVICE_TYPE_DIMMER.equals(zd.getDevicetype())) {
            if (OPERATION_TYPE_SET_BRIGHTNESS.equals(operationtype)) {
                bright = JSONUtil.getInteger(json, BRIGHTNESS);
            } else {
                bright = JSONUtil.getInteger(json, BRIGHTNESSDELTA);
                bright = zd.getStatus() + bright;
            }

            if (bright > 99) {
                bright = 99;
            }
            if (bright < 0) {
                bright = 0;
            }
            value = bright;
            jsonObject.put("operation", "open");
            jsonObject.put("value", bright);

        } else {
            bright = getBrightness(zd.getStatuses(), json, operationtype);
            if (bright == null) {
                log.info("brightness is null");
                return;
            }

            if (value > 99) {
                value = 99;
            }
            if (value < 0) {
                value = 0;
            }

            if (bright > 255) {
                bright = 255;
            }
            if (bright < 0) {
                bright = 0;
            }
            jsonObject.put("power", 255);
            jsonObject.put("bright", bright);

        }

        operate(zwavedeviceid, phoneuser, jsonObject);
    }

    private Integer getBrightness(String statuses, JSONObject json, String operationtype) {
        if (statuses == null) {
            return null;
        }
        Integer bright;
        if (OPERATION_TYPE_SET_BRIGHTNESS.equals(operationtype)) {
            Integer brightness = JSONUtil.getInteger(json, BRIGHTNESS);
            value = brightness;


            Double brightnessValue = brightness * col;

            bright = brightnessValue.intValue();

        } else {
            Integer brightnessDelta = JSONUtil.getInteger(json, BRIGHTNESSDELTA);

            List<Integer> list = JSONArray.parseArray(statuses, Integer.class);
            Integer bri = list.get(1);
            Double v = (brightnessDelta * col) + bri;
            bright = v.intValue();
            Double v1 = bright / col;
            value = v1.intValue();
        }
        return bright;
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
        property.setNamespace("Alexa.BrightnessController");
        property.setName("brightness");
        property.setValue(value);
    }
}
