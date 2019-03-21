package com.iremote.action.device.authorize;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.iremote.action.helper.SinaShortUrlHelper;
import com.iremote.action.sms.RandCodeHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceShareSource;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.common.sms.DomesticPhoneUserSmsSender2;
import com.iremote.common.sms.SMSInterface;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.OemProductor;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.OemProductorService;
import com.iremote.service.UserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "device", parameters = {"zwavedeviceid"})
public class DevicePrivilegeGrantAction implements ServletRequestAware
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber ;
	private String username ;
	private PhoneUser phoneuser ;
	private int zwavedeviceid ;
	private String validfrom;
	private String validthrough;
	private ZWaveDevice zwavedevice;
	private ZWaveDeviceShare zwavedeviceshare;
	private HttpServletRequest request;
	private int validtype = 0 ;
	private String token ;
	private String smssign;
	private String url ;
	private String shorturl ;
	private int platform;
	
	public String execute()
	{	
		ZWaveDeviceService svr = new ZWaveDeviceService();
		zwavedevice = svr.query(zwavedeviceid);

		if ( zwavedevice == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( StringUtils.isBlank(validfrom))
			validfrom = "2001-01-01 00:00";
		if ( StringUtils.isBlank(validthrough))
			validthrough = "2099-12-31 23:59";
		
		saveZWaveDeviceShare();
		sendSMS();
		
		return Action.SUCCESS;
	}
	
	public String forward()
	{
		platform = phoneuser.getPlatform();
		return Action.SUCCESS;
	}
	
	private void sendSMS(){
		OemProductorService ops = new OemProductorService();
		OemProductor oemproductor = ops.querybyplatform(this.phoneuser.getPlatform());
		if(oemproductor!=null){
			smssign = oemproductor.getSmssign();
		}else{
			smssign = "\u3010\u7BA1\u5BB6\u63D0\u9192\u3011";
		}

		if(StringUtils.isNotBlank(shorturl)&&!IRemoteConstantDefine.INTERNATIONAL_DIALING_CODE_INDIA.equals(countrycode)){
			MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.DEVICE_AUTHRIZE_SHORTURL, this.phoneuser.getPlatform(), this.phoneuser.getLanguage());
			mp.getParameter().put("validfrom", validfrom);
			mp.getParameter().put("validthrough", validthrough);
			mp.getParameter().put("url", shorturl);
			mp.getParameter().put("name", zwavedevice.getName());
			DomesticPhoneUserSmsSender2 dpuss = new DomesticPhoneUserSmsSender2();
			dpuss.sendSMS(countrycode, phonenumber, mp,smssign);//TODO Foreign users do not deal with temporarily
		} else {
			MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.DEVICE_AUTHRIZE, this.phoneuser.getPlatform(), this.phoneuser.getLanguage());
			mp.getParameter().put("validfrom", validfrom);
			mp.getParameter().put("validthrough", validthrough);
			mp.getParameter().put("url", url);
			mp.getParameter().put("name", zwavedevice.getName());
			mp.getParameter().put("code", token);
			
			SMSInterface.sendSMS(countrycode, phonenumber, mp, this.phoneuser.getPlatform());
		}
	}
	
	private void saveZWaveDeviceShare()
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		zwavedeviceshare = new ZWaveDeviceShare();
		
		zwavedeviceshare.setZwavedeviceid(zwavedeviceid);
		zwavedeviceshare.setValidfrom(Utils.parseMin(validfrom));
		zwavedeviceshare.setValidthrough(Utils.parseMin(validthrough));
		zwavedeviceshare.setTouser(phonenumber);
		zwavedeviceshare.setUsername(username);
		zwavedeviceshare.setShareowntype(DeviceShareSource.phoneusertemp.getSource());
		
		createTokenandUrl();

		zwavedeviceshare.setDeviceid(zwavedevice.getDeviceid());
		zwavedeviceshare.setValidtype(validtype);
		zwavedeviceshare.setCreatetime(new Date());
		
		zdss.save(zwavedeviceshare);
	}
	
	private void createTokenandUrl()
	{
		UserService svr = new UserService();
		
		if ( IRemoteConstantDefine.INTERNATIONAL_DIALING_CODE_CHINE.equals(countrycode))
		{
			token = Utils.createsecuritytoken(32);
			zwavedeviceshare.setToken(token.substring(0, 16));
			zwavedeviceshare.setSecuritycode(svr.encryptPassword("", token));
			createurl();
			if ( createShortUrl())
				return ;
			token = null ;
		}
		
		token = RandCodeHelper.createRandCode();
		zwavedeviceshare.setToken(token.substring(0, 2));
		zwavedeviceshare.setSecuritycode(svr.encryptPassword(phonenumber, token));
		createurl();
		
	}
	
	private void createurl()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("https://").append(request.getServerName()).append(request.getContextPath());
		sb.append(IRemoteConstantDefine.DEVICE_OPERATE_URI);
		
		url = sb.toString();
	}
	
	private boolean createShortUrl()
	{
		String newurl = url+"?token="+token;
		shorturl = SinaShortUrlHelper.sinaShortUrl(newurl);
		
		if(StringUtils.isNotBlank(shorturl))
			return true ;
		return false ;
	}
	
	public void setServletRequest(HttpServletRequest request) 
	{
		this.request = request;
	}
	
	public int getResultCode()
	{
		return resultCode;
	}
	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	public void setValidfrom(String validfrom)
	{
		this.validfrom = validfrom;
	}
	public void setValidthrough(String validthrough)
	{
		this.validthrough = validthrough;
	}

	public void setCountrycode(String countrycode)
	{
		this.countrycode = countrycode;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setValidtype(int validtype) {
		this.validtype = validtype;
	}

	public int getPlatform() {
		return platform;
	}
	
	
}
