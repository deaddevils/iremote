package com.iremote.action.device;

import com.iremote.action.device.doorlock.DoorlockCommandCache;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public abstract class DeviceOperationBaseAction 
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	protected String deviceid;
	protected int nuid ;
	protected int zwavedeviceid;
	protected ZWaveDevice device;
	protected String operator;
	protected PhoneUser phoneuser ;
	protected boolean hasResult = false;
	
	
	public String execute() 
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		if ( device == null )
		{
			if ( zwavedeviceid != 0 )
				device = zds.query(zwavedeviceid);
			else 
				device = zds.querybydeviceid(deviceid, nuid);
		}
		
		if ( device == null )
		{
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}

		CommandTlv[] cta = createCommandTlv() ;
		
		if ( cta == null ) {
            if (hasResult)
                return Action.SUCCESS;
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}

		Integer tokenid = PhoneUserHelper.getTokenid();
		for ( int i = 0 ; i < cta.length ; i ++ )
		{
			if ( operator != null && operator.length() > 0 )
				cta[i].addUnit(new TlvByteUnit(12 , operator.getBytes()));
			else if ( phoneuser != null )
				cta[i].addUnit(new TlvByteUnit(12 , phoneuser.getPhonenumber().getBytes()));
			if ( tokenid != null )
				cta[i].addUnit(new TlvIntUnit(TagDefine.TAG_APP_USER_TOKEN_ID , tokenid , 4));
			cta[i].addUnit(new TlvIntUnit(79 ,IRemoteConstantDefine.OPERATOR_TYPE_APP_USER , 1));
		}

		
		if ( !ConnectionManager.contants(device.getDeviceid()))
		{
			if ( !DeviceHelper.isZwavedevice(device.getNuid()))
			{
				cacheCommand(device.getDeviceid(),cta);
				resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
	   			return Action.SUCCESS;
			}
			else 
			{
				resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
	   			return Action.SUCCESS;
			}
		}
		
		byte[][] rpa = new byte[cta.length][];
		for ( int i = 0 ; i < cta.length ; i ++ )
		{
			rpa[i] = SynchronizeRequestHelper.synchronizeRequest(device.getDeviceid(), cta[i] , IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
		}
		
		for ( byte[] rp : rpa )
		{
			if ( rp == null )
		   	{
		   		resultCode = ErrorCodeDefine.TIME_OUT;
		   	}
		   	else
		   	{
		   		resultCode = TlvWrap.readInt(rp , 1 , TlvWrap.TAGLENGTH_LENGTH);
		   		if ( resultCode == Integer.MIN_VALUE )
		   			resultCode = ErrorCodeDefine.TIME_OUT;
		   		if (resultCode == ErrorCodeDefine.SYSTEM_BUSY)
		   			resultCode = ErrorCodeDefine.SYSTEM_BUSY;
		   	}
		}

	   	return Action.SUCCESS;
	}
	
	private void cacheCommand(String deviceid , CommandTlv[] ctl)
	{
		for ( CommandTlv ct : ctl)
			GatewayEventConsumerStore.getInstance().put(deviceid,new DoorlockCommandCache(deviceid , ct));
	}
	
	protected abstract CommandTlv[] createCommandTlv();

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid) {
		this.nuid = nuid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setDevice(ZWaveDevice device) {
		this.device = device;
	}
}
