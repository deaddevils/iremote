package com.iremote.action.scens;

import com.iremote.domain.Associationscene;
import com.iremote.service.AssociationsceneService;
import com.iremote.service.SceneService;
import com.iremote.test.db.Db;

public class DeleteAssociationSceneTest
{

	public static void main(String arg[])
	{
		Db.init();
		
		AssociationsceneService ass = new AssociationsceneService();
		
		Associationscene as = ass.query(879);
		
		ass.delete(as);
		SceneService ss = new SceneService();
		ss.delete(as.getScene());
		
		Db.commit();
	}
}
