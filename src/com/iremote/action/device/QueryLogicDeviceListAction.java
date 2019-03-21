package com.iremote.action.device;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.iremote.vo.ApplianceVo;
import com.opensymphony.xwork2.Action;

public class QueryLogicDeviceListAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String devicetype ;
	private Set<ApplianceVo> appliancelist = new HashSet<ApplianceVo>() ;
	private Set<ZWaveDevice> zlist = new HashSet<ZWaveDevice>() ;
	private Set<InfraredDevice> ilist = new HashSet<InfraredDevice>() ;
	private PhoneUser phoneuser;
	
	public String execute()
	{
		querydevice();
		
		filterdevice();
		
		createAppliance();
		
		return Action.SUCCESS;
	}
	
	private void createAppliance()
	{
		for ( ZWaveDevice zd : zlist)
		{
			ApplianceVo a = new ApplianceVo();
			a.setId(zd.getZwavedeviceid());
			a.setName(zd.getName());
			a.setDevicetype(zd.getDevicetype());
			this.appliancelist.add(a);
			if(zd.getzWaveSubDevices() != null){
				for(ZWaveSubDevice zsd : zd.getzWaveSubDevices()){
					ApplianceVo b = new ApplianceVo();
					b.setId(zd.getZwavedeviceid());
					b.setName(zsd.getName());
					b.setDevicetype(zd.getDevicetype());
					this.appliancelist.add(b);
				}
			}
		}
		
		for ( InfraredDevice id : ilist)
		{
			ApplianceVo a = new ApplianceVo();
			a.setId(id.getInfrareddeviceid());
			a.setName(id.getName());
			a.setDevicetype(id.getDevicetype());
			this.appliancelist.add(a);
		}
	}

	private void querydevice()
	{
		List<String> lst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(phoneuser.getPhoneuserid());
		
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<Integer> zidl = zdss.querySharedZwavedeviceid(phoneuser.getPhoneuserid());
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		if ( zidl != null && zidl.size() > 0 )
			zlist.addAll(zds.query(zidl));
		if ( lst != null && lst.size() > 0 )
			zlist.addAll(zds.querybydeviceid(lst));
		
		List<Integer> iidl = zdss.querySharedInfrareddeviceid(phoneuser.getPhoneuserid());

		InfraredDeviceService ids = new InfraredDeviceService();
		if ( lst != null && lst.size() > 0 )
			ilist.addAll(ids.querybydeviceid(lst));
		if ( iidl != null && iidl.size() > 0 )
			ilist.addAll(ids.query(iidl));
	}
	
	private void filterdevice()
	{
		Set<Integer> zidset = new HashSet<Integer>();
		Set<ZWaveDevice> rs = new HashSet<ZWaveDevice>();
		
		for ( ZWaveDevice zd : zlist)
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
		this.zlist.removeAll(rs);
		
		
		Set<InfraredDevice> irs = new HashSet<InfraredDevice>();
		for ( InfraredDevice id : ilist)
		{
			if ( devicetype != null && devicetype.length() > 0 && !devicetype.equals(id.getDevicetype()))
				irs.add(id);
		}
		
		ilist.removeAll(irs);
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public Set<ApplianceVo> getAppliancelist() {
		return appliancelist;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
}
