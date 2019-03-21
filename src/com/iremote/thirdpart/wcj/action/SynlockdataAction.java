package com.iremote.thirdpart.wcj.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.Remote;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.Comunity;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.ComunityService;
import com.iremote.thirdpart.wcj.vo.Lock;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class SynlockdataAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String starttime;
	private String endtime;
	private List<Lock> locklist = new ArrayList<Lock>();
	private Date start;
	private Date end;
	private ThirdPart thirdpart;
	
	
	public String execute()
	{
		init();
		
		ComunityService cs = new ComunityService();
		List<Comunity> cl = cs.queryByThirdpartid(thirdpart.getThirdpartid());
		
		ComunityRemoteService svr = new ComunityRemoteService();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		for ( Comunity c : cl )
		{
			List<Remote> lst = svr.queryRemote(c.getComunityid(), start, end);
			for ( Remote r : lst)
			{
				List<ZWaveDevice> zdl = zds.querybydeviceidtypelist(r.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK);
				if ( zdl == null || zdl.size() == 0 )
					continue;
				for ( ZWaveDevice zd : zdl )
				{
					Lock lk = new Lock(c.getCode() , zd.getName(), zd.getZwavedeviceid() , getlockstatus(zd));
					locklist.add(lk);
				}
			}
		}
		return Action.SUCCESS;
	}

	private int getlockstatus(ZWaveDevice zd)
	{
		if ( zd.getStatus() == -1 )
			return 1 ;
		return 0;
	}
	
	private void init()
	{
		if ( starttime == null )
			starttime = "2000-01-01 00:00:00";
		start = Utils.parseDay(starttime);
		if ( endtime == null )
			end = new Date();
		else 
			end = Utils.parseDay(endtime);
		
		thirdpart = (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public List<Lock> getLocklist() {
		return locklist;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
