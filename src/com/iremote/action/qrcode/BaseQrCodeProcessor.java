package com.iremote.action.qrcode;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;

public class BaseQrCodeProcessor implements IScanQrCodeProcessor {
    @Override
    public void setMessage(String message) {

    }

    @Override
    public void setAppendmessage(String appendmessage) {

    }

    @Override
    public void setPhoneuser(PhoneUser phoneuser) {

    }

    @Override
    public void setTimezoneid(String timezoneid) {

    }

    @Override
    public String execute() {
        return null;
    }

    @Override
    public int getResultCode() {
        return ErrorCodeDefine.PARMETER_ERROR;
    }
}
