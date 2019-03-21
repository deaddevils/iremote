package com.iremote.thirdpart.wcj.action;

import com.iremote.action.device.doorlock.DoorlockFingerprintUserSetter;
import com.iremote.action.device.doorlock.DoorlockPasswordUserSetter;
import com.iremote.action.device.doorlock.IDoorlockOperationProcessor;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.TimeZone;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class StartAddingFingerPrintUserAction {
    private String username;
    private int zwavedeviceid;
    private String validfrom;
    private String validthrough;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute() {
        if (!checkParameters()) {
            return Action.SUCCESS;
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }

        IDoorlockOperationProcessor setter = createDoorLockSetter(zd);

        setter.init();

        if (setter.getStatus() == DoorlockPasswordUserSetter.STATUS_OFFLINE) 
        {
        	if ( DeviceHelper.isZwavedevice(zd))
        		resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
        	else 
        		resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
        } 
        else 
        {
            setter.sendcommand();
        }

        return Action.SUCCESS;
    }

    private IDoorlockOperationProcessor createDoorLockSetter(ZWaveDevice zd) {
        DoorlockFingerprintUserSetter setter = new DoorlockFingerprintUserSetter();
        setter.setDeviceid(zd.getDeviceid());
        setter.setNuid(zd.getNuid());
        setter.setUsername(username);
        setter.setZwavedeviceid(zd.getZwavedeviceid());
        setter.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USERTYPE_FINGERPRINT);
        setter.setValidfrom(validfrom);
        setter.setValidthrough(validthrough);
        setter.setLocaltime(getLocalTime(zd));
        return setter ;
    }

    private Date getLocalTime(ZWaveDevice zd) {
        String timeZoneId = GatewayHelper.getRemoteTimezoneId(zd.getDeviceid());

        if ( StringUtils.isNotBlank(timeZoneId)){
            return TimeZoneHelper.timezoneTranslate(new Date(), TimeZone.getDefault(),TimeZone.getTimeZone(timeZoneId));
        }
        return new Date();
    }

    private boolean checkParameters() {
        if (StringUtils.isBlank(username) || zwavedeviceid == 0
                || StringUtils.isBlank(validfrom) || StringUtils.isBlank(validthrough)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setValidfrom(String validfrom) {
        this.validfrom = validfrom;
    }

    public void setValidthrough(String validthrough) {
        this.validthrough = validthrough;
    }

    public int getResultCode() {
        return resultCode;
    }
}
