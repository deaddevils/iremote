package com.iremote.action.amazon.smarthome.handler;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;
import com.iremote.action.device.DeviceOperationAction;
import com.iremote.action.device.SwitchOffDeviceAction;
import com.iremote.action.device.SwitchOnDeviceAction;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

import java.util.Arrays;
import java.util.List;

public class PowerControllerHandler extends OperationHandler {
    private static final List<String> OPEN_OPERATION = Arrays.asList(new String[]{"TurnOn", "Unlock"});
    private static final List<String> OFF_OPERATION = Arrays.asList(new String[]{"TurnOff", "Lock"});

    @Override
    public void process(String name, int zwavedeviceid, int channelid, JSONObject json, PhoneUser phoneUser) {
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            return;
        }
        if (IRemoteConstantDefine.DEVICE_TYPE_DIMMER.equals(zd.getDevicetype()) ||
                IRemoteConstantDefine.DEVICE_TYPE_CURTAIN.equals(zd.getDevicetype())) {
            JSONObject jsonObject = new JSONObject();
            DeviceOperationAction doa = new DeviceOperationAction();
            doa.setZwavedeviceid(zwavedeviceid);
            doa.setPhoneuser(phoneUser);
            doa.setChannel(channelid);
            if (OFF_OPERATION.contains(name)) {
                jsonObject.put("operation", "close");
            } else {
                jsonObject.put("operation", "open");
            }
            doa.setCommand(jsonObject.toJSONString());
            doa.execute();
            resultCode = doa.getResultCode();
        } else if (IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH.equals(zd.getDevicetype())) {
            JSONObject jsonObject = new JSONObject();
            DeviceOperationAction doa = new DeviceOperationAction();
            doa.setZwavedeviceid(zwavedeviceid);
            doa.setPhoneuser(phoneUser);
            doa.setChannel(channelid);
            if (OFF_OPERATION.contains(name)) {
                jsonObject.put("power", 0);
            } else {
                jsonObject.put("power", 255);
            }
            doa.setCommand(jsonObject.toJSONString());
            doa.execute();
            resultCode = doa.getResultCode();
        } else {
            if (OPEN_OPERATION.contains(name)) {
                swithon(zwavedeviceid, channelid, phoneUser);
            } else if (OFF_OPERATION.contains(name)) {
                swithoff(zwavedeviceid, channelid, phoneUser);
            }
        }
    }


    private void swithon(int zwavedeviceid, int channelid, PhoneUser phoneUser) {
        SwitchOnDeviceAction action = new SwitchOnDeviceAction();
        action.setZwavedeviceid(zwavedeviceid);
        action.setChannel(channelid);
        action.setPhoneuser(phoneUser);
        action.execute();
        resultCode = action.getResultCode();
    }

    private void swithoff(int zwavedeviceid, int channelid, PhoneUser phoneUser) {
        SwitchOffDeviceAction action = new SwitchOffDeviceAction();
        action.setZwavedeviceid(zwavedeviceid);
        action.setChannel(channelid);
        action.setPhoneuser(phoneUser);
        action.execute();
        resultCode = action.getResultCode();
    }

    @Override
    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        super.setProperty(directive);
        property.setName("powerState");
        if (OPEN_OPERATION.contains(directive.getHeader().getName())){
            property.setValue("ON");
        }
        else if (OFF_OPERATION.contains(directive.getHeader().getName())){
            property.setValue("OFF");
        }
    }
}
