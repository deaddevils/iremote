package com.iremote.action.data;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.opensymphony.xwork2.Action;

public class QueryRemoteTemperature {
	private int resultCode = ErrorCodeDefine.TIME_OUT ;
	private int temperature;
	private String deviceid;
	private static Log log = LogFactory.getLog(QueryRemoteTemperature.class);
	public String execute(){
		IAsyncResponse asynchronizeRequest = SynchronizeRequestHelper.asynchronizeRequest(deviceid, createCommandTlv(), 0);
		byte[] content = (byte[]) asynchronizeRequest.getAckResponse(IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
		if(content==null){
			return Action.SUCCESS;
		}
		Integer sequence = TlvWrap.readInteter(content , TagDefine.TAG_RESULT , TlvWrap.TAGLENGTH_LENGTH);
		if(sequence==null||sequence!=0){
			resultCode = sequence;
			return Action.SUCCESS;
		}
		String tem = TlvWrap.readString(content , 61 , TlvWrap.TAGLENGTH_LENGTH);
		if(StringUtils.isEmpty(tem)){
			return Action.SUCCESS;
		}
		temperature = Float.valueOf(tem).intValue();
		resultCode = ErrorCodeDefine.SUCCESS;
		return Action.SUCCESS;
	}
	protected CommandTlv createCommandTlv(){
		return new CommandTlv(6 , 1) ;
	}
	public int getResultCode() {
		return resultCode;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
}
