package com.iremote.action.infraredcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.service.InfreredDeviceProductorCodeMapService;
import com.iremote.service.InfreredDeviceProductorMapService;
import com.opensymphony.xwork2.Action;

public class QueryCodeLiberayAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String devicetype;
	private String productor;
	private Integer[] codeids;
	
	
	public String execute()
	{
		InfreredDeviceProductorMapService mapsvr = new InfreredDeviceProductorMapService();
		
		List<String> plst1 = mapsvr.queryMapProductor(productor , devicetype);
		List<String> plst = new ArrayList<String>();
		plst.add(productor);
		plst.addAll(plst1);
		
		InfreredDeviceProductorCodeMapService svr = new InfreredDeviceProductorCodeMapService();
		List<String> clst = svr.queryCodeidsByProductor(plst , devicetype);
		
		Set<Integer> cst = new HashSet<Integer>();
		for ( String s : clst )
		{
			JSONArray ja = JSON.parseArray(s);
			
			cst.addAll( Arrays.asList(ja.toArray(new Integer[0])));
		}
		
		codeids = cst.toArray(new Integer[0]);
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public Integer[] getCodeids()
	{
		return codeids;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	
	
}
