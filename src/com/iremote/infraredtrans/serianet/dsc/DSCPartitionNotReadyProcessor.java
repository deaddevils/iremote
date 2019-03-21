package com.iremote.infraredtrans.serianet.dsc;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.Partition;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.PartitionService;

public class DSCPartitionNotReadyProcessor extends DSCPartitionInAlarmProcessor {
    @Override
    protected void updateDeviceStatus() {
        super.updateDeviceStatus();
    }

    @Override
    protected void pushMessage() {

    }

    @Override
    protected void writeLog() {

    }

    @Override
    protected void changeTaskStatus() {
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps querybydeviceidandtype = doss.querybydeviceidandtype(seriaNetReportBean.getDeviceid(), DeviceOperationType.setdscarm, dscpartitionid);
        if (querybydeviceidandtype != null && querybydeviceidandtype.getExpiretime().getTime() > System.currentTimeMillis()) {
            JSONObject jsonObject = JSONObject.parseObject(querybydeviceidandtype.getAppendmessage());
            if (jsonObject != null && jsonObject.getIntValue("dscpartitionid") == dscpartitionid) {
                querybydeviceidandtype.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_PARTITION_NOT_READY);
                querybydeviceidandtype.setFinished(true);
                doss.saveOrUpdate(querybydeviceidandtype);
            }
        }
    }

    @Override
    protected void changePartitionArmStatus(Partition partition) {
    }

    @Override
    protected void init() {
        status = IRemoteConstantDefine.DSC_PARTITION_NOT_READY;
    }

    @Override
    protected void changePartitionStatus() {
        PartitionService ps = new PartitionService();
        Partition query = ps.query(partitionid);
        query.setStatus(status);
        ps.saveOrUpdate(query);
    }
}
