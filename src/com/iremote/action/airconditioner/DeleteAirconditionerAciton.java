package com.iremote.action.airconditioner;

import java.util.Date;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class DeleteAirconditionerAciton {
	private Integer zwavedeviceid;
	private static Log log = LogFactory.getLog(DeleteAirconditionerAciton.class);
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private ZWaveDevice zwaveDevice;
	private PhoneUser phoneuser ;
	private int capabilitycode;
	private String message;
	
	public String execute(){
		delZwavedvice();
		this.phoneuser.setLastupdatetime(new Date());
		deviceEvent();
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);
		return Action.SUCCESS;
	}

	public void deviceEvent(){
		if(zwaveDevice == null)
			return;
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent(zwaveDevice.getZwavedeviceid() , zwaveDevice.getDeviceid() , zwaveDevice.getNuid() ,IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE, new Date() , 0);
		zde.setWarningstatuses(zwaveDevice.getWarningstatuses());
		zde.setDevicetype(zwaveDevice.getDevicetype());
		zde.setApplianceid(zwaveDevice.getApplianceid());
		JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_DELETE_ZWAVE_DEVICE,zde);
	}
	
	public void delZwavedvice(){
		ZWaveDeviceService zwaveDeviceService = new ZWaveDeviceService();
		if(zwavedeviceid != null && zwavedeviceid > 0){
			zwaveDevice = zwaveDeviceService.query(zwavedeviceid);
			if(zwaveDevice == null){
				if ( log.isInfoEnabled())
					log.info(String.format("zwaveDevice id: %d not exsits" , zwavedeviceid));
				return;
			}
		}
		
		GatewayCapabilityType gct = GatewayCapabilityType.valueof(capabilitycode);
		if ( gct == null )
			return ;
		
		boolean isNull = true;
		if(zwaveDevice.getDevicetype().equals(String.valueOf(gct.getOutdevicetype()))){
			int outid = -1;
			List<ZWaveDevice> list = zwaveDeviceService.queryByDevicetype(zwaveDevice.getDeviceid() , String.valueOf(gct.getInnerdevicetype()));
			int nuid = zwaveDevice.getNuid();
			outid = (nuid & 0xff00) / 256;
			for(ZWaveDevice zd : list){
				int id = (zd.getNuid() & 0xff00) / 256 ;
				if(id == outid){
					isNull = false;
					break;
				}
			}
			if(isNull){
				zwaveDeviceService.delete(zwaveDevice);
			}else{
				message = "airin is not null";
			}
		}else{
			DeviceHelper.clearZwaveDevice(zwaveDevice);
			zwaveDeviceService.delete(zwaveDevice);
		}
	}

	public boolean inisnull(){

		return false;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public String getMessage() {
		return message;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setCapabilitycode(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}	
	
}
