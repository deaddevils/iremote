package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.rentinghouse.vo.InfoChangeEvent;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class InfoChangeProcessor extends InfoChange implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(InfoChangeProcessor.class);

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
			etd.setType(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED);
			etd.setDeviceid(getDeviceid());
			etd.setEventtime(getEventtime());
			
			InfoChangeEvent ice = new InfoChangeEvent();
			ice.setDeviceid(getDeviceid());
			
			IremotepasswordService ips = new IremotepasswordService();
			Remote r = ips.getIremotepassword(getDeviceid());
			if ( r == null )
				return ;
			
			ice.setName(r.getName());
			if ( ice.getName() == null || ice.getName().length() == 0 )
			{
				if ( r.getDeviceid() != null && r.getDeviceid().length() > 6)
					ice.setName(r.getDeviceid().substring(r.getDeviceid().length() - 6 ));
				else 
					ice.setName(r.getDeviceid());
			}
			ice.setLoginname(r.getPhonenumber());
			ice.setStatus(r.getStatus());
			queryZwavedevice(ice);
			
			queryInfraredDevice(ice);
			
			etd.setObjparam(JSON.toJSONString(ice));  
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
	}
	
	private void queryZwavedevice(InfoChangeEvent ice)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		List<ZWaveDevice> lst = zds.querybydeviceid(ice.getDeviceid());
		for ( ZWaveDevice zd : lst )
		{
			ZWaveDevice z = new ZWaveDevice();
			z.setDevicetype(zd.getDevicetype());
			z.setZwavedeviceid(zd.getZwavedeviceid());
			z.setName(zd.getName());
			z.setProductor(zd.getProductor());
			z.setStatus(zd.getStatus());
			z.setStatuses(zd.getStatuses());
			ice.getZwavedevice().add(z);
		}
	}
	
	private void queryInfraredDevice(InfoChangeEvent ice)
	{
		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> lst = ids.querybydeviceid(ice.getDeviceid());
		
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( InfraredDevice id : lst)
		{
			InfraredDevice idd = new InfraredDevice();
			idd.setInfrareddeviceid(id.getInfrareddeviceid());
			idd.setName(id.getName());
			idd.setDevicetype(id.getDevicetype());
			
			ice.getInfrareddevice().add(idd);
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
