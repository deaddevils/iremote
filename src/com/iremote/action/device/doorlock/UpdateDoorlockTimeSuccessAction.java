package com.iremote.action.device.doorlock;

import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.Notification;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameter = "zwavedeviceid")
public class UpdateDoorlockTimeSuccessAction {
    private Integer zwavedeviceid;
    private Integer newsequence;
    private PhoneUser phoneuser;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        ZWaveDeviceService zds = new ZWaveDeviceService();
        ZWaveDevice zd = zds.query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }

        Notification notification = new Notification();
        notification.setPhoneuserid(phoneuser.getPhoneuserid());
        notification.setDeviceid(zd.getDeviceid());
        notification.setZwavedeviceid(zwavedeviceid);
        notification.setReporttime(new Date());
        notification.setMessage(IRemoteConstantDefine.NOTIFICATION_TYPE_LOCK_TIMES_SYNCHRONIZED);
        notification.setName(zd.getName());
        notification.setMajortype(zd.getMajortype());
        notification.setDevicetype(zd.getDevicetype());
        notification.setAppendmessage(phoneuser.getPhonenumber());

        JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);

        if (newsequence != null) {
            JSONObject json = new JSONObject();
            json.put("newsequence", newsequence);
            notification.setAppendjson(json);
            notification.setMessage(IRemoteConstantDefine.NOTIFICATION_TYPE_BLUE_TOOTH_SEQUENCE_CHANGED);

            JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, notification);
        }

        return Action.SUCCESS;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setNewsequence(Integer newsequence) {
        this.newsequence = newsequence;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }
}
