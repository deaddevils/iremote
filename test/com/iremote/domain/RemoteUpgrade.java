package com.iremote.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.vo.Appliance;
import com.iremote.vo.RemoteData;
@SuppressWarnings("unused")
public class RemoteUpgrade 
{
	
	public static void main(String arg[] )
	{
		RemoteUpgrade ru = new RemoteUpgrade();
		ru.upgradetimezone();
	}
	
	private void upgradetimezone()
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
//				if ( r.getTimezone() == null && d.getTimezone() != null )
//						r.setTimezone(d.getTimezone());
//
//				updateZwavedevice(d);
//				updateInfreddevice(d);
//			}
//			
//			HibernateUtil.commit();
//		}
//		
//		HibernateUtil.closeSession();
	}
	
	private void updateZwavedevice(RemoteData d)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		List<ZWaveDevice> zlst = zds.querybydeviceid(d.getDeviceid());
		
		for ( ZWaveDevice zd : zlst )
		{
			Appliance a = findAppliance(d.getAppliancelist() , zd.getNuid());
			if ( a == null )
				continue ;
			zd.setApplianceid(a.getApplianceid());
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
	
	private void updateInfreddevice(RemoteData d)
	{
		InfraredDeviceService svr = new InfraredDeviceService();
		
		List<InfraredDevice> lst = svr.querybydeviceid(d.getDeviceid());

		for ( InfraredDevice id : lst )
		{
			Appliance a = findAppliance(d.getAppliancelist() , id.getApplianceid());
			if ( a == null )
				continue ;

			id.setDeviceid(d.getDeviceid());
			id.setApplianceid(a.getApplianceid());
			id.setCodeid(a.getCodeid());
			id.setCodeindex(a.getCodeindex());
			id.setCodelibery(a.getCodelibery());
			id.setControlmodeid(a.getControlmodeid());
			id.setDevicetype(a.getDevicetype());
			id.setMajortype(a.getMajortype());
			id.setName(a.getName());
			id.setProductorid(a.getProductorid());
			
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
	
	public  void upgradsecuritykey(String arg[])
	{
		
		for ( ; ; )
		{
			HibernateUtil.beginTransaction();
			
			List<Remote> lst = query();
			
			if ( lst == null || lst.size() == 0 )
				break;
			
			for ( Remote c : lst )
			{
				if ( c.getSecritykey() != null && c.getSecritykey().length > 0 )
					c.setSecritykeybase64(Base64.encodeBase64String(c.getSecritykey()));
				if ( c.getZwavescuritykey() != null && c.getZwavescuritykey().length > 0 )
					c.setZwavescuritykeybase64(Base64.encodeBase64String(c.getZwavescuritykey()));
			}
			
			HibernateUtil.commit();
		}
		
		HibernateUtil.closeSession();
		
	}
	
	private  List<Remote> query(int page)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.setFirstResult(page * 100);
		
		cw.setMaxResults(100);
		
		return cw.list();
	}
	
	private  List<Remote> query()
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.or(ExpWrap.and(Restrictions.isNotNull("zwavescuritykey"), Restrictions.isNull("zwavescuritykeybase64")) , 
				ExpWrap.and(Restrictions.isNotNull("secritykey"), Restrictions.isNull("secritykeybase64"))));
		
		cw.setMaxResults(100);
		
		return cw.list();
	}
}
