package com.iremote.action.airconditioner;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

public class AddAirconditionerPageAction {
	private String deviceid;
	private int capabilitycode;
	private PhoneUser phoneuser; 
	private List<Remote> remoteList = new ArrayList<Remote>();
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int innerdevicetype ;
	private int outdevicetype;
	
	public String execute()
	{
		RemoteService remoteService = new RemoteService();
		List<Remote> lst = remoteService.queryRemoteByPhoneuserid(phoneuser.getPhoneuserid());
	
		GatewayCapabilityType gct = GatewayCapabilityType.valueof(capabilitycode);
		if ( gct == null )
			return Action.ERROR;
		
		this.innerdevicetype = gct.getInnerdevicetype();
		this.outdevicetype = gct.getOutdevicetype();
		
		for ( Remote r : lst )
			if ( GatewayReportHelper.hasGatewayCapability(r, capabilitycode))
				this.remoteList.add(r);
		return Action.SUCCESS;
	}


	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	public List<Remote> getRemoteList() {
		return remoteList;
	}
	public int getResultCode() {
		return resultCode;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}


	public int getCapabilitycode()
	{
		return capabilitycode;
	}


	public int getInnerdevicetype()
	{
		return innerdevicetype;
	}


	public int getOutdevicetype()
	{
		return outdevicetype;
	}


}
