package com.iremote.asiainfo.action;

import com.iremote.action.device.DeviceCommandAction;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.request.DeviceCommandRequest;
import com.iremote.asiainfo.vo.CommandInfo;
import com.iremote.common.Utils;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;

public class AsiainfoDeviceCommandAction extends DeviceCommandAction {

	@Override
	protected byte[] sendcommand(PhoneUser user , CommandTlv ct)
	{
		byte[] rp = null ;
		
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
	   			did = Utils.createZwaveDeviceid(deviceid, device.getZwavedeviceid() , device.getNuid());
	   		CommandInfo ci = new CommandInfo(did , PhoneUserHelper.getUserType(user) ,user.getToken());
	   		if ( device != null )
	   			ci.setControlList(AsiainfoHttpHelper.parseCommand(device.getDevicetype() , cmd));
	   		DeviceCommandRequest dr = new DeviceCommandRequest( ci, ct);
	   		dr.setDeviceid(deviceid);
	   		dr.setTimeoutsecond(timeoutsecond);
	   		dr.process();
	   		rp = dr.getResult();
   		}

	   	return rp ;
	}
}
