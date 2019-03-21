package com.iremote.thirdpart.wcj.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.DoorlockUserService;

public class DeleteFingerprint extends DeleteLockUserBaseAction {
	private static Log log = LogFactory.getLog(DeleteFingerprint.class);

	@Override
	protected boolean deleteLockUser() {
		byte[] b = createcommand();

		boolean sendcommand = sendcommand(createCommandTlv(b));
/*		if(sendcommand){
			DoorlockUserService dus = new DoorlockUserService();
			dus.delete(zwavedeviceid, IRemoteConstantDefine.DOOR_LOCK_USER_FINGERPRINT, usercode);
		}*/
		return sendcommand;
	}

	protected byte[] createcommand() {
		return new byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x09, (byte)usercode, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	}

	protected Byte[] responseKey() {
		return new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x0A, (byte)usercode };
	}

	protected boolean checkResult(byte[] cmd) {
		if (((cmd[3] & 0xff) == 0xA0) && (cmd[8] == 0x01)) // delete fingerprint
			return true;
		return false;
	}

	protected CommandTlv createCommandTlv(byte[] command) {
		CommandTlv ct = new CommandTlv(30, 7);
		ct.addUnit(new TlvByteUnit(70, command));
		ct.addUnit(new TlvIntUnit(71, lock.getNuid(), 1));
		ct.addUnit(new TlvIntUnit(72, 0, 1));
		return ct;
	}

	protected boolean sendcommand(CommandTlv ct) {
		try {
			ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), ct,
					responseKey(), timeoutsecond, 0);
			byte[] rst = wrap.getResponse();
			if (rst == null) {
				resultCode = wrap.getAckresult()==0?ErrorCodeDefine.TIME_OUT:wrap.getAckresult();
				return false;
			}
		
			boolean checkResult = checkResult(rst);
			if(!checkResult){
				resultCode = ErrorCodeDefine.UNKNOW_ERROR;
				return false;
			}
			return true;
	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultCode = ErrorCodeDefine.UNKNOW_ERROR;
			return false;
		}
	}
	@Override
	public void setDoorlockpasswordusertype(int doorlockpasswordusertype) {
		this.doorlockpasswordusertype = IRemoteConstantDefine.DOORLOCKUSER_TYPE_FINGERPRINT;
	}
}
