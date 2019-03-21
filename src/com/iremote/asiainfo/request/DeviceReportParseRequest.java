package com.iremote.asiainfo.request;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSON;
import com.iremote.asiainfo.connection.WebSocketWrap;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.CommonResponse;
import com.iremote.asiainfo.vo.ReportParse;
import com.iremote.common.thread.ISynchronizeRequest;
import com.iremote.common.thread.SynchronizeRequestManager;

@Deprecated
public class DeviceReportParseRequest  implements ISynchronizeRequest{

	private String deviceid ;
	@SuppressWarnings("unused")
	private byte[] command;
	private AsiainfoMessage message;
	@SuppressWarnings("unused")
	private CommonResponse response;

	public DeviceReportParseRequest(String deviceid, byte[] command) {
		super();
		this.deviceid = deviceid;
		this.command = command;
	}

	public void process()
	{
		//message = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_REPORT_PARSE);
		
		ReportParse ci = new ReportParse();
		ci.setDeviceId(deviceid);
		ci.setReplyResult(0);
		ci.setReplySeriNum("R201505121448380000208");

		message.setMessageinfo(JSON.toJSONString(ci));
		try 
		{
			Object response = SynchronizeRequestManager.synchronizeRequest(this);
			response = (CommonResponse)response;
			System.out.println(JSON.toJSONString(response));
		} catch (Throwable e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void sendRequest() throws IOException, TimeoutException 
	{
		WebSocketWrap.writeMessage(message);
	}

	@Override
	public String getkey() {
		return message.getSequence();
	}

}
