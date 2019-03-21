package com.iremote.action.infraredcode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.InfraredDevice;
import com.iremote.infraredcode.ac.ACCodeLiberay;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfreredCodeLiberayService;
import com.opensymphony.xwork2.Action;

public class SetInfreredDevicecodeid
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private int infrareddeviceid;
	private int codeid;
	private String productor;
	
	public String execute()
	{
		InfraredDeviceService svr = new InfraredDeviceService();
		InfraredDevice id = svr.query(infrareddeviceid);
		if ( id == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		id.setCodeid(String.format("%s_%d" , id.getDevicetype() , codeid));
		id.setProductorid(productor);
		
		InfreredCodeLiberayService cls = new InfreredCodeLiberayService();
		String str = cls.queryByCodeid(codeid, id.getDevicetype());
		
//		if ( IRemoteConstantDefine.DEVICE_TYPE_AC.equals(id.getDevicetype()))
//		{
//			JSONArray ja = JSON.parseArray(str);
//			Integer[] lby = ja.toArray(new Integer[0]);
//			int[] liberay = new int[lby.length];
//			for ( int i = 0 ; i < lby.length ; i ++ )
//				liberay[i] = lby[i] ;
//			id.setCodelibery(ACCodeLiberay.composeCodeLiberay(liberay, 1));
//		}
//		else 
			id.setCodeliberyjson(str);
		
		return Action.SUCCESS;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setInfrareddeviceid(int infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}
	public void setCodeid(int codeid)
	{
		this.codeid = codeid;
	}

	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	
	
}
