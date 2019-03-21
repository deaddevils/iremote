package com.iremote.action.share;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserShare;
import com.iremote.service.UserShareService;
import com.iremote.vo.Share;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class QueryShareRequestAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private List<Share> sharerequest;
	
	public String execute()
	{
		PhoneUser user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		UserShareService svr = new UserShareService();
		List<UserShare> lst = svr.queryUnprocessedShareRequest(user.getPhoneuserid());
		
		sharerequest = new ArrayList<Share>();
		
		for ( UserShare us : lst )
		{
			Share s = new Share() ;
			s.setShareid(us.getShareid());
			s.setPhonenumber(us.getShareuser());
			s.setType(us.getSharetype());
			sharerequest.add(s);
		}
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public List<Share> getSharerequest() {
		return sharerequest;
	}

}
