package com.iremote.common.push.vo;

public class Extras {
	private String type ;
	private Integer shareid;
	private long reporttime;
	private String sound;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getShareid() {
		return shareid;
	}

	public void setShareid(Integer shareid) {
		this.shareid = shareid;
	}

	public long getReporttime() {
		return reporttime;
	}

	public void setReporttime(long reporttime) {
		this.reporttime = reporttime;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}
}
