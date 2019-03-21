package com.iremote.thirdpart.action.event;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.ThirdPart;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;
import com.opensymphony.xwork2.Action;

public class QueryEventAction {

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int lastid = 0 ;
	
	private List<ThirdpartEvent> events ;
	private ThirdPart thirdpart;
 	
	public String execute()
	{
		EventtoThirdpartService svr = new EventtoThirdpartService();
		List<EventtoThirdpart> lst = svr.query(thirdpart.getThirdpartid(), lastid);
		
		if ( lst == null || lst.size() == 0 )
			return Action.SUCCESS;
		events = new ArrayList<ThirdpartEvent>(lst.size());
		
		for ( EventtoThirdpart e : lst)
		{
			ThirdpartEvent t = new ThirdpartEvent();
			t.setDeviceid(e.getDeviceid());
			t.setEventtime(e.getEventtime());
			t.setFloatparam(e.getFloatparam());
			t.setId(e.getId());
			t.setIntparam(e.getIntparam());
			t.setThirdpartid(e.getThirdpartid());
			t.setType(e.getType());
			t.setZwavedeviceid(e.getZwavedeviceid());
			
			if ( e.getObjparam() != null && e.getObjparam().length() > 0 )
				t.setObjparam(JSON.parseObject(e.getObjparam()));
			else 
				t.setObjparam(new JSONObject());
			events.add(t);
		}
		
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public List<ThirdpartEvent> getEvents() {
		return events;
	}

	public void setLastid(int lastid) {
		this.lastid = lastid;
	}

	public void setThirdpart(ThirdPart thirdpart) {
		this.thirdpart = thirdpart;
	}
	
	
}
