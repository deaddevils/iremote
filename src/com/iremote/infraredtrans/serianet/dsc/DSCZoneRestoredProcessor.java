package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.iremote.task.timertask.processor.PushHelper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DSCZoneRestoredProcessor extends DSCReportBaseProcessor {
    ZWaveDevice zd = new ZWaveDevice();
    protected int zoneid;
    protected int status;

    @Override
    protected void updateDeviceStatus() {
        ZWaveDeviceService zds = new ZWaveDeviceService();

        DeviceCapabilityService dcs = new DeviceCapabilityService();
        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zWaveSubDevice = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), zoneid);
        int maxChannel = zoneid % 8 == 0 ? zoneid : (zoneid / 8 + 1) * 8;
        if (dcs.query(zd, 3) == null && zWaveSubDevice == null) {
            saveZWaveSubDevice(zd, zsds);
            changeDeviceCapabilityWithZone(zd, maxChannel, dcs);
        }
        updateZwavedevice(zd, zds, maxChannel);
    }

    private void updateZwavedevice(ZWaveDevice zd, ZWaveDeviceService zds, int maxChannel) {
        String statuses = zd.getStatuses();
        zd.setStatuses(processStatuses(zd, statuses, maxChannel));

        ZWaveSubDevice zsd = zd.getzWaveSubDevices().stream().filter(z -> z.getChannelid() == zoneid).findFirst().orElse(null);
        changeZwaveSubDeviceStatus(zsd);
        zds.saveOrUpdate(zd);
    }

    protected void changeZwaveSubDeviceStatus(ZWaveSubDevice zsd) {
        if (zsd != null) {
            zsd.setStatus(IRemoteConstantDefine.DSC_CHANNEL_READY);

            PushHelper.pushZwaveSubDeviceStatusMessage(zsd);
        }
    }

    private String processStatuses(ZWaveDevice zd, String statuses, int maxChannel) {
        if (statuses == null) {
            statuses = newStatuses(maxChannel);
        } else {
            statuses = addStatuses(maxChannel, statuses);
        }
        return statuses;
    }

    private void changeDeviceCapabilityWithZone(ZWaveDevice zd, int maxChannel, DeviceCapabilityService dcs) {
        DeviceCapability deviceCapability = dcs.query(zd, 1);
        if (deviceCapability != null && maxChannel <= Integer.valueOf(deviceCapability.getCapabilityvalue())) {
            return;
        } else if (deviceCapability == null) {
            deviceCapability = new DeviceCapability();
            deviceCapability.setZwavedevice(zd);
            deviceCapability.setCapabilitycode(1);
        }
        deviceCapability.setCapabilityvalue(String.valueOf(maxChannel));
        dcs.saveOrUpdate(deviceCapability);
    }

    private String addStatuses(int maxChannel, String statuses) {
        JSONArray jsonArray = JSONArray.parseArray(statuses);
        for (int i = jsonArray.size(); i <= maxChannel; i++) {
            jsonArray.add(IRemoteConstantDefine.DSC_CHANNEL_READY);
        }
        jsonArray.remove(zoneid);
        jsonArray.add(zoneid, status);
        return jsonArray.toJSONString();
    }

    private String newStatuses(int maxChannel) {
        ArrayList<Integer> statuses = new ArrayList<>();
        statuses.add(0);
        for (int i = 1; i <= maxChannel; i++) {
            if (zoneid == i) {
                statuses.add(status);
            } else {
                statuses.add(IRemoteConstantDefine.DSC_CHANNEL_READY);
            }
        }
        return statuses.toString();
    }

    private void saveZWaveSubDevice(ZWaveDevice zd, ZWaveSubDeviceService zsds) {
        PhoneUser phoneuser = getPhoneUser();
        if (phoneuser == null) {
            return;
        }
        String name0 = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_CHANNEL, phoneuser.getPlatform(), phoneuser.getLanguage());
        String name = String.format("%s%d", name0, zoneid);
        ZWaveSubDevice zWaveSubDevice1 = new ZWaveSubDevice();
        zWaveSubDevice1.setChannelid(zoneid);
        zWaveSubDevice1.setName(name);
        zWaveSubDevice1.setZwavedevice(zd);
        zWaveSubDevice1.setSubdevicetype(String.valueOf(IRemoteConstantDefine.WARNING_ARM_STATUS_FIRE));
        zsds.save(zWaveSubDevice1);
    }

    @Override
    protected void pushMessage() {
    }

    @Override
    protected void writeLog() {

    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_CHANNEL_READY;
        zoneid = getValue(seriaNetReportBean.getCmd(), 3, 6);

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDeviceList = zds.querybydeviceidtype(seriaNetReportBean.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zWaveDeviceList == null || zWaveDeviceList.size() == 0){
            initSucceed = false;
            return;
        }
        zd = zWaveDeviceList.get(0);

        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zsd = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), zoneid);

        if (zsd != null && StringUtils.isBlank(zsd.getSubdevicetype())) {
            initSucceed = false;
        }
    }
}
