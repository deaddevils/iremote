package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.Partition;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.serianet.SeriaNetReportBaseProcessor;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.PartitionService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;

public abstract class DSCReportBaseProcessor extends SeriaNetReportBaseProcessor {
    protected int partitionid;
    protected int zwavedeviceid;
    protected int status = 0;

    protected int getChannelid(byte[] request) {
        return getValue(request, 3, 6);
    }

    protected int getDSCPartitionid(byte[] request) {
        return getValue(request, 3, 4);
    }

    protected int getValue(byte[] request, int beginindex, int endindex) {
        if (beginindex >= endindex) {
            return 0;
        }
        String cmd = TlvWrap.readString(request, TagDefine.TAG_DSC, TagDefine.TAG_HEAD_LENGTH);
        if (cmd == null || cmd.length() < 3) {
            return 0;
        }
        try {
            int integer = Integer.valueOf(cmd.substring(beginindex, endindex));
            return integer;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    protected PhoneUser getPhoneUser() {
        RemoteService rs = new RemoteService();
        Integer phoneuserid = rs.queryOwnerId(seriaNetReportBean.getDeviceid());
        if (phoneuserid == null) {
            return null;
        }
        PhoneUserService pus = new PhoneUserService();
        PhoneUser phoneuser = pus.query(phoneuserid);
        if (phoneuser == null) {
            return null;
        }
        return phoneuser;
    }

    protected int addPartition(int dscpartitionid, ZWaveDevice zd,int armstatus, int warningstatus) {
        PhoneUser phoneuser = getPhoneUser();
        if (phoneuser == null) {
            return 0;
        }
        String name0 = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_TYPE_DSC_PARTITION, phoneuser.getPlatform(), phoneuser.getLanguage());
        String name = String.format("%s%d", name0, dscpartitionid);

        Partition partition = new Partition();
        partition.setArmstatus(armstatus);
        partition.setWarningstatus(warningstatus);
        partition.setDscpartitionid(dscpartitionid);
        partition.setName(name);
        partition.setZwavedevice(zd);
        PartitionService ps = new PartitionService();
        changeDeviceCapabilityPartition(zd, dscpartitionid);
        return ps.save(partition);
    }

    protected void changeDeviceCapabilityPartition(ZWaveDevice zwaveDevice, int dscpartitionid) {
        DeviceCapabilityService dcs = new DeviceCapabilityService();
        DeviceCapability deviceCapability = dcs.query(zwaveDevice, 2);
        if (deviceCapability  != null && dscpartitionid<= Integer.valueOf(deviceCapability.getCapabilityvalue())){
            return;
        } else if (deviceCapability == null) {
            deviceCapability = new DeviceCapability();
            deviceCapability.setZwavedevice(zwaveDevice);
            deviceCapability.setCapabilitycode(2);
        }
        deviceCapability.setCapabilityvalue(String.valueOf(dscpartitionid));
        dcs.saveOrUpdate(deviceCapability);
    }
}
