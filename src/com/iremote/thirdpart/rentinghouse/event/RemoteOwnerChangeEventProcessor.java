package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.domain.CommunityAdministrator;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.CommunityAdministratorService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.rentinghouse.vo.InfoChangeEvent;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class RemoteOwnerChangeEventProcessor extends RemoteOwnerChangeEvent implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(RemoteOwnerChangeEventProcessor.class);
	
	@Override
	public void run() 
	{
		CommunityAdministratorService cas = new CommunityAdministratorService();
		CommunityAdministrator can = cas.querybyphoneuserid(getNewownerid());
		
		CommunityAdministrator cao = null ;
		if ( getOldownerid() != 0 )
			cao = cas.querybyphoneuserid(getOldownerid());
		
		if ( can == null && cao == null )
			return ;
		
		if ( can != null )
		{
			saveComunityRemoteRelation(can);
			saveEvent(can);
		}
		
	}
	
	private void saveComunityRemoteRelation(CommunityAdministrator can )
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.query(can.getThirdpartid() , getDeviceid());
		
		if ( cr == null )
		{
			cr = new ComunityRemote();
			cr.setThirdpartid(can.getThirdpartid());
			cr.setDeviceid(getDeviceid());
		}
		cr.setComunityid(can.getCommunityid());
		crs.saveOrUpdate(cr);
	}
	
	private void saveEvent(CommunityAdministrator can)
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
				z.setProductor(zd.getProductor());
				ice.getZwavedevice().add(z);
			}

			ice.setGatewayonline(getGatewayOnlineStatus());

			etd.setObjparam(JSON.toJSONString(ice));
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
	}

	private Integer getGatewayOnlineStatus() {
		return getRemote() == null ? null : getRemote().getStatus();
	}

	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}

}
