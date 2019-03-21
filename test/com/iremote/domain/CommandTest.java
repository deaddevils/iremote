package com.iremote.domain;

import org.apache.commons.codec.binary.Base64;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.test.db.Db;

@SuppressWarnings("unused")
public class CommandTest {

	public static void main(String arg[])
	{
//		Db.init();
//		ZWaveDevice zd = (new ZWaveDeviceService()).query(1739);
//		System.out.println(zd.getDevicetype() == IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK);
//		System.out.println(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK.equals(zd.getDevicetype()));
		Utils.print("" , Base64.decodeBase64("AEYAAyUBAABIAAEAAEoAAyUDAABHAAEDAE8AAQM="));
//		Utils.print("" , Base64.decodeBase64("AEYAB2ANAAIlAf8ATwABBABKAAdgDQIAJQP/AEgAAQAARwABLw=="));
		
//		Utils.print("" , Base64.decodeBase64("AEYAAyAB/wBKAAMgA/8ASAABAABPAAEEAEcAASw="));
		
//		HibernateUtil.getSession().beginTransaction();
//		
//		
//		Command c = new Command();
//		
//		c.setZwavecommand(new byte[]{98,1,1});
//		
//		HibernateUtil.getSession().save(c);
//		
//		c = new Command();
//		c.setInfraredcode(new byte[]{1,2,3,4,5,6,7});
//		
//		HibernateUtil.getSession().save(c);
//		
//		HibernateUtil.getSession().getTransaction().commit();
//		HibernateUtil.closeSession();
	}
}
