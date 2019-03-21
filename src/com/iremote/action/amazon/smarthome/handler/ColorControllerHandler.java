package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestHSBColor;
import com.iremote.action.amazon.smarthome.util.ColorConvert;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.common.JSONUtil;
import com.iremote.domain.PhoneUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class ColorControllerHandler extends OperationHandler {
    private static Log log = LogFactory.getLog(ColorControllerHandler.class);

    private static final String OPERATION_TYPE_SET_COLOR = "SetColor";
    private static final String COLOR_VALUE_HUE = "directive.payload.color.hue";
    private static final String COLOR_VALUE_SATURATION = "directive.payload.color.saturation";
    private static final String COLOR_VALUE_BRIGHTNESS = "directive.payload.color.brightness";
    private AwsSmartHomeRequestHSBColor color = new AwsSmartHomeRequestHSBColor();

    @Override
    public void process(String name, int zwavedeviceid, int channelid, JSONObject json, PhoneUser phoneuser) {
        String operationtype = JSONUtil.getString(json, NAME_PATH);
        if (!OPERATION_TYPE_SET_COLOR.equals(operationtype)) {
            return;
        }
        String hue = JSONUtil.getString(json, COLOR_VALUE_HUE);
        String saturation = JSONUtil.getString(json, COLOR_VALUE_SATURATION);
        String brightness = JSONUtil.getString(json, COLOR_VALUE_BRIGHTNESS);

        Float huefloat = Float.valueOf(hue);
        Float saturationfloat = Float.valueOf(saturation);
        Float brightnessfloat = Float.valueOf(brightness);

        float[] colors = ColorConvert.hsb2rgb(new float[]{huefloat, saturationfloat, brightnessfloat});

        color.setHue(huefloat);
        color.setSaturation(saturationfloat);
        color.setBrightness(brightnessfloat);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("power", 255);
        jsonObject.put("red", Float.valueOf(colors[0]).intValue());
        jsonObject.put("green", Float.valueOf(colors[1]).intValue());
        jsonObject.put("blue", Float.valueOf(colors[2]).intValue());
        jsonObject.put("alpha", 50);

        log.info("command:" + jsonObject.toJSONString());
        operate(zwavedeviceid, phoneuser, jsonObject);
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
        super.property.setName("color");
        super.property.setValue(color);
    }
}
