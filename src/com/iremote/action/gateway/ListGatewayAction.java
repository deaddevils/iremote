package com.iremote.action.gateway;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.vo.RemoteData;
import com.opensymphony.xwork2.Action;

public class ListGatewayAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private PhoneUser phoneuser;
	private List<RemoteData> remote = new ArrayList<RemoteData>();
	
	public String execute()
	{
		if ( phoneuser == null )
			return Action.SUCCESS;
		RemoteService rs = new RemoteService();
		List<Remote> lst = rs.querybyPhoneUserid(phoneuser.getPhoneuserid());
		ZWaveSubDeviceService zwsds = new ZWaveSubDeviceService();
		for ( Remote r : lst )
		{   
			RemoteData rd = new RemoteData();
			rd.setDeviceid(r.getDeviceid());
			rd.setName(r.getName());
			rd.setRemotetype(r.getRemotetype());
			rd.setCapability(r.getCapability());
			List<ZWaveSubDevice> channel = zwsds.querychannelbydeviceid(rd.getDeviceid());
			if(channel.size()>0){
				rd.setIsexistdsc(true);
				rd.setZwavedeviceid(String.valueOf(channel.get(0).getZwavedevice().getZwavedeviceid()));
			}else{
				rd.setIsexistdsc(false);
			}
			remote.add(rd);
		}
		
		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public List<RemoteData> getRemote()
	{
		return remote;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
}
