package com.iremote.action.infraredcode2;


import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.InfraredDeviceService;
import com.opensymphony.xwork2.Action;

import java.util.Calendar;
import java.util.Date;

public class RemoteKeyStudyAction {
    private Integer infrareddeviceid;
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
        if ( !ConnectionManager.isOnline(deviceid))
        {
            this.resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
            return Action.SUCCESS;
        }

        DeviceOperationStepsService doss = new DeviceOperationStepsService();

        DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid, DeviceOperationType.remoteInfrareddeviceKey);

        if ( dos != null && dos.getExpiretime().getTime() > System.currentTimeMillis())
        {
            this.resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
            return Action.SUCCESS;
        }

        if ( dos == null )
        {
            dos = new DeviceOperationSteps();
            dos.setDeviceid(deviceid);
            dos.setOptype(DeviceOperationType.remoteInfrareddeviceKey.ordinal());
        }
        dos.setInfrareddeviceid(infrareddeviceid);
        dos.setStarttime(new Date());
        dos.setStatus(GatewayInfrareddeviceStudySteps.normal.ordinal());
        dos.setKeyindex(keyindex);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 10);
        dos.setExpiretime(c.getTime());

        doss.saveOrUpdate(dos);

        CommandTlv ct = new CommandTlv(TagDefine.COMMAND_SUB_CLASS_INFRAREKEYSTUDY,TagDefine.COMMAND_SUB_CLASS_INFRAREKEYSTUDY_REQUEST);

        SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 1);

        return Action.SUCCESS;
    }


    private void getInfraredDevice(){
        if(infrareddeviceid == null || keyindex == null){
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

    public void setKeyindex(Integer keyindex) {
        this.keyindex = keyindex;
    }

    public int getResultCode() {
        return resultCode;
    }
}

