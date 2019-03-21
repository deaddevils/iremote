package com.iremote.action.device;

import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.*;
import org.apache.commons.beanutils.PropertyUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.infraredtrans.GatewayReportHelper;
import com.iremote.service.CameraService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.Appliance;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class ListApplianceAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid;
	private List<Appliance> appliance = new ArrayList<Appliance>();
	private Remote remote ;
	private List<GatewayCapability> gatewayCapabilities = new ArrayList<>();

	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		CameraService cs = new CameraService();
		InfraredDeviceService ids = new InfraredDeviceService();
		
		List<ZWaveDevice> zls = zds.querybydeviceid(deviceid) ;
		for ( ZWaveDevice zd : zls )
			appendAppliance(appliance ,createAppliance(zd));

		List<InfraredDevice> ils = ids.querybydeviceid(deviceid);
		for ( InfraredDevice id : ils )
			appendAppliance(appliance ,createAppliance(id));
		
		List<Camera> cls = cs.querybydeviceid(deviceid);
		for ( Camera c : cls )
			appendAppliance(appliance ,createAppliance(c));	

		RemoteService rs = new RemoteService();
		remote = rs.getIremotepassword(deviceid);
		if(remote != null){
			//gatewayCapabilities = remote.getCapability();
			List<Integer> templist = new ArrayList<>();
			for(GatewayCapability gc : remote.getCapability()){
				if(!templist.contains(gc.getCapabilitycode())){
					templist.add(gc.getCapabilitycode());
				}
			}
			for(int i = 0 ; i<templist.size();i++){
				gatewayCapabilities.add(new GatewayCapability(remote,templist.get(i)));
			}
		}
		
		return Action.SUCCESS;
	}
	
	private void appendAppliance(List<Appliance> lst , Appliance a)
	{
		if ( a == null )
			return ;
		lst.add(a);
	}
	
	private Appliance createAppliance(Object obj)
	{
		Appliance a = new Appliance();
		try
		{
			PropertyUtils.copyProperties(a, obj);
		} 
		catch (Throwable t)
		{
			return null ;
		}
		return a ;
	}
	
	public boolean isDscGateway()
	{
		return GatewayReportHelper.hasGatewayCapability(remote , GatewayCapabilityType.dsc.getCapabilitycode());
	}

	public int getResultCode() {
		return resultCode;
	}

	public List<Appliance> getAppliance() {
		return appliance;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public List<GatewayCapability> getGatewayCapabilities() {
		return gatewayCapabilities;
	}
}
