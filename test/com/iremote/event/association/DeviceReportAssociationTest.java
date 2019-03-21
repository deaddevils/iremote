package com.iremote.event.association;

import com.alibaba.fastjson.JSON;
import com.iremote.test.db.Db;

public class DeviceReportAssociationTest
{
	public static void main(String arg[])
	{
		String str = "{\"applianceid\":\"11369118-D561-4B9E-87EC-7F6699FEBC35\",\"battery\":100,\"channel\":0,\"deviceid\":\"iRemote2005000000816\",\"devicetype\":\"12\",\"eventtime\":1489648051000,\"eventtype\":\"devicestatus\",\"name\":\"»úÐµÊÖ\",\"nuid\":4,\"oldstatus\":0,\"report\":\"HgkALgBHAAEEAEYAAyUD/wAMAAsxMzI2NjgzNjM1MAAfAAIHCQBoAARYyjmzAE8AAQLw\",\"status\":255,\"taskIndentify\":1489648051460,\"zwavedeviceid\":1726}";
		
		Db.init();
		
		DeviceReportAssociation dra = JSON.parseObject(str, DeviceReportAssociation.class);
		
		dra.run();
		
		Db.commit();
		
	}
}
