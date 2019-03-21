package com.iremote.thirdpart.wcj.action;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.iremote.action.device.doorlock.DoorlockPasswordHelper;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
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

public class JwzhCard implements AddDoorlockCardUserInterface{
	private static final int[] INT_TO_WEEK_APP = new int[] { 128, 64, 32, 16, 8, 4, 2, 1 };
	private static final int[] INT_TO_WEEK_LOCK = new int[] { 128, 1, 2, 4, 8, 16, 32, 64 };
	public static int STATUS_CARD_SUCCESS = ErrorCodeDefine.SUCCESS;
	public static int STATUS_CARD_OVERFLOW = ErrorCodeDefine.STATUS_CARD_OVERFLOW;

	private int status = ErrorCodeDefine.TIME_OUT;

	private int zwavedeviceid;
	private ZWaveDevice zwavedevice;
	private String cardname;
	private String cardinfo;
	private int cardtype;// 0x1 MF; 0x2 ID card; 0xf Other
	private String validfrom;
	private String validthrough;
	private Integer weekday;
	private String starttime;
	private String endtime;

	private byte[] command = new byte[] { (byte) 0x80, 0x09, 0x01, (byte) 0xff, (byte) 0xff, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	private static Byte[] REPORT_ADD_CARD_REPORT = new Byte[] { (byte) 0x80, 0x09, 0x02 };

	private int usercode;

	private int resultCode = ErrorCodeDefine.SUCCESS;

	public int init() {
		if (zwavedevice == null) {
			return ErrorCodeDefine.DEVICE_NOT_EXSIT;
		}

		if (!ConnectionManager.contants(zwavedevice.getDeviceid())) {
			return ErrorCodeDefine.DEVICE_OFFLINE;
		}

		if (!initCardinfo())
			return resultCode;

		initvalid();
		CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(command, zwavedevice.getNuid());
		ZwaveReportRequestWrap rst = ZwaveReportRequestManager.sendRequest(zwavedevice.getDeviceid(),
				zwavedevice.getNuid(), ct, REPORT_ADD_CARD_REPORT, 5, 0);

		if (rst.getResponse() != null)
			onReport(rst.getResponse());
		else
			this.resultCode = rst.getAckresult();

		if (status == ErrorCodeDefine.SUCCESS)
			saveDoorlockUser();

		resultCode = status;

/*		Date dc = new Date();
		String tzid = GatewayHelper.getRemoteTimezoneId(zwavedevice.getDeviceid());
		if (StringUtils.isNotBlank(tzid))
			dc = TimeZoneHelper.timezoneTranslate(dc, TimeZone.getDefault(), TimeZone.getTimeZone(tzid));

		DoorlockHelper.sendCurrentTime(zwavedevice.getDeviceid(), zwavedevice.getNuid(), dc);*/

		return resultCode;
	}
	public boolean sendcommand(){
		return true;
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
		du.setWeekday(weekday);
		du.setStarttime(starttime);
		du.setEndtime(endtime);
		if (c != null)
			du.setCardid(c.getCardid());

		dus.save(du);

	}

	public boolean initCardinfo() {
		if (cardinfo.length() != 8 && cardinfo.length() != 16) {
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false;
		}

		command[5] = (byte) ((this.cardtype << 4) | (cardinfo.length() / 2));
		if (cardinfo.length() == 8)
			cardinfo += "FFFFFFFF";

		for (int i = 0; i < cardinfo.length() / 2; i++) {
			command[i + 6] = (byte) (Integer.valueOf(cardinfo.substring(i * 2, (i * 2) + 2), 16)).intValue();
		}

		return true;
	}

	private void initvalid() {
		String starthour = "00";
		String endhour = "23";
		byte isweek = (byte) 0x80;
		if (weekday != null && StringUtils.isNotBlank(starttime) && StringUtils.isNotBlank(endtime)) {
			starthour = starttime.substring(0, 2);
			endhour = endtime.substring(0, 2);
			isweek = (byte) (intreverse() & 0xff);
			command[14] = isweek;
			command[15] = (byte) (Integer.parseInt(starthour) & 0xff);
			command[16] = (byte) (Integer.parseInt(endhour) & 0xff);
		}
		if (StringUtils.isNotBlank(validfrom)) {
			Date vf = Utils.parseMin(validfrom);
			Calendar cs = Calendar.getInstance();
			cs.setTime(vf);

			command[17] = (byte) (cs.get(Calendar.YEAR) - 2000);
			command[18] = (byte) (cs.get(Calendar.MONTH) + 1);
			command[19] = (byte) cs.get(Calendar.DAY_OF_MONTH);
			command[20] = (byte) cs.get(Calendar.HOUR_OF_DAY);
		}

		if (StringUtils.isNotBlank(this.validthrough)) {
			Date vt = Utils.parseMin(validthrough);
			Calendar ce = Calendar.getInstance();
			ce.setTime(vt);
			command[21] = (byte) (ce.get(Calendar.YEAR) - 2000);
			command[22] = (byte) (ce.get(Calendar.MONTH) + 1);
			command[23] = (byte) ce.get(Calendar.DAY_OF_MONTH);
			command[24] = (byte) ce.get(Calendar.HOUR_OF_DAY);
		}
	}

	private int intreverse() {
		int wd = 0;
		for (int i = 0; i < INT_TO_WEEK_APP.length; i++) {
			if ((weekday & INT_TO_WEEK_APP[i]) != 0)
				wd = wd | INT_TO_WEEK_LOCK[i];
		}
		return wd;
	}

	private void onReport(byte[] report) {
		if (report[3] == 1) {
			usercode = (report[4] & 0xff) * 256 + (report[5] & 0xff);

			status = ErrorCodeDefine.SUCCESS;
		} else if (report[3] == 2)
			status = STATUS_CARD_OVERFLOW;
		else if (report[3] == 0)
			status = ErrorCodeDefine.DOORLOCK_SETPASSWORD_CLASH;
	}
	
	@Override
	public void setMainValue(String username,int usercode,String cardinfo, int cardtype, String validfrom, String validthrough, int zwavedeviceid,
			ZWaveDevice lock,int weekday, String starttime, String endtime) {
		this.cardname = username;
		this.usercode = usercode;
		this.cardinfo = cardinfo;
		this.cardtype = cardtype;
		this.validfrom = validfrom;
		this.validthrough = validthrough;
		this.zwavedeviceid = zwavedeviceid;
		this.zwavedevice = lock;
		this.weekday = weekday;
		this.starttime = starttime;
		this.endtime = endtime;
	}
	
	@Override
	public int getResultCode() {
		return resultCode;
	}
	
	@Override
	public int getUsercode() {
		return usercode;
	}
}
