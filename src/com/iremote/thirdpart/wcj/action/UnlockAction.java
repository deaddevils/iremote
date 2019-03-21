package com.iremote.thirdpart.wcj.action;

import com.iremote.action.device.doorlock.DoorlockCommandCache;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
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


@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid" , "lockid"})
public class UnlockAction {

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int lockid;
	private int zwavedeviceid;
	private ZWaveDevice lock;
	private ThirdPart thirdpart;
	private int timeoutsecond = 6 ;
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(lockid);
		
		if ( lock == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}

		CommandTlv ct = createCommandTlv();

		if ( !ConnectionManager.contants(lock.getDeviceid())){
			if ( GatewayUtils.isCobbeLock(lock.getDeviceid())){
				cacheCommand(ct, lock.getDeviceid());
				resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
				return Action.SUCCESS;
			}
			else{
				resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
				return Action.SUCCESS;
			}
		}

		byte[] rp = SynchronizeRequestHelper.synchronizeRequest(lock.getDeviceid(), ct , timeoutsecond);
		
		if ( rp == null )
	   	{
	   		resultCode = ErrorCodeDefine.TIME_OUT;
	   	}
	   	else 
	   	{
	   		resultCode = TlvWrap.readInt(rp , 1 , TlvWrap.TAGLENGTH_LENGTH);
	   		if ( resultCode == Integer.MIN_VALUE )
	   			resultCode = ErrorCodeDefine.TIME_OUT;
	   	}

	   	return Action.SUCCESS;
	}

	private void cacheCommand(CommandTlv ct, String deviceid) {
		GatewayEventConsumerStore.getInstance().put(deviceid,new DoorlockCommandCache(deviceid , ct));
	}

	private CommandTlv createCommandTlv()
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , new byte[]{0x62,0x01,0x01}));
		ct.addUnit(new TlvIntUnit(71 , lock.getNuid() , 1));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvByteUnit(74 , new byte[]{0x62,0x03,0x01,0x00,0x02,(byte)0xfe,(byte)0xfe}));
		ct.addUnit(new TlvIntUnit(79 , 5 , 1));
		if ( thirdpart != null )
			ct.addUnit(new TlvByteUnit(12 , thirdpart.getCode().getBytes() ));
		else if ( phoneuser != null )
			ct.addUnit(new TlvByteUnit(12 , phoneuser.getPhonenumber().getBytes() ));
		return ct ;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setLockid(int lockid) {
		this.lockid = lockid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
		this.lockid = this.zwavedeviceid;
	}

	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	
}
