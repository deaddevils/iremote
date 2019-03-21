package com.iremote.action.device.passthrough;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class QueryStatusOfInfraredRawCmdStudyAction {
    private Integer zwavedeviceid;
    private int status;
    private String rawcmd;
    private int resultCode = ErrorCodeDefine.SUCCESS;
    public static final String END_PACKAGE_INDEX = "255";

    public String execute() {
        if (zwavedeviceid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        DeviceOperationStepsService doss = new DeviceOperationStepsService();

        DeviceOperationSteps dos = doss.queryByDeviceidAndTypeAndZwaveDeviceId(zd.getDeviceid(),
                DeviceOperationType.remoteInfrareddeviceKey, zd.getZwavedeviceid());
        if (dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis() - 15 * 1000) {
            this.status = GatewayInfrareddeviceStudySteps.normal.getStep();
            return Action.SUCCESS;
        }

        status = dos.getStatus();
        if (status == GatewayInfrareddeviceStudySteps.finished.ordinal()) {
            rawcmd = processorRawCmd(dos.getAppendmessage());
        }
        return Action.SUCCESS;
    }

    private String processorRawCmd(String appendMessage) {
        JSONObject json = JSONObject.parseObject(appendMessage);
        if (json.size() == 0) {
            return null;
        } else if (json.size() == 1) {
            return json.getString("0").replace(" ","");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < json.size(); i++) {
                sb.append(json.getString(String.valueOf(i)).replaceAll(" ",""));
            }
            sb.append(json.getString(END_PACKAGE_INDEX).replace(" ", ""));
            return sb.toString();
        }
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public int getStatus() {
        return status;
    }

    public String getRawcmd() {
        return rawcmd;
    }

    public int getResultCode() {
        return resultCode;
    }
}
