package com.iremote.asiainfo.request;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.CommandInfo;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class DeviceCommandRequest {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DeviceCommandRequest.class);

	private String deviceid;
	private CommandTlv command;
	private AsiainfoMessage message;
	private byte[] result ;
	private CommandInfo ci;
	private int timeoutsecond;
	
	public DeviceCommandRequest(CommandInfo ci,CommandTlv command) {
		super();
		this.ci = ci;
		this.command = command;
		//this.deviceid = ci.getDeviceId();
	}

	public void process()
	{
		int sq = AsiainfoHttpHelper.getSequence();
		command.addOrReplaceUnit(new TlvIntUnit(31 , sq , 2));
		
		byte[] cmd = command.getByte();
		
		Utils.print("send message" , cmd , cmd.length);
		message = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_SET_COMMAND , deviceid);
		message.setMessage(cmd);
		message.setMessagelength(cmd.length);
		
		message.setMessageinfo(JSON.toJSONString(ci));

		result = AsiainfoHttpHelper.synchronizeRequest(message, deviceid, sq, timeoutsecond);
	}
	
	public byte[] getResult() {
		return result;
	}

	public void setTimeoutsecond(int timeoutsecond) {
		this.timeoutsecond = timeoutsecond;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
}
