package com.iremote.action.share;

import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.mail.MailInterface;
import com.iremote.common.sms.SMSInterface;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Randcode;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RandcodeService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class InviteAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	private PhoneUser user;
	private RandcodeService svr = new RandcodeService();
	
	public String execute()
	{
		user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser fromuser = pus.query(countrycode , phonenumber , user.getPlatform());
		if ( fromuser != null )
		{
			if(IRemoteConstantDefine.PHONEUSER_USER_TYPE_MAIL == fromuser.getUsertype() && fromuser.getStatus() == 0){
			}else{
				resultCode = ErrorCodeDefine.USER_HAS_REGISTED;
				return Action.SUCCESS;
			}
		}
		
		if ( checkRandcodeNumber() == false )  // Just for preventing too many invitations to somebody
		{
			resultCode = ErrorCodeDefine.EXCEED_MAX_REQUEST_TIMES;
			return Action.SUCCESS;
		}
		int result = -1;
		if(IRemoteConstantDefine.PHONEUSER_USER_TYPE_MAIL == user.getUsertype()){
			result = MailInterface.sendInvitation( user.getPhonenumber() ,phonenumber , user.getPlatform() , user.getLanguage());
		}else{
			result = SMSInterface.sendInvitation(countrycode , user.getPhonenumber(), phonenumber , user.getPlatform() , user.getLanguage());
		}

		if ( result != 0 )
			resultCode = ErrorCodeDefine.SMS_SEND_FAILED;
		else 
		{
			Randcode r = new Randcode(countrycode ,"" ,phonenumber , 3);  // Just for preventing too many invitation to somebody
			r.setPlatform(user.getPlatform());
			svr.save(r);
		}
				
		return Action.SUCCESS;
	}
	
	

	private boolean checkRandcodeNumber()
	{
		List<Randcode> lst = svr.querybyphonenumber(countrycode , phonenumber , 3 , user.getPlatform());
		if ( lst == null || lst.size() <= 10 )
			return true;
		return false ;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}



	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
}
