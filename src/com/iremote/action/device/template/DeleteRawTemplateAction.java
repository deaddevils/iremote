package com.iremote.action.device.template;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.RawCmdTemplate;
import com.iremote.service.RawCmdTemplateService;
import com.opensymphony.xwork2.Action;

public class DeleteRawTemplateAction {
    private Integer rawcmdtempalteid;
    private PhoneUser phoneuser;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (rawcmdtempalteid == null) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        RawCmdTemplateService service = new RawCmdTemplateService();
        RawCmdTemplate template = service.query(rawcmdtempalteid);
        if (template == null) {
            resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
        } else {
            if (template.getPhoneuserid() == null
                    || template.getPhoneuserid() != phoneuser.getPhoneuserid()) {
                resultCode = ErrorCodeDefine.NO_PRIVILEGE;
            } else {
                service.delete(template);
            }
        }
        return Action.SUCCESS;
    }

    public void setRawcmdtempalteid(Integer rawcmdtempalteid) {
        this.rawcmdtempalteid = rawcmdtempalteid;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }
}
