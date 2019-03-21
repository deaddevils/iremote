package com.iremote.action.scene;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.helper.TimerHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.*;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.spi.ErrorCode;

import java.util.ArrayList;
import java.util.Date;

public class
AddsceneAction {
	private String name;
	private Integer scenetype;
	private String sceneid;
	private String icon;
	private String associationlist;
	private String timerlist;
	private String commandlist;
	private String conditionlist;
	private String notification;
	private String starttime;
	private String endtime;
	private Integer weekday;
	private Integer enablestatus;
	private PhoneUser phoneuser ;
	private ThirdPart thirdpart ;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int scenedbid;
	private int executorsetting;
	
	public String execute()
	{
		if(scenetype == 0){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		Scene scene = saveScene();
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "addscene");
		if ( phoneuser != null )
			phoneuser.setLastupdatetime(new Date());

		if (!SceneHelper.checkCity(scene.getTimerlist())) {
			resultCode = ErrorCodeDefine.PHONE_USER_HAS_NOT_SET_CITY;
		}
		return Action.SUCCESS;
	}
	
	public Scene saveScene(){
		SceneService sceneService = new SceneService();
		SceneHelper scenehelper = new SceneHelper();
		Scene scene = new Scene();
		scene.setSceneid(StringUtils.isEmpty(sceneid) ? Utils.createtoken() : sceneid);
		scene.setName(name);
		scene.setIcon(icon);
		scene.setScenetype(scenetype);
		scene.setWeekday(weekday);
		if ( StringUtils.isNotEmpty(starttime)){
			scene.setStarttime(starttime);
			scene.setStartsecond(Utils.time2second(starttime));
		}
		if ( StringUtils.isNotEmpty(endtime)){
			scene.setEndsecond(Utils.time2second(endtime));
			scene.setEndtime(endtime);
		}
		scene.setAssociationscenelist(new ArrayList<Associationscene>());
		scene.setCommandlist(new ArrayList<Command>());
		scene.setTimerlist(new ArrayList<Timer>());
		scene.setConditionlist(new ArrayList<Conditions>());
		scenehelper.saveAssociationscene(scene,associationlist);
		scenehelper.saveCommand(scene,commandlist);
		scenehelper.saveTimer(scene,timerlist);
		scenehelper.saveCondition(scene,conditionlist);
		scenehelper.saveNotification(scene,notification);
		if(enablestatus == null) {
			enablestatus = IRemoteConstantDefine.SCENE_ENABLESTATUS_YES;
		}
		scene.setEnablestatus(enablestatus);
		if ( scene.getScenetype() != null && scene.getScenetype() == IRemoteConstantDefine.SCENE_TYPE_SCENE )
		{
			if ( phoneuser != null )
				scene.setPhoneuserid(phoneuser.getPhoneuserid());
			if ( thirdpart != null )
				scene.setThirdpartid(thirdpart.getThirdpartid());
		}

		scene.setExecutorsetting(executorsetting);
		scenedbid = sceneService.save(scene);
		HibernateUtil.getSession().flush();
		TimerHelper.addTimer(scene.getTimerlist());
		return scene;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setScenetype(Integer scenetype) {
		this.scenetype = scenetype;
	}
	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
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
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getScenedbid() {
		return scenedbid;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

    public void setEnablestatus(Integer enablestatus) {
        this.enablestatus = enablestatus;
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

	public void setExecutorsetting(int executorsetting) {
		this.executorsetting = executorsetting;
	}
}
