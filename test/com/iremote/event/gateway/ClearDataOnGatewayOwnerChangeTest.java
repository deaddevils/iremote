package com.iremote.event.gateway;

import java.util.Date;

import com.iremote.domain.Remote;
import com.iremote.test.db.Db;

public class ClearDataOnGatewayOwnerChangeTest
{
	public static void main(String arg[])
	{
		ClearDataOnGatewayOwnerChange cc = new ClearDataOnGatewayOwnerChange();
		cc.setDeviceid("iRemote4005000000001");
		cc.setEventtime(new Date());
		cc.setNewownerid(90);
		cc.setNewownerphonenumber("13502876070");
		cc.setOldownerid(169);
		cc.setOldownerphonenumber("13266836350");
		cc.setPhoneuserid(90);
		cc.setRemote(new Remote());
		
		Db.init();
		
		cc.run();
		Db.commit();
	}
}
