package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "devicerawcmd", parameter = "devicerawcmdid")
public class EditRawCmdAction {
    private Integer devicerawcmdid;
    private String name;
    private Integer cmdindex;
    private String rawcmd;
    private PhoneUser phoneuser;
    private ZWaveDevice zd;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!checkParameters()){
            return Action.SUCCESS;
        }
        DeviceRawCmdService service = new DeviceRawCmdService();
        DeviceRawCmd cmd = service.query(devicerawcmdid);
        if (cmd == null) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
            return Action.SUCCESS;
        }
        if (!checkDevice(cmd.getZwavedeviceid())) {
            resultCode = ErrorCodeDefine.NOT_SUPPORT;
            return Action.SUCCESS;
        }
        if (StringUtils.isNotBlank(name)) {
            cmd.setName(name);
        }

        manageCmdIndex(cmd, service);

        if (cmdindex != null) {
            cmd.setCmdindex(cmdindex);
        }
        if (StringUtils.isNotBlank(rawcmd)) {
            cmd.setRawcmd(rawcmd);
        }

        pushMessage();
        return Action.SUCCESS;
    }

    private void manageCmdIndex(DeviceRawCmd cmd, DeviceRawCmdService service) {
        if (cmdindex == null || cmd.getCmdindex() == cmdindex.intValue()) {
            return;
        }
        if (PassThroughDeviceCmdHelper.checkCmdIndexIfPower(cmdindex)) {
            service.deleteByCmdIndex(cmdindex, zd.getZwavedeviceid());
        }
    }

    private void pushMessage() {
        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
    }

    private boolean checkDevice(int zwavedeviceid) {
        zd = new ZWaveDeviceService().query(zwavedeviceid);
        return zd != null && PassThroughDeviceCmdHelper.isPassThroughDevice(zd.getDevicetype());
    }

    private boolean checkParameters() {
        if (devicerawcmdid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        if (StringUtils.isNotBlank(rawcmd)) {
            try {
                JWStringUtils.hexStringtobyteArray(rawcmd = rawcmd.replace(" ", ""));
            } catch (Exception e) {
                resultCode = ErrorCodeDefine.PARMETER_ERROR;
                return false;
            }
        }
        return true;
    }

    public void setDevicerawcmdid(Integer devicerawcmdid) {
        this.devicerawcmdid = devicerawcmdid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCmdindex(Integer cmdindex) {
        this.cmdindex = cmdindex;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public void setRawcmd(String rawcmd) {
        this.rawcmd = rawcmd;
    }

    public int getResultCode() {
        return resultCode;
    }
}
