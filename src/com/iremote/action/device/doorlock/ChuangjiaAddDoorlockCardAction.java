package com.iremote.action.device.doorlock;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.Card;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.CardService;
import com.iremote.service.DoorlockUserService;
import com.opensymphony.xwork2.Action;

public class ChuangjiaAddDoorlockCardAction {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ChuangjiaAddDoorlockCardAction.class);

	public static int STATUS_CARD_SUCCESS = ErrorCodeDefine.SUCCESS;
	public static int STATUS_CARD_OVERFLOW = ErrorCodeDefine.STATUS_CARD_OVERFLOW;

	private int zwavedeviceid;
	private ZWaveDevice zwavedevice;
	private String cardname;
	private String cardinfo;
	private int usertype = 2;// 2 ID CARD;
	private int cardtype = 1;// 0x1 MF; 0x2 ID card; 0xf Other
	private String validfrom;
	private String validthrough;

	private byte[] command = new byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0x90, (byte) 0x10, 0x01, 0x01, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	private static Byte[] REPORT_ADD_CARD_REPORT = new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0x90, (byte) 0x10, 0x01,
			0x03 };

	private int usercode;

	private int resultCode = ErrorCodeDefine.SUCCESS;

	public String execute() {
		if (zwavedevice == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}

		if (!ConnectionManager.contants(zwavedevice.getDeviceid())) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return Action.SUCCESS;
		}

		if (!initCardinfo() || !initUsercode())
			return Action.SUCCESS;

		if (!sendcommand() || !sendValidtime())
			return Action.SUCCESS;

		saveDoorlockUser();

		sendCurrentTime();

		return Action.SUCCESS;
	}

	private boolean sendValidtime() {
		this.resultCode = DoorlockHelper.sendTimeConfigure(zwavedevice.getDeviceid(), zwavedevice.getNuid(),
				(byte) usertype, (byte) usercode, validfrom, validthrough);

		return (this.resultCode == ErrorCodeDefine.SUCCESS);
	}

	private void sendCurrentTime() {
		DoorlockHelper.sendCurrentTime(zwavedevice.getDeviceid(), zwavedevice.getNuid());
	}

	private boolean initUsercode() {
		DoorlockUserService dus = new DoorlockUserService();
		List<Integer> lst = dus.queryUsercode(zwavedeviceid, IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD);

		for (int i = 1; i <= 100; i++) {
			if (!lst.contains(i)) {
				usercode = i;
				command[7] = (byte) usercode;
				return true;
			}
		}

		resultCode = STATUS_CARD_OVERFLOW;
		return false;
	}

	private void saveDoorlockUser() {
		Card c = null;
		if (cardinfo != null) {
			String sha1key = DigestUtils.sha1Hex(cardinfo);

			CardService cs = new CardService();
			c = cs.queryCardbykey(sha1key, Utils.getRemotePlatform(zwavedevice.getDeviceid()));

			if (c == null) {
				c = new Card();
				c.setSha1key(sha1key);
				c.setThirdpartid(Utils.getRemotePlatform(zwavedevice.getDeviceid()));
				c.setCardtype(cardtype);
				cs.save(c);
			}
		}
		DoorlockUserService dus = new DoorlockUserService();
		dus.delete(zwavedeviceid, IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD_10, usercode);

		DoorlockUser du = new DoorlockUser();

		if (StringUtils.isBlank(cardname))
			cardname = String.format("%s%d",
					MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_CARD_USER, 0,
							IRemoteConstantDefine.DEFAULT_LANGUAGE),
					usercode);
		du.setUsername(cardname);
		du.setUsertype(IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_CARD_10);
		du.setUsercode(usercode);
		du.setZwavedeviceid(zwavedeviceid);
		du.setValidfrom(validfrom);
		du.setValidthrough(validthrough);
		if (c != null)
			du.setCardid(c.getCardid());

		dus.save(du);

	}

	public boolean sendcommand() {
		CommandTlv ct = CommandUtil.createCommandTlv(command, zwavedevice.getNuid());
		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(zwavedevice.getDeviceid(),
				zwavedevice.getNuid(), ct, REPORT_ADD_CARD_REPORT, 0, 0);
		byte[] report = wrap.getResponse();
		if (report == null) {
			resultCode = wrap.getAckresult();
			return false;
		}
		if (Utils.isByteMatch(REPORT_ADD_CARD_REPORT, report)) {
			if (report[8] == 1) {
				return true;
			} else if (report[8] == 2) {
				resultCode = STATUS_CARD_OVERFLOW;
				return false;
			} else if (report[8] == 0) {
				resultCode = ErrorCodeDefine.DEVICE_RETURN_ERROR;
				return false;
			}

		}
		return false;
	}

	public boolean initCardinfo() {
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

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	public void setCardinfo(String cardinfo) {
		this.cardinfo = cardinfo;
	}

	public void setCardtype(int cardtype) {
		this.cardtype = cardtype;
	}

	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough) {
		this.validthrough = validthrough;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getUsercode() {
		return usercode;
	}

	public void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
	}

}
