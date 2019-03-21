package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="appversion")
public class AppVersion {
	
	private int appversionid ;
	private int platform ;
	private String latestversion;
	private int latestiversion;
	private String description;
	private String downloadurl;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "appversionid")  
	public int getAppversionid() {
		return appversionid;
	}
	public void setAppversionid(int appversionid) {
		this.appversionid = appversionid;
	}
    @Column(name = "platform")  
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
    @Column(name = "latestversion")  
	public String getLatestversion() {
		return latestversion;
	}
	public void setLatestversion(String latestversion) {
		this.latestversion = latestversion;
	}
    @Column(name = "latestiversion")  
	public int getLatestiversion() {
		return latestiversion;
	}
	public void setLatestiversion(int latestiversion) {
		this.latestiversion = latestiversion;
	}
	@Type(type="text") 
    @Column(name = "description")  
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    @Column(name = "downloadurl")  
	public String getDownloadurl() {
		return downloadurl;
	}
	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
}
