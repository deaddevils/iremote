package com.iremote.action.infraredcode2;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.InfraredKey;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.InfraredDeviceService;
import com.opensymphony.xwork2.Action;

public class QueryStatusOfRemoteKeyStudyAction {
    private Integer infrareddeviceid;
    private Integer status;
    private Integer keyindex;

    private String deviceid;
    private InfraredDevice infraredDevice;

    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute()
    {
        getInfraredDevice();
        if(resultCode != ErrorCodeDefine.SUCCESS){
            return Action.SUCCESS;
        }
        DeviceOperationStepsService doss = new DeviceOperationStepsService();

        DeviceOperationSteps dos = doss.querybydeviceidandtypeandinfrareddeviceid(deviceid, DeviceOperationType.remoteInfrareddeviceKey,infrareddeviceid);
        if ( dos == null || dos.getExpiretime().getTime() < System.currentTimeMillis() - 15 * 1000 )
        {
            this.status = GatewayInfrareddeviceStudySteps.normal.getStep();
            return Action.SUCCESS;
        }
        keyindex = dos.getKeyindex();
        status = dos.getStatus();
        return Action.SUCCESS;
    }

    private void getInfraredDevice(){
        if(infrareddeviceid == null){
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return;
        }
        InfraredDeviceService idf = new InfraredDeviceService();
        infraredDevice = idf.query(infrareddeviceid);
        if(infraredDevice == null){
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return;
        }
        deviceid = infraredDevice.getDeviceid();
    }

    public void setInfrareddeviceid(Integer infrareddeviceid) {
        this.infrareddeviceid = infrareddeviceid;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getKeyindex() {
        return keyindex;
    }

    public int getResultCode() {
        return resultCode;
    }
}
