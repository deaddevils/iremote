package com.iremote.action.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhoneUserHelper {
	private static Log log = LogFactory.getLog(PhoneUserHelper.class);

	public static final int DEFAULT_USER_CODE = 1;
	public static boolean checkModifyPrivilege(PhoneUser user , String deviceid)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null || r.getPhoneuserid() == null || !r.getPhoneuserid().equals(user.getPhoneuserid()) )
			return false ;
		return true;
	}
	
	public static boolean checkPrivilege(PhoneUser user , ZWaveDevice zwavedevice)
	{
		if ( checkPrivilege(user , zwavedevice.getDeviceid()))
			return true; 
		if ( checkZwaveDevicePrivilege(user , zwavedevice))
			return true ;
		return false ;
	}
	
	public static boolean checkIsMyZWaveDevice(PhoneUser user,ZWaveDevice zwavedevice){
		String deviceid = zwavedevice.getDeviceid();
		IremotepasswordService svr = new IremotepasswordService();
		List<String> deviceidlist = svr.queryDeviceidbyPhoneUserid(user.getPhoneuserid());
		if(deviceidlist.contains(deviceid)){
			return true;
		}
		return false;
	}
	
	public static boolean checkPrivilege(PhoneUser user , String deviceid)
	{
		return checkPrivilege(user.getPhoneuserid() , deviceid);
	}
	
	private static boolean checkPrivilege(int phoneuserid , String deviceid)
	{
		List<Integer> lst = querybySharetoPhoneuserid(phoneuserid);
		
		IremotepasswordService svr = new IremotepasswordService();
		
		List<String> devicelst = svr.queryDeviceidbyPhoneUserid(lst) ;
		if ( devicelst.contains(deviceid) )
			return true;

		return false ;
	}
	
	public static boolean checkZwaveDevicePrivilege(PhoneUser user ,ZWaveDevice zwavedevice)
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<ZWaveDeviceShare> lst = zdss.query(zwavedevice.getZwavedeviceid(), user.getPhoneuserid()) ;
		if ( lst != null && lst.size() > 0 )
			return true ;
		lst = zdss.query(zwavedevice.getDeviceid(),0, user.getPhoneuserid());
		if ( lst != null && lst.size() > 0 )
			return true ;
		return false ;
	}
	
	
	//Query all devices that share to me , include mine.
	//In other words , all devices I have operation privilege.
	public static List<String> queryDeviceidbySharetoPhoneuserid(int phoneuserid)
	{
		List<Integer> lst = querybySharetoPhoneuserid(phoneuserid);
		
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> l = svr.querybyPhoneUserid(lst);
		
		List<String> rl = new ArrayList<String>(l.size());
		for ( Remote r : l )
			rl.add(r.getDeviceid());
		
		return rl ;
	}
	
	
	//Query all phone user that share their gateway to me , include myself. 
	public static List<Integer> querybySharetoPhoneuserid(Integer phoneuserid)
	{
		UserShareService svr = new UserShareService();
		List<UserShare> lst = svr.querybytoUser(phoneuserid);
		
		List<Integer> l = new ArrayList<Integer>();
		l.add(phoneuserid);
		
		for ( UserShare us : lst )
			if ( us.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL 
				&& us.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL)
				l.add(us.getShareuserid());
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(phoneuserid);
		
		l.addAll(queryPhoneuseridbyfamilyid(pu.getFamilyid()));
		
		return l;
	}
	
	public static List<Integer> queryPhoneuseridbyfamilyid(Integer familyid)
	{
		List<Integer> lst = new ArrayList<Integer>();
		if ( familyid == null )
			return lst ;
		
		PhoneUserService pus = new PhoneUserService();
		List<PhoneUser> fpul = pus.querybyfamiliyid(familyid);
		
		
		for ( PhoneUser pu : fpul)
			lst.add(pu.getPhoneuserid());
		return lst ;
	}
	
	//Query all user to who I have shared one or more my devices , include myself . 
	public static List<Integer> querySharetoPhoneuserid(int phoneuserid)
	{
		UserShareService svr = new UserShareService();
		List<UserShare> lst = svr.querybyShareUser(phoneuserid);
		
		List<Integer> l = new ArrayList<Integer>();
		l.add(phoneuserid);
		
		for ( UserShare us : lst )
		{
			if ( us.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL)
				l.add(us.getTouserid());
		}
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(phoneuserid);
		
		if ( pu == null )
			return l ;
		l.addAll(queryPhoneuseridbyfamilyid(pu.getFamilyid()));
		
		return l;
	}
	
	//Query all user to who I have shared all my devices , include myself
	//In other words , Query all user that have operation privilege of all my devices ;
	public static List<Integer> queryAuthorityPhoneuserid(int phoneuserid)
	{
		UserShareService svr = new UserShareService();
		List<UserShare> lst = svr.querybyShareUser(phoneuserid);
		
		List<Integer> l = new ArrayList<Integer>();
		l.add(phoneuserid);
		
		for ( UserShare us : lst )
		{
			if ( us.getStatus() == IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL
					&& us.getSharedevicetype() == IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL)
				l.add(us.getTouserid());
		}
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(phoneuserid);
		
		if ( pu == null )
			return l ;
		l.addAll(queryPhoneuseridbyfamilyid(pu.getFamilyid()));
		
		return l;
	}
	
	public static List<Integer> queryPhoneuseridbyDeviceShare(String deviceid , Integer zwavedeviceid , Integer cameraid)
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<Integer> lst = zdss.queryPhoneuseidbydevice(deviceid, zwavedeviceid);
		lst.addAll(zdss.queryPhoneuseridbyCameraid(cameraid));
		return lst ;
	}
	
	public static List<Integer> queryPhoneuseridbyDeviceShare(String deviceid , Integer zwavedeviceid)
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		return zdss.queryPhoneuseidbydevice(deviceid, zwavedeviceid);
	}
	
	public static List<String> queryAuthorityAliasBybycameraid(int phoneuserid , int cameraid )
	{
		List<Integer> pn = queryAuthorityPhoneuserid(phoneuserid);
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		pn.addAll(zdss.queryPhoneuseridbyCameraid(cameraid));
		
		PhoneUserService pus = new PhoneUserService();
		return pus.queryAlias(pn);
	}
	
	public static List<String> queryAuthorityAliasByDeviceid(String deviceid , Integer zwavedeviceid)
	{
		return queryAuthorityAliasByDeviceid(deviceid , zwavedeviceid , null );
	}

	public static List<String> queryAuthorityAliasByPhoneuserid(Integer phoneuserid)
	{
		List<Integer> phoneuserids = queryAuthorityPhoneuserid(phoneuserid);
		PhoneUserService pus = new PhoneUserService();
		List<String> alias = pus.query(phoneuserids).stream().map(PhoneUser::getAlias).collect(Collectors.toList());
		return alias;
	}

	public static List<String> queryAuthorityAliasByDevicetypeOrPhoneuserid(String deviceid , Integer zwavedeviceid, Integer phoneuserid)
	{
		if (phoneuserid == null){
			return queryAuthorityAliasByDeviceid(deviceid, zwavedeviceid);
		}
		return queryAuthorityAliasByPhoneuserid(phoneuserid);
	}

	public static List<String> queryAuthorityAliasByDeviceid(String deviceid , Integer zwavedeviceid , Integer cameraid)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null )
			return null ;
		if ( r.getPhoneuserid() == null || r.getPhoneuserid() == 0 )
			return null ;
		List<Integer> pn = queryAuthorityPhoneuserid(r.getPhoneuserid());
		if ( zwavedeviceid != null )
			pn.addAll(queryPhoneuseridbyDeviceShare(deviceid,zwavedeviceid));
		if ( cameraid != null )
			pn.addAll(queryPhoneuseridbyDeviceShare(deviceid,zwavedeviceid));
		
		PhoneUserService pus = new PhoneUserService();
		return pus.queryAlias(pn);
	}

	public static List<Integer> queryAuthorityByDeviceid(String deviceid , Integer zwavedeviceid , Integer cameraid)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null )
			return null ;
		if ( r.getPhoneuserid() == null || r.getPhoneuserid() == 0 )
			return null ;
		List<Integer> pn = queryAuthorityPhoneuserid(r.getPhoneuserid());
		if ( zwavedeviceid != null )
			pn.addAll(queryPhoneuseridbyDeviceShare(deviceid,zwavedeviceid));
		if ( cameraid != null )
			pn.addAll(queryPhoneuseridbyDeviceShare(deviceid,zwavedeviceid));

		return pn;
	}

	public static List<Integer> queryPhoneuseridByDeviceid(String deviceid , Integer zwavedeviceid)
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null )
			return null ;
		if ( r.getPhoneuserid() == null || r.getPhoneuserid() == 0 )
			return null ;
		List<Integer> pn = queryAuthorityPhoneuserid(r.getPhoneuserid());
		pn.addAll(queryPhoneuseridbyDeviceShare(deviceid , zwavedeviceid));
		return pn;
	}
	
	@Deprecated
	public static List<PhoneUser> queryAuthorizedUser(int phoneuserid)
	{
		List<Integer> lst = queryAuthorityPhoneuserid(phoneuserid);

		PhoneUserService pus = new PhoneUserService();
		
		return pus.query(lst);
	}
	
	@Deprecated
	public static PhoneUser savePhoneUser(String phonenumber , String password)
	{
		PhoneUser user = new PhoneUser();
		user.setPhonenumber(phonenumber);
		user.setCountrycode(IRemoteConstantDefine.DEFAULT_COUNTRYCODE);
		UserService svr = new UserService();
		String ep = "";
		if ( password != null && password.length() > 0 )
			ep = svr.encryptPassword(phonenumber, password);
		
		user.setPassword(ep);
		user.setCreatetime(new Date());
		user.setLastupdatetime(new Date());
		
		PhoneUserService us = new PhoneUserService();
		int id = us.save(user);
		user.setAlias(createAlias(id));
		
		return user;
	}
	
	public static String createAlias(int id)
	{
		UUID u = UUID.randomUUID();
		String su = u.toString().replaceAll("-", "");
		
		Random r = new Random();
		int ri = r.nextInt(su.length());
		
		StringBuffer sb = new StringBuffer();
		sb.append(su.substring(0, ri)).append(String.valueOf(id)).append(su.substring(ri));
		
		return sb.toString();
	}
	
	public static int[] createplatform(int platform)
	{
		return new int[]{platform};
	}
	
	public static String getUserId(PhoneUser user)
	{
		if ( user.getPlatform() == IRemoteConstantDefine.PLATFORM_ASININFO )
			return user.getToken();
		return String.valueOf(user.getPhoneuserid());
	}
	
	public static String getUserType(PhoneUser user)
	{
		if ( user.getPlatform() == IRemoteConstantDefine.PLATFORM_ASININFO )
			return "0";
		return "1";
	}
	
	public static String getLanguange(PhoneUser user)
	{
		if ( user != null && user != null )
			return user.getLanguage();
		return IRemoteConstantDefine.DEFAULT_LANGUAGE;
	}
	
	public static Integer getPhoneuserArmStatus(int phoneuserid)
	{
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(phoneuserid);
		
		return getPhoneuserArmStatus(pu);
	}
	
	public static Integer getPhoneuserArmStatus(PhoneUser user)
	{
		if ( user == null )
			return null;
		else if ( user.getFamilyid() == null )
			return user.getArmstatus();
		else 
		{
			FamilyService fs = new FamilyService();
			Family f = fs.query(user.getFamilyid());
			return f.getArmstatus();
		}
	}
	
	public static void sendInfoChangeMessage(PhoneUser user)
	{
		sendInfoChangeMessage(user , null );
	}
	
	public static void sendInfoChangeMessage(PhoneUser user , String action)
	{
		if ( user == null )
			return ;
		List<Integer> pul = querySharetoPhoneuserid(user.getPhoneuserid());
		
		PhoneUserService pus = new PhoneUserService();
		List<String> al = pus.queryAlias(pul); 
				
		PushMessage.pushInfoChangedMessage(al.toArray(new String[0]) , getTokenid() , action, user.getPlatform());
	}
	
	public static void sendPasswordChangedMessage(PhoneUser user)
	{
		if ( user == null )
			return  ;
		PushMessage.pushPasswordChangedMessage(user.getAlias(), user.getPlatform(), getTokenid());
	}
	
	public static Integer getTokenid()
	{
		Integer tokenid = null ;
		if (ActionContext.getContext() != null && ActionContext.getContext().getSession() != null )
			tokenid = (Integer)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_TOKEN_ID);
		return tokenid ;
	}
	
	public static void sendAtributeChangeMessage(PhoneUser user , String code , String value)
	{
		
		PushMessage.pushPhoneuserAttributeChangedMessage(user.getAlias(), user.getPlatform(), code, value);
	}
	
	public static boolean hasArmFunction(PhoneUser user)
	{
		if ( user == null )
			return false ;
		return OemProductorHelper.hasArmFunction(user.getPlatform());
	}
	
	public static boolean isAdminUser(List<PhoneUserAttribute> pua)
	{
		if ( pua == null || pua.size() == 0)
			return false ;
		for ( PhoneUserAttribute a : pua )
			if ( "admin".equals(a.getCode()))
				return true ;
		return false ;
	}
	
	public static boolean isAmetaAdminUser(List<PhoneUserAttribute> pua)
	{
		if ( pua == null || pua.size() == 0)
			return false ;
		for ( PhoneUserAttribute a : pua )
			if ( "ametaadmin".equals(a.getCode()))
				return true ;
		return false ;
	}

	public static boolean isAdminUser(int phoneUserId) {
		List<PhoneUserAttribute> attributes = new PhoneUserAttributeService().querybyphoneuserid(phoneUserId);
		return PhoneUserHelper.isAdminUser(attributes);
	}
}
