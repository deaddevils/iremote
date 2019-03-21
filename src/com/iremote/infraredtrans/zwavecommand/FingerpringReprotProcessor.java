package com.iremote.infraredtrans.zwavecommand;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.service.DeviceOperationStepsService;

public class FingerpringReprotProcessor extends ZWaveReportBaseProcessor{
	private static Log log = LogFactory.getLog(FingerpringReprotProcessor.class);
	@Override
	protected void updateDeviceStatus() {
		
		int zwavedeviceid= zrb.getDevice().getZwavedeviceid();
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybyzwavedeviceidandtype(zwavedeviceid,DeviceOperationType.readFingerping);
		if(dos == null) {
			return ;
		}

		byte[] cmd = zrb.getCmd();
		byte status0 = cmd[8];
		int packetId = cmd[7] & 0xff;
		
		if (log.isInfoEnabled()){
			log.info("Finger123 id" +packetId);
		}
		if(packetId == 255) {
			if(status0 == 0x03) {
				// 再次输入指纹
				dos.setStatus(2);
				return;
			}
			String appendmessage = dos.getAppendmessage();
			JSONObject jsonObject = JSON.parseObject(appendmessage);
			if(jsonObject.containsKey("0") && jsonObject.containsKey("1") && jsonObject.containsKey("2") && jsonObject.containsKey("3")) {
				dos.setStatus(3);
				dos.setFinished(true);
			}else {
				dos.setStatus(13);
				dos.setFinished(true);
			}
			doss.saveOrUpdate(dos);	
			return ;
		}
		
		byte[]data = new byte[128];
		for(int i=0; i+10 < cmd.length && i<128;i++) {
			data[i]=cmd[i+10];
		}
		
		if(new Date().getTime() > dos.getExpiretime().getTime()) {
			dos.setStatus(10);
			dos.setFinished(true);
		}
		if(status0 == 0x00) {
			// 失败
			dos.setStatus(11);
		}
		if(status0 == 0x03) {
			// 再次输入指纹
			dos.setStatus(2);
		}
		if(status0 == 0x01) {
			//success
			/*if(dos.getAppendmessage() != null) {
				byte[] databyte = Base64.decodeBase64(dos.getAppendmessage());
				byte[] newdatatype = addBytes(databyte,data);
				dos.setAppendmessage(Base64.encodeBase64String(newdatatype));
				Utils.print("Finger123: ", newdatatype);
			}else {
				Utils.print("Finger321: ", data);
				dos.setAppendmessage(Base64.encodeBase64String(data));
				dos.setStatus(2);
			}*/
			Utils.print("Finger123: ", data);
			if(dos.getAppendmessage()!=null) {
				JSONObject jsonObject = JSON.parseObject(dos.getAppendmessage());
				jsonObject.put(String.valueOf(packetId),Base64.encodeBase64String(data));
				dos.setAppendmessage(jsonObject.toJSONString());
			}else{
				JSONObject json = new JSONObject();
				json.put(String.valueOf(packetId), Base64.encodeBase64String(data));
				dos.setAppendmessage(json.toJSONString());
			}
		}
		doss.saveOrUpdate(dos);
	}

	@Override
	public String getMessagetype() {
		return null;
	}
}
