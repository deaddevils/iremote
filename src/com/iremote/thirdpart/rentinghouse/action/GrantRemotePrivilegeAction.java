package com.iremote.thirdpart.rentinghouse.action;

import java.util.Calendar;
import java.util.Date;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ShareRelationshipChange;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceShareService;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@Deprecated
public class GrantRemotePrivilegeAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String deviceid;
	private String username;
	private String validfrom;
	private String validthrough;
	
	public String execute()
	{
		resultCode = ErrorCodeDefine.NOT_SUPPORT;
		return Action.SUCCESS;
	}
	
	public String execute_deprecated()
	{
		ThirdPart tp = (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
		
		ComunityRemoteService crs = new ComunityRemoteService();
		if ( crs.query(tp.getThirdpartid(), deviceid) == null )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(IRemoteConstantDefine.DEFAULT_COUNTRYCODE, username, tp.getPlatform());
		
		if ( pu == null )
		{
			resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
			return Action.SUCCESS;
		}
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		ZWaveDeviceShare zs = zdss.queryRemoteShare(deviceid);
		
		int oldphoneuserid = 0 ;
		if ( zs == null )
			zs = new ZWaveDeviceShare();
		else if ( zs.getTouserid() == pu.getPhoneuserid() )
			return Action.SUCCESS;
		else 
			oldphoneuserid = zs.getTouserid();
		
		zs.setDeviceid(deviceid);
		zs.setTouser(pu.getPhonenumber());
		zs.setTouserid(pu.getPhoneuserid());
		zs.setZwavedeviceid(0);
		zs.setCreatetime(new Date());
		if ( validfrom == null || validfrom.length() == 0 )
			zs.setValidfrom(new Date());
		else 
			zs.setValidfrom(Utils.parseTime(validfrom));
		if ( validthrough == null || validthrough.length() == 0 )
		{
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, 100);
			zs.setValidthrough(c.getTime());
		}
		else 
			zs.setValidthrough(Utils.parseTime(validthrough));
		
		zdss.save(zs);
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_SHARE_RELA_CHANGED, new ShareRelationshipChange(new Integer[]{pu.getPhoneuserid() , oldphoneuserid} , pu.getPlatform() , 0));
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}
	
	
}
