package com.iremote.action.device.doorlock;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class AddDoorlockCardAction{
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(AddDoorlockCardAction.class);
		
	private int zwavedeviceid;
	private String cardname;
	private String cardinfo;
	private int cardtype;//0x1 MF; 0x2 ID card; 0xf Other 
	private String validfrom;
	private String validthrough;
	private Integer weekday;
	private String starttime ;
	private String endtime;
	private ZWaveDevice zWaveDevice;
	private int asynch;
	private int usercode;

	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	public String execute()
	{
		ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();
		zWaveDevice = zWaveDeviceService.query(zwavedeviceid);
		
		if(zWaveDevice == null){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( !ConnectionManager.contants(zWaveDevice.getDeviceid()))
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return Action.SUCCESS;
		}
		
		if ( StringUtils.isNotBlank(zWaveDevice.getProductor())
				&& zWaveDevice.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX.toLowerCase())
				&& !zWaveDevice.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_CHUANGJIA_PRODUCTOR_PREFIX_2.toLowerCase()))
		{
			ChuangjiaAddDoorlockCardAction action = new ChuangjiaAddDoorlockCardAction();
			action.setCardinfo(cardinfo);
			action.setCardname(cardname);
			action.setCardtype(cardtype);
			action.setValidfrom(validfrom);
			action.setValidthrough(validthrough);
			action.setZwavedeviceid(zwavedeviceid);
			action.setZwavedevice(zWaveDevice);
			String rs = action.execute();
			
			this.usercode = action.getUsercode();
			this.resultCode = action.getResultCode();
			return rs ;
		}
		else 
		{
			JwzhAddDoorlockCardAction action = new JwzhAddDoorlockCardAction();
			action.setCardinfo(cardinfo);
			action.setCardname(cardname);
			action.setCardtype(cardtype);
			action.setValidfrom(validfrom);
			action.setValidthrough(validthrough);
			action.setWeekday(weekday);
			action.setStarttime(starttime);
			action.setEndtime(endtime);
			action.setZwavedeviceid(zwavedeviceid);
			action.setZwavedevice(zWaveDevice);
			String rs = action.execute();
			
			this.usercode = action.getUsercode();
			this.resultCode = action.getResultCode();
			return rs ;
		}
		
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public void setCardinfo(String cardinfo) {
		this.cardinfo = cardinfo;
	}

	public void setCardtype(int cardtype) {
		this.cardtype = cardtype;
	}


	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getUsercode()
	{
		return usercode;
	}
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setAsynch(int asynch) {
		this.asynch = asynch;
	}
}
