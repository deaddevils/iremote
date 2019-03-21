package com.iremote.action.gateway;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivileges;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

@DataPrivileges( dataprivilege = {
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid"),
		@DataPrivilege(dataprivilgetype = DataPrivilegeType.ATTRIBUTE, domain = "devicemanage", usertype={"thirdpart"}),
})
public class StartAddingZWaveDeviceAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	private String name ;
	private int encrypt;//1 encrypt (default)2 not encrypt 
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( !ConnectionManager.isOnline(deviceid))
		{
			this.resultCode = ErrorCodeDefine.TRANSOFFLINE;
			return Action.SUCCESS;
		}
		
		if (!checkdevicename())
			return Action.SUCCESS;
		
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		
		DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.addzWavedevice);
		
		if ( dos != null && dos.isFinished() == false && dos.getExpiretime().getTime() > System.currentTimeMillis())
		{
			this.resultCode = ErrorCodeDefine.GATEWAY_BUSSING;
			return Action.SUCCESS;
		}
		
		if ( dos == null )
		{
			dos = new DeviceOperationSteps();
			dos.setDeviceid(deviceid);
			dos.setOptype(DeviceOperationType.addzWavedevice.ordinal());
		}
		
		dos.setStarttime(new Date());
		dos.setStatus(GatewayAddZWaveDeviceSteps.normal.ordinal());
		dos.setAppendmessage(name);
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, 10);
		dos.setExpiretime(c.getTime());
		
		doss.saveOrUpdate(dos);
		
		CommandTlv ct = new CommandTlv(TagDefine.COMMAND_CLASS_DEVICE_MANAGER , TagDefine.COMMAND_SUB_CLASS_START_ZWAVEDEVICE_ADDING_PROGRESS_REQUEST);
		if(encrypt==2){
			ct.addUnit(new TlvIntUnit(75 , encrypt , 1));
		}
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 1);
		
		return Action.SUCCESS;
	}
	
	private boolean checkdevicename()
	{
		if ( StringUtils.isBlank(name) )
			return true ;
		RemoteService rs = new RemoteService();
		List<String> lst = rs.queryDeviceidbyPhoneUserid(phoneuser.getPhoneuserid());
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> dlst = zds.querybydeviceidandName(lst , name );
		if ( dlst != null && dlst.size() > 0 )
		{
			resultCode = ErrorCodeDefine.NAME_IS_EXIST;
			return false;
		}
		return true;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneuser(PhoneUser phoneuser){
		this.phoneuser = phoneuser;
	}

	public void setEncrypt(int encrypt) {
		this.encrypt = encrypt;
	}
	
}
