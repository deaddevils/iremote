package com.iremote.action.scene;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Scene;
import com.iremote.domain.ThirdPart;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

public class EditsceneAction {
	private int scenedbid;
	private String name;
	private String icon;
	private String starttime;
	private String endtime;
	private Integer weekday;
	private String associationlist;
	private String timerlist;
	private String commandlist;
	private String conditionlist;
	private String notification;
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart ;
	private Integer executorsetting;

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	
	public String execute()
	{
		Scene scene = updateScene();
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "editscene");
		if ( phoneuser != null )
			phoneuser.setLastupdatetime(new Date());

		if (scene != null && !SceneHelper.checkCity(scene.getTimerlist())) {
			resultCode = ErrorCodeDefine.PHONE_USER_HAS_NOT_SET_CITY;
		}
		return Action.SUCCESS;
	}
	
	public Scene updateScene(){
		SceneService sceneService = new SceneService();
		SceneHelper scenehelper = new SceneHelper();
		Scene scene = sceneService.query(scenedbid);
		if(scene == null){
			resultCode = ErrorCodeDefine.SCENE_NOT_EXIST;
			return null;
		}
		scene.setName(name);
		scene.setIcon(icon);
		scene.setWeekday(weekday);
		if (executorsetting != null) {
			scene.setExecutorsetting(executorsetting);
		}
		if ( StringUtils.isNotEmpty(starttime)){
			scene.setStarttime(starttime);
			scene.setStartsecond(Utils.time2second(starttime));
		}
		if ( StringUtils.isNotEmpty(endtime)){
			scene.setEndsecond(Utils.time2second(endtime));
			scene.setEndtime(endtime);
		}

		TimerHelper.cancelTimer(scene.getTimerlist());
		
		scene.getAssociationscenelist().clear();
		scene.getCommandlist().clear();
		scene.getTimerlist().clear();
		scene.getConditionlist().clear();
		scenehelper.saveAssociationscene(scene,associationlist);
		scenehelper.saveCommand(scene,commandlist);
		scenehelper.saveTimer(scene,timerlist);
		scenehelper.saveCondition(scene,conditionlist);
		scenehelper.saveNotification(scene,notification);

		if ( scene.getScenetype() != null && scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE )
		{
			if ( phoneuser != null )
				scene.setPhoneuserid(phoneuser.getPhoneuserid());
			if ( thirdpart != null )
				scene.setThirdpartid(thirdpart.getThirdpartid());
		}
		
		HibernateUtil.getSession().flush();
		TimerHelper.addTimer(scene.getTimerlist());
		return scene;
	}
	

	
	public void setScenedbid(int scenedbid) {
		this.scenedbid = scenedbid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setAssociationlist(String associationlist) {
		this.associationlist = associationlist;
	}

	public void setTimerlist(String timerlist) {
		this.timerlist = timerlist;
	}
	public void setCommandlist(String commandlist) {
		this.commandlist = commandlist;
	}
	public int getResultCode() {
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

	public void setConditionlist(String conditionlist) {
		this.conditionlist = conditionlist;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public void setExecutorsetting(Integer executorsetting) {
		this.executorsetting = executorsetting;
	}
}
