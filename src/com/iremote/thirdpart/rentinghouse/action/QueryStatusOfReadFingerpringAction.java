package com.iremote.thirdpart.rentinghouse.action;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.zwavecommand.FingerpringReprotProcessor;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

//@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class QueryStatusOfReadFingerpringAction {

	private static Log log = LogFactory.getLog(QueryStatusOfReadFingerpringAction.class);
	
	private Integer resultCode = ErrorCodeDefine.SUCCESS ;
	private Integer zwavedeviceid;
	private Integer status;
	private String fingerprint;
	public String execute(){
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.query(zwavedeviceid);
		if ( zd == null ){
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybyzwavedeviceidandtype(zwavedeviceid, DeviceOperationType.readFingerping);
		if(dos == null || new Date().getTime() > dos.getExpiretime().getTime()) {
			resultCode = ErrorCodeDefine.SUCCESS ;
			status = 0;
			return Action.SUCCESS;
		}
		status = dos.getStatus();
		if(status == 3) {
			JSONObject json = JSON.parseObject(dos.getAppendmessage());
			ArrayList<byte[]> arrays = new ArrayList<>();
			for(int i=0; json.containsKey(String.valueOf(i));i++) {
				String s = json.getString(String.valueOf(i));
				log.info("json str "+i+": "+s);
				byte[] decodeBase64 = Base64.decodeBase64(s);
				if(i == 0) {
					arrays.add(decodeBase64);
				}else {
					byte[] addBytes = addBytes(arrays.get(i-1),decodeBase64);
					arrays.add(addBytes);
				}
			}
			if (arrays== null || arrays.size() == 0) {
				resultCode = ErrorCodeDefine.TIME_OUT;
				return Action.SUCCESS;
			}
			byte[] bb = arrays.get(arrays.size()-1);
			Utils.print("byte str: ", bb);
			fingerprint =  Base64.encodeBase64String(bb);
		}
		return Action.SUCCESS;
	}
	
	public Integer getResultCode() {
		return resultCode;
	}
	public Integer getStatus() {
		return status;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	

	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;
	}
}
