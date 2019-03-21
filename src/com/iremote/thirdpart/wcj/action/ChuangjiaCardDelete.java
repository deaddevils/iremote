package com.iremote.thirdpart.wcj.action;

import com.iremote.action.device.doorlock.DoorlockPasswordHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;

public class ChuangjiaCardDelete implements DeleteDoorlockCardUserInterface {

	private ZWaveDevice zWaveDevice;
	private DoorlockUser doorlockUser;
	private int resultCode = ErrorCodeDefine.SUCCESS;

	@Override
	public boolean sendDeleteCommand() {
		sendcommand();

		if (resultCode == ErrorCodeDefine.SUCCESS)
			deletedoorlockuser();

		return resultCode == ErrorCodeDefine.SUCCESS;
	}

	private void deletedoorlockuser() {
		DoorlockUserService doorlockUserService = new DoorlockUserService();
		doorlockUserService.delete(doorlockUser);
	}

	private void sendcommand() {
		byte[] command = new byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0x90, 0x10, 0x01, 0x09,
				(byte) doorlockUser.getUsercode(), 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00 };

		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(command, zWaveDevice.getNuid());

		Byte[] rk = new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0x90, 0x10, 0x01, 0x0A,
				(byte) doorlockUser.getUsercode() };

		ZwaveReportRequestWrap rst = ZwaveReportRequestManager.sendRequest(zWaveDevice.getDeviceid(),
				zWaveDevice.getNuid(), ct, rk, 0, 0);
		
		if (rst.getResponse() != null)
			reportArrive(rst.getResponse());
		else if (rst.getAckresult() != ErrorCodeDefine.SUCCESS)
			this.resultCode = rst.getAckresult();
		else
			this.resultCode = ErrorCodeDefine.TIME_OUT;
	}

	public void reportArrive(byte[] report) {
		if (report == null)
			this.resultCode = ErrorCodeDefine.TIME_OUT;
		else if (report[8] == 1)
			resultCode = ErrorCodeDefine.SUCCESS;
		else if (report[8] == 0)
			resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
	}

	@Override
	public void setValue(ZWaveDevice lock, DoorlockUser doorlockUser) {
		this.zWaveDevice = lock;
		this.doorlockUser = doorlockUser;
	}

	public int getResultCode() {
		return resultCode;
	}
}
