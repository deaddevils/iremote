package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.task.timertask.processor.ZwaveDelayAccordingHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class DoorlockOpenProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DoorlockOpenProcessor.class);
	
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
			etd.setType(getEventtype());
			etd.setDeviceid(getDeviceid());
			etd.setZwavedeviceid(getZwavedeviceid());
			etd.setEventtime(getEventtime());
			if ( super.getAppendmessage() != null )
				etd.setObjparam(super.getAppendmessage().toJSONString());
			
			JSONObject json = getAppendmessage();
			if ( json != null && json.containsKey("usercode") )
			{
				etd.setIntparam(json.getIntValue("usercode"));
			}
			etd.setWarningstatus(super.getWarningstatus());
			etd.setWarningstatuses(super.getWarningstatuses());
	
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}

		executeDoorlockAssociation();
	}

	private void executeDoorlockAssociation() {
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(getZwavedeviceid());
		JSONObject json = getAppendmessage();
		if (zd == null || json == null || !json.containsKey("usercode")) {
			return ;
		}

		ZwaveDelayAccordingHelper zdah = new ZwaveDelayAccordingHelper(zd);
		zdah.executeDoorlockAssociation(zd.getZwavedeviceid(), json.getIntValue("usercode"), zd.getName());
	}

	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}
