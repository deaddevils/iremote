package com.iremote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="timer")
public class Timer {

	private int timerid;
	private ZWaveDevice zwavedevice;
	private InfraredDevice infrareddevice;
	private int weekday;
	private int time;
	private byte[] infraredcode;
	private byte[] zwavecommand;
	private String infraredcodebase64;
	private String zwavecommandbase64;
	private String description;
	private int valid;
	private int executor;
	private int scenetype;
	private String excutetime;
	private Scene scene;
	private String commandjsonstr;
	private JSONArray commandjson;
	private Integer scenedbid ;
	private int timetype;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "timerid")  
	public int getTimerid() {
		return timerid;
	}
	public void setTimerid(int timerid) {
		this.timerid = timerid;
	}

	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=ZWaveDevice.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)           
	@JoinColumn(name="zwavedeviceid",referencedColumnName="zwavedeviceid",nullable=true)
	public ZWaveDevice getZwavedevice() {
		return zwavedevice;
	}
	public void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
	}
	public int getWeekday() {
		return weekday;
	}
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@Transient
	public byte[] getInfraredcode() {
		return infraredcode;
	}
	public void setInfraredcode(byte[] infraredcode) {
		this.infraredcode = infraredcode;
		infraredcodebase64 = Base64.encodeBase64String(this.infraredcode);
	}
	@Transient
	public byte[] getZwavecommand() {
		return zwavecommand;
	}
	public void setZwavecommand(byte[] zwavecommand) {
		this.zwavecommand = zwavecommand;
		zwavecommandbase64 = Base64.encodeBase64String(this.zwavecommand);
	}
	public String getInfraredcodebase64() {
		return infraredcodebase64;
	}
	public void setInfraredcodebase64(String infraredcodebase64) {
		this.infraredcodebase64 = infraredcodebase64;
		infraredcode = Base64.decodeBase64(infraredcodebase64);
	}
	public String getZwavecommandbase64() {
		return zwavecommandbase64;
	}
	public void setZwavecommandbase64(String zwavecommandbase64) {
		this.zwavecommandbase64 = zwavecommandbase64;
		zwavecommand = Base64.decodeBase64(zwavecommandbase64);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public int getExecutor() {
		return executor;
	}
	public void setExecutor(int executor) {
		this.executor = executor;
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=InfraredDevice.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)           
	@JoinColumn(name="infrareddeviceid",referencedColumnName="infrareddeviceid",nullable=true)
	public InfraredDevice getInfrareddevice() {
		return infrareddevice;
	}
	public void setInfrareddevice(InfraredDevice infrareddevice) {
		this.infrareddevice = infrareddevice;
	}
	public int getScenetype() {
		return scenetype;
	}
	public void setScenetype(int scenetype) {
		this.scenetype = scenetype;
	}
	
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=Scene.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)           
	@JoinColumn(name="scenedbid",referencedColumnName="scenedbid",nullable=true)
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public String getExcutetime() {
		return excutetime;
	}
	public void setExcutetime(String excutetime) {
		this.excutetime = excutetime;
	}
	@Column(name = "scenedbid" ,insertable=false, updatable=false)
	public Integer getScenedbid()
	{
		return scenedbid;
	}
	public void setScenedbid(Integer scenedbid)
	{
		this.scenedbid = scenedbid;
	}
	
	@Transient
	public JSONArray getCommandjson() {
		return commandjson;
	}
	public void setCommandjson(JSONArray commandjson) {
		this.commandjson = commandjson;
		if ( this.commandjson != null )
			this.commandjsonstr = this.commandjson.toJSONString();
		else 
			this.commandjsonstr = null ;
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@Type(type="text")
	@Column(name="commandjson")
	public String getCommandjsonstr()
	{
		return commandjsonstr;
	}
	public void setCommandjsonstr(String commandjsonstr)
	{
		this.commandjsonstr = commandjsonstr;
		if ( this.commandjsonstr != null )
			this.commandjson = JSONObject.parseArray(this.commandjsonstr);
		else 
			this.commandjson = null ;
	}

	public int getTimetype() {
		return timetype;
	}

	public void setTimetype(int timetype) {
		this.timetype = timetype;
	}
}
