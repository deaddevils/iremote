package com.iremote.thirdpart.rentinghouse.action;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.ApplianceVo;
import com.opensymphony.xwork2.Action;

public class QueryThirdpartDeviceAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private ThirdPart thirdpart ;
	private String loginname ;
	private List<ApplianceVo> appliances = new ArrayList<ApplianceVo>();
	
	public String execute()
	{
		if ( thirdpart == null )
			return Action.SUCCESS;
		
		CommunityAdministratorService cas = new CommunityAdministratorService();
		CommunityAdministrator ca = cas.querybyloginname(loginname);
		if ( ca == null || ca.getThirdpartid() != thirdpart.getThirdpartid())
		{
			this.resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		RemoteService rs = new RemoteService();
		List<String> lst = rs.queryDeviceidbyPhoneUserid(ca.getPhoneuserid());
		
		if ( lst == null || lst.size() == 0 )
			return Action.SUCCESS;
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lzd = zds.querybydeviceid(lst);
		
		for ( ZWaveDevice zd : lzd )
		{
			ApplianceVo avo = new ApplianceVo();
			avo.setZwavedeviceid(zd.getZwavedeviceid());
			avo.setName(zd.getName());
			avo.setDevicetype(zd.getDevicetype());
			
			appliances.add(avo);
		}
		
		return Action.SUCCESS;
	}
	
	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public List<ApplianceVo> getAppliances() {
		return appliances;
	}
}
