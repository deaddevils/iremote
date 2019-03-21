package com.iremote.thirdpart.wcj.action;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class SetPasswordThread implements Runnable  {

	private static Log log = LogFactory.getLog(SetPasswordThread.class);
	private int zwavedeviceid;
	private byte usercode;
	private byte usertype = 1;  //1 password , 2 card , 3 fingerprint
	private DoorlockPassword dp ;

	private final int timeoutsecond = 10 ;
	protected String key ;
	private ZWaveDevice lock;
	private int resultCode = 0 ;
	
	public SetPasswordThread(int zwavedeviceid, byte usercode) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.usercode = usercode;
	}
	
	public SetPasswordThread(int zwavedeviceid, byte usercode , byte usertype) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.usercode = usercode;
		this.usertype = usertype;
	}

	public SetPasswordThread(DoorlockPassword dp)
	{
		super();
		this.dp = dp;
		this.zwavedeviceid = dp.getZwavedeviceid();
		this.usercode = (byte)dp.getUsercode();
		this.usertype = (byte)dp.getUsertype();
	}

	@Override
	public void run() 
	{
		Utils.sleep(1000);
		
		sendpassword();
	}
	
	public void sendpassword()
	{
		DoorlockPasswordService dps = new DoorlockPasswordService();
		if ( dp == null )
			dp = dps.queryLatestActivePassword(zwavedeviceid,usertype, usercode & 0xff);
		
		if ( dp == null 
				|| ( dp.getSynstatus() !=  IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING
					&& dp.getSynstatus() != IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET ) )
			return ;
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		
		if ( lock == null )
		{
			dp.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ECLIPSED);
			return ;
		}

		if ( sendcommand() == true )
		{
			dp.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT);
			dp.setSendtime(new Date());
		}
		else 
		{
			dp.setErrorcount(dp.getErrorcount()+1);
			if ( this.resultCode == 0 )
				this.resultCode = ErrorCodeDefine.TIME_OUT;
		}
		
		if ( dp.getDoorlockpasswordid() != 0 )
			dps.update(dp);
	}

	protected boolean sendcommand()
	{
		if ( dp.getSynstatus() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING)
		{
			if ( !sendpassowrd()  )
				return false;

			// password set success .
			dp.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
		}
		
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(this.lock.getDeviceid());
		
//		if ( dp.getPasswordtype() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP 
//				|| GatewayUtils.isLockGateway(r))
//			return true;
		
		if ( !sendTimeConfigure()  )
			return false;

		if ( !GatewayUtils.isAccessControl(r))
			if ( !sendCurrentTime() )
				return false;
		
		return true;
	}
	
	public boolean sendCurrentTime()
	{
		Date dc = new Date();
		String tzid = GatewayHelper.getRemoteTimezoneId(lock.getDeviceid());
		if ( StringUtils.isNotBlank(tzid))
			dc = TimeZoneHelper.timezoneTranslate(dc, TimeZone.getDefault(), TimeZone.getTimeZone(tzid));
		
		DoorlockHelper.sendCurrentTime(lock.getDeviceid(), lock.getNuid() , dc);
		
		return true;
	}
	
	protected boolean sendTimeConfigure()
	{
		Calendar cs = Calendar.getInstance();
		cs.setTime(dp.getValidfrom());
		
		Calendar ce = Calendar.getInstance();
		ce.setTime(dp.getValidthrough());
		
		byte[] b = new byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x05,0x01,usertype,usercode,0x01,
				(byte)(cs.get(Calendar.YEAR) - 2000 ),
				(byte)(cs.get(Calendar.MONTH) + 1 ),
				(byte)cs.get(Calendar.DAY_OF_MONTH),
				(byte)cs.get(Calendar.HOUR_OF_DAY),
				(byte)cs.get(Calendar.MINUTE),
				(byte)(ce.get(Calendar.YEAR) - 2000 ),
				(byte)(ce.get(Calendar.MONTH) + 1 ),
				(byte)ce.get(Calendar.DAY_OF_MONTH),
				(byte)ce.get(Calendar.HOUR_OF_DAY),
				(byte)ce.get(Calendar.MINUTE),
				0x00 };
		
		key = IRemoteConstantDefine.SYNC_KEY_SETTIMESPAN;
		
		Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x06,usertype, usercode,0x01} ;

		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), createCommandTlv(b), responsekey, timeoutsecond, 0);
		boolean brst = checkResult(wrap , 10);
		
		if ( !brst || this.dp.getWeekday() == null )
			return brst ;
		
		return ( ErrorCodeDefine.SUCCESS == DoorlockHelper.sendWeekTimeConfig(lock.getDeviceid(), lock.getNuid(), usertype, usercode, dp.getWeekday(), dp.getStarttime(), dp.getEndtime()));
	}
	
	public boolean sendpasswordonly(){
		return sendpassowrd();
	}
	
	private boolean sendpassowrd()
	{
		StringBuffer sb = new StringBuffer(AES.decrypt2Str(dp.getPassword()));
		for ( ; sb.length() <= 12 ; )
			sb.append("F");
				
		if ( isAdminUser() )
		{
			byte[] b = new byte[]{(byte)0x80,0x07,0x00,(byte)0xD0,0x10,0x01,0x01,usercode,0x61,0x64,0x6d,0x69,0x6e,0x69,0x73,0x74,
					Integer.valueOf(sb.substring(0, 2), 16).byteValue(),
					Integer.valueOf(sb.substring(2, 4), 16).byteValue(),
					Integer.valueOf(sb.substring(4, 6), 16).byteValue(),
					Integer.valueOf(sb.substring(6, 8), 16).byteValue(),
					Integer.valueOf(sb.substring(8, 10), 16).byteValue(),
					Integer.valueOf(sb.substring(10, 12), 16).byteValue()};
			setuserid(b);
			
			Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xD0,0x10,0x01,0x02} ;
			
			ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), createCommandTlv(b), responsekey, timeoutsecond, 0);
			return checkResult(wrap , 7);
		}
		else
		{
			byte[] b = new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x01,usercode,0x35,0x35,0x35,0x35,0x36,0x36,0x36,0x36,
					Integer.valueOf(sb.substring(0, 2), 16).byteValue(),
					Integer.valueOf(sb.substring(2, 4), 16).byteValue(),
					Integer.valueOf(sb.substring(4, 6), 16).byteValue(),
					Integer.valueOf(sb.substring(6, 8), 16).byteValue(),
					Integer.valueOf(sb.substring(8, 10), 16).byteValue(),
					Integer.valueOf(sb.substring(10, 12), 16).byteValue()};
			setuserid(b);
			
			Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x03 ,null} ;
			if ( usercode != 0 && ( usercode & 0xff) != 0xff )
				responsekey[7] = usercode ;
			
			ZwaveReportRequestWrap wrap= ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), createCommandTlv(b), responsekey, timeoutsecond, 0);
			
			if ( checkResult(wrap , 8) == true ) 
			{
				if ( usercode == 0 || ( usercode & 0xff) == 0xff )
				{
					usercode = wrap.getResponse()[7];
					dp.setUsercode(usercode);
				}
				return true;
			}
			else 
				return false ;
		}
	}
	
	private boolean isAdminUser()
	{
		return ( (usercode & 0xff) == 0xF0) ;
	}
	
	protected String getDoorlockUserid()
	{
		int uc = usercode & 0xff ;
		if ( uc == 0xF2 )
			return IRemoteConstantDefine.DOOR_LOCK_TEMP_USER;
		else if ( uc == 0xF0 )
			return IRemoteConstantDefine.DOOR_LOCK_ADMIN_USER ;
		else 
			return String.format("%s%03d" , IRemoteConstantDefine.DOOR_LOCK_USER , usercode);
	}
	
	private void setuserid(byte[] b)
	{
		String str = getDoorlockUserid();
		byte[] s = null ; 
		if ( str.length() > 8 )
			s = str.substring(0, 8).getBytes();
		else 
			s = str.getBytes();
		
		System.arraycopy(s, 0, b, 8, s.length);
	}
	
	private boolean checkResult(ZwaveReportRequestWrap wrap , int resultindex)
	{
		byte[] rst = wrap.getResponse();
		if ( rst == null )
		{
			log.info("result is null");
			this.resultCode = wrap.getAckresult();
			return false ;
		}
		if ( log.isInfoEnabled())
			Utils.print("Result is ", rst);
		if ( rst[resultindex] == 0x01 )
			return true;
		log.info("set command response: "+rst[resultindex]);
		return false ;
	}
	
	protected CommandTlv createCommandTlv(byte[] command)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		if ( lock.getNuid() <= 256 )
			ct.addUnit(new TlvIntUnit(71 , lock.getNuid() , 1));
		else 
			ct.addUnit(new TlvIntUnit(71 , lock.getNuid() , 4));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		return ct ;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	

}
