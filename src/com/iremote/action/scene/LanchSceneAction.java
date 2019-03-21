package com.iremote.action.scene;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.domain.ThirdPart;
import com.iremote.scene.SceneExecutor;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;

public class LanchSceneAction 
{

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int scenedbid;
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart ;

	public String execute()
	{
		SceneService ss = new SceneService();
		Scene s = ss.query(scenedbid);
		
		if ( s == null )
		{
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		SceneExecutor se = new SceneExecutor(scenedbid , phoneuser , thirdpart , phoneuser.getPhonenumber(),
				IRemoteConstantDefine.OPERATOR_TYPE_SCENE,
				IRemoteConstantDefine.SCENE_EXECUTE_TYPE_USER_TRIGGER);
		se.run();
		//SceneHelper.executeScene(s.getCommandlist() , IRemoteConstantDefine.OPERATOR_TYPE_SCENE , phoneuser.getPhonenumber());
		
		return Action.SUCCESS;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setScenedbid(int scenedbid) {
		this.scenedbid = scenedbid;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}
	
	
}
