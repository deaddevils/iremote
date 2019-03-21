package com.iremote.thirdpart.tecus.event;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceUnalarmEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

import net.sf.json.JSONObject;

public class UnwarningEventProcessor extends ZWaveDeviceUnalarmEvent implements ITextMessageProcessor{
	
	@Override
	public void run() 
	{
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
		JSONObject json = new JSONObject();
		if ( this.getEmployeename() != null && this.getEmployeename().length() > 0 )
		{
			json.put("employeename", this.getEmployeename());
		}
		else if ( this.getPhonenumber() != null && this.getPhonenumber().length() > 0 )
		{
			json.put("countrycode", this.getCountrycode());
			json.put("phonenumber", this.getPhonenumber());
		}
		else 
			return "";
		return json.toString();
	}

	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}
