package com.iremote.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name="oemproductor")
public class OemProductor
{
	private int oemproductorid ;
	private String name ;
	private int platform ;
	private String pushmasterkey;
	private String pushappkey;
	private String smssign ;
	private String deviceprefix;
	private String lechangeappid ;
	private String lechangeappSecret ;
	private String abroadlechangeappid ;
	private String abroadlechangeappSecret ;
	private String lechangeadmin ;
	private String androidappdownloadurl;
	private String iosappdownloadurl ;
	private String adverpicname ;
	private List<OemProductorAttribute> oemproductorattributelist;
	
	@Id    
	public int getOemproductorid()
	{
		return oemproductorid;
	}
	public void setOemproductorid(int oemproductorid)
	{
		this.oemproductorid = oemproductorid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getPlatform()
	{
		return platform;
	}
	public void setPlatform(int platform)
	{
		this.platform = platform;
	}
	public String getPushmasterkey()
	{
		return pushmasterkey;
	}
	public void setPushmasterkey(String pushmasterkey)
	{
		this.pushmasterkey = pushmasterkey;
	}
	public String getPushappkey()
	{
		return pushappkey;
	}
	public void setPushappkey(String pushappkey)
	{
		this.pushappkey = pushappkey;
	}
	public String getSmssign()
	{
		return smssign;
	}
	public void setSmssign(String smssign)
	{
		this.smssign = smssign;
	}
	public String getDeviceprefix()
	{
		return deviceprefix;
	}
	public void setDeviceprefix(String deviceprefix)
	{
		this.deviceprefix = deviceprefix;
	}
	public String getLechangeappid()
	{
		return lechangeappid;
	}
	public void setLechangeappid(String lechangeappid)
	{
		this.lechangeappid = lechangeappid;
	}
	public String getLechangeappSecret()
	{
		return lechangeappSecret;
	}
	public void setLechangeappSecret(String lechangeappSecret)
	{
		this.lechangeappSecret = lechangeappSecret;
	}
	public String getLechangeadmin()
	{
		return lechangeadmin;
	}
	public void setLechangeadmin(String lechangeadmin)
	{
		this.lechangeadmin = lechangeadmin;
	}
	public String getAndroidappdownloadurl()
	{
		return androidappdownloadurl;
	}
	public void setAndroidappdownloadurl(String androidappdownloadurl)
	{
		this.androidappdownloadurl = androidappdownloadurl;
	}
	public String getIosappdownloadurl()
	{
		return iosappdownloadurl;
	}
	public void setIosappdownloadurl(String iosappdownloadurl)
	{
		this.iosappdownloadurl = iosappdownloadurl;
	}
	public String getAdverpicname() {
		return adverpicname;
	}
	public void setAdverpicname(String adverpicname) {
		this.adverpicname = adverpicname;
	}

	public String getAbroadlechangeappid() {
		return abroadlechangeappid;
	}

	public void setAbroadlechangeappid(String abroadlechangeappid) {
		this.abroadlechangeappid = abroadlechangeappid;
	}

	public String getAbroadlechangeappSecret() {
		return abroadlechangeappSecret;
	}

	public void setAbroadlechangeappSecret(String abroadlechangeappSecret) {
		this.abroadlechangeappSecret = abroadlechangeappSecret;
	}
	@OneToMany(targetEntity=OemProductorAttribute.class,cascade={CascadeType.ALL,CascadeType.REMOVE},fetch=FetchType.EAGER ,orphanRemoval=true,mappedBy="oemproductor")
	public List<OemProductorAttribute> getOemproductorattributelist() {
		return oemproductorattributelist;
	}
	public void setOemproductorattributelist(List<OemProductorAttribute> oemproductorattributelist) {
		this.oemproductorattributelist = oemproductorattributelist;
	}
}
