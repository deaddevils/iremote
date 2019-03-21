package com.iremote.domain;

import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.Appliance;
import com.iremote.vo.RemoteData;

public class TimerUpgrade {

	public static void main(String arg[])
	{
		byte[] b = Base64.decodeBase64("AEYAB2ANAAElAf8ASgAHYA0BACUD/wBHAAEOAEgAAQAARgAHYA0AAiUB/wBKAAdgDQIAJQP/AEcAAQ4ASAABAABGAAdgDQADJQH/AEoAB2ANAwAlA/8ARwABDgBIAAEA");
		for ( int i = 0 ; i < b.length ; i ++ )
			System.out.print(String.format("%d ", b[i] & 0xff));
//		TimerUpgrade tu = new TimerUpgrade();
//		tu.upgradeTimer();
	}
	
	@SuppressWarnings("unused")
	private void upgradeTimer()
	{
//		for ( int i = 0 ; ; i ++ )
//		{
//			HibernateUtil.beginTransaction();
//			
//			List<Remote> lst = query(i);
//			
//			if ( lst == null || lst.size() == 0 )
//				break;
//			
//			for ( Remote r : lst )
//			{
//				if ( r.getData() == null || r.getData().length() == 0 )
//					continue;
//
//				RemoteData d = JSON.parseObject(r.getData(), RemoteData.class);
//
//				updateZWaveDeviceTimer(d);
//				updateInfreddeviceTimer(d);
//			}
//			
//			HibernateUtil.commit();
//		}
//		
//		HibernateUtil.closeSession();
	}
	
	private void updateZWaveDeviceTimer(RemoteData d)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		List<ZWaveDevice> zlst = zds.querybydeviceid(d.getDeviceid());
		
		for ( ZWaveDevice zd : zlst )
		{
			Appliance a = findAppliance(d.getAppliancelist() , zd.getNuid());
			if ( a == null )
				continue ;
			if ( zd.getTimer() != null )
			{
				zd.getTimer().clear();
				zd.getTimer().addAll(a.getTimer());
			}
			else 
				zd.setTimer(a.getTimer());
			if ( zd.getTimer() != null )
				for ( Timer t : zd.getTimer())
					t.setZwavedevice(zd);
		}

	}
	
	private void updateInfreddeviceTimer(RemoteData d)
	{
		InfraredDeviceService svr = new InfraredDeviceService();
		
		List<InfraredDevice> lst = svr.querybydeviceid(d.getDeviceid());

		for ( InfraredDevice id : lst )
		{
			Appliance a = findAppliance(d.getAppliancelist() , id.getApplianceid());
			if ( a == null )
				continue ;

			if ( id.getTimer() != null )
			{
				id.getTimer().clear();
				id.getTimer().addAll(a.getTimer());
			}
			else 
				id.setTimer(a.getTimer());
			if ( id.getTimer() != null )
				for ( Timer t : id.getTimer())
					t.setInfrareddevice(id);
		}
	}
	
	private Appliance findAppliance(List<Appliance> appliancelist , String applianceid)
	{
		for ( Appliance a : appliancelist )
		{
			if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED.equals(a.getMajortype()) 
					&& a.getApplianceid().equals(applianceid) )
				return a ;
		}
		return null ;
	}
	
	private Appliance findAppliance(List<Appliance> appliancelist , int nuid)
	{
		for ( Appliance a : appliancelist )
		{
			if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(a.getMajortype()) 
					&& a.getNuid() == nuid )
				return a ;
		}
		return null ;
	}
	
	private  List<Remote> query(int page)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.setFirstResult(page * 100);
		
		cw.setMaxResults(100);
		
		return cw.list();
	}
}
