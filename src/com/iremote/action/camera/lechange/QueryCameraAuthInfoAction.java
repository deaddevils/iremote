package com.iremote.action.camera.lechange;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.AuthQrCode;
import com.iremote.domain.PhoneUser;
import com.iremote.service.AuthQrCodeService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryCameraAuthInfoAction {
    private String applianceuuid;
    private PhoneUser phoneuser;
    private String qid;
    private String authtime;
    private String operator;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!PhoneUserHelper.isAdminUser(phoneuser.getPhoneuserid())) {
            resultCode = ErrorCodeDefine.NO_PRIVILEGE;
            return Action.SUCCESS;
        }
        if (StringUtils.isBlank(applianceuuid)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        AuthQrCodeService service = new AuthQrCodeService();
        AuthQrCode authQrCode = service.findByApplianceUuid(applianceuuid);
        if (authQrCode == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        qid = authQrCode.getQid();
        authtime = convertDateTime(authQrCode.getAuthtime());
        operator = authQrCode.getOperator();
        return Action.SUCCESS;
    }

    public static String convertDateTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public void setApplianceuuid(String applianceuuid) {
        this.applianceuuid = applianceuuid;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public String getQid() {
        return qid;
    }

    public String getAuthtime() {
        return authtime;
    }

    public String getOperator() {
        return operator;
    }

    public int getResultCode() {
        return resultCode;
    }
}
