package com.iremote.action.device;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.device.doorlock.DoorlockCommandCache;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class DeviceCommandAction {

	private static Log log = LogFactory.getLog(DeviceCommandAction.class);
	
	protected String deviceid;
	@SuppressWarnings("unused")
	private String password;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	protected String infraredcode;
	protected String zwavecommand;
	protected int timeoutsecond = 6 ;
	protected String usertoken ;
	protected int nuid ;
	protected int zwavedeviceid ;
	protected ZWaveDevice device;
	protected byte[] cmd;
	private PhoneUser phoneuser ;
	private byte[] zwcmd ;
	private byte[] ircmd ;
	
	public String execute()
	{
		if ( StringUtils.isBlank(deviceid) )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXIST;
			return Action.SUCCESS;
		}
		
		initCommand();
		
		if ( StringUtils.isNotBlank(this.zwavecommand))
		{
			this.initZwaveDevice();
			if ( this.device == null )
			{
				this.resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
				return Action.SUCCESS;
			}
			if ( PhoneUserHelper.checkPrivilege(phoneuser, device) == false )
			{
	   			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
	   			return Action.SUCCESS;
			}
		}

		CommandTlv ct = createCommandTlv();

		if ( ct == null )
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}

		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( !ConnectionManager.contants(deviceid))
		{
			if ( GatewayUtils.isCobbeLock(r))
			{
				cacheCommand(ct);
				resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
	   			return Action.SUCCESS;
			}
			else 
			{
				resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
	   			return Action.SUCCESS;
			}
		}
		
		byte[] rp = sendcommand(phoneuser ,ct);
	   		
	   	if ( rp == null )
	   	{
	   		if ( GatewayUtils.isCobbeLock(r) )
			{
				cacheCommand(ct);
				resultCode = ErrorCodeDefine.WAKEUP_DEVICE;
	   			return Action.SUCCESS;
			}
			else 
				resultCode = ErrorCodeDefine.TIME_OUT;
	   	}
	   	else 
	   	{
	   		resultCode = TlvWrap.readInt(rp , 1 , TlvWrap.TAGLENGTH_LENGTH);
	   		if ( resultCode == Integer.MIN_VALUE )
	   			resultCode = ErrorCodeDefine.TIME_OUT;
	   	}
	   	log.info(resultCode);
	   	return Action.SUCCESS;
	}
	
	private void initCommand()
	{
		if ( StringUtils.isNotBlank(this.zwavecommand))
		{
			int[] ca = Utils.jsontoIntArray(zwavecommand);
			if ( ca != null )
			{
				this.zwcmd = new byte[ca.length];
				copyArray(ca , zwcmd);
			}
		}
		else if ( StringUtils.isNotBlank(this.infraredcode))
		{
			int[] ca = Utils.jsontoIntArray(infraredcode);
			if ( ca != null )
			{
				this.ircmd = new byte[ca.length];
				copyArray(ca , ircmd);
			}
		}
	}
	
	private void copyArray(int[] src , byte[] dest)
	{
		for ( int i = 0 ; i < src.length ; i ++ )
			dest[i] = (byte) src[i] ;
	}
	
	private void initZwaveDevice()
	{
		if ( this.zwcmd == null || this.zwcmd.length == 0 )
			return ;
		nuid = TlvWrap.readInt(this.zwcmd, TagDefine.TAG_NUID, 0);
		
		if ( nuid == Integer.MIN_VALUE)
		{
			nuid = 0 ;
			return ;
		}
		
		if ( zwavedeviceid != 0 )
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			device = zds.query(zwavedeviceid);
			if ( device == null  )
				return ;
			if ( device.getNuid() != nuid )
			{
				device = null ;
				return ;
			}
			deviceid = device.getDeviceid();
		}
		else 
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			device = zds.querybydeviceid(deviceid, nuid);
		}
	}
	
	private void cacheCommand(CommandTlv ct)
	{
		GatewayEventConsumerStore.getInstance().put(deviceid,new DoorlockCommandCache(deviceid , ct));
	}
	
	protected byte[] sendcommand(PhoneUser user , CommandTlv ct)
	{		
		return SynchronizeRequestHelper.synchronizeRequest(deviceid, ct , timeoutsecond);
	}

	private CommandTlv createCommandTlv()
	{
		if ( this.ircmd != null && this.ircmd.length > 0 )
		{
			CommandTlv ct = new CommandTlv(4 , 1);
			ct.addUnit(new TlvByteUnit(40 , this.ircmd));
			return ct ;
		}
		else if ( this.zwcmd != null && this.zwcmd.length > 0 )
		{			
			CommandTlv ct = new CommandTlv(30 , 7);
			addUnit(ct , this.zwcmd , 70 );
			addUnit(ct , this.zwcmd , 71 );
			addUnit(ct , this.zwcmd , 72 );
			if ( !isLockCloseCommand(this.zwcmd))
				addUnit(ct , this.zwcmd , 74 );
			addUnit(ct , this.zwcmd , 12 );
			addUnit(ct , this.zwcmd , 79 );
			addUnit(ct , this.zwcmd , 75  );
			
			cmd = TlvWrap.readTag(this.zwcmd, 70, 0);
			if (cmd != null && cmd.length > 1 &&  ( cmd[0] & 0xff ) == 0x62 )
				timeoutsecond = 15 ;
						
			return ct ;
		}
		return null ;
	}
	
	private boolean isLockCloseCommand(byte[] command)
	{
		if ( command == null )
			return false ;
		byte[] tb = TlvWrap.readTag(command, 70, 0);
		if ( tb == null || tb.length < 3 )
			return false ;
		return ( tb[0] == 98 && tb[1] == 1 && tb[2] == (byte)255 ); 
	}
	
	private void addUnit(CommandTlv ct , byte[] b , int tag)
	{
		byte[] tb = TlvWrap.readTag(b, tag, 0);
		if ( tb != null )
			ct.addUnit(new TlvByteUnit(tag , tb ));
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setInfraredcode(String infraredcode) {
		this.infraredcode = infraredcode;
	}

	public void setZwavecommand(String zwavecommand) {
		this.zwavecommand = zwavecommand;
	}
	
	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}


}
