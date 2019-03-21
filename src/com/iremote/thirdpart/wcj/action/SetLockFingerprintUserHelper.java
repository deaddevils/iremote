package com.iremote.thirdpart.wcj.action;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.annotations.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.zwavecommand.notifiy.IZwaveReportConsumer;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;
import com.iremote.service.ZWaveDeviceService;

public class SetLockFingerprintUserHelper implements IZwaveReportConsumer {

	private static final int[] INT_TO_WEEK_APP = new int[] { 128, 64, 32, 16, 8, 4, 2, 1 };
	private static final int[] INT_TO_WEEK_LOCK = new int[] { 128, 1, 2, 4, 8, 16, 32, 64 };

	private static Log log = LogFactory.getLog(SetLockFingerprintUserHelper.class);

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int usercode;
	private int baglen;

	private int weekday;

	private ZWaveDevice lock;
	private Byte[] reskey;
	private byte[] report;
	private Byte[] lastreskey = new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x0c };
	private long timeouttime = 0;
	private Object waitobj = new Object();
	private boolean finished;
	private int ackresult;

	public JSONObject sendCommand(int zwavedeviceid,String fingerprint,String validfrom,String validthrough,int weekday,String starttime,String endtime) {
		this.weekday = weekday;
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);

		if (check() == false){
			return getReturnResult(resultCode,usercode);
		}
		if (StringUtils.isBlank(fingerprint)) {
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return getReturnResult(resultCode,usercode);
		}

		byte[] fb = Base64.decodeBase64(fingerprint);
		byte temp = fb[0];
		for (int i = 1; i < fb.length; i++) {
			temp ^= fb[i];
		}

		if (StringUtils.isBlank(validfrom))
			validfrom = "2010-01-01 00:00:00";
		if (StringUtils.isBlank(validthrough))
			validthrough = "2099-01-01 00:00:00";
		Date datefrom = Utils.parseTime(validfrom);
		Date dateto = Utils.parseTime(validthrough);

		Calendar cf = Calendar.getInstance();
		cf.setTime(datefrom);
		Calendar ct = Calendar.getInstance();
		ct.setTime(dateto);
		String starthour = "00";
		String startmin = "00";
		String endhour = "23";
		String endmin = "59";
		byte isweek = (byte) 0x80;
		if (weekday != 0 && StringUtils.isNotBlank(starttime) && StringUtils.isNotBlank(endtime)) {
			starthour = starttime.substring(0, 2);
			startmin = starttime.substring(3, 5);
			endhour = endtime.substring(0, 2);
			endmin = endtime.substring(3, 5);
			isweek = (byte) (intreverse() & 0xff);
		}
		byte[] b = new byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x0d, (byte) 0xff,
				(byte) (fb.length / 256), (byte) (fb.length % 256), 0x00, (byte) (0xff ^ temp), isweek,
				(byte) (Integer.parseInt(starthour) & 0xff), (byte) (Integer.parseInt(startmin) & 0xff),
				(byte) (Integer.parseInt(endhour) & 0xff), (byte) (Integer.parseInt(endmin) & 0xff),
				(byte) (cf.get(Calendar.YEAR) - 2000), (byte) (cf.get(Calendar.MONTH) + 1),
				(byte) (cf.get(Calendar.DAY_OF_MONTH)), (byte) (cf.get(Calendar.HOUR_OF_DAY)),
				(byte) (cf.get(Calendar.MINUTE)), (byte) (ct.get(Calendar.YEAR) - 2000),
				(byte) (ct.get(Calendar.MONTH) + 1), (byte) (ct.get(Calendar.DAY_OF_MONTH)),
				(byte) (ct.get(Calendar.HOUR_OF_DAY)), (byte) (ct.get(Calendar.MINUTE)) };

		Byte[] responsekey = new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x0e };
		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(),
				createCommandTlv(b), responsekey, 10, 0);
		ZwaveReportRequestWrap wrapr = null;
		if (checkResult(wrap, 8)) {
			byte[] resp = wrap.getResponse();
			//usercode = resp[7];
			//lastreskey[7] = (byte) usercode;
			//ZwaveReportNotifyManager.getInstance().regist(lock.getDeviceid(), lock.getNuid(), lastreskey, IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND,this);

			baglen = 256 * resp[9] + resp[10];
			byte[] fbc;
			int bagcount = fb.length % baglen == 0 ? fb.length / baglen : fb.length / baglen + 1;
			for (int i = 0; i < bagcount; i++) {
				if (i == bagcount - 1) {
					fbc = subBytes(fb, i * baglen, fb.length - baglen * i);
					registFinalResponse();
				} else {
					fbc = subBytes(fb, i * baglen, baglen);
				}
				for (int j = 0; j < 3; j++) {
					wrapr = sendrequest(i, fbc);
					if (checkResult(wrapr, 8)) {
						break;
					}
				}
				if (!checkResult(wrapr, 8)) {
					resultCode = ErrorCodeDefine.FINGERPRINT_SET_FAILED;
					return getReturnResult(resultCode,usercode);
				}
			}
		}
		getResponse();
		if (report != null && report.length >= 9) {
			log.info("report8" + report[8]);
			if (report[8] == 1){
				usercode = report[7];
				resultCode = ErrorCodeDefine.SUCCESS;
			}
			else if (report[8] == 0)
				resultCode = ErrorCodeDefine.FINGERPRINT_SET_FAILED;
			else if (report[8] == 2)
				resultCode = ErrorCodeDefine.FINGERPRINT_IS_FULL;
			else if (report[8] == 3)
				resultCode = ErrorCodeDefine.FINGERPRINT_DEVICE_NORESPONSE;
			else if (report[8] == 4)
				resultCode = ErrorCodeDefine.FINGERPRINT_WRITE_FAILED;
			else if (report[8] == 5)
				resultCode = ErrorCodeDefine.FINGERPRINT_STORAGE_FAILED;
		} else{
			resultCode = ErrorCodeDefine.TIME_OUT;
			log.info("set fingerprint without report!");
		}
		
		return getReturnResult(resultCode,usercode);
	}

	private void registFinalResponse() {
		ZwaveReportNotifyManager.getInstance().regist(lock.getDeviceid(), lock.getNuid(), lastreskey, 10,this);
		
	}

	private JSONObject getReturnResult( int resultCode ,int usercode){
		JSONObject json = new JSONObject();
		json.put("resultCode", resultCode);
		json.put("usercode", usercode);
		return json;
	}
	private boolean check() {
		if (lock == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}
		if (lock.getStatus() != null && lock.getStatus() == -1) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		if (ConnectionManager.isOnline(lock.getDeviceid()) == false) {
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		return true;
	}

	private boolean checkResult(ZwaveReportRequestWrap wrap, int resultindex) {
		byte[] rst = wrap.getResponse();
		if (rst == null) {
			log.info("result is null");
			resultCode = wrap.getAckresult();
			return false;
		}
		if (log.isInfoEnabled())
			Utils.print("Result is ", rst);
		if (rst[resultindex] == 0x01)
			return true;
		return false;
	}

	private int intreverse() {
		int wd = 0;
		for (int i = 0; i < INT_TO_WEEK_APP.length; i++) {
			if ((weekday & INT_TO_WEEK_APP[i]) != 0)
				wd = wd | INT_TO_WEEK_LOCK[i];
		}
		return wd;
	}

	private CommandTlv createCommandTlv(byte[] command) {
		CommandTlv ct = new CommandTlv(30, 7);
		ct.addUnit(new TlvByteUnit(70, command));
		ct.addUnit(new TlvIntUnit(72, 0, 1));
		ct.addUnit(new TlvIntUnit(75, 2, 1));
		ct.addUnit(new TlvIntUnit(71, lock.getNuid(), CommandUtil.getnuIdLenght(lock.getNuid())));
		return ct;
	}

	private byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
	}

	private ZwaveReportRequestWrap sendrequest(int i, byte[] fbc) {
		byte[] cc = new byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x01, (byte) i };
		byte[] c = (byte[]) ArrayUtils.addAll(cc, fbc);
		reskey = new Byte[] { (byte) 0x80, 0x07, 0x00, (byte) 0xA0, 0x10, 0x01, 0x03, (byte) i };
		ZwaveReportRequestWrap wrapi = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(),
				createCommandTlv(c), reskey, 10, 0);
		return wrapi;
	}

	@JSON(serialize = false)
	@JSONField(serialize = false)
	public byte[] getResponse() {
		if (this.report != null)
			return this.report;
		synchronized (waitobj) {
			if (this.report != null)
				return this.report;
			try {
				waitobj.wait(IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND * 1000);
			} catch (InterruptedException e) {
			}
			if (this.report == null && this.ackresult == ErrorCodeDefine.EXECUTE_STATUS_NOT_START)
				this.resultCode = ErrorCodeDefine.TIME_OUT;
			finished = true;
			ZwaveReportNotifyManager.getInstance().unregist(lock.getDeviceid(), lock.getNuid(), lastreskey, this);
		}
		return report;
	}

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report) {
		synchronized (waitobj) {

			this.report = report;
			finished = true;
			if (ackresult == ErrorCodeDefine.EXECUTE_STATUS_NOT_START)
				ackresult = ErrorCodeDefine.SUCCESS;
			waitobj.notifyAll();
		}

	}

	@JSON(serialize = false)
	@JSONField(serialize = false)
	@Override
	public boolean isFinished() {
		if (finished) // finished
			return true;
		else if (this.timeouttime == 0) // not execute
			return false;
		else if (System.currentTimeMillis() > this.timeouttime) { // time out .
			this.resultCode = ErrorCodeDefine.TIME_OUT;
			log.info("Time out , discard task");
			if (log.isInfoEnabled())
				log.info(com.alibaba.fastjson.JSON.toJSONString(this));
			return true;
		} else
			return false;
	}

}
