package com.iremote.thirdpart.rentinghouse.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.PhoneUserBlueToothHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.LockPasswordRefreshed;
import com.iremote.common.jms.vo.ShareRelationshipChange;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.util.*;

public class UngrantDevicePrivilegeAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String zwavedeviceid;
	private String username;
	
	private Set<String> deviceidset = new HashSet<String>();
	
	public String execute()
	{
		ThirdPart tp = (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
		
		ComunityRemoteService crs = new ComunityRemoteService();
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser pu = pus.query(IRemoteConstantDefine.DEFAULT_COUNTRYCODE, username, tp.getPlatform());

		ZWaveDeviceService zds = new ZWaveDeviceService();
		JSONArray ja = JSON.parseArray(zwavedeviceid);
		List<ZWaveDevice> lst =  zds.query(Arrays.asList( ja.toArray(new Integer[0])));
		
		List<ZWaveDeviceShare> zssl = new ArrayList<ZWaveDeviceShare>();
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		for (ZWaveDevice zd : lst  )
		{
			if ( !deviceidset.contains(zd.getDeviceid()) && crs.query(tp.getThirdpartid(), zd.getDeviceid()) == null )
			{
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
				return Action.SUCCESS;
			}
			deviceidset.add(zd.getDeviceid());
			
			zssl.addAll(zdss.query(zd.getZwavedeviceid(), pu.getPhoneuserid()));
			deleteBlueToothPasswordPackage(zd, pu.getPhoneuserid(), pu.getPlatform(), pus);
		}
		
		zdss.delete(zssl);
		
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_SHARE_RELA_CHANGED, new ShareRelationshipChange(new Integer[]{pu.getPhoneuserid()} , pu.getPlatform() , 0));
		
		return Action.SUCCESS;
	}

    private void deleteBlueToothPasswordPackage(ZWaveDevice zd, int phoneUserId,int platform, PhoneUserService pus) {
        Set<Integer> phoneUserList = PhoneUserBlueToothHelper.modifyBlueToothDevicePassword(Arrays.asList(zd), Arrays.asList(phoneUserId));
        JMSUtil.sendmessage(IRemoteConstantDefine.MESSAGE_LOCK_PASSWORD_REFRESHED, new LockPasswordRefreshed(pus.queryAlias(phoneUserList), zd.getZwavedeviceid(), zd.getDeviceid(), platform));

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
}
