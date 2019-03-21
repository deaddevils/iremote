package com.iremote.thirdpart.rentinghouse.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.LockPasswordRefreshed;
import com.iremote.common.jms.vo.ShareRelationshipChange;
import com.iremote.domain.*;
import com.iremote.service.*;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class GrantDevicePrivilegeAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String zwavedeviceid;
	private String username;
	private String validfrom;
	private String validthrough;
	private String countrycode;
	private List<ZWaveDeviceShare> zdsharelist = new ArrayList<ZWaveDeviceShare>();
	
	private Set<String> deviceidset = new HashSet<String>();
	
	public String execute()
	{
		ThirdPart tp = (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
		
		ComunityRemoteService crs = new ComunityRemoteService();

		PhoneUserService pus = new PhoneUserService();
		if(countrycode!=null&&!"".equals(countrycode)){
	
		}else{
			countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
		}
		PhoneUser pu = pus.query(countrycode, username, tp.getPlatform());

		if ( pu == null )
		{
			resultCode = ErrorCodeDefine.USER_HAS_NOT_REGISTED_2;
			return Action.SUCCESS;
		}
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		JSONArray ja = JSON.parseArray(zwavedeviceid);
		
		List<ZWaveDevice> lst =  zds.query(Arrays.asList( ja.toArray(new Integer[0])));
		
		Set<String> shareddeviceid = new HashSet<String>();
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
        BlueToothPasswordService blueToothPasswordService = new BlueToothPasswordService();
        for (ZWaveDevice zd : lst  )
		{
			if ( !deviceidset.contains(zd.getDeviceid()) && crs.query(tp.getThirdpartid(), zd.getDeviceid()) == null )
			{
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
				return Action.SUCCESS;
			}
			deviceidset.add(zd.getDeviceid());
			
			ZWaveDeviceShare zs = new ZWaveDeviceShare();
			zs.setDeviceid(zd.getDeviceid());
			zs.setTouser(pu.getPhonenumber());
			zs.setTouserid(pu.getPhoneuserid());
			zs.setZwavedeviceid(zd.getZwavedeviceid());
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
			zdsharelist.add(zs);
			shareddeviceid.add(zd.getDeviceid());

			if (PhoneUserBlueToothHelper.isBlueToothLock(zd)) {
				createBlueToothPassword(zd, pu, blueToothPasswordService);
			}
		}

		if ( zdsharelist == null || zdsharelist.size() == 0 )
			return Action.SUCCESS;
		
		for ( ZWaveDeviceShare zs : zdsharelist)
			zdss.save(zs);
		
		RemoteService rs = new RemoteService();
		List<Integer> puidl = rs.queryOwnerId(shareddeviceid);
		
		List<PhoneUser> pul = pus.query(puidl);
		
		for ( PhoneUser spu : pul)
			spu.setLastupdatetime(new Date());
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_SHARE_RELA_CHANGED, new ShareRelationshipChange(new Integer[]{pu.getPhoneuserid()} , pu.getPlatform() , 0));
		
		return Action.SUCCESS;
	}

	private void createBlueToothPassword(ZWaveDevice zd, PhoneUser pu, BlueToothPasswordService blueToothPasswordService){
        BlueToothPassword password = blueToothPasswordService.findByZwaveDeviceIdAndPhoneUserId(zd.getZwavedeviceid(), pu.getPhoneuserid());
        if (password == null) {
            PhoneUserBlueToothHelper.createBlueToothPassword(Arrays.asList(zd), Arrays.asList(pu.getPhoneuserid()), null);
            JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_LOCK_PASSWORD_REFRESHED, new LockPasswordRefreshed(Arrays.asList(pu.getAlias()), zd.getZwavedeviceid(), zd.getDeviceid(), pu.getPlatform()));
        }
	}


	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(String zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
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

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	
}
