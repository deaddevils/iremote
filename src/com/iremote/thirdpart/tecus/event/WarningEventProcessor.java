package com.iremote.thirdpart.tecus.event;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.RemoteService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class WarningEventProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {

	private static final String SUFFIX_SHORT = "short";
		
	@Override
	public void run() 
	{
		if (getWarningstatus() == null ||getWarningstatus() == 0 )
			return ;
		EventtoThirdpart etd = new EventtoThirdpart();
		
		etd.setThirdpartid(IRemoteConstantDefine.DEFAULT_THIRDPART_ID);
		etd.setType(getEventtype());
		etd.setDeviceid(getDeviceid());
		etd.setEventtime(getEventtime());
		etd.setZwavedeviceid(getZwavedeviceid());
		
		etd.setObjparam(createWarningMessage());
		
		EventtoThirdpartService svr = new EventtoThirdpartService();
		svr.save(etd);
	}

	private String createWarningMessage()
	{
		RemoteService svr = new RemoteService();
		Remote r = svr.getIremotepassword(getDeviceid());
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(getZwavedeviceid());
		
		if ( r == null || zd == null )
			return null ;
		
		String devicename = zd.getName();
		if ( devicename == null )
			devicename = "";
		MessageParser m = MessageManager.getMessageParser(getEventtype() + SUFFIX_SHORT, r.getPlatform(), IRemoteConstantDefine.DEFAULT_UNCH_LANGUAGE) ;
		if ( m != null )
			m.getParameter().put("name", devicename);
		else 
			return null ;
		JSONObject json = new JSONObject();
		json.put("content", m.getMessage());
		
		return json.toJSONString() ;
	}

	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}
