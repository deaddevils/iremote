package com.iremote.thirdpart.tecus.event;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;

import java.util.ArrayList;
import java.util.List;

public class TriggerAlarmForAmetaProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {
    @Override
    public String getTaskKey() {
        return getDeviceid();
    }

    @Override
    public void run() {
        if (getZwavedeviceid() == 0) {
            return;
        }

        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(getZwavedeviceid());
        if (zd == null) {
            return;
        }
        if (zd.getPartitionid() == null) {
            triggerNoPartitionAlarm();
            return;
        }

        RemoteService rs = new RemoteService();
        List<String> devices = rs.queryDeviceidByPhoneuserid(rs.queryOwnerId(getDeviceid()));

        List<ZWaveDevice> zWaveDeviceList = zds.queryByDevicetypeAndPartitionid(devices, IRemoteConstantDefine.DEVICE_TYPE_ALARM, zd.getPartitionid());
        for (ZWaveDevice zWaveDevice : zWaveDeviceList) {
            CommandTlv ct = CommandUtil.createAlarmCommand(zWaveDevice.getNuid());
            SynchronizeRequestHelper.asynchronizeRequest(zWaveDevice.getDeviceid(), ct , 0);
        }
    }

    private void triggerNoPartitionAlarm() {
        IremotepasswordService rs = new IremotepasswordService();
        Remote r = rs.getIremotepassword(getDeviceid());

        if ( r == null || r.getPhoneuserid() == null )
            return ;

        PhoneUserService pus = new PhoneUserService();
        PhoneUser phoneuser = pus.query(r.getPhoneuserid()) ;

        if ( phoneuser == null )
            return ;

        List<Integer> pidl;
        if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
            pidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
        else
        {
            pidl = new ArrayList<>(1);
            pidl.add(phoneuser.getPhoneuserid());
        }

        List<String> didl = rs.queryDeviceidbyPhoneUserid(pidl);

        ZWaveDeviceService zds = new ZWaveDeviceService();
        List<ZWaveDevice> lst = zds.querybydeviceid(didl);
        if ( lst == null || lst.size() == 0 )
            return ;

        for ( ZWaveDevice d : lst )
        {
            if ( !IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype()))
                continue;
            if (d.getPartitionid() != null) {
                continue;
            }

            CommandTlv ct = CommandUtil.createAlarmCommand(d.getNuid());
            SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);

        }
    }
}
