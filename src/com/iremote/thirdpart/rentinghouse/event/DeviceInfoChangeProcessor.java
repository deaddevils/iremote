package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.DeviceInfoChange;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.ZWaveSubDevice;
import com.iremote.service.ZWaveSubDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.rentinghouse.vo.DeviceInfoChangeEvent;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class DeviceInfoChangeProcessor extends DeviceInfoChange implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DeviceInfoChangeProcessor.class);

	@Override
	public void run() 
	{
		List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(super.getDeviceid());
		
		for ( Integer tpid : tidlst )
		{
			if ( tpid == null || tpid == 0 )
				continue ;
			
			EventtoThirdpart etd = new EventtoThirdpart();
			
			etd.setThirdpartid(tpid);
			etd.setType(IRemoteConstantDefine.NOTIFICATION_DEVICE_INFO_CHANGED);
			etd.setDeviceid(getDeviceid());
			etd.setZwavedeviceid(getZwavedeviceid());
			etd.setEventtime(getEventtime());
			
			DeviceInfoChangeEvent ice = new DeviceInfoChangeEvent();
			ice.setDeviceid(getDeviceid());
			ice.setZwavedeviceid(getZwavedeviceid());
			ice.setDevicetype(getDevicetype());
			ice.setName(getName());
			
			querySubdevice(ice);
			
			etd.setObjparam(JSON.toJSONString(ice));  
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
	}
	
	private void querySubdevice(DeviceInfoChangeEvent ice)
	{
		ZWaveSubDeviceService zds = new ZWaveSubDeviceService();
		
		List<ZWaveSubDevice> lst = zds.query(ice.getZwavedeviceid());
		for ( ZWaveSubDevice zd : lst )
		{
			ZWaveSubDevice z = new ZWaveSubDevice();
			z.setZwavesubdeviceid(zd.getZwavesubdeviceid());
			z.setChannelid(zd.getChannelid());
			z.setName(zd.getName());
			z.setSubdevicetype(zd.getSubdevicetype());
			ice.getZwavesubdevice().add(z);
		}
	}
	

	
	@Deprecated
	protected Integer queryThirdpartid()
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(getDeviceid());
		if ( cr == null )
			return null;
		return cr.getThirdpartid();
	}
	
	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}
