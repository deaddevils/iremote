package com.iremote.infraredcode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.infraredcode.ac.DownloadACCodeLibeary;
import com.iremote.infraredcode.tv.TvCodeLiberay;
import com.opensymphony.xwork2.Action;

public class DownloadCodeLiberay {
	
	private static Log log = LogFactory.getLog(DownloadCodeLiberay.class);
	private String deviceid;
	private String codeid;
	private int resultCode = ErrorCodeDefine.CODELIBERAY_ID_ERROR;
	private int[] codelibery;
	
	public String execute()
	{
		
		if ( codeid == null )
			return Action.SUCCESS;
		
		if ( codeid.startsWith("AC_") )
			return downloadACCodeLiberay();
		if ( codeid.startsWith("TV_") )
			return downloadTVCodeLiberay();
		if ( codeid.startsWith("STB_") )
			return downloadSTBCodeLiberay();
		return Action.SUCCESS;
	}
	
	private String downloadACCodeLiberay()
	{
		DownloadACCodeLibeary dc = new DownloadACCodeLibeary();
		dc.setCodeid(codeid);
		dc.setDeviceid(deviceid);
		
		String rst = dc.execute();
		
		this.resultCode = dc.getResultCode();
		this.codelibery = dc.getCodelibery();
		
		return rst;
	}

	private String downloadTVCodeLiberay()
	{
		int id = Integer.valueOf(codeid.substring("TV_".length()));
		codelibery = CodeLiberayHelper.composeTVCodeLiberay(TvCodeLiberay.getCodeLiberay(id));
		
		if ( codelibery != null )
			resultCode = ErrorCodeDefine.SUCCESS;
		
		return Action.SUCCESS;
	}

	private String downloadSTBCodeLiberay()
	{
		int id = Integer.valueOf(codeid.substring("STB_".length()));
		codelibery = CodeLiberayHelper.composeSTBCodeLiberay(Downloadproductorcodeliberay.stbliberay.getCodeLiberay((id)));
		
		if ( codelibery != null )
			resultCode = ErrorCodeDefine.SUCCESS;
		
		return Action.SUCCESS;
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

	public String getCodeid() {
		return codeid;
	}
	
	public static void main(String arg[])
	{
		log.info(log.getClass().getName());
		log.info("fdsfsf");
	}
	
}
