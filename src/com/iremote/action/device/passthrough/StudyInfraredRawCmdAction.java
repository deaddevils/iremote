package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.spi.ErrorCode;

import java.util.Calendar;
import java.util.Date;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class StudyInfraredRawCmdAction {
    private Integer zwavedeviceid;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!checkParameters()){
            return Action.SUCCESS;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(zwavedeviceid);
        if (!checkDevice(zd)) {
            return Action.SUCCESS;
        }

        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.remoteInfrareddeviceKey);

        if ( dos != null && dos.getExpiretime().getTime() > System.currentTimeMillis()) {
            this.resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
            return Action.SUCCESS;
        }
        if ( dos == null ) {
            dos = new DeviceOperationSteps();
            dos.setDeviceid(zd.getDeviceid());
            dos.setOptype(DeviceOperationType.remoteInfrareddeviceKey.ordinal());
        }
        dos.setZwavedeviceid(zwavedeviceid);
        dos.setStarttime(new Date());
        dos.setStatus(GatewayInfrareddeviceStudySteps.normal.ordinal());
        dos.setExpiretime(offsetDate(Calendar.SECOND, 10));
        //should be null where init. it's size will use to check package's validity
        dos.setAppendmessage(null);

        CommandTlv ct = PassThroughDeviceCmdHelper.createStudyInfraredCodeCommand(zd.getNuid());

        byte[] result = SynchronizeRequestHelper.synchronizeRequest(zd.getDeviceid(), ct, 1);
        if (result == null) {
            resultCode = ConnectionManager.isOnline(zd.getDeviceid()) ? ErrorCodeDefine.TIME_OUT : ErrorCodeDefine.DEVICE_OFFLINE;
            return Action.SUCCESS;
        }
        resultCode = TlvWrap.readInteter(result , 1 , TlvWrap.TAGLENGTH_LENGTH);
        if(resultCode == ErrorCodeDefine.SUCCESS) {
            dos.setStatus(GatewayInfrareddeviceStudySteps.study.ordinal());
            doss.saveOrUpdate(dos);
        }
        return Action.SUCCESS;
    }

    private boolean checkDevice(ZWaveDevice zd) {
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return false;
        }
        if (!PassThroughDeviceCmdHelper.isInfraredPassThroughDevice(zd.getDevicetype())) {
            resultCode = ErrorCodeDefine.NOT_SUPPORT;
            return false;
        }
        if (!ConnectionManager.isOnline(zd.getDeviceid())) {
            resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
            return false;
        }
        return true;
    }

    public static Date offsetDate(int field, int amount) {
        Calendar c = Calendar.getInstance();
        c.add(field, amount);
        return c.getTime();
    }

    private boolean checkParameters() {
        if (zwavedeviceid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public int getResultCode() {
        return resultCode;
    }
}
