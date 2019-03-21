package com.iremote.action.device.template;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DataDictionary;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.RawCmdTemplate;
import com.iremote.service.DataDictionaryService;
import com.iremote.service.RawCmdTemplateService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;

public class QueryRawCmdTemplateTypeAction {
    private String language;
    private PhoneUser phoneuser;
    private RawCmdTemplate[] rawcmdtemplatetype;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        List<DataDictionary> list = new DataDictionaryService().findByLanguage(StringUtils.isBlank(language) ? PhoneUserHelper.getLanguange(phoneuser) : language);
        rawcmdtemplatetype = new RawCmdTemplate[list.size()];
        for (int i = 0; i < list.size(); i++) {
            RawCmdTemplate template = new RawCmdTemplate();
            DataDictionary dictionary = list.get(i);
            template.setTemplatetype(dictionary.getKey().toUpperCase());
            template.setName(dictionary.getValue());

            rawcmdtemplatetype[i] = template;
        }
        return Action.SUCCESS;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public RawCmdTemplate[] getRawcmdtemplatetype() {
        return rawcmdtemplatetype;
    }

    public int getResultCode() {
        return resultCode;
    }
}
