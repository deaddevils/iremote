package com.iremote.asiainfo.processor;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.task.devicemanage.BindingInfraredDeviceTaskProcessor;
import com.iremote.asiainfo.task.devicemanage.BindingZWaveDeviceTaskProcessor;
import com.iremote.common.taskmanager.TaskManager;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SetRemoteOwerProcessor;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.ZWaveDeviceService;

public class AsiainfoSetRemoteOwerProcessor extends SetRemoteOwerProcessor {

	@Override
	protected boolean checkprivilege()
	{
		if ( remote == null )
			return true;
		if ( remote.getPhoneuserid() == null )
			return true;
		if ( remote.getPhoneuserid() == phoneuser.getPhoneuserid())
			return true ;
		return false ;
	}
	
	@Override
	protected boolean bindDevice()
	{
		//User not changed , need not bind device again.
		if ( remote != null && remote.getPhoneuserid() != null && remote.getPhoneuserid() == phoneuser.getPhoneuserid() )
			return true;
		
		String str = AsiainfoHttpHelper.deviceBinding(phoneuser, remote);
		if ( str == null || str.length() == 0 )
			return false ;

		JSONObject json = JSON.parseObject(str);
		
		if ( json.getIntValue("resultCode") != 0 )
			return false ;
		
		bindsubdevice();
		
		return true;
	}
	
	private void bindsubdevice()
	{
		List<PhoneUser> su = PhoneUserHelper.queryAuthorizedUser(phoneuser.getPhoneuserid());

		ZWaveDeviceService svr = new ZWaveDeviceService();
		List<ZWaveDevice> lst = svr.querybydeviceid(remote.getDeviceid());
		
		for ( ZWaveDevice d : lst )
			TaskManager.addTask(new BindingZWaveDeviceTaskProcessor(phoneuser , d , su));
		
		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> idl = ids.querybydeviceid(remote.getDeviceid());
		
		for ( InfraredDevice id : idl )
			TaskManager.addTask(new BindingInfraredDeviceTaskProcessor(phoneuser , id , su));	

	}
}
