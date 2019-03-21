package com.iremote.thirdpart.jwzh;

import org.hibernate.Transaction;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Remote;
import com.iremote.service.IremotepasswordService;
@SuppressWarnings("unused")
public class UpdateRemoteSecurityKeyTest {

	public static void main(String arg[])
	{
		HibernateUtil.beginTransaction();
		
//		UpdateRemoteSecurityKey sk = new UpdateRemoteSecurityKey();
//		sk.setDeviceid("iRemote2005000000614");
//		sk.setSecuritykey("97C552C55FF95A8B2BB9D0C2D7624CCE");
//		sk.execute();
//		
//		System.out.println(JSONObject.toJSONString(sk));
		
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword("iRemote2005000000614");
		for ( int i = 0 ; i < remote.getSecritykey().length ; i ++ )
			System.out.print(String.format("%d ", remote.getSecritykey()[i] & 0xff));
		//151 197 82 197 95 249 90 139 43 185 208 194 215 98 76 206 
		HibernateUtil.commit();
		HibernateUtil.closeSession();
	}
}
