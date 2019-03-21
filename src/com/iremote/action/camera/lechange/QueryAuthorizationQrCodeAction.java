package com.iremote.action.camera.lechange;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.AuthQrCode;
import com.iremote.domain.PhoneUser;
import com.iremote.service.AuthQrCodeService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

public class QueryAuthorizationQrCodeAction {
    private String qid;
    private String applianceid;
    private String authtime;
    private String operator;
    private PhoneUser phoneuser;
    private Integer status;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
        if (!PhoneUserHelper.isAdminUser(phoneuser.getPhoneuserid())) {
            resultCode = ErrorCodeDefine.NO_PRIVILEGE;
            return Action.SUCCESS;
        }
        if (StringUtils.isBlank(qid)) {
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        AuthQrCodeService service = new AuthQrCodeService();
        AuthQrCode authQrCode = service.findByQid(qid);
        if (authQrCode == null) {
            resultCode = ErrorCodeDefine.QR_CODE_INVALID;
            return Action.SUCCESS;
        }
        applianceid = authQrCode.getApplianceuuid();
        authtime = QueryCameraAuthInfoAction.convertDateTime(authQrCode.getAuthtime());
        operator = authQrCode.getOperator();
        status = authQrCode.getStatus();
        return Action.SUCCESS;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public void setPhoneuser(PhoneUser phoneuser) {
        this.phoneuser = phoneuser;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getApplianceid() {
        return applianceid;
    }

    public String getAuthtime() {
        return authtime;
    }

    public String getOperator() {
        return operator;
    }

    public Integer getStatus() {
        return status;
    }
}
