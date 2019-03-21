package com.iremote.thirdpart.wcj.vo;

public class Lock {

	private String communitycode;
	private String roomid;
	private int lockid;
	private int lockstate;
	
	public Lock(String communitycode, String roomid, int lockid, int lockstate) {
		super();
		this.communitycode = communitycode;
		this.roomid = roomid;
		this.lockid = lockid;
		this.lockstate = lockstate;
	}
	public String getCommunitycode() {
		return communitycode;
	}
	public void setCommunitycode(String communitycode) {
		this.communitycode = communitycode;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public int getLockid() {
		return lockid;
	}
	public void setLockid(int lockid) {
		this.lockid = lockid;
	}
	public int getLockstate() {
		return lockstate;
	}
	public void setLockstate(int lockstate) {
		this.lockstate = lockstate;
	}
}
