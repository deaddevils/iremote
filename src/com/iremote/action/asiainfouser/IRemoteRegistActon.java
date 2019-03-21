package com.iremote.action.asiainfouser;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.asiainfo.connection.WebSocketClientConnection;
import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.helper.AsiainfoMessageHelper;
import com.iremote.asiainfo.vo.BusinessData;
import com.iremote.asiainfo.vo.RemoteInfo;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.Remote;
import com.iremote.service.IremotepasswordService;
import com.opensymphony.xwork2.Action;

public class IRemoteRegistActon {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String result;
	private String deviceid ;
	
	public String execute()
	{
		BusinessData data = new BusinessData();
		data.setSystemId(IRemoteConstantDefine.CLIENT_CODE_OF_SYSTEM_ID);
		data.setAccessToken(WebSocketClientConnection.getAccessToken());
		data.setMsgSeqNo(AsiainfoMessageHelper.createMessageSequence());
	
		RemoteInfo di = new RemoteInfo();
		di.setDeviceId(deviceid);
		di.setAuthKey(deviceid.substring(deviceid.length() - 6));
		di.setVersion("1");
		di.setModel("DLHSS1121");
		di.setType(1);
		di.setCreateTime(Utils.formatTime(new Date() , "yyyyMMddHHmmss"));
		
		data.setMessage(new Object[]{di});
		data.setCount(1);
		
		result = AsiainfoHttpHelper.httprequest(IRemoteConstantDefine.ASIA_INFO_BASE_URL + "/partner/homeControlInfoReg" ,data);
		
		if ( result != null && result.length() > 0 )
		{
			JSONObject json = JSON.parseObject(result);
			if ( json.containsKey("resultCode") && json.getIntValue("resultCode") == 0 )
			{
				Remote r = new Remote();
				r.setDeviceid(deviceid);
				r.setPlatform(IRemoteConstantDefine.PLATFORM_ASININFO);
				r.setCreatetime(new Date());
				
				IremotepasswordService rs = new IremotepasswordService();
				rs.save(r);
			}
		}
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getResult() {
		return result;
	}
	
	
}
