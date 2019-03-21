package com.iremote.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Command {

	private String deviceid;
	private String applianceid;
	private Integer infrareddeviceid;
	private Integer zwavedeviceid;
	private Integer launchscenedbid;
	private JSONArray commandjson;
	private int index ;
	private int delay;
	private int[] infraredcode;
	private int[] zwavecommand;
	private int[] zwavecommands;
	private String description;
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
	public int[] getInfraredcode() {
		return infraredcode;
	}
	public void setInfraredcode(int[] infraredcode) {
		this.infraredcode = infraredcode;
	}
	public int[] getZwavecommand() {
		return zwavecommand;
	}
	public void setZwavecommand(int[] zwavecommand) {
		this.zwavecommand = zwavecommand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int[] getZwavecommands() {
		return zwavecommands;
	}
	public void setZwavecommands(int[] zwavecommands) {
		this.zwavecommands = zwavecommands;
	}
	public Integer getInfrareddeviceid()
	{
		return infrareddeviceid;
	}
	public void setInfrareddeviceid(Integer infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}
	public Integer getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	public Integer getLaunchscenedbid()
	{
		return launchscenedbid;
	}
	public void setLaunchscenedbid(Integer launchscenedbid)
	{
		this.launchscenedbid = launchscenedbid;
	}
	public JSONArray getCommandjson()
	{
		return commandjson;
	}
	public void setCommandjson(JSONArray commandjson)
	{
		this.commandjson = commandjson;
	}
}
