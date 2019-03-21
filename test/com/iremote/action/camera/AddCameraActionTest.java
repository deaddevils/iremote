package com.iremote.action.camera;

import com.iremote.test.db.Db;

public class AddCameraActionTest {

	public static void main(String arg[])
	{
		Db.init();
		
		AddCameraAction action = new AddCameraAction();
		action.setApplianceuuid("VIEW-190371-YKKXR");
		action.setDevicetype("4");
		action.setName("fjdsaf");
		action.setProductorid("dahua");
		
		action.execute();
		Db.commit();
	}
}
