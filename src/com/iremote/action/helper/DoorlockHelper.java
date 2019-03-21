package com.iremote.action.helper;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestManager;
import com.iremote.infraredtrans.zwavecommand.request.ZwaveReportRequestWrap;

public class DoorlockHelper
{
	private static Log log = LogFactory.getLog(DoorlockHelper.class);
	private static final int[] INT_TO_WEEK_APP = new int[]{128 ,64,32,16,8,4,2,1};
	private static final int[] INT_TO_WEEK_LOCK = new int[]{128 ,1,2,4,8,16,32,64};
	
	public static Pair<Integer , Integer> sendPassword(byte usercode , String password , ZWaveDevice lock)
	{
		StringBuffer sb = new StringBuffer(password);
		for ( ; sb.length() <= 12 ; )
			sb.append("F");
		
		byte[] b = new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x01,usercode,0x35,0x35,0x35,0x35,0x36,0x36,0x36,0x36,
				Integer.valueOf(sb.substring(0, 2), 16).byteValue(),
				Integer.valueOf(sb.substring(2, 4), 16).byteValue(),
				Integer.valueOf(sb.substring(4, 6), 16).byteValue(),
				Integer.valueOf(sb.substring(6, 8), 16).byteValue(),
				Integer.valueOf(sb.substring(8, 10), 16).byteValue(),
				Integer.valueOf(sb.substring(10, 12), 16).byteValue()};
		
		Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x03 ,null} ;
		if ( usercode != 0 && ( usercode & 0xff) != 0xff )
			responsekey[7] = usercode ;
		
		CommandTlv ct = CommandUtil.createCommandTlv(b, lock.getNuid());
		ZwaveReportRequestWrap wrap= ZwaveReportRequestManager.sendRequest(lock.getDeviceid(), lock.getNuid(), ct, responsekey, IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND * 2, 0);
		
		byte[] rst = wrap.getResponse();
		int irst ;
		if ( rst == null )
		{
			log.info("result is null");
			irst = wrap.getAckresult() ;
			return Pair.of(ErrorCodeDefine.TIME_OUT, 0) ;
		}
		if ( log.isInfoEnabled())
			Utils.print("Result is ", rst);
		switch(rst[8])
		{
		case 1:
			irst = ErrorCodeDefine.SUCCESS;
			break;
		case 2:
			irst = ErrorCodeDefine.DOORLOCK_PASSWORD_EXHAUST;
			break;
		case 3:
			irst = ErrorCodeDefine.DOORLOCK_PASSWORD_DUPLICATE;
			break;
		default:
			irst = ErrorCodeDefine.DEVICE_RETURN_ERROR;
		}
		
		int iuc = 0 ;
		if ( irst == ErrorCodeDefine.SUCCESS ) 
			iuc= wrap.getResponse()[7] & 0xff;
		
		return Pair.of(irst, iuc) ;
	}
	
	public static int sendTimeConfigure(String deviceid , int nuid, byte usertype , byte usercode,String validfrom , String validthrough )
	{
		return sendTimeConfigure(deviceid ,  nuid,  usertype ,  usercode, validfrom ,  validthrough , false);
	}
	
	public static int sendTimeConfigure(String deviceid , int nuid, byte usertype , byte usercode,String validfrom , String validthrough , boolean needresponse )
	{
		usertype = translateusertype(usertype);
		
		Calendar cs = Calendar.getInstance();
		cs.setTime(Utils.parseTime(validfrom));
		
		Calendar ce = Calendar.getInstance();
		ce.setTime(Utils.parseTime(validthrough));		
		
		byte[] b = new byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x05,0x01,usertype,usercode,0x01,
				(byte)(cs.get(Calendar.YEAR) - 2000 ),
				(byte)(cs.get(Calendar.MONTH) + 1 ),
				(byte)cs.get(Calendar.DAY_OF_MONTH),
				(byte)cs.get(Calendar.HOUR_OF_DAY),
				(byte)cs.get(Calendar.MINUTE),
				(byte)(ce.get(Calendar.YEAR) - 2000 ),
				(byte)(ce.get(Calendar.MONTH) + 1 ),
				(byte)ce.get(Calendar.DAY_OF_MONTH),
				(byte)ce.get(Calendar.HOUR_OF_DAY),
				(byte)ce.get(Calendar.MINUTE),
				0x00 };
				
		Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x06,usertype, usercode,0x01} ;

		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(deviceid, nuid, CommandUtil.createCommandTlv(b, nuid), responsekey, 0, 0);

		return checkResult(wrap , 10 , needresponse);
	}
	
	public static int sendWeekTimeConfig(String deviceid , int nuid, byte usertype , byte usercode , Integer weekday , String starttime , String endtime)
	{
		if ( weekday == null || StringUtils.isBlank(starttime) || StringUtils.isBlank(endtime) )
			return ErrorCodeDefine.SUCCESS;
		
		int wd = 0 ;
		for ( int i = 0 ; i < INT_TO_WEEK_APP.length ; i ++ )
		{
			if ( ( weekday & INT_TO_WEEK_APP[i] ) != 0 ) 
				wd = wd | INT_TO_WEEK_LOCK[i];
		}
		
		
		byte[] b = new byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x01,0x01,usertype,usercode,0x01 ,
				(byte)wd , 
				Integer.valueOf(starttime.substring(0,2)).byteValue(),
				Integer.valueOf(starttime.substring(3,5)).byteValue(),
				Integer.valueOf(endtime.substring(0,2)).byteValue(),
				Integer.valueOf(endtime.substring(3,5)).byteValue(),
				0,0,0,0,0,0};
		
		Byte[] responsekey = new Byte[]{(byte)0x80,0x07,0x00,0x70,0x10,0x01,0x03,usertype, usercode,0x01} ;

		ZwaveReportRequestWrap wrap = ZwaveReportRequestManager.sendRequest(deviceid, nuid, CommandUtil.createCommandTlv(b, nuid), responsekey, 0, 0);

		return checkResult(wrap , 10 , true);
	}
	
	private static byte translateusertype(byte usertype)
	{
		if ( usertype == 0x15 ) //password
			return 1 ;
		else if ( usertype == 0x20 ) // card 
			return 2 ;
		else if ( usertype == 0x16) //fingerprint
			return 3 ;
		return usertype ;
			
	}
	
	public static void sendCurrentTime(String deviceid , int nuid)
	{
		sendCurrentTime(deviceid , nuid , new Date());
	}
	
	public static void sendCurrentTime(String deviceid , int nuid , Date time)
	{
		log.info(System.currentTimeMillis());
		log.info(time);
		Calendar ct = Calendar.getInstance();
		ct.setTime(time);
		byte[] b = new byte[]{(byte)0x8B,0x01,
							(byte)(ct.get(Calendar.YEAR) / 256 ),
							(byte)(ct.get(Calendar.YEAR) % 256 ),
							(byte)(ct.get(Calendar.MONTH) + 1 ),
							(byte)ct.get(Calendar.DAY_OF_MONTH),
							(byte)ct.get(Calendar.HOUR_OF_DAY),
							(byte)ct.get(Calendar.MINUTE),
							(byte)ct.get(Calendar.SECOND)};
		
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, CommandUtil.createCommandTlv(b , nuid) , 0);
	}
	
	private static int checkResult(ZwaveReportRequestWrap wrap , int resultindex , boolean needresponse)
	{
		byte[] rst = wrap.getResponse();
		int ackresult = wrap.getAckresult();

		//TODO
		if (!needresponse) {
			if (rst == null) {
				log.info("result is null");
				if (needresponse)
					return ErrorCodeDefine.TIME_OUT;
				return ackresult;
			}
			if (log.isInfoEnabled())
				Utils.print("Result is ", rst);
			if (rst[resultindex] == 0x01)
				return ErrorCodeDefine.SUCCESS;
			return ErrorCodeDefine.DEVICE_RETURN_ERROR;
		}
		if (rst == null) {
			log.info("result is null");
			if (ackresult == ErrorCodeDefine.SUCCESS) {
				return ErrorCodeDefine.TIME_OUT;
			}
			return ackresult;
		} else {
			if (log.isInfoEnabled()) {
				Utils.print("Result is ", rst);
			}
			if (rst[resultindex] == 0x01) {
				return ErrorCodeDefine.SUCCESS;
			}
			return ErrorCodeDefine.DEVICE_RETURN_ERROR;
		}
	}
	
	public static boolean isWifiLock(ZWaveDevice zwavedevice)
	{
		if ( zwavedevice == null )
			return false ;
		return zwavedevice.getNuid() > 256 ;
	}
}
