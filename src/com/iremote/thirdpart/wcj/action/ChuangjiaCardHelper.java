package com.iremote.thirdpart.wcj.action;

import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;

public class ChuangjiaCardHelper {
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int usercode;
	private static Byte[] REPORT_ADD_CARD_REPORT = new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0x90, (byte) 0x10, 0x01,0x03 };

	public boolean initCardinfo(String cardinfo, byte[] command) {
		if (cardinfo.length() != 8 && cardinfo.length() != 16) {
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}
		if (cardinfo.length() == 8) {
			cardinfo += "00000000";
		}

		for (int i = 0; i < cardinfo.length() / 2; i++) {
			command[i + 8] = (byte) (Integer.valueOf(cardinfo.substring(i * 2, (i * 2) + 2), 16)).intValue();
		}
		return true;
	}

	public boolean initUsercode(int zwavedeviceid, byte[] command) {
		DoorlockUserService dus = new DoorlockUserService();
		List<Integer> lst = dus.queryUsercode(zwavedeviceid, IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD);

		for (int i = 1; i <= 100; i++) {
			if (!lst.contains(i)) {
				usercode = i;
				command[7] = (byte) usercode;
				return true;
			}
		}

		resultCode = ErrorCodeDefine.STATUS_CARD_OVERFLOW;
		return false;
	}

	public boolean sendcommand(ZWaveDevice zwavedevice, byte[] command) {
		CommandTlv ct = CommandUtil.createCommandTlv(command, zwavedevice.getNuid());
		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(zwavedevice.getDeviceid(),
				zwavedevice.getNuid(), ct, REPORT_ADD_CARD_REPORT, 0, 0);
		byte[] report = wrap.getResponse();
		if (report == null) {
			if(wrap.getAckresult()!=0){
				resultCode = wrap.getAckresult();
			}else{
				resultCode = ErrorCodeDefine.TIME_OUT;
			}
			return false;
		}
		if (Utils.isByteMatch(REPORT_ADD_CARD_REPORT, report)) {
			if (report[8] == 1) {
				return true;
			} else if (report[8] == 2) {
				resultCode = ErrorCodeDefine.STATUS_CARD_OVERFLOW;
				return false;
			} else if (report[8] == 0) {
				resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
				return false;
			}

		}
		return false;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getUsercode() {
		return usercode;
	}
	
}
