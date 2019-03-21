package com.iremote.domain;

import com.alibaba.fastjson.JSON;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.CommandService;
import com.iremote.test.db.Db;

public class AssociationsceneTest
{
	public static void main(String arg[])
	{
		Db.init();
		
		AssociationsceneService ass = new AssociationsceneService();
		Associationscene as = ass.query(598);
		
		
		System.out.println(JSON.toJSONString(as));
		
		CommandService cs = new CommandService();
		for ( Command c : as.getCommandlist())
			cs.delete(c);
		
		Db.commit();
		
	}
}
