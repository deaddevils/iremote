package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.PhoneUser;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.service.DeviceCapabilityService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;

import java.util.Date;

public class DoorlockForbidRemoteOpen extends ZWaveReportBaseProcessor {
    @Override
    protected void updateDeviceStatus() {
        init();

        DeviceCapabilityService dcs = new DeviceCapabilityService();
        DeviceCapability dc = dcs.query(zrb.getDevice(), IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_FORBID_REMOTE_OPEN);
        if (dc != null) {
            return;
        }

        DeviceCapability dc0 = new DeviceCapability(zrb.getDevice(), IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_FORBID_REMOTE_OPEN);
        dcs.save(dc0);

        RemoteService rs = new RemoteService();
        Integer phoneuserid = rs.queryOwnerId(zrb.getDeviceid());
        if (phoneuserid != null) {
            PhoneUserService pus = new PhoneUserService();
            PhoneUser pu = pus.query(phoneuserid);
            pu.setLastupdatetime(new Date());

            PhoneUserHelper.sendInfoChangeMessage(pu);
        }

    }

    private void init() {
        this.dontpusmessage();
        this.dontsavenotification();
    }

    @Override
    public String getMessagetype() {
        return null;
    }
}
