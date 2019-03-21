package com.iremote.asiainfo.request;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSON;
import com.iremote.asiainfo.connection.WebSocketWrap;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.asiainfo.vo.CommonResponse;
import com.iremote.asiainfo.vo.RemoteInfo;
import com.iremote.common.thread.ISynchronizeRequest;
import com.iremote.common.thread.SynchronizeRequestManager;

@Deprecated
public class DeviceInfoFilingRequest implements ISynchronizeRequest{

	private String deviceid;
	private AsiainfoMessage message;
	private CommonResponse response;
	
	public DeviceInfoFilingRequest(String deviceid) {
		super();
		this.deviceid = deviceid;
	}

	public void process()
	{
		//message = AsiainfoMessageHelper.createMessageHead(IRemoteConstantDefine.ASIAINFO_MSG_ID_DEVICE_INFO_FILING);
		
		RemoteInfo di = new RemoteInfo();
		di.setDeviceId(deviceid);
		di.setDeviceType("1");
		di.setManufacturer("jwzh");
		di.setVersion("1.0");
		
		message.setMessageinfo(JSON.toJSONString(di));

		try 
		{
			Object response = SynchronizeRequestManager.synchronizeRequest(this);
			response = (CommonResponse)response;
			System.out.println(JSON.toJSONString(response));
		} catch (Throwable e) {
			e.printStackTrace();
		} 
	}
	
	public CommonResponse getResponse() {
		return response;
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
