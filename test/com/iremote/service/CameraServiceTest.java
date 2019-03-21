package com.iremote.service;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Camera;
import com.iremote.domain.ZWaveDevice;
import com.iremote.test.db.Db;

import java.util.List;

public class CameraServiceTest {

    public static void main(String arg[]) {
        testQueryWarningDevice();
    }

    private static void testQueryWarningDevice() {
        Db.init();
        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDevices = zds.queryWarningDevice();

        for (ZWaveDevice zWaveDevice : zWaveDevices) {
            if (IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zWaveDevice.getDevicetype())){
                continue;
            }

            List<Integer> jsonList = JSONArray.parseArray(zWaveDevice.getWarningstatuses(), Integer.class);
            for (Integer warningstatus : jsonList) {
                String type = getWarningstatusType(zWaveDevice.getDevicetype(), warningstatus);
                System.out.print(zWaveDevice.getName() + ":" + zWaveDevice.getDevicetype() + " -> " + type + ":" + warningstatus);
                if (type == null) {
                    System.out.println("<----------------");
                } else {
                    System.out.println();
                }
            }
        }

        System.out.println("============");

        CameraService cs = new CameraService();
        List<Camera> cameras = cs.queryWarningDevice();

        for (Camera camera : cameras) {
            List<Integer> jsonList = JSONArray.parseArray(camera.getWarningstatuses(), Integer.class);
            for (Integer warningstatus : jsonList) {
                String status = getCameraWarningstatusType(warningstatus);
                System.out.println(camera.getName() + " -> " + status + ":" + warningstatus);
            }
        }
    }

    private static String getWarningstatusType(String devicetype, Integer warningstatus) {
        if (devicetype == null || warningstatus == null) {
            return null;
        }

        String operation = String.format("%s_%d", devicetype, warningstatus);
        return IRemoteConstantDefine.DEVICE_WARNING_MESSAGE_MAP.get(operation);
    }

    private static String getCameraWarningstatusType(Integer warningstatus) {
        return getWarningstatusType("camera", warningstatus);
    }
}
