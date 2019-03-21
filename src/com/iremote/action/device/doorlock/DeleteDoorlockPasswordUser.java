package com.iremote.action.device.doorlock;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.DoorlockUserService;

public class DeleteDoorlockPasswordUser implements IDoorlockOperationProcessor
{
	public static String[] STATUS = new String[]{"" , "init", "gatewayoffline","sendingcommand" , "deletefailed" , "deletesuccess"};
	public static int STATUS_INIT = 1 ;
	public static int STATUS_OFFLINE = 2 ;
	public static int STATUS_SENDING_COMMAN = 3 ;
	public static int STATUS_FAILED = 4 ;
	public static int STATUS_SUCCESS = 5 ;

	private String deviceid ;
	private int nuid ;
	private int usertype ;
	private int usercode = 0xFF;
	private int status = STATUS_INIT ;
	private boolean hassendcommand = false ;
	private int doorlockuserid;
	private int zwavedeviceid;
	
	private long expiretime = System.currentTimeMillis() + 30 * 1000;
	private static Byte[] REPORT_DELETE_PASSWORD_COMMAND = new Byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x0A};
	private static Byte[] REPORT_DELETE_FINGERPRINT_COMMAND = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x0A};
	private static Byte[] REPORT_DELETE_CARD_COMMAND = new Byte[]{(byte)0x80,0x09,0x04};
	private Byte[] report ;
	private byte[] command;
	
	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{
		if ( status == STATUS_SUCCESS || status == STATUS_FAILED)
			return ;
		
		int rst = 0 ;
		if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD 
				|| usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_FINGERPRINT)
			rst = report[8] ;
			
		else if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD)
			rst = report[3] ;
		
		if ( rst == 1 )
		{
			DoorlockUserService dus = new DoorlockUserService();
			DoorlockUser du = dus.query(doorlockuserid);
			if ( du != null )
				dus.delete(du);
			
			status = STATUS_SUCCESS;
		}
		else 
			status = STATUS_FAILED;

	}

	@Override
	public long getExpireTime()
	{
		return expiretime;
	}

	@Override
	public void onGatewayOnline()
	{
		this.sendcommand();
	}

	@Override
	public void onGatewayOffline()
	{
		
	}

	@Override
	public int getStatus()
	{
		return status;
	}

	@Override
	public String getMessage()
	{
		return STATUS[status];
	}

	@Override
	public void init()
	{
		regist();
		checkGatewayStatus();
		
		if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD)
		{
			report = REPORT_DELETE_PASSWORD_COMMAND;
			command = DoorlockPasswordHelper.createDeletePasswordCommand((byte)usercode);
		}
		else if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_FINGERPRINT)
		{
			report = REPORT_DELETE_FINGERPRINT_COMMAND;
			command = DoorlockPasswordHelper.createDeleteFingerprintCommand((byte)usercode);
		}
		else if ( usertype == IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD)
		{
			report = REPORT_DELETE_CARD_COMMAND;
			command = DoorlockPasswordHelper.createDeleteCardCommand(usercode);
		}
		else 
			status = STATUS_FAILED;
	}
	
	
	private void regist()
	{
		GatewayEventConsumerStore.getInstance().put(deviceid, this);
		DoorlockOperationStore.getInstance().put(String.valueOf(zwavedeviceid), this);
	}
	
	private void checkGatewayStatus()
	{
		if ( !ConnectionManager.contants(deviceid))
			status = STATUS_OFFLINE;
	}

	@Override
	public void sendcommand()
	{
		if ( hassendcommand == true || command == null )
			return ;
		hassendcommand = true;
		
		ZwaveReportNotifyManager.getInstance().regist(deviceid, nuid, report, this);
		
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(command, nuid);
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 10);

		expiretime = System.currentTimeMillis() + 15 * 1000;
		status = STATUS_SENDING_COMMAN;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}

	public void setUsercode(int usercode)
	{
		this.usercode = usercode;
	}

	public void setDoorlockuserid(int doorlockuserid)
	{
		this.doorlockuserid = doorlockuserid;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}

	@Override
	public boolean isFinished()
	{
		return ( status == STATUS_FAILED || status == STATUS_SUCCESS );
	}

	@Override
	public boolean equals(Object obj)
	{
		if ( obj == null )
			return false ;
		
		if (! ( obj instanceof DeleteDoorlockPasswordUser) )
			return false ;
		
		DeleteDoorlockPasswordUser other = ( DeleteDoorlockPasswordUser)obj;
		
		if ( other.zwavedeviceid == this.zwavedeviceid 
				&& other.usertype == this.usertype 
				&& other.usercode == this.usercode )
			return true ;
		return false ;
	}

}
