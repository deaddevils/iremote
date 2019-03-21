package com.iremote.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="associationscene")
public class Associationscene {

	private int associationsceneid;
	private ZWaveDevice zwavedevice;
	private Integer zwavedeviceid ;
	private Integer cameraid ;
	private int channelid;
	private int status;
	private List<Command> commandlist;
	private int scenetype;
	private String devicestatus;
	private Scene scene;
	private Integer scenedbid;
	private String operator;
	private String description;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "associationsceneid")  
	public int getAssociationsceneid() {
		return associationsceneid;
	}
	public void setAssociationsceneid(int associationsceneid) {
		this.associationsceneid = associationsceneid;
	}
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@OneToMany(targetEntity=Command.class,cascade={CascadeType.DETACH,CascadeType.REFRESH},fetch=FetchType.LAZY,orphanRemoval=false,mappedBy="associationscene")   
	@BatchSize(size=300)
	public List<Command> getCommandlist() {
		return commandlist;
	}
	public void setCommandlist(List<Command> commandlist) {
		this.commandlist = commandlist;
	}

	public int getScenetype() {
		return scenetype;
	}
	public void setScenetype(int scenetype) {
		this.scenetype = scenetype;
	}
	public String getDevicestatus() {
		return devicestatus;
	}
	public void setDevicestatus(String devicestatus) {
		this.devicestatus = devicestatus;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	@Column(name = "scenedbid",insertable=false,updatable=false)  
	public Integer getScenedbid()
	{
		return scenedbid;
	}
	public void setScenedbid(Integer scenedbid)
	{
		this.scenedbid = scenedbid;
	}
	@Column(name = "zwavedeviceid",insertable=false,updatable=false)  
	public Integer getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Integer getCameraid() {
		return cameraid;
	}
	public void setCameraid(Integer cameraid) {
		this.cameraid = cameraid;
	}
}
