package com.iremote.action.device;	

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.opensymphony.xwork2.Action;

public class QueryDeviceListAction {

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String devicetype ;
	private Set<ZWaveDevice> appliancelist = new HashSet<ZWaveDevice>() ;
	private PhoneUser phoneuser;
	
	public String execute()
	{
		List<String> lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuser.getPhoneuserid());
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<Integer> zidl = zdss.querySharedZwavedeviceid(phoneuser.getPhoneuserid());
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		if ( zidl != null && zidl.size() > 0 )
			appliancelist.addAll(zds.query(zidl));
		if ( lst != null && lst.size() > 0 )
			appliancelist.addAll(zds.querybydeviceid(lst));
		
		filterdevice();
		
		return Action.SUCCESS;
	}

	private void filterdevice()
	{
		Set<Integer> zidset = new HashSet<Integer>();
		Set<ZWaveDevice> rs = new HashSet<ZWaveDevice>();
		
		for ( ZWaveDevice zd : appliancelist)
		{
			if ( zidset.contains(zd.getZwavedeviceid()))
			{
				rs.add(zd);
				continue;
			}
			zidset.add(zd.getZwavedeviceid());
			if ( devicetype != null && devicetype.length() > 0 && !devicetype.equals(zd.getDevicetype()))
				rs.add(zd);
		}
		this.appliancelist.removeAll(rs);
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public Set<ZWaveDevice> getAppliancelist() {
		return appliancelist;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	
}
