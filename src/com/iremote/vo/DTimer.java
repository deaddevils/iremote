package com.iremote.vo;

public class DTimer {

	private int weekday;
	private int time ;
	private int[] infraredcode;
	private int[] zwavecommand;
	private String description;
	private int valid;
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
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
}
