package com.iremote.action.scene;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Utils;
import com.iremote.domain.*;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

public class EditmultisceneAction {
	private String scene;
	private PhoneUser phoneuser;
	private ThirdPart thirdpart ;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private List<Scene> scenerst = new ArrayList<Scene>();
	private SceneService sceneService;
	private SceneHelper scenehelper;
	private boolean isPhoneUserHasCity = true;

	public String execute()
	{
		if(scene!=null && scene.length()>0){
			JSONArray jsonArray = JSON.parseArray(scene);
			sceneService = new SceneService();
			scenehelper = new SceneHelper();
			for(int i = 0; i < jsonArray.size(); i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Scene sc = sceneService.query(jsonObject.getIntValue("scenedbid"));
				if(sc == null){
					sc = new Scene();
					saveScene(sc,jsonObject);
					scenehelper.resetTimer(sc.getTimerlist());

				}else{
					TimerHelper.cancelTimer(sc.getTimerlist());
					
					updateScene(sc,jsonObject);
					
					HibernateUtil.getSession().flush();
					TimerHelper.addTimer(sc.getTimerlist());

				}
				Scene out = new Scene();
				out.setScenedbid(sc.getScenedbid());
				out.setSceneid(sc.getSceneid());
				scenerst.add(out);

				if (isPhoneUserHasCity)
					isPhoneUserHasCity = SceneHelper.checkCity(sc.getTimerlist());
			}
		}
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "editmultiscene");
		if ( phoneuser != null )
			phoneuser.setLastupdatetime(new Date());
		if (!isPhoneUserHasCity)
			resultCode = ErrorCodeDefine.PHONE_USER_HAS_NOT_SET_CITY;
		return Action.SUCCESS;
	}
	
	public void updateScene(Scene sc,JSONObject json){
		sc.setSceneid(StringUtils.isEmpty(json.getString("sceneid")) ? Utils.createtoken() : json.getString("sceneid"));
		sc.setName(json.getString("name"));
		sc.setExecutorsetting(json.getIntValue("executorsetting"));
		sc.getAssociationscenelist().clear();
		sc.getCommandlist().clear();
		sc.getTimerlist().clear();
		sc.getConditionlist().clear();
		scenehelper.saveAssociationscene(sc,json.getString("associationlist"));
		scenehelper.saveCommand(sc,json.getString("commandlist"));
	}
	
	public void saveScene(Scene sc,JSONObject json){
		sc.setSceneid(StringUtils.isEmpty(json.getString("sceneid")) ? Utils.createtoken() : json.getString("sceneid"));
		sc.setName(json.getString("name"));
		sc.setScenetype(json.getIntValue("scenetype"));
		sc.setAssociationscenelist(new ArrayList<Associationscene>());
		sc.setCommandlist(new ArrayList<Command>());
		sc.setTimerlist(new ArrayList<Timer>());
		sc.setConditionlist(new ArrayList<Conditions>());
		sc.setPhoneuserid(phoneuser.getPhoneuserid());
		sc.setExecutorsetting(json.getIntValue("executorsetting"));
		scenehelper.saveAssociationscene(sc,json.getString("associationlist"));
		scenehelper.saveCommand(sc,json.getString("commandlist"));
		scenehelper.saveCondition(sc,json.getString("conditionlist"));
		scenehelper.saveNotification(sc,json.getString("notification"));
		sceneService.save(sc);
		
		if ( sc.getScenetype() != null && sc.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE )
		{
			if ( phoneuser != null )
				sc.setPhoneuserid(phoneuser.getPhoneuserid());
			if ( thirdpart != null )
				sc.setThirdpartid(thirdpart.getThirdpartid());
		}
	}


	public int getResultCode() {
		return resultCode;
	}
	
	public void setScene(String scene) {
		this.scene = scene;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public List<Scene> getScenerst() {
		return scenerst;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

}
