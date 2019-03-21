package com.iremote.action.device.doorlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class AddLockUserMenuAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid ;
	private String deviceid ;
	private int nuid ;
	private boolean andoridos = false ;
	private Set<Integer> capability ;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = null ;
		if ( zwavedeviceid == 0 )	
			zd = zds.querybydeviceid(deviceid, nuid);
		else 
			 zd = zds.query(zwavedeviceid);
		if ( zd == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.ERROR;
		}
		
		zwavedeviceid = zd.getZwavedeviceid();
		//capability = getCapability(zd);
		//getCapability(zd);
		
		this.capability = DeviceHelper.initDeviceCapability(zd);

		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		String ua = request.getHeader("User-Agent");
		
		if ( ua != null && ua.contains("Android"))
			this.andoridos = true;
		
		return Action.SUCCESS;
	}
	
	public boolean isSupoprtPasswordUser()
	{
		return this.capability.contains(5);
	}
	
	public boolean isSupoprtFingerprintUser()
	{
		return this.capability.contains(6);
	}

	public boolean isSupoprtCardUser()
	{
		return this.capability.contains(7);
	}
	
	public boolean isSupoprtKeyUser()
	{
		return this.capability.contains(8);
	}
	
	private void getCapability(ZWaveDevice zd){
		List<DeviceCapability> list = zd.getCapability();
		capability.add(5);
		capability.add(6);
		capability.add(7);
		capability.add(9);
		capability.add(10);
		capability.add(11);
		
		
		List<Integer> codelist2 = new ArrayList<Integer>();
		for(DeviceCapability item : list){
			for(DeviceCapability item2 : list){
				if(item.getCapabilitycode() == 5 && item2.getCapabilitycode() == 9){
					capability.remove(new Integer(5));
				}
				if(item.getCapabilitycode() == 6 && item2.getCapabilitycode() == 10){
					capability.remove(new Integer(6));
				}
				if(item.getCapabilitycode() == 7 && item2.getCapabilitycode() == 11){
					capability.remove(new Integer(7));
				}
			}
			codelist2.add(item.getCapabilitycode());
		}
		
		capability.retainAll(codelist2);
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public boolean isAndoridos()
	{
		return andoridos;
	}

	public Set<Integer> getCapability() {
		return capability;
	}


	
}
