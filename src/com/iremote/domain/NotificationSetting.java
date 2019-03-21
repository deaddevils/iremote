package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iremote.common.IRemoteConstantDefine;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="notificationsetting")
public class NotificationSetting {

	private int notificationsettingid ;
	private int phoneuserid ;
	private String phonenumber;
	private int notificationtype;
	private int athome;
	private String starttime;
	private String endtime;
	private Integer startsecond;
	private Integer endsecond;
	private Integer app;
	private Integer ring;
	private String mail;
	private Integer builder_id;
	private String sound;

	public void ringToBuilderAndSound(){
		switch (ring){
			case 1:
				builder_id = IRemoteConstantDefine.JPUSH_BUILDER_1;
				sound = IRemoteConstantDefine.JPUSH_SOUND_1;
				break;
			case 2:
				builder_id = IRemoteConstantDefine.JPUSH_BUILDER_2;
				sound = IRemoteConstantDefine.JPUSH_SOUND_2;
				break;
			case 3:
				builder_id = IRemoteConstantDefine.JPUSH_BUILDER_3;
				sound = IRemoteConstantDefine.JPUSH_SOUND_3;
				break;
			default:
				break;
		}
	}


	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "notificationsettingid")  
	public int getNotificationsettingid() {
		return notificationsettingid;
	}
	public void setNotificationsettingid(int notificationsettingid) {
		this.notificationsettingid = notificationsettingid;
	}
    @Column(name = "notificationtype")  
	public int getNotificationtype() {
		return notificationtype;
	}
	public void setNotificationtype(int notificationtype) {
		this.notificationtype = notificationtype;
	}
    @Column(name = "athome")  
	public int getAthome() {
		return athome;
	}
	public void setAthome(int athome) {
		this.athome = athome;
	}
    @Column(name = "starttime")  
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
    @Column(name = "endtime")  
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
    @Column(name = "phonenumber")  
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
    @Column(name = "startsecond")  
	public Integer getStartsecond() {
		return startsecond;
	}
	public void setStartsecond(Integer startsecond) {
		this.startsecond = startsecond;
	}
    @Column(name = "endsecond")  
	public Integer getEndsecond() {
		return endsecond;
	}
	public void setEndsecond(Integer endsecond) {
		this.endsecond = endsecond;
	}
    @Column(name = "phoneuserid")  
	public int getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}

	public Integer getApp() {
		return app;
	}

	public void setApp(Integer app) {
		this.app = app;
	}

	public Integer getRing() {
		return ring;
	}

	public void setRing(Integer ring) {
		this.ring = ring;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getBuilder_id() {
		return builder_id;
	}

	public void setBuilder_id(Integer builder_id) {
		this.builder_id = builder_id;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}
}
