package com.iremote.thirdpart.rentinghouse.action;

import java.util.Date;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

//@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class ReadFingerpringAction {

	private Integer resultCode = ErrorCodeDefine.SUCCESS ;
	private int zwavedeviceid;
	
	public String execute() {
		ZWaveDeviceService zds = new ZWaveDeviceService();

		ZWaveDevice zd = zds.query(zwavedeviceid);
		if ( zd == null )
		{
			this.resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		byte[] startreadCmd = new byte[]{(byte) 0x80,0x07,0x00,(byte) 0xA0,0x10,0x01,0x10
				,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	
		CommandTlv commandTlv = CommandUtil.createCommandTlv(startreadCmd,zd.getNuid());
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybyzwavedeviceidandtype(zwavedeviceid,DeviceOperationType.readFingerping);
		if(dos ==null) {
			dos = new DeviceOperationSteps();
		}
		initDeviceOperationStpe(zd,dos);
		byte[] rst = SynchronizeRequestHelper.synchronizeRequest(zd.getDeviceid(),commandTlv, 0);
//		byte[] rst = (byte[])ar.getAckResponse(IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND);
		if(rst !=null) {
			resultCode = TlvWrap.readInteter(rst , 1 , TlvWrap.TAGLENGTH_LENGTH);
			if(resultCode == ErrorCodeDefine.SUCCESS) {
				doss.saveOrUpdate(dos);	
			}
			return Action.SUCCESS;
		}
		resultCode = ErrorCodeDefine.TIME_OUT;
		return Action.SUCCESS;
	}
	
	private DeviceOperationSteps initDeviceOperationStpe(ZWaveDevice zd,DeviceOperationSteps dos) {
		dos.setDeviceid(zd.getDeviceid());
		dos.setStarttime(new Date());
		dos.setExpiretime(new Date(new Date().getTime() + 60*1000));
		dos.setZwavedeviceid(zd.getZwavedeviceid());
		dos.setOptype(DeviceOperationType.readFingerping.ordinal());
		dos.setAppendmessage(null);
		dos.setStatus(1);
		dos.setFinished(false);
		return dos;
	}
	
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public Integer getResultCode() {
		return resultCode;
	}
}
