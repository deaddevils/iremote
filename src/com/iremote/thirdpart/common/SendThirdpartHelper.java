package com.iremote.thirdpart.common;

import java.util.Date;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.schedule.ScheduleManager;

public class SendThirdpartHelper implements Runnable{

	private int result;
	private int zwavedeviceid;
	private int usercode;
	private String deviceid;
	private String tid;
	private String msgstr;
	
	public SendThirdpartHelper() {
		super();
	}

	public SendThirdpartHelper(int result, int zwavedeviceid, int usercode, String deviceid, String tid,
			String msgstr) {
		super();
		this.result = result;
		this.zwavedeviceid = zwavedeviceid;
		this.usercode = usercode;
		this.deviceid = deviceid;
		this.tid = tid;
		this.msgstr = msgstr;
	}
	public void sendThirdpartMsg(){
		ScheduleManager.excutein(3,this);
	}

	@Override
	public void run() {
		if(IRemoteConstantDefine.ADD_LOCK_USER_RESULT.equals(msgstr)){
			ThirdPartHelper.sendAddThirdpartMessage(result, zwavedeviceid, usercode & 0xff, deviceid, tid, new Date());
		}else if(IRemoteConstantDefine.DELETE_LOCK_USER_RESULT.equals(msgstr)){
			ThirdPartHelper.sendDeleteThirdpartMessage(result, zwavedeviceid, usercode & 0xff, deviceid, tid, new Date());
		}
	}
}
