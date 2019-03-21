package com.iremote.infraredtrans.zwavecommand.doorlock;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.domain.DeviceExtendInfo;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.service.DeviceExtendInfoService;

public class DoorlockVersionReportProcessor extends ZWaveReportBaseProcessor
{
	public DoorlockVersionReportProcessor()
	{
		super();
		this.dontpusmessage();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		zrb.getDevice().setProductor2(JWStringUtils.toHexString(new byte[]{zrb.getCmd()[7]}));
		if ( IRemoteConstantDefine.JWZH_ZWAVE_PRODUCTOR2.equals(zrb.getDevice().getProductor2())
				&& StringUtils.isBlank(zrb.getDevice().getProductor()))
		{
			zrb.getDevice().setProductor(IRemoteConstantDefine.JWZH_ZWAVE_PRODUCTOR);
			JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(zrb.getDeviceid() , new Date() , 0) );
		}
		
		
		zrb.getDevice().setModel2( JWStringUtils.toHexString(new byte[]{zrb.getCmd()[8]}));
		zrb.getDevice().setFunctionversion(JWStringUtils.toHexString(new byte[]{zrb.getCmd()[9] , zrb.getCmd()[10]}));
		
		int size = 0 ;
		for ( ; size + 11 < zrb.getCmd().length && zrb.getCmd()[11 + size] != 0 ; size ++ )
			;  //do nothing
		byte[] v2 = new byte[size];
		System.arraycopy(zrb.getCmd(), 11, v2, 0, v2.length);
		zrb.getDevice().setVersion2(new String(v2));
		
		saveDeviceExtendInfo();
		
		if ( zrb.getPhoneuser() != null )
		{
			zrb.getPhoneuser().setLastupdatetime(new Date());
			PhoneUserHelper.sendInfoChangeMessage(zrb.getPhoneuser());
		}
	}
	
	private void saveDeviceExtendInfo()
	{
		DeviceExtendInfo dei = new DeviceExtendInfo();
		dei.setZwavedeviceid(zrb.getDevice().getZwavedeviceid());
		JSONArray ja = (JSONArray)JSONArray.toJSON(zrb.getCmd());
		dei.setZwaveproductormessage(ja.toJSONString());

		DeviceExtendInfoService des = new DeviceExtendInfoService();
		des.save(dei);
	}

	@Override
	public String getMessagetype()
	{
		return null;
	}

}
