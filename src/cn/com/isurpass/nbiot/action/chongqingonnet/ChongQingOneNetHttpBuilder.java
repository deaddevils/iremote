package cn.com.isurpass.nbiot.action.chongqingonnet;

import com.iremote.common.http.AsyncHttpBuilder;

public class ChongQingOneNetHttpBuilder extends AsyncHttpBuilder
{
	private static final String URL = "http://api.heclouds.com/nbiot/execute";
	private static final String MAST_KEY = "FdaZKoWTBo6h65A2hrgIxsnJkGE=";

	public ChongQingOneNetHttpBuilder() 
	{
		super();
		super.appendHearder("api-key",MAST_KEY );
	}
	
	public void setImei(String imei)
	{
		String url = String.format("%s?imei=%s&obj_id=3200&obj_inst_id=0&res_id=5505", URL,imei);
		super.setUrl(url);
	}
}
