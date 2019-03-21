package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.devicecommand.CommandHelper;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class SendRawCmdAction {
    private Integer zwavedeviceid;
    private Integer devicerawcmdid;
    private String rawcmd;
    private int cmdindex;
    private Remote remote;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute() {
        if (!checkParameters()){
            return Action.SUCCESS;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        if (!ConnectionManager.isOnline(zd.getDeviceid())) {
            resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
            return Action.SUCCESS;
        }
        remote = new RemoteService().querybyDeviceid(zd.getDeviceid());
        if (remote == null) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
            return Action.SUCCESS;
        }
        if (devicerawcmdid != null) {
            DeviceRawCmd cmd = new DeviceRawCmdService().query(devicerawcmdid);
            if (cmd == null) {
                resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
                return Action.SUCCESS;
            }
            rawcmd = cmd.getRawcmd();
            cmdindex = cmd.getCmdindex();
        }

        cmdindex = PassThroughDeviceCmdHelper.getCmdIndexByDeviceStatus(cmdindex, zd.getStatus());

        sendRawCommand(zd, JWStringUtils.hexStringtobyteArray(rawcmd), cmdindex);

        return Action.SUCCESS;
    }

    private boolean checkParameters() {
        if (zwavedeviceid == null || (devicerawcmdid == null && StringUtils.isBlank(rawcmd))) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        try {
            JWStringUtils.hexStringtobyteArray(rawcmd = rawcmd.replace(" ", ""));
        } catch (Exception e) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    private void sendRawCommand(ZWaveDevice zd, byte[] rawcmd, int cmdindex) {
        byte[][] cmdPackages = PassThroughDeviceCmdHelper.createCmdPackages(rawcmd, cmdindex);
        if (cmdPackages == null) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
            return;
        }
        ArrayList<CommandTlv> list = new ArrayList<>();
        for (byte[] cmdPackage : cmdPackages) {
            list.add(PassThroughDeviceCmdHelper.createCmdBytes(zd.getDeviceid(), zd.getNuid(), cmdPackage));
        }
        List<CommandTlv> commandTlvs = CommandHelper.mergeCommand(remote, list);
        if (commandTlvs != null) {
            for (CommandTlv commandTlv : commandTlvs) {
                byte[] rp = SynchronizeRequestHelper.synchronizeRequest(zd.getDeviceid(), commandTlv, 10);
                if (rp == null) {
                    resultCode = ErrorCodeDefine.TIME_OUT;
                    return;
                } else {
                    resultCode = TlvWrap.readInteter(rp , 1 , TlvWrap.TAGLENGTH_LENGTH);
                    if (resultCode != ErrorCodeDefine.SUCCESS) {
                        break;
                    }
                }
            }
        }
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setDevicerawcmdid(Integer devicerawcmdid) {
        this.devicerawcmdid = devicerawcmdid;
    }

    public void setRawcmd(String rawcmd) {
        this.rawcmd = rawcmd;
    }

    public void setCmdindex(int cmdindex) {
        this.cmdindex = cmdindex;
    }

    public int getResultCode() {
        return resultCode;
    }
}
