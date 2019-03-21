package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.service.DoorlockUserService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DoorlockSelfDefineOpenwithPwandnoIdReportProcessor
		extends DoorlockSelfDefineOpenwithPasswordReportProcessor {

	private DoorlockUser newdu;
	
	@Override
	protected void initusercode() 
	{
		usertype = zrb.getCmd()[8] & 0xff ;
		usercode = zrb.getCmd()[9] & 0xff ;
		
		//if ( usercode != 0 )
		{
			username = queryDoorlockusername();
			if ( username == null )
				username = queryDoorlockTempPasswordName(usertype, usercode);
			if ( username != null )
				return ;
		}
		
		if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USERTYPE_PASSWORD
				&& Utils.isTempPassword(usercode))
		{
			username = MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_TEMP_USER,0, getLanguange());
			return ;
		}
		
		String mk = null ;
		switch( usertype )
		{
		case 0x00: 
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_KEY_USER ;
			break;
		case 0x15 :
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER;
			break;
		case 0x16 :
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FINGER_USER;
			break;
		case 0x20:
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER;
			break;
		case 0x27:
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_FACE_USER;
			break;
		case 0x29:
			mk = IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_BLUE_TOOTH_USER;
			break;
		default :
			return ;
		}
		if (username == null) {
			if (usertype == 0x29) {
				username = createUserName(getPhoneUserName(), MessageManager.getmessage(mk, 0, getLanguange()));
			} else {
				username = String.format("%s%d", MessageManager.getmessage(mk, 0, getLanguange()), usercode);
			}
		}

	}
	
	private String queryDoorlockusername()
	{
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(super.zrb.getDevice().getZwavedeviceid(), usertype , usercode);
		
		if ( du == null && isValidDoorlockUser() )
		{
			newdu = new DoorlockUser();
			newdu.setUsercode(usercode);
			newdu.setUsertype(usertype);
			newdu.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
			newdu.setUsername(String.valueOf(usercode));
			
			dus.save(newdu);
			
			return null ;
		}
		else if ( du == null )
			return null ;
		return du.getUsername();
	}

	private String queryDoorlockTempPasswordName(int usertype , int usercode)
	{
		if ( !GatewayUtils.isCobbeLock(this.zrb.getRemote()) )
			return null;
		
		if ( usertype != 0x15 ) //password
			return null;
		
		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		
		DoorlockPassword dp = dpsvr.queryLatestActivePassword(super.zrb.getDevice().getZwavedeviceid(), 1,usercode);
		if ( dp == null || dp.getPasswordtype() != IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP)
			return null;
		return dp.getPhonenumber();
	}

	private boolean isValidDoorlockUser()
	{
		if ( !Utils.isLockTempPassordforSMSSend(usercode) 
				&& !Utils.isTempPassword(usercode)
				&& ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD 
					|| usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_FINGERPRINT 
					|| usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD) )
			return true ;
		return false ;
	}
	
	@Override
	protected void afterprocess()
	{
		super.afterprocess();
		
		if ( !isValidDoorlockUser())
			return ;
		
		JSONObject json = new JSONObject();
		json.put("edit", "lockusername");
		
		this.notification.setAppendjson(json);
		
		if ( newdu != null )
			newdu.setUsername(username);
	}
	
}
