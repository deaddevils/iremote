package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.ZWaveDevice;
import org.hibernate.Query;

public class ZWaveDeviceService extends BaseService<ZWaveDevice> {

	public ZWaveDevice query(int zwavedeviceid )
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.uniqueResult();
	}
	
	public List<ZWaveDevice> query(Collection<Integer> zwavedeviceid )
	{
		if ( zwavedeviceid == null || zwavedeviceid.size() == 0 )
			return new ArrayList<ZWaveDevice>();
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}

	public List<ZWaveDevice> query(Collection<Integer> zwavedeviceid ,String deviceType)
	{
		if ( zwavedeviceid == null || zwavedeviceid.size() == 0 )
			return new ArrayList<>();
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("devicetype", deviceType));
		return cw.list();
	}
	
	public List<ZWaveDevice> querybyIdandName(Collection<Integer> zwavedeviceid  , String name)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("name", name));
		return cw.list();
	}
	
	public void saveOrUpdate(ZWaveDevice zwavedevice)
	{
		HibernateUtil.getSession().saveOrUpdate(zwavedevice);
	}
	
	public void delete(ZWaveDevice zwavedevice)
	{
		HibernateUtil.getSession().delete(zwavedevice);
	}
	
	public void delete(String deviceid)
	{
		HqlWrap hql = new HqlWrap();
		hql.addifnotnull("delete from ZWaveDevice where deviceid = ?" , deviceid);
		hql.executeUpdate();
	}
	
	public List<ZWaveDevice> querybydeviceid(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.list();
	}
	
	public List<ZWaveDevice> querybydeviceid(Collection<String> deviceidcollection)
	{
		if ( deviceidcollection == null || deviceidcollection.size() == 0 )
			return new ArrayList<ZWaveDevice>();
		
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceidcollection));
		return cw.list();
	}
	
	public List<Integer> queryidbydeviceid(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.addFields(new String[]{"zwavedeviceid"});
		return cw.list();
	}
	
	public List<Integer> queryidbydeviceid(List<String> deviceid)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<Integer>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.addFields(new String[]{"zwavedeviceid"});
		return cw.list();
	}
	
	public ZWaveDevice querybydeviceidapplianceid(String deviceid , String applianceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("applianceid", applianceid));
		return cw.uniqueResult();
	}
	
	public List<ZWaveDevice> querybydeviceidtype(String deviceid , String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("devicetype", devicetype));
		return cw.list();
	}

	public List<ZWaveDevice> querybydeviceidtypelist(String deviceid , List<String> devicetypelist)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.in("devicetype", devicetypelist));
		return cw.list();
	}

	public List<ZWaveDevice> querybydeviceid(List<String> deviceid)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<ZWaveDevice>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		return cw.list();
	}
	
	public List<ZWaveDevice> filterByDeviceid(Collection<ZWaveDevice> zwavedevicecollection , String deviceid)
	{
		List<ZWaveDevice> lst = new ArrayList<ZWaveDevice>();
		if ( zwavedevicecollection == null || zwavedevicecollection.size() == 0 || StringUtils.isBlank(deviceid))
			return lst ;
		
		for ( ZWaveDevice zd : zwavedevicecollection)
			if ( deviceid.equals(zd.getDeviceid()))
				lst.add(zd);
		return lst ;
	}
	
	
	public List<ZWaveDevice> querybydeviceidandtype(List<String> deviceid,String devicetype)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<ZWaveDevice>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.eq("devicetype", devicetype));
		return cw.list();
	}
	
	public List<ZWaveDevice> querybydeviceidandName(List<String> deviceid , String name)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<ZWaveDevice>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.eq("name", name));
		return cw.list();
	}
	
	public List<ZWaveDevice> queryByDeviceidAndNameAndNotInZwavedeviceid(List<String> deviceid , String name,int zwavedeviceid)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<ZWaveDevice>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.eq("name", name));
		cw.add(ExpWrap.not(ExpWrap.eq("zwavedeviceid", zwavedeviceid)));
		return cw.list();
	}
	
	public List<ZWaveDevice> querydevicestatusbyzwavedeviceid(Collection<Integer> zwavedeviceids)
	{
		if ( zwavedeviceids == null || zwavedeviceids.size() == 0)
			return new ArrayList<ZWaveDevice>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceids));
		cw.addFields(new String[]{"zwavedeviceid","deviceid","name","devicetype","nuid","status","warningstatuses" , "statuses","fstatus","battery","enablestatus","lastactivetime"});
		return cw.list(ZWaveDevice.class);
	}
	
	public List<ZWaveDevice> querydevicestatusbydeviceid(Collection<String> deviceid)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<ZWaveDevice>() ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.addFields(new String[]{"zwavedeviceid","deviceid","name","devicetype","nuid","status","warningstatuses" , "statuses","fstatus","battery","enablestatus","lastactivetime"});
		return cw.list(ZWaveDevice.class);
	}
	
	public ZWaveDevice querybydeviceid(String deviceid , int nuid)
	{
		if ( StringUtils.isBlank(deviceid))
			return null ;
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("nuid", nuid));
		return cw.uniqueResult();
	}
	
	public List<ZWaveDevice> queryAlarmDevice(String remotedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", remotedeviceid));
		cw.add(ExpWrap.eq("devicetype", IRemoteConstantDefine.DEVICE_TYPE_ALARM));
		return cw.list();
	}
	
	public List<ZWaveDevice> queryRoboticArmDevice(String remotedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", remotedeviceid));
		cw.add(ExpWrap.eq("devicetype", IRemoteConstantDefine.DEVICE_TYPE_ROBOTIC_ARM));
		return cw.list();
	}
	
	public List<ZWaveDevice> queryOpeningDoorSensor(Collection<String> deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.eq("devicetype", IRemoteConstantDefine.DEVICE_TYPE_DOOR_SENSOR));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_OPEN));
		return cw.list();
	}
	
	public List<ZWaveDevice> queryDeviceByDeviceType(String deviceid , String[] devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.in("devicetype" , devicetype));

		return cw.list();
	}
	
	public List<ZWaveDevice> queryDeviceByNuidType(String deviceid,int nuid , String[] devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("nuid", nuid));
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.in("devicetype" , devicetype));
		return cw.list();
	}
	
	public List<ZWaveDevice> queryByDevicetype(String deviceid , String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("devicetype",devicetype));
		return cw.list();
	}

	public List<ZWaveDevice> queryByDevicetype(Collection<String> deviceIdList , String devicetype)
	{
		if (deviceIdList == null || deviceIdList.size() == 0) {
			return  new ArrayList<>();
		}
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceIdList));
		cw.add(ExpWrap.eq("devicetype",devicetype));
		return cw.list();
	}

	public List<ZWaveDevice> queryByDevicetypeList(String deviceid , List<String> devicetypelist)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.in("devicetype",devicetypelist));
		return cw.list();
	}
	
	public int queryCountBySharedeviceAndRemode(List<String> deviceids,List<Integer> zwaveDevices)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid" , deviceids));
		cw.add(ExpWrap.in("zwavedeviceid" , zwaveDevices));
		return cw.list().size();
	}
	
	public List<ZWaveDevice> querybypartitionid(int partitionid){
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("partitionid", partitionid));
		return cw.list();
	}

	public List<ZWaveDevice> queryWarningDevice() {
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.eq("enablestatus" , 0));
		cw.add(ExpWrap.not(ExpWrap.eq("warningstatuses", "[]")));
		cw.add(ExpWrap.not(ExpWrap.eq("warningstatuses", "[0]")));
		cw.add(ExpWrap.not(ExpWrap.isnull("warningstatuses")));
		return cw.list();
	}

	public List<ZWaveDevice> queryByDevicetypeAndPartitionid(List<String> deviceids , String devicetype, Integer partitionid) {
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceids));
		cw.add(ExpWrap.eq("devicetype",devicetype));
		cw.add(ExpWrap.eq("partitionid", partitionid));
		return cw.list();
	}

	public List<ZWaveDevice> queryByDevicetype(Collection<String> deviceIdList , Collection<String> deviceTypeList)
	{
		if (deviceIdList == null || deviceIdList.size() == 0 || deviceIdList == null || deviceIdList.size() == 0) {
			return  new ArrayList<>();
		}
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceIdList));
		cw.add(ExpWrap.in("devicetype",deviceTypeList));
		return cw.list();
	}
	
	public List<Integer> getZWaveDeviceid(Collection<ZWaveDevice> zwavedevicecollection)
	{
		List<Integer> lst = new ArrayList<Integer>();
		if ( zwavedevicecollection == null || zwavedevicecollection.size() == 0 ) 
			return lst ;
		for ( ZWaveDevice zd : zwavedevicecollection)
			lst.add(zd.getZwavedeviceid());
		return lst ;
		
	}

	public List<ZWaveDevice> queryDSC(List<String> fromUserDeviceIds) {
		CriteriaWrap cw = new CriteriaWrap(ZWaveDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", fromUserDeviceIds));
		cw.add(ExpWrap.eq("devicetype", IRemoteConstantDefine.DEVICE_TYPE_DSC));
		return cw.list();
	}
}
