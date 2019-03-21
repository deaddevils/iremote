package com.iremote.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="room")
public class Room {

	private int roomdbid ;
	private String roomid ;
	private int phoneuserid ;
	private String phonenumber ;
	private String name ;
	private List<RoomAppliance> appliancelist = new ArrayList<RoomAppliance>();
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getRoomdbid() {
		return roomdbid;
	}
	public void setRoomdbid(int roomdbid) {
		this.roomdbid = roomdbid;
	}
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(targetEntity=RoomAppliance.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="room")   
	@BatchSize(size=300)
	public List<RoomAppliance> getAppliancelist() {
		return appliancelist;
	}
	public void setAppliancelist(List<RoomAppliance> appliancelist) {
		this.appliancelist = appliancelist;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
}
