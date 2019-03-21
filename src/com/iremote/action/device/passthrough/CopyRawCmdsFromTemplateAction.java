package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.RawCmdTemplateService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class CopyRawCmdsFromTemplateAction {
    private Integer rawcmdtempalteid;
    private Integer zwavedeviceid;
    private PhoneUser phoneuser;
    private ArrayList<DeviceRawCmd> rawcmds;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute() {
        if (rawcmdtempalteid == null || zwavedeviceid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        RawCmdTemplate template = new RawCmdTemplateService().query(rawcmdtempalteid);
        List<RawCmdTemplateDtl> dtls = template.getRawCmdTemplateDtlList();
        if (template == null || dtls == null || dtls.size() == 0) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
            return Action.SUCCESS;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        DeviceRawCmdService cmdService = new DeviceRawCmdService();
        rawcmds = new ArrayList<>();

        cmdService.deleteByZwaveDeviceId(zwavedeviceid);
        for (RawCmdTemplateDtl dtl : dtls) {
            DeviceRawCmd cmd = new DeviceRawCmd();
            cmd.setZwavedeviceid(zwavedeviceid);
            cmd.setName(dtl.getName());
            cmd.setCmdindex(dtl.getCmdindex());
            cmd.setCmdtype(dtl.getCmdtype());
            cmd.setRawcmd(dtl.getRawcmd());
            cmd.setCreatetime(new Date());

            rawcmds.add(cmd);
            cmdService.save(cmd);
        }

        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
        return Action.SUCCESS;
    }

    public void setRawcmdtempalteid(Integer rawcmdtempalteid) {
        this.rawcmdtempalteid = rawcmdtempalteid;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public ArrayList<DeviceRawCmd> getRawcmds() {
        return rawcmds;
    }

    public int getResultCode() {
        return resultCode;
    }
}
