package com.iremote.task.notification;

import java.util.Arrays;

import com.iremote.common.taskmanager.TaskManager;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;

public class GooutWarningTest {

	public static void main(String arg[])
	{
		PhoneUserService pus = new PhoneUserService();
		PhoneUser user = pus.query(3);
		
		TaskManager.addTask(new GooutWarning(user , Arrays.asList(new String[]{"iRemote2005000000010"})));
		
		//Payload pl = PushHelper.createNotification(new String[]{"c3b24ff32351444e9635e19003ddfa173"}, "test", null);
		//PushMessage.push(pl, 0);
	}
}
