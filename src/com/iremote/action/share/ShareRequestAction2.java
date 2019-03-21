package com.iremote.action.share;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.push.PushMessage;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
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
@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "cameras", parameter = "cameraids"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "zwavedevices", parameter = "zwavedeviceids"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "infrareddevices", parameter = "infrareddeviceids"),
	})
@Deprecated
public class ShareRequestAction2 {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	
	private PhoneUser touser ;
	private PhoneUser phoneuser;
	private int shareid ;
	private int type;
	
	private int sharedevicetype;
	private Integer[] zwavedeviceids;
	private Integer[] infrareddeviceids;
	private Integer[] cameraids;
	
	
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
		
		if  (checkThirdpartPrivilege() == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		resultCode = ShareHelper.checkfamilyshare(type , phoneuser ,touser); 
		if (  resultCode != ErrorCodeDefine.SUCCESS )
			return Action.SUCCESS;
		
		List<UserShare> lst = svr.query(phoneuser.getPhoneuserid(), touser.getPhoneuserid()) ;
		
		for ( UserShare us : lst )
		{
			if ( us == null )
				continue; 
			//Do not allow any share request when a family share has exists . 
			if ( us.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY)
			{
				resultCode = ErrorCodeDefine.USER_SHARE_SAME_OR_SUPER_ITEM_EXISTS;
				shareid = us.getShareid();
				return Action.SUCCESS;
			}
			
			//Do not allow friend share request when a friend share of all devices exists.
			if ( type == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL 
					&& us.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL
					&& us.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL )
			{
				resultCode = ErrorCodeDefine.USER_SHARE_SAME_OR_SUPER_ITEM_EXISTS;
				shareid = us.getShareid();
				return Action.SUCCESS;
			}
			
			//If a friend share contains all device the request one requests , do not create a new share item.
			if ( type == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL 
					&& sharedevicetype == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY 
					&& us.getSharetype() == IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL  
					&& us.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY )
			{
				if ( isAllDeviceExist( us.getUserShareDevices()))
				{
					resultCode = ErrorCodeDefine.USER_SHARE_SAME_OR_SUPER_ITEM_EXISTS;
					shareid = us.getShareid();
					return Action.SUCCESS;
				}
			}
		}

		saveUserShare();

		sendPushMessage();

		return Action.SUCCESS;
	}
	
	private boolean isAllDeviceExist(List<UserShareDevice> userShareDevices )
	{
		if ( userShareDevices == null || userShareDevices.size() == 0 )
			return false ;
		
		if ( this.zwavedeviceids != null )
		{
			for ( int i = 0 ; i < this.zwavedeviceids.length ; i ++ )
			{
				if ( isZwaveDeviceExists(this.zwavedeviceids[i] , userShareDevices) == false )
					return false ;
			}
		}
		
		if ( this.infrareddeviceids != null )
		{
			for ( int i = 0 ; i < this.infrareddeviceids.length ; i ++ )
			{
				if ( isinfrareddeviceExists(this.infrareddeviceids[i] , userShareDevices) == false )
					return false ;
			}
		}
		
		if ( this.cameraids != null )
		{
			for ( int i = 0 ; i < this.cameraids.length ; i ++ )
			{
				if ( iscameraExists(this.cameraids[i] , userShareDevices) == false )
					return false ;
			}
		}
		return true;
	}
	
	private boolean isZwaveDeviceExists(int zwavedeviceid , List<UserShareDevice> userShareDevices)
	{
		for ( UserShareDevice usd : userShareDevices )
			if ( usd.getZwavedeviceid() != null && usd.getZwavedeviceid() == zwavedeviceid )
				return true ;
		return false ;
	}

	private boolean isinfrareddeviceExists(int infrareddeviceid , List<UserShareDevice> userShareDevices)
	{
		for ( UserShareDevice usd : userShareDevices )
			if ( usd.getInfrareddeviceid() != null && usd.getInfrareddeviceid() == infrareddeviceid )
				return true ;
		return false ;
	}

	private boolean iscameraExists(int cameraid , List<UserShareDevice> userShareDevices)
	{
		for ( UserShareDevice usd : userShareDevices )
			if ( usd.getCameraid() != null && usd.getCameraid() == cameraid )
				return true ;
		return false ;
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
	
	public List<UserShareDevice> saveZwavedeviceShare(UserShare su)
	{
		List<UserShareDevice> lst = new ArrayList<UserShareDevice>();
		
		if ( this.zwavedeviceids != null )
		{
			for ( int i = 0 ; i < this.zwavedeviceids.length ; i ++ )
			{
				UserShareDevice userShareDevice = new UserShareDevice();
				userShareDevice.setZwavedeviceid(this.zwavedeviceids[i]);
				userShareDevice.setUserShare(su);
				lst.add(userShareDevice);
			}
		}
		
		if ( this.infrareddeviceids != null )
		{
			for ( int i = 0 ; i < this.infrareddeviceids.length ; i ++ )
			{
				UserShareDevice userShareDevice = new UserShareDevice();
				userShareDevice.setInfrareddeviceid(this.infrareddeviceids[i]);
				userShareDevice.setUserShare(su);
				lst.add(userShareDevice);
			}
		}
		
		if ( this.cameraids != null )
		{
			for ( int i = 0 ; i < this.cameraids.length ; i ++ )
			{
				UserShareDevice userShareDevice = new UserShareDevice();
				userShareDevice.setCameraid(this.cameraids[i]);
				userShareDevice.setUserShare(su);
				lst.add(userShareDevice);
			}
		}
		
		return lst;
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

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceids(Integer[] zwavedeviceids)
	{
		this.zwavedeviceids = zwavedeviceids;
	}

	public void setInfrareddeviceids(Integer[] infrareddeviceids)
	{
		this.infrareddeviceids = infrareddeviceids;
	}

	public void setCameraids(Integer[] cameraids)
	{
		this.cameraids = cameraids;
	}
	
	
}
