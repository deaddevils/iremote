package com.iremote.device;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.request.DeviceCommandRequest;
import com.iremote.asiainfo.vo.CommandInfo;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@Deprecated
public class DeviceCommand {

	private static Log log = LogFactory.getLog(DeviceCommand.class);
	
	private String deviceid;
	@SuppressWarnings("unused")
	private String password;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String infraredcode;
	private String zwavecommand;
	private int timeoutsecond = 6 ;
	private String usertoken ;
	private int nuid ;
	private byte[] cmd;
	
	public String execute()
	{
		if ( deviceid == null || deviceid.length() == 0 )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXIST;
			return Action.SUCCESS;
		}
		
		PhoneUser user = (PhoneUser) ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
		
		List<String> dlst = PhoneUserHelper.queryDeviceidbySharetoPhoneuserid(user.getPhoneuserid());
		
		IremotepasswordService svr = new IremotepasswordService();
		
		if ( dlst == null || !dlst.contains(deviceid))
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}

		CommandTlv ct = createCommandTlv();

		if ( ct == null )
		{
			resultCode = ErrorCodeDefine.UNKNOW_ERROR;
			return Action.SUCCESS;
		}
		Remote u = svr.getIremotepassword(deviceid);
		
	   	byte[] rp = null ;
	   	
	   	if ( u.getPlatform() == IRemoteConstantDefine.PLATFORM_ASININFO )
	   	{
	   		PhoneUserService pus = new PhoneUserService();
	   		PhoneUser pu = pus.query(user.getPhoneuserid());
	   		
	   		ZWaveDeviceService zds = new ZWaveDeviceService();
	   		ZWaveDevice device = zds.querybydeviceid(deviceid, nuid);

	   		if ( pu != null )
	   		{
		   		if ( usertoken != null && usertoken.length() > 0 )
		   			pu.setToken(usertoken);
		   		String did = deviceid ; 
		   		if ( device != null )
		   			did = Utils.createZwaveDeviceid(deviceid, device.getZwavedeviceid(),device.getNuid());
		   		CommandInfo ci = new CommandInfo(did , PhoneUserHelper.getUserType(user) ,PhoneUserHelper.getUserId(pu));
		   		if ( device != null )
		   			ci.setControlList(AsiainfoHttpHelper.parseCommand(cmd));
		   		DeviceCommandRequest dr = new DeviceCommandRequest( ci, ct);
		   		dr.setDeviceid(deviceid);
		   		dr.setTimeoutsecond(timeoutsecond);
		   		dr.process();
		   		rp = dr.getResult();
	   		}
	   	}
	   	else //if ( u.getPlatform() == IRemoteConstantDefine.PLATFORM_ZHJW )
	   		rp = SynchronizeRequestHelper.synchronizeRequest(deviceid, ct , timeoutsecond);
	   		
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
	   	log.info(resultCode);
	   	return Action.SUCCESS;
	}

	private CommandTlv createCommandTlv()
	{
		if ( infraredcode != null && infraredcode.length() > 0 )
		{
			JSONArray ary = JSONArray.fromObject(infraredcode) ;
			int ci[] = (int[])JSONArray.toArray(ary, int.class);
			byte b[] = new byte[ci.length];
			for ( int i = 0 ; i < ci.length ; i ++ )
				b[i] = (byte)ci[i];
			
			CommandTlv ct = new CommandTlv(4 , 1);
			ct.addUnit(new TlvByteUnit(40 , b));
			return ct ;
		}
		else if ( zwavecommand != null && zwavecommand.length() > 0 )
		{
			JSONArray ary = JSONArray.fromObject(zwavecommand) ;
			int ci[] = (int[])JSONArray.toArray(ary, int.class);
			byte b[] = new byte[ci.length];
			for ( int i = 0 ; i < ci.length ; i ++ )
				b[i] = (byte)ci[i];
			
			CommandTlv ct = new CommandTlv(30 , 7);
			addUnit(ct , b , 70 );
			addUnit(ct , b , 71 );
			addUnit(ct , b , 72 );
			addUnit(ct , b , 74 );
			addUnit(ct , b , 12 );
			
			cmd = TlvWrap.readTag(b, 70, 0);
			if (cmd != null && cmd.length > 1 &&  ( cmd[0] & 0xff ) == 0x62 )
				timeoutsecond = 15 ;
			
			nuid = TlvWrap.readInt(b, 71, 0);
			
			return ct ;
		}
		return null ;
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
	
	public static void main(String arg[])
	{
		String str = "[55,39,31,87,0,0,0,0,0,0,0,8,36,118,91,197,59,122,19,22,80,28,201]";
		JSONArray ary = JSONArray.fromObject(str) ;
		int ci[] = (int[])JSONArray.toArray(ary, int.class);
		byte b[] = new byte[ci.length];
		for ( int i = 0 ; i < ci.length ; i ++ )
			b[i] = (byte)ci[i];
		
		for ( int i = 0 ; i < b.length ; i ++ )
		{
			System.out.print(b[i] & 0xff);
			System.out.print(" ");
		}
	}

	public void setZwavecommand(String zwavecommand) {
		this.zwavecommand = zwavecommand;
	}
	
	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}


}
