package com.iremote.action.partition;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "zwavedevice", parameter = "zwavedeviceid")
public class QuerySetChannelEnabletatusStatus {
    private Integer zwavedeviceid;
    private Integer channelid;
    private int resultCode = ErrorCodeDefine.SUCCESS;
    private int status;

    public String execute() {
        if (zwavedeviceid == null || channelid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);

        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        if (!IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zd.getDevicetype())){
            resultCode = ErrorCodeDefine.NOT_SUPPORT;
            return Action.SUCCESS;
        }

        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setbypass);

        if (dos == null || dos.getAppendmessage() == null){
            status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_NOT_IN_PROCESSOR;
            return Action.SUCCESS;
        }

        JSONObject json = JSONObject.parseObject(dos.getAppendmessage());
        int channelid0 = json.getIntValue("channelid");
        if (channelid != channelid0){
            status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_NOT_IN_PROCESSOR;
            return Action.SUCCESS;
        }

        if (!dos.isFinished()) {
            if (dos.getExpiretime().getTime() < System.currentTimeMillis()) {
                status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_TIMEOUT;
            } else {
                status = dos.getStatus();
            }
        } else {
            status = dos.getStatus();
        }

        resultCode = ErrorCodeDefine.SUCCESS;
        return Action.SUCCESS;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public int getResultCode() {
        return resultCode;
    }

    public int getStatus() {
        return status;
    }
}
