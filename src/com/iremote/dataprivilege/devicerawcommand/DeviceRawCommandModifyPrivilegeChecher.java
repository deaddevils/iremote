package com.iremote.dataprivilege.devicerawcommand;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.ZWaveDeviceService;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class DeviceRawCommandModifyPrivilegeChecher implements IURLDataPrivilegeChecker<PhoneUser> {
    protected PhoneUser user;
    protected Integer devicerawcmdid;

    @Override
    public void setUser(PhoneUser user) {
        this.user = user;
    }

    @Override
    public void setParameter(String parameter) {
        if (StringUtils.isNotBlank(parameter)) {
            devicerawcmdid = Integer.valueOf(parameter);
        }
    }

    @Override
    public void SetParameters(Map<String, String> parameters) {
    }

    @Override
    public boolean checkprivilege() {
        if (devicerawcmdid == null) {
            return true;
        }
        DeviceRawCmd deviceRawCmd = new DeviceRawCmdService().query(devicerawcmdid);
        if (deviceRawCmd == null || deviceRawCmd.getZwavedeviceid() == 0) {
            return true;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(deviceRawCmd.getZwavedeviceid());
        return zd != null && PhoneUserHelper.checkModifyPrivilege(user, zd.getDeviceid());
    }
}
