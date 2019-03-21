package com.iremote.action.device.doorlock;

import java.util.Calendar;

import com.google.common.primitives.Bytes;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ByteUtils;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class DoorlockPasswordHelper
{
	public static byte[] createSetLockUserPasswordCommand(byte usercode,String password , byte[] userid)
	{
		StringBuffer sb = new StringBuffer(password);
		for ( ; sb.length() <= 12 ; )
			sb.append("F");
		
		byte[] b = new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x01,usercode,0x35,0x35,0x35,0x35,0x35,0x35,0x35,0x35,
							Integer.valueOf(sb.substring(0, 2), 16).byteValue(),
							Integer.valueOf(sb.substring(2, 4), 16).byteValue(),
							Integer.valueOf(sb.substring(4, 6), 16).byteValue(),
							Integer.valueOf(sb.substring(6, 8), 16).byteValue(),
							Integer.valueOf(sb.substring(8, 10), 16).byteValue(),
							Integer.valueOf(sb.substring(10, 12), 16).byteValue()};
		
		if ( userid != null )
			System.arraycopy(userid, 0, b, 8, userid.length);
		return b ;
	}

	public static byte[] createZwaveSetLockUserPasswordCommnad(String password, byte usercode) {
		char[] chars = password.toCharArray();
		byte[] bytes1;
		if (chars.length <= 8) {
			bytes1 = new byte[chars.length];
		} else {
			bytes1 = new byte[8];
		}

		for (int i = 0; i < chars.length && i < 8; i++) {
			bytes1[i] = (byte) chars[i];
		}
		byte[] bytes = {0x63, 0x01, usercode, 0x01};

		return ByteUtils.addBytes(bytes, bytes1);
	}

	public static byte[] createSetValidTimeCommand(byte usercode , String validfrom , String validthrought, byte usertype)
	{
		Calendar cs = Calendar.getInstance();
		cs.setTime(Utils.parseTime(validfrom));
		
		Calendar ce = Calendar.getInstance();
		ce.setTime(Utils.parseTime(validthrought));
		
		return createSetValidTimeCommand(usercode , cs , ce, usertype);
	}
	
	public static byte[] createSetValidTimeCommand(byte usercode , Calendar validfrom , Calendar validthrought, byte usertype)
	{
		Calendar cs = validfrom;
		Calendar ce = validthrought;
		
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
		
		return b ;
	}
	
	public static CommandTlv createCommandTlv(byte[] command , int nuid)
	{
		CommandTlv ct = new CommandTlv(30 , 7);
		ct.addUnit(new TlvByteUnit(70 , command));
		if ( nuid <= 256 )
			ct.addUnit(new TlvIntUnit(71 , nuid , 1));
		else 
			ct.addUnit(new TlvIntUnit(71 , nuid , 4));
		ct.addUnit(new TlvIntUnit(72 , 0 , 1));
		return ct ;
	}
	
	public static byte[] createAddCardCommand(byte cardtype , byte[] cardinfo)
	{
		return new byte[]{(byte)0x80 , 0x09 , 0x01 , (byte)0xff,(byte)0xff, cardtype , 
				cardinfo[0],cardinfo[1],cardinfo[2],cardinfo[3],cardinfo[4],cardinfo[5],cardinfo[6],cardinfo[7],
				(byte)0xff,0,23,  //
				1 , 1 , 1 , 0 ,  // valid from 2001-01-01 00:00:00
				99 , 12 , 31 , 23 } ; // valid through 2099-12-31 23:59:59
	}
	
	public static byte[] createInputFingerprintCommand()
	{
		return new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x07,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	}
	
	public static byte[] createDeletePasswordCommand(byte usercode)
	{
		return new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x09,usercode,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	}
	
	public static byte[] createDeleteCardCommand(int usercode)
	{
		return new byte[]{(byte)0x80,0x09,0x03,(byte)(usercode / 256),(byte)(usercode % 256 ),0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	}
	
	public static byte[] createDeleteFingerprintCommand(byte usercode)
	{
		return new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x09,usercode,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	}
		
	public static Integer checkResponse(IAsyncResponse response)
	{
		if ( response != null )
		{
			Object rst = response.getAckResponse(-1);
			if ( rst == null )
			{
				return null ;
			}
			else 
			{
				byte[] br = (byte[]) rst;
				return TlvWrap.readInt(br, TagDefine.TAG_RESULT, 4);
			}
		}
		return null ;
	}
	
	public static int checkZwaveDevice(ThirdPart thirdpart , PhoneUser phoneuser,ZWaveDevice zd)
	{
		if ( zd == null )
		{
			return ErrorCodeDefine.DEVICE_NOT_EXSIT;
		}
		
		if ( zd.getStatus() != null && zd.getStatus() == -1 )
		{
			return ErrorCodeDefine.DEVICE_OFFLINE;
		}
		
		if ( ConnectionManager.isOnline(zd.getDeviceid()) == false )
		{
			return ErrorCodeDefine.DEVICE_OFFLINE;
		}
		
		if ( checkprivilege(thirdpart , phoneuser,zd) == false )
		{
			return ErrorCodeDefine.NO_PRIVILEGE;
		}
		
		return ErrorCodeDefine.SUCCESS;
	}
	
	public static boolean checkprivilege(ThirdPart thirdpart , PhoneUser phoneuser,ZWaveDevice zd)
	{
		if ( thirdpart != null )
		{
			ComunityRemoteService crs = new ComunityRemoteService();
			if ( crs.query(thirdpart.getThirdpartid(), zd.getDeviceid()) != null )
				return true;
		}
		if ( phoneuser != null )
			return PhoneUserHelper.checkPrivilege(phoneuser, zd);

		return false ;
	}
}
