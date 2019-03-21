package com.iremote.thirdpart.wcj.action;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.Card;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.CardService;
import com.iremote.service.DoorlockUserService;

public class ChuangjiaCard implements AddDoorlockCardUserInterface {

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

	private int usercode;

	private int resultCode = ErrorCodeDefine.SUCCESS;
	
	private ChuangjiaCardHelper cjhelper = new ChuangjiaCardHelper();

	public int init() {
		
		if (zwavedevice == null) {
			return ErrorCodeDefine.DEVICE_NOT_EXSIT;
		}

		if (!ConnectionManager.contants(zwavedevice.getDeviceid())) {
			return ErrorCodeDefine.DEVICE_OFFLINE;
		}

		if (!cjhelper.initCardinfo(cardinfo, command) || !cjhelper.initUsercode(zwavedeviceid, command)){
			resultCode = cjhelper.getResultCode();
			return resultCode;
		}
		if (!sendcommand()){
			resultCode = cjhelper.getResultCode();
			return resultCode;
		}
		usercode = cjhelper.getUsercode();
		saveDoorlockUser();

		return resultCode;
	}
	public boolean sendcommand(){
		resultCode = cjhelper.getResultCode();
		return cjhelper.sendcommand(zwavedevice, command);
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
	}
	
	@Override
	public int getUsercode() {
		return usercode;
	}
	
	@Override
	public int getResultCode() {
		return resultCode;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
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
}
