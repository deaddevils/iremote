package com.iremote.thirdpart.tecus.event;

import java.util.ArrayList;
import java.util.List;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceService;

public class TriggerAlarmProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {
	
	@Override
	public void run() 
	{
		if ( ServerRuntime.getInstance().getSystemcode() == IRemoteConstantDefine.PLATFORM_AMETA)
			return;

		IremotepasswordService rs = new IremotepasswordService();
		Remote r = rs.getIremotepassword(getDeviceid());
		
		if ( r == null || r.getPhoneuserid() == null )
			return ;
		
		PhoneUserService pus = new PhoneUserService();
		PhoneUser phoneuser = pus.query(r.getPhoneuserid()) ;
		
		if ( phoneuser == null )
			return ;
		
		List<Integer> pidl;
		if ( phoneuser.getFamilyid() != null && phoneuser.getFamilyid() != 0 )
			pidl = PhoneUserHelper.queryPhoneuseridbyfamilyid(phoneuser.getFamilyid());
		else 
		{
			pidl = new ArrayList<Integer>(1);
			pidl.add(phoneuser.getPhoneuserid());
		}

		List<String> didl = rs.queryDeviceidbyPhoneUserid(pidl);
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceid(didl);
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice d : lst )
		{
			if ( !IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(d.getDevicetype()))
				continue;
			CommandTlv ct = CommandUtil.createAlarmCommand(d.getNuid());
			SynchronizeRequestHelper.asynchronizeRequest(d.getDeviceid(), ct , 0);

		}
	}

	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}

}
