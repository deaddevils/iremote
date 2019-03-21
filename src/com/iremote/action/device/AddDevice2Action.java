package com.iremote.action.device;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.*;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntArray;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Add air condition from web GUI.
@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class AddDevice2Action
{
	private int nuid ;
	private ZWaveDevice zwaveDevice ;
	private String name;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser ;
	private int zwavedeviceid ;
	private String devicetype;
	private String deviceid;
	private String message;

	public String execute()
	{
		initNuid();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwaveDevice = zds.querybydeviceid(deviceid, nuid);

		if ( zwaveDevice != null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_HAS_EXIST;
			return Action.SUCCESS;
		}

		List<ZWaveDevice> zdl = zds.querybydeviceidtype(deviceid, devicetype);
		int[] nuids = new int[]{nuid};
		if ( zdl != null && zdl.size() > 0 )
		{
			nuids = new int[zdl.size() + 1] ;
			for ( int i = 0 ; i < zdl.size() ; i ++ )
				nuids[i] = zdl.get(i).getNuid();
			nuids[nuids.length -1] = nuid;
		}

		CommandTlv ct = new CommandTlv(105 ,1 );

		ct.addUnit(new TlvIntArray(83 , nuids , 4));

		byte[] rp = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct , 8);

		if ( rp == null ){
			if(ConnectionManager.isOnline(deviceid) == false){

				this.resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			}else{
				this.resultCode = ErrorCodeDefine.TIME_OUT;	
			}
			message = "synchronize fail";
		}
		else{
			message = "synchronize success";
			savedevice();
			phoneuser.setLastupdatetime(new Date());
			PhoneUserHelper.sendInfoChangeMessage(phoneuser);
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zwaveDevice.getDeviceid() , new Date() , 0) );
		}

		return Action.SUCCESS;
	}

	private void savedevice()
	{
		zwaveDevice = new ZWaveDevice();
		initdefault();
		zwaveDevice.setNuid(nuid);
		zwaveDevice.setName(name);
		zwaveDevice.setApplianceid(Utils.createtoken());
		zwaveDevice.setBattery(100);
		zwaveDevice.setDeviceid(deviceid);
		zwaveDevice.setDevicetype(devicetype);
		zwaveDevice.setStatus(0);
		zwaveDevice.setzWaveSubDevices(new ArrayList<ZWaveSubDevice>());

		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwavedeviceid = zds.save(zwaveDevice);
	}

	private void initNuid()
	{
		if(IRemoteConstantDefine.DEVICE_TYPE_BGM.equals(devicetype)){
			nuid = 2097152 | this.nuid;
		}
	}
	
	private void initdefault(){
		if(IRemoteConstantDefine.DEVICE_TYPE_BGM.equals(devicetype)){
			//nuid = 2097152 | 0x00 * 256 | this.nuid;
			zwaveDevice.setStatuses("[1,3,1,32,2,0,0,0,0,0,'null']");
		}
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public int getZwavedeviceid() {
		return zwavedeviceid;
	}

	public String getMessage() {
		return message;
	}
}
