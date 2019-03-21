package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.service.DeviceOperationStepsService;

import java.util.Date;
import java.util.List;

public class DSCSystemErrorProcessor extends DSCReportBaseProcessor {
    @Override
    protected void updateDeviceStatus() {
        int errorcode = getValue(seriaNetReportBean.getCmd(), 3, 6);
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        List<DeviceOperationSteps> dosList = doss.queryDSCTask(seriaNetReportBean.getDeviceid());
        if (dosList != null) {
            for (DeviceOperationSteps dos : dosList) {
                if (dos == null || dos.getExpiretime().getTime() < new Date().getTime()) {
                    return;
                }
                if (errorcode == 24) {
                    dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PARTITION_NOT_READY);
                    dos.setFinished(true);
                } else if (errorcode == 21) {
                    dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_FAIL);
                } else if (errorcode == 23) {
                    JSONObject json = JSONObject.parseObject(dos.getAppendmessage());
                    Integer toarmstatus = json.getInteger("toarmstatus");
                    if (toarmstatus == IRemoteConstantDefine.DSC_TO_ARM_STATUS_DIS_ARM) {
                        dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PROCESSOR_SUCCESS);
                        dos.setFinished(true);
                    }
                }
            }
        }
    }

    @Override
    protected void pushMessage() {

    }

    @Override
    protected void writeLog() {

    }

    @Override
    protected void init() {

    }
}
