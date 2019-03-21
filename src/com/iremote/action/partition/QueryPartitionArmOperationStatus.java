package com.iremote.action.partition;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.Partition;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.PartitionService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

import java.util.Comparator;
import java.util.List;

@DataPrivileges( dataprivilege = {
        @DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "zwavedevice", parameter = "zwavedeviceid"),
        @DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "partition", parameter = "partitionid")
})
public class QueryPartitionArmOperationStatus {
    protected int zwavedeviceid;
    protected int partitionid;
    protected int status;
    protected int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute() {
        if (zwavedeviceid == 0 && partitionid == 0) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }

        DeviceOperationSteps dos = null;
        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        PartitionService ps = new PartitionService();

        if (zwavedeviceid != 0) {
            ZWaveDeviceService zds = new ZWaveDeviceService();
            ZWaveDevice zd = zds.query(zwavedeviceid);

            if (zd == null) {
                resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                return Action.SUCCESS;
            }
            if (!IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zd.getDevicetype())) {
                resultCode = ErrorCodeDefine.NOT_SUPPORT;
                return Action.SUCCESS;
            }

            if (partitionid != 0) {
                Partition partition = ps.query(partitionid);
                if (partition == null) {
                    resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                    return Action.SUCCESS;
                }
                dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setdscarm, partition.getDscpartitionid());
            }else{
                List<DeviceOperationSteps> deviceOperationSteps = doss.queryDSCTask(zd.getDeviceid());
                for (DeviceOperationSteps deviceOperationStep : deviceOperationSteps) {
                    if (deviceOperationStep.getExpiretime().getTime() > System.currentTimeMillis()) {
                        dos = deviceOperationStep;
                        break;
                    }
                }
                if (dos == null) {
                    status = IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_NOT_IN_PROCESSOR;
                    return Action.SUCCESS;
                }
            }
        } else {
            Partition partition = ps.query(partitionid);
            if (partition == null) {
                resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
                return Action.SUCCESS;
            }
            dos = doss.queryByObjidAndType(partitionid, DeviceOperationType.setzWavePartitionArm);
        }

        if (dos == null) {
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

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setPartitionid(int partitionid) {
        this.partitionid = partitionid;
    }

    public int getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }
}
