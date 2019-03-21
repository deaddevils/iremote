package com.iremote.action.share;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserShare;
import com.iremote.domain.UserShareDevice;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserShareService;
import com.opensymphony.xwork2.Action;


//User can raise family shares at will unless he is holding a family share raised by others.
//User can accept only one family share .
@Deprecated
@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "sharedevice", parameters = {"sharedevice","sharedevicetype"})
public class ShareRequestAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	
	private PhoneUser touser ;
	private PhoneUser phoneuser;
	private int shareid ;
	private int type;
	
	private int sharedevicetype;
	private String sharedevice;
	
	
	private UserShareService svr = new UserShareService();
	
	public String execute()
	{		
		PhoneUserService pus = new PhoneUserService();
		touser = pus.query(countrycode , phonenumber , phoneuser.getPlatform());
		
		if ( touser == null )
		{
			resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
			return Action.SUCCESS;
		}
		
		if ( touser.getStatus() != null && IRemoteConstantDefine.USER_STATUS_ENABLED != touser.getStatus())
		{
			resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
			return Action.SUCCESS;
		}
		
		if  (checkThirdpartPrivilege() == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		List<UserShare> lst = svr.query(phoneuser.getPhoneuserid(), touser.getPhoneuserid()) ;
		
		UserShare existshare = null ;
		for ( UserShare us : lst )
		{
			//If a share has been established.
			if ( us != null && us.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL )
			{
				//If the established share is as same as the request one , or the established share is a family share.  
				if ( ( us.getSharetype() == type && us.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL )  
						|| us.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY )
				{
					shareid = us.getShareid();
					return Action.SUCCESS;
				}
			}
			if ( sharedevicetype !=  IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY
					&& us.getSharedevicetype() != IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY
					&& (existshare == null || us.getSharetype() == type ) )
				existshare = us ;
		}
		
		resultCode = ShareHelper.checkfamilyshare(type , phoneuser ,touser); 
		if (  resultCode != ErrorCodeDefine.SUCCESS )
			return Action.SUCCESS;
		
		if ( existshare == null || existshare.getSharetype() != type)
		{
			saveUserShare();
		}else{
			saveZwavedeviceShare(existshare);
		}
		sendPushMessage();

		return Action.SUCCESS;
	}
	
	private boolean checkThirdpartPrivilege()
	{
		CommunityAdministratorService cas = new CommunityAdministratorService();
		CommunityAdministrator ca1 = cas.querybyphoneuserid(phoneuser.getPhoneuserid());
		CommunityAdministrator ca2 = cas.querybyphoneuserid(touser.getPhoneuserid());
		
		if ( ca1 == null && ca2 == null )
			return true;
		if ( ca1 == null || ca2 == null ) 
			return false ;
		return (ca1.getThirdpartid() == ca2.getThirdpartid() && ca1.getCommunityid() == ca2.getCommunityid() );
	}
	
	private void saveUserShare()
	{
		UserShare su = new UserShare();
		su.setShareuserid(phoneuser.getPhoneuserid());
		su.setShareuser(phoneuser.getPhonenumber());
		su.setTouserid(touser.getPhoneuserid());
		su.setTouser(touser.getPhonenumber());
		su.setTousercountrycode(touser.getCountrycode());
		su.setStatus(IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE);
		su.setSharetype(this.type);
		su.setCreatetime(new Date());
		if(sharedevicetype == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL){
			su.setSharedevicetype(sharedevicetype);
		}else if(sharedevicetype == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY){
			su.setSharedevicetype(sharedevicetype);
			su.setUserShareDevices(saveZwavedeviceShare(su));
		}
		shareid = svr.save(su);
	}
	
	public List<UserShareDevice> saveZwavedeviceShare(UserShare su){
		List<UserShareDevice> userShareDevices = su.getUserShareDevices();
		JSONArray sharedeviceja = JSON.parseArray(sharedevice);
		if(sharedeviceja != null && sharedeviceja.size() > 0){
			for(int i=0; i < sharedeviceja.size(); i++ ){
				UserShareDevice userShareDevice = new UserShareDevice();
				JSONObject json = sharedeviceja.getJSONObject(i);
				Integer zwavedeviceid = json.getInteger("zwavedeviceid");
				Integer infrareddeviceid = json.getInteger("infrareddeviceid");
				Integer cameraid = json.getInteger("cameraid");
				if((zwavedeviceid == null || zwavedeviceid == 0) 
						&& (infrareddeviceid == null || infrareddeviceid == 0)
						&& (cameraid == null || cameraid == 0))
					continue;
				boolean isExist = false;;
				for(UserShareDevice ssd : userShareDevices){
					if(( ssd.getZwavedeviceid() != null && ssd.getZwavedeviceid() == zwavedeviceid )
							|| (ssd.getInfrareddeviceid() != null && ssd.getInfrareddeviceid() == infrareddeviceid )
							|| (ssd.getCameraid() != null && ssd.getCameraid() == cameraid))
					{
						isExist = true;
						continue;
					}
				}
				
				if(isExist)
					continue;
				
				if(zwavedeviceid != null && zwavedeviceid > 0){
					userShareDevice.setZwavedeviceid(zwavedeviceid);
				}
				if(infrareddeviceid != null && infrareddeviceid > 0){
					userShareDevice.setInfrareddeviceid(infrareddeviceid);
				}
				if(cameraid != null && cameraid > 0){
					userShareDevice.setCameraid(cameraid);
				}
				userShareDevice.setUserShare(su);
				userShareDevices.add(userShareDevice);
			}
		}
		return userShareDevices;
	}
	
	private void sendPushMessage()
	{
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.NOTIFICATION_SHARE_REQUEST,phoneuser.getPlatform(), touser.getLanguage()) ;
		mp.getParameter().put("phonenumber", phoneuser.getPhonenumber());
		String message = mp.getMessage();
		
		int r = PushMessage.pushShareRequestMessage(touser.getAlias(), phoneuser.getPlatform(),message);
		if ( r != 0 )
			resultCode = ErrorCodeDefine.PUSH_MESSAGE_SEND_FAILED;
	}
	
//	private boolean checkfamilyshare()
//	{
//		if ( sharetype == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL )
//			return true;
//		
//		if ( user.getFamilyid() != null )
//		{
//			FamilyService fs = new FamilyService();
//			Family f = fs.query(user.getFamilyid());
//			if ( f.getPhoneuserid() != user.getPhoneuserid() )
//			{
//				resultCode = ErrorCodeDefine.USER_SHARE_CANNOT_RAISE_FAMILY_SHARE;
//				return false ;
//			}
//		}
//		
//		if ( touser.getFamilyid() != null )
//		{
//			resultCode = ErrorCodeDefine.USER_SHARE_CAN_ACCEPT_ONLY_ONE_FAMILY_SHARE;
//			return false;
//		}
//
//		return true;
//	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getShareid() {
		return shareid;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSharedevicetype(int sharedevicetype) {
		this.sharedevicetype = sharedevicetype;
	}

	public void setSharedevice(String sharedevice) {
		this.sharedevice = sharedevice;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}
