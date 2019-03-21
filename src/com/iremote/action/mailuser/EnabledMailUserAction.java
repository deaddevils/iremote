package com.iremote.action.mailuser;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.sms.RandCodeHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Randcode;
import com.iremote.service.NotificationSettingService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RandcodeService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class EnabledMailUserAction
{
    private String mail;
    private int platform;
    private String randcode;
    private int resultCode = ErrorCodeDefine.SUCCESS ;

    private PhoneUserService us = new PhoneUserService();
    private RandcodeService rs = new RandcodeService();
    private UserService svr = new UserService();

    private PhoneUser phoneuser ;

    public String execute()
    {
        if(!StringUtils.isNotBlank(this.mail)
                || !StringUtils.isNotBlank(this.randcode)){
            resultCode = ErrorCodeDefine.MAIL_FORMAT_ERROR;
            return Action.ERROR;
        }

        if(!JWStringUtils.checkEmail(mail)){
            resultCode = ErrorCodeDefine.MAIL_FORMAT_ERROR;
            return Action.ERROR;
        }
        phoneuser = us.querybymail(mail,platform);
        if(phoneuser != null&&IRemoteConstantDefine.USER_STATUS_ENABLED == phoneuser.getStatus()){
        	return Action.SUCCESS;
        }
        resultCode = RandCodeHelper.checkRandcode(mail , IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_REGISTE, randcode , platform);
        if ( resultCode != ErrorCodeDefine.SUCCESS )
            return Action.ERROR;
        
        if(phoneuser != null){
            if(IRemoteConstantDefine.USER_STATUS_ENABLED == phoneuser.getStatus()){
                resultCode = ErrorCodeDefine.MAIL_HAS_REGISTED;
                return Action.ERROR;
            }
        }else {
            resultCode = ErrorCodeDefine.MAIL_REGISTED_FAIL;
            return Action.ERROR;
        }
        Randcode rc = rs.querybycode(randcode);
        phoneuser.setStatus(IRemoteConstantDefine.USER_STATUS_ENABLED);
        phoneuser.setLastupdatetime(new Date());
        us.save(phoneuser);
        rs.delete(rc);
        saveDefaultNotificationSetting();

        return Action.SUCCESS;
    }


    private void saveDefaultNotificationSetting()
    {
        NotificationSetting s = new NotificationSetting();
        s.setPhonenumber(mail);

        s.setNotificationtype(4);
        s.setAthome(IRemoteConstantDefine.NOTIFICATION_NOTIFY_ME);
        if ( PhoneUserHelper.hasArmFunction(phoneuser))
        {
            s.setStarttime("00:00");
            s.setEndtime("00:00");
        }
        else
        {
            s.setStarttime("00:00");
            s.setEndtime("23:59");
        }
        s.setPhoneuserid(phoneuser.getPhoneuserid());
        NotificationSettingService svr = new NotificationSettingService();
        svr.saveorUpdate(s);
        
        saveDefaultMailNotificationSetting();
    }

    private void saveDefaultMailNotificationSetting(){
    	NotificationSetting s = new NotificationSetting();
        s.setPhonenumber(StringUtils.isEmpty(mail)?phoneuser.getPhonenumber():mail);
        s.setMail(StringUtils.isEmpty(mail)?phoneuser.getPhonenumber():mail);
        s.setNotificationtype(5);
        s.setAthome(IRemoteConstantDefine.NOTIFICATION_NOTIFY_ME);
        if ( PhoneUserHelper.hasArmFunction(phoneuser))
        {
            s.setStarttime("00:00");
            s.setEndtime("00:00");
        }
        else
        {
            s.setStarttime("00:00");
            s.setEndtime("23:59");
        }
        s.setPhoneuserid(phoneuser.getPhoneuserid());
    	NotificationSettingService svr = new NotificationSettingService();
        svr.saveorUpdate(s);
    }
    public int getResultCode() {
        return resultCode;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setRandcode(String randcode) {
        this.randcode = randcode;
    }
}
