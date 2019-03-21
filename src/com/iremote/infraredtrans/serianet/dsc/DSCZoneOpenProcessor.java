package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.task.timertask.processor.PushHelper;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class DSCZoneOpenProcessor extends DSCZoneRestoredProcessor {
    @Override
    protected void updateDeviceStatus() {
        super.updateDeviceStatus();
    }

    @Override
    protected void pushMessage() {

    }

    @Override
    protected void writeLog() {

    }

    @Override
    protected void changeZwaveSubDeviceStatus(ZWaveSubDevice zsd) {
        if (zsd != null) {
            zsd.setStatus(IRemoteConstantDefine.DSC_CHANNEL_NOT_READY);

            PushHelper.pushZwaveSubDeviceStatusMessage(zsd);
        }
    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_CHANNEL_NOT_READY;
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
