package com.iremote.thirdpart.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class ThirdPartHelper 
{
	public static ComunityRemote queryThirdpart(String deviceid)
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		return crs.querybyDeviceid(deviceid);
	}
	
	public static void saveEventtoThirdpart(int thirdpartid,String type,String deviceid ,int zwavedeviceid ,int intparam,Float floatparam,String objparam,Date eventtime)
	{
		EventtoThirdpart etd = new EventtoThirdpart();
		
		etd.setThirdpartid(thirdpartid);
		etd.setType(type);
		etd.setDeviceid(deviceid);
		etd.setZwavedeviceid(zwavedeviceid);
		etd.setIntparam(intparam);
		etd.setFloatparam(floatparam);
		etd.setObjparam(objparam);
		etd.setEventtime(eventtime);
				
		EventtoThirdpartService svr = new EventtoThirdpartService();
		svr.save(etd);

	}

	public static List<Integer> queryThirdpartid(String deviceid)
	{
		List<Integer> lst = new ArrayList<Integer>();
		ComunityRemote cr = ThirdPartHelper.queryThirdpart(deviceid);
		if ( cr != null )
			lst.add(cr.getThirdpartid());
		
		if ( ServerRuntime.getInstance().getDoorlinkallthirdpartid() != 0 
				&& IRemoteConstantDefine.PLATFORM_DORLINK == Utils.getRemotePlatform(deviceid) )
			lst.add(ServerRuntime.getInstance().getDoorlinkallthirdpartid());
		
		if ( ServerRuntime.getInstance().getAmetaallthirdpartid() != 0 
				&&IRemoteConstantDefine.PLATFORM_AMETA == Utils.getRemotePlatform(deviceid) )
			lst.add(ServerRuntime.getInstance().getAmetaallthirdpartid());
		
		return lst ;
	}


	public static void sendDeleteThirdpartMessage(int result, int zwavedeviceid, int usercode, String deviceid, String tid, Date eventtime) {
		sendThirdpartMessage(result, zwavedeviceid, usercode, deviceid, tid, eventtime, IRemoteConstantDefine.DELETE_LOCK_USER_RESULT);
	}

	public static void sendAddThirdpartMessage(int result, int zwavedeviceid, int usercode, String deviceid, String tid, Date eventtime) {
		sendThirdpartMessage(result, zwavedeviceid, usercode, deviceid, tid, eventtime, IRemoteConstantDefine.ADD_LOCK_USER_RESULT);
	}

	public static void sendThirdpartMessage(int result, int zwavedeviceid, int usercode, String deviceid, String tid, Date eventtime, String type) {
		List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(deviceid);

		for (Integer tpid : tidlst) {
			if (tpid == null || tpid == 0)
				continue;

			EventtoThirdpart etd = new EventtoThirdpart();

			etd.setThirdpartid(tpid);
			etd.setType(type);
			etd.setDeviceid(deviceid);
			etd.setZwavedeviceid(zwavedeviceid);
			etd.setIntparam(usercode);
			etd.setEventtime(eventtime);
			JSONObject json = new JSONObject();
			json.put("resultCode", result);
			if (tid != null) {
				json.put("tid", tid);
			}
			etd.setObjparam(json.toJSONString());

			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
	}

}
