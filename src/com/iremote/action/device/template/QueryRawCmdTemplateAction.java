package com.iremote.action.device.template;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.RawCmdTemplate;
import com.iremote.service.RawCmdTemplateService;
import com.opensymphony.xwork2.Action;

import java.util.List;

public class QueryRawCmdTemplateAction {
    private PhoneUser phoneuser;
    private RawCmdTemplate[] rawcmdtemplate;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (phoneuser == null) {
            resultCode = ErrorCodeDefine.SESSION_TIMEOUT;
            return Action.SUCCESS;
        }
        List<Integer> list = PhoneUserHelper.querybySharetoPhoneuserid(phoneuser.getPhoneuserid());
        RawCmdTemplateService service = new RawCmdTemplateService();
        List<RawCmdTemplate> userLevelTemplates = service.queryByPhoneUserId(list);
        List<RawCmdTemplate> systemLevelTemplates = service.querySystemLevelTemplates();
        rawcmdtemplate = new RawCmdTemplate[userLevelTemplates.size() + systemLevelTemplates.size()];

        for (int i = 0; i < userLevelTemplates.size(); i++) {
            insert(userLevelTemplates, i, null);
        }

        for (int i = userLevelTemplates.size(); i < userLevelTemplates.size() + systemLevelTemplates.size(); i++) {
            insert(systemLevelTemplates, i, i - userLevelTemplates.size());
        }
        return Action.SUCCESS;
    }

    private void insert(List<RawCmdTemplate> userLevelTemplates, int i, Integer index) {
        RawCmdTemplate template = new RawCmdTemplate();
        RawCmdTemplate rawCmdTemplate = userLevelTemplates.get(index == null ? i : index);

        template.setRawcmdtemplateid(rawCmdTemplate.getRawcmdtemplateid());
        template.setPhoneuserid(rawCmdTemplate.getPhoneuserid());
        template.setName(rawCmdTemplate.getName());
        template.setType(rawCmdTemplate.getType());
        template.setTemplatetype(rawCmdTemplate.getTemplatetype());

        rawcmdtemplate[i] = template;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public RawCmdTemplate[] getRawcmdtemplate() {
        return rawcmdtemplate;
    }

    public int getResultCode() {
        return resultCode;
    }
}
