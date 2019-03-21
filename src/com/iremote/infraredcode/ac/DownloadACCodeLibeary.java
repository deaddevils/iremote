package com.iremote.infraredcode.ac;

import com.iremote.common.ErrorCodeDefine;

import com.opensymphony.xwork2.Action;

public class DownloadACCodeLibeary 
{
	private String deviceid;
	private String codeid;
	private int resultCode = ErrorCodeDefine.CODELIBERAY_ID_ERROR;
	private int[] codelibery;
	
	public String getCodeid() {
		return codeid;
	}

	public String execute()
	{
		if ( codeid == null || !codeid.startsWith("AC_"))
			return Action.SUCCESS;
		
		int id = Integer.valueOf(codeid.substring("AC_".length()));
		codelibery = ACCodeLiberay.composeCodeLiberay(id);
		if ( codelibery != null )
			resultCode = ErrorCodeDefine.SUCCESS;
//		if ( isValidRequest(id , "tcl") == true )
//			return Action.SUCCESS;
//		if ( isValidRequest(id , "Midea") == true )
//			return Action.SUCCESS;
//		if ( isValidRequest(id , "Whirlpool") == true )
//			return Action.SUCCESS;
//		if ( isValidRequest(id , "Other") == true )
//			return Action.SUCCESS;
		return Action.SUCCESS;
	}

	private boolean isValidRequest(int id , String productor)
	{
		int[] pi = ACProductorLibery.getProductorLibery(productor);
		
		for ( int i = 0 ; i < pi.length ; i ++ )
		{
			if ( id == pi[i] )
			{
				codelibery = ACCodeLiberay.composeCodeLiberay(id);
				resultCode = ErrorCodeDefine.SUCCESS;
				return true;
			}
		}
		
		return false;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public int[] getCodelibery() {
		return codelibery;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	
	
	public static void main(String arg[])
	{
		DownloadACCodeLibeary dac = new DownloadACCodeLibeary();
		dac.codeid = "AC_22";
		
		dac.execute();
		
		System.out.println(66 ^ 84);
	}
	
}
