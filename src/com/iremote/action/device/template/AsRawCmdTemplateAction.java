package com.iremote.action.device.template;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.RawCmdTemplateService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameter = "zwavedeviceid")
public class AsRawCmdTemplateAction {
    private Integer zwavedeviceid;
    private String name;
    private String templatetype;
    private Integer rawcmdtempalteid;
    private PhoneUser phoneuser;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!checkParameters()){
            return Action.SUCCESS;
        }

        List<DeviceRawCmd> deviceRawCmds = new DeviceRawCmdService().queryByZwaveDeviceId(zwavedeviceid);
        if (deviceRawCmds == null || deviceRawCmds.size() == 0) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
            return Action.SUCCESS;
        }
        ZWaveDevice zd = new ZWaveDeviceService().query(zwavedeviceid);
        if (zd == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        RawCmdTemplate template = createRawCmdTemplate(PassThroughDeviceCmdHelper.getRawCmdTemplateType(zd.getDevicetype()));

        addRawCmdTemplateDtl(deviceRawCmds, template);
        rawcmdtempalteid = new RawCmdTemplateService().save(template);

        return Action.SUCCESS;
    }

    private void addRawCmdTemplateDtl(List<DeviceRawCmd> deviceRawCmds, RawCmdTemplate template) {
        for (DeviceRawCmd cmd : deviceRawCmds) {
            RawCmdTemplateDtl dtl = new RawCmdTemplateDtl();
            dtl.setCmdindex(cmd.getCmdindex());
            dtl.setCmdtype(cmd.getCmdtype());
            dtl.setName(cmd.getName());
            dtl.setRawcmd(cmd.getRawcmd());
            dtl.setRawCmdTemplate(template);
            template.getRawCmdTemplateDtlList().add(dtl);
        }
    }

    private RawCmdTemplate createRawCmdTemplate(int type) {
        RawCmdTemplate template = new RawCmdTemplate();
        template.setName(name);
        template.setType(type);
        template.setTemplatetype(templatetype);
        template.setPhoneuserid(phoneuser.getPhoneuserid());
        template.setCreatetime(new Date());
        template.setRawCmdTemplateDtlList(new ArrayList<>());
        return template;
    }

    private boolean checkParameters() {
        if (zwavedeviceid == null || StringUtils.isBlank(name) || StringUtils.isBlank(templatetype)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return false;
        }
        return true;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public void setTemplatetype(String templatetype) {
        this.templatetype = templatetype;
    }

    public Integer getRawcmdtempalteid() {
        return rawcmdtempalteid;
    }

    public int getResultCode() {
        return resultCode;
    }
}
