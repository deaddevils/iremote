package com.iremote.init;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.Notification;
import com.iremote.domain.UserShare;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.NotificationService;
import com.iremote.test.db.Db;

public class NotificationInitTest {
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Db.init();
		for(int i=0; ; i = i+ 100){
			CriteriaWrap cw = new CriteriaWrap(Notification.class.getName());
			cw.setFirstResult(i);
			cw.setMaxResults(100);
			List<Notification> nList = cw.list();
			for(Notification nt : nList){
				CriteriaWrap cw2 = new CriteriaWrap(UserShare.class.getName());
				cw.add(ExpWrap.eq("shareuserid", nt.getPhoneuserid()));
				List<UserShare> uList = cw2.list();
				if(uList != null){
					for(UserShare userShare : uList){
						Notification n = new Notification();
						PropertyUtils.copyProperties(n, nt);
						n.setPhoneuserid(userShare.getTouserid());
						n.setNotificationid(0);
						//new NotificationService().save(n);
						JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_REPORT_SHARE, n);

					}
				}
				if(nt.getDeviceid() != null && nt.getZwavedeviceid() != null){
					CriteriaWrap cw3 = new CriteriaWrap(ZWaveDeviceShare.class.getName());
					cw.add(ExpWrap.eq("deviceid", nt.getDeviceid()));
					cw.add(ExpWrap.eq("zwavedeviceid", nt.getZwavedeviceid()));
					List<ZWaveDeviceShare> zsList = cw3.list();
					if(zsList != null){
						for(ZWaveDeviceShare zds : zsList){
							Notification n = new Notification();
							PropertyUtils.copyProperties(n, nt);
							n.setPhoneuserid(zds.getTouserid());
							n.setNotificationid(0);
							new NotificationService().save(n);
						}
					}
				}
			}
			if(nList == null || nList.size() < 100)
				return;
		}
	}
	
}
