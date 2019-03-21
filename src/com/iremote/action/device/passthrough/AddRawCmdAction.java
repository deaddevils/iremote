package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
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

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class AddRawCmdAction {
    private Integer zwavedeviceid;
    private String name;
    private Integer cmdindex;
    private String rawcmd;
    private Integer devicerawcmdid;
    private PhoneUser phoneuser;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!checkParameters()){
            return Action.SUCCESS;
        }

        DeviceRawCmdService service = new DeviceRawCmdService();
        DeviceRawCmd deviceRawCmd = null;
        if (PassThroughDeviceCmdHelper.checkCmdIndexIfPower(cmdindex)) {
            deviceRawCmd = service.queryUniqueByCmdindex(cmdindex, zwavedeviceid);
        }
        if (deviceRawCmd == null || !PassThroughDeviceCmdHelper.checkCmdIndexIfPower(cmdindex)){
            deviceRawCmd = new DeviceRawCmd();
            deviceRawCmd.setCmdtype(IRemoteConstantDefine.PASS_THROUGH_DEVICE_CMD_TYPE_OF_BYTE_ARRAY);
        }
        deviceRawCmd.setCmdindex(cmdindex);
        deviceRawCmd.setName(name);
        deviceRawCmd.setRawcmd(rawcmd.replace(" ", ""));
        deviceRawCmd.setZwavedeviceid(zwavedeviceid);
        deviceRawCmd.setCreatetime(new Date());

        devicerawcmdid = deviceRawCmd.getDevicerawcmdid() == 0
                ? service.save(deviceRawCmd) : deviceRawCmd.getDevicerawcmdid();

        pushMessage();
        return Action.SUCCESS;
    }

    private void pushMessage() {
        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
    }

    private boolean checkParameters() {
        if (StringUtils.isBlank(name) || cmdindex == null || StringUtils.isBlank(rawcmd) || zwavedeviceid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return false;
        }

        try {
            JWStringUtils.hexStringtobyteArray(rawcmd);
        } catch (Exception e) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCmdindex(Integer cmdindex) {
        this.cmdindex = cmdindex;
    }

    public void setRawcmd(String rawcmd) {
        this.rawcmd = rawcmd;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public Integer getDevicerawcmdid() {
        return devicerawcmdid;
    }

    public int getResultCode() {
        return resultCode;
    }
}
