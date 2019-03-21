package cn.com.isurpass.iremote.opt.qrcode;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.GatewayInfo;
import com.iremote.service.GatewayInfoService;
import com.opensymphony.xwork2.Action;

public class QueryGatewayQrcodeAction {
	private String deviceid = "";
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String qid ;
	private String qrstring ;
	
	public String execute(){
		
		if ( StringUtils.isBlank(deviceid)){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		GatewayInfoService gis = new GatewayInfoService();
		GatewayInfo gi = gis.querybydeviceid(deviceid);
		
		if ( gi != null ){
			if ( StringUtils.isNotBlank(gi.getGatewaytype()) && !gi.getGatewaytype().equals("gateway") ){
				resultCode = ErrorCodeDefine.NO_PRIVILEGE;
				return Action.SUCCESS;
			}
			qid = gi.getQrcodekey(); 
		}else{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( StringUtils.isBlank(qid)){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		JSONObject json = new JSONObject();
		json.put("type", "gateway");
		json.put("qid", qid);
		
		qrstring = json.toJSONString();
		
		return Action.SUCCESS;	
	
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getQrstring() {
		return qrstring;
	}

	
}
