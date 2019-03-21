package com.iremote.infraredcode;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredcode.ac.ACCodeLiberayVO;
import com.iremote.infraredcode.liberay.ACProductorCodeLiberay;
import com.iremote.infraredcode.liberay.STBProductorCodeLiberay;
import com.iremote.infraredcode.liberay.TVProductorCodeLiberay;
import com.iremote.infraredcode.vo.RemoteControllerCode;
import com.opensymphony.xwork2.Action;

public class Downloadproductorcodeliberay {
	
	public static final ACProductorCodeLiberay acliberay = new ACProductorCodeLiberay();
	public static final STBProductorCodeLiberay stbliberay = new STBProductorCodeLiberay();
	public static final TVProductorCodeLiberay tvliberay = new TVProductorCodeLiberay();
	
	@SuppressWarnings("unused")
	private String deviceid;
	private String devicetype;
	private String productor;
	private int resultCode;
	private ACCodeLiberayVO[] codeliberys = new ACCodeLiberayVO[0];
	private RemoteControllerCode[] remotecontrol ;

	public String execute()
	{
		if ( IRemoteConstantDefine.DEVICE_TYPE_AC.equalsIgnoreCase(devicetype))
		{
			codeliberys = acliberay.getProductorCodeLiberay(productor);
			remotecontrol = acliberay.getRemoteControllerCode(productor);
		}
		if ( IRemoteConstantDefine.DEVICE_TYPE_TV.equalsIgnoreCase(devicetype))
		{
			codeliberys = tvliberay.getProductorCodeLiberay(productor);
			remotecontrol = tvliberay.getRemoteControllerCode(productor);
		}
		if ( IRemoteConstantDefine.DEVICE_TYPE_STB.equalsIgnoreCase(devicetype))
		{
			codeliberys = stbliberay.getProductorCodeLiberay(productor);
			remotecontrol = stbliberay.getRemoteControllerCode(productor);
		}
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}
	public ACCodeLiberayVO[] getCodeliberys() {
		return codeliberys;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}

	public RemoteControllerCode[] getRemotecontrol() {
		return remotecontrol;
	}
}
