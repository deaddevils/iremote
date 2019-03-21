package com.iremote.common.jms.vo;

import java.util.Date;

import com.iremote.domain.Remote;

public class RemoteOwnerChangeEvent extends RemoteEvent {
	
	private int oldownerid ;
	private int newownerid ;
	
	private String oldownerphonenumber; 
	private String newownerphonenumber ;
	
	private Remote remote;

	public RemoteOwnerChangeEvent() {
		super();
	}

	public RemoteOwnerChangeEvent(String deviceid, Date eventtime, int oldownerid, int newownerid,
			String oldownerphonenumber, String newownerphonenumber, long taskid) {
		super(deviceid, eventtime, taskid);
		this.oldownerid = oldownerid;
		this.newownerid = newownerid;
		this.oldownerphonenumber = oldownerphonenumber;
		this.newownerphonenumber = newownerphonenumber;
	}

	public int getOldownerid() {
		return oldownerid;
	}

	public void setOldownerid(int oldownerid) {
		this.oldownerid = oldownerid;
	}

	public int getNewownerid() {
		return newownerid;
	}

	public void setNewownerid(int newownerid) {
		this.newownerid = newownerid;
	}

	public String getOldownerphonenumber() {
		return oldownerphonenumber;
	}

	public void setOldownerphonenumber(String oldownerphonenumber) {
		this.oldownerphonenumber = oldownerphonenumber;
	}

	public String getNewownerphonenumber() {
		return newownerphonenumber;
	}

	public void setNewownerphonenumber(String newownerphonenumber) {
		this.newownerphonenumber = newownerphonenumber;
	}

	public Remote getRemote() {
		return remote;
	}

	public void setRemote(Remote remote) {
		this.remote = remote;
	}

	
}
