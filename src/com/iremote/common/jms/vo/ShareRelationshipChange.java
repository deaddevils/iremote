package com.iremote.common.jms.vo;


public class ShareRelationshipChange {

	private Integer[] phoneuserid;
	private int platform ;
	private long taskid ;
	
	public ShareRelationshipChange() {
		super();
	}

	public ShareRelationshipChange(Integer[] phoneuserid , int platform , long taskid) {
		super();
		this.phoneuserid = phoneuserid;
		this.platform = platform ;
		this.taskid = taskid ;
	}

	public long getTaskIndentify() {
		return taskid;
	}
	public Integer[] getPhoneuserid() {
		return phoneuserid;
	}

	public void setPhoneuserid(Integer[] phoneuserid) {
		this.phoneuserid = phoneuserid;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}
	
}
