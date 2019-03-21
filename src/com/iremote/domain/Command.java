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
@Table(name="command")
public class Command {

	private int commandid ;
	private Associationscene associationscene;
	private Scene scene ;
	private String deviceid;
	private String applianceid;
	private Integer zwavedeviceid;
	private Integer infrareddeviceid;
	private int index ;
	private int delay;
	private byte[] infraredcode;
	private byte[] zwavecommand;
	private byte[] zwavecommands;
	private String infraredcodebase64;
	private String zwavecommandbase64;
	private String zwavecommandsbase64;
	private Integer weekday;
	private String starttime;
	private String endtime ;
	private Integer startsecond;
	private Integer endsecond;
	private String description ;
	private JSONArray commandjson;
	private String commandjsonstr;
	private ZWaveDevice zwavedevice;
	private InfraredDevice infrareddevice;
	private Camera camera;
	private Integer launchscenedbid;
	private Integer cameraid;

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "commandid")  
	public int getCommandid() {
		return commandid;
	}
	public void setCommandid(int commandid) {
		this.commandid = commandid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getApplianceid() {
		return applianceid;
	}
	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
	}
    @Column(name = "cmdindex")  
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=Associationscene.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)           
	@JoinColumn(name="associationsceneid",referencedColumnName="associationsceneid",nullable=true)
	public Associationscene getAssociationscene() {
		return associationscene;
	}
	public void setAssociationscene(Associationscene associationscene) {
		this.associationscene = associationscene;
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

	@Column(name="cameraid",insertable=false,updatable=false)
	public Integer getCameraid() {
		return cameraid;
	}

	public void setCameraid(Integer cameraid) {
		this.cameraid = cameraid;
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=Camera.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)
	@JoinColumn(name="cameraid",referencedColumnName="cameraid",nullable=true)
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
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
	@Transient
	public byte[] getZwavecommands() {
		return zwavecommands;
	}
	public void setZwavecommands(byte[] zwavecommands) {
		this.zwavecommands = zwavecommands;
		zwavecommandsbase64 = Base64.encodeBase64String(this.zwavecommands);
	}
	public String getZwavecommandsbase64() {
		return zwavecommandsbase64;
	}
	public void setZwavecommandsbase64(String zwavecommandsbase64) {
		this.zwavecommandsbase64 = zwavecommandsbase64;
		zwavecommands = Base64.decodeBase64(zwavecommandsbase64);
	}
	public Integer getWeekday() {
		return weekday;
	}
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
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
	public String getStarttime()
	{
		return starttime;
	}
	public void setStarttime(String starttime)
	{
		this.starttime = starttime;
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

	public Integer getLaunchscenedbid() {
		return launchscenedbid;
	}
	public void setLaunchscenedbid(Integer launchscenedbid) {
		this.launchscenedbid = launchscenedbid;
	}
	
	@Column(name="zwavedeviceid",insertable=false,updatable=false)
	public Integer getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	@Column(name="infrareddeviceid",insertable=false,updatable=false)
	public Integer getInfrareddeviceid()
	{
		return infrareddeviceid;
	}
	public void setInfrareddeviceid(Integer infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
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

}
