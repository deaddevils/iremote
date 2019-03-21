package com.iremote.action.helper;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

import java.util.Date;
import java.util.List;

public class PushEventToThirdpartHelper {
    public static final int DEFAULT_ZWAVEDEVICE_ID =0;

    public static void pushEventToThirdpart(String deviceid, int zwavedeviceid, Integer partitionid, Integer dscpartitionid, Integer armstatus, Integer usercode, String type){
        List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(deviceid);

        for (Integer tpid : tidlst) {
            if (tpid == null || tpid == 0)
                continue;

            EventtoThirdpart etd = new EventtoThirdpart();

            etd.setThirdpartid(tpid);
            etd.setType(type);
            etd.setDeviceid(deviceid);
            etd.setZwavedeviceid(zwavedeviceid);
            etd.setIntparam(usercode);
            etd.setEventtime(new Date());

            JSONObject json = new JSONObject();
            json.put("armstatus", armstatus);
            if (partitionid != null) {
                json.put("partitionid", partitionid);
            }
            if (dscpartitionid != null) {
                json.put("dscpartitionid", dscpartitionid);
            }
            etd.setObjparam(json.toJSONString());

            EventtoThirdpartService svr = new EventtoThirdpartService();
            svr.save(etd);
        }
    }

    public static void pushEventToThirdpartFromZwavePartition(Integer phoneuserid, Integer partitionid, Integer armstatus, String type){
        ZWaveDeviceService zds = new ZWaveDeviceService();
        String deviceid = null;

        if (partitionid != null) {
            List<ZWaveDevice> zWaveDevices = zds.querybypartitionid(partitionid);

            for (ZWaveDevice zWaveDevice : zWaveDevices) {
                if (zWaveDevice.getDeviceid() != null) {
                    deviceid = zWaveDevice.getDeviceid();
                    break;
                }
            }
        }

        if (deviceid == null) {
            if (phoneuserid == null) {
                return;
            }

            RemoteService rs = new RemoteService();
            List<String> devices = rs.queryDeviceidByPhoneuserid(phoneuserid);
            if (devices != null && devices.size() != 0) {
                deviceid = devices.get(0);
            }
        }

        int usercode = IRemoteConstantDefine.DEFAULT_USER_CODE;
        pushEventToThirdpart(deviceid, DEFAULT_ZWAVEDEVICE_ID, partitionid,null, armstatus, usercode, type);
    }
}
