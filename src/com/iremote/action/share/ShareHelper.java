package com.iremote.action.share;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.Family;
import com.iremote.domain.PhoneUser;
import com.iremote.service.FamilyService;

public class ShareHelper {

	public static int checkfamilyshare(int sharetype , PhoneUser user , PhoneUser touser)
	{
		if ( sharetype == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL )
			return ErrorCodeDefine.SUCCESS;
		
		if ( user.getFamilyid() != null )
		{
			FamilyService fs = new FamilyService();
			Family f = fs.query(user.getFamilyid());
			if ( f.getPhoneuserid() != user.getPhoneuserid() )
				return ErrorCodeDefine.USER_SHARE_CANNOT_RAISE_FAMILY_SHARE;
		}
		
		if ( touser.getFamilyid() != null )
			return ErrorCodeDefine.USER_SHARE_CAN_ACCEPT_ONLY_ONE_FAMILY_SHARE;

		return ErrorCodeDefine.SUCCESS;
	}
	
	public static int sendPushMessage(PhoneUser phoneuser , PhoneUser touser)
	{
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.NOTIFICATION_SHARE_REQUEST,phoneuser.getPlatform(), touser.getLanguage()) ;
		mp.getParameter().put("phonenumber", phoneuser.getPhonenumber());
		String message = mp.getMessage();

		return PushMessage.pushShareRequestMessage(touser.getAlias(), phoneuser.getPlatform(),message);
	}
}
