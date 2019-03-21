package com.iremote.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="serverruntimglog")
public class ServerRuntimeLog
{
	private int serverruntimglogid ;
	private int onlinegatewaycount;
	private Date time ;
	
	@Id    
	public int getServerruntimglogid()
	{
		return serverruntimglogid;
	}
	public void setServerruntimglogid(int serverruntimglogid)
	{
		this.serverruntimglogid = serverruntimglogid;
	}
	public int getOnlinegatewaycount()
	{
		return onlinegatewaycount;
	}
	public void setOnlinegatewaycount(int onlinegatewaycount)
	{
		this.onlinegatewaycount = onlinegatewaycount;
	}
	public Date getTime()
	{
		return time;
	}
	public void setTime(Date time)
	{
		this.time = time;
	}
}
