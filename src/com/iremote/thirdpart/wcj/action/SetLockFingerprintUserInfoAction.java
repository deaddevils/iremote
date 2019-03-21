package com.iremote.thirdpart.wcj.action;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.annotations.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
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
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class SetLockFingerprintUserInfoAction  implements IZwaveReportConsumer {
	
	private static final int[] INT_TO_WEEK_APP = new int[]{128 ,64,32,16,8,4,2,1};
	private static final int[] INT_TO_WEEK_LOCK = new int[]{128 ,1,2,4,8,16,32,64};
	
	private static Log log = LogFactory.getLog(SetLockFingerprintUserInfoAction.class);
	
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;
	private int usercode;
	private int baglen;
	private String fingerprint ;
	private String validfrom;
	private String validthrough;
	private int weekday;
	private String starttime ;
	private String endtime;
	private ZWaveDevice lock;
	private Byte[] reskey;
	private byte[] report ;
	private Byte[] lastreskey = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x0c,0x00};
	private long timeouttime = 0 ;
	private Object waitobj = new Object();
	private boolean finished;
	private int ackresult ;
	private int asynch;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		lock = zds.query(zwavedeviceid);
		
		if ( check() == false )
			return Action.SUCCESS;
		if(StringUtils.isBlank(fingerprint))
		{
			this.resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		byte[] fb = Base64.decodeBase64(fingerprint);
		byte temp = fb[0];
		for(int i=1;i<fb.length;i++){
			temp^=fb[i];
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
		byte isweek = (byte) 0x80 ;
		if(weekday!=0&&StringUtils.isNotBlank(starttime)&&StringUtils.isNotBlank(endtime)){
			starthour = starttime.substring(0,2);
			startmin = starttime.substring(3,5);
			endhour = endtime.substring(0,2);
			endmin = endtime.substring(3,5);
			isweek = (byte)(intreverse() & 0xff);
		}
		byte[] b = new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x0d,(byte) 0xff,(byte)(fb.length/256),(byte)(fb.length%256),0x00,(byte)(0xff ^ temp),
				isweek,
				(byte)(Integer.parseInt(starthour)&0xff),
				(byte)(Integer.parseInt(startmin)&0xff),
				(byte)(Integer.parseInt(endhour)&0xff),
				(byte)(Integer.parseInt(endmin)&0xff),
				(byte)(cf.get(Calendar.YEAR) - 2000 ),
				(byte)(cf.get(Calendar.MONTH) + 1 ),
				(byte)(cf.get(Calendar.DAY_OF_MONTH)),
				(byte)(cf.get(Calendar.HOUR_OF_DAY)),
				(byte)(cf.get(Calendar.MINUTE)),
				(byte)(ct.get(Calendar.YEAR) - 2000 ),
				(byte)(ct.get(Calendar.MONTH) + 1 ),
				(byte)(ct.get(Calendar.DAY_OF_MONTH)),
				(byte)(ct.get(Calendar.HOUR_OF_DAY)),
				(byte)(ct.get(Calendar.MINUTE))
				};
		
		Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x0e} ;
		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), createCommandTlv(b), responsekey, 10, 0);
		ZwaveReportRequestWrap wrapr = null;
		if(checkResult(wrap , 8)){
			byte[] resp = wrap.getResponse();
			usercode = resp[7];
			lastreskey[7] = (byte)usercode;
			//ZwaveReportNotifyManager.getInstance().regist(lock.getDeviceid(), lock.getNuid(), lastreskey, this);
			
			baglen = 256*resp[9]+resp[10];
			byte[] fbc ;
			int bagcount = fb.length%baglen==0?fb.length/baglen:fb.length/baglen+1;
			for(int i = 0 ; i< bagcount;i++){
				if(i==bagcount-1){
					registFinalResponse();
					fbc = subBytes(fb,i * baglen,fb.length-baglen*i);
				}else{
					fbc = subBytes(fb,i * baglen,baglen);
				}
				for(int j = 0 ;j<3;j++){
					wrapr = sendrequest(i,fbc);
					if(checkResult(wrapr , 8)){
						break;
					}
				}
				if(!checkResult(wrapr , 8)){
					this.resultCode = ErrorCodeDefine.FINGERPRINT_SET_FAILED;
					return Action.SUCCESS;
				}
			}
		}
		
		this.getResponse();
		if (this.report != null && this.report.length >= 9) {
			log.error("report8" + this.report[8]);
			if (this.report[8] == 1)
				this.resultCode = ErrorCodeDefine.SUCCESS;
			else if (this.report[8] == 0)
				this.resultCode = ErrorCodeDefine.FINGERPRINT_SET_FAILED;
			else if (this.report[8] == 2)
				this.resultCode = ErrorCodeDefine.FINGERPRINT_IS_FULL;
			else if (this.report[8] == 3)
				this.resultCode = ErrorCodeDefine.FINGERPRINT_DEVICE_NORESPONSE;
			else if (this.report[8] == 4)
				this.resultCode = ErrorCodeDefine.FINGERPRINT_WRITE_FAILED;
			else if (this.report[8] == 5)
				this.resultCode = ErrorCodeDefine.FINGERPRINT_STORAGE_FAILED;
		} else
			this.resultCode = ErrorCodeDefine.FINGERPRINT_SET_FAILED;

		return Action.SUCCESS;
	}

	private void registFinalResponse() {
		ZwaveReportNotifyManager.getInstance().regist(lock.getDeviceid(), lock.getNuid(), lastreskey, 10,this);
		
	}

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report) {
		synchronized(waitobj){
			
			this.report = report ;
			finished = true;
			if ( ackresult == ErrorCodeDefine.EXECUTE_STATUS_NOT_START )
				ackresult = ErrorCodeDefine.SUCCESS;
			waitobj.notifyAll();
		}
		
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	public byte[] getResponse()
	{
		if ( this.report != null )
			return this.report;
	
		synchronized(waitobj)
		{
			if ( this.report != null )
				return this.report;
			try
			{
				waitobj.wait(IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND * 1000);
			} 
			catch (InterruptedException e)
			{
			}
			if ( this.report == null && this.ackresult == ErrorCodeDefine.EXECUTE_STATUS_NOT_START )
				this.ackresult = ErrorCodeDefine.TIME_OUT;
			finished = true ;
			ZwaveReportNotifyManager.getInstance().unregist(lock.getDeviceid(), lock.getNuid(), lastreskey, this);
		}
		return report ;
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@Override
	public boolean isFinished() {
		if ( finished )  // finished 
			return true;
		else if ( this.timeouttime == 0 )  // not execute
			return false ;
		else if ( System.currentTimeMillis() > this.timeouttime )  // time out .
		{
			this.ackresult = ErrorCodeDefine.TIME_OUT;
			log.info("Time out , discard task");
			if ( log.isInfoEnabled())
				log.info(com.alibaba.fastjson.JSON.toJSONString(this));
			return true ;
		}
		else 
			return false ;
	}
	private ZwaveReportRequestWrap sendrequest(int i,byte[] fbc){
		byte[] cc = new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x01,(byte)i};
		byte[] c = (byte[]) ArrayUtils.addAll(cc, fbc);
		reskey = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x03,(byte)i} ;
		ZwaveReportRequestWrap wrapi = ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), createCommandTlv(c), reskey, 10, 0);
		return wrapi;
	}
	protected CommandTlv createCommandTlv(byte[] command){
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		ct.addUnit(new TlvIntUnit(75,2,1));
		ct.addUnit(new TlvIntUnit(71 , lock.getNuid() , CommandUtil.getnuIdLenght(lock.getNuid())));
		return ct ;
	}
	private boolean checkResult(ZwaveReportRequestWrap wrap , int resultindex){
		byte[] rst = wrap.getResponse();
		if ( rst == null ){
			log.info("result is null");
			this.resultCode = wrap.getAckresult();
			return false ;
		}
		if ( log.isInfoEnabled())
			Utils.print("Result is ", rst);
		if ( rst[resultindex] == 0x01 )
			return true;
		return false ;
	}
	private int intreverse()
	{
		int wd = 0 ;
		for ( int i = 0 ; i < INT_TO_WEEK_APP.length ; i ++ )
		{
			if ( ( weekday & INT_TO_WEEK_APP[i] ) != 0 ) 
				wd = wd | INT_TO_WEEK_LOCK[i];
		}
		return wd;
	}
	private boolean check(){
		if ( lock == null ){
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false;
		}		
		if ( lock.getStatus() != null && lock.getStatus() == -1 ){
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}		
		if ( ConnectionManager.isOnline(lock.getDeviceid()) == false ){
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false;
		}
		return true;
	}
	public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }
	public int getResultCode(){
		return resultCode;
	}

	public void setZwavedeviceid(int zwavedeviceid){
		this.zwavedeviceid = zwavedeviceid;
	}

	public void setValidfrom(String validfrom){
		this.validfrom = validfrom;
	}

	public void setValidthrough(String validthrough){
		this.validthrough = validthrough;
	}


	public int getUsercode() {
		return usercode;
	}

	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}

	public void setBaglen(int baglen) {
		this.baglen = baglen;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public void setAsynch(int asynch) {
		this.asynch = asynch;
	}
}
