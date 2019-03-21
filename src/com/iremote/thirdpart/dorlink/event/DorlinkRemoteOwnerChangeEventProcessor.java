package com.iremote.thirdpart.dorlink.event;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.rentinghouse.vo.InfoChangeEvent;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

@Deprecated
public class DorlinkRemoteOwnerChangeEventProcessor extends RemoteOwnerChangeEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		Integer tpid = this.queryThirdpartid();
		if ( tpid == null || tpid == 0 )
			return ;
		
		EventtoThirdpart etd = new EventtoThirdpart();
		
		etd.setThirdpartid(tpid);
		etd.setType(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED);
		etd.setDeviceid(getDeviceid());
		etd.setEventtime(getEventtime());
		
		InfoChangeEvent ice = new InfoChangeEvent();
		ice.setDeviceid(getRemote().getDeviceid());
		ice.setName(getRemote().getName());
		if ( ice.getName() == null || ice.getName().length() == 0 )
		{
			if ( getRemote().getDeviceid() != null && getRemote().getDeviceid().length() > 6 )
				ice.setName(getRemote().getDeviceid().substring(getRemote().getDeviceid().length() - 6));
			else 
				ice.setName(getRemote().getDeviceid());
		}
		ice.setLoginname(getNewownerphonenumber());
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		List<ZWaveDevice> lst = zds.querybydeviceid(ice.getDeviceid());
		for ( ZWaveDevice zd : lst )
		{
			ZWaveDevice z = new ZWaveDevice();
			z.setDevicetype(zd.getDevicetype());
			z.setZwavedeviceid(zd.getZwavedeviceid());
			z.setName(zd.getName());
			ice.getZwavedevice().add(z);
		}
		
		etd.setObjparam(JSON.toJSONString(ice));
		
		EventtoThirdpartService svr = new EventtoThirdpartService();
		svr.save(etd);
		
	}

	@Override
	public String getTaskKey()
	{
		return getDeviceid();
	}

	protected Integer queryThirdpartid()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return 0;

		return DorlinkEventHelper.queryThirdpartid();
	}
}
