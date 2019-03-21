package com.iremote.thirdpart.rentinghouse.action;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.SystemParameterService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class AddAdminstratorAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String failedloginname ;
	private String administrator ;
	private PhoneUserService us = new PhoneUserService();
	private CommunityAdministratorService cas = new CommunityAdministratorService();
	
	public String execute()
	{
		Object jt= JSON.parse(administrator);
		
		JSONArray ja = null ;  //JSON.parseArray(administrator);
		
		if ( jt instanceof JSONArray )
			ja = (JSONArray)jt ;
		else if ( jt instanceof JSON )
		{
			ja = new JSONArray();
			ja.add(jt);
		}
		else 
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		ThirdPart tp = (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
		
		for ( int i = 0 ; i < ja.size() ; i ++ )
		{
			JSONObject json = ja.getJSONObject(i);
			String phonenumber = json.getString("loginname");
			String password = json.getString("password");
			int communityid = 0;
			if ( json.containsKey("comnutiyid"))
				communityid = json.getIntValue("comnutiyid");
			else 
				communityid = json.getIntValue("communityid");
			
			if ( tp.getAdminprefix() == null || tp.getAdminprefix().length() == 0 
					|| phonenumber == null || !phonenumber.startsWith(tp.getAdminprefix())
					|| phonenumber.length() != 7 )
			{
				resultCode = ErrorCodeDefine.COMMUNITY_ADMIN_LOGIN_NAME_INVALID;
				failedloginname = phonenumber;
				return Action.SUCCESS;
			}
			
			if ( checkReqiste(phonenumber , tp.getPlatform()) == true )
			{
				resultCode = ErrorCodeDefine.USER_HAS_REGISTED;
				failedloginname = phonenumber;
				return Action.SUCCESS;
			}
			
			int phoneuserid = savePhoneUser(phonenumber , password , tp.getPlatform());
			
			saveComunityAdministor(tp.getThirdpartid() ,communityid , phoneuserid , phonenumber );
		}
		
		
		return Action.SUCCESS;
	}
	
	public String getFailedloginname() {
		return failedloginname;
	}

	private void saveComunityAdministor(int thirdpartid,  int communityid , int phoneuserid, String phonenumber)
	{
		CommunityAdministrator ca = new CommunityAdministrator();
		ca.setThirdpartid(thirdpartid);
		ca.setCommunityid(communityid);
		ca.setLogicname(phonenumber);
		ca.setPhoneuserid(phoneuserid);
		ca.setCreatetime(new Date());

		cas.save(ca);
	}
	
	private boolean checkReqiste(String phonenumber , int platform )
	{
		if ( us.query(IRemoteConstantDefine.DEFAULT_COUNTRYCODE ,phonenumber , platform) != null )
			return true;
		return false ;
	}
	
	private int savePhoneUser(String phonenumber, String password , int platform)
	{
		PhoneUser ph = new PhoneUser();
		ph.setCountrycode(IRemoteConstantDefine.DEFAULT_COUNTRYCODE);
		ph.setPhonenumber(phonenumber);
		ph.setPlatform(platform);
		
		SystemParameterService sps = new SystemParameterService();
		
		ph.setArmstatus( sps.getIntValue("defaultarmstatus", IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM));
	
		UserService svr = new UserService();
		String ep = svr.encryptPassword(phonenumber, password);
		
		ph.setPassword(ep);
		ph.setCreatetime(new Date());
		ph.setLastupdatetime(new Date());
		ph.setUsertype(IRemoteConstantDefine.PHONEUSER_USER_TYPE_RENTINGHOUSE_ADMIN);
		ph.setLanguage(IRemoteConstantDefine.DEFAULT_LANGUAGE);
		
		int phoneuserid = us.save(ph);
		ph.setAlias(Utils.createAlias(phoneuserid));
		
		return phoneuserid;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	} 
	
	
	
}
