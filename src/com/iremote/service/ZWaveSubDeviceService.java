package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveSubDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ZWaveSubDeviceService {

	public void save(ZWaveSubDevice zdm)
	{
		HibernateUtil.getSession().save(zdm);
	}
	
	public List<ZWaveSubDevice> query(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwavedeviceid));
		return cw.list();
	}

	public List<ZWaveSubDevice> queryWarningSubDevices(int zwaveDeviceId){
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwaveDeviceId));
//		cw.add(ExpWrap.not(ExpWrap.in("subdevicetype", IRemoteConstantDefine.DSC_ZONE_WARNING_TYPES_NOT_SAFE)));
		cw.add(ExpWrap.and(ExpWrap.not(ExpWrap.isnull("warningstatuses")), ExpWrap.not(ExpWrap.eq("warningstatuses","[]"))));
		return cw.list();
	}

	public List<ZWaveSubDevice> querySafeWarningSubDevices(int zwaveDeviceId){
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwaveDeviceId));
		cw.add(ExpWrap.not(ExpWrap.in("subdevicetype", IRemoteConstantDefine.DSC_ZONE_WARNING_TYPES_NOT_SAFE)));
		cw.add(ExpWrap.and(ExpWrap.not(ExpWrap.isnull("warningstatuses")), ExpWrap.not(ExpWrap.eq("warningstatuses","[]"))));
		return cw.list();
	}

	public List<ZWaveSubDevice> query(Collection<Integer> zwavedeviceids)
	{
		if ( zwavedeviceids == null || zwavedeviceids.size() == 0 )
			return new ArrayList<ZWaveSubDevice>();
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.in("zwavesubdeviceid", zwavedeviceids));
		return cw.list();
	}
	
	public List<ZWaveSubDevice> queryByZwaveDeviceid(Collection<Integer> zwavedeviceids)
	{
		if ( zwavedeviceids == null || zwavedeviceids.size() == 0 )
			return new ArrayList<ZWaveSubDevice>();
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.in("zwavedevice.zwavedeviceid", zwavedeviceids));
		return cw.list();
	}

	public ZWaveSubDevice queryByZwavedeviceidAndChannelid(int zwavedeviceid, int channelid){
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("channelid", channelid));
		return cw.uniqueResult();
	}

	public List<ZWaveSubDevice> queryByParitionid(int partitionid){
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.eq("partition.partitionid", partitionid));
		return cw.list();
	}
	
	public ZWaveSubDevice queryByid(int id)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveSubDevice.class.getName());
		cw.add(ExpWrap.eq("zwavesubdeviceid", id));
		return cw.uniqueResult();
	}
	
	public List<ZWaveSubDevice> querybydeviceidandName(List<String> deviceid , String name)
	{
		if ( deviceid == null || deviceid.size() == 0 || name == null || name.length() == 0)
			return new ArrayList<ZWaveSubDevice>() ;

		HqlWrap hw = new HqlWrap();
		hw.add("from ZWaveSubDevice where ");
		hw.addifnotnull(" zwavedevice.deviceid in :deviceid ", "deviceid", deviceid );
		hw.addifnotnull(" and name = :name ", "name", name );
		return hw.list();
	}
	
	public List<ZWaveSubDevice> querybydeviceidandNameAndNotInId(List<String> deviceid , String name,ZWaveDevice zwavedevice)
	{
		if ( deviceid == null || deviceid.size() == 0 || name == null || name.length() == 0)
			return new ArrayList<ZWaveSubDevice>() ;

		HqlWrap hw = new HqlWrap();
		hw.add("from ZWaveSubDevice where ");
		hw.addifnotnull(" zwavedevice.deviceid in :deviceid ", "deviceid", deviceid);
		hw.addifnotnull(" and name = :name ", "name", name );
		hw.addifnotnull(" and zwavedevice = :zwavedevice ", "zwavedevice", zwavedevice );
		return hw.list();
	}

	public List<ZWaveSubDevice> querychannelbydeviceid(String deviceid )
	{
		if ( deviceid == null || deviceid.length() == 0 )
			return new ArrayList<ZWaveSubDevice>() ;
		HqlWrap hw = new HqlWrap();
		hw.add("from ZWaveSubDevice where ");
		hw.addifnotnull(" zwavedevice.deviceid = :deviceid ", "deviceid", deviceid );
		hw.addifnotnull(" and zwavedevice.devicetype = :devicetype", "devicetype",IRemoteConstantDefine.DEVICE_TYPE_DSC);
		return hw.list();
	}
	
	public List<ZWaveSubDevice> filterByDeviceid(Collection<ZWaveSubDevice> infraredevicecollection , int zwavedeviceid)
	{
		List<ZWaveSubDevice> lst = new ArrayList<ZWaveSubDevice>();
		
		if ( infraredevicecollection == null || infraredevicecollection.size() == 0 )
			return lst ;
		
		for ( ZWaveSubDevice id : infraredevicecollection)
			if ( zwavedeviceid == id.getZwavedevice().getZwavedeviceid())
				lst.add(id);
		return lst ;
	}
}
