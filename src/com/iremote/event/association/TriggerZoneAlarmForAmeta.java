package com.iremote.event.association;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.DSCEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;

import java.util.List;

public class TriggerZoneAlarmForAmeta extends DSCEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        if (getChannelid() == 0) {
            return;
        }

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> zWaveDeviceList = zds.queryByDevicetype(getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
        if (zWaveDeviceList == null || zWaveDeviceList.size() == 0) {
            return;
        }

        ZWaveDevice zd = zWaveDeviceList.get(0);

        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zsd = zsds.queryByZwavedeviceidAndChannelid(zd.getZwavedeviceid(), getChannelid());
        if (zsd == null || zsd.getPartition() == null) {
            return;
        }

        RemoteService rs = new RemoteService();
        List<String> devices = rs.queryDeviceidByPhoneuserid(rs.queryOwnerId(getDeviceid()));

        List<ZWaveDevice> zdList = zds.queryByDevicetypeAndPartitionid(
                devices, IRemoteConstantDefine.DEVICE_TYPE_ALARM, zsd.getPartition().getPartitionid());
        for (ZWaveDevice zWaveDevice : zdList) {
            CommandTlv ct = CommandUtil.createAlarmCommand(zWaveDevice.getNuid());
            SynchronizeRequestHelper.asynchronizeRequest(zWaveDevice.getDeviceid(), ct , 0);
        }
    }
}
