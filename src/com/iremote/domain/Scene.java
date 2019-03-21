package com.iremote.domain;

import java.util.List;

import javax.persistence.*;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="scene")
public class Scene {
	private int scenedbid ;
	private int phoneuserid;
	private String name ;
	private String sceneid = Utils.createtoken();
	private String icon ;
	private String starttime;
	private String endtime;
	private Integer weekday;
	private Integer startsecond;
	private Integer endsecond;
	private int enablestatus = IRemoteConstantDefine.SCENE_ENABLESTATUS_YES;
	private Integer thirdpartid;
	private Integer scenetype;
	private List<Command> commandlist;
	private List<Associationscene> associationscenelist;
	private List<Timer> timerlist;
	private List<Conditions> conditionlist;
	private SceneNotification scenenotification;
	private Integer executorsetting = 0; //000


	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "scenedbid")  
	public int getScenedbid() {
		return scenedbid;
	}
	public void setScenedbid(int scenedbid) {
		this.scenedbid = scenedbid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@OneToMany(targetEntity=Associationscene.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="scene")
	@BatchSize(size=300)
	public List<Associationscene> getAssociationscenelist() {
		return associationscenelist;
	}
	public void setAssociationscenelist(List<Associationscene> associationscenelist) {
		this.associationscenelist = associationscenelist;
	}
	@OneToMany(targetEntity=Command.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="scene")   
	@BatchSize(size=300)
	public List<Command> getCommandlist() {
		return commandlist;
	}
	public void setCommandlist(List<Command> commandlist) {
		this.commandlist = commandlist;
	}
	@OneToMany(targetEntity=Timer.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="scene")   
	@BatchSize(size=300)
	public List<Timer> getTimerlist() {
		return timerlist;
	}
	public void setTimerlist(List<Timer> timerlist) {
		this.timerlist = timerlist;
	}
	public Integer getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(Integer phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public Integer getThirdpartid() {
		return thirdpartid;
	}
	public Integer getScenetype() {
		return scenetype;
	}
	public void setThirdpartid(Integer thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public void setScenetype(Integer scenetype) {
		this.scenetype = scenetype;
	}

	public int getEnablestatus() {
		return enablestatus;
	}

	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}
	@OneToMany(targetEntity=Conditions.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="scene")
	@BatchSize(size=300)
	public List<Conditions> getConditionlist() {
		return conditionlist;
	}

	public void setConditionlist(List<Conditions> conditionlist) {
		this.conditionlist = conditionlist;
	}
	@OneToOne(cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true ,mappedBy="scene")
	public SceneNotification getScenenotification() {
		return scenenotification;
	}

	public void setScenenotification(SceneNotification scenenotification) {
		this.scenenotification = scenenotification;
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

	public Integer getStartsecond() {
		return startsecond;
	}

	public void setStartsecond(Integer startsecond) {
		this.startsecond = startsecond;
	}

	public Integer getEndsecond() {
		return endsecond;
	}

	public void setEndsecond(Integer endsecond) {
		this.endsecond = endsecond;
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
