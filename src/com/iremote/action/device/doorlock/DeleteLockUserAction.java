package com.iremote.action.device.doorlock;

import com.iremote.domain.DeviceCapability;
import com.iremote.service.DeviceCapabilityService;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DoorlockAssociationService;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;
import com.opensymphony.xwork2.Action;

public class DeleteLockUserAction
{
	
	private static Log log = LogFactory.getLog(DeleteLockUserAction.class);
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int doorlockuserid;
	private int zwavedeviceid ;
	private String operation = "delete";
	private PhoneUser phoneuser ;
	private ZWaveDevice zwavedevice;
	private int usercode;
	
	public String execute()
	{
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(doorlockuserid);
		
		if ( du == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		usercode = du.getUsercode();
		DoorlockAssociationService das = new DoorlockAssociationService();
		das.deletebyzwavedeviceidandusercode(du.getZwavedeviceid(), usercode);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwavedevice = zds.query(du.getZwavedeviceid());
		
		if ( zwavedevice == null )
		{
			dus.delete(du);
			return Action.SUCCESS;
		}
		
		zwavedeviceid = zwavedevice.getZwavedeviceid();
		if ( PhoneUserHelper.checkPrivilege(phoneuser, zwavedevice) == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.ERROR;
		}
		
		if ( DeviceHelper.isZwavedevice(zwavedevice)
				&& !GatewayHelper.isOnline(zwavedevice.getDeviceid()))
			return "gatewayoffline";

		IDoorlockOperationProcessor ddpu;
		if (isStandardZwaveDevice()) {
			ddpu = createDeleteStandardZwaveDoorlockPasswordUser(zwavedevice, du);
		} else {
			ddpu = createDeleteDoorlockPasswordUser(zwavedevice, du);
		}
		
		IDoorlockOperationProcessor setter = (IDoorlockOperationProcessor)DoorlockOperationStore.getInstance().get(String.valueOf(zwavedeviceid));
		
		if ( setter != null && !setter.isFinished() )
		{
			if ( ddpu.equals(setter))
				return setter.getMessage();
			else 
			{
				if ( log.isInfoEnabled())
					log.info(JSON.toJSONString(setter));
				resultCode = ErrorCodeDefine.NO_PRIVILEGE_3;
				return "devicebussing";
			}
		}
		
				
		ddpu.init();
		
		if ( ddpu.getStatus() != DeleteDoorlockPasswordUser.STATUS_OFFLINE)
			ddpu.sendcommand();
		
		DoorlockPasswordService dps = new DoorlockPasswordService();
		int ut = transfertype(du.getUsertype());
		DoorlockPassword dp = dps.queryLatestActivePassword2(zwavedeviceid,ut,du.getUsercode());
		if(dp!=null){
			dp.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
			dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
		}
		sendToThirdPart();
		return ddpu.getMessage();
		
	}
	private void sendToThirdPart() {
		JMSUtil.sendmessage(IRemoteConstantDefine.DELETE_LOCK_USER_RESULT, createZwaveDeviceEvent());
	}

	private ZWaveDeviceEvent createZwaveDeviceEvent() {
		ZWaveDeviceEvent zde = new ZWaveDeviceEvent();
		zde.setZwavedeviceid(zwavedeviceid);
		zde.setDeviceid(zwavedevice.getDeviceid());
		zde.setEventtime(new Date());
		zde.setEventtype(IRemoteConstantDefine.DELETE_LOCK_USER_RESULT);
		zde.setAppendmessage(createAppendMessage());

		return zde;
	}

	private JSONObject createAppendMessage() {
		JSONObject json = new JSONObject();
		json.put("resultCode", 1);
		json.put("userCode", usercode);
		return json;
	}
	private int transfertype(int usertype) {
		switch(usertype){
		case 21:
			return 1;
		case 32:
			return 2;
		case 22:
			return 3;
		default :
			return 2;
		}
	}

	private boolean isStandardZwaveDevice() {
		DeviceCapabilityService dcs = new DeviceCapabilityService();
		DeviceCapability dc = dcs.query(zwavedeviceid, 13);
		return dc != null;
	}
	
	private DeleteDoorlockPasswordUser createDeleteDoorlockPasswordUser(ZWaveDevice zd , DoorlockUser du )
	{
		DeleteDoorlockPasswordUser ddpu = new DeleteDoorlockPasswordUser();
		ddpu.setDeviceid(zd.getDeviceid());
		ddpu.setNuid(zd.getNuid());
		ddpu.setZwavedeviceid(zd.getZwavedeviceid());
		ddpu.setDoorlockuserid(du.getDoorlockuserid());
		ddpu.setUsercode(du.getUsercode());
		ddpu.setUsertype(du.getUsertype());
		return ddpu ;
	}

	private DeleteStandardZwaveDeviceUserPassword createDeleteStandardZwaveDoorlockPasswordUser(ZWaveDevice zd , DoorlockUser du )
	{
		DeleteStandardZwaveDeviceUserPassword ddpu = new DeleteStandardZwaveDeviceUserPassword();
		ddpu.setzWaveDevice(zd);
		ddpu.setZwavedeviceid(zd.getZwavedeviceid());
		ddpu.setUsercode(du.getUsercode());
		ddpu.setPhoneUser(phoneuser);
		ddpu.setDoorlockuserid(doorlockuserid);
		return ddpu ;
	}
	public int getResultCode()
	{
		return resultCode;
	}

	public void setDoorlockuserid(int doorlockuserid)
	{
		this.doorlockuserid = doorlockuserid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	
	
	public boolean isZwavedevice()
	{
		return DeviceHelper.isZwavedevice(zwavedevice);
	}
}
