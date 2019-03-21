package com.iremote.action.qrcode;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.domain.DeviceIndentifyInfo;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceIndentifyInfoService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class AddPowerfreeDeviceAction implements IScanQrCodeProcessor
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String message;
	private String appendmessage;
	private DeviceIndentifyInfo deviceinfo;
	private int nuid ;
	private int zwavedeviceid ;
	private PhoneUser phoneuser ;
	private String timezoneid;

	@Override
	public String execute()
	{
		if ( StringUtils.isBlank(message) || StringUtils.isBlank(appendmessage))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		JSONObject jm = JSON.parseObject(message);
		JSONObject am = JSON.parseObject(appendmessage);
		
		if ( checkGateway(am) == false )
			return Action.SUCCESS;
		
		if ( checkDevice(jm) == false )
			return Action.SUCCESS;
		
		if ( sendCommand(am) == false )
			return Action.SUCCESS;
		
		if ( saveDevice(jm ,am) == false )
			return Action.SUCCESS;
		
		if ( this.phoneuser != null )
			this.phoneuser.setLastupdatetime(new Date());
		
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);

		return Action.SUCCESS;
	}
	
	private boolean saveDevice(JSONObject jm ,JSONObject am)
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = zds.querybydeviceid(am.getString("deviceid"), nuid);
		
		if ( zd != null )
		{
			DeviceHelper.clearZwaveDeviceServiceData(zd);
			zds.delete(zd);
		}
		
		zd = new ZWaveDevice();
		zd.setDeviceid(am.getString("deviceid"));
		zd.setDevicetype(this.deviceinfo.getDevicetype());
		zd.setName(am.getString("name"));
		zd.setNuid(nuid);
		zd.setStatus(Utils.getDeviceDefaultStatus(zd.getDevicetype()));
		zd.setStatuses(Utils.getDeviceDefaultStatuses(zd.getDevicetype()));
		zd.setBattery(100);
		zd.setApplianceid(Utils.createtoken());
		
		zds.saveOrUpdate(zd);
		
		this.deviceinfo.setZwavedeviceid(zd.getZwavedeviceid());
		zwavedeviceid = zd.getZwavedeviceid();
		
		return true;
	}
	
	private boolean sendCommand( JSONObject am)
	{
		CommandTlv ct = new CommandTlv(105 , 5);
		ct.addUnit(new TlvByteUnit(87 , JWStringUtils.hexStringtobyteArray(this.deviceinfo.getDevicecode())));
		ct.addUnit(new TlvByteUnit(88 ,deviceinfo.getDevicetype().getBytes()) );
		
		byte[] rst = SynchronizeRequestHelper.synchronizeRequest(am.getString("deviceid"), ct, 0  );
		
		if ( rst == null )
		{
			resultCode = ErrorCodeDefine.TIME_OUT;
			return false ;
			
			
			//for test 
//			nuid = 12345 ;
//			return true ;
		}
		
		resultCode = TlvWrap.readInt(rst, TagDefine.TAG_RESULT, TagDefine.TAG_HEAD_LENGTH);
		
		nuid = TlvWrap.readInt(rst, TagDefine.TAG_NUID, TagDefine.TAG_HEAD_LENGTH);
		
		return (resultCode == ErrorCodeDefine.SUCCESS);
	}
	
	private boolean checkGateway(JSONObject am)
	{
		if ( !am.containsKey("deviceid") || StringUtils.isBlank(am.getString("deviceid")))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false ;
		}
		
		if ( !ConnectionManager.contants(am.getString("deviceid")))
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			return false ;
		}
		
		return true;
	}
	
	private boolean checkDevice(JSONObject jm)
	{
		if ( !jm.containsKey("qid") || StringUtils.isBlank(jm.getString("qid")))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false ;
		}
		
		DeviceIndentifyInfoService svr = new DeviceIndentifyInfoService();
		deviceinfo = svr.querybyQrcode(jm.getString("qid"));
		
		if ( deviceinfo == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return false ;
		}
		
		if ( deviceinfo.getZwavedeviceid() != null && deviceinfo.getZwavedeviceid() != 0 )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE_3;
			return false ;
		}
		
		return true ;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setAppendmessage(String appendmessage)
	{
		this.appendmessage = appendmessage;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	@Override
	public void setTimezoneid(String timezoneid) {
		this.timezoneid = timezoneid;
	}

	public static void main(String arg[])
	{
		Utils.print("", JWStringUtils.hexStringtobyteArray("102A"));
	}
}
