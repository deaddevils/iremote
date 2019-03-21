package com.iremote.vo;

import java.util.ArrayList;
import java.util.List;

import com.iremote.domain.Associationscene;
import com.iremote.domain.Conditions;
import com.iremote.domain.SceneNotification;
import com.iremote.domain.Timer;

public class Scene {

	private int scenedbid;
	private String name;
	private String phonenumber;
	private String sceneid;
	private int enablestatus;
	private String icon;
	private String starttime;
	private String endtime;
	private Integer weekday;
	private List<Associationscene> associationlist = new ArrayList<Associationscene>();
	private List<Command> commandlist = new ArrayList<Command>();
	private List<Timer> timerlist = new ArrayList<Timer>();
	private List<Conditions> conditionlist = new ArrayList<Conditions>();
	private SceneNotification scenenotification;
	private Integer executorsetting = 0; //000

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getSceneid() {
		return sceneid;
	}
	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Command> getCommandlist() {
		return commandlist;
	}
	public void setCommandlist(List<Command> commandlist) {
		this.commandlist = commandlist;
	}
	public int getScenedbid()
	{
		return scenedbid;
	}
	public void setScenedbid(int scenedbid)
	{
		this.scenedbid = scenedbid;
	}
	public List<Associationscene> getAssociationlist()
	{
		return associationlist;
	}
	public void setAssociationlist(List<Associationscene> associationlist)
	{
		this.associationlist = associationlist;
	}
	public List<Timer> getTimerlist()
	{
		return timerlist;
	}
	public void setTimerlist(List<Timer> timerlist)
	{
		this.timerlist = timerlist;
	}

	public SceneNotification getScenenotification() {
		return scenenotification;
	}

	public void setScenenotification(SceneNotification scenenotification) {
		this.scenenotification = scenenotification;
	}

	public List<Conditions> getConditionlist() {
		return conditionlist;
	}

	public void setConditionlist(List<Conditions> conditionlist) {
		this.conditionlist = conditionlist;
	}

	public int getEnablestatus() {
		return enablestatus;
	}

	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public Integer getExecutorsetting() {
		return executorsetting;
	}

	public void setExecutorsetting(Integer executorsetting) {
		if (executorsetting != null) {
			this.executorsetting = executorsetting;
		}
	}
}
