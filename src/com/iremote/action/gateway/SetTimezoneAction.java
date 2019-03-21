package com.iremote.action.gateway;

import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.Timezone;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class SetTimezoneAction {
	
    private String deviceid;
    private String timezoneid;
    private PhoneUser phoneuser;
    private int resultCode = ErrorCodeDefine.SUCCESS;

    public String execute(){
    	if(StringUtils.isBlank(deviceid)||StringUtils.isBlank(timezoneid)){
    		resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
    	}
    	RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
        if (r == null) {
            resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
            return Action.SUCCESS;
        }
        
		TimeZone tz = TimeZone.getTimeZone(timezoneid);
		if ( tz != null && timezoneid.equals(tz.getID())){
			if ( r.getTimezone() == null ){
				r.setTimezone(new Timezone());
			}
			r.getTimezone().setId(tz.getID());
			r.getTimezone().setZonetext(TimeZoneHelper.createTimezoneDisplayname(tz , phoneuser));
			r.getTimezone().setZoneid(TimeZoneHelper.createTimezoneOffsetHour(tz));
			return Action.SUCCESS;
		}
        resultCode = ErrorCodeDefine.PARMETER_ERROR;
        return Action.SUCCESS;
    }

    public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}


	public void setTimezoneid(String timezoneid) {
		this.timezoneid = timezoneid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public int getResultCode() {
        return resultCode;
    }
}
