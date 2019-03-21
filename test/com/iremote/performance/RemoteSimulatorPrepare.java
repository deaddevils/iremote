package com.iremote.performance;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Transaction;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.ZWaveDeviceService;

public class RemoteSimulatorPrepare {
	
	private IremotepasswordService svr = new IremotepasswordService();
	private ZWaveDeviceService zdsvr = new ZWaveDeviceService();
	private int count = 6000 ;

	public static void main(String arg[])
	{
		RemoteSimulatorPrepare pp = new RemoteSimulatorPrepare();
		
		pp.initpara(arg);
		//HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
		Transaction tx = HibernateUtil.getSession().beginTransaction();

		pp.prepareUser();
		pp.prepareRemote();
		
		tx.commit();
		HibernateUtil.closeSession();

	}
	
	private void initpara(String arg[])
	{
		for ( int i = 0 ; i < arg.length ; i ++ )
		{
			if ( "-count".equals(arg[i]))
			{
				i ++ ;
				count = Integer.valueOf(arg[i]);
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	private void prepareUser()
	{

		CriteriaWrap cw = new CriteriaWrap(PhoneUser.class.getName());
		cw.add(ExpWrap.like("phonenumber", "135%"));
		cw.addFields(new String[]{"phonenumber"});
		
		List<String> lst = cw.list();
		
		Set<String> set = new HashSet<String>();
		set.addAll(lst);
		
		for ( int i = 0 ; i < count ; i ++ )
		{
			String pn = String.format("135%08d", i);
			if ( set.contains(pn) )
				continue;
			PhoneUserHelper.savePhoneUser(pn , pn);
		}
	}
	
	private void prepareRemote()
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.like("deviceid", "TiRemote2005000%"));
		cw.addFields(new String[]{"deviceid"});
		
		List<String> lst = cw.list();
		
		Set<String> set = new HashSet<String>();
		set.addAll(lst);
		
		for ( int i = 0 ; i < count ; i ++ )
		{
			String deviceid = String.format("TiRemote2005000%05d", i);
			if ( set.contains(deviceid))
				continue;
			prepareRemote(i);
			prepareDevice(deviceid);
		}
	}
	
	private void prepareRemote(int id)
	{
		
		Remote r = new Remote();
		r.setPhonenumber(String.format("135%08d" , id));
		//r.setPhoneuserid(phoneuser.getPhoneuserid());
		r.setSsid(String.format("ssid%03d" , id));
		r.setLatitude(114044323);
		r.setLongitude(22645638);
		r.setIp("127.0.0.1");
		r.setMac("EA:23:54:19:34");
		r.setStatus(IRemoteConstantDefine.REMOTE_STATUS_ONLINE);
		r.setDeviceid(String.format("TiRemote2005000%05d" , id));
		r.setCreatetime(new Date());
		r.setLastupdatetime(new Date());
		r.setPlatform(0);
		svr.save(r);

	}
	
	private void prepareDevice(String deviceid)
	{
		prepareDevice(deviceid , 2 ,1 );
		prepareDevice(deviceid , 3 ,3 );
		prepareDevice(deviceid , 4 ,2 );
		prepareDevice(deviceid , 5 ,4 );
		prepareDevice(deviceid , 6 ,3 );
		prepareDevice(deviceid , 7 ,6 );
		prepareDevice(deviceid , 8 ,5 );
		prepareDevice(deviceid , 9 ,7 );
		prepareDevice(deviceid , 10 ,9);
		prepareDevice(deviceid , 11 ,1 );
	}
	
	private void prepareDevice(String deviceid  , int nuid , int type)
	{
		ZWaveDevice z = new ZWaveDevice();
		z.setBattery(100);
		if ( type == 5 ) // IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK)
		{
			z.setStatus(255);// door lock default status is closed.
			z.setShadowstatus(z.getStatus());
		}

		z.setDeviceid(deviceid);
		z.setNuid(nuid);
		z.setDevicetype(String.valueOf(type));
		z.setName(String.format("%d_%d", type , nuid));
		
		zdsvr.saveOrUpdate(z);
	}
}
