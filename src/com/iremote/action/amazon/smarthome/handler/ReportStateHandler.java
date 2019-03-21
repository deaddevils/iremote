package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.*;
import com.iremote.action.amazon.smarthome.util.ColorConvert;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.List;

public class ReportStateHandler extends OperationHandler {
    private static Log log = LogFactory.getLog(ReportStateHandler.class);
    private List<Integer> modeList = Arrays.asList(1, 2, 3, 4, 6);
    private int zwavedeviceid;
    private int channel;

    @Override
    public void process(String name, int deviceid, int channelid, JSONObject json, PhoneUser phoneUser) {
        zwavedeviceid = deviceid;
        channel = channelid;
    }

    @Override
    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        super.setProperty(directive);
        header.setName("StateReport");

        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            return;
        }

        setStatus(zd);
    }

    private void setStatus(ZWaveDevice zd) {
        switch (zd.getDevicetype()) {
            case IRemoteConstantDefine.DEVICE_TYPE_OUTLET:
            case IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH:
                setStatus(zd.getStatus());
                property.setName("powerState");
                property.setNamespace("Alexa.PowerController");
                break;
            case IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH:
            case IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH:
                setStatus(zd.getStatuses(), zd.getDevicetype());
                property.setName("powerState");
                property.setNamespace("Alexa.PowerController");
                break;
            case IRemoteConstantDefine.DEVICE_TYPE_CURTAIN:
                property.setValue(String.valueOf(zd.getStatus()));
                property.setName("percentage");
                property.setNamespace("Alexa.PercentageController");
                addProperty(zd);
                break;
            case IRemoteConstantDefine.DEVICE_TYPE_DIMMER:
                property.setValue(String.valueOf(zd.getStatus()));
                property.setName("brightness");
                property.setNamespace("Alexa.BrightnessController");
                addProperty(zd);
                break;
            case IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH:
                List<Integer> list = JSONArray.parseArray(zd.getStatuses(), Integer.class);
                float[] floats = ColorConvert.rgb2hsb(list.get(2), list.get(3), list.get(4));
                log.info("hue:" + floats[0]);
                AwsSmartHomeRequestHSBColor color = new AwsSmartHomeRequestHSBColor();
                color.setHue(floats[0]);
                color.setSaturation(floats[1]);
                color.setBrightness(floats[2]);
                property.setValue(color);
                property.setName("color");
                property.setNamespace("Alexa.ColorController");
                addProperty(zd);
                addBrightness(zd);
                addColorTemperature(zd);
                break;
            case IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK:
                property.setName("lockState");
                property.setNamespace("Alexa.LockController");
                setDoorLockStatus(zd.getStatus());
                break;
            case IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER:
                property.setName("powerState");
                property.setNamespace("Alexa.PowerController");
                property.setValue(zd.getStatus() == 1 ? "ON" : "OFF");
                addTargetTemperature(zd);
                addTargetTemperatureMode(zd);
                break;
            default:
                property.setName("powerState");
                property.setNamespace("Alexa.PowerController");
                property.setValue(zd.getStatus() == 255 ? "ON" : "OFF");
                break;
        }
    }

    private void addTargetTemperature(ZWaveDevice zd) {
        AwsSmartHomeRequestProperty property1 = new AwsSmartHomeRequestProperty();
        property1.setName("targetSetpoint");
        property1.setNamespace("Alexa.ThermostatController");
        property1.setTimeOfSample();
        property1.setUncertaintyInMilliseconds(0);

        List<Integer> list = JSONArray.parseArray(zd.getStatuses(), Integer.class);
        AwsSmartHomeResponseValue value = new AwsSmartHomeResponseValue();
        value.setValue(list.get(2));
        value.setScale(ThermostatControllerHandler.CELSIUS);
        property1.setValue(value);

        context.getProperties().add(property1);
    }

    private void addTargetTemperatureMode(ZWaveDevice zd) {
        AwsSmartHomeRequestProperty property1 = new AwsSmartHomeRequestProperty();
        property1.setName("thermostatMode");
        property1.setNamespace("Alexa.ThermostatController");
        property1.setTimeOfSample();
        property1.setUncertaintyInMilliseconds(0);
        List<Integer> list = JSONArray.parseArray(zd.getStatuses(), Integer.class);
        if (!modeList.contains(list.get(0))){
            property1.setValue(ThermostatControllerHandler.MODE[3]);
        }
        property1.setValue(ThermostatControllerHandler.MODE[list.get(0)]);
        context.getProperties().add(property1);
    }

    private void setDoorLockStatus(Integer status) {
        if (status == 1) {
            property.setValue("UNLOCKED");
        } else if (status == 255) {
            property.setValue("LOCKED");
        } else {
            property.setValue("JAMMED");
        }
    }

    private void addProperty(ZWaveDevice zd) {
        AwsSmartHomeRequestProperty property1 = new AwsSmartHomeRequestProperty();
        property1.setName("powerState");
        property1.setNamespace("Alexa.PowerController");
        property1.setTimeOfSample();
        property1.setUncertaintyInMilliseconds(0);
        property1.setValue(zd.getStatus() == 0 ? "OFF" : "ON");
        context.getProperties().add(property1);
    }

    private void addBrightness(ZWaveDevice zd) {
        AwsSmartHomeRequestProperty property1 = new AwsSmartHomeRequestProperty();
        property1.setName("brightness");
        property1.setNamespace("Alexa.BrightnessController");
        property1.setTimeOfSample();
        property1.setUncertaintyInMilliseconds(0);
        List<Integer> list = JSONArray.parseArray(zd.getStatuses(), Integer.class);
        if (list.size() < 2) {
            return;
        }
        Integer brightness = list.get(1);
        Double v = brightness / 2.55;
        property1.setValue(v.intValue());
        context.getProperties().add(property1);
    }

    private void addColorTemperature(ZWaveDevice zd) {
        AwsSmartHomeRequestProperty property1 = new AwsSmartHomeRequestProperty();
        property1.setName("colorTemperatureInKelvin");
        property1.setNamespace("Alexa.ColorTemperatureController");
        property1.setTimeOfSample();
        property1.setUncertaintyInMilliseconds(0);
        List<Integer> list = JSONArray.parseArray(zd.getStatuses(), Integer.class);
        if (list.size() < 2) {
            return;
        }
        Integer brightness = list.get(0);
        Double v = (brightness * 19.6) + 1000;
        property1.setValue(v.intValue());
        context.getProperties().add(property1);
    }

    private void setStatus(int status) {
        if (status != 255) {
            property.setValue("OFF");
        } else {
            property.setValue("ON");
        }
    }

    private void setStatus(String statuses, String devicetype) {
        if (IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH.equals(devicetype)
                || IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH.equals(devicetype)) {
            List<Integer> list = JSONArray.parseArray(statuses, Integer.class);
            Integer integer = list.get(channel - 1);
            setStatus(integer);
        }
    }
}
