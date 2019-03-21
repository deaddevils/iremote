package com.iremote.action.scene;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;

public class DeletesceneAction {
	private int scenedbid;
	private PhoneUser phoneuser ;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute(){
		deleteScene();
		deleteDoorlockAssociation();
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "deletescene");
		if ( phoneuser != null )
			phoneuser.setLastupdatetime(new Date());

		com.iremote.action.helper.SceneHelper.clearOnDeleteScene(scenedbid);

		return Action.SUCCESS;
	}
	
	public void deleteScene(){
		SceneService sceneService = new SceneService();
		Scene scene = sceneService.query(scenedbid);
		if(scene==null){
			resultCode = ErrorCodeDefine.SCENE_NOT_EXIST;
			return;
		}
		if(scene.getTimerlist() != null && scene.getTimerlist().size() > 0){
			TimerHelper.cancelTimer(scene.getTimerlist());
		}
		sceneService.delete(scene);
		
	}
	public void deleteDoorlockAssociation(){
		DoorlockAssociationService das = new DoorlockAssociationService();
		das.deletebyobjtypeandobjid(1, scenedbid);
	}
	
	public void setScenedbid(int scenedbid) {
		this.scenedbid = scenedbid;
	}
	public int getResultCode() {
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
}
