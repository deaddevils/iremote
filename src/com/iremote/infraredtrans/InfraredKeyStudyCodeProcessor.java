package com.iremote.infraredtrans;

import com.iremote.action.helper.PassThroughDeviceCmdHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.InfraredKey;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.DeviceRawCmdService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfraredKeyService;
import org.xsocket.DataConverter;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Date;

public class InfraredKeyStudyCodeProcessor implements IRemoteRequestProcessor
{
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		String deviceid = nbc.getDeviceid();
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.remoteInfrareddeviceKey);
		if(dos == null)
			return null;

		if (dos.getZwavedeviceid() != null) {
			processZwaveDevice(dos, request);
		} else {
			processInfrared(dos, request);
			dos.setAppendmessage(null);
		}

		dos.setFinished(true);
		dos.setExpiretime(new Date());
		dos.setStatus(GatewayInfrareddeviceStudySteps.finished.ordinal());
		doss.update(dos);

		return null;
	}

	private void processZwaveDevice(DeviceOperationSteps dos, byte[] request) {
		dos.setAppendmessage(convertRawCmd(request));
	}

	private String convertRawCmd(byte[] request) {
		byte[] rawCmd = TlvWrap.readTag(request, TagDefine.TAG_ZWAVE_COMMAND, TagDefine.TAG_HEAD_LENGTH);
		return DataConverter.toHexString(rawCmd, rawCmd.length).replace(" ", "");
	}

	private void processInfrared(DeviceOperationSteps dos, byte[] request) {
		InfraredKeyService iks = new InfraredKeyService();
		InfraredDeviceService ids = new InfraredDeviceService();
		InfraredDevice infraredDevice = ids.query(dos.getInfrareddeviceid());
		if(infraredDevice == null)
			return;
		InfraredKey ik = iks.querybyinfrareddeviceidandkeyindex(dos.getInfrareddeviceid(), dos.getKeyindex());

		if(ik == null){
			ik = new InfraredKey();
			ik.setInfrareddeviceid(infraredDevice.getInfrareddeviceid());
			ik.setKeyindex(dos.getKeyindex());
		}
		ik.setKeycode(Utils.byteArraytoString(request));

		iks.saveOrUpdate(ik);
	}
}
