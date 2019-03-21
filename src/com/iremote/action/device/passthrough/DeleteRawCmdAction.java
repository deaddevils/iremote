package com.iremote.action.device.passthrough;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.PhoneUser;
import com.iremote.service.DeviceRawCmdService;
import com.opensymphony.xwork2.Action;

import java.util.Date;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "devicerawcmd", parameter = "devicerawcmdid")
public class DeleteRawCmdAction {
    private Integer devicerawcmdid;
    private PhoneUser phoneuser;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (devicerawcmdid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }

        DeviceRawCmdService service = new DeviceRawCmdService();
        DeviceRawCmd cmd = service.query(devicerawcmdid);
        if (cmd == null) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
            return Action.SUCCESS;
        }
        service.delete(cmd);

        pushMessage();
        return Action.SUCCESS;
    }

    private void pushMessage() {
        phoneuser.setLastupdatetime(new Date());
        PhoneUserHelper.sendInfoChangeMessage(phoneuser);
    }

    public void setDevicerawcmdid(Integer devicerawcmdid) {
        this.devicerawcmdid = devicerawcmdid;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }
}
