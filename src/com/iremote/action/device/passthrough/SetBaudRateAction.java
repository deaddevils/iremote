package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.NumberUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class SetBaudRateAction {
    private Integer zwavedeviceid;
    private Integer baudrate;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!checkParameters()){
            return Action.SUCCESS;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(zwavedeviceid);
        if (!checkDevice(zd)) {
            return Action.SUCCESS;
        }

        if (sendSetBaudRateCommand(zd)) {
            PhoneUserBlueToothHelper.setDeviceCapability(zd, IRemoteConstantDefine.DEVICE_CAPABILITY_CODE_BAUD_RATE, String.valueOf(baudrate));
        }

        return Action.SUCCESS;
    }

    private boolean sendSetBaudRateCommand(ZWaveDevice zd) {
        byte[] rp = SynchronizeRequestHelper.synchronizeRequest(zd.getDeviceid(), PassThroughDeviceCmdHelper.createCmdBytes(zd.getNuid(), createBytes(baudrate)), 10);
        if (rp == null) {
            resultCode = ErrorCodeDefine.GATEWAY_ERROR_CODE_BUSSING;
            return false;
        }
        resultCode = TlvWrap.readInteter(rp , 1 , TlvWrap.TAGLENGTH_LENGTH);
        return resultCode == ErrorCodeDefine.SUCCESS;
    }

    private boolean checkParameters() {
        if (zwavedeviceid == null || baudrate == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    private boolean checkDevice(ZWaveDevice zd) {
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return false;
        }
        if (!PassThroughDeviceCmdHelper.isPassThroughDevice(zd.getDevicetype())) {
            resultCode = ErrorCodeDefine.NOT_SUPPORT;
            return false;
        }
        if (!ConnectionManager.isOnline(zd.getDeviceid())) {
            resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
            return false;
        }
        return true;
    }

    private byte[] createBytes(Integer baudrate) {
        byte[] cmds = {0x70, 0x04, 0x01, 0x04, 0x00, 0x00, 0x00, 0x00};
        System.arraycopy(NumberUtil.intToByte4(baudrate),0, cmds, 4, 4);
        return cmds;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setBaudrate(Integer baudrate) {
        this.baudrate = baudrate;
    }

    public int getResultCode() {
        return resultCode;
    }
}
