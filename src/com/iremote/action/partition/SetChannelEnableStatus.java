package com.iremote.action.partition;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ChannelEnableEvent;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveSubDeviceService;
import com.opensymphony.xwork2.Action;

import java.util.Date;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "zwavedevice", parameter = "zwavedeviceid")
public class SetChannelEnableStatus {
    private Integer zwavedeviceid;
    private Integer channelid;
    private String password;
    private int enablestatus;
    private int resultCode;
    private PhoneUser phoneuser;
    private boolean isFinished = false;

    public String execute() {
        if (!init()) {
            return Action.SUCCESS;
        }

        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (!queryDevice(zd)) {
            return Action.SUCCESS;
        }

        DeviceOperationStepsService doss = new DeviceOperationStepsService();
        DeviceOperationSteps dos = doss.querybydeviceidandtype(zd.getDeviceid(), DeviceOperationType.setbypass);
        if (dos == null) {
            dos = new DeviceOperationSteps();
        }else{
            if (!isFinished(dos)) {
                resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
                return Action.SUCCESS;
            }
        }
        initTask(dos);

        createTask(zd, dos, doss);

        if (!isFinished) {
            ChannelEnableEvent cee = new ChannelEnableEvent(channelid, password, enablestatus, zd);
            JMSUtil.sendmessage(IRemoteConstantDefine.SET_CHANNEL_ENABLE_STATUS_TASK, cee);
        }

        resultCode = ErrorCodeDefine.SUCCESS;
        return Action.SUCCESS;
    }

    private boolean queryDevice(ZWaveDevice zd) {
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return false;
        } else if (!IRemoteConstantDefine.DEVICE_TYPE_DSC.equals(zd.getDevicetype())) {
            resultCode = ErrorCodeDefine.NOT_SUPPORT;
            return false;
        }

        ZWaveSubDeviceService zsds = new ZWaveSubDeviceService();
        ZWaveSubDevice zWaveSubDevice = zsds.queryByZwavedeviceidAndChannelid(zwavedeviceid, channelid);
        if (zWaveSubDevice == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return false;
        }
        isFinished = zWaveSubDevice.getEnablestatus() == enablestatus;
        return true;
    }

    private void createTask(ZWaveDevice zd, DeviceOperationSteps dos, DeviceOperationStepsService doss) {
        JSONObject json = new JSONObject();
        json.put("enablestatus", enablestatus);
        json.put("password", password);
        json.put("channelid", channelid);
        json.put("phonenumber", phoneuser.getPhonenumber());

        dos.setAppendmessage(json.toJSONString());
        dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_IN_PROCESSOR);
        dos.setDeviceid(zd.getDeviceid());
        dos.setStarttime(new Date());
        dos.setExpiretime(new Date(System.currentTimeMillis() + 50 * 1000));
        dos.setFinished(isFinished);
        dos.setOptype(DeviceOperationType.setbypass.ordinal());
        dos.setZwavedeviceid(zwavedeviceid);

        doss.saveOrUpdate(dos);
    }

    private boolean init() {
        if (zwavedeviceid == null || channelid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        if (password == null || (password.length() != 4 && password.length() != 6)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    private void initTask(DeviceOperationSteps dos) {
        dos.setAppendmessage(null);
        dos.setFinished(false);
        dos.setStarttime(null);
        dos.setExpiretime(new Date(System.currentTimeMillis() - 1000));
        dos.setStatus(IRemoteConstantDefine.DSC_PARTITION_ARLAM_STATUS_NOT_IN_PROCESSOR);
    }

    private boolean isFinished(DeviceOperationSteps dos) {
        return dos.isFinished() || System.currentTimeMillis() > dos.getExpiretime().getTime();
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnablestatus(int enablestatus) {
        this.enablestatus = enablestatus;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }
}
