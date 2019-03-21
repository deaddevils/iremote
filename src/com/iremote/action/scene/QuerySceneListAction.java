package com.iremote.action.scene;

import java.util.ArrayList;
import java.util.List;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;

public class QuerySceneListAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser ;
	private List<Scene> scenelist;
	
	public String execute()
	{
		SceneService ss = new SceneService();

		List<Scene> lst = ss.queryScenebyPhoneuserid(PhoneUserHelper.querybySharetoPhoneuserid(phoneuser.getPhoneuserid()));
		
		scenelist = new ArrayList<Scene>(lst.size());
		for ( Scene s : lst)
		{
			Scene svo = new Scene();
			svo.setName(s.getName());
			svo.setScenedbid(s.getScenedbid());
			svo.setSceneid(null);
			scenelist.add(svo);
		}
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public List<Scene> getScenelist() {
		return scenelist;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	
}
